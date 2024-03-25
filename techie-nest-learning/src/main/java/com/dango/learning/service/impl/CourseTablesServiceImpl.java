package com.dango.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.exception.BusinessException;
import com.dango.learning.feignclient.ContentServiceClient;
import com.dango.learning.mapper.ChooseCourseMapper;
import com.dango.learning.mapper.CourseTablesMapper;
import com.dango.learning.model.dto.ChooseCourseDto;
import com.dango.learning.model.dto.CoursePublish;
import com.dango.learning.model.dto.CourseTablesDto;
import com.dango.learning.model.entity.ChooseCourse;
import com.dango.learning.model.entity.CourseTables;
import com.dango.learning.service.CourseTablesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author dango
 * @description 针对表【course_tables】的数据库操作Service实现
 * @createDate 2024-03-23 21:28:52
 */
@Service
@Slf4j
public class CourseTablesServiceImpl extends ServiceImpl<CourseTablesMapper, CourseTables>
        implements CourseTablesService {
    @Autowired
    ChooseCourseMapper chooseCourseMapper;

    @Autowired
    CourseTablesMapper CourseTablesMapper;

    @Autowired
    ContentServiceClient contentServiceClient;

    @Autowired
    CourseTablesService myCourseTablesService;

    @Autowired
    CourseTablesServiceImpl currentProxy;


    @Override
    public ChooseCourseDto addChooseCourse(String userId, Long courseId) {
        //查询课程信息
        CoursePublish coursepublish = contentServiceClient.getCoursepublish(courseId);
        //课程收费标准
        String charge = coursepublish.getCharge();
        //选课记录
        ChooseCourse chooseCourse = null;
        if ("201000".equals(charge)) {//课程免费
            //添加免费课程
            chooseCourse = addFreeCoruse(userId, coursepublish);
            //添加到我的课程表
            CourseTables courseTables = addCourseTables(chooseCourse);
        } else {
            //添加收费课程
            chooseCourse = addChargeCourse(userId, coursepublish);
        }
        //获取学习资格
        ChooseCourseDto chooseCourseDto = new ChooseCourseDto();
        BeanUtils.copyProperties(chooseCourse, chooseCourseDto);


        CourseTablesDto courseTablesDto = getLearningStatus(userId, courseId);
        chooseCourseDto.setLearnStatus(courseTablesDto.getLearnStatus());
        return chooseCourseDto;


    }

    @Override
    public CourseTablesDto getLearningStatus(String userId, Long courseId) {
        //查询我的课程表
        CourseTables CourseTables = getCourseTables(userId, courseId);
        if (CourseTables == null) {
            CourseTablesDto CourseTablesDto = new CourseTablesDto();
            //没有选课或选课后没有支付
            CourseTablesDto.setLearnStatus("702002");
            return CourseTablesDto;
        }
        CourseTablesDto CourseTablesDto = new CourseTablesDto();
        BeanUtils.copyProperties(CourseTables, CourseTablesDto);
        //是否过期,true过期，false未过期
        boolean isExpires = CourseTables.getValidTimeEnd().isBefore(LocalDateTime.now());
        if (!isExpires) {
            //正常学习
            CourseTablesDto.setLearnStatus("702001");
            return CourseTablesDto;

        } else {
            //已过期
            CourseTablesDto.setLearnStatus("702003");
            return CourseTablesDto;
        }


    }

    private ChooseCourse addChargeCourse(String userId, CoursePublish coursepublish) {

        //如果存在待支付交易记录直接返回
        LambdaQueryWrapper<ChooseCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(ChooseCourse::getUserId, userId)
                .eq(ChooseCourse::getCourseId, coursepublish.getId())
                .eq(ChooseCourse::getOrderType, "700002")//收费订单
                .eq(ChooseCourse::getStatus, "701002");//待支付
        List<ChooseCourse> ChooseCourses = chooseCourseMapper.selectList(queryWrapper);
        if (ChooseCourses != null && ChooseCourses.size() > 0) {
            return ChooseCourses.get(0);
        }

        ChooseCourse chooseCourse = new ChooseCourse();
        chooseCourse.setCourseId(coursepublish.getId());
        chooseCourse.setCourseName(coursepublish.getName());
        chooseCourse.setCoursePrice(coursepublish.getPrice());
        chooseCourse.setUserId(userId);
        chooseCourse.setCompanyId(coursepublish.getCompanyId());
        chooseCourse.setOrderType("700002");//收费课程
        chooseCourse.setCreateDate(LocalDateTime.now());
        chooseCourse.setStatus("701002");//待支付

        chooseCourse.setValidDays(coursepublish.getValidDays());
        chooseCourse.setValidTimeStart(LocalDateTime.now());
        if (coursepublish.getValidDays() == null) {
            chooseCourse.setValidDays(365);
        }
        chooseCourse.setValidTimeEnd(LocalDateTime.now().plusDays(chooseCourse.getValidDays()));
        chooseCourseMapper.insert(chooseCourse);
        return chooseCourse;

    }

    public CourseTables getCourseTables(String userId, Long courseId) {
        LambdaQueryWrapper<CourseTables> queryWrapper = new LambdaQueryWrapper<CourseTables>()
                .eq(CourseTables::getUserId, userId)
                .eq(CourseTables::getCourseId, courseId);
        CourseTables CourseTables = CourseTablesMapper.selectOne(queryWrapper);
        return CourseTables;

    }


    private CourseTables addCourseTables(ChooseCourse chooseCourse) {
        //选课记录完成且未过期可以添加课程到课程表
        String status = chooseCourse.getStatus();
        if (!"701001".equals(status)) {
            throw new BusinessException("选课未成功，无法添加到课程表");
        }
        //查询我的课程表
        CourseTables CourseTables = getCourseTables(chooseCourse.getUserId(), chooseCourse.getCourseId());
        if (CourseTables != null) {
            return CourseTables;
        }
        CourseTables CourseTablesNew = new CourseTables();
        CourseTablesNew.setChooseCourseId(chooseCourse.getId());
        CourseTablesNew.setUserId(chooseCourse.getUserId());
        CourseTablesNew.setCourseId(chooseCourse.getCourseId());
        CourseTablesNew.setCompanyId(chooseCourse.getCompanyId());
        CourseTablesNew.setCourseName(chooseCourse.getCourseName());
        CourseTablesNew.setCreateDate(LocalDateTime.now());
        CourseTablesNew.setValidTimeStart(chooseCourse.getValidTimeStart());
        CourseTablesNew.setValidTimeEnd(chooseCourse.getValidTimeEnd());
        CourseTablesNew.setCourseType(chooseCourse.getOrderType());
        CourseTablesMapper.insert(CourseTablesNew);

        return CourseTablesNew;

    }

    private ChooseCourse addFreeCoruse(String userId, CoursePublish coursepublish) {
        //查询选课记录表是否存在免费的且选课成功的订单
        LambdaQueryWrapper<ChooseCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(ChooseCourse::getUserId, userId)
                .eq(ChooseCourse::getCourseId, coursepublish.getId())
                .eq(ChooseCourse::getOrderType, "700001")//免费课程
                .eq(ChooseCourse::getStatus, "701001");//选课成功
        List<ChooseCourse> ChooseCourses = chooseCourseMapper.selectList(queryWrapper);
        if (ChooseCourses != null && !ChooseCourses.isEmpty()) {
            return ChooseCourses.get(0);
        }
        //添加选课记录信息
        ChooseCourse ChooseCourse = new ChooseCourse();
        ChooseCourse.setCourseId(coursepublish.getId());
        ChooseCourse.setCourseName(coursepublish.getName());
        ChooseCourse.setCoursePrice(0.00);//免费课程价格为0
        ChooseCourse.setUserId(userId);
        ChooseCourse.setCompanyId(coursepublish.getCompanyId());
        ChooseCourse.setOrderType("700001");//免费课程
        ChooseCourse.setCreateDate(LocalDateTime.now());
        ChooseCourse.setStatus("701001");//选课成功

        ChooseCourse.setValidDays(365);//免费课程默认365
        ChooseCourse.setValidTimeStart(LocalDateTime.now());
        ChooseCourse.setValidTimeEnd(LocalDateTime.now().plusDays(365));
        chooseCourseMapper.insert(ChooseCourse);

        return ChooseCourse;

    }

    @Override
    public boolean saveChooseCourseSuccess(String chooseCourseId) {

        //根据选课id查询选课表
        ChooseCourse chooseCourse = chooseCourseMapper.selectById(chooseCourseId);
        if(chooseCourse == null){
            log.debug("接收购买课程的消息，根据选课id从数据库找不到选课记录,选课id:{}",chooseCourseId);
            return false;
        }
        //选课状态
        String status = chooseCourse.getStatus();
        //只有当未支付时才更新为已支付
        if("701002".equals(status)){
            //更新选课记录的状态为支付成功
            chooseCourse.setStatus("701001");
            int i = chooseCourseMapper.updateById(chooseCourse);
            if(i<=0){
                log.debug("添加选课记录失败:{}",chooseCourse);
                throw new BusinessException("添加选课记录失败");
            }

            //向我的课程表插入记录
            CourseTables xcCourseTables = addCourseTables(chooseCourse);
            return true;
        }
        return false;
    }
}




