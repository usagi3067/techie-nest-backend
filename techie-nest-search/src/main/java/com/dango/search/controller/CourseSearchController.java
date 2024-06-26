package com.dango.search.controller;

import com.dango.model.BaseResponse;
import com.dango.model.PageParams;
import com.dango.search.dto.SearchCourseParamDto;
import com.dango.search.dto.SearchPageResultDto;
import com.dango.search.po.CourseIndex;
import com.dango.search.service.CourseSearchService;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description 课程搜索接口
 * @author Mr.M
 * @date 2022/9/24 22:31
 * @version 1.0
 */
@Api(value = "课程搜索接口",tags = "课程搜索接口")
 @RestController
 @RequestMapping("/course")
public class CourseSearchController {

 @Autowired
 CourseSearchService courseSearchService;


 @ApiOperation("课程搜索列表")
  @GetMapping("/list")
 public BaseResponse<SearchPageResultDto<CourseIndex>> list(PageParams pageParams, SearchCourseParamDto searchCourseParamDto){

  SearchPageResultDto<CourseIndex> courseIndexSearchPageResultDto = courseSearchService.queryCoursePubIndex(pageParams, searchCourseParamDto);
  return ResultUtils.success(courseIndexSearchPageResultDto);

 }
}
