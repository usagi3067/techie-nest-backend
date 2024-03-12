package com.dango.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.media.model.entity.MediaProcessHistory;
import com.dango.media.service.MediaProcessHistoryService;
import com.dango.media.mapper.MediaProcessHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【media_process_history(媒资处理历史表)】的数据库操作Service实现
* @createDate 2024-03-12 12:44:46
*/
@Service
public class MediaProcessHistoryServiceImpl extends ServiceImpl<MediaProcessHistoryMapper, MediaProcessHistory>
    implements MediaProcessHistoryService{

}




