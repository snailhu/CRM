package com.zifangdt.ch.base.mapper.cfg;

import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.setting.ConfigItem;
import com.zifangdt.ch.base.enums.ConfigType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * Created by 袁兵 on 2018/1/8.
 */
@Repository
public interface ConfigItemMapper extends BaseMapper<ConfigItem> {

    boolean nameExists(@Param("configType") ConfigType configType, @Param("name") String name);

    boolean nameExistsAndEnabled(@Param("configType") ConfigType configType, @Param("name") String name);

    boolean typeExists(ConfigType configType);

    ConfigItem getOneByType(ConfigType configType);

    List<ConfigItem> getListByType(ConfigType configType);

    List<ConfigItem> getListByTypes(@Param("configTypes") Set<ConfigType> configTypes);

    List<ConfigItem> getListByIds(@Param("ids") Set<Long> ids);

    ConfigItem getStepByUuid(String uuid);

    void updateApprovers(@Param("id") Long id, @Param("ids") List<Long> ids);

    ConfigItem getOneById(Long id);

    void disable(Long id);

    void bulkInsert(List<ConfigItem> list);
}
