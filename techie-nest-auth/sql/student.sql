create table student
(
    id      varchar(32) not null
        primary key,
    user_id varchar(32) not null comment '用户id',
    hobby   varchar(64) null comment '爱好'
)