drop table if exists lecturer;
create table lecturer
(
    id      varchar(32)   not null
        primary key,
    name    varchar(64)   not null comment '称呼',
    intro   varchar(512)  null comment '个人简介',
    resume  varchar(1024) null comment '个人简历',
    pic     varchar(128)  null comment '讲师照片'
)