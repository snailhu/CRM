package com.zifangdt.ch.market.event;

import com.zifangdt.ch.base.dto.market.PromotionAffiliate;
import com.zifangdt.ch.base.event.EntityCreateEvent;

public class PromotionAffiliateCreateEvent extends EntityCreateEvent<PromotionAffiliate>{
    public PromotionAffiliateCreateEvent(Object source, PromotionAffiliate entity) {
        super(source, entity);
    }
}
