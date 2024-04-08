package com.dango.learning.feignclient;

import com.dango.learning.model.dto.CoursePublishDto;
import com.dango.learning.model.dto.LecturerBalanceDto;
import com.dango.model.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mr.M
 * @version 1.0
 * @description 内容管理远程接口
 * @date 2022/10/25 9:13
 */
@FeignClient(value = "content")
public interface ContentServiceClient {

    @ResponseBody
    @GetMapping("/api/content/r/coursepublish/{courseId}")
    public BaseResponse<CoursePublishDto> getCoursepublish(@PathVariable("courseId") Long courseId);

    @ResponseBody
    @PostMapping("/api/content/course/count/{courseId}")
    BaseResponse<Boolean> updateCourseStudyCount(@PathVariable("courseId") Long courseId);


    @ResponseBody
    @PostMapping("/api/content/lecturer")
    void updateBalance(@RequestBody LecturerBalanceDto lecturerBalanceDto);
}
