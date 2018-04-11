package com.zifangdt.ch.market.event;

import com.zifangdt.ch.base.api.CommonServerApi;
import com.zifangdt.ch.base.constant.NoticeTags;
import com.zifangdt.ch.base.dto.common.Notice;
import com.zifangdt.ch.base.dto.market.Promotion;
import com.zifangdt.ch.base.dto.market.PromotionAffiliate;
import com.zifangdt.ch.base.dto.market.PromotionJoin;
import com.zifangdt.ch.base.enums.NoticeType;
import com.zifangdt.ch.base.enums.RedirectType;
import com.zifangdt.ch.base.util.CurrentUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class Listener {

    @Autowired
    CommonServerApi commonServerApi;

    @EventListener
    public void onPromotionAffiliateCreate(PromotionAffiliateCreateEvent event) {
        PromotionAffiliate promotionAffiliate = event.getEntity();
        commonServerApi.saveNotice(
                Notice.newBuilder()
                        .name(promotionAffiliate.getPromotionName())
                        .type(NoticeType.ACTIVITY_PUBLISHED, CurrentUser.getUsername(), promotionAffiliate.getPromotionName())
                        .tag(NoticeTags.MARKET_ACTIVITY)
                        .redirect(RedirectType.PromotionAffiliate, promotionAffiliate.getId())
                        .receivers(promotionAffiliate.getUserId())
                        .build()
        );
    }

    @EventListener
    public void onPromotionJoinCreateEvent(PromotionJoinCreateEvent promotionJoinCreateEvent) {
        PromotionJoin promotionJoin = promotionJoinCreateEvent.getEntity();
        commonServerApi.saveNotice(
                Notice.newBuilder()
                        .name(promotionJoin.getPromotionName())
                        .type(NoticeType.ACTIVITY_REFRESHED, promotionJoin.getPromotionName(), "1")
                        .tag(NoticeTags.MARKET_ACTIVITY)
                        .redirect(RedirectType.PromotionJoin, promotionJoin.getPromotionAffiliateId())
                        .receivers(promotionJoin.getOwnerId())
                        .build()
        );
    }
}
