CREATE TABLE `job`
(
    `id`             bigint(11)   NOT NULL COMMENT '主键',
    `name`           varchar(50)  NOT NULL COMMENT '任务名',
    `tag`            varchar(30)           DEFAULT NULL COMMENT '标签',
    `description`    varchar(255)          DEFAULT NULL COMMENT '任务描述',
    `type`           tinyint(4)   NOT NULL COMMENT '任务类型',
    `job_group_name` varchar(30)  NOT NULL COMMENT 'quartz任务组名',
    `job_name`       varchar(50)  NOT NULL COMMENT 'quartz任务名',
    `cron`           varchar(120) NOT NULL COMMENT '时间表达式',
    `zone_id`        varchar(50)           DEFAULT NULL COMMENT '时区',
    `param_json`     json                  DEFAULT NULL COMMENT '任务入参',
    `start_at`       datetime              DEFAULT NULL COMMENT '开始时间',
    `end_at`         datetime              DEFAULT NULL COMMENT '结束时间',
    `ctime`          datetime     NOT NULL COMMENT '创建时间',
    `mtime`          datetime              DEFAULT NULL COMMENT '更新时间',
    `deleted`        tinyint(4)   NOT NULL DEFAULT '0' COMMENT '是否删除 1是 0否',
    PRIMARY KEY (`id`),
    KEY `UNIQ_TYPE` (`type`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='任务表';