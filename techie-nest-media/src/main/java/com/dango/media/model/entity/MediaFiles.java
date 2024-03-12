package com.dango.media.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

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
     * 机构ID
     */
    @TableField(value = "company_id")
    private Long companyId;

    /**
     * 机构名称
     */
    @TableField(value = "company_name")
    private String companyName;

    /**
     * 文件名称
     */
    @TableField(value = "filename")
    private String filename;

    /**
     * 文件类型（图片、文档，视频）
     */
    @TableField(value = "file_type")
    private String fileType;

    /**
     * 标签
     */
    @TableField(value = "tags")
    private String tags;

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
     * 上传人
     */
    @TableField(value = "username")
    private String username;

    /**
     * 上传时间
     */
    @TableField(value = "create_date")
    private LocalDateTime createDate;

    /**
     * 修改时间
     */
    @TableField(value = "change_date")
    private LocalDateTime changeDate;

    /**
     * 状态,1:正常，0:不展示
     */
    @TableField(value = "status")
    private String status;

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