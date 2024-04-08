package com.dango.media.model.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author dango
 * @description
 * @date 2024-03-12
 */
@Data
@Api(value = "upload_file_result", tags = "上传文件返回结果")
public class UploadFileResultDto implements Serializable {
    @ApiModelProperty(value = "文件id,md5值")
    private String id;

    @ApiModelProperty(value = "讲师ID")
    private Long lecturerId;

        @ApiModelProperty(value = "讲师名称")
    private String lecturerName;

        @ApiModelProperty(value = "文件名称")
    private String filename;

        @ApiModelProperty(value = "文件类型（图片、文档，视频）")
    private String fileType;

        @ApiModelProperty(value = "标签")
    private String tags;

        @ApiModelProperty(value = "存储目录")
    private String bucket;

        @ApiModelProperty(value = "存储路径")
    private String filePath;

        @ApiModelProperty(value = "文件id")
    private String fileId;

        @ApiModelProperty(value = "媒资文件访问地址")
    private String url;

        @ApiModelProperty(value = "上传人")
    private String username;

        @ApiModelProperty(value = "上传时间")
    private LocalDateTime createDate;

        @ApiModelProperty(value = "修改时间")
    private LocalDateTime changeDate;

        @ApiModelProperty(value = "状态,1:正常，0:不展示")
    private String status;

        @ApiModelProperty(value = "备注")
    private String remark;

        @ApiModelProperty(value = "审核状态")
    private String auditStatus;

        @ApiModelProperty(value = "审核意见")
    private String auditMind;

        @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
