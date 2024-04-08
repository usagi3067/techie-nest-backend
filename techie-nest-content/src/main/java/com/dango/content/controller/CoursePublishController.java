package com.dango.content.controller;


import com.alibaba.fastjson.JSON;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.model.dto.HomePageDisplayDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.CoursePublish;
import com.dango.content.service.CoursePublishPreService;
import com.dango.content.service.CoursePublishService;
import com.dango.content.util.SecurityUtil;
import com.dango.model.BaseResponse;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-03-19
 */
@RestController
@Api(tags = "课程发布接口")
public class CoursePublishController {

    @Resource
    private CoursePublishService coursePublishService;

    @Resource
    private CoursePublishPreService coursePublishPreService;

    @GetMapping("/coursepreview/{courseId}")
    @ApiOperation("课程预览页面")
    public BaseResponse<CoursePreviewDto> preview(@PathVariable("courseId") Long courseId) {
        CoursePreviewDto coursePreviewDto = coursePublishService.fetchCoursePreviewInfo(courseId);
        return ResultUtils.success(coursePreviewDto);
    }

    @PostMapping("/courseaudit/commit/{courseId}")
    @ApiOperation("提交审核")
    public BaseResponse<Boolean> commitAudit(@PathVariable("courseId") Long courseId) {
        // 提交审核逻辑
        Long lecturerId = SecurityUtil.getLecturerId();
        coursePublishPreService.commitAudit(lecturerId, courseId);
        return ResultUtils.success(true);
    }

    @PostMapping("/courseaudit/commit/success/{courseId}")
    @ApiOperation("审核通过")
    public BaseResponse<Boolean> commitAuditSuccess(@PathVariable("courseId") Long courseId) {
        coursePublishPreService.commitAuditSuccess(courseId);
        return ResultUtils.success(true);
    }

    @PostMapping("/courseaudit/commit/fail/{courseId}")
    @ApiOperation("审核未通过")
    public BaseResponse<Boolean> commitAuditFail(@PathVariable("courseId") Long courseId, @RequestBody(required = false) String content) {
        coursePublishPreService.commitAuditFail(courseId, content);
        return ResultUtils.success(true);
    }

    @ApiOperation("课程发布")
    @PostMapping("/coursepublish/{courseId}")
    public BaseResponse<Boolean> coursepublish(@PathVariable("courseId") Long courseId) {
        Long lecturerId = SecurityUtil.getLecturerId();
        coursePublishService.publish(lecturerId, courseId);
        return ResultUtils.success(true);
    }

    @ApiOperation("查询课程发布信息")
    @GetMapping("/r/coursepublish/{courseId}")
    public BaseResponse<CoursePublish> getCoursepublish(@PathVariable("courseId") Long courseId) {
        //查询课程发布信息
        CoursePublish coursePublish = coursePublishService.getCoursePublish(courseId);
        return ResultUtils.success(coursePublish);
    }

    @ApiOperation("获取课程发布信息")
    @GetMapping("/course/whole/{courseId}")
    public BaseResponse<CoursePreviewDto> getCoursePublish(@PathVariable("courseId") Long courseId) {
        //查询课程发布信息
        CoursePublish coursePublish = coursePublishService.getCoursePublish(courseId);
        if (coursePublish == null) {
            return ResultUtils.success(new CoursePreviewDto());
        }

        //课程基本信息
        CourseBaseInfoDto courseBase = new CourseBaseInfoDto();
        BeanUtils.copyProperties(coursePublish, courseBase);
        //课程计划
        List<TeachPlanDto> teachplans = JSON.parseArray(coursePublish.getTeachPlan(), TeachPlanDto.class);
        CoursePreviewDto coursePreviewInfo = new CoursePreviewDto();
        coursePreviewInfo.setCourseBase(courseBase);
        coursePreviewInfo.setTeachPlans(teachplans);
        return ResultUtils.success(coursePreviewInfo);
    }

    @ApiOperation("更新选课人数")
    @PostMapping("/course/count/{courseId}")
    public BaseResponse<Boolean> updateCourseStudyCount(@PathVariable(value = "courseId") Long courseId) {
        Boolean b = coursePublishService.updateCourseStudyCount(courseId);
        return ResultUtils.success(b);
    }

    @ApiOperation("查询主页展示数据")
    @GetMapping("/course/index")
    public BaseResponse<HomePageDisplayDto> display() {
        HomePageDisplayDto dto = coursePublishService.display();
        return ResultUtils.success(dto);
    }
}

