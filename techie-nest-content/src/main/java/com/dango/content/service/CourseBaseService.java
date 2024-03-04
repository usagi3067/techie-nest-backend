package com.dango.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.content.model.dto.QueryCoursePageDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.model.PageParams;
import com.dango.model.PageResult;


/**
* @author dango
* @description 针对表【course_base(课程基本信息表)】的数据库操作Service
* @createDate 2024-01-18 11:57:33
*/
public interface CourseBaseService extends IService<CourseBase> {
    /**
     * 分页查询课程列表
     * @param pageParam 分页参数
     * @param queryCoursePageDto 查询条件
     * @return 课程列表
     */
    PageResult<CourseBase> queryCoursePageList(PageParams pageParam, QueryCoursePageDto queryCoursePageDto);
}
