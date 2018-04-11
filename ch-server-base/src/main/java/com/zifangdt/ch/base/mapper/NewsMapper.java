package com.zifangdt.ch.base.mapper;

import com.zifangdt.ch.base.bo.NewsPageQueryBo;
import com.zifangdt.ch.base.bo.setting.NewsPageQueryProjectBo;
import com.zifangdt.ch.base.dao.BaseMapper;
import com.zifangdt.ch.base.dto.News;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/23.
 */
@Repository
public interface NewsMapper extends BaseMapper<News> {

    List<News> findListByRelatedId(NewsPageQueryBo bo);

    //    List<News> findListByRelatedIdProject(@Param("relatedId") Long relatedId,@Param("unitIds") List<Long> unitIds);
    List<News> findListByRelatedIdProject(NewsPageQueryProjectBo bo);

    List<News> tags(Long relatedId);

    List<News> tagsProject(@Param("relatedId") Long relatedId, @Param("unitIds") List<Long> unitIds);

    List<News> findMine(@Param("userId") Long userId, @Param("date") String date);
}
