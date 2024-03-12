package com.dango.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.media.service.MqMessageHistoryService;
import com.dango.media.model.entity.MqMessageHistory;
import com.dango.media.mapper.MqMessageHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【mq_message_history(消息历史表)】的数据库操作Service实现
* @createDate 2024-03-12 12:44:46
*/
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory>
    implements MqMessageHistoryService {

}




