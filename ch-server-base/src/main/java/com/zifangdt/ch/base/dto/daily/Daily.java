package com.zifangdt.ch.base.dto.daily;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.JsonReplacer;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.dto.News;
import com.zifangdt.ch.base.util.DateUtil;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by 袁兵 on 2017/9/21.
 */
@Table(name = "biz_daily")
public class Daily extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = -1734825708359421018L;

    @NotBlank
    private String todaySummary;
    @NotBlank
    private String tomorrowPlan;
    @NotBlank
    private String todayGain;
    @NotBlank
    private String todayProblem;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long createId;
    private Date createTime;
    @NotNull
    private Date date;

    @Transient
    private List<Comment> comments;
    @Transient
    private Long likeCount;
    @Transient
    private String commenter;

    private Boolean fromApp;

    @Transient
    private List<News> news;

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public Boolean getFromApp() {
        return fromApp;
    }

    public void setFromApp(Boolean fromApp) {
        this.fromApp = fromApp;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public Long getCommentCount() {
        if (comments == null) {
            return null;
        }
        return comments.stream().filter(comment -> comment.getParentId() == null).count();
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    @JsonReplacer("date")
    public String getDateString() {
        return DateUtil.FORMAT_DATE.format(date);
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTodaySummary() {
        return todaySummary;
    }

    public void setTodaySummary(String todaySummary) {
        this.todaySummary = todaySummary;
    }

    public String getTomorrowPlan() {
        return tomorrowPlan;
    }

    public void setTomorrowPlan(String tomorrowPlan) {
        this.tomorrowPlan = tomorrowPlan;
    }

    public String getTodayGain() {
        return todayGain;
    }

    public void setTodayGain(String todayGain) {
        this.todayGain = todayGain;
    }

    public String getTodayProblem() {
        return todayProblem;
    }

    public void setTodayProblem(String todayProblem) {
        this.todayProblem = todayProblem;
    }

    public Long getCreateId() {
        return createId;
    }

    public void setCreateId(Long createId) {
        this.createId = createId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
