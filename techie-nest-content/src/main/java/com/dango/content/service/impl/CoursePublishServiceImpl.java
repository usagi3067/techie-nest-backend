package com.dango.content.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.config.MultipartSupportConfig;
import com.dango.content.feignclient.MediaServiceClient;
import com.dango.content.mapper.CourseBaseMapper;
import com.dango.content.mapper.CoursePublishMapper;
import com.dango.content.mapper.CoursePublishPreMapper;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.CoursePreviewDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.model.entity.CoursePublish;
import com.dango.content.model.entity.CoursePublishPre;
import com.dango.content.service.CourseBaseService;
import com.dango.content.service.CoursePublishService;
import com.dango.content.service.TeachPlanService;
import com.dango.exception.BusinessException;
import com.dango.exception.CommonError;
import com.dango.messagesdk.domain.entity.MqMessage;
import com.dango.messagesdk.service.MqMessageService;
import com.dango.model.state.CourseStatus;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author dango
* @description 针对表【course_publish(课程发布)】的数据库操作Service实现
* @createDate 2024-03-20 09:15:15
*/
@Service
@Slf4j
public class CoursePublishServiceImpl extends ServiceImpl<CoursePublishMapper, CoursePublish>
    implements CoursePublishService{

    @Resource
    private CourseBaseService courseBaseService;

    @Resource
    private TeachPlanService teachPlanService;
    
    @Resource
    private CoursePublishPreMapper coursePublishPreMapper;

    @Resource
    private CourseBaseMapper courseBaseMapper;

    @Resource
    private MqMessageService mqMessageService;

    @Resource
    private MediaServiceClient mediaServiceClient;



    /**
     * 获取课程预览信息
     *
     * @param courseId 课程ID
     * @return com.dango.content.model.dto.CoursePreviewDto
     */
    @Override
    public CoursePreviewDto fetchCoursePreviewInfo(Long courseId) {
        // 获取课程基本信息和营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseService.getCourseBaseInfo(courseId);

        // 获取课程计划信息
        List<TeachPlanDto> teachPlanTree = teachPlanService.findTeachPlanTree(courseId);

        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();
        coursePreviewDto.setCourseBase(courseBaseInfo);
        coursePreviewDto.setTeachPlans(teachPlanTree);
        return coursePreviewDto;
    }

    @Transactional
    @Override
    public void publish(Long companyId, Long courseId) {
        // 约束校验
        // 查询课程预发布表
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPre == null) {
            throw new BusinessException("请先提交课程审核，审核通过才可以发布");
        }

        // 本机构只允许提交本机构的课程
        if (!coursePublishPre.getCompanyId().equals(companyId)) {
            throw new BusinessException("不允许提交其他机构的课程");
        }

        // 课程审核状态
        String auditStatus = coursePublishPre.getStatus();
        // 审核通过方可发布
        if (!"202004".equals(auditStatus)) {
            throw new BusinessException("操作失败，课程审核通过方可发布");
        }

        // 保存课程发布信息
        saveCoursePublishInfo(courseId);

        // 保存消息表记录
        saveCoursePublishMessageRecord(courseId);

        // 删除课程预发布表对应记录
        coursePublishPreMapper.deleteById(courseId);
    }

    /**
     * 获取课程预览信息
     * @param courseId 课程ID
     * @return com.dango.content.model.dto.CoursePreviewDto
     */
    @Override
    public CoursePreviewDto getCoursePreviewInfo(Long courseId) {
        CoursePreviewDto coursePreviewDto = new CoursePreviewDto();

        // 课程详情信息（包括课程基本信息和营销信息）
        CourseBaseInfoDto courseBaseInfo = courseBaseService.getCourseBaseInfo(courseId);
        coursePreviewDto.setCourseBase(courseBaseInfo);

        // 课程计划信息
        List<TeachPlanDto> teachPlanTree = teachPlanService.findTeachPlanTree(courseId);
        coursePreviewDto.setTeachPlans(teachPlanTree);

        return coursePreviewDto;
    }
    @Override
    public File generateCourseHtml(Long courseId) {

        //静态化文件
        File htmlFile  = null;

        try {
            //配置freemarker
            Configuration configuration = new Configuration(Configuration.getVersion());

            //加载模板
            //选指定模板路径,classpath下templates下
            //得到classpath路径
            String classpath = this.getClass().getResource("/").getPath();
            configuration.setDirectoryForTemplateLoading(new File(classpath + "/templates/"));
            //设置字符编码
            configuration.setDefaultEncoding("utf-8");

            //指定模板文件名称
            Template template = configuration.getTemplate("course_template.ftl");

            //准备数据
            CoursePreviewDto coursePreviewInfo = this.getCoursePreviewInfo(courseId);

            Map<String, Object> map = new HashMap<>();
            map.put("model", coursePreviewInfo);

            //静态化
            //参数1：模板，参数2：数据模型
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
            //将静态化内容输出到文件中
            InputStream inputStream = IOUtils.toInputStream(content);
            //创建静态化文件
            htmlFile = File.createTempFile("course",".html");
            log.debug("课程静态化，生成静态文件:{}",htmlFile.getAbsolutePath());
            //输出流
            FileOutputStream outputStream = new FileOutputStream(htmlFile);
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            log.error("课程静态化异常:{}",e.toString());
            throw new BusinessException("课程静态化异常");
        }

        return htmlFile;
    }

    @Override
    public void uploadCourseHtml(Long courseId, File file) {
        MultipartFile multipartFile = MultipartSupportConfig.getMultipartFile(file);
        String course = mediaServiceClient.upload(multipartFile, "course/"+courseId+".html");
        if(course==null){
            throw new BusinessException("上传静态文件异常");
        }
    }

    public CoursePublish getCoursePublish(Long courseId){
        CoursePublish coursePublish = baseMapper.selectById(courseId);
        return coursePublish ;
    }



    /**
     * 保存课程发布信息
     * @param courseId  课程ID
     */
    private void saveCoursePublishInfo(Long courseId) {
        // 整合课程发布信息
        // 查询课程预发布表
        CoursePublishPre coursePublishPre = coursePublishPreMapper.selectById(courseId);
        if (coursePublishPre == null) {
            throw new BusinessException("课程预发布数据为空");
        }

        CoursePublish coursePublish = new CoursePublish();

        // 拷贝到课程发布对象
        BeanUtils.copyProperties(coursePublishPre, coursePublish);
        coursePublish.setStatus(CourseStatus.PUBLISHED.getCode());
        CoursePublish coursePublishUpdate = baseMapper.selectById(courseId);
        if (coursePublishUpdate == null) {
            baseMapper.insert(coursePublish);
        } else {
            baseMapper.updateById(coursePublish);
        }

        // 更新课程基本表的发布状态
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setStatus("203002");
        courseBaseMapper.updateById(courseBase);
    }

    /**
     * 保存消息表记录
     * @param courseId  课程ID
     */
    private void saveCoursePublishMessageRecord(Long courseId) {
        MqMessage mqMessage = mqMessageService.addMessage("course_publish", String.valueOf(courseId), null, null);
        if(mqMessage==null){
            throw new BusinessException(CommonError.QUERY_NULL.getErrMessage());
        }

    }


}




