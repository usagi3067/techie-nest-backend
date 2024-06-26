package com.dango.learning.service.impl;

import com.alibaba.fastjson.JSON;
import com.dango.exception.BusinessException;
import com.dango.learning.config.PayNotifyConfig;
import com.dango.learning.feignclient.ContentServiceClient;
import com.dango.learning.mapper.ChooseCourseMapper;
import com.dango.learning.model.dto.LecturerBalanceDto;
import com.dango.learning.model.entity.ChooseCourse;
import com.dango.learning.service.CourseTablesService;
import com.dango.messagesdk.domain.entity.MqMessage;
import com.dango.messagesdk.service.MqMessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceivePayNotifyService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MqMessageService mqMessageService;

    @Autowired
    CourseTablesService courseTablesService;

    @Autowired
    ContentServiceClient contentServiceClient;

    @Autowired
    ChooseCourseMapper chooseCourseMapper;




    //监听消息队列接收支付结果通知
    @RabbitListener(queues = PayNotifyConfig.PAYNOTIFY_QUEUE)
    public void receive(Message message, Channel channel) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //获取消息
        MqMessage mqMessage = JSON.parseObject(message.getBody(), MqMessage.class);
        log.debug("学习中心服务接收支付结果:{}", mqMessage);

        //消息类型
        String messageType = mqMessage.getMessageType();
        //订单类型,60201表示购买课程
        String businessKey2 = mqMessage.getBusinessKey2();
        //这里只处理支付结果通知
        if (PayNotifyConfig.MESSAGE_TYPE.equals(messageType) && "60201".equals(businessKey2)) {
            //选课记录id
            String choosecourseId = mqMessage.getBusinessKey1();
            //添加选课
            boolean b = courseTablesService.saveChooseCourseSuccess(choosecourseId);
            if (!b) {
                //添加选课失败，抛出异常，消息重回队列
                throw new BusinessException("收到支付结果，添加选课失败");
            }

            // 调用讲师端余额增加
            ChooseCourse chooseCourse = chooseCourseMapper.selectById(choosecourseId);
            Long lecturerId = chooseCourse.getLecturerId();
            Double price = chooseCourse.getCoursePrice();
            LecturerBalanceDto dto = new LecturerBalanceDto();
            dto.setPrice(price);
            dto.setLecturerId(lecturerId);
            contentServiceClient.updateBalance(dto);
        }
    }


}
