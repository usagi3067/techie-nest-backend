drop table if exists `course_market`;

CREATE TABLE `course_market`
(
    `id`             bigint      NOT NULL COMMENT '主键，课程id',
    `charge`         varchar(32) NOT NULL COMMENT '收费规则，对应数据字典',
    `price`          float(10, 2) DEFAULT NULL COMMENT '现价',
    `original_price` float(10, 2) DEFAULT NULL COMMENT '原价',
    `qq`             varchar(32)  DEFAULT NULL COMMENT '咨询qq',
    `wechat`         varchar(64)  DEFAULT NULL COMMENT '微信',
    `phone`          varchar(32)  DEFAULT NULL COMMENT '电话',
    `valid_days`     int          DEFAULT NULL COMMENT '有效期天数',
    PRIMARY KEY (`id`)
) COMMENT ='课程营销信息';