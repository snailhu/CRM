package com.zifangdt.ch.base.api;

import com.zifangdt.ch.base.bo.ProcessOverview;
import com.zifangdt.ch.base.dto.approval.ApprovalOperateBo;
import com.zifangdt.ch.base.dto.approval.ProcessInstance;
import com.zifangdt.ch.base.dto.approval.RunningTrack;
import com.zifangdt.ch.base.enums.pair.ProcessType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/20.
 */
@FeignClient("approval-server")
public interface ApprovalServerApi {
    /**
     * 将流程的状态从“待审批”设为“审批中”
     *
     * @param trackId
     */
    @PutMapping("/processes/status/approving?for=internal")
    void setStatusToApproving(@RequestParam("trackId") Long trackId);

    /**
     * 将流程的状态设为“已取消”
     *
     * @param processId
     */
    @PutMapping("/processes/status/canceled?for=internal")
    void setStatusToCanceled(@RequestParam("processId") Long processId);

    /**
     * 发起流程
     *
     * @param name   流程名称
     * @param type   流程类型
     * @param object 关联对象id
     * @return
     */
    @PostMapping("/processes")
    Long launchProcess(@RequestParam("name") String name,
                       @RequestParam("type") ProcessType type,
                       @RequestParam("object") Long object);

    /**
     * 获取流程详情
     *
     * @param processId
     * @return
     */
    @GetMapping("/processes/detail")
    ProcessInstance getProcess(@RequestParam("processId") Long processId);

    /**
     * 批量将流程的状态设为“已取消”
     *
     * @param processIds
     */
    @PutMapping("/processes/status/canceledBatch?for=internal")
    void setStatusToCanceledBatch(@RequestParam("processIds") List<Long> processIds);

    /**
     * 获取流程日志
     *
     * @param id
     * @return
     */
    @GetMapping("/processes/{id}/tracks")
    List<RunningTrack> tracks(@PathVariable("id") Long id);

    /**
     * 获取流程当前概况，包括当前步骤、上一步、下一步、当前第几步、是否是从上一步退回过来的、流程实例、当前用户是否需要进行审批
     *
     * @param id
     * @return
     */
    @GetMapping("/processes/{id}/overview")
    ProcessOverview overview(@PathVariable("id") Long id);

    /**
     * 当前步骤审批通过
     *
     * @param operateBo
     */
    @PutMapping("/processes/resolve")
    void resolve(@RequestBody ApprovalOperateBo operateBo);

    @GetMapping("/processes/{id}")
    ProcessInstance getProcessDetail(@PathVariable("id") Long processId);

    @GetMapping("/processes/byObjects?for=internal")
    List<ProcessInstance> findByObjects(@RequestParam("type") ProcessType processType, @RequestParam("objects") Set<Long> objects);
}
