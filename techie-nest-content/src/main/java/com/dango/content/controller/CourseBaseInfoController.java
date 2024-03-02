package com.dango.content.controller;

import com.dango.content.model.dto.QueryCoursePageReq;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.service.CourseBaseService;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dango
 * @description
 * @date 2024-01-16
 */
@RestController
@Slf4j
@Api(tags = "课程信息编辑接口")
@RequestMapping("/course")
public class CourseBaseInfoController {
    @Resource
    private CourseBaseService courseBaseService;

    @ApiOperation("课程查询接口")
    @PostMapping("/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody(required = false) QueryCoursePageReq req) {
        return courseBaseService.queryCoursePageList(pageParams, req);
    }

}
