drop table if exists `course_market`;

create table course_market
(
    id             bigint          not null comment '主键，课程id'
        primary key,
    is_free        tinyint         not null comment '是否免费(1: 免费, 0: 收费)',
    original_price float(10, 2)    null comment '原价',
    price          float(10, 2)    null comment '现价',
    qq             varchar(32)     null comment '咨询qq',
    wechat         varchar(64)     null comment '微信',
    phone          varchar(32)     null comment '电话',
    valid_days     int default 365 null comment '有效期天数'
)
    comment '课程营销信息';