package com.dango.learning.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.learning.model.dto.ChooseCourseDto;
import com.dango.learning.model.dto.CourseTablesDto;
import com.dango.learning.model.entity.CourseTables;

/**
* @author dango
* @description 针对表【course_tables】的数据库操作Service
* @createDate 2024-03-23 21:28:52
*/
public interface CourseTablesService extends IService<CourseTables> {
    /**
     * 添加选课
     * @param userId
     * @param courseId
     * @return
     */
    ChooseCourseDto addChooseCourse(String userId, Long courseId);

    /**
     * @description 判断学习资格
     * @param userId
     * @param courseId
     * @return CourseTablesDto 学习资格状态 [{"code":"702001","desc":"正常学习"},{"code":"702002","desc":"没有选课或选课后没有支付"},{"code":"702003","desc":"已过期需要申请续期或重新支付"}]
     * @author Mr.M
     * @date 2022/10/3 7:37
     */
    public CourseTablesDto getLearningStatus(String userId, Long courseId);

}
