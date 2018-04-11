package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.BooleanBo;
import com.zifangdt.ch.base.constant.Constants;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.dto.uaa.Organization;
import com.zifangdt.ch.base.dto.uaa.Permission;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.Module;
import com.zifangdt.ch.base.exception.DataInvalidException;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@FeignClient(Constants.SERVICE_UAA)
public interface UaaServerApi {
    @GetMapping("/users/internal")
    Map<Long, User> internalFindByIds(@RequestParam("ids") Set<Long> ids);

    //    @GetMapping(value = "/users/{id}?for=internal")
    @RequestMapping(value = "/users/{id}?for=internal", method = RequestMethod.GET)
    User getUser(@PathVariable("id") Long id);

    @GetMapping("/users/{idOrUsername}")
    User getDetail(@PathVariable("idOrUsername") String idOrUsername, @RequestParam(value = "by", required = false) String by);

    @GetMapping("/organs/{id}")
    Organization getOrganization(@PathVariable("id") Long id);

    @GetMapping("/organs/{id}/userIds")
    List<Long> getWithUser(@PathVariable("id") Long id);

    @GetMapping("/users/getListForInternal?for=internal")
    List<User> getListForInternal(@RequestParam("ids") List<Long> ids);

    //    @GetMapping("/users/internal/{id}/allInferiors")
    @RequestMapping(value = "/users/internal/{id}/allInferiors", method = RequestMethod.GET)
    List<User> allInferiors(@PathVariable("id") Long id);

    @GetMapping("/users")
    List<User> findAll();

    @GetMapping("/users/withDeleted")
    List<User> findAllWithDeleted();

    @GetMapping("/users/byOrganIds?for=internal")
    List<Long> byOrganIds(@RequestParam("organIds") List<Long> organIds);

    @GetMapping("/users/internal/findBy")
    List<User> internalFindBy(@RequestParam("name") String name);

    @GetMapping("/users/{userId}/perms")
    List<Permission> findPermissionsByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/perms?for=internal")
    List<Permission> findPermissions();

    @GetMapping("/organs/all")
    List<Organization> findAllOrgas();

    @GetMapping("/users/internal/{id}/superiors")
    List<User> superiors(@PathVariable("id") Long id);

    @GetMapping("/users/internal/{id}/inferiors")
    List<User> inferiors(@PathVariable("id") Long id, @RequestParam(value = "organId", required = false) Long organId);

    @GetMapping("/users/internal/{a}/isSuperiorOf/{b}")
    BooleanBo isSuperiorOf(@PathVariable("a") Long a, @PathVariable("b") Long b);

    @GetMapping("/users/byOrgan")
    List<Map<String, Object>> findAllByOrgan();

    /**
     * fail fast interface
     *
     * @param organizationId
     * @return
     */
    default Organization getOneOrganization(Long organizationId) {
        Organization organization = getOrganization(organizationId);
        if (organization == null) throw new DataInvalidException("id=" + organizationId + "的部门不存在");
        return organization;
    }

    @GetMapping("/users/{userId}/news")
    List<News> newsByDate(@PathVariable("userId") Long userId, @RequestParam("date") String date);

    @GetMapping("/users/updateApproverOf?for=internal")
    void updateApproverOf(@RequestParam("inserts") Set<Long> inserts,
                          @RequestParam("deletes") Set<Long> deletes,
                          @RequestParam("module") Module module);
}
