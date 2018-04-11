package com.zifangdt.ch.ticket.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zifangdt.ch.base.api.*;
import com.zifangdt.ch.base.bo.IdAndName;
import com.zifangdt.ch.base.bo.JournalTicketBo;
import com.zifangdt.ch.base.bo.cfg.base.DetailOfTicketType;
import com.zifangdt.ch.base.dto.common.File;
import com.zifangdt.ch.base.dto.common.Notice;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.ticket.*;
import com.zifangdt.ch.base.dto.ticket.input.*;
import com.zifangdt.ch.base.dto.ticket.output.*;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.ConfigType;
import com.zifangdt.ch.base.enums.NoticeType;
import com.zifangdt.ch.base.enums.pair.RevenueOrExpense;
import com.zifangdt.ch.base.enums.ticket.*;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.util.CurrentUser;

import com.zifangdt.ch.ticket.TicketOperateEvent;
import com.zifangdt.ch.ticket.bo.AppTicketQueryBo;
import com.zifangdt.ch.ticket.bo.ReceiptTicketQueryBo;
import com.zifangdt.ch.ticket.bo.TicketQueryBo;
import com.zifangdt.ch.ticket.bo.TicketSimpleQueryBo;
import com.zifangdt.ch.ticket.mapper.TicketMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TicketService extends BaseService<Ticket, Long> {

    @Autowired
    TicketMapper ticketMapper;

    @Autowired
    CustomerServerApi customerServerApi;

    @Autowired
    ManagerServerApi managerServerApi;

    @Autowired
    TicketActionLogServer ticketActionLogServer;

    @Autowired
    ContractServerApi contractServerApi;

    @Autowired
    ConfigItemService configItemService;

    @Autowired
    ReceiptService receiptService;

    @Autowired
    FinanceServerApi financeServerApi;

    @Autowired
    UaaServerApi uaaServerApi;

    @Autowired
    CommonServerApi commonServerApi;

    @Autowired
    ApprovalServerApi approvalServerApi;

    @Autowired
    ApplicationEventPublisher publisher;

    private String generateNumber() {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDate date = LocalDate.now();
        Long count = ticketMapper.countFor(date.format(DateTimeFormatter.ofPattern("yyyy-MM-d")));
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + (count + 1);
    }

    private void fillExtraInfo(Ticket ticket, TicketCreateDto dto) {
        ticket.setSerialNumber(generateNumber());
        Customer customer = customerServerApi.getOneCustomer(dto.getCustomerId());
        if (dto.getContactName() == null) dto.setContactName(customer.getName());
        if (dto.getContactAddress() == null) dto.setContactAddress(customer.getAddress());
        if (dto.getContactPhone() == null) dto.setContactPhone(customer.getPhone());
    }

    public Page<Ticket> getAllTicket(TicketSimpleQueryBo bo) {
        if (StringUtils.isNotEmpty(bo.getSearch())) {
            //todo: 客户名称、创建人
            List<User> users = uaaServerApi.internalFindBy(bo.getSearch());
            bo.setUserIds(users.stream().map(User::getId).collect(Collectors.toSet()));
        }
        return PageHelper.startPage(bo.getPage(), bo.getSize()).doSelectPage(() -> ticketMapper.findBy(bo));
    }

    /**
     * 项目相关工单
     *
     * @param projectId
     * @return
     */
    public List<ProcedureTicketDto> getTicketsForProject(Long projectId) {
        List<Ticket> ticketList = ticketMapper.findTicketForProject(projectId);
        List<ProcedureTicketDto> result = new ArrayList<>();
        for (Ticket ticket : ticketList) {
            ProcedureTicketDto dto = new ProcedureTicketDto(ticket);
            dto.setProcedureName(contractServerApi.optionName(ticket.getSourceProcedureId()));
            dto.setUsername(uaaServerApi.getUser(ticket.getOperatorId()).getName());
            WorkDescription description = new WorkDescription();
            if (ticket.getClearing() != null) {
                BeanUtils.copyProperties(ticket.getClearing(), description);
            }
            dto.setWorkDescription(description);
            result.add(dto);
        }
        return result;
    }

    /**
     * 创建工单
     *
     * @param dto
     * @return
     */
    public Long createTicket(TicketCreateDto dto) {
        customerServerApi.getOneCustomer(dto.getCustomerId());
        isPermit(TicketActionType.CREATE, null);
        Ticket ticket = dto.convertTo();
        fillExtraInfo(ticket, dto);
        if (ticket.getSource() == TicketTypeSource.CONTRACT_TYPE) {
            if (ticket.getContractId() == null) throw new DataInvalidException("合同不能为空");
            Contract contract = contractServerApi.detail(ticket.getContractId());
            if (contract.getProjectId() == null) throw new DataInvalidException("合同没有对应的项目");
            ticket.setProjectId(contract.getProjectId());
        } else if (ticket.getSource() == TicketTypeSource.STAFF_FEEDBACK) {
            if (ticket.getSourceOrganizationId() == null) throw new DataInvalidException("需要选择部门");
            if (ticket.getSourceUserId() == null) throw new DataInvalidException("需要选择用户");
            uaaServerApi.getOneOrganization(ticket.getSourceOrganizationId());
        }
        DetailOfTicketType detail = (DetailOfTicketType) configItemService.detail(dto.getTicketTypeId());
        ticket.setTicketType(detail.getName());
        ticket.setSource(detail.getTicketTypeSource());
        save(ticket);
        ticketActionLogServer.create(TicketActionType.CREATE, ticket.getId(), CurrentUser.getUserId(), null);
        if (ticket.getProjectProcedureId() != null) {
            managerServerApi.saveUnitNum(ticket.getProjectProcedureId(), ticket.getId().toString());
        }
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.CREATE));
        return ticket.getId();
    }

    public TicketDetailDto getTicketDetail(Long ticketId) {
        Ticket ticket = get(ticketId);
        if (ticket == null) throw new DataNotFoundException("工单id=" + ticketId + "找不到");
        TicketDetailDto ticketDetailDto = new TicketDetailDto(ticket);
        ticketDetailDto.setActions(ticketDetailDto.getActions().stream().filter(actionType -> isPermitAction(actionType, ticket)).collect(Collectors.toList()));
        if (ticketDetailDto.getSource() == TicketTypeSource.CONTRACT_TYPE) {
            Contract contract = contractServerApi.detail(ticketDetailDto.getContractId());
            ContractDto contractDto = new ContractDto(contract);
            if (contractDto.getId() != null) {
                contractDto.setContractTypeNames(contractDto.getTypes().stream().map(contractServerApi::optionName).collect(Collectors.toList()));
                contractDto.setProcessStatus(approvalServerApi.getProcessDetail(contract.getProcessId()).getStatus());
                ticketDetailDto.setContract(contractDto);
            }

            ProjectDto projectDto = new ProjectDto(managerServerApi.getProjectInfo(ticketDetailDto.getProjectId()));
            ticketDetailDto.setProject(projectDto);

            ticketDetailDto.setSourceContractTypeName(contractServerApi.optionName(ticketDetailDto.getSourceContractTypeId()));
            ticketDetailDto.setSourceProcedureName(contractServerApi.optionName(ticketDetailDto.getSourceProcedureId()));
        } else if (ticketDetailDto.getSource() == TicketTypeSource.STAFF_FEEDBACK) {
            User user = uaaServerApi.getUser(ticket.getSourceUserId());
            ticketDetailDto.setSourceUserName(user != null ? user.getName() : "不存在");
            ticketDetailDto.setSourceOrganizationName(user.getOrganizationName());
        }
        ticketDetailDto.setLogs(ticketActionLogServer.getTicketLogs(ticketId));
        ticketDetailDto.setReceipts(receiptService.getTicketReceipts(ticketId));
        if (ticketDetailDto.getAttachments() != null && ticketDetailDto.getAttachments().length != 0) {
            List<File> files = commonServerApi.fileInfo(Arrays.asList(ticketDetailDto.getAttachments()));
            ticketDetailDto.setAttachmentDtos(files.stream().map(file -> new FileIdAndName(file.getId(), file.getName())).collect(Collectors.toList()));
        }
        return ticketDetailDto;
    }

    /**
     * 删除工单
     *
     * @param ticketId
     */
    public void delete(Long ticketId) {
        Ticket ticket = get(ticketId);
        if (ticket == null) throw new DataInvalidException("找不到工单");
        isPermit(TicketActionType.DELETE, ticket);
        ticket.setDelete(true);
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.DELETE, ticketId, CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.DELETE));
    }

    /**
     * 指派
     *
     * @param dto
     */
    public void assign(TicketAssignDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.ASSIGN, ticket);
        if (ticket.getTicketStatus() != TicketStatus.WAIT_ASSIGN)
            throw new DataInvalidException("工单当前状态不能指派");
        ticket.setOperatorId(dto.getUserId());
        ticket.setTicketStatus(TicketStatus.ASSIGNED);
        ticket.setAssignTime(new Date());
        updateInternal(ticket);

        ticketActionLogServer.create(TicketActionType.ASSIGN, ticket.getId(), CurrentUser.getUserId(), dto.getUserId());
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.ASSIGN));
    }

    public void accept(BaseTicketProcessDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.ACCEPT, ticket);
        ticket.setTicketStatus(TicketStatus.ACCEPTED);
        ticket.setAcceptTime(new Date());
        updateInternal(ticket);

        ticketActionLogServer.create(TicketActionType.ACCEPT, ticket.getId(), CurrentUser.getUserId(), null);
        // 新建工单应收：工单前面的状态可能会被修改，只能在接受工单时创建应收
        if (ticket.getReceiveInfo() != null && !ticket.getReceiveInfo().getTotal().equals(BigDecimal.ZERO)) {
            JournalTicketBo bo=new JournalTicketBo();
            bo.setPlannedDate(ticket.getReceiveInfo().getPaymentDate());
            bo.setPlannedMoney(ticket.getReceiveInfo().getTotal());
            bo.setRevenueOrExpense(RevenueOrExpense.REVENUE);
            bo.setTicket(getTicketDetail(dto.getTicketId()));
//            RevenueTicket revenueTicket = new RevenueTicket();
//            revenueTicket.setTicketId(ticket.getId());
//            revenueTicket.setPlannedTime(ticket.getReceiveInfo().getPaymentDate());
//            if (ticket.getContractId() != null) revenueTicket.setContractId(ticket.getContractId());
//            revenueTicket.setPlannedMoney(ticket.getReceiveInfo().getTotal());
//            revenueTicket.setName("[工单:" + ticket.getSerialNumber() + "]" + "应收");
            financeServerApi.noteTicket(bo);
        }

        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.ACCEPT));
    }

    public void reject(TicketAssignRejectDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.REJECT, ticket);
        ticket.setTicketStatus(TicketStatus.ACCEPTED);
        ticket.setTicketStatus(TicketStatus.WAIT_ASSIGN);
        ticket.setOperatorId(null);
        Remark[] remarks = ticket.getRemarks();
        List<Remark> remarks1 = remarks == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(remarks));
        Remark remark = new Remark();
        remark.setInfo(dto.getInfo());
        remark.setUserId(CurrentUser.getUserId());
        remark.setUsername(CurrentUser.getName());
        remarks1.add(remark);
        ticket.setRemarks(remarks1.toArray(new Remark[remarks1.size()]));
        ticketMapper.updateByPrimaryKey(ticket);
        ticketActionLogServer.create(TicketActionType.REJECT, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.REJECT));
    }

    public void startTicket(BaseTicketProcessDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.START, ticket);
        ticket.setTicketStatus(TicketStatus.ON_GOING);
        ticket.setOperateStartTime(new Date());
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.START, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.START));
    }

    public void createReceipt(CreateReceipt dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.CREATE_RECEIPT, ticket);
        ticket.setTicketStatus(TicketStatus.WAIT_FINISH);
        updateInternal(ticket);
        Receipt receipt = receiptService.create(dto);
        ticket.setReceipt(new ReceiptDto(receipt));
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.CREATE_RECEIPT, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.CREATE_RECEIPT));
    }

    public void rejectReceipt(ConfirmRejectReceiptDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.REJECT_RECEIPT, ticket);
        updateInternal(ticket);
        Receipt receipt = receiptService.reject(dto);
        ticket.setReceipt(new ReceiptDto(receipt));
        ticket.setTicketStatus(TicketStatus.ON_GOING);
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.REJECT_RECEIPT, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.REJECT_RECEIPT));
    }

    public void confirmReceipt(ConfirmRejectReceiptDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.CONFIRM_RECEIPT, ticket);
        updateInternal(ticket);
        Receipt receipt = receiptService.confirm(dto);
        ticket.setReceipt(new ReceiptDto(receipt));
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.CONFIRM_RECEIPT, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.CONFIRM_RECEIPT));
    }

    /**
     * 创建回访
     *
     * @param dto
     */
    public void createReturnVisit(ReturnVisitDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.CREATE_RETURN_VISIT, ticket);
        ReturnVisit returnVisit = new ReturnVisit();
        BeanUtils.copyProperties(dto, returnVisit);
        returnVisit.setUserId(CurrentUser.getUserId());
        returnVisit.setUsername(CurrentUser.getName());
        ticket.setReturnVisit(returnVisit);
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.CREATE_RETURN_VISIT, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.CREATE_RETURN_VISIT));
    }

    /**
     * 工单完成 = 回执完成+结算完成+回访完成，
     */
    public void finishTicket(BaseTicketProcessDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.FINISH, ticket);
        ticket.setTicketStatus(TicketStatus.FINISH);
        ticket.setFinishTime(new Date());
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.FINISH, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.FINISH));
        if (ticket.getProjectProcedureId() != null) {
            managerServerApi.completeUnit(ticket.getProjectProcedureId());
        }
    }

    /**
     * 创建结算
     *
     * @param dto
     */
    public void createClearing(ClearingDto dto) {
        Ticket ticket = get(dto.getTicketId());
        isPermit(TicketActionType.CREATE_CLEARING, ticket);
        Receipt currentReceipt = receiptService.getTicketCurrentReceipt(ticket.getId());
        Clearing clearing = new Clearing();
        BeanUtils.copyProperties(dto, clearing);
        clearing.setWorkloadType(currentReceipt.getWorkloadType());
        clearing.setUsername(CurrentUser.getName());
        clearing.setUserId(CurrentUser.getUserId());
        clearing.updateTotal();

        ticket.setClearing(clearing);
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.CREATE_CLEARING, ticket.getId(), CurrentUser.getUserId(), null);
        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.CREATE_CLEARING));

        // 创建财务工单结算
        JournalTicketBo bo=new JournalTicketBo();
        bo.setPlannedMoney(clearing.getTotal());
        bo.setPlannedDate(new Date());
        bo.setRevenueOrExpense(RevenueOrExpense.EXPENSE);
        bo.setTicket(getTicketDetail(dto.getTicketId()));
//        ExpenseTicketAccounting expense = new ExpenseTicketAccounting();
//        expense.setPlannedMoney(clearing.getTotal());
//        if (ticket.getContractId() != null) expense.setContractId(ticket.getContractId());
//        expense.setPlannedTime(new Date());
//        expense.setTicketId(ticket.getId());
//        expense.setTargets(uaaServerApi.getUser(ticket.getOperatorId()).getName());
        financeServerApi.noteTicket(bo);
    }

    /**
     * 财务确认结算
     */
    public void confirmClearing(ClearingConfirmDto dto) {
        Ticket ticket = get(dto.getTicketId());
        Clearing clearing = ticket.getClearing();
        clearing.setFinanceUserId(dto.getFinanceUserId());
        clearing.setFinanceUsername(dto.getFinanceUsername());
        clearing.setActualTotal(dto.getActualTotal());
        clearing.setActualTime(new Date());
        ticket.setClearing(clearing);
        updateInternal(ticket);

        publisher.publishEvent(new TicketOperateEvent(this, ticket, TicketActionType.CONFIRM_CLEARING));
        ticketActionLogServer.create(TicketActionType.CONFIRM_CLEARING, ticket.getId(), CurrentUser.getUserId(), null);
        // 如果是项目相关工单，回调项目，完成工序
        if (ticket.getProjectProcedureId() != null) {
            managerServerApi.changeUnitAccountStatus(ticket.getProjectProcedureId());
        }
    }

    public void isPermit(TicketActionType actionType, Ticket ticket) {
        Long userId = CurrentUser.getUserId();
        if (userId == null) throw new AuthorizationServiceException("用户未登录");
        Boolean permit = false;
        Set<Long> customerServiceIds = configItemService.permittedUsers(ConfigType.customerServiceStaff);
        TicketStatus currentStatus = null;
        if (ticket != null) {
            currentStatus = ticket.getTicketStatus();
            currentStatus.valid(actionType);
        }
        String message = "没有" + actionType.getName() + "工单权限";
        switch (actionType) {
            case CREATE:
            case EDIT:
            case DELETE:
            case ASSIGN:
                permit = customerServiceIds.contains(userId);
                break;
            case ACCEPT:
            case REJECT:
            case TRANSFER:
            case START:
                permit = userId.equals(ticket.getOperatorId());
                break;
            case CREATE_RECEIPT:
                permit = userId.equals(ticket.getOperatorId());
                permit = permit && receiptService.isPermit(actionType, ticket.getId());
                break;
            case REJECT_RECEIPT:
            case CONFIRM_RECEIPT:
                permit = customerServiceIds.contains(userId);
                permit = permit && receiptService.isPermit(actionType, ticket.getId());
                break;
            case CREATE_RETURN_VISIT:
                permit = customerServiceIds.contains(userId);
                Receipt receipt = receiptService.getTicketCurrentReceipt(ticket.getId());
                permit = permit && receipt.getReceiptStatus() == TicketReceiptStatus.CONFIRM;
                permit = permit && ticket.getReturnVisit() == null;
                break;
            case CREATE_CLEARING:
                permit = customerServiceIds.contains(userId);
                Receipt receipt1 = receiptService.getTicketCurrentReceipt(ticket.getId());
                permit = permit && receipt1.getReceiptStatus() == TicketReceiptStatus.CONFIRM;
                permit = permit && ticket.getClearing() == null;
                break;
            case FINISH:
                permit = customerServiceIds.contains(userId);
                Receipt receipt2 = receiptService.getTicketCurrentReceipt(ticket.getId());
                Clearing clearing = ticket.getClearing();
                ReturnVisit returnVisit = ticket.getReturnVisit();
                permit = permit && receipt2 != null && receipt2.getReceiptStatus() == TicketReceiptStatus.CONFIRM
                        && clearing != null && returnVisit != null;
                break;
        }
        if (!permit) {
            throw new DataInvalidException(message);
        }
    }

    public Boolean isPermitAction(TicketActionType action, Ticket ticket) {
        try {
            isPermit(action, ticket);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void batchDelete(Set<Long> ticketIds) {
        List<String> errorMsg = new ArrayList<>();
        for (Long ticketId : ticketIds) {
            try {
                delete(ticketId);
            } catch (DataInvalidException e) {
                errorMsg.add("工单：" + ticketId + "," + e.getMessage());
            }
        }
        if (!CollectionUtils.isEmpty(errorMsg)) {
            throw new DataInvalidException(String.join(";", errorMsg));
        }
    }

    public Page<Ticket> getReceiptTickets(ReceiptTicketQueryBo bo) {
        Page<Ticket> result = PageHelper
                .startPage(bo.getPage(), bo.getSize())
                .doSelectPage(() -> ticketMapper.findReceiptTickets(bo));
        return result;
    }

    public Page<Ticket> appQueryForTickets(AppTicketQueryBo bo) {
        return PageHelper.startPage(bo.getPage(), bo.getSize()).doSelectPage(() -> ticketMapper.findForApp(bo));
    }

    public void addRemark(TicketRemarkDto dto) {
        Remark remark = new Remark();
        remark.setUsername(CurrentUser.getName());
        remark.setUserId(CurrentUser.getUserId());
        remark.setInfo(dto.getInfo());
        if (dto.getAttachments().size() != 0) {
            List<File> files = commonServerApi.fileInfo(dto.getAttachments());
            List<FileIdAndName> fileIdAndNames = files.stream().map(file -> new FileIdAndName(file.getId(), file.getName())).collect(Collectors.toList());
            remark.setAttachments(fileIdAndNames.toArray(new FileIdAndName[0]));
        }

        Ticket ticket = get(dto.getTicketId());
        List<Remark> remarks;
        if (ticket.getRemarks() == null) {
            remarks = new ArrayList<>();
        } else {
            remarks = Arrays.stream(ticket.getRemarks()).collect(Collectors.toList());
        }
        remarks.add(0, remark);
        ticket.setRemarks(remarks.toArray(new Remark[remarks.size()]));
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.REMARK, ticket.getId(), CurrentUser.getUserId(), null);
    }

    public List<TicketBrief> getMulti(Set<Long> ids) {
        List<Ticket> tickets = ticketMapper.getMulti(ids);
        return tickets.stream().map(ticket -> {
            TicketBrief tb = new TicketBrief(ticket);
            tb.setReceiveInfo(ticket.getReceiveInfo());
            if (ticket.getProjectId() != null) {
                ProjectDto dto = new ProjectDto(managerServerApi.getProjectInfo(ticket.getProjectId()));
                if (ticket.getSourceContractTypeId() != null) {
                    dto.setConstruction(contractServerApi.optionName(ticket.getSourceContractTypeId()));
                }
                tb.setProjectDto(dto);
            }
            if (ticket.getSourceProcedureId() != null) {
                tb.setProcedureName(contractServerApi.optionName(ticket.getSourceProcedureId()));
            }

            if (ticket.getOperatorId() != null) {
                tb.setOperatorName(uaaServerApi.getUser(ticket.getOperatorId()).getName());
            } else {
                tb.setOperatorName("无负责人");
            }
            return tb;
        }).collect(Collectors.toList());
    }

    public Long updateTicket(Long ticketId, TicketCreateDto dto) {
        Ticket ticket1 = get(ticketId);
        if (ticket1 == null) throw new DataNotFoundException("找不到工单");
        customerServerApi.getOneCustomer(dto.getCustomerId());
        isPermit(TicketActionType.EDIT, ticket1);
        Ticket ticket = dto.convertTo();
        ticket.setId(ticket1.getId());
        fillExtraInfo(ticket, dto);
        if (ticket.getSource() == TicketTypeSource.CONTRACT_TYPE) {
            Contract contract = contractServerApi.detail(ticket.getContractId());
            if (contract.getProjectId() == null) throw new DataInvalidException("合同没有对应的项目");
            ticket.setProjectId(contract.getProjectId());
        } else if (ticket.getSource() == TicketTypeSource.STAFF_FEEDBACK) {

        }
        DetailOfTicketType detail = (DetailOfTicketType) configItemService.detail(dto.getTicketTypeId());
        ticket.setTicketType(detail.getName());
        ticket.setSource(detail.getTicketTypeSource());
        updateInternal(ticket);
        ticketActionLogServer.create(TicketActionType.EDIT, ticket.getId(), CurrentUser.getUserId(), null);
        return ticket.getId();
    }

    public Page<Ticket> findBy(TicketQueryBo bo) {
        Page<Ticket> result = PageHelper.startPage(bo.getPage(), bo.getSize()).doSelectPage(
                () -> ticketMapper.findAdvanceBy(bo));
        return result;
    }

    public List<OrganUserDto> findUserByOrgan() {
        List<Map<String, Object>> users = uaaServerApi.findAllByOrgan();
        final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper
        List<OrganUserDto> result = mapper.convertValue(users, new TypeReference<List<OrganUserDto>>() {
        });
        Map<Long, Integer> unfinish = findUnfinishCount();
        result.forEach(organUserDto -> {
            for (UserDto userDto : organUserDto.getChildren()) {
                userDto.setUnfinishTicketCount(unfinish.getOrDefault(userDto.getId(), 0));
            }
        });
        return result;
    }

    public Map<Long,Integer> findUnfinishCount() {
        List<UserReceiptCount> urc = ticketMapper.findUnfinishCount();
        return urc.stream().collect(Collectors.toMap(UserReceiptCount::getUserId, UserReceiptCount::getCnt));
    }
}
