package com.dango.content.service.impl;

import com.dango.content.model.dto.CourseCategoryTreeDto;
import com.dango.content.service.CourseCategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author dango
 * @description
 * @date
 */
@SpringBootTest
class CourseCategoryServiceImplTest {
    @Resource
    CourseCategoryService courseCategoryService;

    @Test
    void testQueryTreeNodes() {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryService.queryTreeNodes("1");
        System.out.println(courseCategoryTreeDtos);
    }
}