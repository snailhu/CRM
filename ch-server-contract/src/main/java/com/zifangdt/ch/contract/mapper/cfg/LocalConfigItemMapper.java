package com.zifangdt.ch.contract.mapper.cfg;

import com.zifangdt.ch.base.dto.setting.ConfigItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/18.
 */
@Repository
public interface LocalConfigItemMapper {
    void updateVersionByProcedureId(@Param("m") long m, @Param("id") Long id);

    List<ConfigItem> selectContractTypesByProcedureId(Long id);
}
