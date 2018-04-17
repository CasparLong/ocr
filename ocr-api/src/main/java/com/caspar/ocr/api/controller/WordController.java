package com.caspar.ocr.api.controller;

import com.caspar.ocr.api.form.TextWordForm;
import com.caspar.ocr.api.service.WordService;
import com.caspar.ocr.common.enums.ResponseEnum;
import com.caspar.ocr.common.exception.OcrException;
import com.caspar.ocr.common.response.ResponseBuilder;
import com.caspar.ocr.common.response.dto.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-17
 */
@RestController
@RequestMapping("/word")
public class WordController {

    @Autowired
    private WordService wordService;

    @RequestMapping("/add")
    public Mono<Response> addWord(@Valid TextWordForm textWordForm,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new OcrException(ResponseEnum.PARAM_ERROR);
        }
        return Mono.fromCallable(() -> ResponseBuilder.buildSuccess(wordService.addTextWord(textWordForm)));
    }

}
