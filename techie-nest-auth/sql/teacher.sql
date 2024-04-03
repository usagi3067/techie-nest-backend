create table teacher
(
    id      varchar(32)   not null
        primary key,
    user_id varchar(32)   not null comment '用户id',
    name    varchar(64)   not null comment '称呼',
    intro   varchar(512)  null comment '个人简介',
    resume  varchar(1024) null comment '个人简历',
    pic     varchar(128)  null comment '老师照片'
)