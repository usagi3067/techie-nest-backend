package com.dango.content.feignclient;

import com.dango.content.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author dango
 * @description 远程调用媒资服务的接口
 * @date 2024-03-21
 */
@FeignClient(value = "media",configuration = MultipartSupportConfig.class)
public interface MediaServiceClient {

    @RequestMapping(value = "/api/media/upload/course-file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file-data") MultipartFile filedata,
                  @RequestParam(value = "objectName", required = false) String objectName);
}

