package com.caspar.ocr.common.util;

import java.io.IOException;
import java.util.Base64;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-13
 */
public class Image2Base64 {

    private Image2Base64() {
        throw new UnsupportedOperationException("don't initialize...");
    }

    /**
     * 获取图片base64串
     *
     * @param imgPath
     * @return
     */
    public static String getImgBase64Str(String imgPath) {
        byte[] data = null;

        try {
            data = ToolFile.readFile2Bytes(imgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Base64.getEncoder().encodeToString(data);
    }

}
