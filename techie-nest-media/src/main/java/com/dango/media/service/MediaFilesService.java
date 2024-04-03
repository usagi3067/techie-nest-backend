package com.dango.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.media.model.dto.QueryMediaParamsDto;
import com.dango.media.model.dto.UploadFileParamsDto;
import com.dango.media.model.dto.UploadFileResultDto;
import com.dango.media.model.entity.MediaFiles;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;

/**
 * @author dango
 * @description 针对表【media_files(媒资信息)】的数据库操作Service
 * @createDate 2024-03-12 12:44:46
 */
public interface MediaFilesService extends IService<MediaFiles> {
    /**
     * 上传文件
     *
     * @param companyId           机构id
     * @param uploadFileParamsDto 文件信息
     * @param localFilePath       文件本地路径
     * @param ObjectName          对象名
     * @return UploadFileResultDto
     */
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath, String ObjectName);

    /**
     * 查询媒资文件列表
     *
     * @param companyId
     * @param pageParams
     * @param queryMediaParamsDto
     * @return
     */
    PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

    /**
     * 添加媒资文件到数据库
     *
     * @param companyId           机构 id
     * @param fileMd5             文件 md5值
     * @param uploadFileParamsDto 文件信息
     * @param bucket              存储桶
     * @param objectName          对象名
     * @return
     */
    @Transactional
    MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName);


    boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName);

    /**
     * @param fileMd5 文件的md5
     * @return RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查文件是否存在
     */
    Boolean checkFile(String fileMd5);

    /**
     * @param fileMd5    文件的md5
     * @param chunkIndex 分块序号
     * @return RestResponse<java.lang.Boolean> false不存在，true存在
     * @description 检查分块是否存在
     */
    Boolean checkChunk(String fileMd5, int chunkIndex);

    /**
     * @param fileMd5            文件md5
     * @param chunk              分块序号
     * @param localChunkFilePath 分块文件本地路径
     * @return BaseResponse
     * @description 上传分块
     */
    Boolean uploadChunk(String fileMd5, int chunk, String localChunkFilePath);


    Boolean mergechunks(long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto);

    /**
     * 从minio下载文件
     * @param bucket 桶
     * @param objectName 对象名称
     * @return 下载后的文件
     */
    public File downloadFileFromMinIO(String bucket, String objectName);


    MediaFiles getFileById(String mediaId);
}
