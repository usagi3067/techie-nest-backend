DROP TABLE IF EXISTS `course_publish_pre`;

CREATE TABLE `course_publish_pre`
(
    `id`                 bigint                                     NOT NULL COMMENT '主键',
    `name`               varchar(512)                               NOT NULL COMMENT '课程名称',
    `tags`               varchar(1024) DEFAULT NULL COMMENT '标签(json格式)',
    `lecturer_id`        bigint                                     NOT NULL COMMENT '讲师ID',
    `lecturer_name`      varchar(512)  DEFAULT NULL COMMENT '讲师名称',
    `main_category`      varchar(512)                               NOT NULL COMMENT '主分类',
    `sub_category`       varchar(512)                               NOT NULL COMMENT '次分类',
    `sub_category_name`  varchar(512)                               NOT NULL COMMENT '次分类名称',
    `pic`                varchar(1024)                              NOT NULL COMMENT '课程图片',
    `description`        text COMMENT '课程介绍',
    `pre_knowledge`      text COMMENT '预备知识',
    `market`             text COMMENT '课程营销信息，json格式',
    `teach_plan`         text COMMENT '所有课程计划，json格式',
    `lecturer_info`      text COMMENT '讲师信息, json格式',
    `status`             varchar(10)                                null comment '审核状态（10001:审核未通过 10002审核通过 10003已提交 10004未提交）',
    `date_created`       datetime(3)   default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    `date_updated`       datetime(3)   default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    `is_free`            tinyint                                    not null comment '是否免费(1: 免费, 0: 收费)',
    `price`              double(11, 2) DEFAULT NULL COMMENT '现价',
    `original_price`     double(11, 2) DEFAULT NULL COMMENT '原价',
    `valid_days`         int           DEFAULT NULL COMMENT '课程有效期天数',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT ='课程预发布表';
