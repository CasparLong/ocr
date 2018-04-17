package com.caspar.ocr.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Description:凭票信息
 *
 * @author Caspar
 * @Date 2018-04-13
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptInfo {

    /**
     * 商城名称
     */
    private String maillName;

    /**
     * 订单时间
     */
    private long orderTime;

    /**
     * 订单金额
     */
    private double orderAmount;

}
