package com.caspar.ocr.common.constant;

import java.nio.charset.Charset;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-13
 */
public final class StrConst {

    private StrConst() {
        throw new UnsupportedOperationException("don't initialize...");
    }

    /**
     * 默认编码
     */
    public static final String DEFAULT_CHARSET_NAME = "UTF-8";
    public static final Charset DEFAULT_CHARSET = Charset.forName(DEFAULT_CHARSET_NAME);

    /**
     * Ali Ocr图片文字最小高度
     */
    public static final int ALI_OCR_MIN_SIZE = 10;

    public static final int IMAGE_PATH_TYPE = 0;
    public static final int IMAGE_BASE64_TYPE = 1;
    public static final int IMAGE_URL_TYPE = 2;


}
