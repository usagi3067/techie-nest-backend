package com.dango.media.controller;

import com.dango.media.model.dto.QueryMediaParamsDto;
import com.dango.media.model.dto.UploadFileParamsDto;
import com.dango.media.model.dto.UploadFileResultDto;
import com.dango.media.model.entity.MediaFiles;
import com.dango.media.service.MediaFilesService;
import com.dango.model.BaseResponse;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * @author dango
 * @description
 * @date 2024-03-12
 */
@RestController
@Api(tags = " 媒资文件接口", value = "媒资文件接口")
public class MediaFileController {

    @Resource
    private MediaFilesService mediaFileService;

    @ApiOperation("媒资列表查询接口")
    @PostMapping("/files")
    public BaseResponse<PageResult<MediaFiles>> list(PageParams pageParams, @RequestBody QueryMediaParamsDto queryMediaParamsDto){
        Long companyId = 1234L;
        PageResult<MediaFiles> mediaFilesPageResult = mediaFileService.queryMediaFiles(companyId, pageParams, queryMediaParamsDto);
        return ResultUtils.success(mediaFilesPageResult);
    }

    @ApiOperation("上传文件")
    @RequestMapping(value = "/upload/course-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BaseResponse<UploadFileResultDto> upload(@RequestPart("file-data") MultipartFile upload,
                                      @RequestParam(value = "folder", required = false) String folder,
                                      @RequestParam(value = "objectName", required = false) String objectName) throws IOException {
        Long companyId = 1234L;
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        //文件大小
        uploadFileParamsDto.setFileSize(upload.getSize());
        //图片
        uploadFileParamsDto.setFileType("001001");
        //文件名称
        uploadFileParamsDto.setFilename(upload.getOriginalFilename());//文件名称
        //文件大小
        long fileSize = upload.getSize();
        uploadFileParamsDto.setFileSize(fileSize);
        //创建临时文件
        File tempFile = File.createTempFile("minio", "temp");
        //上传的文件拷贝到临时文件
        upload.transferTo(tempFile);
        //文件路径
        String absolutePath = tempFile.getAbsolutePath();
        //上传文件
        UploadFileResultDto uploadFileResultDto = mediaFileService.uploadFile(companyId, uploadFileParamsDto, absolutePath, objectName);
        return ResultUtils.success(uploadFileResultDto);
    }

}
