package com.zifangdt.ch.base.dto.todo;

import com.zifangdt.ch.base.converter.JsonPropertyTarget;
import com.zifangdt.ch.base.converter.NamedProperty;
import com.zifangdt.ch.base.dto.AuditEntity;
import com.zifangdt.ch.base.dto.common.Notice;
import com.zifangdt.ch.base.dto.customer.Customer;
import com.zifangdt.ch.base.enums.RedirectType;
import com.zifangdt.ch.base.enums.pair.*;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

/**
 * Created by 袁兵 on 2017/10/25.
 */
@Table(name = "biz_task")
public class TodoTask extends AuditEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1531075731995596520L;

    @NotBlank
    private String name;
    @NotNull
    @NamedProperty
    private TaskType type;
    private String description;
    private Long relatedBiz;
    @NamedProperty
    private RelatedBizType relatedBizType;
    @NamedProperty(target = JsonPropertyTarget.USER)
    private Long operator;
    @NotEmpty
    private Set<Long> receivers;
    private Date deadline;
    @NamedProperty
    private TaskUrgency urgency;
    @NamedProperty
    private TaskStatus status;

    private RedirectType redirectType;
    private Object[] redirectArgs;

    @Transient
    private String content; //用于推送显示

    @Transient
    private String relatedBizName;

    @Transient
    private Customer customer;

    private Date operateTime;

    private Boolean deleted;

    @Transient
    private String relatedBizTypeName;

    private CreatedFrom createdFrom;
    private Long createdFromId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TaskType getType() {
        return type;
    }

    public void setType(TaskType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRelatedBiz() {
        return relatedBiz;
    }

    public void setRelatedBiz(Long relatedBiz) {
        this.relatedBiz = relatedBiz;
    }

    public RelatedBizType getRelatedBizType() {
        return relatedBizType;
    }

    public void setRelatedBizType(RelatedBizType relatedBizType) {
        this.relatedBizType = relatedBizType;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Set<Long> getReceivers() {
        return receivers;
    }

    public void setReceivers(Set<Long> receivers) {
        this.receivers = receivers;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public TaskUrgency getUrgency() {
        return urgency;
    }

    public void setUrgency(TaskUrgency urgency) {
        this.urgency = urgency;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
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

    public String getContent() {
        return content;
    }

    public String getRelatedBizName() {
        return relatedBizName;
    }

    public void setRelatedBizName(String relatedBizName) {
        this.relatedBizName = relatedBizName;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getRelatedBizTypeName() {
        return relatedBizTypeName;
    }

    public void setRelatedBizTypeName(String relatedBizTypeName) {
        this.relatedBizTypeName = relatedBizTypeName;
    }

    public CreatedFrom getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(CreatedFrom createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Long getCreatedFromId() {
        return createdFromId;
    }

    public void setCreatedFromId(Long createdFromId) {
        this.createdFromId = createdFromId;
    }

    public TodoTask setContent(String content) {
        this.content = content;
        return this;
    }

    @Transient
    private Notice notice;

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private TaskType type;
        private Set<Long> receivers;
        private Date deadline;
        private TaskUrgency urgency;
        private RedirectType redirectType;
        private Object[] redirectArgs;
        private Notice.Builder noticeBuilder;

        /**
         * 适用于系统自动生成的任务类型（流程、子任务、工单其中一个），并且希望在创建任务时自动生成指定的消息。
         * 如果noticeBuilder未调用name、redirect、receivers、tag，将分别取任务的对应字段。
         *
         * @param noticeBuilder
         * @return
         */
        public Builder withNotice(Notice.Builder noticeBuilder) {
            this.noticeBuilder = noticeBuilder;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder type(TaskType type) {
            this.type = type;
            return this;
        }

        public Builder receivers(Long... receivers) {
            return receivers(Arrays.asList(receivers));
        }

        public Builder receivers(Collection<Long> receivers) {
            if (this.receivers == null) {
                this.receivers = new HashSet<>(receivers);
            } else {
                this.receivers.addAll(receivers);
            }
            return this;
        }

        public Builder deadline(Date deadline) {
            this.deadline = deadline;
            return this;
        }

        public Builder urgency(TaskUrgency urgency) {
            this.urgency = urgency;
            return this;
        }

        public Builder redirect(RedirectType redirectType, Object... redirectArgs) {
            this.redirectType = redirectType;
            this.redirectArgs = redirectArgs;
            return this;
        }

        public Builder trackId(Long trackId) {
            return redirect(RedirectType.TRACK, trackId);
        }

        public TodoTask build() {
            if (type == null || type == TaskType.TODO) {
                throw new IllegalArgumentException("自动创建的任务类型只能是“流程”、“子任务”、“工单”中的一个");
            }
            if (CollectionUtils.isEmpty(receivers)) {
                throw new IllegalArgumentException("任务的接收者必须至少指定一个");
            }
            if (redirectType != null && redirectType.getArgumentList().split(",").length != redirectArgs.length) {
                throw new IllegalArgumentException("任务跳转参数个数不一致");
            }

            TodoTask todoTask = new TodoTask();
            BeanUtils.copyProperties(this, todoTask);

            if (noticeBuilder != null) {
                if (noticeBuilder.getName() == null) {
                    noticeBuilder.name(this.name);
                }
                if (noticeBuilder.getRedirectType() == null) {
                    noticeBuilder.redirect(this.redirectType, this.redirectArgs);
                }
                if (noticeBuilder.getReceivers() == null) {
                    noticeBuilder.receivers(this.receivers);
                }
                if (noticeBuilder.getTag() == null) {
                    noticeBuilder.tag(this.type.getName());
                }

                todoTask.notice = noticeBuilder.build();
            }
            return todoTask;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public TaskType getType() {
            return type;
        }

        public void setType(TaskType type) {
            this.type = type;
        }

        public Set<Long> getReceivers() {
            return receivers;
        }

        public void setReceivers(Set<Long> receivers) {
            this.receivers = receivers;
        }

        public Date getDeadline() {
            return deadline;
        }

        public void setDeadline(Date deadline) {
            this.deadline = deadline;
        }

        public TaskUrgency getUrgency() {
            return urgency;
        }

        public void setUrgency(TaskUrgency urgency) {
            this.urgency = urgency;
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

    }
}
