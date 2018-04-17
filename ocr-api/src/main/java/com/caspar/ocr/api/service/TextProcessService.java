package com.caspar.ocr.api.service;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-14
 */
public interface TextProcessService {

    /**
     * 获取商城名
     *
     * @param text
     * @return
     */
    String getMallNameByText(String text);

    /**
     * 是否包含金额相关词汇
     *
     * @param text
     * @return
     */
    boolean containsAmountWords(String text);

}
