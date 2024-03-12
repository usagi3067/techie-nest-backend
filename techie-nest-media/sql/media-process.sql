use media;
DROP TABLE IF EXISTS `media_process`;
CREATE TABLE `media_process`
(
    `id`          bigint       NOT NULL AUTO_INCREMENT,
    `file_id`     varchar(120) NOT NULL COMMENT '文件标识',
    `filename`    varchar(255) NOT NULL COMMENT '文件名称',
    `bucket`      varchar(128) NOT NULL COMMENT '存储桶',
    `file_path`   varchar(512)  DEFAULT NULL COMMENT '存储路径',
    `status`      varchar(12)  NOT NULL COMMENT '状态,1:未处理，2：处理成功  3处理失败',
    `create_date` datetime     NOT NULL COMMENT '上传时间',
    `finish_date` datetime      DEFAULT NULL COMMENT '完成时间',
    `fail_count`  int           DEFAULT 0 COMMENT '失败次数',
    `url`         varchar(1024) DEFAULT NULL COMMENT '媒资文件访问地址',
    `error_msg`   varchar(1024) DEFAULT NULL COMMENT '失败原因',
    PRIMARY KEY (`id`) USING BTREE,
    UNIQUE KEY `unique_file_id` (`file_id`) USING BTREE
) comment '媒资处理表';



