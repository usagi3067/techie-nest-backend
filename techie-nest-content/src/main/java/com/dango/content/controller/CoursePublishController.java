package com.dango.content.controller;

import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.service.CoursePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author dango
 * @description
 * @date 2024-03-19
 */
@Controller
public class CoursePublishController {

    @Autowired
    private CoursePublishService coursePublishService;

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
    }


}
