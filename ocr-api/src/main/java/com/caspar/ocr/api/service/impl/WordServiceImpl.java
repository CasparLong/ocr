package com.caspar.ocr.api.service.impl;

import com.caspar.ocr.api.form.TextWordForm;
import com.caspar.ocr.api.service.WordService;
import com.caspar.ocr.api.word.WordInitializer;
import com.caspar.ocr.common.enums.ResponseEnum;
import com.caspar.ocr.common.exception.OcrException;
import com.caspar.ocr.persistent.entity.TextWord;
import com.caspar.ocr.service.service.TextWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-17
 */
@Service
@Slf4j
public class WordServiceImpl implements WordService {

    @Autowired
    private TextWordService textWordService;

    @Autowired
    private WordInitializer wordInitializer;

    @Override
    public TextWord addTextWord(TextWordForm textWordForm) {
        TextWord textWord = new TextWord();
        BeanUtils.copyProperties(textWordForm, textWord);

        List<TextWord> existTextWords = textWordService.select(textWord);
        if (CollectionUtils.isEmpty(existTextWords)) {
            // TODO:异常处理
            textWord = textWordService.saveNotNull(textWord);
            wordInitializer.refreshWords(textWordForm.getWordType());
        } else {
            throw new OcrException(ResponseEnum.WORD_EXIST);
        }

        return textWord;
    }
}
