-- auto-generated definition
drop table if exists course_base;
create table course_base
(
    id           bigint auto_increment primary key comment '主键',
    name         varchar(512)                              not null comment '课程名称',
    instructor_name varchar(512)                              not null default '' comment '讲师名称',
    instructor_id   bigint                                    not null comment '讲师id',
    users        varchar(500)                              not null comment '适用人群',
    tags         varchar(500) default null comment '课程标签',
    mt           varchar(500)                              not null comment '大分类',
    st           varchar(500)                              not null comment '小分类',
    grade        varchar(500)                              not null comment '课程等级',
    teach_mode   varchar(500)                              not null comment '授课模式(record录播, live直播(暂不支持))',
    description  text                                      not null comment '课程介绍',
    pic          varchar(500)                              null comment '课程图片',
    date_created datetime(3)  default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    date_updated datetime(3)  default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    created_by   varchar(50)  default 'sys'                not null comment '创建人',
    updated_by   varchar(50)  default 'sys'                not null comment '更新人',
    is_delete    tinyint      default 0                    not null comment '是否删除',
    audit_status varchar(10)                               null comment '审核状态（**审核未通过** **审核通过****已提交****未提交**）',
    status       varchar(10)  default '1'                  null comment '发布状态(未发布, 已发布, 下线)'
)
    comment '课程基本信息表';


SELECT *
FROM menu
WHERE id IN (SELECT menu_id FROM permission WHERE role_id IN (SELECT role_id FROM user_role WHERE user_id = 50));

SELECT * FROM permission WHERE role_id IN (SELECT role_id FROM user_role WHERE user_id = 50)
(SELECT * FROM user_role WHERE user_id = 50)