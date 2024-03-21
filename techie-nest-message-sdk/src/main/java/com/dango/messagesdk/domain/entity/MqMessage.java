package com.dango.messagesdk.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息表
 * @TableName mq_message
 */
@TableName(value ="mq_message")
@Data
public class MqMessage implements Serializable {
    /**
     * 消息id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息类型代码: course_publish ,  media_test
     */
    @TableField(value = "message_type")
    private String messageType;

    /**
     * 关联业务信息
     */
    @TableField(value = "business_key1")
    private String businessKey1;

    /**
     * 关联业务信息
     */
    @TableField(value = "business_key2")
    private String businessKey2;

    /**
     * 关联业务信息
     */
    @TableField(value = "business_key3")
    private String businessKey3;

    /**
     * 通知次数
     */
    @TableField(value = "execute_num")
    private Integer executeNum;

    /**
     * 处理状态，0:初始，1:成功
     */
    @TableField(value = "state")
    private String state;

    /**
     * 回复失败时间
     */
    @TableField(value = "return_failure_date")
    private LocalDateTime returnFailureDate;

    /**
     * 回复成功时间
     */
    @TableField(value = "return_success_date")
    private LocalDateTime returnSuccessDate;

    /**
     * 回复失败内容
     */
    @TableField(value = "return_failure_msg")
    private String returnFailureMsg;

    /**
     * 最近通知时间
     */
    @TableField(value = "execute_date")
    private LocalDateTime executeDate;

    /**
     * 阶段1处理状态, 0:初始，1:成功
     */
    @TableField(value = "stage_state1")
    private String stageState1;

    /**
     * 阶段2处理状态, 0:初始，1:成功
     */
    @TableField(value = "stage_state2")
    private String stageState2;

    /**
     * 阶段3处理状态, 0:初始，1:成功
     */
    @TableField(value = "stage_state3")
    private String stageState3;

    /**
     * 阶段4处理状态, 0:初始，1:成功
     */
    @TableField(value = "stage_state4")
    private String stageState4;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}