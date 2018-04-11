package com.zifangdt.ch.base.web;

import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.service.AbstractNewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by 袁兵 on 2018/1/25.
 */
@RestController
@RequestMapping("/news")
@ConditionalOnBean(AbstractNewsService.class)
public class NewsController {
    @Autowired
    private AbstractNewsService newsService;

    @PostMapping
    public void save(@RequestBody News news) {
        newsService.save(news);
    }

    @GetMapping("/mine")
    public List<News> mine(@RequestParam(value = "date", required = false) String date) {
        return newsService.findByDate(null, date);
    }

    @GetMapping("/byDate")
    public List<News> newsByDate(@RequestParam(value = "userId", required = false) Long userId, @RequestParam(value = "date", required = false) String date) {
        return newsService.findByDate(userId, date);
    }
}
