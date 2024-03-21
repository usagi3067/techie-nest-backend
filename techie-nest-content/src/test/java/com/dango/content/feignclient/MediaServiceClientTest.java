package com.dango.content.feignclient;

import com.dango.content.config.MultipartSupportConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;

/**
 * @author dango
 * @description
 * @date
 */
@SpringBootTest
public class MediaServiceClientTest {

    @Resource
    MediaServiceClient mediaServiceClient;

    //远程调用，上传文件
    @Test
    public void test() {

        MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(new File("/Users/dango/Documents/code/techie-nest/techie-nest-backend/techie-nest-content/test.html"));
        mediaServiceClient.upload(multipartFile,"course/test.html");
        System.out.println("上传成功");
    }

}
