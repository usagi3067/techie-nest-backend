package com.dango.content.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dango.content.model.dto.QueryCoursePageDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author dango
 * @description
 * @date
 */
@SpringBootTest
class CourseBaseMapperTest {
    @Autowired
    CourseBaseMapper courseBaseMapper;

    // 测试 mapper
    @Test
    void testCourseBaseMapper() {
        CourseBase courseBase = courseBaseMapper.selectById(74L);
        Assertions.assertNotNull(courseBase);

        // 测试查询接口
        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        QueryCoursePageDto req = QueryCoursePageDto.builder().courseName("java")
                .build();
        queryWrapper.like(req.getCourseName() != null, CourseBase::getName, req.getCourseName());
        PageParams pageParams = new PageParams();
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());
        Page<CourseBase> res = courseBaseMapper.selectPage(page, queryWrapper);
        List<CourseBase> items = res.getRecords();
        long total = res.getTotal();
        PageResult<CourseBase> pageResult = new PageResult<>(items, total, pageParams.getPageNo(), pageParams.getPageSize());

        System.out.println(pageResult);
    }
}