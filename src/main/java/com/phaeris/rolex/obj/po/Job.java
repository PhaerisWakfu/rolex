package com.phaeris.rolex.obj.po;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.phaeris.rolex.enums.JobTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * 定时任务持久化对象
 * <p>
 * mp updateStrategy说明:
 * <p>{@link FieldStrategy#DEFAULT}：追随全局配置
 * <p>{@link FieldStrategy#NOT_NULL}：更新时，字段不是null的才会更新进去
 * <p>{@link FieldStrategy#IGNORED}：更新时，字段是null也会更新进去
 * <p>{@link FieldStrategy#NEVER}：更新时，永远不更新该字段
 *
 * @author wyh
 * @since 2024/7/22
 */
@Data
public class Job {

    private Long id;

    @NotBlank(message = "任务名不能为空")
    @Size(max = 50, message = "名称长度不能超过50个字符")
    private String name;

    @Size(max = 30, message = "标签长度不能超过30个字符")
    private String tag;

    @Size(max = 255, message = "描述长度不能超过255个字符")
    private String description;

    /**
     * {@link JobTypeEnum}
     */
    @NotNull(message = "任务类型不能为空")
    private Integer type;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String jobGroupName;

    @TableField(updateStrategy = FieldStrategy.NEVER)
    private String jobName;

    @NotBlank(message = "cron表达式不能为空")
    @Size(max = 120, message = "cron表达式长度不能超过120个字符")
    private String cron;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String zoneId;

    private String paramJson;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date startAt;

    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Date endAt;

    private Date ctime;

    private Date mtime;

    private Integer deleted;
}
