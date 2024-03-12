package com.dango.media.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 媒资处理表
 * @TableName media_process
 */
@TableName(value ="media_process")
@Data
public class MediaProcess implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 文件标识
     */
    @TableField(value = "file_id")
    private String fileId;

    /**
     * 文件名称
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 存储桶
     */
    @TableField(value = "bucket")
    private String bucket;

    /**
     * 存储路径
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 状态,1:未处理，2：处理成功  3处理失败
     */
    @TableField(value = "status")
    private String status;

    /**
     * 上传时间
     */
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 完成时间
     */
    @TableField(value = "finish_date")
    private LocalDateTime finishDate;

    /**
     * 失败次数
     */
    @TableField(value = "fail_count")
    private Integer failCount;

    /**
     * 媒资文件访问地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 失败原因
     */
    @TableField(value = "error_msg")
    private String errorMsg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}