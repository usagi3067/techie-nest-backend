package com.dango.content.mapper;

import com.dango.content.model.entity.Lecturer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
* @author dango
* @description 针对表【lecturer】的数据库操作Mapper
* @createDate 2024-04-04 10:36:05
* @Entity com.dango.content.model.entity.Lecturer
*/
public interface LecturerMapper extends BaseMapper<Lecturer> {

    void incrementBalance(@Param("lecturerId") Long lecturerId, @Param("price") Double price);
}




