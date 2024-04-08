package com.dango.content.service.jobHandler;

import com.dango.content.feignclient.SearchServiceClient;
import com.dango.content.mapper.CoursePublishMapper;
import com.dango.content.model.entity.CoursePublish;
import com.dango.content.service.CoursePublishService;
import com.dango.exception.BusinessException;
import com.dango.messagesdk.domain.entity.MqMessage;
import com.dango.messagesdk.service.MessageProcessAbstract;
import com.dango.messagesdk.service.MqMessageService;
import com.dango.model.BaseResponse;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author dango
 * @description
 * @date 2024-03-20
 */
@Slf4j
@Component
public class CoursePublishTask extends MessageProcessAbstract {

    @Resource
    private CoursePublishService coursePublishService;

    @Resource
    private CoursePublishMapper coursePublishMapper;

    @Autowired
    private SearchServiceClient searchServiceClient;

    @XxlJob("CoursePublishJobHandler")
    public void coursePublishJobHandler() throws Exception {
        // 分片参数
        int shardIndex = XxlJobHelper.getShardIndex();
        int shardTotal = XxlJobHelper.getShardTotal();
        log.debug("shardIndex="+shardIndex+",shardTotal="+shardTotal);
        //参数:分片序号、分片总数、消息类型、一次最多取到的任务数量、一次任务调度执行的超时时间
        process(shardIndex,shardTotal,"course_publish",30,60);
    }


    //课程发布任务处理
    @Override
    public boolean execute(MqMessage mqMessage) {
        //获取消息相关的业务信息
        String businessKey1 = mqMessage.getBusinessKey1();
        long courseId = Integer.parseInt(businessKey1);
        //课程索引
        saveCourseIndex(mqMessage,courseId);
        //课程缓存
        saveCourseCache(mqMessage,courseId);
        return true;
    }

    //将课程信息缓存至redis
    public void saveCourseCache(MqMessage mqMessage,long courseId){
        log.debug("将课程信息缓存至redis,课程id:{}",courseId);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //保存课程索引信息
    public void saveCourseIndex(MqMessage mqMessage,long courseId){
        //任务id
        Long taskId = mqMessage.getId();
        MqMessageService mqMessageService = this.getMqMessageService();
        //取出第一个阶段状态
        int stageOne = mqMessageService.getStageOne(taskId);

        //任务幂等性处理
        if(stageOne>0){
            log.debug("课程索引信息已写入，无需执行...");
            return;
        }
        //查询课程信息，调用搜索服务添加索引接口
        //从课程发布表查询课程信息
        CoursePublish coursePublish = coursePublishMapper.selectById(courseId);

        CourseIndex courseIndex = new CourseIndex();
        BeanUtils.copyProperties(coursePublish,courseIndex);
        //远程调用
        BaseResponse<Boolean> add = searchServiceClient.add(courseIndex);
        if(!add.getData()){
            throw new BusinessException("远程调用搜索服务添加课程索引失败");
        }

        //完成本阶段的任务
        mqMessageService.completedStageOne(taskId);

    }


}
