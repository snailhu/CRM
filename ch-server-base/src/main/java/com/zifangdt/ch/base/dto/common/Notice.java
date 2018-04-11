package com.zifangdt.ch.base.dto.common;

import com.zifangdt.ch.base.dto.BaseEntity;
import com.zifangdt.ch.base.enums.NoticeType;
import com.zifangdt.ch.base.enums.RedirectType;
import com.zifangdt.ch.base.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 袁兵 on 2017/10/18.
 */
@Table(name = "com_notice")
public class Notice extends BaseEntity<Long> implements Serializable {
    private static final long serialVersionUID = 38889725491324754L;

    private static final Pattern PATTERN = Pattern.compile("%s");

    private String name;
    private NoticeType type;
    private Object[] arguments;
    private String tag;
    private Date createTime;
    private RedirectType redirectType;
    private Object[] redirectArgs;
    @Transient
    private Date readTime;
    @Transient
    private List<Long> receivers;

    public Map<String, Object> getRedirect() {
        if (redirectType == null) {
            return new HashMap<>();
        }
        return redirectType.parse(redirectArgs);
    }

    public String getContent() {
        if (type == null) {
            return null;
        }
        return String.format(type.getContentTemplate(), arguments);
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

    public String getNormalizedCreateTime() {
        return createTime == null || new Date().before(createTime) ? null : DateUtil.normalize(createTime);
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public List<Long> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<Long> receivers) {
        this.receivers = receivers;
    }

    public NoticeType getType() {
        return type;
    }

    public void setType(NoticeType type) {
        this.type = type;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private NoticeType type;
        private Object[] arguments;
        private String tag;
        private Date createTime;
        private RedirectType redirectType;
        private Object[] redirectArgs;
        private List<Long> receivers;

        public Builder trackId(Long trackId) {
            return redirect(RedirectType.TRACK, trackId);
        }

        public Builder processId(Long processId) {
            return redirect(RedirectType.PROCESS, processId);
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(NoticeType type, Object... arguments) {
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

        public Builder receivers(Long... receivers) {
            return receivers(Arrays.asList(receivers));
        }

        public Builder receivers(Collection<Long> receivers) {
            if (this.receivers == null) {
                this.receivers = new ArrayList<>(new HashSet<>(receivers));
            } else {
                this.receivers.addAll(new HashSet<>(receivers));
            }
            return this;
        }

        public Notice build() {
            if (CollectionUtils.isEmpty(receivers)) {
                throw new IllegalArgumentException("消息通知的接收者必须至少指定一个");
            }
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
            receivers = new ArrayList<>(new HashSet<>(receivers));
            Notice notice = new Notice();
            BeanUtils.copyProperties(this, notice);
            return notice;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public NoticeType getType() {
            return type;
        }

        public void setType(NoticeType type) {
            this.type = type;
        }

        public Object[] getArguments() {
            return arguments;
        }

        public void setArguments(Object[] arguments) {
            this.arguments = arguments;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
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

        public List<Long> getReceivers() {
            return receivers;
        }

        public void setReceivers(List<Long> receivers) {
            this.receivers = receivers;
        }
    }
}
