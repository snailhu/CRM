package com.zifangdt.ch.market.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zifangdt.ch.base.api.UaaServerApi;
import com.zifangdt.ch.base.dto.market.Promotion;
import com.zifangdt.ch.base.dto.market.PromotionAffiliate;
import com.zifangdt.ch.base.dto.market.PromotionDraft;
import com.zifangdt.ch.base.dto.market.PromotionJoin;
import com.zifangdt.ch.base.dto.uaa.User;
import com.zifangdt.ch.base.enums.PromotionJoinStatusEnum;
import com.zifangdt.ch.base.enums.PromotionStatusEnum;
import com.zifangdt.ch.base.exception.DataInvalidException;
import com.zifangdt.ch.base.exception.DataNotFoundException;
import com.zifangdt.ch.base.service.BaseService;
import com.zifangdt.ch.base.dto.market.PromotionBo;
import com.zifangdt.ch.market.bo.PromotionDraftBo;
import com.zifangdt.ch.market.bo.PromotionQueryBo;
import com.zifangdt.ch.market.bo.UserOrga;
import com.zifangdt.ch.market.mapper.PromotionAffiliateMapper;
import com.zifangdt.ch.market.mapper.PromotionJoinMapper;
import com.zifangdt.ch.market.mapper.PromotionMapper;
import com.zifangdt.ch.base.dto.market.Component;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PromotionService extends BaseService<Promotion, Long> {

    @Autowired
    PromotionMapper promotionMapper;

    @Autowired
    PromotionDraftService promotionDraftService;

    @Autowired
    UaaServerApi uaaServerApi;

    @Autowired
    PromotionAffiliateService promotionAffiliateService;

    @Autowired
    PromotionAffiliateMapper promotionAffiliateMapper;

    @Autowired
    PromotionJoinService promotionJoinService;

    @Autowired
    PromotionJoinMapper promotionJoinMapper;

    static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    /**
     * 获取所有活动
     *
     * @param promotionQueryBo
     * @return
     */
    public List<PromotionBo> getAllPromotion(PromotionQueryBo promotionQueryBo) {
        List<Promotion> promotions = promotionMapper.findBy(promotionQueryBo);
        return promotions.stream().map(promotion -> {
            PromotionBo bo = new PromotionBo();
            bo.setCreateTime(promotion.getCreateTime());
            bo.setJoinNumber(promotionJoinMapper.getJoinNumberForPromotion(promotion.getId()));
            bo.setPv(promotionAffiliateMapper.getPvForPromotion(promotion.getId()));
            bo.setStatus(promotion.getStatus());
            bo.setType(promotion.getType());
            bo.setName(promotion.getName());
            bo.setId(promotion.getId());
            return bo;
        }).collect(Collectors.toList());
    }

    /**
     * 获取所有活动草稿(未发布的活动)
     *
     * @param promotionQueryBo
     * @return
     */
    public List<PromotionBo> getAllPromotionDraft(PromotionQueryBo promotionQueryBo) {
        ObjectMapper mapper = new ObjectMapper();
        List<PromotionDraft> drafts = promotionDraftService.findBy(promotionQueryBo);
        return drafts.stream()
                .map(draft -> {
                    PromotionDraftBo bo = null;
                    try {
                        bo = mapper.readValue(draft.getContent(), PromotionDraftBo.class);
                    } catch (IOException e) {
                        e.printStackTrace();

                    }
                    PromotionBo bo1 = new PromotionBo();
                    bo1.setName(bo.getName());
                    bo1.setCreateTime(draft.getCreateTime());
                    bo1.setType(bo.getType());
                    bo1.setPv(null);
                    bo1.setJoinNumber(null);
                    bo1.setStatus(PromotionStatusEnum.UnPublish);
                    bo1.setId(draft.getId());
                    return bo1;
                })
                .filter(bo -> promotionQueryBo.getStatus() == null || promotionQueryBo.getStatus() == PromotionStatusEnum.UnPublish)
                .filter(bo -> promotionQueryBo.getType() == null || bo.getType() == promotionQueryBo.getType())
                .collect(Collectors.toList());
    }

    public PromotionDraft createPromotionDraft(String content) {
        PromotionDraft draft = new PromotionDraft();
        draft.setContent(content);
        promotionDraftService.save(draft);
        return draft;
    }

    public PromotionDraft updatePromotionDraft(Long id, String content) {
        PromotionDraft draft = promotionDraftService.get(id);
        if (draft == null) throw new DataNotFoundException();
        draft.setContent(content);
        promotionDraftService.updateInternal(draft);
        return draft;
    }

    public PromotionDraftBo getPromotionDraft(Long id) throws IOException {
        PromotionDraft draft = promotionDraftService.get(id);
        if (draft == null) return null;
        ObjectMapper mapper = new ObjectMapper();
        PromotionDraftBo bo = mapper.readValue(draft.getContent(), PromotionDraftBo.class);
        return bo;
    }

    @Value("${PROMOTION_URL}")
    String promotionUrl;

    public Long publishDraft(Long draftId) throws IOException {
        PromotionDraft draft = promotionDraftService.get(draftId);
        if (draft == null) {
            throw new DataInvalidException("草稿不存在");
        }
        ObjectMapper mapper = new ObjectMapper();
        PromotionDraftBo bo = mapper.readValue(draft.getContent(), PromotionDraftBo.class);
        if (bo.getName() == null || bo.getName().isEmpty()) {
            throw new DataInvalidException("活动名不能为空");
        }
        if (bo.getType() == null) {
            throw new DataInvalidException("活动类型不能为空");
        }
        Promotion promotion = new Promotion();
        promotion.setName(bo.getName());
        promotion.setStatus(PromotionStatusEnum.OnGoing);
        promotion.setType(bo.getType());
        promotion.setUrl(promotionUrl);
        promotion.setComponents(mapper.writeValueAsString(bo.getComponents()));
        save(promotion);

        promotion.setUrl(promotionUrl + "?p=" + promotion.getId());
        updateInternal(promotion);

        if (bo.getUserOrgas() != null) {
            Set<Long> userIds = new HashSet<>();
            for (UserOrga userOrga : bo.getUserOrgas()) {
                if (userOrga.isUser) {
                    // TODO: check if user exist
                    userIds.add(userOrga.userId);
                } else {
                    List<Long> userIds1 = uaaServerApi.getWithUser(userOrga.orgaId);
                    userIds.addAll(userIds1);
                }
            }
            userIds.forEach(userId -> {
                PromotionAffiliate pa = new PromotionAffiliate();
                pa.setUserId(userId);
                pa.setPromotionId(promotion.getId());
                pa.setPromotionName(promotion.getName());
                promotionAffiliateService.save(pa);
            });
        }
        promotionDraftService.delete(draft.getId());
        return promotion.getId();
    }

    public void cancelPromotion(Long id) {
        Promotion promotion = get(id);
        promotion.setStatus(PromotionStatusEnum.Canceled);
        updateInternal(promotion);
    }

    public PromotionDraftBo copyPromotion(Long id) throws IOException {
        PromotionDraft draft = new PromotionDraft();
        Promotion promotion = get(id);
        List<PromotionAffiliate> affiliates = promotionAffiliateMapper.findByPromotionId(id);
        PromotionDraftBo bo = new PromotionDraftBo();
        bo.setName(promotion.getName());
        bo.setType(promotion.getType());
        bo.setComponents(Arrays.asList(mapper.readValue(promotion.getComponents(), Component[].class)));
        bo.setUserOrgas(affiliates.stream()
                .map(promotionAffiliate -> {
                    User user = uaaServerApi.getUser(promotionAffiliate.getUserId());
                    UserOrga userOrga = new UserOrga();
                    userOrga.isUser = true;
                    userOrga.userId = promotionAffiliate.getUserId();
                    userOrga.username = user.getName();
                    userOrga.organizationName = user.getOrganizationName();
                    return userOrga;
                })
                .collect(Collectors.toList())
        );
        draft.setContent(mapper.writeValueAsString(bo));
        promotionDraftService.save(draft);
        bo.setId(draft.getId());
        return bo;
    }

    public PromotionBo getPromotionDetail(Long id) throws IOException {
        Promotion promotion = get(id);
        if (promotion == null) throw new DataNotFoundException();
        List<PromotionAffiliate> affiliates = promotionAffiliateMapper.findByPromotionId(id);
        affiliates.stream().forEach(affiliate -> affiliate.updateUrl(promotionUrl));
        PromotionBo bo = new PromotionBo();
        BeanUtils.copyProperties(promotion, bo);
        bo.setAffiliates(affiliates);
        bo.setComponents(Arrays.asList(mapper.readValue(promotion.getComponents(), Component[].class)));
        List<PromotionJoin> joins = promotionJoinMapper.findByPromotion(id);
        bo.setJoins(joins);
        return bo;
    }

    public Long addUserOrga(Long promotionId, UserOrga userOrga) {
        Promotion promotion = get(promotionId);
        List<PromotionAffiliate> affiliates = promotionAffiliateMapper.findByPromotionId(promotionId);
        Set<Long> userIds = new HashSet<>();
        if (userOrga.isUser) {
            userIds.add(userOrga.userId);
        } else {
            List<Long> userIds1 = uaaServerApi.getWithUser(userOrga.orgaId);
            userIds.addAll(userIds1);
        }
        userIds.removeAll(affiliates.stream().map(PromotionAffiliate::getUserId).collect(Collectors.toList()));
        userIds.forEach(userId -> {
            PromotionAffiliate pa = new PromotionAffiliate();
            pa.setUserId(userId);
            pa.setPromotionId(promotion.getId());
            promotionAffiliateService.save(pa);
        });
        return promotionId;
    }

    /**
     * 获取活动页面样式，同时代表活动页面浏览次数+1
     *
     * @param promotionId
     * @param userId
     * @return
     * @throws IOException
     */
    public PromotionDraftBo viewPromotion(Long promotionId, Long userId) throws IOException {
        Promotion promotion = get(promotionId);
        if (promotion != null && PromotionStatusEnum.OnGoing != promotion.getStatus())
            throw new DataInvalidException("活动结束");
        PromotionAffiliate affiliate = promotionAffiliateMapper.findByUserAndPromotion(userId, promotionId);
        if (affiliate == null) throw new DataInvalidException("此人没有参加活动推广");
        affiliate.setVisitCnt(affiliate.getVisitCnt() + 1);
        promotionAffiliateService.updateInternal(affiliate);
        List<Component> components = Arrays.asList(mapper.readValue(promotion.getComponents(), Component[].class));
        PromotionDraftBo bo = new PromotionDraftBo();
        bo.setName(promotion.getName());
        bo.setComponents(components);
        return bo;
    }

    /**
     * 报名参加活动
     *
     * @param promotionId
     * @param userId
     * @param info
     * @return
     * @throws IOException
     */
    public Long joinPromotion(Long promotionId, Long userId, String info) throws IOException {
        Promotion promotion = get(promotionId);
        if (promotion != null && PromotionStatusEnum.OnGoing != promotion.getStatus())
            throw new DataInvalidException("活动结束");
        Boolean exist = promotionJoinMapper.isJoinExist(promotionId, info);
        if (exist)
            throw new DataInvalidException("您已经报名，请勿重复报名");
        PromotionAffiliate affiliate = promotionAffiliateMapper.findByUserAndPromotion(userId, promotionId);
        if (affiliate == null) throw new DataInvalidException("此人没有参加活动推广");

        affiliate.setJoinCnt(affiliate.getJoinCnt() + 1);
        promotionAffiliateService.updateInternal(affiliate);

        List<Component> components = Arrays.asList(mapper.readValue(promotion.getComponents(), Component[].class));
        PromotionJoin promotionJoin = new PromotionJoin();
        promotionJoin.setPromotionId(promotionId);
        promotionJoin.setOwnerId(userId);
        promotionJoin.setStatus(PromotionJoinStatusEnum.Unhandled);
        promotionJoin.setSubmitInfo(info);
        promotionJoin.setPromotionName(promotion.getName());
        promotionJoin.setPromotionAffiliateId(affiliate.getId());
        promotionJoinService.save(promotionJoin);
        return promotionJoin.getId();
    }

    public void invalidPromotionJoin(long promotionJoinId) {
        PromotionJoin promotionJoin = promotionJoinService.get(promotionJoinId);
        promotionJoin.setStatus(PromotionJoinStatusEnum.Invalid);
        promotionJoinService.updateInternal(promotionJoin);
    }
}
