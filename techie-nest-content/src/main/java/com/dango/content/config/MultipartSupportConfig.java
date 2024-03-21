package com.dango.content.config;

import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Configuration // 表明该类是Spring的一个配置类
public class MultipartSupportConfig {

    // 自动注入HttpMessageConverters，用于请求和响应的转换
    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    // 自定义Feign的编码器Bean，使Feign支持多部分表单数据的处理
    @Bean
    @Primary // 当存在多个相同类型的Bean时，此Bean优先被注入
    @Scope("prototype") // 每次注入时都创建一个新的实例
    public Encoder feignEncoder() {
        // SpringFormEncoder用于处理多部分表单数据，这里被封装在SpringEncoder中
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    // 静态方法，将java.io.File转换为Spring的MultipartFile
    public static MultipartFile getMultipartFile(File file) {
        // 创建一个FileItem对象，表示表单中的文件部分
        FileItem item = new DiskFileItemFactory().createItem("file", MediaType.MULTIPART_FORM_DATA_VALUE, true, file.getName());
        try (FileInputStream inputStream = new FileInputStream(file); // 文件输入流
             OutputStream outputStream = item.getOutputStream()) { // 文件输出流
            IOUtils.copy(inputStream, outputStream); // 将文件内容复制到输出流，即FileItem中
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用CommonsMultipartFile封装FileItem，创建MultipartFile实例
        return new CommonsMultipartFile(item);
    }
}
