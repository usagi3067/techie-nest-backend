package com.dango.content.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.shaded.com.google.gson.Gson;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.CourseBaseMapper;
import com.dango.content.mapper.CourseMarketMapper;
import com.dango.content.mapper.CoursePublishPreMapper;
import com.dango.content.mapper.LecturerMapper;
import com.dango.content.model.dto.CourseBaseInfoDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.CourseBase;
import com.dango.content.model.entity.CourseMarket;
import com.dango.content.model.entity.CoursePublishPre;
import com.dango.content.model.entity.Lecturer;
import com.dango.content.service.CourseBaseService;
import com.dango.content.service.CoursePublishPreService;
import com.dango.content.service.TeachPlanService;
import com.dango.exception.BusinessException;
import com.dango.model.state.CourseAuditStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author dango
 * @description 针对表【course_publish_pre(课程发布)】的数据库操作Service实现
 * @createDate 2024-03-20 16:49:35
 */
@Service
public class CoursePublishPreServiceImpl extends ServiceImpl<CoursePublishPreMapper, CoursePublishPre>
        implements CoursePublishPreService {

    @Resource
    private CourseBaseMapper courseBaseMapper;

    @Resource
    private CourseBaseService courseBaseService;

    @Resource
    private CourseMarketMapper courseMarketMapper;

    @Resource
    private TeachPlanService teachPlanService;

    @Resource
    private LecturerMapper lecturerMapper;

    /**
     * 提交课程审核
     *
     * @param lecturerId 讲师id
     * @param courseId  课程ID
     */
    @Transactional
    @Override
    public void commitAudit(Long lecturerId, Long courseId) {

        // 查询课程基本信息
        CourseBaseInfoDto courseBaseInfo = courseBaseService.getCourseBaseInfo(courseId);

        // 获取课程审核状态
        String auditStatus = courseBaseInfo.getAuditStatus();

        // 如果当前审核状态为已提交，则不允许再次提交
        if (CourseAuditStatus.SUBMITTED.getCode().equals(auditStatus)) {
            throw new BusinessException("当前为等待审核状态，审核完成可以再次提交。");
        }

        // 只允许提交讲师自己的课程
        if (!courseBaseInfo.getLecturerId().equals(lecturerId)) {
            throw new BusinessException("不允许提交其它机构的课程。");
        }

        // 检查课程图片是否填写
        if (StringUtils.isEmpty(courseBaseInfo.getPic())) {
            throw new BusinessException("提交失败，请上传课程图片");
        }

        // 添加课程预发布记录
        CoursePublishPre coursePublishPre = new CoursePublishPre();

        // 获取课程基本信息以及部分营销信息

        BeanUtils.copyProperties(courseBaseInfo, coursePublishPre);

        // 标签序列化
        coursePublishPre.setTags(JSON.toJSONString(courseBaseInfo.getTags()));

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
        coursePublishPre.setStatus(CourseAuditStatus.SUBMITTED.getCode());

        // 设置教学机构信息
        coursePublishPre.setLecturerId(lecturerId);
        Lecturer lecturer = lecturerMapper.selectById(lecturerId);
        if (Objects.nonNull(lecturer)) {
            coursePublishPre.setLecturerInfo(gson.toJson(lecturer));
        }

        CoursePublishPre coursePublishPreUpdate = baseMapper.selectById(courseId);

        if (coursePublishPreUpdate == null) {
            // 添加课程预发布记录
            baseMapper.insert(coursePublishPre);
        } else {
            baseMapper.updateById(coursePublishPre);
        }

        // 更新课程基本表的审核状态
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        courseBase.setAuditStatus(CourseAuditStatus.SUBMITTED.getCode());
        courseBaseMapper.updateById(courseBase);
    }

    @Transactional
    @Override
    public void commitAuditSuccess(Long courseId) {
        // 查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);

        // 获取课程审核状态
        String auditStatus = courseBase.getAuditStatus();

        if (!CourseAuditStatus.SUBMITTED.getCode().equals(auditStatus))
            throw new BusinessException("当前课程并未提交");

        courseBase.setAuditStatus(CourseAuditStatus.APPROVED.getCode());
        courseBaseMapper.updateById(courseBase);

        CoursePublishPre coursePublishPre = baseMapper.selectById(courseId);
        coursePublishPre.setStatus(CourseAuditStatus.APPROVED.getCode());
        baseMapper.updateById(coursePublishPre);
    }

    @Transactional
    @Override
    public void commitAuditFail(Long courseId, String content) {
        // 查询课程基本信息
        CourseBase courseBase = courseBaseMapper.selectById(courseId);

        // 获取课程审核状态
        String auditStatus = courseBase.getAuditStatus();

        if (!CourseAuditStatus.SUBMITTED.getCode().equals(auditStatus))
            throw new BusinessException("当前课程并未提交");
        // todo 补充审核意见
        courseBase.setAuditStatus(CourseAuditStatus.REJECTED.getCode());
        courseBaseMapper.updateById(courseBase);

        CoursePublishPre coursePublishPre = baseMapper.selectById(courseId);
        coursePublishPre.setStatus(CourseAuditStatus.REJECTED.getCode());
        baseMapper.updateById(coursePublishPre);

    }
}




