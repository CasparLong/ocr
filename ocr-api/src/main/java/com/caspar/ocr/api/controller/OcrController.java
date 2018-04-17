package com.caspar.ocr.api.controller;

import com.caspar.ocr.api.service.ImageService;
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

    @Autowired
    private ImageService imageService;

    @RequestMapping("/receipt")
    public Mono<Response> getReceiptInfo(@RequestParam String imgName) {
        return Mono.fromCallable(() -> ResponseBuilder.buildSuccess(ocrService.getReceiptInfoByPath(imageService.getDefaultImgPath(imgName))));
    }

}
