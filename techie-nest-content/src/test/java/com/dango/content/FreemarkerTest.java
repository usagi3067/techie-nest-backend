package com.dango.content;

import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.service.CoursePublishService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.apache.commons.io.IOUtils;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author dango
 * @description
 * @date 2024-03-21
 */
@SpringBootTest
public class FreemarkerTest {
    @Resource
    CoursePublishService coursePublishService;

    @Test
    public void testFreemarker() throws IOException, TemplateException {
        // 创建配置类
        Configuration configuration = new Configuration(Configuration.getVersion());

        // 设置模板路径
        String path = Objects.requireNonNull(this.getClass().getResource("/")).getPath();
        configuration.setDirectoryForTemplateLoading(new File(path + "/templates/"));
        configuration.setDefaultEncoding("utf-8");

        // 设置模板文件名称
        Template template = configuration.getTemplate("course_template.ftl");

        // 准备数据
        CoursePreviewDto coursePreviewInfo = coursePublishService.getCoursePreviewInfo(74L);

        HashMap<String, Object> map = new HashMap<>();
        map.put("model", coursePreviewInfo);

        // 静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        System.out.println(content);

        // 将静态化内容写入文件
        InputStream inputStream = IOUtils.toInputStream(content, "utf-8");
        try (FileOutputStream fileOutputStream = new FileOutputStream("test.html")) {
            IOUtils.copy(inputStream, fileOutputStream);
        }
    }
}
