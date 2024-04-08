DROP TABLE IF EXISTS `course_publish`;

-- auto-generated definition
create table course_publish
(
    id                   bigint        not null comment '主键'
        primary key,
    name                 varchar(512)  not null comment '课程名称',
    tags                 varchar(1024) null comment '标签(json格式)',
    lecturer_id          bigint        not null comment '讲师ID',
    lecturer_name        varchar(512)  null comment '讲师名称',
    main_category        varchar(512)  not null comment '大分类',
    `main_category_name` varchar(512)  NOT NULL COMMENT '主分类名称',
    `sub_category`       varchar(512)  NOT NULL COMMENT '次分类',
    `sub_category_name`  varchar(512)  NOT NULL COMMENT '次分类名称',
    pic                  varchar(1024)  not null comment '课程图片',
    description          text          null comment '课程介绍',
    `pre_knowledge`      text          null comment '预备知识',
    market               text          null comment '课程营销信息，json格式',
    teach_plan           text          null comment '所有课程计划，json格式',
    lecturer_info        text          null comment '讲师信息',
    `publish_date`       datetime      NOT NULL COMMENT '发布时间',
    offline_date         datetime      null comment '下架时间',
    status               varchar(10)   null comment '发布状态(20001:未发布, 20002:已发布, 20003:下线)',
    remark               varchar(512)  null comment '备注',
    is_free              tinyint       not null comment '是否免费(1: 免费, 0: 收费)',
    price                double(11, 2) null comment '现价',
    original_price       double(11, 2) null comment '原价',
    valid_days           int           null comment '课程有效期天数',
    `count_buy`          int           NOT NULL DEFAULT 0 COMMENT '购买人数',
    `count_study`        int           NOT NULL DEFAULT 0 COMMENT '学习人数'
)
    comment '课程发布';


SELECT id,name,tags,lecturer_id,lecturer_name,main_category,main_category_name,sub_category,sub_category_name,pic,description,pre_knowledge,market,teach_plan,lecturer_info,publish_date,offline_date,status,remark,is_free,price,original_price,valid_days,count_buy,count_study
FROM course_publish
WHERE (is_free = 0) ORDER BY count_study DESC
LIMIT 10;