package com.dango.content.controller;

import com.dango.content.model.entity.CourseTeacher;
import com.dango.content.service.CourseTeacherService;
import com.dango.model.BaseResponse;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dango
 * @description 师资编辑接口
 * @date 2024-03-07
 */
@Slf4j
@RestController
@RequestMapping("/courseTeacher")
@Api(value = "师资编辑接口",tags = "师资编辑接口")
public class CourseTeacherController {
    @Resource
    private CourseTeacherService courseTeacherService;

    @ApiOperation("查询师资列表")
    @GetMapping("/list/{courseId}")
    public BaseResponse<List<CourseTeacher>> list(@PathVariable Long courseId) {
        List<CourseTeacher> teacherList = courseTeacherService.list(courseId);
        return ResultUtils.success(teacherList);
    }
}