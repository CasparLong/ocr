package com.caspar.ocr.api.form;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-17
 */
@Data
public class TextWordForm {

    @NotEmpty(message = "词汇不能为空")
    private String word;

    @Min(value = 0, message = "词汇类型有误")
    @Max(value = 1, message = "词汇类型有误")
    private Integer wordType;

}
