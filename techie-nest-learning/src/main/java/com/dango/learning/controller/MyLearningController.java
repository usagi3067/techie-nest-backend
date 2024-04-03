package com.dango.learning.controller;

import com.dango.learning.service.LearningService;
import com.dango.learning.util.SecurityUtil;
import com.dango.model.BaseResponse;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.M
 * @version 1.0
 * @description 我的学习接口
 * @date 2022/10/27 8:59
 */
@Api(value = "学习过程管理接口", tags = "学习过程管理接口")
@Slf4j
@RestController
public class MyLearningController {

    @Autowired
    LearningService learningService;


    @ApiOperation("获取视频")
    @GetMapping("/open/learn/getvideo/{courseId}/{teachPlanId}/{mediaId}")
    public BaseResponse<String> getvideo(@PathVariable("courseId") Long courseId, @PathVariable("teachPlanId") Long teachPlanId, @PathVariable("mediaId") String mediaId) {
        SecurityUtil.User user = SecurityUtil.getUser();
        String userId;
        if (user == null) {
            userId = "50";
        } else {
            userId = user.getId();
        }
        //获取视频
        String res = learningService.getVideo(userId, courseId, teachPlanId, mediaId);

        return ResultUtils.success(res);

    }

}
