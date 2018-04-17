package com.caspar.ocr.common.response.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2017-12-16
 */
@Builder
@Getter
public class Response {

    private Object data;
    private Integer code;
    private String msg;
    private boolean success;

}
