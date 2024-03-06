package com.dango.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.CourseBaseMapper;
import com.dango.content.mapper.CourseCategoryMapper;
import com.dango.content.mapper.CourseMarketMapper;
import com.dango.content.model.dto.*;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.service.CourseBaseService;
import com.dango.exception.BusinessException;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Override
    public CourseBaseInfoDto addCourseBase(Long companyId, AddCourseDto addCourseDto) {
        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(addCourseDto, courseBase);
        // 设置审核状态 todo 提取枚举类
        courseBase.setAuditStatus("202002");
        // 设置发布状态 todo 提取枚举类
        courseBase.setStatus("203001");
        // 设置机构id
        courseBase.setCompanyId(companyId);

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
        if (Objects.nonNull(courseMarket)) {
            BeanUtils.copyProperties(courseMarket, courseBaseInfoDto);
        }

        // 补充分类名称
        String mtName = courseCategoryMapper.selectById(courseBase.getMt()).getName();
        String stName = courseCategoryMapper.selectById(courseBase.getSt()).getName();
        courseBaseInfoDto.setMtName(mtName);
        courseBaseInfoDto.setStName(stName);

        return courseBaseInfoDto;
    }

    @Transactional
    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        Long courseId = dto.getId();
        CourseBase courseBase = baseMapper.selectById(courseId);
        if (Objects.isNull(courseBase)) {
            BusinessException.cast("课程不存在");
        }

        // 校验教学机构只能修改自己的课程
        if (!companyId.equals(courseBase.getCompanyId())) {
            BusinessException.cast("无权限修改他人课程");
        }

        // 封装课程基本信息
        BeanUtils.copyProperties(dto, courseBase);
        courseBase.setDateUpdated(LocalDateTime.now());

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

    private void saveCourseMarket(CourseMarket courseMarket) {
        // 参数合法性校验
        String charge = courseMarket.getCharge();
        if (StringUtils.isEmpty(charge)) {
            throw new RuntimeException("收费规则不能为空");
        }

        // 如果课程收费， 价格没有填写也需要抛出异常  todo 枚举类提取
        if ("201001".equals(charge)) {
            if (Objects.isNull(courseMarket.getPrice()) || courseMarket.getPrice().floatValue() <= 0) {
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




