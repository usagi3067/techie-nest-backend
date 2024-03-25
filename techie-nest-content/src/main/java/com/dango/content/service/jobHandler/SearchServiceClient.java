package com.dango.content.service.jobHandler;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "search")
public interface SearchServiceClient {

 @PostMapping("/api/search/index/course")
 Boolean add(@RequestBody CourseIndex courseIndex);
}