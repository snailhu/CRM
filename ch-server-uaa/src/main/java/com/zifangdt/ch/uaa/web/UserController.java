package com.zifangdt.ch.uaa.web;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.zifangdt.ch.base.api.*;
import com.zifangdt.ch.base.bo.*;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.common.File;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.Module;
import com.zifangdt.ch.base.service.CustomQuery;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.base.util.FileUtil;
import com.zifangdt.ch.base.web.RestResult;
import com.zifangdt.ch.uaa.bo.*;
import com.zifangdt.ch.uaa.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private Validator validator;
    @Autowired
    private CommonServerApi commonServerApi;
    private static final String TEMPLATE_IMPORT_FILENAME = "用户导入模板.xlsx";
    private static final String TEMPLATE_FAILURE_FILENAME = "用户导入失败记录.xls";
    @Autowired
    private CustomerServerApi customerServerApi;
    @Autowired
    private ManagerServerApi managerServerApi;
    @Autowired
    private ContractServerApi contractServerApi;
    @Autowired
    private FastFileStorageClient client;
    @Autowired
    private QuoteServerApi quoteServerApi;
    @Autowired
    private ProductServerApi productServerApi;

    @GetMapping("/{idOrUsername}")
    public User getDetail(@PathVariable("idOrUsername") String idOrUsername, @RequestParam(value = "by", required = false) String by) {
        if ("username".equals(by)) {
            return userService.findByUsername(idOrUsername);
        } else {
            return userService.getDetail(Long.valueOf(idOrUsername));
        }
    }

    @GetMapping("/byPhone/{phone}")
    public User getByPhone(@PathVariable("phone") String phone) {
        return userService.findByPhone(phone);
    }

    @GetMapping("/{id}/perms")
    public List<Permission> findPermissionsByUserId(@PathVariable("id") Long id, @RequestParam(value = "category", required = false) String category) {
        return userService.findPermissionsByUserId(id, category);
    }

    @GetMapping("/self/perms")
    public List<Permission> findPermissionsByUserId(@RequestParam(value = "category", required = false) String category) {
        return userService.findPermissionsByUserId(CurrentUser.getUserId(), category);
    }

    @PutMapping("/{id}/boss")
    public void setBoss(@PathVariable("id") Long id) {
        userService.setBoss(id, Constants.YES);
    }

    @DeleteMapping("/{id}/boss")
    public void unsetBoss(@PathVariable("id") Long id) {
        userService.setBoss(id, Constants.NO);
    }

    @DeleteMapping("/{id}/status")
    public void setLeaveJob(@PathVariable("id") Long id) {
        userService.setLeaveJob(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        userService.delete(id);
    }

    @PostMapping
    public Long save(@Valid @RequestBody User user) {
        userService.save(user);
        return user.getId();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateBo userUpdateBo) {
        userService.update(id, userUpdateBo);
    }

    @PutMapping("/{id}/organ/{organId}")
    public void transferOrganization(@PathVariable("id") Long id, @PathVariable("organId") Long organId) {
        userService.transferOrganization(id, organId);
    }

    @PutMapping("/{id}/password/{newPassword}")
    public void modifyPassword(@PathVariable("id") Long id, @PathVariable("newPassword") String newPassword) {
        userService.modifyPassword(id, newPassword);
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/withDeleted")
    public List<User> findAllWithDeleted() {
        return userService.findAllWithDeleted();
    }

    @PutMapping("/organ/{organId}")
    public void transferOrganizationBatch(@RequestBody BatchUpdateBo batchUpdateBo, @PathVariable("organId") Long organId) {
        userService.transferOrganizationBatch(batchUpdateBo.getIds(), organId);
    }

    @PutMapping("/boss")
    public void setBoss(@RequestBody BatchUpdateBo batchUpdateBo) {
        userService.setBossBatch(batchUpdateBo.getIds(), Constants.YES);
    }

    @DeleteMapping("/boss")
    public void unsetBoss(@RequestBody BatchUpdateBo batchUpdateBo) {
        userService.setBossBatch(batchUpdateBo.getIds(), Constants.NO);
    }

    @PutMapping("/{id}/head")
    public void setHead(@PathVariable("id") Long id) {
        userService.updateHead(id);
    }

    @DeleteMapping("/{id}/head")
    public void unsetHead(@PathVariable("id") Long id) {
        userService.deleteHead(id);
    }

    @PutMapping("/head")
    public void setHead(@RequestBody BatchUpdateBo batchUpdateBo) {
        userService.updateHeadBatch(batchUpdateBo.getIds());
    }

    @DeleteMapping("/head")
    public void unsetHead(@RequestBody BatchUpdateBo batchUpdateBo) {
        userService.deleteHeadBatch(batchUpdateBo.getIds());
    }

    @DeleteMapping("/status")
    public void setLeaveJob(@RequestBody BatchUpdateBo batchUpdateBo) {
        userService.setLeaveJobBatch(batchUpdateBo.getIds());
    }

    @GetMapping("/internal")
    public Map<Long, User> internalFindByIds(@RequestParam("ids") Set<Long> ids) {
        Map<Long, User> result = userService.internalFindByIds(ids);
        ids.removeAll(result.keySet());
        if (ids.size() != 0) {
            for (Long id : ids) {
                result.put(id, User.DELETED_NONEXIST_USER);
            }
        }
        return result;
    }

    @GetMapping("/internal/{id}/superiors")
    public List<User> superiors(@PathVariable("id") Long id) {
        return userService.getSuperiors(id);
    }

    @GetMapping("/internal/{id}/inferiors")
    public List<User> inferiors(@PathVariable("id") Long id) {
        return userService.getInferiors(id, null);
    }

    @GetMapping("/self/inChargeOrgans")
    public List<Organization> getInChargeOrgans() {
        return userService.getInChargeOrgans(CurrentUser.getUserId());
    }

    @GetMapping("/self/inChargeOrgans/{organId}/inferiors")
    public List<User> getInferiorsOfOrgan(@PathVariable("organId") Long organId) {
        return userService.getInferiors(CurrentUser.getUserId(), organId);
    }

    @GetMapping("/{id}/inChargeOrgans")
    public List<Organization> getInChargeOrgans(@PathVariable("id") Long id) {
        return userService.getInChargeOrgans(id);
    }

    @GetMapping("/{userId}/inChargeOrgans/{organId}/inferiors")
    public List<User> getInferiorsOfOrgan(@PathVariable("userId") Long userId, @PathVariable("organId") Long organId) {
        return userService.getInferiors(userId, organId);
    }

    @GetMapping("/internal/{a}/isSuperiorOf/{b}")
    public BooleanBo isSuperiorOf(@PathVariable("a") Long a, @PathVariable("b") Long b) {
        return new BooleanBo(userService.isSuperiorOf(a, b));
    }

    @GetMapping("/internal/{id}/allInferiors")
    public List<User> allInferiors(@PathVariable("id") Long id) {
        return userService.getAllInferiors(id);
    }

    @GetMapping("/import")
    public ResponseEntity<byte[]> getTemplate() throws IOException {
        return FileUtil.download("template/" + TEMPLATE_IMPORT_FILENAME, TEMPLATE_IMPORT_FILENAME);
    }

    @PostMapping("/import")
    public ResponseEntity<RestResult> importUsers(@RequestParam("file") MultipartFile file) throws Exception {
        ImportParams params = new ImportParams();
        List<UserExcelBo> users = ExcelImportUtil.importExcel(file.getInputStream(), UserExcelBo.class, params);
        Map<Boolean, List<UserExcelBo>> filtered = users.stream().collect(Collectors.partitioningBy(u -> {
            Set<ConstraintViolation<UserExcelBo>> set = validator.validate(u);
            if (!CollectionUtils.isEmpty(set)) {
                u.setReason(set.stream().map(ConstraintViolation::getMessage).collect(Collectors.joining(";")));
                return false;
            }
            if (!u.getLevelName().equals("boss") && StringUtils.isEmpty(u.getOrganizationName())) {
                u.setReason("职级不为boss时必须设置部门");
                return false;
            }
            return true;
        }));

        List<UserExcelBo> failures = filtered.get(Boolean.FALSE);
        List<UserExcelBo> successes = filtered.get(Boolean.TRUE);
        if (!successes.isEmpty()) {
            List<UserExcelBo> result = new ArrayList<>();
            successes.stream().forEach(u -> {
                User user = new User();
                BeanUtils.copyProperties(u, user);
                user.setPassword("123456");
                try {
                    userService.save(user);
                } catch (Exception e) {
                    u.setReason(e.getMessage());
                    result.add(u);
                }
            });
            failures.addAll(result);
        }

        ImportStatistics importStatistics = new ImportStatistics(users.size() - failures.size(), failures.size());
        if (!failures.isEmpty()) {
            Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, null), UserExcelBo.class, failures);

            Path tempPath = Files.createTempFile(Constants.IMPORT_TEMP_FILE_PREFIX, ".xls");
            java.io.File tempFile = tempPath.toFile();
            try (FileOutputStream out = new FileOutputStream(tempFile)) {
                workbook.write(out);
            }
            File toBeSavedFile = FileUtil.uploadLocalFile(client, tempFile.getAbsolutePath(), TEMPLATE_FAILURE_FILENAME);
            String id = commonServerApi.saveFile(toBeSavedFile);

            importStatistics.setFailureExcel(id);
            Files.delete(tempPath);
        }
        return new ResponseEntity<>(RestResult.success(importStatistics), HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteBatch(@Valid @RequestBody BatchUpdateBo batchUpdateBo) {
        userService.deleteBatch(batchUpdateBo.getIds());
    }

    @PutMapping("/permissions")
    public void updatePermissionsBatch(@Valid @RequestBody BatchUpdatePermissionsBo bo) {
        userService.updatePermissionsBatch(bo.getIds(), bo.getPermissions());
    }

    @GetMapping(params = "for=web")
    public PageResultBo listForWeb(@Valid UserWebQueryBo userWebQueryBo) {
        return userService.findListForWeb(userWebQueryBo);
    }

    @GetMapping(value = "/{id}", params = "for=internal")
    public User getUser(@PathVariable("id") Long id) {
        return userService.get(id);
    }

    @GetMapping("/self")
    public User self() {
        return userService.get(CurrentUser.getUserId());
    }

    @PutMapping("/self")
    public void updateSelf(@Valid @RequestBody UserAppUpdateBo updateBo) {
        userService.update(CurrentUser.getUserId(), updateBo);
    }

    @PutMapping("/self/password")
    public void updatePassword(@Valid @RequestBody PwdAppUpdateBo updateBo) {
        userService.modifyPasswordForApp(updateBo);
    }

    @GetMapping("/export")
    public void export(@Valid UserWebQueryBo userWebQueryBo, HttpServletResponse response) throws IOException {
        List<UserExcelExportBo> list = userService.findListForWebToExport(userWebQueryBo);
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(null, null), UserExcelExportBo.class, list);
        FileUtil.download(response, workbook, "用户列表.xls");
    }

    @GetMapping("/self/news")
    public List<News> news(@RequestParam(value = "date", required = false) String date) {
        List<News> customerNews = customerServerApi.mineNews(date);
        List<News> contractNews = contractServerApi.mineNews(date);
        List<News> projectNews = managerServerApi.mineNews(date);

        List<News> news = new ArrayList<>();
        news.addAll(customerNews);
        news.addAll(contractNews);
        news.addAll(projectNews);

        news.sort(Comparator.comparing(News::getHappenedAt).reversed());
        return news;
    }

    @GetMapping("/byOrgan")
    public List<Map<String, Object>> findAllByOrgan() {
        return userService.findAllByOrgan();
    }

    @GetMapping(value = "/byOrganIds", params = "for=internal")
    public List<Long> byOrganIds(@RequestParam("organIds") List<Long> organIds) {
        return userService.findIdsByOrganIds(organIds);
    }

    @GetMapping("/internal/findBy")
    public List<User> internalFindBy(@RequestParam("name") String name) {
        CustomQuery query = CustomQuery.builder().andCondition("name like \"%" + name + "%\"").build();
        return userService.findAll(query);
    }

    @GetMapping(value = "/getListForInternal", params = "for=internal")
    public List<User> getListForInternal(@RequestParam("ids") List<Long> ids) {
        return userService.getListForInternal(ids);
    }

    @GetMapping("/{userId}/news")
    public List<News> news(@PathVariable("userId") Long userId, @RequestParam(value = "date", required = false) String date) {
        List<News> news = new ArrayList<>();
        news.addAll(customerServerApi.newsByDate(userId, date));
        news.addAll(contractServerApi.newsByDate(userId, date));
        news.addAll(managerServerApi.newsByDate(userId, date));
        news.addAll(quoteServerApi.newsByDate(userId, date));
        news.addAll(productServerApi.newsByDate(userId, date));

        news.sort(Comparator.comparing(News::getHappenedAt).reversed());
        return news;
    }

    @GetMapping(value = "/updateApproverOf", params = "for=internal")
    public void updateApproverOf(@RequestParam(value = "inserts", required = false) Set<Long> inserts,
                                 @RequestParam(value = "deletes", required = false) Set<Long> deletes,
                                 @RequestParam("module") Module module) {
        userService.updateApproverOf(inserts, deletes, module);
    }
}
