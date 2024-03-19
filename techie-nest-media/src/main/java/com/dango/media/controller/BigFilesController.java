package com.dango.media.controller;

import com.dango.media.model.dto.UploadFileParamsDto;
import com.dango.media.service.MediaFilesService;
import com.dango.model.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * 文件上传控制器，负责大文件的上传处理
 * @author dango
 * @description 实现大文件的上传前检查、分块上传、分块检查及文件的最终合并
 * @date 2024-03-13
 */
@RestController
@Api(tags = "大文件上传接口")
public class BigFilesController {
    @Resource
    private MediaFilesService mediaFilesService;

    /**
     * 检查文件是否已存在
     * @param fileMd5 文件的MD5值，用于唯一标识文件
     * @return 文件是否已存在
     * @throws Exception 处理请求时发生异常
     */
    @ApiOperation(value = "文件上传前检查")
    @PostMapping("/upload/check-file")
    public RestResponse<Boolean> checkFile(@RequestParam("fileMd5") String fileMd5) throws Exception {
        // 逻辑实现
        return mediaFilesService.checkFile(fileMd5);
    }

    /**
     * 检查分块文件是否已上传
     * @param fileMd5 文件的MD5值
     * @param chunk 当前分块的序号
     * @return 分块是否已存在
     * @throws Exception 处理请求时发生异常
     */
    @ApiOperation(value = "分块文件上传前检查")
    @PostMapping("/upload/check-chunk")
    public RestResponse<Boolean> checkChunk(@RequestParam("fileMd5") String fileMd5, @RequestParam("chunk") int chunk) throws Exception {
        // 逻辑实现
        return mediaFilesService.checkChunk(fileMd5, chunk);
    }

    /**
     * 上传文件分块
     * @param file 分块文件
     * @param fileMd5 文件的MD5值
     * @param chunk 当前分块的序号
     * @return 上传结果
     * @throws Exception 处理请求时发生异常
     */
    @ApiOperation(value = "上传文件分块")
    @PostMapping("/upload/upload-chunk")
    public RestResponse uploadChunk(@RequestParam("file") MultipartFile file, @RequestParam("fileMd5") String fileMd5, @RequestParam("chunk") int chunk) throws Exception {
        // 逻辑实现
        //创建临时文件
        File tempFile = File.createTempFile("minio", "temp");
        //上传的文件拷贝到临时文件
        file.transferTo(tempFile);
        //文件路径
        String absolutePath = tempFile.getAbsolutePath();
        return mediaFilesService.uploadChunk(fileMd5,chunk,absolutePath);
    }

    /**
     * 合并所有分块文件
     * @param fileMd5 文件的MD5值，用于验证文件完整性
     * @param fileName 最终文件的名称
     * @param chunkTotal 分块的总数
     * @return 合并结果
     * @throws Exception 处理请求时发生异常
     */
    @ApiOperation(value = "合并文件分块")
    @PostMapping("/upload/merge-chunks")
    public RestResponse<Boolean> mergeChunks(@RequestParam("fileMd5") String fileMd5, @RequestParam("fileName") String fileName, @RequestParam("chunkTotal") int chunkTotal) throws Exception {
        long companyId = 1234L;
        //文件信息对象
        UploadFileParamsDto uploadFileParamsDto = new UploadFileParamsDto();
        uploadFileParamsDto.setFilename(fileName);
        uploadFileParamsDto.setTags("视频文件");
        uploadFileParamsDto.setFileType("001002");
        return mediaFilesService.mergechunks(companyId, fileMd5, chunkTotal, uploadFileParamsDto);
    }
}

