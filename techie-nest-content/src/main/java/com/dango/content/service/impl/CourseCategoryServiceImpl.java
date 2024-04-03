package com.dango.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.content.mapper.CourseCategoryMapper;
import com.dango.content.model.dto.CourseCategoryMenuDto;
import com.dango.content.model.dto.CourseCategoryTreeDto;
import com.dango.content.model.entity.CourseCategory;
import com.dango.content.service.CourseCategoryService;
import com.google.common.collect.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author dango
* @description 针对表【course_category(课程分类)】的数据库操作Service实现
* @createDate 2024-03-03 15:47:30
*/
@Service
public class CourseCategoryServiceImpl extends ServiceImpl<CourseCategoryMapper, CourseCategory>
    implements CourseCategoryService {

    /**
     * 课程分类树形结构查询
     * @param id 父节点id
     * @return 课程分类树形结构
     */
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = baseMapper.selectTreeNodes(id);
        // 找到每个节点的子节点， 封转为结果返回
        // list转 map, 除去根节点, 目的是为了方便后续查询获取节点
        Map<String, CourseCategoryTreeDto> mapTemp = courseCategoryTreeDtos.stream()
                .filter(item -> !id.equals(item.getId()))
                .collect(Collectors.toMap(CourseCategoryTreeDto::getId, value -> value, (k1, k2) -> k2));
        // 定义一个 list作为最终返回的 list
        List<CourseCategoryTreeDto> courseCategoryList = Lists.newArrayList();

        // 从头遍历 List, 找到每个节点的子节点, 并将子节点添加到父节点的 children 属性中
        courseCategoryTreeDtos.stream().filter(item -> !id.equals(item.getId()))
                .forEach(item -> {
                    if (item.getParentId().equals(id)) {
                        courseCategoryList.add(item);
                    }
                    // 找到节点的父节点
                    CourseCategoryTreeDto parent = mapTemp.get(item.getParentId());
                    if (Objects.nonNull(parent)) {
                        if (Objects.isNull(parent.getChildrenTreeNode())) {
                            parent.setChildrenTreeNode(Lists.newArrayList());
                        }
                        parent.getChildrenTreeNode().add(item);
                    }
                });

        return courseCategoryList;
    }

    @Override
    public List<CourseCategoryMenuDto> getAllMt() {
        LambdaQueryWrapper<CourseCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseCategory::getParentId, "1");
        List<CourseCategory> courseCategories = baseMapper.selectList(queryWrapper);
        List<CourseCategoryMenuDto> result = courseCategories.stream()
                .map(courseCategory -> {
                    CourseCategoryMenuDto dto = new CourseCategoryMenuDto();
                    BeanUtils.copyProperties(courseCategory, dto);
                    return dto;
                })
                .collect(Collectors.toList());


        return  result;
    }

    @Override
    public List<CourseCategoryMenuDto> getAllSt(String id) {
        LambdaQueryWrapper<CourseCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseCategory::getParentId, id);
        List<CourseCategory> courseCategories = baseMapper.selectList(queryWrapper);
        List<CourseCategoryMenuDto> result = courseCategories.stream()
                .map(courseCategory -> {
                    CourseCategoryMenuDto dto = new CourseCategoryMenuDto();
                    BeanUtils.copyProperties(courseCategory, dto);
                    return dto;
                })
                .collect(Collectors.toList());
        return  result;
    }
}




