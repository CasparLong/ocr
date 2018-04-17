package com.caspar.ocr.api.service;

import com.caspar.ocr.api.form.TextWordForm;
import com.caspar.ocr.persistent.entity.TextWord;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-17
 */
public interface WordService {

    TextWord addTextWord(TextWordForm textWordForm);

}
