package com.dango.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dango.pay.domain.dto.AddOrderDto;
import com.dango.pay.domain.dto.PayRecordDto;
import com.dango.pay.domain.dto.PayStatusDto;
import com.dango.pay.domain.entity.XcOrders;
import com.dango.pay.domain.entity.XcPayRecord;

/**
* @author dango
* @description 针对表【xc_orders】的数据库操作Service
* @createDate 2024-03-24 17:53:38
*/
public interface XcOrdersService extends IService<XcOrders> {

    /**
     * @description 创建商品订单
     * @param addOrderDto 订单信息
     * @return PayRecordDto 支付交易记录(包括二维码)
     * @author Mr.M
     * @date 2022/10/4 11:02
     */
    public PayRecordDto createOrder(String userId, AddOrderDto addOrderDto);

    /**
     * @description 查询支付记录
     * @param payNo  交易记录号
     * @return com.xuecheng.orders.model.po.XcPayRecord
     * @author Mr.M
     * @date 2022/10/20 23:38
     */
    public XcPayRecord getPayRecordByPayno(String payNo);


    /**
     * 请求支付宝查询支付结果
     * @param payNo 支付记录id
     * @return 支付记录信息
     */
    public PayRecordDto queryPayResult(String payNo);


    /**
     * @description 保存支付宝支付结果
     * @param payStatusDto  支付结果信息
     * @return void
     * @author Mr.M
     * @date 2022/10/4 16:52
     */
    public void saveAliPayStatus(PayStatusDto payStatusDto) ;

}
