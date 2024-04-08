-- auto-generated definition
use learning;
drop table if exists choose_course;
create table choose_course
(
    id               bigint auto_increment comment '主键'
        primary key,
    course_id        bigint                                   not null comment '课程id',
    course_name      varchar(512)                              not null comment '课程名称',
    user_id          bigint                                   not null comment '用户id',
    lecturer_id      bigint                                   not null comment '讲师id',
    is_free          tinyint                                  not null comment '1:免费 0： 收费',
    pic              varchar(1024)                             not null comment '课程图片',
    date_created     datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    date_updated     datetime(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    course_price     double(11, 2)                            not null comment '课程价格',
    valid_days       int                                      not null comment '课程有效期(天)',
    status           varchar(10)                              not null comment '选课状态 400001:选课成功 400002:待支付 400003：已过期  ',
    valid_time_start datetime                                 not null comment '开始服务时间',
    valid_time_end   datetime                                 not null comment '结束服务时间',
    remarks          varchar(1024)                             null comment '备注'
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;


# //701001: 选课成功， 701002: 待支付，702003： 已过期需要申请续期或重新支付