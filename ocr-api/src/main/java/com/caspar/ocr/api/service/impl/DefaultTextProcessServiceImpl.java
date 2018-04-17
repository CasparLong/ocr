package com.caspar.ocr.api.service.impl;

import com.caspar.ocr.api.service.TextProcessService;
import com.caspar.ocr.api.word.WordFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.caspar.ocr.api.word.WordFilter.MAX_MATCH_TYPE;
import static com.caspar.ocr.api.word.WordInitializer.AMOUNT_TEXT_WORD_TYPE;
import static com.caspar.ocr.api.word.WordInitializer.MALL_TEXT_WORD_TYPE;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-14
 */
@Service
@Slf4j
public class DefaultTextProcessServiceImpl implements TextProcessService {

    @Autowired
    private WordFilter wordFilter;

    @Override
    public String getMallNameByText(String text) {
        return wordFilter.getFirstMatch(MALL_TEXT_WORD_TYPE, text, MAX_MATCH_TYPE);
    }

    @Override
    public boolean containsAmountWords(String text) {
        String firstMatch = wordFilter.getFirstMatch(AMOUNT_TEXT_WORD_TYPE, text, MAX_MATCH_TYPE);

        if (StringUtils.isNotBlank(firstMatch)) {
            return true;
        }
        return false;
    }


}
