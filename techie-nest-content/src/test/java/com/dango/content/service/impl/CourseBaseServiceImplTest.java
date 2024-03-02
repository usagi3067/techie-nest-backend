package com.dango.content.service.impl;

import com.dango.content.model.dto.QueryCoursePageReq;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.service.CourseBaseService;
import com.dango.model.PageParams;
import com.dango.model.PageResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

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
    void queryCoursePageList() {
        // 查询条件
        QueryCoursePageReq queryCoursePageReq = new QueryCoursePageReq();
        queryCoursePageReq.setCourseName("java");
        queryCoursePageReq.setAuditStatus("202004");
        queryCoursePageReq.setPublishStatus("203001");
        // 分页参数
        PageParams pageParams = new PageParams();
        pageParams.setPageNo(1);
        pageParams.setPageSize(10);
        PageResult<CourseBase> pageResult = courseBaseService.queryCoursePageList(pageParams, queryCoursePageReq);
        log.info("{}", pageResult);
    }
}