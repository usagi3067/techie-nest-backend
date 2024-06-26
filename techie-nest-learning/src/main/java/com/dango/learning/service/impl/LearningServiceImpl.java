package com.dango.learning.service.impl;

import com.dango.exception.BusinessException;
import com.dango.learning.feignclient.ContentServiceClient;
import com.dango.learning.feignclient.MediaServiceClient;
import com.dango.learning.model.dto.CoursePublishDto;
import com.dango.learning.model.dto.CourseTablesDto;
import com.dango.learning.service.CourseTablesService;
import com.dango.learning.service.LearningService;
import com.dango.model.BaseResponse;
import com.dango.model.RestResponse;
import com.dango.model.state.ChooseCourseStatus;
import com.dango.model.state.CourseFeeStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author dango
 * @description
 * @date 2024-03-25
 */
@Service
public class LearningServiceImpl implements LearningService {

    @Autowired
    ContentServiceClient contentServiceClient;

    @Autowired
    CourseTablesService courseTablesService;

    @Autowired
    MediaServiceClient mediaServiceClient;

    @Override
    public String getVideo(Long userId, Long courseId, Long teachPlanId, String mediaId) {
        //查询课程信息
        BaseResponse<CoursePublishDto> coursePublishBaseResponse = contentServiceClient.getCoursepublish(courseId);
        if (coursePublishBaseResponse.getCode() != 0) {
            throw  new BusinessException("课程发布信息不存在");
        }
        CoursePublishDto coursePublishDto = coursePublishBaseResponse.getData();
        //校验学习资格

        //如果登录
        if (userId != null) {

            //判断是否选课，根据选课情况判断学习资格
            CourseTablesDto xcCourseTablesDto = courseTablesService.getLearningStatus(userId, courseId);
            //学习资格状态 [{"code":"702001","desc":"正常学习"},{"code":"702002","desc":"没有选课或选课后没有支付"},{"code":"702003","desc":"已过期需要申请续期或重新支付"}]
            String learnStatus = xcCourseTablesDto.getLearnStatus();
            if (learnStatus.equals(ChooseCourseStatus.SUCCESS.getCode())) {
                BaseResponse<String> playUrlByMediaId = mediaServiceClient.getPlayUrlByMediaId(mediaId);
                if (playUrlByMediaId.getCode() == 0) return playUrlByMediaId.getData();
            } else if (learnStatus.equals(ChooseCourseStatus.OUTDATED.getCode())) {
                RestResponse.validfail("您的选课已过期需要申请续期或重新支付");
            }
        }

        //未登录或未选课判断是否收费
        Integer isFree = coursePublishDto.getIsFree();
        if (isFree.equals(CourseFeeStatus.FREE.getCode())) {//免费可以正常学习
            BaseResponse<String> playUrlByMediaId = mediaServiceClient.getPlayUrlByMediaId(mediaId);
            if (playUrlByMediaId.getCode() == 0) return playUrlByMediaId.getData();
        }

        throw new BusinessException("请购买课程后继续学习");

    }
}
