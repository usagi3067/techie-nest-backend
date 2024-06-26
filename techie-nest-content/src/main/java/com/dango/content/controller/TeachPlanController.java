package com.dango.content.controller;

import com.dango.content.model.dto.BindTeachPlanMediaDto;
import com.dango.content.model.dto.SaveTeachPlanDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.service.TeachPlanService;
import com.dango.model.BaseResponse;
import com.dango.utils.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dango
 * @description
 * @date 2024-03-06
 */
@RestController
@Api(value = "课程计划编辑接口", tags = "课程计划编辑接口")
public class TeachPlanController {

    @Resource
    private TeachPlanService teachPlanService;

    @ApiOperation("查询课程计划树形列表")
    @ApiImplicitParam(value = "courseId", name = "课程Id", required = true, dataType = "Long", paramType = "path")
    @GetMapping("/teach-plan/{courseId}/tree-nodes")
    public BaseResponse<List<TeachPlanDto>> getTreeNodes(@PathVariable Long courseId) {
        List<TeachPlanDto> teachPlanList = teachPlanService.findTeachPlanTree(courseId);
        return ResultUtils.success(teachPlanList);
    }

    /**
     * 根据有无传 id 判断是新增还是修改， 有 id 则修改，无 id 则新增
     *
     * @param saveTeachPlanDto 保存课程计划的 dto
     */
    @ApiOperation("课程计划参加或修改")
    @PostMapping("/teach-plan")
    public BaseResponse<Boolean> saveTeachPlan(@RequestBody SaveTeachPlanDto saveTeachPlanDto) {
        teachPlanService.saveTeachPlan(saveTeachPlanDto);
        return ResultUtils.success(true);
    }

    @ApiOperation(value = "课程计划和媒资信息绑定")
    @PostMapping("/teach-plan/association/media")
    public BaseResponse<Boolean> associationMedia(@RequestBody BindTeachPlanMediaDto bindTeachPlanMediaDto) {
        teachPlanService.associationMedia(bindTeachPlanMediaDto);
        return ResultUtils.success(true);
    }

    @ApiOperation("课程计划解除媒资信息绑定")
    @DeleteMapping("/teach-plan/association/media/{teachPlanId}/{mediaId}")
    public BaseResponse<Boolean> unAssociationMedia(@PathVariable Long teachPlanId, @PathVariable String mediaId) {
        teachPlanService.unAssociationMedia(teachPlanId, mediaId);
        return ResultUtils.success(true);
    }

    @ApiOperation("课程计划删除")
    @DeleteMapping("/teachPlan/{teachPlanId}")
    public BaseResponse<Boolean> deleteTeachPlan(@PathVariable Long teachPlanId) {
        teachPlanService.deleteTeachPlan(teachPlanId);
        return ResultUtils.success(true);
    }
}