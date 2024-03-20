package com.dango.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.TeachPlanMapper;
import com.dango.content.mapper.TeachPlanMediaMapper;
import com.dango.content.model.dto.BindTeachPlanMediaDto;
import com.dango.content.model.dto.SaveTeachPlanDto;
import com.dango.content.model.dto.TeachPlanDto;
import com.dango.content.model.entity.TeachPlan;
import com.dango.content.model.entity.TeachPlanMedia;
import com.dango.content.service.TeachPlanService;
import com.dango.exception.BusinessException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author dango
 * @description 针对表【teach_plan(课程计划)】的数据库操作Service实现
 * @createDate 2024-03-06 11:03:10
 */
@Service
public class TeachPlanServiceImpl extends ServiceImpl<TeachPlanMapper, TeachPlan>
        implements TeachPlanService {

    @Resource
    TeachPlanMediaMapper teachPlanMediaMapper;

    @Override
    public List<TeachPlanDto> findTeachPlanTree(long courseId) {
        return baseMapper.findTeachPlanList(courseId);
    }

    /**
     * 保存或新增课程计划
     *
     * @param saveTeachPlanDto 保存课程计划的 dto
     */
    @Transactional
    @Override
    public void saveTeachPlan(SaveTeachPlanDto saveTeachPlanDto) {
        // 获得课程计划id
        Long id = saveTeachPlanDto.getId();
        // 有id则修改，无id则新增
        if (!Objects.isNull(id)) {
            TeachPlan teachPlan = baseMapper.selectById(id);
            BeanUtils.copyProperties(saveTeachPlanDto, teachPlan);
            baseMapper.updateById(teachPlan);
        } else {
            // 取出跟所属父级下课程计划数量， 方便设置最后的排序
            int countsByCurrentParent = getCountsByCurrentParent(saveTeachPlanDto.getCourseId(), saveTeachPlanDto.getParentId());
            // 设置排序号
            TeachPlan teachPlan = new TeachPlan();
            teachPlan.setOrderBy(countsByCurrentParent + 1);
            BeanUtils.copyProperties(saveTeachPlanDto, teachPlan);
            baseMapper.insert(teachPlan);
        }
    }

    /**
     * 获取当前课程当前父级下课程计划数量
     *
     * @param courseId 课程id
     * @param parentId 父级id
     * @return 课程计划数量
     */
    private int getCountsByCurrentParent(Long courseId, Long parentId) {
        LambdaQueryWrapper<TeachPlan> teachPlanLambdaQueryWrapper = new LambdaQueryWrapper<>();
        teachPlanLambdaQueryWrapper.eq(TeachPlan::getCourseId, courseId);
        teachPlanLambdaQueryWrapper.eq(TeachPlan::getParentId, parentId);
        return baseMapper.selectCount(teachPlanLambdaQueryWrapper);
    }

    @Transactional
    @Override
    public void associationMedia(BindTeachPlanMediaDto bindTeachPlanMediaDto) {
        //教学计划id
        Long teachPlanId = bindTeachPlanMediaDto.getTeachPlanId();
        TeachPlan teachPlan = baseMapper.selectById(teachPlanId);
        if (teachPlan == null) {
            throw new BusinessException("教学计划不存在");
        }
        Integer grade = teachPlan.getGrade();
        if (grade != 2) {
            throw new BusinessException("只允许第二级教学计划绑定媒资文件");
        }
        //课程id
        Long courseId = teachPlan.getCourseId();

        //先删除原来该教学计划绑定的媒资
        teachPlanMediaMapper.delete(new LambdaQueryWrapper<TeachPlanMedia>().eq(TeachPlanMedia::getTeachPlanId, teachPlanId));

        //再添加教学计划与媒资的绑定关系
        TeachPlanMedia teachPlanMedia = new TeachPlanMedia();
        teachPlanMedia.setCourseId(courseId);
        teachPlanMedia.setTeachPlanId(teachPlanId);
        teachPlanMedia.setMediaFileName(bindTeachPlanMediaDto.getFileName());
        teachPlanMedia.setMediaId(bindTeachPlanMediaDto.getMediaId());
        teachPlanMedia.setDateCreated(LocalDateTime.now());
        teachPlanMediaMapper.insert(teachPlanMedia);
    }

    @Override
    public void unAssociationMedia(Long teachPlanId, String mediaId) {
        LambdaQueryWrapper<TeachPlanMedia> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TeachPlanMedia::getTeachPlanId, teachPlanId)
                .eq(TeachPlanMedia::getMediaId, mediaId);
        teachPlanMediaMapper.delete(queryWrapper);
    }

    @Transactional
    @Override
    public void deleteTeachPlan(Long teachplanId) {
        if (teachplanId == null)
            throw new BusinessException("课程计划id为空");
        TeachPlan teachplan = baseMapper.selectById(teachplanId);
        // 判断当前课程计划是章还是节
        Integer grade = teachplan.getGrade();
        // 当前课程计划为章
        if (grade == 1) {
            // 查询当前课程计划下是否有小节
            LambdaQueryWrapper<TeachPlan> queryWrapper = new LambdaQueryWrapper<>();
            // select * from teachPlan where parent_id = {当前章计划id}
            queryWrapper.eq(TeachPlan::getParentId, teachplanId);
            // 获取一下查询的条目数
            Integer count = baseMapper.selectCount(queryWrapper);
            // 如果当前章下还有小节，则抛异常
            if (count > 0)
                throw new BusinessException("课程计划信息还有子级信息，无法操作");
            baseMapper.deleteById(teachplanId);
        } else {
            // 课程计划为节，删除改小节课程计划
            baseMapper.deleteById(teachplanId);
            // 条件构造器
            LambdaQueryWrapper<TeachPlanMedia> queryWrapper = new LambdaQueryWrapper<>();
            // 删除媒资信息中对应teachPlanId的数据
            queryWrapper.eq(TeachPlanMedia::getTeachPlanId, teachplanId);
            teachPlanMediaMapper.delete(queryWrapper);
        }
    }
}




