DROP TABLE if exists user;
create table user
(
    id          bigint                             not null
        primary key,
    username    varchar(45)                              not null,
    password    varchar(96)                              null,
    wx_union_id  varchar(128)                             null comment '微信unionid',
    user_pic    varchar(255)                             null comment '头像',
    u_type      tinyint                              not null COMMENT '1为学生， 2为老师， 3为管理员',
    birthday    datetime                                 null,
    sex         char                                     null,
    email       varchar(45)                              null,
    cellphone   varchar(45)                              null,
    qq          varchar(32)                              null,
    status      tinyint                              not null comment '用户状态',
    create_time datetime(3) default CURRENT_TIMESTAMP(3) not null,
    update_time datetime(3) default CURRENT_TIMESTAMP(3) null,
    constraint unique_user_username
        unique (username)
);
