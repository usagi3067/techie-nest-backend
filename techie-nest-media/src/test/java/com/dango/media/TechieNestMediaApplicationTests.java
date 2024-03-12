package com.dango.media;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.UploadObjectArgs;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.InputStream;

@SpringBootTest
class TechieNestMediaApplicationTests {

    static MinioClient minioClient =
            MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("root", "A10251123a")
                    .build();


    @SneakyThrows
    @Test
    void upload() {
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("testbucket")
                        .object("sora001.mp4")
                        .filename("/Users/dango/Movies/sora/Sora AI Video Generator.mp4")
                        .build());
        System.out.println("上传成功");
    }

    @SneakyThrows
    @Test
    void delete() {
        minioClient.removeObject(
                RemoveObjectArgs.builder().bucket("testbucket").object("sora001.mp4").build());
        System.out.println("删除成功");
    }

    @SneakyThrows
    @Test
    void getObject() {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("testbucket")
                        .object("sora001.mp4")
                        .build());
             FileOutputStream fileOutputStream = new FileOutputStream("/Users/dango/Movies/sora/tmp.mp4")) {
            // Read data from stream
            IOUtils.copy(stream, fileOutputStream);
            System.out.println("下载成功");
        }
    }

}
