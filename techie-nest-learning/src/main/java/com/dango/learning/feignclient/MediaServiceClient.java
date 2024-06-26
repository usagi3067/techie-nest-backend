package com.dango.learning.feignclient;


import com.dango.model.BaseResponse;
import com.dango.model.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description TODO
 * @author Mr.M
 * @date 2022/10/27 9:04
 * @version 1.0
 */
 @FeignClient(value = "media")
 public interface MediaServiceClient {

  @GetMapping("api/media/open/preview/{mediaId}")
  public BaseResponse<String> getPlayUrlByMediaId(@PathVariable("mediaId") String mediaId);

 }
