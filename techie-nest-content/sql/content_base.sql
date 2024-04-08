-- auto-generated definition
drop table if exists course_base;
-- auto-generated definition
create table course_base
(
    id             bigint auto_increment comment '主键'
        primary key,
    name           varchar(512)                              not null comment '课程名称',
    lecturer_name  varchar(512) default ''                   not null comment '讲师名称',
    lecturer_id    bigint                                    not null comment '讲师id',
    tags           varchar(1024)                             null comment '课程标签(json数组)',
    main_category  varchar(512)                              not null comment '主分类',
    sub_category   varchar(512)                              not null comment '子分类',
    description    text                                      not null comment '课程介绍',
    pre_knowledge  varchar(1024)                             null comment '预备知识',
    pic            varchar(1024)                             null comment '课程图片',
    date_created   datetime(3)  default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    date_updated   datetime(3)  default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    audit_status   varchar(10)                               null comment '审核状态（10001:审核未通过 10002审核通过 10003已提交 10004未提交）',
    publish_status varchar(10)  default '20001'              null comment '发布状态(20001:未发布, 20002:已发布, 20003:下线)'
)
    comment '课程基本信息表';


SELECT *
FROM menu
WHERE id IN (SELECT menu_id FROM permission WHERE role_id IN (SELECT role_id FROM user_role WHERE user_id = 50));

SELECT * FROM permission WHERE role_id IN (SELECT role_id FROM user_role WHERE user_id = 50)
(SELECT * FROM user_role WHERE user_id = 50)