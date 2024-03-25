package com.dango.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.pay.service.MqMessageService;
import com.dango.pay.domain.entity.MqMessage;
import com.dango.pay.mapper.MqMessageMapper;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【mq_message】的数据库操作Service实现
* @createDate 2024-03-24 17:53:38
*/
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage>
    implements MqMessageService {

}




