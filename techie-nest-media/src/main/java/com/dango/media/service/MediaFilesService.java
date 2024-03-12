package com.dango.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.media.model.dto.QueryMediaParamsDto;
import com.dango.media.model.dto.UploadFileParamsDto;
import com.dango.media.model.dto.UploadFileResultDto;
import com.dango.media.model.entity.MediaFiles;
import com.dango.model.PageParams;
import com.dango.model.PageResult;

/**
* @author dango
* @description 针对表【media_files(媒资信息)】的数据库操作Service
* @createDate 2024-03-12 12:44:46
*/
public interface MediaFilesService extends IService<MediaFiles> {
    /**
     * 上传文件
     * @param companyId 机构id
     * @param uploadFileParamsDto 文件信息
     * @param localFilePath 文件本地路径
     * @return UploadFileResultDto
     */
    UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath);

    /**
     * 查询媒资文件列表
     * @param companyId
     * @param pageParams
     * @param queryMediaParamsDto
     * @return
     */
    PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto);

    /**
     * 添加媒资文件到数据库(事务控制（
     * @param localFilePath
     * @param mimeType
     * @param bucket
     * @param objectName
     * @return
     */
    boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName);
}
