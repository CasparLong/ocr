package com.caspar.ocr.common.exception;

import com.caspar.ocr.common.enums.ResponseEnum;
import lombok.Getter;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-14
 */
@Getter
public class OcrException extends RuntimeException {

    private Integer code;

    public OcrException(ResponseEnum responseEnum) {
        this(responseEnum.getCode(), responseEnum.getMessage());
    }

    public OcrException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
