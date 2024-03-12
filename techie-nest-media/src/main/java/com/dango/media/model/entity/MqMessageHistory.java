package com.dango.media.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息历史表
 * @TableName mq_message_history
 */
@TableName(value ="mq_message_history")
@Data
public class MqMessageHistory implements Serializable {
    /**
     * 消息id
     */
    @TableId(value = "id")
    private String id;

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
     * 消息队列主机
     */
    @TableField(value = "mq_host")
    private String mqHost;

    /**
     * 消息队列端口
     */
    @TableField(value = "mq_port")
    private Integer mqPort;

    /**
     * 消息队列虚拟主机
     */
    @TableField(value = "mq_virtualhost")
    private String mqVirtualhost;

    /**
     * 队列名称
     */
    @TableField(value = "mq_queue")
    private String mqQueue;

    /**
     * 通知次数
     */
    @TableField(value = "inform_num")
    private Integer informNum;

    /**
     * 处理状态，0:初始，1:成功，2:失败
     */
    @TableField(value = "state")
    private Integer state;

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
    @TableField(value = "inform_date")
    private LocalDateTime informDate;

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