package com.dango.messagesdk.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dango.messagesdk.domain.entity.MqMessage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
* @author dango
* @description 针对表【mq_message(消息表)】的数据库操作Mapper
* @createDate 2024-03-20 20:21:40
* @Entity com.dango.messagesdk.domain.entity.MqMessage
*/
public interface MqMessageMapper extends BaseMapper<MqMessage> {

    @Select("SELECT t.* FROM mq_message t WHERE t.id % #{shardTotal} = #{shardindex} and t.state='0' and t.message_type=#{messageType} limit #{count}")
    List<MqMessage> selectListByShardIndex(@Param("shardTotal") int shardTotal, @Param("shardindex") int shardindex, @Param("messageType") String messageType, @Param("count") int count);

}




