package com.dango.auth.ucenter.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "checkcode")
public interface CheckCodeClient {

 @PostMapping(value = "/api/checkcode/verify")
 Boolean verify(@RequestParam("key") String key, @RequestParam("code") String code);

}