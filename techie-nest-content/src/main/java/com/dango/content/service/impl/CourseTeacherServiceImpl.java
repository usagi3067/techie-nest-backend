package com.dango.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.model.entity.CourseTeacher;
import com.dango.content.service.CourseTeacherService;
import com.dango.content.mapper.CourseTeacherMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author dango
* @description 针对表【course_teacher(师资编辑接口关系表)】的数据库操作Service实现
* @createDate 2024-03-07 16:41:37
*/
@Service
public class CourseTeacherServiceImpl extends ServiceImpl<CourseTeacherMapper, CourseTeacher>
    implements CourseTeacherService{
    @Override
    public List<CourseTeacher> list(Long courseId) {
        //根据 courseId 查询课程教师关系
        LambdaQueryWrapper<CourseTeacher> courseTeacherLambdaQueryWrapper = new LambdaQueryWrapper<>();
        courseTeacherLambdaQueryWrapper.eq(CourseTeacher::getCourseId, courseId);
        return baseMapper.selectList(courseTeacherLambdaQueryWrapper);
    }
}




