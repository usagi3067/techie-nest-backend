package com.dango.content.controller;

import com.dango.content.model.dto.AddCourseDto;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.EditCourseDto;
import com.dango.content.model.dto.QueryCoursePageDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.service.CourseBaseService;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author dango
 * @description
 * @date 2024-01-16
 */
@RestController
@Slf4j
@Api(tags = "课程信息编辑接口")
@RequestMapping()
public class CourseBaseInfoController {
    @Resource
    private CourseBaseService courseBaseService;

    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCoursePageDto req) {
        return courseBaseService.queryCoursePageList(pageParams, req);
    }

    @ApiOperation("课程新增接口")
    @PostMapping("/course")
    public CourseBaseInfoDto addCourseBase(@RequestBody @Validated AddCourseDto addCourseDto) {
        Long companyId = 1234L;
        return courseBaseService.addCourseBase(companyId, addCourseDto);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/course/{id}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable("id") Long id) {
        return courseBaseService.getCourseBaseInfo(id);
    }

    @ApiOperation("修改课程基本信息")
    @PutMapping("/course")
    public CourseBaseInfoDto updateCourseBase(@RequestBody @Validated EditCourseDto editCourseDto) {
        Long companyId = 1234L;
        return courseBaseService.updateCourseBase(companyId, editCourseDto);
    }

}
