package com.dango.content.service.impl;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.CourseBaseMapper;
import com.dango.content.mapper.CourseCategoryMapper;
import com.dango.content.mapper.CourseMarketMapper;
import com.dango.content.mapper.TeachPlanMapper;
import com.dango.content.model.dto.AddCourseDto;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.EditCourseDto;
import com.dango.content.model.dto.QueryCoursePageDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.model.entity.CourseMarket;
import com.dango.content.model.entity.TeachPlan;
import com.dango.content.service.CourseBaseService;
import com.dango.exception.BusinessException;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import com.dango.model.state.CourseAuditStatus;
import com.dango.model.state.CoursePublishStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author dango
 * @description 针对表【course_base(课程基本信息表)】的数据库操作Service实现
 * @createDate 2024-01-18 11:57:33
 */
@Service
public class CourseBaseServiceImpl extends ServiceImpl<CourseBaseMapper, CourseBase>
        implements CourseBaseService {
    @Resource
    private CourseMarketMapper courseMarketMapper;

    @Resource
    private CourseCategoryMapper courseCategoryMapper;

    @Resource
    private TeachPlanMapper teachPlanMapper;

    /**
     * 分页查询课程列表
     *
     * @param pageParam          分页参数
     * @param queryCoursePageDto 查询条件
     * @return 课程列表
     */
    @Override
    public PageResult<CourseBase> queryCoursePageList(Long lecturerId, PageParams pageParam, QueryCoursePageDto queryCoursePageDto) {
        // 1. 构建查询对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        // 1.1 课程名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(queryCoursePageDto.getCourseName()), CourseBase::getName, queryCoursePageDto.getCourseName());
        // 1.2 课程审核状态精确查询
        queryWrapper.eq(StringUtils.isNotBlank(queryCoursePageDto.getAuditStatus()), CourseBase::getAuditStatus, queryCoursePageDto.getAuditStatus());
        // 1.3 课程发布状态精确查询
        queryWrapper.eq(StringUtils.isNotBlank(queryCoursePageDto.getPublishStatus()), CourseBase::getPublishStatus, queryCoursePageDto.getPublishStatus());
        // 1.4 根据讲师id拼装查询条件
        queryWrapper.eq(CourseBase::getLecturerId, lecturerId);

        // 2. 分页查询
        // 2.1 构建分页对象
        Page<CourseBase> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        // 2.2 分页查询
        Page<CourseBase> res = baseMapper.selectPage(page, queryWrapper);
        // 2.3 封装结果
        return new PageResult<>(res.getRecords(), res.getTotal(), pageParam.getPageNo(), pageParam.getPageSize());
    }

    @Override
    public CourseBaseInfoDto addCourseBase(Long lecturerId, AddCourseDto addCourseDto) {
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDto, courseBase);
        courseBase.setTags(JSON.toJSONString(addCourseDto.getTags()));
        // 设置审核状态
        courseBase.setAuditStatus(CourseAuditStatus.NOT_SUBMITTED.getCode());
        // 设置发布状态
        courseBase.setPublishStatus(CoursePublishStatus.UNPUBLISHED.getCode());
        // 设置讲师 id
        courseBase.setLecturerId(lecturerId);

        // 插入课程基本信息表
        int insert = baseMapper.insert(courseBase);
        if (insert < 0) {
            throw new RuntimeException("新增课程基本信息失败");
        }

        // 插入营销信息表
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(addCourseDto, courseMarket);
        // 设置课程的 id
        courseMarket.setId(courseBase.getId());
        saveCourseMarket(courseMarket);
        // 从数据库查询课程的详细信息， 包括两部分
        return getCourseBaseInfo(courseBase.getId());
    }

    @Override
    public CourseBaseInfoDto getCourseBaseInfo(Long id) {
        // 从课程基本信息表中查询课程基本信息
        CourseBase courseBase = baseMapper.selectById(id);
        if (Objects.isNull(courseBase)) {
            return null;
        }
        // 从课程营销表查询
        CourseMarket courseMarket = courseMarketMapper.selectById(id);

        // 组装
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase, courseBaseInfoDto);
        // 标签数组转化
        String tags = courseBase.getTags();
        courseBaseInfoDto.setTags(JSON.parseArray(tags, String.class));
        if (Objects.nonNull(courseMarket)) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }
        // 补充分类名称
        String mainCategoryName = courseCategoryMapper.selectById(courseBase.getMainCategory()).getName();
        String subCategoryName = courseCategoryMapper.selectById(courseBase.getSubCategory()).getName();
        courseBaseInfoDto.setMainCategoryName(mainCategoryName);
        courseBaseInfoDto.setSubCategoryName(subCategoryName);

        return courseBaseInfoDto;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long lecturerId, EditCourseDto dto) {
        Long courseId = dto.getId();
        CourseBase courseBase = baseMapper.selectById(courseId);
        if (Objects.isNull(courseBase)) {
            BusinessException.cast("课程不存在");
        }

        // 校验讲师只能修改自己的课程
        if (!lecturerId.equals(courseBase.getLecturerId())) {
            BusinessException.cast("无权限修改他人课程");
        }

        // 封装课程基本信息
        BeanUtils.copyProperties(dto, courseBase);

        courseBase.setTags(JSON.toJSONString(dto.getTags()));

        // 更新课程基本信息
        baseMapper.updateById(courseBase);

        // 封装课程营销信息
        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto, courseMarket);
        // 保存课程营销信息
        saveCourseMarket(courseMarket);

        // 查询并返回课程详细信息， 包含基本信息和营销信息
        return getCourseBaseInfo(courseId);
    }

    @Transactional
    @Override
    public Boolean deleteCourse(Long lecturerId, Long courseId) {
        CourseBase courseBase = baseMapper.selectById(courseId);
        if (!lecturerId.equals(courseBase.getLecturerId()))
            throw new BusinessException("只允许删除本机构的课程");
        // 删除课程计划
        LambdaQueryWrapper<TeachPlan> teachplanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teachplanLambdaQueryWrapper.eq(TeachPlan::getCourseId, courseId);
        teachPlanMapper.delete(teachplanLambdaQueryWrapper);
        // 删除营销信息
        courseMarketMapper.deleteById(courseId);
        // 删除课程基本信息
        baseMapper.deleteById(courseId);

        return true;
    }

    @Override
    public PageResult<CourseBase> queryCoursePageList(PageParams pageParam, QueryCoursePageDto queryCoursePageDto) {
        // 1. 构建查询对象
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        // 1.1 课程名称模糊查询
        queryWrapper.like(StringUtils.isNotBlank(queryCoursePageDto.getCourseName()), CourseBase::getName, queryCoursePageDto.getCourseName());
        // 1.2 课程审核状态精确查询
        queryWrapper.eq(StringUtils.isNotBlank(queryCoursePageDto.getAuditStatus()), CourseBase::getAuditStatus, queryCoursePageDto.getAuditStatus());
        // 1.3 课程发布状态精确查询
        queryWrapper.eq(StringUtils.isNotBlank(queryCoursePageDto.getPublishStatus()), CourseBase::getPublishStatus, queryCoursePageDto.getPublishStatus());

        // 2. 分页查询
        // 2.1 构建分页对象
        Page<CourseBase> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        // 2.2 分页查询
        Page<CourseBase> res = baseMapper.selectPage(page, queryWrapper);
        // 2.3 封装结果
        return new PageResult<>(res.getRecords(), res.getTotal(), pageParam.getPageNo(), pageParam.getPageSize());

    }

    private void saveCourseMarket(CourseMarket courseMarket) {
        // 参数合法性校验
        Integer isFree = courseMarket.getIsFree();
        if (isFree == null) {
            throw new RuntimeException("收费规则不能为空");
        }

        // 课程收费
        if (isFree == 0) {
            if (Objects.isNull(courseMarket.getPrice()) || courseMarket.getPrice() <= 0) {
                throw new RuntimeException("课程收费，价格不能为空且大于0");
            }
        }

        // 从数据库查询营销信息， 存在则更新， 不存在则添加
        Long id = courseMarket.getId();
        CourseMarket courseMarketTmp = courseMarketMapper.selectById(id);
        if (Objects.isNull(courseMarketTmp)) {
            courseMarketMapper.insert(courseMarket);
        } else {
            courseMarketMapper.updateById(courseMarket);
        }
    }
}




