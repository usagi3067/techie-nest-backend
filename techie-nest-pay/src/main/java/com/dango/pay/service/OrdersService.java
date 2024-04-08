package com.dango.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.messagesdk.domain.entity.MqMessage;
import com.dango.pay.domain.dto.AddOrderDto;
import com.dango.pay.domain.dto.PayRecordDto;
import com.dango.pay.domain.dto.PayStatusDto;
import com.dango.pay.domain.entity.Orders;
import com.dango.pay.domain.entity.PayRecord;

/**
* @author dango
* @description 针对表【xc_orders】的数据库操作Service
* @createDate 2024-03-24 17:53:38
*/
public interface OrdersService extends IService<Orders> {

    /**
     * @description 创建商品订单
     * @param addOrderDto 订单信息
     * @return PayRecordDto 支付交易记录(包括二维码)
     **/
    public PayRecordDto createOrder(Long userId, AddOrderDto addOrderDto);

    /**
     * @description 查询支付记录
     * @param payNo  交易记录号
     */
    public PayRecord getPayRecordByPayno(String payNo);


    /**
     * 请求支付宝查询支付结果
     * @param payNo 支付记录id
     * @return 支付记录信息
     */
    public PayRecordDto queryPayResult(String payNo);


    /**
     * @description 保存支付宝支付结果
     * @param payStatusDto  支付结果信息
     */
    public void saveAliPayStatus(PayStatusDto payStatusDto) ;

    /**
     * 发送通知结果
     * @param message
     */
    public void notifyPayResult(MqMessage message);


}
