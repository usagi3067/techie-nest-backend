DROP TABLE IF EXISTS `mq_message_history`;

CREATE TABLE `mq_message_history`
(
    `id`                  bigint      NOT NULL COMMENT '消息id',
    `message_type`        varchar(32) NOT NULL COMMENT '消息类型代码',
    `business_key1`       varchar(64)               DEFAULT NULL COMMENT '关联业务信息',
    `business_key2`       varchar(255)              DEFAULT NULL COMMENT '关联业务信息',
    `business_key3`       varchar(512)              DEFAULT NULL COMMENT '关联业务信息',
    `execute_num`         int unsigned              DEFAULT NULL COMMENT '通知次数',
    `state`               int(10) unsigned zerofill DEFAULT NULL COMMENT '处理状态，0:初始，1:成功，2:失败',
    `return_failure_date` datetime                  DEFAULT NULL COMMENT '回复失败时间',
    `return_success_date` datetime                  DEFAULT NULL COMMENT '回复成功时间',
    `return_failure_msg`  varchar(255)              DEFAULT NULL COMMENT '回复失败内容',
    `execute_date`        datetime                  DEFAULT NULL COMMENT '最近通知时间',
    `stage_state1`        char(1)                   DEFAULT NULL,
    `stage_state2`        char(1)                   DEFAULT NULL,
    `stage_state3`        char(1)                   DEFAULT NULL,
    `stage_state4`        char(1)                   DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) COMMENT '消息历史表';