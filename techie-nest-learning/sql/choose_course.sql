## 无论是否收费， 添加选课都要插入该记录。
create table choose_course
(
    id               bigint auto_increment comment '主键'
        primary key,
    course_id        bigint       not null comment '课程id',
    course_name      varchar(32)  not null comment '课程名称',
    user_id          varchar(32)  not null comment '用户id',
    company_id       bigint       not null comment '机构id',
    order_type       varchar(32)  not null comment '选课类型 700001:免费， 700002:收费',
    create_date      datetime     not null comment '添加时间',
    course_price     float(10, 2) not null comment '课程价格',
    valid_days       int          not null comment '课程有效期(天)',
    status           varchar(32)  not null comment '选课状态 701001: 选课成功， 701002: 待支付，702003： 已过期需要申请续期或重新支付 ',
    valid_time_start datetime     not null comment '开始服务时间',
    valid_time_end   datetime     not null comment '结束服务时间',
    remarks          varchar(255) null comment '备注'
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;