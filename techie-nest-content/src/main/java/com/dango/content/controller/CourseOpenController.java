package com.dango.content.controller;

import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.service.CourseBaseService;
import com.dango.content.service.CoursePublishService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dango
 * @description
 * @date 2024-03-20
 */
@Api(value = "课程公开查询接口", tags = "课程公开查询接口")
@RestController
@RequestMapping("/open")
public class CourseOpenController {

    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private CoursePublishService coursePublishService;

    /**
     * 获取课程信息预览
     *
     * @param courseId 课程ID
     * @return com.dango.content.model.dto.CoursePreviewDto
     */
    @GetMapping("/course/whole/{courseId}")
    public CoursePreviewDto retrieveCoursePreviewInfo(@PathVariable("courseId") Long courseId) {
        // 获取课程预览信息
        return coursePublishService.fetchCoursePreviewInfo(courseId);
    }
}
