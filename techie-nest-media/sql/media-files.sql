-- auto-generated definition
use media;
DROP table if exists media_files;
create table media_files
(
    id            varchar(32)                              not null comment '文件id,md5值'
        primary key,
    lecturer_id   bigint                                   null comment '讲师ID',
    lecturer_name varchar(256)                             null comment '讲师名称',
    filename      varchar(256)                             not null comment '文件名称',
    file_type     varchar(10)                              null comment '文件类型（图片、文档，视频）300001:图片 300002: 视频 30003:其他',
    bucket        varchar(128)                             null comment '存储目录',
    file_path     varchar(512)                             null comment '存储路径',
    file_id       varchar(32)                              not null comment '文件id',
    url           varchar(1024)                            null comment '媒资文件访问地址',
    date_created  datetime(3) default CURRENT_TIMESTAMP(3) not null comment '创建时间',
    date_updated  datetime(3) default CURRENT_TIMESTAMP(3) not null on update CURRENT_TIMESTAMP(3) comment '更新时间',
    remark        varchar(1024)                            null comment '备注',
    audit_status  varchar(10) default '100002'             null comment '审核状态',
    audit_mind    varchar(255)                             null comment '审核意见',
    file_size     bigint                                   null comment '文件大小',
    constraint unique_file_id
        unique (file_id) comment '文件id唯一索引 '
) comment '媒资信息';

