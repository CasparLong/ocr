package com.caspar.ocr.api.service;


import com.caspar.ocr.api.dto.ReceiptInfo;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-13
 */
public interface OcrService {

    /**
     * 提取凭票信息
     *
     * @param imgBase64Str 图片Base64编码
     * @return
     */
    ReceiptInfo getReceiptInfoByBase64(String imgBase64Str);

    /**
     * 提取凭票信息
     *
     * @param imgPath 图片路径
     * @return
     */
    ReceiptInfo getReceiptInfoByPath(String imgPath);

    /**
     * 提取凭票信息
     *
     * @param image
     * @param type
     * @return
     */
    ReceiptInfo getReceiptInfoByType(String image, int type);

    ReceiptInfo getReceiptInfoByUrl(String imageUrl);

}
