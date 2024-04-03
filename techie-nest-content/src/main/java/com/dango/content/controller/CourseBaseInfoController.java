package com.dango.content.controller;

import com.dango.content.model.dto.AddCourseDto;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.EditCourseDto;
import com.dango.content.model.dto.QueryCoursePageDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.service.CourseBaseService;
import com.dango.content.util.SecurityUtil;
import com.dango.model.BaseResponse;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * œ
 *
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
    public BaseResponse<PageResult<CourseBase>> list(PageParams pageParams, @RequestBody(required = false) QueryCoursePageDto req) {
        //当前登录用户
        SecurityUtil.User user = SecurityUtil.getUser();
        //用户所属机构id
        Long companyId = null;

        if (user != null && StringUtils.isNotEmpty(user.getCompanyId())) {
            companyId = Long.parseLong(user.getCompanyId());
        } else {
            companyId = 1234L;
        }
        PageResult<CourseBase> pageResult = courseBaseService.queryCoursePageList(companyId, pageParams, req);
        return ResultUtils.success(pageResult);
    }

    @ApiOperation("课程新增接口")
    @PostMapping("/course")
    public BaseResponse<CourseBaseInfoDto> addCourseBase(@RequestBody @Validated AddCourseDto addCourseDto) {
        Long companyId = 1234L;
        CourseBaseInfoDto courseBaseInfoDto = courseBaseService.addCourseBase(companyId, addCourseDto);
        return ResultUtils.success(courseBaseInfoDto);
    }

    @ApiOperation("根据课程id查询课程信息")
    @GetMapping("/course/{id}")
    public BaseResponse<CourseBaseInfoDto> getCourseBaseById(@PathVariable("id") Long id) {
        SecurityUtil.User user = SecurityUtil.getUser();
        log.info("user:{}", user);
        CourseBaseInfoDto courseBaseInfodto = courseBaseService.getCourseBaseInfo(id);
        return ResultUtils.success(courseBaseInfodto);
    }

    @ApiOperation("修改课程基本信息")
    @PutMapping("/course")
    public BaseResponse<CourseBaseInfoDto> updateCourseBase(@RequestBody @Validated EditCourseDto editCourseDto) {
        Long companyId = 1234L;
        CourseBaseInfoDto courseBaseInfoDto = courseBaseService.updateCourseBase(companyId, editCourseDto);
        return ResultUtils.success(courseBaseInfoDto);
    }

    @ApiOperation("删除课程")
    @DeleteMapping("/course/{courseId}")
    public BaseResponse<Boolean> deleteCourse(@PathVariable Long courseId) {
        //当前登录用户
        SecurityUtil.User user = SecurityUtil.getUser();
        //用户所属机构id
        Long companyId = null;
        if (StringUtils.isNotEmpty(user.getCompanyId())) {
            companyId = Long.parseLong(user.getCompanyId());
        } else {
            companyId = 1234L;
        }

        Boolean b = courseBaseService.deleteCourse(companyId, courseId);
        return ResultUtils.success(b);
    }
}


