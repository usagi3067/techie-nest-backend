package com.dango.content.controller;

import com.dango.content.model.dto.CourseCategoryTreeDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dango.content.service.CourseCategoryService;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-03-03
 */
@Slf4j
@RestController
@RequestMapping("/course-category")
@Api(value = "课程分类接口", tags = "课程分类接口")
public class CourseCategoryController {
    @Resource
    private CourseCategoryService courseCategoryService;

    @GetMapping("/tree-nodes")
    @ApiOperation("查询课程分类树")
    public List<CourseCategoryTreeDto> queryTreeNodes() {
        return courseCategoryService.queryTreeNodes("1");
    }


}
