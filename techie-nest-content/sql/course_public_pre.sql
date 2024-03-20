DROP TABLE IF EXISTS `course_publish_pre`;

CREATE TABLE `course_publish_pre`
(
    `id`             bigint       NOT NULL COMMENT '主键',
    `company_id`     bigint       NOT NULL COMMENT '机构ID',
    `company_name`   varchar(255) DEFAULT NULL COMMENT '公司名称',
    `name`           varchar(100) NOT NULL COMMENT '课程名称',
    `users`          varchar(500) NOT NULL COMMENT '适用人群',
    `tags`           varchar(32)  DEFAULT NULL COMMENT '标签',
    `username`       varchar(32)  DEFAULT NULL COMMENT '创建人',
    `mt`             varchar(20)  NOT NULL COMMENT '大分类',
    `mt_name`        varchar(255) NOT NULL COMMENT '大分类名称',
    `st`             varchar(20)  NOT NULL COMMENT '小分类',
    `st_name`        varchar(255) NOT NULL COMMENT '小分类名称',
    `grade`          varchar(32)  NOT NULL COMMENT '课程等级',
    `teach_mode`      varchar(32)  NOT NULL COMMENT '教育模式',
    `pic`            varchar(500) NOT NULL COMMENT '课程图片',
    `description`    text COMMENT '课程介绍',
    `market`         text COMMENT '课程营销信息，json格式',
    `teach_plan`      text COMMENT '所有课程计划，json格式',
    `teachers`       text COMMENT '教师信息，json格式',
    `create_date`    datetime     DEFAULT NULL COMMENT '提交时间',
    `audit_date`     datetime     DEFAULT NULL COMMENT '审核时间',
    `status`         varchar(10)  DEFAULT '1' COMMENT '状态',
    `remark`         varchar(500) DEFAULT NULL COMMENT '备注',
    `charge`         varchar(32)  DEFAULT NULL COMMENT '收费规则，对应数据字典--203',
    `price`          float(10, 2) DEFAULT NULL COMMENT '现价',
    `original_price` float(10, 2) DEFAULT NULL COMMENT '原价',
    `valid_days`     int          DEFAULT NULL COMMENT '课程有效期天数',
    PRIMARY KEY (`id`) USING BTREE
) COMMENT ='课程发布';
