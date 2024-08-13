package com.phaeris.rolex.enums;

import com.phaeris.rolex.exception.ExternalException;
import com.phaeris.rolex.exception.InternalException;
import com.phaeris.rolex.job.system.PassThroughJob;
import com.phaeris.rolex.util.EnumUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author wyh
 * @since 2023/11/28
 */
@Getter
@AllArgsConstructor
public enum JobTypeEnum {

    SYS_PASS_TROUGH(-1, "PASS_TROUGH", PassThroughJob.class, "系统内置-透传调用任务");

    private final Integer type;

    private final String groupName;

    private final Class<? extends QuartzJobBean> jobClass;

    private final String desc;

    /**
     * 根据type获取枚举
     */
    public static JobTypeEnum getByType(Integer type) {
        return EnumUtil.valueOfThrow(JobTypeEnum.class, type, JobTypeEnum::getType,
                () -> new ExternalException("任务类型未定义"));
    }

    /**
     * 根据className获取枚举
     */
    public static JobTypeEnum getByJobClass(Class<? extends QuartzJobBean> clazz) {
        return EnumUtil.valueOfThrow(JobTypeEnum.class, clazz, JobTypeEnum::getJobClass,
                () -> new InternalException("任务类未定义"));
    }
}
