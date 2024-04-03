DROP TABLE user;
create table user
(
    id          varchar(64)                              not null
        primary key,
    username    varchar(45)                              not null,
    password    varchar(96)                              null,
    wx_union_id  varchar(128)                             null comment '微信unionid',
    user_pic    varchar(255)                             null comment '头像',
    company_id  varchar(32)                              null,
    u_type      int                              not null COMMENT '1为学生， 2未用户',
    birthday    datetime                                 null,
    sex         char                                     null,
    email       varchar(45)                              null,
    cellphone   varchar(45)                              null,
    qq          varchar(32)                              null,
    status      varchar(32)                              not null comment '用户状态',
    create_time datetime(3) default CURRENT_TIMESTAMP(3) not null,
    update_time datetime(3) default CURRENT_TIMESTAMP(3) null,
    constraint unique_user_username
        unique (username)
)