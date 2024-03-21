package com.dango.messagesdk.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.messagesdk.domain.entity.MqMessageHistory;
import com.dango.messagesdk.service.MqMessageHistoryService;
import com.dango.messagesdk.mapper.MqMessageHistoryMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【mq_message_history(消息历史表)】的数据库操作Service实现
* @createDate 2024-03-20 20:43:11
*/
@Service
public class MqMessageHistoryServiceImpl extends ServiceImpl<MqMessageHistoryMapper, MqMessageHistory>
    implements MqMessageHistoryService{

}




