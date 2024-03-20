package com.dango.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.CoursePublishMapper;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.CoursePublish;
import com.dango.content.service.CourseBaseService;
import com.dango.content.service.CoursePublishService;
import com.dango.content.service.TeachPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author dango
* @description 针对表【course_publish(课程发布)】的数据库操作Service实现
* @createDate 2024-03-20 09:15:15
*/
@Service
public class CoursePublishServiceImpl extends ServiceImpl<CoursePublishMapper, CoursePublish>
    implements CoursePublishService{

    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private TeachPlanService teachPlanService;

    /**
     * 获取课程预览信息
     *
     * @param courseId 课程ID
     * @return com.dango.content.model.dto.CoursePreviewDto
     */
    @Override
    public CoursePreviewDto fetchCoursePreviewInfo(Long courseId) {
        // 获取课程基本信息和营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseService.getCourseBaseInfo(courseId);

        // 获取课程计划信息
        List<TeachPlanDto> teachPlanTree = teachPlanService.findTeachPlanTree(courseId);

        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        coursePreviewDto.setCourseBase(courseBaseInfo);
        coursePreviewDto.setTeachPlans(teachPlanTree);
        return coursePreviewDto;
    }
}




