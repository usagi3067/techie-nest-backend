package com.dango.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.media.mapper.MediaProcessHistoryMapper;
import com.dango.media.mapper.MediaProcessMapper;
import com.dango.media.model.entity.MediaProcess;
import com.dango.media.model.entity.MediaProcessHistory;
import com.dango.media.service.MediaProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author dango
* @description 针对表【media_process(媒资处理表)】的数据库操作Service实现
* @createDate 2024-03-12 12:44:46
*/
@Slf4j
@Service
public class MediaProcessServiceImpl extends ServiceImpl<MediaProcessMapper, MediaProcess>
    implements MediaProcessService {

    @Resource
    MediaProcessHistoryMapper mediaProcessHistoryMapper;

    @Override
    public List<MediaProcess> getMediaProcessList(int shardIndex, int shardTotal, int count) {
        return baseMapper.selectListByShardIndex(shardTotal, shardIndex, count);
    }

    @Transactional
    @Override
    public void saveProcessFinishStatus(Long taskId, String status, String fileId, String url, String errorMsg) {
        // 查询这个任务
        MediaProcess mediaProcess = baseMapper.selectById(taskId);
        if (mediaProcess == null) {
            log.debug("更新任务状态时，此任务：{}，为空", taskId);
            return;
        }
        LambdaQueryWrapper<MediaProcess> queryWrapper = new LambdaQueryWrapper<MediaProcess>().eq(MediaProcess::getId, taskId);
        // 如果任务失败
        if ("3".equals(status)) {
            log.debug("任务失败：{}", taskId);
            MediaProcess mediaProcess_u = new MediaProcess();
            mediaProcess_u.setStatus("3");
            mediaProcess_u.setErrorMsg(errorMsg);
            mediaProcess_u.setFinishDate(LocalDateTime.now());
            baseMapper.update(mediaProcess_u, queryWrapper);
            return;
        }
        // 任务成功，将其从待处理任务表中删除，同时新增历史处理表记录
        if ("2".equals(status)) {
            mediaProcess.setStatus("2");
            mediaProcess.setUrl(url);
            mediaProcess.setFinishDate(LocalDateTime.now());
            baseMapper.update(mediaProcess, queryWrapper);
            MediaProcessHistory mediaProcessHistory = new MediaProcessHistory();
            // 两张表的属性完全一致，直接拷贝
            BeanUtils.copyProperties(mediaProcess, mediaProcessHistory);
            // 向历史处理表新增数据
            mediaProcessHistoryMapper.insert(mediaProcessHistory);
            // 同时删除待处理任务表中的数据
            baseMapper.deleteById(taskId);
        }
    }

    public boolean startTask(long id) {
        int result = baseMapper.startTask(id);
        return result<=0?false:true;
    }
}




