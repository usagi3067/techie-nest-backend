package com.dango.content.controller;

import com.dango.content.model.dto.CourseCategoryMenuDto;
import com.dango.content.model.dto.CourseCategoryTreeDto;
import com.dango.content.service.CourseCategoryService;
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
    public BaseResponse<List<CourseCategoryTreeDto>> queryTreeNodes() {
        List<CourseCategoryTreeDto> treeNodes = courseCategoryService.queryTreeNodes("1");
        return ResultUtils.success(treeNodes);
    }


    @GetMapping("/allMt")
    @ApiOperation("查询所有大分类")
    public BaseResponse<List<CourseCategoryMenuDto>> queryAllMt() {
        List<CourseCategoryMenuDto> allMt = courseCategoryService.getAllMt();
        return ResultUtils.success(allMt);
    }

    @GetMapping("/allSt/{id}")
    @ApiOperation("查询大分类下的小分类")
    public BaseResponse<List<CourseCategoryMenuDto>> queryAllSt(@PathVariable("id") String id) {
        List<CourseCategoryMenuDto> allSt = courseCategoryService.getAllSt(id);
        return ResultUtils.success(allSt);
    }

}
