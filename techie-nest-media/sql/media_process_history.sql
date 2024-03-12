DROP TABLE IF EXISTS `media_process_history`;

CREATE TABLE `media_process_history`
(
    `id`          bigint        NOT NULL AUTO_INCREMENT,
    `file_id`     varchar(120)  NOT NULL COMMENT '文件标识',
    `filename`    varchar(255)  NOT NULL COMMENT '文件名称',
    `bucket`      varchar(128)  NOT NULL COMMENT '存储源',
    `status`      varchar(12)   NOT NULL COMMENT '状态,1:未处理，2：处理成功  3处理失败',
    `create_date` datetime      NOT NULL COMMENT '上传时间',
    `finish_date` datetime      NOT NULL COMMENT '完成时间',
    `url`         varchar(1024) NOT NULL COMMENT '媒资文件访问地址',
    `fail_count`  int           DEFAULT 0 COMMENT '失败次数',
    `file_path`   varchar(512)  DEFAULT NULL COMMENT '文件路径',
    `error_msg`    varchar(1024) DEFAULT NULL COMMENT '失败原因',
    PRIMARY KEY (`id`) USING BTREE
) comment '媒资处理历史表';
INSERT INTO `media_process_history` (`id`, `file_id`, `filename`, `bucket`, `status`, `create_date`, `finish_date`,
                                     `url`, `file_path`, `error_msg`)
VALUES (1, '81d7ed5153316f5774885d3b4c07d8bc', 'Spring Security快速上手-创建工程.avi', 'video', '2',
        '2022-12-15 09:41:50', '2022-12-15 10:30:26',
        '/video/8/1/81d7ed5153316f5774885d3b4c07d8bc/81d7ed5153316f5774885d3b4c07d8bc.mp4',
        '8/1/81d7ed5153316f5774885d3b4c07d8bc/81d7ed5153316f5774885d3b4c07d8bc.avi', NULL),
       (2, '18f919e23bedab97a78762c4875addc4', '垂直分库-插入和查询测试.avi', 'video', '2', '2022-12-15 09:45:18',
        '2022-12-15 10:30:11', '/video/1/8/18f919e23bedab97a78762c4875addc4/18f919e23bedab97a78762c4875addc4.mp4',
        '1/8/18f919e23bedab97a78762c4875addc4/18f919e23bedab97a78762c4875addc4.avi', NULL),
       (3, 'efd2eacc4485946fc27feb0caef7506c', '读写分离-理解读写分离.avi', 'video', '2', '2022-12-15 09:45:19',
        '2022-12-15 10:31:04', '/video/e/f/efd2eacc4485946fc27feb0caef7506c/efd2eacc4485946fc27feb0caef7506c.mp4',
        'e/f/efd2eacc4485946fc27feb0caef7506c/efd2eacc4485946fc27feb0caef7506c.avi', NULL);