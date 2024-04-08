package com.dango.pay.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dango.pay.domain.entity.PayRecord;
import com.dango.pay.mapper.PayRecordMapper;
import com.dango.pay.service.PayRecordService;
import org.springframework.stereotype.Service;

/**
* @author dango
* @description 针对表【xc_pay_record】的数据库操作Service实现
* @createDate 2024-03-24 17:53:38
*/
@Service
public class PayRecordServiceImpl extends ServiceImpl<PayRecordMapper, PayRecord>
    implements PayRecordService {

}




