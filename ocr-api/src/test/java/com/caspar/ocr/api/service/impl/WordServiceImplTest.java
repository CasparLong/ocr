package com.caspar.ocr.api.service.impl;

import com.caspar.ocr.api.form.TextWordForm;
import com.caspar.ocr.api.service.WordService;
import com.caspar.ocr.api.word.WordInitializer;
import com.caspar.ocr.persistent.entity.TextWord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class WordServiceImplTest {

    @Autowired
    private WordService wordService;

    @Test
    public void addTextWord() {
        TextWordForm textWordForm = new TextWordForm();
        textWordForm.setWord("实收");
        textWordForm.setWordType(WordInitializer.AMOUNT_TEXT_WORD_TYPE);

        TextWord textWord = wordService.addTextWord(textWordForm);
        System.out.println(textWord);
    }
}