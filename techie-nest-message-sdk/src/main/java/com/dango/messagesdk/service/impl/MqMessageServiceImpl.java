package com.dango.messagesdk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.messagesdk.domain.entity.MqMessage;
import com.dango.messagesdk.domain.entity.MqMessageHistory;
import com.dango.messagesdk.mapper.MqMessageHistoryMapper;
import com.dango.messagesdk.mapper.MqMessageMapper;
import com.dango.messagesdk.service.MqMessageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
* @author dango
* @description 针对表【mq_message(消息表)】的数据库操作Service实现
* @createDate 2024-03-20 20:21:40
*/
@Service
public class MqMessageServiceImpl extends ServiceImpl<MqMessageMapper, MqMessage>
    implements MqMessageService{

    @Resource
    private MqMessageHistoryMapper mqMessageHistoryMapper;

    @Override
    public List<MqMessage> getMessageList(int shardIndex, int shardTotal, String messageType, int count) {
        return baseMapper.selectListByShardIndex(shardTotal,shardIndex,messageType,count);
    }

    @Override
    public MqMessage addMessage(String messageType, String businessKey1, String businessKey2, String businessKey3) {
        MqMessage mqMessage = new MqMessage();
        mqMessage.setMessageType(messageType);
        mqMessage.setBusinessKey1(businessKey1);
        mqMessage.setBusinessKey2(businessKey2);
        mqMessage.setBusinessKey3(businessKey3);
        int insert = baseMapper.insert(mqMessage);
        if(insert>0){
            return mqMessage;
        }else{
            return null;
        }

    }

    @Transactional
    @Override
    public int completed(long id) {
        MqMessage mqMessage = new MqMessage();
        //完成任务
        mqMessage.setState("1");
        int update = baseMapper.update(mqMessage, new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId, id));
        if(update>0){

            mqMessage = baseMapper.selectById(id);
            //添加到历史表
            MqMessageHistory mqMessageHistory = new MqMessageHistory();
            BeanUtils.copyProperties(mqMessage,mqMessageHistory);
            mqMessageHistoryMapper.insert(mqMessageHistory);
            //删除消息表
            baseMapper.deleteById(id);
            return 1;
        }
        return 0;

    }

    @Override
    public int completedStageOne(long id) {
        MqMessage mqMessage = new MqMessage();
        //完成阶段1任务
        mqMessage.setStageState1("1");
        return baseMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int completedStageTwo(long id) {
        MqMessage mqMessage = new MqMessage();
        //完成阶段2任务
        mqMessage.setStageState2("1");
        return baseMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int completedStageThree(long id) {
        MqMessage mqMessage = new MqMessage();
        //完成阶段3任务
        mqMessage.setStageState3("1");
        return baseMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int completedStageFour(long id) {
        MqMessage mqMessage = new MqMessage();
        //完成阶段4任务
        mqMessage.setStageState4("1");
        return baseMapper.update(mqMessage,new LambdaQueryWrapper<MqMessage>().eq(MqMessage::getId,id));
    }

    @Override
    public int getStageOne(long id) {
        return Integer.parseInt(baseMapper.selectById(id).getStageState1());
    }

    @Override
    public int getStageTwo(long id) {
        return Integer.parseInt(baseMapper.selectById(id).getStageState2());
    }

    @Override
    public int getStageThree(long id) {
        return Integer.parseInt(baseMapper.selectById(id).getStageState3());
    }

    @Override
    public int getStageFour(long id) {
        return Integer.parseInt(baseMapper.selectById(id).getStageState4());
    }

}




