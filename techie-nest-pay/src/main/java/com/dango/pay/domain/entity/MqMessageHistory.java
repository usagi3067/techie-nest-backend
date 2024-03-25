package com.dango.pay.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName mq_message_history
 */
@TableName(value ="mq_message_history")
@Data
public class MqMessageHistory implements Serializable {
    /**
     * 消息id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 消息类型代码
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
     * 处理状态，0:初始，1:成功，2:失败
     */
    @TableField(value = "state")
    private Integer state;

    /**
     * 回复失败时间
     */
    @TableField(value = "returnfailure_date")
    private LocalDateTime returnfailureDate;

    /**
     * 回复成功时间
     */
    @TableField(value = "returnsuccess_date")
    private LocalDateTime returnsuccessDate;

    /**
     * 回复失败内容
     */
    @TableField(value = "returnfailure_msg")
    private String returnfailureMsg;

    /**
     * 最近通知时间
     */
    @TableField(value = "execute_date")
    private LocalDateTime executeDate;

    /**
     * 
     */
    @TableField(value = "stage_state1")
    private String stageState1;

    /**
     * 
     */
    @TableField(value = "stage_state2")
    private String stageState2;

    /**
     * 
     */
    @TableField(value = "stage_state3")
    private String stageState3;

    /**
     * 
     */
    @TableField(value = "stage_state4")
    private String stageState4;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}