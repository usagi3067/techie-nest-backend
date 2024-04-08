package com.dango.content.mapper;

import com.dango.content.model.entity.CoursePublish;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author dango
* @description 针对表【course_publish(课程发布)】的数据库操作Mapper
* @createDate 2024-04-04 09:37:31
* @Entity com.dango.content.model.entity.CoursePublish
*/
public interface CoursePublishMapper extends BaseMapper<CoursePublish> {

    void incrementCountStudy(Long countId);
}




