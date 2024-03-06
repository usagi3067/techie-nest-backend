package com.dango.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.TeachPlanMapper;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.TeachPlan;
import com.dango.content.service.TeachPlanService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author dango
* @description 针对表【teach_plan(课程计划)】的数据库操作Service实现
* @createDate 2024-03-06 11:03:10
*/
@Service
public class TeachPlanServiceImpl extends ServiceImpl<TeachPlanMapper, TeachPlan>
    implements TeachPlanService{

    @Override
    public List<TeachPlanDto> findTeachPlanTree(long courseId) {
        return baseMapper.findTeachPlanList(courseId);
    }
}




