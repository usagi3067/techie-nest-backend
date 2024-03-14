package com.dango.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.exception.BusinessException;
import com.dango.media.mapper.MediaFilesMapper;
import com.dango.media.mapper.MediaProcessMapper;
import com.dango.media.model.dto.QueryMediaParamsDto;
import com.dango.media.model.dto.UploadFileParamsDto;
import com.dango.media.model.dto.UploadFileResultDto;
import com.dango.media.model.entity.MediaFiles;
import com.dango.media.model.entity.MediaProcess;
import com.dango.media.service.MediaFilesService;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import com.dango.model.RestResponse;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.*;
import io.minio.messages.DeleteError;
import io.minio.messages.DeleteObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 媒资信息服务实现类
 * 主要用于执行媒资信息表（media_files）的数据库操作
 *
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
    private MediaProcessMapper mediaProcessMapper;

    @Autowired
    MediaFilesService currentProxy;

    // 从配置文件中获取文件存储桶名
    @Value("${minio.bucket.files}")
    private String bucketFiles;

    @Value("${minio.bucket.videoFiles}")
    private String bucketVideoFiles;

    /**
     * 上传文件
     *
     * @param companyId           公司ID
     * @param uploadFileParamsDto 上传文件的参数
     * @param localFilePath       文件在本地的路径
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
        // 上传文件到MinIO并获取上传结果
        addMediaFilesToMinIO(localFilePath, mimeType, bucketFiles, objectName);

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
     *
     * @param companyId           公司ID
     * @param fileMd5             文件的MD5值
     * @param uploadFileParamsDto 上传文件的参数
     * @param bucket              存储桶名称
     * @param objectName          文件在存储桶中的路径
     * @return MediaFiles 媒资信息实体
     */
    @Transactional
    @Override
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

            addWaitingTask(mediaFiles);
            log.info("保存媒体文件信息到待处理表成功, mediaFiles={}", mediaFiles);
        }
        return mediaFiles;
    }

    /**
     * 添加媒体文件到待处理表中  目前只支持 avi文件格式
     * @param mediaFiles 媒体文件
     */
    private void addWaitingTask(MediaFiles mediaFiles) {
        //文件名称
        String filename = mediaFiles.getFilename();
        //文件扩展名
        String extension = filename.substring(filename.lastIndexOf("."));
        //文件mimeType
        String mimeType = getMimeType(extension);
        //如果是avi视频添加到视频待处理表
        if(mimeType.equals("video/x-msvideo")){
            MediaProcess mediaProcess = new MediaProcess();
            BeanUtils.copyProperties(mediaFiles,mediaProcess);
            mediaProcess.setStatus("1");//未处理
            mediaProcess.setFailCount(0);//失败次数默认为0
            mediaProcessMapper.insert(mediaProcess);
        }

    }

    /**
     * 获取文件的MD5值
     *
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
     *
     * @return String 默认的文件路径，格式为: yyyy/MM/dd/
     */
    private String getDefaultFolderPath() {
        return new SimpleDateFormat("yyyy/MM/dd/").format(new Date());
    }

    /**
     * 根据文件扩展名获取MIME类型
     *
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
     *
     * @param localFilePath 文件在本地的路径
     * @param mimeType      文件的MIME类型
     * @param bucket        存储桶名称
     * @param objectName    文件在存储桶中的路径
     * @return boolean 上传是否成功
     */
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
    @Override
    public RestResponse<Boolean> checkFile(String fileMd5) {
        // 查询文件信息
        MediaFiles mediaFiles = baseMapper.selectById(fileMd5);
        if (mediaFiles != null) {
            // 尝试获取文件
            try {
                InputStream stream = minioClient.getObject(
                        GetObjectArgs.builder()
                                .bucket(mediaFiles.getBucket())
                                .object(mediaFiles.getFilePath())
                                .build());
                // 如果能够获取到文件流，则文件已存在
                if (stream != null) {
                    return RestResponse.success(true);
                }
            } catch (Exception e) {
                // 日志记录异常，忽略处理以返回文件不存在的结果
            }
        }
        // 文件不存在
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean> checkChunk(String fileMd5, int chunkIndex) {
        // 构造分块文件的路径
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        String chunkFilePath = chunkFileFolderPath + chunkIndex;
        // 尝试获取分块文件
        try {
            InputStream fileInputStream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketVideoFiles)
                            .object(chunkFilePath)
                            .build());
            // 如果能够获取到文件流，则分块已存在
            if (fileInputStream != null) {
                return RestResponse.success(true);
            }
        } catch (Exception e) {
            // 日志记录异常，忽略处理以返回分块不存在的结果
        }
        // 分块不存在
        return RestResponse.success(false);
    }

    @Override
    public RestResponse<Boolean>uploadChunk(String fileMd5, int chunk, String localChunkFilePath) {
        //得到分块文件的目录路径
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //得到分块文件的路径
        String chunkFilePath = chunkFileFolderPath + chunk;
        //mimeType
        String mimeType = getMimeType(null);
        //将文件存储至minIO
        boolean b = addMediaFilesToMinIO(localChunkFilePath, mimeType, bucketVideoFiles, chunkFilePath);
        if (!b) {
            log.debug("上传分块文件失败:{}", chunkFilePath);
            return RestResponse.validfail(false, "上传分块失败");
        }
        log.debug("上传分块文件成功:{}",chunkFilePath);
        return RestResponse.success(true);
    }

    @Override
    public RestResponse<Boolean> mergechunks(long companyId, String fileMd5, int chunkTotal, UploadFileParamsDto uploadFileParamsDto) {
        //分块文件所在目录
        String chunkFileFolderPath = getChunkFileFolderPath(fileMd5);
        //找到所有的分块文件
        List<ComposeSource> sources = Stream.iterate(0, i -> ++i).limit(chunkTotal).map(i -> ComposeSource.builder().bucket(bucketVideoFiles).object(chunkFileFolderPath + i).build()).collect(Collectors.toList());
        //源文件名称
        String filename = uploadFileParamsDto.getFilename();
        //扩展名
        String extension = filename.substring(filename.lastIndexOf("."));
        //合并后文件的objectname
        String objectName = getFilePathByMd5(fileMd5, extension);
        //指定合并后的objectName等信息
        ComposeObjectArgs composeObjectArgs = ComposeObjectArgs.builder()
                .bucket(bucketVideoFiles)
                .object(objectName)//合并后的文件的objectname
                .sources(sources)//指定源文件
                .build();
        //===========合并文件============
        //报错size 1048576 must be greater than 5242880，minio默认的分块文件大小为5M
        try {
            minioClient.composeObject(composeObjectArgs);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("合并文件出错,bucket:{},objectName:{},错误信息:{}",bucketVideoFiles,objectName,e.getMessage());
            return RestResponse.validfail(false,"合并文件异常");
        }

        //===========校验合并后的和源文件是否一致，视频上传才成功===========
        //先下载合并后的文件
        File file = downloadFileFromMinIO(bucketVideoFiles, objectName);
        try(FileInputStream fileInputStream = new FileInputStream(file)){
            //计算合并后文件的md5
            String mergeFile_md5 = DigestUtils.md5Hex(fileInputStream);
            //比较原始md5和合并后文件的md5
            if(!fileMd5.equals(mergeFile_md5)){
                log.error("校验合并文件md5值不一致,原始文件:{},合并文件:{}",fileMd5,mergeFile_md5);
                return RestResponse.validfail(false,"文件校验失败");
            }
            //文件大小
            uploadFileParamsDto.setFileSize(file.length());
        }catch (Exception e) {
            return RestResponse.validfail(false,"文件校验失败");
        }

        //==============将文件信息入库============
        MediaFiles mediaFiles = currentProxy.addMediaFilesToDb(companyId, fileMd5, uploadFileParamsDto, bucketVideoFiles, objectName);
        if(mediaFiles == null){
            return RestResponse.validfail(false,"文件入库失败");
        }
        //==========清理分块文件=========
        clearChunkFiles(chunkFileFolderPath,chunkTotal);


        return RestResponse.success(true);
    }

    /**
     * 从minio下载文件
     * @param bucket 桶
     * @param objectName 对象名称
     * @return 下载后的文件
     */
    @Override
    public File downloadFileFromMinIO(String bucket, String objectName){
        //临时文件
        File minioFile = null;
        FileOutputStream outputStream = null;
        try{
            InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucket)
                    .object(objectName)
                    .build());
            //创建临时文件
            minioFile=File.createTempFile("minio", ".merge");
            outputStream = new FileOutputStream(minioFile);
            IOUtils.copy(stream,outputStream);
            return minioFile;
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(outputStream!=null){
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 清除分块文件
     * @param chunkFileFolderPath 分块文件路径
     * @param chunkTotal 分块文件总数
     */
    private void clearChunkFiles(String chunkFileFolderPath,int chunkTotal){
        Iterable<DeleteObject> objects =  Stream.iterate(0, i -> ++i).limit(chunkTotal).map(i -> new DeleteObject(chunkFileFolderPath+ i)).collect(Collectors.toList());;
        RemoveObjectsArgs removeObjectsArgs = RemoveObjectsArgs.builder().bucket(bucketVideoFiles).objects(objects).build();
        Iterable<Result<DeleteError>> results = minioClient.removeObjects(removeObjectsArgs);
        //要想真正删除
        results.forEach(f->{
            try {
                DeleteError deleteError = f.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    /**
     * 得到合并后的文件的地址
     * @param fileMd5 文件id即md5值
     * @param fileExt 文件扩展名
     * @return
     */
    private String getFilePathByMd5(String fileMd5,String fileExt){
        return   fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/" +fileMd5 +fileExt;
    }


    /**
     * 构建分块文件的存储目录路径
     *
     * @param fileMd5 文件的MD5值
     * @return 分块文件的存储目录路径
     */
    private String getChunkFileFolderPath(String fileMd5) {
        return fileMd5.charAt(0) + "/" + fileMd5.charAt(1) + "/" + fileMd5 + "/chunk/";
    }


}




