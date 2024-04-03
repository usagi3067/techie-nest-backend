package com.dango.learning.feignclient;

import com.dango.TechieNestLearningApplication;
import com.dango.learning.model.dto.CoursePublish;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dango
 * @description
 * @date
 */
@SpringBootTest(classes = TechieNestLearningApplication.class)
class ContentServiceClientTest {
    @Autowired
    ContentServiceClient contentServiceClient;

//    @Test
//    public void testContentServiceClient(){
//        CoursePublish coursepublish = contentServiceClient.getCoursepublish(74L);
//        Assertions.assertNotNull(coursepublish);
//    }


}