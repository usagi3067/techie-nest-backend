package com.dango.learning.controller;

import com.dango.model.RestResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dango
 * @description
 * @date 2024-03-24
 */
@Api(value = "学习过程管理接口", tags = "学习过程管理接口")
@Slf4j
@RestController
public class TestController {


    @ApiOperation("获取视频")
    @GetMapping("/hello")
    public RestResponse<String> getvideo() {

        RestResponse<String> stringRestResponse = new RestResponse<>();

        return stringRestResponse;

    }

}

