package com.phaeris.rolex.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 与{@link org.quartz.Trigger.TriggerState}不同,
 * 该状态为源表trigger_state字段的枚举
 *
 * @author wyh
 * @since 2024/7/24
 */
@Getter
@AllArgsConstructor
public enum TriggerStateEnum {

    WAITING("等待中"),

    ACQUIRED("准备执行"),

    EXECUTING("执行中"),

    COMPLETE("执行完毕"),

    BLOCKED("阻塞"),

    ERROR("错误"),

    PAUSED("暂停"),

    PAUSED_BLOCKED("暂停且阻塞"),

    DELETED("删除");

    private final String desc;
}
