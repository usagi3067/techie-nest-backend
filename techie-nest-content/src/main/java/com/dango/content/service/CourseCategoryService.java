package com.dango.content.service;

import com.dango.content.model.dto.CourseCategoryTreeDto;
import com.dango.content.model.entity.CourseCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author dango
* @description 针对表【course_category(课程分类)】的数据库操作Service
* @createDate 2024-03-03 15:47:30
*/
public interface CourseCategoryService extends IService<CourseCategory> {

    /**
     * 课程分类树形结构查询
     * @param id 父节点id
     * @return 课程分类树形结构
     */
    List<CourseCategoryTreeDto> queryTreeNodes(String id);
}
