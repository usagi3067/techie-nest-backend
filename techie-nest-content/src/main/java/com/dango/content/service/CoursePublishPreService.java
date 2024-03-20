package com.dango.content.service;

import com.dango.content.model.entity.CoursePublishPre;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author dango
 * @description 针对表【course_publish_pre(课程发布)】的数据库操作Service
 * @createDate 2024-03-20 16:49:35
 */
public interface CoursePublishPreService extends IService<CoursePublishPre> {
    /**
     * 提交课程审核
     *
     * @param companyId 机构ID
     * @param courseId  课程ID
     */
    void commitAudit(Long companyId, Long courseId);
}
