package com.dango.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.media.model.entity.MqMessage;
import com.dango.media.service.MqMessageService;
import com.dango.media.mapper.MqMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【mq_message(消息表)】的数据库操作Service实现
* @createDate 2024-03-12 12:44:46
*/
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage>
    implements MqMessageService{

}




