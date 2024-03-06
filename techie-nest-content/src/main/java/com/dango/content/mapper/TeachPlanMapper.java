package com.dango.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.TeachPlan;

import java.util.List;

/**
 * @author dango
 * @description 针对表【teach_plan(课程计划)】的数据库操作Mapper
 * @createDate 2024-03-06 11:03:10
 * @Entity com.dango.content.model.entity.TeachPlan
 */
public interface TeachPlanMapper extends BaseMapper<TeachPlan> {


    /**
     * 查询课程计划树
     * @param courseId 课程id
     * @return 课程计划树
     */
    List<TeachPlanDto> findTeachPlanList(long courseId);
}




