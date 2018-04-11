package com.zifangdt.ch.base.dto.customer;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.RedirectType;
import com.zifangdt.ch.base.enums.pair.NewsType;
import org.springframework.beans.BeanUtils;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 袁兵 on 2018/1/23.
 */
@Table(name = "com_sales_clue_news")
public class SalesClueNews extends BaseEntity<Long> implements Serializable {

    private static final Pattern PATTERN = Pattern.compile("\\?");
    private static final long serialVersionUID = 3824680510941312791L;

    private NewsType type;
    private Object[] arguments;
    private Date happenedAt;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long operator;
    private RedirectType redirectType;
    private Object[] redirectArgs;
    private String tag;
    private Long relatedId;

    public String getContentTemplate() {
        return type == null ? null : type.getContentTemplate();
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }

    public NewsType getType() {
        return type;
    }

    public void setType(NewsType type) {
        this.type = type;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Date getHappenedAt() {
        return happenedAt;
    }

    public void setHappenedAt(Date happenedAt) {
        this.happenedAt = happenedAt;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public RedirectType getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(RedirectType redirectType) {
        this.redirectType = redirectType;
    }

    public Object[] getRedirectArgs() {
        return redirectArgs;
    }

    public void setRedirectArgs(Object[] redirectArgs) {
        this.redirectArgs = redirectArgs;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private NewsType type;
        private Object[] arguments;
        private Date happenedAt;
        private Long operator;
        private RedirectType redirectType;
        private Object[] redirectArgs;
        private String tag;
        private Long relatedId;

        public Builder happenedAt(Date happenedAt) {
            this.happenedAt = happenedAt;
            return this;
        }

        public Builder relatedId(Long relatedId) {
            this.relatedId = relatedId;
            return this;
        }

        public Builder type(NewsType type, Object... arguments) {
            this.type = type;
            this.arguments = arguments;
            return this;
        }

        public Builder tag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder redirect(RedirectType redirectType, Object... redirectArgs) {
            this.redirectType = redirectType;
            this.redirectArgs = redirectArgs;
            return this;
        }

        public Builder operator(Long operator) {
            this.operator = operator;
            return this;
        }

        public SalesClueNews build() {
            if (type != null) {
                int count = 0;
                Matcher matcher = PATTERN.matcher(type.getContentTemplate());
                while (matcher.find()) {
                    count++;
                }
                if (count != arguments.length) {
                    throw new IllegalArgumentException("消息内容参数个数不一致");
                }
            }

            if (redirectType != null && redirectType.getArgumentList().split(",").length != redirectArgs.length) {
                throw new IllegalArgumentException("消息跳转参数个数不一致");
            }
            if (happenedAt == null) {
                happenedAt = new Date();
            }
            SalesClueNews news = new SalesClueNews();
            BeanUtils.copyProperties(this, news);
            return news;
        }

        public Long getRelatedId() {
            return relatedId;
        }

        public void setRelatedId(Long relatedId) {
            this.relatedId = relatedId;
        }

        public NewsType getType() {
            return type;
        }

        public void setType(NewsType type) {
            this.type = type;
        }

        public Object[] getArguments() {
            return arguments;
        }

        public void setArguments(Object[] arguments) {
            this.arguments = arguments;
        }

        public Date getHappenedAt() {
            return happenedAt;
        }

        public void setHappenedAt(Date happenedAt) {
            this.happenedAt = happenedAt;
        }

        public Long getOperator() {
            return operator;
        }

        public void setOperator(Long operator) {
            this.operator = operator;
        }

        public RedirectType getRedirectType() {
            return redirectType;
        }

        public void setRedirectType(RedirectType redirectType) {
            this.redirectType = redirectType;
        }

        public Object[] getRedirectArgs() {
            return redirectArgs;
        }

        public void setRedirectArgs(Object[] redirectArgs) {
            this.redirectArgs = redirectArgs;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }
    }

}
