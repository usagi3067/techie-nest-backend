package com.dango.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.media.model.entity.MediaProcess;

import java.util.List;

/**
 * @author dango
 * @description 针对表【media_process(媒资处理表)】的数据库操作Service
 * @createDate 2024-03-12 12:44:46
 */
public interface MediaProcessService extends IService<MediaProcess> {
    /**
     * 获取待处理的任务
     *
     * @param shardIndex 分片序号
     * @param shardTotal 分片总数
     * @param count      获取该记录
     * @return
     */
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count);

    /**
     * 更新任务状态
     * @param taskId 任务 id
     * @param status 更新的状态
     * @param fileId
     * @param url
     * @param errorMsg
     */
    void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg);

    /**
     *  开启一个任务
     * @param id 任务id
     * @return true开启任务成功，false开启任务失败
     */
    boolean startTask(long id);
}
