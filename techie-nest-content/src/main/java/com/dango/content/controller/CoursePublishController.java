package com.dango.content.controller;

import com.alibaba.fastjson.JSON;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.CoursePublish;
import com.dango.content.service.CoursePublishPreService;
import com.dango.content.service.CoursePublishService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-03-19
 */
@Controller
public class CoursePublishController {

    @Resource
    private CoursePublishService coursePublishService;

    @Resource
    private CoursePublishPreService coursePublishPreService;

    // 处理课程预览页面请求
    @GetMapping("/coursepreview/{courseId}")
    public ModelAndView preview(@PathVariable("courseId") Long courseId){
        CoursePreviewDto coursePreviewDto = coursePublishService.fetchCoursePreviewInfo(courseId);

        // 创建一个新的 ModelAndView 对象
        ModelAndView modelAndView = new ModelAndView();

        // 将模型对象添加到 ModelAndView 中（这里模型对象为空，可以根据需要添加具体数据）
        modelAndView.addObject("model", coursePreviewDto);

        // 设置视图名称为 course_template，返回给客户端
        modelAndView.setViewName("course_template");

        // 返回 ModelAndView 对象
        return modelAndView;
    }

    /**
     * 提交审核
     *
     * @param courseId 课程ID
     */
    @ResponseBody
    @PostMapping("/courseaudit/commit/{courseId}")
    public void commitAudit(@PathVariable("courseId") Long courseId) {
        // 提交审核逻辑
        Long companyId = 1234L;
        coursePublishPreService.commitAudit(companyId,courseId);

    }

    @ApiOperation("课程发布")
    @ResponseBody
    @PostMapping ("/coursepublish/{courseId}")
    public void coursepublish(@PathVariable("courseId") Long courseId){
        Long companyId = 1234L;
        coursePublishService.publish(companyId,courseId);

    }

    @ApiOperation("查询课程发布信息")
    @ResponseBody
    @GetMapping("/r/coursepublish/{courseId}")
    public CoursePublish getCoursepublish(@PathVariable("courseId") Long courseId) {
        //查询课程发布信息
        CoursePublish coursePublish = coursePublishService.getCoursePublish(courseId);
        return coursePublish;
    }


    @ApiOperation("获取课程发布信息")
    @ResponseBody
    @GetMapping("/course/whole/{courseId}")
    public CoursePreviewDto getCoursePublish(@PathVariable("courseId") Long courseId) {
        //查询课程发布信息
        CoursePublish coursePublish = coursePublishService.getCoursePublish(courseId);
        if (coursePublish == null) {
            return new CoursePreviewDto();
        }

        //课程基本信息
        CourseBaseInfoDto courseBase = new CourseBaseInfoDto();
        BeanUtils.copyProperties(coursePublish, courseBase);
        //课程计划
        List<TeachPlanDto> teachplans = JSON.parseArray(coursePublish.getTeachPlan(), TeachPlanDto.class);
        CoursePreviewDto coursePreviewInfo = new CoursePreviewDto();
        coursePreviewInfo.setCourseBase(courseBase);
        coursePreviewInfo.setTeachPlans(teachplans);
        return coursePreviewInfo;
    }



}
