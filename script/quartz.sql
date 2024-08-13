-- 日历表
CREATE TABLE `QRTZ_CALENDARS`
(
    `SCHED_NAME`    varchar(120) NOT NULL COMMENT '调度器名称',
    `CALENDAR_NAME` varchar(190) NOT NULL COMMENT '日历名称',
    `CALENDAR`      blob         NOT NULL COMMENT '日历数据',
    PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`)
) ENGINE = InnoDB COMMENT ='日历表';

-- 已触发的触发器表
CREATE TABLE `QRTZ_FIRED_TRIGGERS`
(
    `SCHED_NAME`        varchar(120) NOT NULL COMMENT '调度器名称',
    `ENTRY_ID`          varchar(95)  NOT NULL COMMENT '记录ID',
    `TRIGGER_NAME`      varchar(190) NOT NULL COMMENT '触发器名称',
    `TRIGGER_GROUP`     varchar(190) NOT NULL COMMENT '触发器分组',
    `INSTANCE_NAME`     varchar(190) NOT NULL COMMENT '实例名称',
    `FIRED_TIME`        bigint       NOT NULL COMMENT '触发时间',
    `SCHED_TIME`        bigint       NOT NULL COMMENT '调度时间',
    `PRIORITY`          int          NOT NULL COMMENT '优先级',
    `STATE`             varchar(16)  NOT NULL COMMENT '状态',
    `JOB_NAME`          varchar(190) DEFAULT NULL COMMENT '任务名称',
    `JOB_GROUP`         varchar(190) DEFAULT NULL COMMENT '任务分组',
    `IS_NONCONCURRENT`  varchar(1)   DEFAULT NULL COMMENT '是否不允许并发',
    `REQUESTS_RECOVERY` varchar(1)   DEFAULT NULL COMMENT '是否需要恢复',
    PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`),
    KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`, `INSTANCE_NAME`),
    KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`, `INSTANCE_NAME`, `REQUESTS_RECOVERY`),
    KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
    KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`, `JOB_GROUP`),
    KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB COMMENT ='已触发的触发器表';

-- 任务详情表
CREATE TABLE `QRTZ_JOB_DETAILS`
(
    `SCHED_NAME`        varchar(120) NOT NULL COMMENT '调度器名称',
    `JOB_NAME`          varchar(190) NOT NULL COMMENT '任务名称',
    `JOB_GROUP`         varchar(190) NOT NULL COMMENT '任务分组',
    `DESCRIPTION`       varchar(250) DEFAULT NULL COMMENT '描述',
    `JOB_CLASS_NAME`    varchar(250) NOT NULL COMMENT '任务类名',
    `IS_DURABLE`        varchar(1)   NOT NULL COMMENT '是否持久化',
    `IS_NONCONCURRENT`  varchar(1)   NOT NULL COMMENT '是否允许并发执行',
    `IS_UPDATE_DATA`    varchar(1)   NOT NULL COMMENT '是否更新数据',
    `REQUESTS_RECOVERY` varchar(1)   NOT NULL COMMENT '是否需要恢复',
    `JOB_DATA`          blob COMMENT '任务数据',
    PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
    KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`, `REQUESTS_RECOVERY`),
    KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`, `JOB_GROUP`)
) ENGINE = InnoDB COMMENT ='任务详情表';

-- 锁表
CREATE TABLE `QRTZ_LOCKS`
(
    `SCHED_NAME` varchar(120) NOT NULL COMMENT '调度器名称',
    `LOCK_NAME`  varchar(40)  NOT NULL COMMENT '锁名称',
    PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`)
) ENGINE = InnoDB COMMENT ='锁表';

-- 暂停的触发器分组表
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`
(
    `SCHED_NAME`    varchar(120) NOT NULL COMMENT '调度器名称',
    `TRIGGER_GROUP` varchar(190) NOT NULL COMMENT '触发器分组',
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB COMMENT ='暂停的触发器分组表';

-- 调度器状态表
CREATE TABLE `QRTZ_SCHEDULER_STATE`
(
    `SCHED_NAME`        varchar(120) NOT NULL COMMENT '调度器名称',
    `INSTANCE_NAME`     varchar(190) NOT NULL COMMENT '实例名称',
    `LAST_CHECKIN_TIME` bigint       NOT NULL COMMENT '最后检查时间',
    `CHECKIN_INTERVAL`  bigint       NOT NULL COMMENT '检查间隔',
    PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`)
) ENGINE = InnoDB COMMENT ='调度器状态表';

-- 简单属性触发器表
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`
(
    `SCHED_NAME`    varchar(120) NOT NULL COMMENT '调度器名称',
    `TRIGGER_NAME`  varchar(190) NOT NULL COMMENT '触发器名称',
    `TRIGGER_GROUP` varchar(190) NOT NULL COMMENT '触发器分组',
    `STR_PROP_1`    varchar(512)   DEFAULT NULL COMMENT '字符串属性1',
    `STR_PROP_2`    varchar(512)   DEFAULT NULL COMMENT '字符串属性2',
    `STR_PROP_3`    varchar(512)   DEFAULT NULL COMMENT '字符串属性3',
    `INT_PROP_1`    int            DEFAULT NULL COMMENT '整数属性1',
    `INT_PROP_2`    int            DEFAULT NULL COMMENT '整数属性2',
    `LONG_PROP_1`   bigint         DEFAULT NULL COMMENT '长整数属性1',
    `LONG_PROP_2`   bigint         DEFAULT NULL COMMENT '长整数属性2',
    `DEC_PROP_1`    decimal(13, 4) DEFAULT NULL COMMENT '小数属性1',
    `DEC_PROP_2`    decimal(13, 4) DEFAULT NULL COMMENT '小数属性2',
    `BOOL_PROP_1`   varchar(1)     DEFAULT NULL COMMENT '布尔属性1',
    `BOOL_PROP_2`   varchar(1)     DEFAULT NULL COMMENT '布尔属性2',
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB COMMENT ='简单属性触发器表';

-- 触发器表
CREATE TABLE `QRTZ_TRIGGERS`
(
    `SCHED_NAME`     varchar(120) NOT NULL COMMENT '调度名称',
    `TRIGGER_NAME`   varchar(190) NOT NULL COMMENT '触发器名称',
    `TRIGGER_GROUP`  varchar(190) NOT NULL COMMENT '触发器组',
    `JOB_NAME`       varchar(190) NOT NULL COMMENT '作业名称',
    `JOB_GROUP`      varchar(190) NOT NULL COMMENT '作业组',
    `DESCRIPTION`    varchar(250) DEFAULT NULL COMMENT '描述',
    `NEXT_FIRE_TIME` bigint       DEFAULT NULL COMMENT '下次触发时间',
    `PREV_FIRE_TIME` bigint       DEFAULT NULL COMMENT '上次触发时间',
    `PRIORITY`       int          DEFAULT NULL COMMENT '优先级',
    `TRIGGER_STATE`  varchar(16)  NOT NULL COMMENT '触发器状态',
    `TRIGGER_TYPE`   varchar(8)   NOT NULL COMMENT '触发器类型',
    `START_TIME`     bigint       NOT NULL COMMENT '开始时间',
    `END_TIME`       bigint       DEFAULT NULL COMMENT '结束时间',
    `CALENDAR_NAME`  varchar(190) DEFAULT NULL COMMENT '日历名称',
    `MISFIRE_INSTR`  smallint     DEFAULT NULL COMMENT '错失触发策略',
    `JOB_DATA`       blob,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    KEY `IDX_QRTZ_T_J` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`),
    KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`, `JOB_GROUP`),
    KEY `IDX_QRTZ_T_C` (`SCHED_NAME`, `CALENDAR_NAME`),
    KEY `IDX_QRTZ_T_G` (`SCHED_NAME`, `TRIGGER_GROUP`),
    KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`, `TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`, `TRIGGER_GROUP`, `TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`, `NEXT_FIRE_TIME`),
    KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`, `TRIGGER_STATE`, `NEXT_FIRE_TIME`),
    KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`),
    KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_STATE`),
    KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`, `MISFIRE_INSTR`, `NEXT_FIRE_TIME`, `TRIGGER_GROUP`,
                                         `TRIGGER_STATE`)
) ENGINE = InnoDB COMMENT ='触发器表';

-- blob触发器表
CREATE TABLE `QRTZ_BLOB_TRIGGERS`
(
    `SCHED_NAME`    varchar(120) NOT NULL COMMENT '调度名称',
    `TRIGGER_NAME`  varchar(190) NOT NULL COMMENT '触发器名称',
    `TRIGGER_GROUP` varchar(190) NOT NULL COMMENT '触发器组',
    `BLOB_DATA`     blob,
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`),
    KEY `SCHED_NAME` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB COMMENT ='blob触发器表';

-- cron触发器表
CREATE TABLE `QRTZ_CRON_TRIGGERS`
(
    `SCHED_NAME`      varchar(120) NOT NULL COMMENT '调度名称',
    `TRIGGER_NAME`    varchar(190) NOT NULL COMMENT '触发器名称',
    `TRIGGER_GROUP`   varchar(190) NOT NULL COMMENT '触发器组',
    `CRON_EXPRESSION` varchar(120) NOT NULL COMMENT 'Cron表达式',
    `TIME_ZONE_ID`    varchar(80) DEFAULT NULL COMMENT '时区ID',
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB COMMENT ='cron触发器表';

-- 简单触发器表
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`
(
    `SCHED_NAME`      varchar(120) NOT NULL COMMENT '调度名称',
    `TRIGGER_NAME`    varchar(190) NOT NULL COMMENT '触发器名称',
    `TRIGGER_GROUP`   varchar(190) NOT NULL COMMENT '触发器组',
    `REPEAT_COUNT`    bigint       NOT NULL COMMENT '重复次数',
    `REPEAT_INTERVAL` bigint       NOT NULL COMMENT '重复间隔',
    `TIMES_TRIGGERED` bigint       NOT NULL COMMENT '触发次数',
    PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE = InnoDB COMMENT ='简单触发器表';