package com.dango.content.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.model.entity.CoursePublish;

import java.io.File;

/**
 * @author dango
 * @description 针对表【course_publish(课程发布)】的数据库操作Service
 * @createDate 2024-03-20 09:15:15
 */
public interface CoursePublishService extends IService<CoursePublish> {
    /**
     * 获取课程预览信息
     *
     * @param courseId 课程ID
     * @return com.dango.content.model.dto.CoursePreviewDto
     * @author dango
     * @date 2024-03-19
     */
    CoursePreviewDto fetchCoursePreviewInfo(Long courseId);

    /**
     * 发布课程
     *
     * @param companyId 公司ID
     * @param courseId  课程ID
     * @author dango
     * @date 2024-03-19
     */
    void publish(Long companyId, Long courseId);

    /**
     * 获取课程预览信息
     * @param courseId 课程ID
     * @return com.dango.content.model.dto.CoursePreviewDto
     */
    public CoursePreviewDto getCoursePreviewInfo(Long courseId);

    /**
     * @description 课程静态化
     * @param courseId  课程id
     * @return File 静态化文件
     * @author dango
     * @date 2024-03-21
     */
    public File generateCourseHtml(Long courseId);
    /**
     * @description 上传课程静态化页面
     * @param file  静态化文件
     * @author dango
     * @date 2024-03-21
     */
    public void  uploadCourseHtml(Long courseId, File file);

    CoursePublish getCoursePublish(Long courseId);
}
