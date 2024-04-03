package com.dango.learning.service;

import com.dango.model.RestResponse;

/**
 * @author dango
 * @description
 * @date 2024-03-25
 */
public interface LearningService {

    /**
     * @description 获取教学视频
     * @param courseId 课程id
     * @param teachplanId 课程计划id
     * @param mediaId 视频文件id
     * @return com.xuecheng.base.model.RestResponse<java.lang.String>
     * @author Mr.M
     * @date 2022/10/5 9:08
     */
    public String getVideo(String userId, Long courseId, Long teachplanId, String mediaId);

}
