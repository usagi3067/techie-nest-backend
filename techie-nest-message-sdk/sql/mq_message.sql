DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`
(
    `id`                  bigint       NOT NULL AUTO_INCREMENT COMMENT '消息id',
    `message_type`        varchar(32)  NOT NULL COMMENT '消息类型代码: course_publish ,  media_test',
    `business_key1`       varchar(64)           DEFAULT NULL COMMENT '关联业务信息',
    `business_key2`       varchar(255)          DEFAULT NULL COMMENT '关联业务信息',
    `business_key3`       varchar(512)          DEFAULT NULL COMMENT '关联业务信息',
    `execute_num`         int unsigned NOT NULL DEFAULT '0' COMMENT '通知次数',
    `state`               char(1)      NOT NULL DEFAULT '0' COMMENT '处理状态，0:初始，1:成功',
    `return_failure_date` datetime              DEFAULT NULL COMMENT '回复失败时间',
    `return_success_date` datetime              DEFAULT NULL COMMENT '回复成功时间',
    `return_failure_msg`  varchar(2048)         DEFAULT NULL COMMENT '回复失败内容',
    `execute_date`        datetime              DEFAULT NULL COMMENT '最近通知时间',
    `stage_state1`        char(1)      NOT NULL DEFAULT '0' COMMENT '阶段1处理状态, 0:初始，1:成功',
    `stage_state2`        char(1)      NOT NULL DEFAULT '0' COMMENT '阶段2处理状态, 0:初始，1:成功',
    `stage_state3`        char(1)      NOT NULL DEFAULT '0' COMMENT '阶段3处理状态, 0:初始，1:成功',
    `stage_state4`        char(1)      NOT NULL DEFAULT '0' COMMENT '阶段4处理状态, 0:初始，1:成功',
    PRIMARY KEY (`id`) USING BTREE
) comment '消息表';
