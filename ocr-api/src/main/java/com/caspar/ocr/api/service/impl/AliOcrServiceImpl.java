package com.caspar.ocr.api.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.caspar.ocr.api.dto.ReceiptInfo;
import com.caspar.ocr.api.service.OcrService;
import com.caspar.ocr.api.service.TextProcessService;
import com.caspar.ocr.api.word.DateFilter;
import com.caspar.ocr.common.constant.StrConst;
import com.caspar.ocr.common.enums.ResponseEnum;
import com.caspar.ocr.common.exception.OcrException;
import com.caspar.ocr.common.http.OkHttpSender;
import com.caspar.ocr.common.util.Image2Base64;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-13
 */
@Service
@Slf4j
public class AliOcrServiceImpl implements OcrService {

    @Value("${ali.ocr.url}")
    private String ALI_OCR_URL;

    @Value("${ali.ocr.appCode}")
    private String ALI_OCR_APPCODE;

    @Autowired
    private TextProcessService textProcessService;

    private Map<String, String> headers;

    private static JSONObject configure;

    static {
        configure = new JSONObject();
        configure.put("min_size", StrConst.ALI_OCR_MIN_SIZE);
        configure.put("output_prob", true);
    }

    @Override
    public ReceiptInfo getReceiptInfoByBase64(String imgBase64Str) {
        JSONObject ocrByAli = getOcrByAli(imgBase64Str);

        JSONArray ret = ocrByAli.getJSONArray("ret");
        if (CollectionUtils.isEmpty(ret)) {
            throw new OcrException(ResponseEnum.BAD_REQUEST_BY_ALI_OCR);
        }

        ReceiptInfo receiptInfo = new ReceiptInfo();

        for (int i = 0, j = ret.size(); i < j; i++) {
            String word = ret.getJSONObject(i).getString("word");

            if (StringUtils.isBlank(receiptInfo.getMaillName())) {
                String mallName = textProcessService.getMallNameByText(word);
                if (StringUtils.isNotBlank(mallName)) {
                    receiptInfo.setMaillName(mallName);
                }
            }

            if (i > 0 && receiptInfo.getOrderAmount() <= 0 && textProcessService.containsAmountWords(word)) {
                String amountStr = ret.getJSONObject(i + 1).getString("word");
                receiptInfo.setOrderAmount(getAmount(amountStr));
            }

            if (receiptInfo.getOrderTime() <= 0) {
                receiptInfo.setOrderTime(DateFilter.matchDate(word));
            }
        }

        return receiptInfo;
    }

    @Override
    public ReceiptInfo getReceiptInfoByPath(String imgPath) {
        log.info("识别图片,path={}", imgPath);
        return this.getReceiptInfoByBase64(Image2Base64.getImgBase64Str(imgPath));
    }

    /**
     * 请求阿里Ocr
     *
     * @param imgBase64Str
     * @return
     */
    private JSONObject getOcrByAli(String imgBase64Str) {
        JSONObject requestObj = new JSONObject();
        requestObj.put("image", imgBase64Str);
        requestObj.put("configure", configure);

        long l = System.currentTimeMillis();
        String result = OkHttpSender.post(ALI_OCR_URL, requestObj.toJSONString(), getHeaders());
        log.info("请求阿里Ocr,deal={}", (System.currentTimeMillis() - l));

        JSONObject response = JSONObject.parseObject(result);
        if (response == null || !response.containsKey("success") || !response.getBoolean("success")) {
            log.error("请求阿里Ocr失败,返回={}", result);
            throw new OcrException(ResponseEnum.BAD_REQUEST_BY_ALI_OCR);
        }

        return response;
    }

    private Map<String, String> getHeaders() {
        if (headers == null) {
            headers = new HashMap<>();
            headers.put("Authorization", "APPCODE " + ALI_OCR_APPCODE);
            headers.put("Content-Type", "application/json; charset=UTF-8");
        }
        return headers;
    }

    /**
     * 从文本中提取金额
     *
     * @param amountStr
     * @return
     */
    private double getAmount(String amountStr) {
        char[] chars = amountStr.toCharArray();
        StringBuilder numberStr = new StringBuilder("0");

        for (int i = 0, j = amountStr.length(); i < j; i++) {
            if (("0123456789.").contains(chars[i] + "")) {
                numberStr.append(chars[i]);
            }
        }

        return NumberUtils.toDouble(numberStr.toString());
    }

}
