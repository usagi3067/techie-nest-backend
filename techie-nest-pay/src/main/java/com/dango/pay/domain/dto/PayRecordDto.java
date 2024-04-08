package com.dango.pay.domain.dto;

import com.dango.pay.domain.entity.PayRecord;
import lombok.Data;
import lombok.ToString;

/**
 * @author dango
 * @description
 * @date 2024-03-24
 */
@Data
@ToString
public class PayRecordDto extends PayRecord {

    //二维码
    private String qrcode;

}

