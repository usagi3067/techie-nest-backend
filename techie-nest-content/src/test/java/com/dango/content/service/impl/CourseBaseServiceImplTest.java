package com.dango.content.service.impl;

import com.dango.content.model.dto.AddCourseDto;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.service.CourseBaseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author dango
 * @description
 * @date
 */
@Slf4j
@SpringBootTest
class CourseBaseServiceImplTest {
    @Resource
    private CourseBaseService courseBaseService;


    @BeforeEach
    void setUp() {
        System.out.println("课程基本信息表测试开始");
    }

    @AfterEach
    void tearDown() {
        System.out.println("课程基本信息表测试结束");
    }



    @Test
    @Transactional
    public void testAddCourseBase() {
        // 使用方法创建测试数据
        AddCourseDto addCourseDto = createTestAddCourseDto();

        // 调用待测试的方法
        CourseBaseInfoDto result = courseBaseService.addCourseBase(1L, addCourseDto);

        // 验证结果
        assertNotNull(result);
    }


    private AddCourseDto createTestAddCourseDto() {
        AddCourseDto addCourseDto = new AddCourseDto();
        addCourseDto.setName("测试课程");
        addCourseDto.setMainCategory("技术");
        addCourseDto.setSubCategory("编程");
        addCourseDto.setIsFree(0);
        addCourseDto.setPrice(100.0);
        addCourseDto.setOriginalPrice(150.0);
        addCourseDto.setPic("test.jpg");
        // 根据需要添加其他字段
        return addCourseDto;
    }

}