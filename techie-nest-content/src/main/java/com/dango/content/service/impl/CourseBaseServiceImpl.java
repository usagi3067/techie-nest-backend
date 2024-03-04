package com.dango.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.CourseBaseMapper;
import com.dango.content.model.dto.QueryCoursePageDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.service.CourseBaseService;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【course_base(课程基本信息表)】的数据库操作Service实现
* @createDate 2024-01-18 11:57:33
*/
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase>
    implements CourseBaseService {

    /**
     * 分页查询课程列表
     * @param pageParam 分页参数
     * @param queryCoursePageDto 查询条件
     * @return 课程列表
     */
    @Override
    public PageResult<CourseBase> queryCoursePageList(PageParams pageParam, QueryCoursePageDto queryCoursePageDto) {
        // 1. 构建查询对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        // 1.1 课程名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(queryCoursePageDto.getCourseName()), CourseBase::getName, queryCoursePageDto.getCourseName());
        // 1.2 课程审核状态精确查询
        queryWrapper.eq(StringUtils.isNotBlank(queryCoursePageDto.getAuditStatus()), CourseBase::getAuditStatus, queryCoursePageDto.getAuditStatus());
        // 1.3 课程发布状态精确查询
        queryWrapper.eq(StringUtils.isNotBlank(queryCoursePageDto.getPublishStatus()), CourseBase::getStatus, queryCoursePageDto.getPublishStatus());

        // 2. 分页查询
        // 2.1 构建分页对象
        Page<CourseBase> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        // 2.2 分页查询
        Page<CourseBase> res = baseMapper.selectPage(page, queryWrapper);
        // 2.3 封装结果
        return new PageResult<>(res.getRecords(), res.getTotal(), pageParam.getPageNo(), pageParam.getPageSize());
    }
}




