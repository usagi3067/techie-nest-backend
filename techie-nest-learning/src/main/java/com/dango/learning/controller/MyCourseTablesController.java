package com.dango.learning.controller;


import com.dango.exception.BusinessException;
import com.dango.learning.model.dto.ChooseCourseDto;
import com.dango.learning.model.dto.CourseTablesDto;
import com.dango.learning.model.dto.MyCourseTableParams;
import com.dango.learning.model.entity.CourseTables;
import com.dango.learning.service.CourseTablesService;
import com.dango.learning.util.SecurityUtil;
import com.dango.model.BaseResponse;
import com.dango.model.ErrorCode;
import com.dango.model.PageResult;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mr.M
 * @version 1.0
 * @description 我的课程表接口
 * @date 2022/10/25 9:40
 */

@Api(value = "我的课程表接口", tags = "我的课程表接口")
@Slf4j
@RestController
public class MyCourseTablesController {
    @Autowired
    CourseTablesService courseTablesService;


    @ApiOperation("添加选课")
    @PostMapping("/choosecourse/{courseId}")
    public BaseResponse<ChooseCourseDto> addChooseCourse(@PathVariable("courseId") Long courseId) {
        //登录用户
        SecurityUtil.User user = SecurityUtil.getUser();
        if (user == null) {
            throw new BusinessException("请登录后继续选课");
        }
        String userId = user.getId();
        ChooseCourseDto chooseCourseDto = courseTablesService.addChooseCourse(userId, courseId);
        return ResultUtils.success(chooseCourseDto);

    }

    @ApiOperation("查询学习资格")
    @PostMapping("/choosecourse/learnstatus/{courseId}")
    public BaseResponse<CourseTablesDto> getLearningStatus(@PathVariable("courseId") Long courseId) {
        //登录用户
        SecurityUtil.User user = SecurityUtil.getUser();
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        String userId = user.getId();
        CourseTablesDto learningStatus = courseTablesService.getLearningStatus(userId, courseId);
        return ResultUtils.success(learningStatus);
    }

    @ApiOperation("我的课程表")
    @GetMapping("/mycoursetable")
    public BaseResponse<PageResult<CourseTables>> mycoursetable(MyCourseTableParams params) {
        //登录用户
        SecurityUtil.User user = SecurityUtil.getUser();
        if (user == null) {
            throw new BusinessException("请登录后继续选课");
        }
        String userId = user.getId();
//设置当前的登录用户
        params.setUserId(userId);

        PageResult<CourseTables> courseTablesPageResult = courseTablesService.myCourseTables(params);
        return ResultUtils.success(courseTablesPageResult);

    }

}
