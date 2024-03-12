package com.dango.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.exception.BusinessException;
import com.dango.media.mapper.MediaFilesMapper;
import com.dango.media.model.dto.QueryMediaParamsDto;
import com.dango.media.model.dto.UploadFileParamsDto;
import com.dango.media.model.dto.UploadFileResultDto;
import com.dango.media.model.entity.MediaFiles;
import com.dango.media.service.MediaFilesService;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.MinioClient;
import io.minio.UploadObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 媒资信息服务实现类
 * 主要用于执行媒资信息表（media_files）的数据库操作
 * @author dango
 * @createDate 2024-03-12 12:44:46
 */
@Slf4j
@Service
public class MediaFilesServiceImpl extends ServiceImpl<MediaFilesMapper, MediaFiles>
        implements MediaFilesService {

    // 注入MinIO客户端
    @Resource
    private MinioClient minioClient;

    @Resource
    private MediaFilesService currentProxy;

    // 从配置文件中获取文件存储桶名
    @Value("${minio.bucket.files}")
    private String bucketFiles;

    /**
     * 上传文件
     * @param companyId 公司ID
     * @param uploadFileParamsDto 上传文件的参数
     * @param localFilePath 文件在本地的路径
     * @return UploadFileResultDto 上传文件结果的数据传输对象
     */
    @Override
    public UploadFileResultDto uploadFile(Long companyId, UploadFileParamsDto uploadFileParamsDto, String localFilePath) {
        File file = new File(localFilePath);
        // 检查文件是否存在
        if (!file.exists()) {
            throw new BusinessException("文件不存在");
        }

        // 获取文件相关信息
        String filename = uploadFileParamsDto.getFilename();
        String extension = filename.substring(filename.lastIndexOf("."));
        String mimeType = getMimeType(extension);
        String fileMd5 = getFileMd5(file);
        String defaultFolderPath = getDefaultFolderPath();

        // 构造在MinIO中的存储路径
        String objectName = defaultFolderPath + fileMd5 + extension;
        // 上传文件到MinIO并获取上传∞结果
        currentProxy.addMediaFilesToMinIO(localFilePath, mimeType, bucketFiles, objectName);

        // 设置文件大小并保存文件信息到数据库
        uploadFileParamsDto.setFileSize(file.length());
        MediaFiles mediaFiles = addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketFiles, objectName);

        // 构造返回结果
        UploadFileResultDto uploadFileResultDto = new UploadFileResultDto();
        BeanUtils.copyProperties(mediaFiles, uploadFileResultDto);
        return uploadFileResultDto;
    }

    @Override
    public PageResult<MediaFiles> queryMediaFiles(Long companyId, PageParams pageParams, QueryMediaParamsDto queryMediaParamsDto) {
        //构建查询条件对象
        LambdaQueryWrapper<MediaFiles> queryWrapper = new LambdaQueryWrapper<>();
        //分页对象
        Page<MediaFiles> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        // 查询数据内容获得结果
        Page<MediaFiles> pageResult = baseMapper.selectPage(page, queryWrapper);
        // 获取数据列表
        List<MediaFiles> list = pageResult.getRecords();
        // 获取数据总数
        long total = pageResult.getTotal();
        // 构建结果集
        return new PageResult<>(list, total, pageParams.getPageNo(), pageParams.getPageSize());

    }

    /**
     * 将文件信息保存到数据库
     * @param companyId 公司ID
     * @param fileMd5 文件的MD5值
     * @param uploadFileParamsDto 上传文件的参数
     * @param bucket 存储桶名称
     * @param objectName 文件在存储桶中的路径
     * @return MediaFiles 媒资信息实体
     */
    public MediaFiles addMediaFilesToDb(Long companyId, String fileMd5, UploadFileParamsDto uploadFileParamsDto, String bucket, String objectName) {
        // 查询文件是否已存在
        MediaFiles mediaFiles = baseMapper.selectById(fileMd5);
        if (Objects.isNull(mediaFiles)) {
            mediaFiles = new MediaFiles();
            BeanUtils.copyProperties(uploadFileParamsDto, mediaFiles);
            mediaFiles.setId(fileMd5);
            mediaFiles.setFileId(fileMd5);
            mediaFiles.setCompanyId(companyId);
            mediaFiles.setUrl("/" + bucket + "/" + objectName);
            mediaFiles.setBucket(bucket);
            mediaFiles.setFilePath(objectName);
            mediaFiles.setCreateDate(LocalDateTime.now());
            // 这里设置初始审核状态和文件状态，可根据实际业务调整
            mediaFiles.setAuditStatus("002003"); // 待审核
            mediaFiles.setStatus("1"); // 正常状态

            // 插入媒资信息到数据库
            int insert = baseMapper.insert(mediaFiles);
            if (insert <= 0) {
                log.error("保存文件信息到数据库失败, {}", mediaFiles);
                throw new BusinessException("保存文件信息失败");
            }
            log.info("保存文件信息到数据库成功，{}", mediaFiles);
        }
        return mediaFiles;
    }

    /**
     * 获取文件的MD5值
     * @param file 文件对象
     * @return String 文件的MD5字符串
     */
    private String getFileMd5(File file) {
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            return DigestUtils.md5Hex(fileInputStream);
        } catch (Exception e) {
            log.error("计算文件MD5出错", e);
            return null;
        }
    }

    /**
     * 获取默认的文件存储路径
     * @return String 默认的文件路径，格式为: yyyy/MM/dd/
     */
    private String getDefaultFolderPath() {
        return new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
    }

    /**
     * 根据文件扩展名获取MIME类型
     * @param extension 文件扩展名
     * @return String 文件的MIME类型
     */
    private String getMimeType(String extension) {
        if (Objects.isNull(extension)) {
            extension = "";
        }
        ContentInfo extensionMatch = ContentInfoUtil.findExtensionMatch(extension);
        return Objects.isNull(extensionMatch) ? MediaType.APPLICATION_OCTET_STREAM_VALUE : extensionMatch.getMimeType();
    }

    /**
     * 将文件上传至MinIO
     * @param localFilePath 文件在本地的路径
     * @param mimeType 文件的MIME类型
     * @param bucket 存储桶名称
     * @param objectName 文件在存储桶中的路径
     * @return boolean 上传是否成功
     */
    @Transactional
    @Override
    public boolean addMediaFilesToMinIO(String localFilePath, String mimeType, String bucket, String objectName) {
        try {
            UploadObjectArgs uploadObjectArgs = UploadObjectArgs.builder()
                    .bucket(bucket) // 桶
                    .filename(localFilePath) // 指定本地文件路径
                    .object(objectName) // 对象名 在桶下存储该文件
                    .contentType(mimeType) // 设置媒体文件类型
                    .build();
            log.info("上传minio成功，bucket:{}, objectName:{}", bucket, objectName);
            minioClient.uploadObject(uploadObjectArgs);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传minio出错， bucket:{}, objectName:{}, 原因: {}", bucket, objectName, e.getMessage(), e);
            return false;
        }
    }
}




