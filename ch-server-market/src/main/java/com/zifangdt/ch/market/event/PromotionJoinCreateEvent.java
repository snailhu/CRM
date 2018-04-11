package com.zifangdt.ch.market.event;

import com.zifangdt.ch.base.dto.market.PromotionJoin;
import com.zifangdt.ch.base.event.EntityCreateEvent;

public class PromotionJoinCreateEvent extends EntityCreateEvent<PromotionJoin>{
    public PromotionJoinCreateEvent(Object source, PromotionJoin entity) {
        super(source, entity);
    }
}
