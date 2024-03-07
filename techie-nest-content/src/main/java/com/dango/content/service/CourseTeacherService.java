package com.dango.content.service;

import com.dango.content.model.entity.CourseTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author dango
* @description 针对表【course_teacher(课程-教师关系表)】的数据库操作Service
* @createDate 2024-03-07 16:41:37
*/
public interface CourseTeacherService extends IService<CourseTeacher> {

    List<CourseTeacher> list(Long courseId);
}
