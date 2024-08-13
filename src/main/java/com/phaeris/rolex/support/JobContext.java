package com.phaeris.rolex.support;

import cn.hutool.core.text.CharSequenceUtil;
import com.phaeris.rolex.exception.ExternalException;
import com.phaeris.rolex.exception.ScheduleException;
import com.phaeris.rolex.constants.JobConstants;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.ZoneId;
import java.util.*;

/**
 * @author wyh
 * @since 2023/9/7
 */
public class JobContext {

    private static Scheduler scheduler;

    @Autowired
    @SuppressWarnings("SpringJavaAutowiredMembersInspection")
    public void setUp(Scheduler scheduler) {
        JobContext.scheduler = scheduler;
    }


    /**
     * 增加作业
     *
     * @param jobClass 任务实现类
     * @param group    任务组名
     * @param jobName  任务名称(建议唯一)
     * @param jobTime  时间表达式 （如：0/5 * * * * ? ）
     * @param zoneId   时区
     * @param param    参数
     * @param startAt  开始时间
     * @param endAt    结束时间
     */
    public static void addJob(Class<? extends QuartzJobBean> jobClass, String group, String jobName, String jobTime, String zoneId,
                              Object param, Date startAt, Date endAt) {
        try {
            // 创建jobDetail实例，绑定Job实现类
            // 指明job的名称，所在组的名称，以及绑定job类
            // 任务名称和组构成任务key
            JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, group).build();
            // 设置job参数
            Optional.ofNullable(param).ifPresent(p -> {
                Map<String, Object> jobDataMap = new HashMap<>();
                jobDataMap.put(JobConstants.JOB_DATA_PARAM_KEY, p);
                jobDetail.getJobDataMap().putAll(jobDataMap);
            });
            // 定义调度触发规则
            // 使用cornTrigger规则
            // 触发器key
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobTime)
                    .withMisfireHandlingInstructionDoNothing();
            if (CharSequenceUtil.isNotBlank(zoneId)) {
                cronScheduleBuilder.inTimeZone(TimeZone.getTimeZone(ZoneId.of(zoneId)));
            }
            TriggerBuilder<CronTrigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobName, group)
                    .withSchedule(cronScheduleBuilder);
            checkBeginEnd(startAt, endAt);
            Optional.ofNullable(endAt).ifPresent(triggerBuilder::endAt);
            if (startAt != null) {
                triggerBuilder.startAt(startAt);
            } else {
                triggerBuilder.startNow();
            }
            Trigger trigger = triggerBuilder.build();
            // 把作业和触发器注册到任务调度中
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (Exception e) {
            throw new ScheduleException(e.getMessage(), e);
        }
    }

    /**
     * 更新作业
     *
     * @param group   作业组名称
     * @param jobName 作业名称
     * @param jobTime 工作时间
     * @param zoneId  时区
     * @param startAt 开始时间
     * @param endAt   结束时间
     */
    public static void updateJob(String group, String jobName, String jobTime, String zoneId, Date startAt, Date endAt) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, group);
            Trigger.TriggerState originalState = scheduler.getTriggerState(triggerKey);
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                throw new ScheduleException("不存在的定时任务");
            }
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobTime)
                    .withMisfireHandlingInstructionDoNothing();
            if (CharSequenceUtil.isNotBlank(zoneId)) {
                cronScheduleBuilder.inTimeZone(TimeZone.getTimeZone(ZoneId.of(zoneId)));
            }
            TriggerBuilder<CronTrigger> triggerBuilder = trigger.getTriggerBuilder().withIdentity(triggerKey)
                    //修改完不立即执行等待下一次cron
                    .withSchedule(cronScheduleBuilder);
            checkBeginEnd(startAt, endAt);
            triggerBuilder.endAt(endAt);
            Optional.ofNullable(startAt).ifPresent(triggerBuilder::startAt);
            trigger = triggerBuilder.build();
            // 重启触发器
            scheduler.rescheduleJob(triggerKey, trigger);
            if (Trigger.TriggerState.PAUSED.equals(originalState)) {
                scheduler.pauseJob(JobKey.jobKey(jobName, group));
            }
        } catch (Exception e) {
            throw new ScheduleException(e.getMessage(), e);
        }
    }

    /**
     * 删除作业
     *
     * @param group   作业组名称
     * @param jobName 作业名称
     */
    public static void deleteJob(String group, String jobName) {
        try {
            scheduler.deleteJob(JobKey.jobKey(jobName, group));
        } catch (Exception e) {
            throw new ScheduleException(e.getMessage(), e);
        }
    }

    /**
     * 暂停作业
     *
     * @param group   作业组名称
     * @param jobName 作业名称
     */
    public static void pauseJob(String group, String jobName) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobName, group));
        } catch (Exception e) {
            throw new ScheduleException(e.getMessage(), e);
        }
    }

    /**
     * 恢复作业
     *
     * @param group   作业组名称
     * @param jobName 作业名称
     */
    public static void resumeJob(String group, String jobName) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobName, group));
        } catch (Exception e) {
            throw new ScheduleException(e.getMessage(), e);
        }
    }

    /**
     * 立即执行作业
     *
     * @param group   作业组名称
     * @param jobName 作业名称
     */
    public static void runJobNow(String group, String jobName) {
        try {
            scheduler.triggerJob(JobKey.jobKey(jobName, group));
        } catch (Exception e) {
            throw new ScheduleException(e.getMessage(), e);
        }
    }

    /**
     * 获取作业运行状态
     *
     * @param group   组
     * @param jobName 名称
     * @return {@link Trigger.TriggerState}
     */
    @SuppressWarnings("unused")
    public static Trigger.TriggerState getTriggerState(String group, String jobName) {
        try {
            return scheduler.getTriggerState(TriggerKey.triggerKey(jobName, group));
        } catch (Exception e) {
            throw new ScheduleException(e.getMessage(), e);
        }
    }

    /**
     * 校验开始结束时间
     *
     * @param startAt 开始时间
     * @param endAt   结束时间
     */
    private static void checkBeginEnd(Date startAt, Date endAt) {
        if (endAt != null && endAt.before(new Date())) {
            throw new ExternalException("有效期不能在当前时间之前");
        }
        if (startAt != null && endAt != null && endAt.before(startAt)) {
            throw new ExternalException("结束日期不能小于开始日期");
        }
    }
}
