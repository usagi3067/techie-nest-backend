create table company
(
    id   BIGINT       not null
        primary key auto_increment,
    name varchar(255) not null comment "机构名称"
) COMMENT '机构表';