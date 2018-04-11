package com.zifangdt.ch.finance.mapper;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/12.
 */
@Repository
public interface LocalConfigItemMapper extends BaseMapper<ConfigItem> {

    boolean journalTypeUsed(Long id);

    boolean paymentWayUsed(Long id);

    List<ConfigItem> getAllWithWhetherUsed();
}
