package com.dango.media.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 媒资信息
 * @TableName media_files
 */
@TableName(value ="media_files")
@Data
public class MediaFiles implements Serializable {
    /**
     * 文件id,md5值
     */
    @TableId(value = "id")
    private String id;

    /**
     * 讲师ID
     */
    @TableField(value = "lecturer_id")
    private Long lecturerId;

    /**
     * 讲师名称
     */
    @TableField(value = "lecturer_name")
    private String lecturerName;

    /**
     * 文件名称
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 文件类型（图片、文档，视频）300001:图片 300002: 视频 30003:其他
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 存储目录
     */
    @TableField(value = "bucket")
    private String bucket;

    /**
     * 存储路径
     */
    @TableField(value = "file_path")
    private String filePath;

    /**
     * 文件id
     */
    @TableField(value = "file_id")
    private String fileId;

    /**
     * 媒资文件访问地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 创建时间
     */
    @TableField(value = "date_created")
    private LocalDateTime dateCreated;

    /**
     * 更新时间
     */
    @TableField(value = "date_updated")
    private LocalDateTime dateUpdated;

    /**
     * 备注
     */
    @TableField(value = "remark")
    private String remark;

    /**
     * 审核状态
     */
    @TableField(value = "audit_status")
    private String auditStatus;

    /**
     * 审核意见
     */
    @TableField(value = "audit_mind")
    private String auditMind;

    /**
     * 文件大小
     */
    @TableField(value = "file_size")
    private Long fileSize;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}