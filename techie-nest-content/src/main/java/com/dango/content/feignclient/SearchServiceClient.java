package com.dango.content.feignclient;


import com.dango.content.service.jobHandler.CourseIndex;
import com.dango.model.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "search")
public interface SearchServiceClient {

 @PostMapping("/api/search/index/course")
 BaseResponse<Boolean> add(@RequestBody CourseIndex courseIndex);
}