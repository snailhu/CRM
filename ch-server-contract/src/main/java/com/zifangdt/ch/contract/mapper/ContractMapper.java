package com.zifangdt.ch.contract.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.contract.Contract;
import com.zifangdt.ch.contract.bo.ApprovedContractQueryBo;
import com.zifangdt.ch.contract.bo.ByCustomerIdQueryBo;
import com.zifangdt.ch.contract.bo.QueryBo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/19.
 */
@Repository
public interface ContractMapper extends BaseMapper<Contract> {

    Contract getDetail(Long id);

    List<Contract> getDetails(@Param("ids") List<Long> ids);

    List<Contract> findList(QueryBo queryBo);

    void setInvalidReasonBatch(@Param("ids") List<Long> ids, @Param("reason") String reason);

    boolean numberExists(String number);

    List<Contract> findApproved(ApprovedContractQueryBo queryBo);

    Contract getDetailByProjectId(Long projectId);

    boolean existsCustomer(Long customerId);

    List<Contract> findByCustomerId(ByCustomerIdQueryBo queryBo);

    List<Long> findSignedCustomerIdsInDays(int days);

    long countByCustomerId(Long customerId);

    List<Long> findRepurchasedCustomerIds();
}
