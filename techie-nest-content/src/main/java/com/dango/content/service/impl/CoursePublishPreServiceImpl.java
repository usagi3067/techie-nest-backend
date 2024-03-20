package com.dango.content.service.impl;

import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.CourseBaseMapper;
import com.dango.content.mapper.CourseMarketMapper;
import com.dango.content.mapper.CoursePublishPreMapper;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.CourseMarket;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.model.entity.CoursePublishPre;
import com.dango.content.service.CourseBaseService;
import com.dango.content.service.CoursePublishPreService;
import com.dango.content.service.TeachPlanService;
import com.dango.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
* @author dango
* @description 针对表【course_publish_pre(课程发布)】的数据库操作Service实现
* @createDate 2024-03-20 16:49:35
*/
@Service
public class CoursePublishPreServiceImpl extends ServiceImpl<CoursePublishPreMapper, CoursePublishPre>
    implements CoursePublishPreService{
    
    @Resource 
    private CourseBaseMapper courseBaseMapper;

    @Resource
    private CourseBaseService courseBaseService;

    @Resource
    private CourseMarketMapper courseMarketMapper;

    @Resource
    private TeachPlanService teachPlanService;

    /**
     * 提交课程审核
     *
     * @param companyId 机构ID
     * @param courseId  课程ID
     */
    @Override
    public void commitAudit(Long companyId, Long courseId) {

        // 查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);

        // 获取课程审核状态
        String auditStatus = courseBase.getAuditStatus();

        // 如果当前审核状态为已提交，则不允许再次提交
        if ("202003".equals(auditStatus)) {
            throw new BusinessException("当前为等待审核状态，审核完成可以再次提交。");
        }

        // 只允许提交本机构的课程
        if (!courseBase.getCompanyId().equals(companyId)) {
            throw new BusinessException("不允许提交其它机构的课程。");
        }

        // 检查课程图片是否填写
        if (StringUtils.isEmpty(courseBase.getPic())) {
            throw new BusinessException("提交失败，请上传课程图片");
        }

        // 添加课程预发布记录
        CoursePublishPre coursePublishPre = new CoursePublishPre();

        // 获取课程基本信息以及部分营销信息
        CourseBaseInfoDto courseBaseInfo = courseBaseService.getCourseBaseInfo(courseId);
        BeanUtils.copyProperties(courseBaseInfo, coursePublishPre);

        // 获取课程营销信息
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);

        // 转为json格式
        Gson gson = new Gson();
        String courseMarketJson = gson.toJson(courseMarket);
        coursePublishPre.setMarket(courseMarketJson);

        // 查询课程计划信息
        List<TeachPlanDto> teachPlanTree = teachPlanService.findTeachPlanTree(courseId);

        if (teachPlanTree.size() <= 0) {
            throw new BusinessException("提交失败，还没有添加课程计划");
        }

        // 转为json格式
        String teachplanTreeString = gson.toJson(teachPlanTree);
        coursePublishPre.setTeachPlan(teachplanTreeString);

        // 设置预发布记录状态为已提交
        coursePublishPre.setStatus("202003");

        // 设置教学机构id
        coursePublishPre.setCompanyId(companyId);

        // 设置提交时间
        coursePublishPre.setCreateDate(LocalDateTime.now());

        CoursePublishPre coursePublishPreUpdate = baseMapper.selectById(courseId);

        if (coursePublishPreUpdate == null) {
            // 添加课程预发布记录
            baseMapper.insert(coursePublishPre);
        } else {
            baseMapper.updateById(coursePublishPre);
        }

        // 更新课程基本表的审核状态
        courseBase.setAuditStatus("202003");
        courseBaseMapper.updateById(courseBase);
    }
}




