package com.dango.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.model.entity.CourseCategory;
import com.dango.content.mapper.CourseCategoryMapper;
import com.dango.content.service.CourseCategoryService;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【course_category(课程分类)】的数据库操作Service实现
* @createDate 2024-03-03 15:47:30
*/
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory>
    implements CourseCategoryService {

}




