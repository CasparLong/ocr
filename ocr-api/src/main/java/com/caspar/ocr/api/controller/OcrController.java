package com.caspar.ocr.api.controller;

import com.caspar.ocr.api.service.OcrService;
import com.caspar.ocr.common.response.ResponseBuilder;
import com.caspar.ocr.common.response.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-16
 */
@RestController
@RequestMapping("/ocr")
public class OcrController {

    @Autowired
    private OcrService ocrService;

    /**
     * 提取凭票信息:0-目录,1-base64,2-url
     * @param image
     * @param type
     * @return
     */
    @RequestMapping("/receipt")
    public Mono<Response> getReceiptInfo(@RequestParam String image,
                                         @RequestParam int type) {
        return Mono.fromCallable(() -> ResponseBuilder.buildSuccess(ocrService.getReceiptInfoByType(image, type)));
    }

}
