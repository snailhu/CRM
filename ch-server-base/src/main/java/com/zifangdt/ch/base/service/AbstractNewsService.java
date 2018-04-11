package com.zifangdt.ch.base.service;

import com.zifangdt.ch.base.bo.NewsPageQueryBo;
import com.zifangdt.ch.base.bo.PageResultBo;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.mapper.NewsMapper;
import com.zifangdt.ch.base.util.CurrentUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by 袁兵 on 2018/1/23.
 */
@Service
@Transactional
public abstract class AbstractNewsService {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private NewsMapper newsMapper;

    public PageResultBo<News> findListForWeb(NewsPageQueryBo bo) {
        PageResultBo<News> pageResultBo = PageResultBo.of(bo, newsMapper::findListByRelatedId);
        List<News> tags = newsMapper.tags(bo.getRelatedId());
        Map<String, Long> count = tags.stream().collect(Collectors.groupingBy(News::getTag, Collectors.counting()));
        pageResultBo.setExtraInfo(count);
        return pageResultBo;
    }

    public void save(News news) {
        newsMapper.insertSelective(news);
    }

    public List<News> findByDate(Long userId, String date) {
        if (userId == null) {
            userId = CurrentUser.getUserId();
        }
        return newsMapper.findMine(userId, date);
    }
}
