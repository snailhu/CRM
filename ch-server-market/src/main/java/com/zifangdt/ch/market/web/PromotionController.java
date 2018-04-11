package com.zifangdt.ch.market.web;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.dto.market.PromotionAffiliate;
import com.zifangdt.ch.base.dto.market.PromotionBo;
import com.zifangdt.ch.base.dto.market.PromotionJoin;
import com.zifangdt.ch.base.dto.todo.TodoTask;
import com.zifangdt.ch.base.util.CurrentUser;
import com.zifangdt.ch.market.bo.*;
import com.zifangdt.ch.market.service.PromotionAffiliateService;
import com.zifangdt.ch.market.service.PromotionDraftService;
import com.zifangdt.ch.market.service.PromotionJoinService;
import com.zifangdt.ch.market.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/promotion")
public class PromotionController {

    @Autowired
    PromotionService promotionService;

    @Autowired
    PromotionDraftService promotionDraftService;

    @Autowired
    PromotionJoinService promotionJoinService;

    @Autowired
    PromotionAffiliateService promotionAffiliateService;

    static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    @PostMapping("/draft")
    Long createPromotionDraft(@RequestBody @Valid PromotionDraftBo draftCreateBo) throws JsonProcessingException {
        return promotionService.createPromotionDraft(mapper.writeValueAsString(draftCreateBo)).getId();
    }

    @PutMapping("/draft/{id}")
    Long updatePromotionDraft(@PathVariable("id") Long id, @RequestBody @Valid PromotionDraftBo draftCreateBo) throws JsonProcessingException {
        return promotionService.updatePromotionDraft(id, mapper.writeValueAsString(draftCreateBo)).getId();
    }

    @GetMapping("/draft/{id}")
    PromotionDraftBo getDraft(@PathVariable("id") Long id) throws Exception{
        return promotionService.getPromotionDraft(id);
    }

    @PutMapping("/draft/{id}/publish")
    Long publishPromotion(@PathVariable("id") Long id) throws IOException{
        return promotionService.publishDraft(id);
    }

    @PutMapping("/{id}/cancel")
    Long cancelPromotion(@PathVariable("id") Long id){
        promotionService.cancelPromotion(id);
        return id;
    }

    @GetMapping("/draft")
    List<PromotionDraftBo> getPromotions(){
        return null;
    }

    @GetMapping("")
    List<PromotionBo> getPromotion(PromotionQueryBo bo){
        return Stream.concat(
                promotionService.getAllPromotionDraft(bo).stream(),
                promotionService.getAllPromotion(bo).stream())
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    PromotionBo getDetail(@PathVariable("id") Long id) throws IOException {
        return promotionService.getPromotionDetail(id);
    }

    @PostMapping("/{id}/copy")
    PromotionDraftBo copyToDraft(@PathVariable("id") Long id) throws IOException {
        return promotionService.copyPromotion(id);
    }

    @PostMapping("/{id}/addUserOrga")
    Long addUserOrga(@PathVariable("id") Long promotionId, @RequestBody UserOrga userOrga){
        return promotionService.addUserOrga(promotionId, userOrga);
    }

    /**
     * 访问页面
     * @param promotionId
     * @param userId
     * @return
     * @throws IOException
     */
    @GetMapping("/{promotionId}/{userId}")
    PromotionDraftBo viewPromotion(@PathVariable("promotionId") Long promotionId,
                                  @PathVariable("userId") Long userId) throws IOException {
        return promotionService.viewPromotion(promotionId, userId);
    }

    /**
     * 报名
     * @param promotionId
     * @param userId
     * @param info
     * @return
     * @throws IOException
     */
    @PostMapping("/{promotionId}/{userId}/join")
    Long joinPromotion(@PathVariable("promotionId") Long promotionId,
                       @PathVariable("userId") Long userId,
                       @RequestBody String info) throws IOException {
        return promotionService.joinPromotion(promotionId, userId, info);
    }

    /**
     * 报名信息设置为无效
     * @param promotionJoinId
     * @return
     */
    @PutMapping("/join/{id}/invalid")
    Long invalidPromotionJoin(@PathVariable("id") Long promotionJoinId){
        promotionService.invalidPromotionJoin(promotionJoinId);
        return promotionJoinId;
    }

    /**
     * 报名信息加为客户
     * @param id
     * @return
     */
    @PutMapping("/join/{id}/addCustomer")
    Long addJoinToCustomer(@PathVariable("id") Long id, @RequestBody @Valid Customer customer,
                           @RequestParam(value = "isPool", defaultValue = "false") Boolean isPool) throws Exception{
        return promotionJoinService.addToCustomer(id, customer, isPool).getId();
    }

    @PutMapping("/join/{id}/addTodo")
    Long addJoinTodo(@PathVariable("id") Long id, @RequestBody @Valid TodoTask todo){
        return promotionJoinService.addToTodo(id, todo).getId();
    }

    /**
     * 获取所有报名
     * @return
     */
    @GetMapping("/join")
    PageDto<PromotionJoin> getAllJoin(PromotionJoinQueryBo queryBo){
        Page<PromotionJoin> page = promotionJoinService.findByQuery(queryBo);
        return PageDto.fromPage(page);
    }

    /**
     * 报名详情
     * @param promotionJoinId
     * @return
     */
    @GetMapping("/join/{id}")
    PromotionJoin getJoinDetail(@PathVariable("id") Long promotionJoinId){
        return promotionJoinService.getOne(promotionJoinId);
    }

    @GetMapping("/my/affiliate")
    List<PromotionAffiliate> getMyAffiliates(){
        Long userId = CurrentUser.getUserId();
        return promotionAffiliateService.getUserAffiliates(userId);
    }

    @GetMapping("/my/affiliate/{id}")
    PromotionAffiliate getMyAffiliateDetail(@PathVariable("id") Long affiliateId){
        return promotionAffiliateService.getAffiliateDetail(affiliateId);
    }

    @GetMapping("/my/join")
    PageDto<PromotionJoin> getMyJoin(PromotionJoinQueryBo bo){
        Long userId = CurrentUser.getUserId();
        bo.setOwnerId(userId);
        return PageDto.fromPage(promotionJoinService.findByQuery(bo));
    }

    @PostMapping("/aaa")
    QueryBo getAaa(@RequestBody QueryBo bo)  {
        return bo;
    }
}


