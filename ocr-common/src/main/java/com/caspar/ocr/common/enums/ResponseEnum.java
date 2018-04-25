package com.caspar.ocr.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2017-12-16
 */
@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS(200, "成功"),
    FAIL(400, "失败"),
    JSON_PARSE_ERROR(401, "JSON解析出错"),
    SYSTEM_ERROR(900, "系统繁忙"),
    ILLEGAL_ARGUMENT(401,"非法参数"),
    PROGRAM_ERROR(900,"程序打包异常,请联系管理员"),


    PARAM_ERROR(1, "参数不正确"),
    BAD_REQUEST_BY_ALI_OCR(501, "请求阿里Ocr失败"),
    BAD_REQUEST_BY_BAIDU_NLP(501, "请求百度语言处理失败"),

    IMAGE_FILE_NOT_EXIST(404, "图片不存在"),
    WORD_EXIST(405, "该词汇已存在"),
    GET_IMAGE_ERROR(406, "获取图片失败"),

    UPDATE_STATUS_FAIL(407, "更新审核状态失败"),
    OPERATOR_NOT_EXIST(408, "操作员不存在"),

    ;

    private Integer code;
    private String message;

}
