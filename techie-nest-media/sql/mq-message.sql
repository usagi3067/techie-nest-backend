DROP TABLE IF EXISTS `mq_message`;
CREATE TABLE `mq_message`
(
    `id`                 varchar(64)  NOT NULL COMMENT '消息id',
    `message_type`       varchar(32)  NOT NULL COMMENT '消息类型代码',
    `business_key1`      varchar(64)   DEFAULT NULL COMMENT '关联业务信息',
    `business_key2`      varchar(255)  DEFAULT NULL COMMENT '关联业务信息',
    `business_key3`      varchar(512)  DEFAULT NULL COMMENT '关联业务信息',
    `mq_host`            varchar(32)  NOT NULL COMMENT '消息队列主机',
    `mq_port`            int          NOT NULL COMMENT '消息队列端口',
    `mq_virtualhost`     varchar(32)  NOT NULL COMMENT '消息队列虚拟主机',
    `mq_queue`           varchar(32)  NOT NULL COMMENT '队列名称',
    `inform_num`         int unsigned NOT NULL COMMENT '通知次数',
    `state`              char(1)      NOT NULL COMMENT '处理状态，0:初始，1:成功',
    `return_failure_date` datetime      DEFAULT NULL COMMENT '回复失败时间',
    `return_success_date` datetime      DEFAULT NULL COMMENT '回复成功时间',
    `return_failure_msg`  varchar(2048) DEFAULT NULL COMMENT '回复失败内容',
    `inform_date`        datetime      DEFAULT NULL COMMENT '最近通知时间',
    `stage_state1`       char(1)       DEFAULT NULL COMMENT '阶段1处理状态, 0:初始，1:成功',
    `stage_state2`       char(1)       DEFAULT NULL COMMENT '阶段2处理状态, 0:初始，1:成功',
    `stage_state3`       char(1)       DEFAULT NULL COMMENT '阶段3处理状态, 0:初始，1:成功',
    `stage_state4`       char(1)       DEFAULT NULL COMMENT '阶段4处理状态, 0:初始，1:成功',
    PRIMARY KEY (`id`) USING BTREE
) comment '消息表';

INSERT INTO `mq_message` (`id`, `message_type`, `business_key1`, `business_key2`, `business_key3`, `mq_host`, `mq_port`,
                          `mq_virtualhost`, `mq_queue`, `inform_num`, `state`, `return_failure_date`,
                          `return_success_date`, `return_failure_msg`, `inform_date`, `stage_state1`, `stage_state2`,
                          `stage_state3`, `stage_state4`)
VALUES ('f29a3149-7429-40be-8a4e-9909f32003b0', 'xc.mq.msgsync.coursepub', '111', NULL, NULL, '127.0.0.1', 5607, '/',
        'xc.course.publish.queue', 0, '0', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
