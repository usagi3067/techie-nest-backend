package com.dango.media.controller;

import com.dango.exception.BusinessException;
import com.dango.media.model.entity.MediaFiles;
import com.dango.media.service.MediaFilesService;
import com.dango.model.BaseResponse;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dango
 * @description
 * @date 2024-03-20
 */
@Api(value = "媒资文件管理接口", tags = "媒资文件管理接口")
@RestController
@RequestMapping("/open")
public class MediaOpenController {

    @Autowired
    private MediaFilesService mediaFilesService;

    /**
     * 预览文件
     *
     * @param mediaId 媒体文件ID
     * @return RestResponse 包含媒体文件播放链接
     */
    @ApiOperation("预览文件")
    @GetMapping("/preview/{mediaId}")
    public BaseResponse<String> getPlayUrlByMediaId(@PathVariable String mediaId) {
        MediaFiles mediaFiles = mediaFilesService.getFileById(mediaId);
        if (mediaFiles == null || StringUtils.isEmpty(mediaFiles.getUrl())) {
            throw new BusinessException("视频还没有转码处理");
        }
        return ResultUtils.success(mediaFiles.getUrl());
    }
}
