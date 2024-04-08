use learning;
-- auto-generated definition
drop table if exists course_tables;
create table course_tables
(
    id               bigint auto_increment
        primary key,
    choose_course_id bigint       not null comment '选课记录id',
    user_id          bigint       not null comment '用户id',
    course_id        bigint       not null comment '课程id',
    lecturer_id      bigint       not null comment '讲师id',
    course_name      varchar(512) not null comment '课程名称',
    is_free          tinyint      not null comment '是否免费(1: 免费, 0: 收费)',
    pic              varchar(500) not null comment '课程图片',
    valid_time_start datetime     null comment '开始服务时间',
    valid_time_end   datetime     not null comment '到期时间',
    remarks          varchar(255) null comment '备注',
    constraint course_tables_unique
        unique (user_id, course_id)
)
    collate = utf8mb4_general_ci
    row_format = DYNAMIC;

