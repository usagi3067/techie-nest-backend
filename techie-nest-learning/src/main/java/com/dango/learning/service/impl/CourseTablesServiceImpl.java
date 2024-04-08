package com.dango.learning.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.exception.BusinessException;
import com.dango.learning.feignclient.ContentServiceClient;
import com.dango.learning.mapper.ChooseCourseMapper;
import com.dango.learning.mapper.CourseTablesMapper;
import com.dango.learning.model.dto.ChooseCourseDto;
import com.dango.learning.model.dto.CoursePublishDto;
import com.dango.learning.model.dto.CourseTablesDto;
import com.dango.learning.model.dto.MyCourseTableParams;
import com.dango.learning.model.entity.ChooseCourse;
import com.dango.learning.model.entity.CourseTables;
import com.dango.learning.service.CourseTablesService;
import com.dango.model.BaseResponse;
import com.dango.model.PageResult;
import com.dango.model.state.ChooseCourseStatus;
import com.dango.model.state.CourseFeeStatus;
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
public class CourseTablesServiceImpl extends ServiceImpl<CourseTablesMapper, CourseTables> implements CourseTablesService {
    @Autowired
    ChooseCourseMapper chooseCourseMapper;

    @Autowired
    CourseTablesMapper courseTablesMapper;

    @Autowired
    ContentServiceClient contentServiceClient;

    @Autowired
    CourseTablesService myCourseTablesService;

    @Autowired
    CourseTablesServiceImpl currentProxy;


    @Override
    public ChooseCourseDto addChooseCourse(Long userId, Long courseId) {
        //查询课程信息
        BaseResponse<CoursePublishDto> coursePublishBaseResponse = contentServiceClient.getCoursepublish(courseId);
        if (coursePublishBaseResponse.getCode() != 0)
            throw new BusinessException("没有相关课程发布记录");
        CoursePublishDto coursePublishDto = coursePublishBaseResponse.getData();
        //课程收费标准
        Integer isFree = coursePublishDto.getIsFree();
        //选课记录
        ChooseCourse chooseCourse = null;
        if (CourseFeeStatus.FREE.getCode().equals(isFree)) {//课程免费
            //添加免费课程
            chooseCourse = addFreeCoruse(userId, coursePublishDto);
            //添加到我的课程表
            addCourseTables(chooseCourse);
        } else {
            //添加收费课程
            chooseCourse = addChargeCourse(userId, coursePublishDto);
        }
        //获取学习资格
        ChooseCourseDto chooseCourseDto = new ChooseCourseDto();
        BeanUtils.copyProperties(chooseCourse, chooseCourseDto);


        CourseTablesDto courseTablesDto = getLearningStatus(userId, courseId);
        chooseCourseDto.setLearnStatus(courseTablesDto.getLearnStatus());
        return chooseCourseDto;


    }

    @Override
    public CourseTablesDto getLearningStatus(Long userId, Long courseId) {
        //查询我的课程表
        CourseTables CourseTables = getCourseTables(userId, courseId);
        if (CourseTables == null) {
            CourseTablesDto CourseTablesDto = new CourseTablesDto();
            //没有选课或选课后没有支付
            CourseTablesDto.setLearnStatus(ChooseCourseStatus.NEED_PAY.getCode());
            return CourseTablesDto;
        }
        CourseTablesDto CourseTablesDto = new CourseTablesDto();
        BeanUtils.copyProperties(CourseTables, CourseTablesDto);
        //是否过期,true过期，false未过期
        boolean isExpires = CourseTables.getValidTimeEnd().isBefore(LocalDateTime.now());
        if (!isExpires) {
            //正常学习
            CourseTablesDto.setLearnStatus(ChooseCourseStatus.SUCCESS.getCode());
            return CourseTablesDto;

        } else {
            //已过期
            CourseTablesDto.setLearnStatus(ChooseCourseStatus.OUTDATED.getCode());
            return CourseTablesDto;
        }


    }

    private ChooseCourse addChargeCourse(Long userId, CoursePublishDto coursepublish) {

        //如果存在待支付交易记录直接返回
        LambdaQueryWrapper<ChooseCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(ChooseCourse::getUserId, userId).eq(ChooseCourse::getCourseId, coursepublish.getId()).eq(ChooseCourse::getIsFree, CourseFeeStatus.PAID.getCode())//收费订单
                .eq(ChooseCourse::getStatus, ChooseCourseStatus.NEED_PAY.getCode());//待支付
        List<ChooseCourse> ChooseCourses = chooseCourseMapper.selectList(queryWrapper);
        if (ChooseCourses != null && ChooseCourses.size() > 0) {
            return ChooseCourses.get(0);
        }

        ChooseCourse chooseCourse = new ChooseCourse();
        chooseCourse.setCourseId(coursepublish.getId());
        chooseCourse.setCourseName(coursepublish.getName());
        chooseCourse.setCoursePrice(coursepublish.getPrice());
        chooseCourse.setUserId(userId);
        chooseCourse.setLecturerId(coursepublish.getLecturerId());
        chooseCourse.setIsFree(0);//收费课程
        chooseCourse.setStatus(ChooseCourseStatus.NEED_PAY.getCode());//待支付

        chooseCourse.setValidDays(coursepublish.getValidDays());
        chooseCourse.setValidTimeStart(LocalDateTime.now());
        if (coursepublish.getValidDays() == null) {
            chooseCourse.setValidDays(365);
        }
        chooseCourse.setPic(coursepublish.getPic());
        chooseCourse.setValidTimeEnd(LocalDateTime.now().plusDays(chooseCourse.getValidDays()));
        chooseCourseMapper.insert(chooseCourse);
        return chooseCourse;

    }

    public CourseTables getCourseTables(Long userId, Long courseId) {
        LambdaQueryWrapper<CourseTables> queryWrapper = new LambdaQueryWrapper<CourseTables>().eq(CourseTables::getUserId, userId).eq(CourseTables::getCourseId, courseId);
        return courseTablesMapper.selectOne(queryWrapper);

    }



    private CourseTables addCourseTables(ChooseCourse chooseCourse) {
        //选课记录完成且未过期可以添加课程到课程表
        String status = chooseCourse.getStatus();
        if (!ChooseCourseStatus.SUCCESS.getCode().equals(status)) {
            throw new BusinessException("选课未成功，无法添加到课程表");
        }
        //查询我的课程表
        CourseTables CourseTables = getCourseTables(chooseCourse.getUserId(), chooseCourse.getCourseId());
        if (CourseTables != null) {
            return CourseTables;
        }
        CourseTables courseTablesNew = new CourseTables();
        courseTablesNew.setChooseCourseId(chooseCourse.getId());
        courseTablesNew.setUserId(chooseCourse.getUserId());
        courseTablesNew.setCourseId(chooseCourse.getCourseId());
        courseTablesNew.setLecturerId(chooseCourse.getLecturerId());
        courseTablesNew.setCourseName(chooseCourse.getCourseName());
        courseTablesNew.setPic(chooseCourse.getPic());
        courseTablesNew.setValidTimeStart(chooseCourse.getValidTimeStart());
        courseTablesNew.setValidTimeEnd(chooseCourse.getValidTimeEnd());
        courseTablesNew.setIsFree(chooseCourse.getIsFree());
        courseTablesNew.setPic(chooseCourse.getPic());
        courseTablesMapper.insert(courseTablesNew);

        contentServiceClient.updateCourseStudyCount(chooseCourse.getCourseId());

        return courseTablesNew;

    }

    private ChooseCourse addFreeCoruse(Long userId, CoursePublishDto coursepublish) {
        //查询选课记录表是否存在免费的且选课成功的订单
        LambdaQueryWrapper<ChooseCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper = queryWrapper.eq(ChooseCourse::getUserId, userId).eq(ChooseCourse::getCourseId, coursepublish.getId()).eq(ChooseCourse::getIsFree, CourseFeeStatus.FREE.getCode())//免费课程
                .eq(ChooseCourse::getStatus, ChooseCourseStatus.SUCCESS.getCode());//选课成功
        List<ChooseCourse> ChooseCourses = chooseCourseMapper.selectList(queryWrapper);
        if (ChooseCourses != null && !ChooseCourses.isEmpty()) {
            return ChooseCourses.get(0);
        }
        //添加选课记录信息
        ChooseCourse chooseCourse = new ChooseCourse();
        chooseCourse.setCourseId(coursepublish.getId());
        chooseCourse.setCourseName(coursepublish.getName());
        chooseCourse.setCoursePrice(0.00);//免费课程价格为0
        chooseCourse.setUserId(userId);
        chooseCourse.setLecturerId(coursepublish.getLecturerId());
        chooseCourse.setIsFree(CourseFeeStatus.FREE.getCode());//免费课程
        chooseCourse.setStatus(ChooseCourseStatus.SUCCESS.getCode());//选课成功
        chooseCourse.setPic(coursepublish.getPic());
        chooseCourse.setValidDays(365);//免费课程默认365
        chooseCourse.setValidTimeStart(LocalDateTime.now());
        chooseCourse.setValidTimeEnd(LocalDateTime.now().plusDays(365));
        chooseCourseMapper.insert(chooseCourse);

        return chooseCourse;

    }

    @Override
    public boolean saveChooseCourseSuccess(String chooseCourseId) {

        //根据选课id查询选课表
        ChooseCourse chooseCourse = chooseCourseMapper.selectById(chooseCourseId);
        if (chooseCourse == null) {
            log.debug("接收购买课程的消息，根据选课id从数据库找不到选课记录,选课id:{}", chooseCourseId);
            return false;
        }
        //选课状态
        String status = chooseCourse.getStatus();
        //只有当未支付时才更新为已支付
        if (ChooseCourseStatus.NEED_PAY.getCode().equals(status)) {
            //更新选课记录的状态为支付成功
            chooseCourse.setStatus(ChooseCourseStatus.SUCCESS.getCode());
            int i = chooseCourseMapper.updateById(chooseCourse);
            if (i <= 0) {
                log.debug("添加选课记录失败:{}", chooseCourse);
                throw new BusinessException("添加选课记录失败");
            }

            //向我的课程表插入记录
            addCourseTables(chooseCourse);
            return true;
        }
        return false;
    }

    @Override
    public PageResult<CourseTables> myCourseTables(MyCourseTableParams params) {

        //页码
        int pageNo = params.getPage();
        //每页记录数,固定为4
        int pageSize = 4;
        //分页条件
        Page<CourseTables> page = new Page<>(pageNo, pageSize);
        //根据用户id查询
        Long userId = params.getUserId();
        LambdaQueryWrapper<CourseTables> lambdaQueryWrapper = new LambdaQueryWrapper<CourseTables>().eq(CourseTables::getUserId, userId);

        //分页查询
        Page<CourseTables> pageResult = baseMapper.selectPage(page, lambdaQueryWrapper);
        List<CourseTables> records = pageResult.getRecords();
        //记录总数
        long total = pageResult.getTotal();
        PageResult<CourseTables> courseTablesResult = new PageResult<>(records, total, pageNo, pageSize);
        return courseTablesResult;

    }
}




