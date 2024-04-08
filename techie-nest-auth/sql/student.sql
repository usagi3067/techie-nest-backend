drop table if exists student;
create table student
(
    id      varchar(32) not null
        primary key,
    hobby   varchar(64) null comment '爱好'
)