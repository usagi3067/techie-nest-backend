package com.dango.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.content.model.dto.BindTeachPlanMediaDto;
import com.dango.content.model.dto.SaveTeachPlanDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.TeachPlan;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* @author dango
* @description 针对表【teach_plan(课程计划)】的数据库操作Service
* @createDate 2024-03-06 11:03:10
*/
public interface TeachPlanService extends IService<TeachPlan> {
    /**
     * 查询课程计划树
     * @param courseId 课程id
     * @return 课程计划树
     */
    List<TeachPlanDto> findTeachPlanTree(long courseId);

    void saveTeachPlan(SaveTeachPlanDto saveTeachPlanDto);

    /**
     * 关联媒资文件
     *
     * @param bindTeachPlanMediaDto 关联媒资文件dto
     */
    @Transactional
    void associationMedia(BindTeachPlanMediaDto bindTeachPlanMediaDto);

    /** 解绑教学计划与媒资信息
     * @param teachPlanId       教学计划id
     * @param mediaId           媒资信息id
     */
    void unAssociationMedia(Long teachPlanId, String mediaId);

    @Transactional
    void deleteTeachPlan(Long teachPlanId);
}
