package com.dango.content.controller;

import com.dango.content.model.dto.QueryCoursePageDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.service.CourseBaseService;
import com.dango.model.BaseResponse;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.dango.content.util.SecurityUtil.getLecturerId;

/**
 * @author dango
 * @description
 * @date 2024-04-04
 */
@RestController
@Slf4j
@Api(tags = "管理员相关接口")
@RequestMapping()
public class AdminController {
    @Resource
    private CourseBaseService courseBaseService;
    @ApiOperation("课程查询接口")
    @PostMapping("/admin/course/list")
    public BaseResponse<PageResult<CourseBase>> listByAdmin(PageParams pageParams, @RequestBody(required = false) QueryCoursePageDto req) {
        PageResult<CourseBase> pageResult = courseBaseService.queryCoursePageList(pageParams, req);
        return ResultUtils.success(pageResult);
    }
}
