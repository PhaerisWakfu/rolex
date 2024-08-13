package com.phaeris.rolex.job;

import com.alibaba.fastjson.JSON;
import com.phaeris.rolex.enums.JobTypeEnum;
import com.phaeris.rolex.util.TypeUtil;
import com.phaeris.rolex.constants.JobConstants;
import com.phaeris.rolex.constants.MDCConstants;
import com.phaeris.rolex.obj.bo.JobBO;
import com.phaeris.rolex.service.JobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;

/**
 * 定义一个新的job类型只需要进行如下操作：
 * <ol>
 *     <li>在当前目录下新建业务子目录, cf.: job/ai
 *     <li>新增任务类继承该类, cf.: AIJob
 *     <li>新增一个任务类所需参数类, cf.: AIJobParam
 *     <li>定义参数泛型指向参数类, e.g.: public class AIJob extends AbstractJob<AIJobParam>
 *     <li>新增枚举{@link JobTypeEnum}
 * </ol>
 *
 * @author wyh
 * @since 2023/9/7
 */
@Slf4j
@DisallowConcurrentExecution
public abstract class AbstractJob<T extends Serializable> extends QuartzJobBean {

    @Autowired
    private JobService jobService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        //获取job参数
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        Long jobId = (Long) jobDataMap.get(JobConstants.JOB_DATA_PARAM_KEY);
        JobBO job = jobService.getDetail(jobId);
        String name = job.getName();
        String paramStr = job.getParamJson();
        JobTypeEnum type = JobTypeEnum.getByJobClass(this.getClass());
        MDC.put(MDCConstants.GROUP, type.name());
        T param = JSON.parseObject(paramStr, TypeUtil.getActualType(this.getClass()));
        //开始执行
        log.info("任务开始 ==> [{}#{}]", jobId, name);
        try {
            //执行
            exec(param);
            log.info("任务执行成功 ==> [{}#{}]", jobId, name);
        } catch (Exception e) {
            log.error("任务执行失败[{}#{}] ==>", jobId, name, e);
        } finally {
            MDC.clear();
        }
    }

    /**
     * job执行逻辑
     *
     * @param param 参数
     */
    public abstract void exec(T param);
}
