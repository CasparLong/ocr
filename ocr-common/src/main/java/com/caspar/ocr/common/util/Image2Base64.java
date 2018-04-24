package com.caspar.ocr.common.util;

import com.caspar.ocr.common.enums.HttpMethod;
import com.caspar.ocr.common.enums.ResponseEnum;
import com.caspar.ocr.common.exception.OcrException;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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

    /**
     * 从图片链接获取Base64
     *
     * @param imgUrl
     * @return
     */
    public static String getImgBase64ByUrl(String imgUrl) {
        byte[] data = getImgByte(imgUrl);
        if (data == null) {
            throw new OcrException(ResponseEnum.get_image_error);
        }

        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * 请求图片链接获取二进制数组
     *
     * @param imgUrl
     * @return
     */
    private static byte[] getImgByte(String imgUrl) {
        InputStream inputStream = null;
        ByteArrayOutputStream outputStream = null;
        byte[] data = null;
        try {
            URL url = new URL(imgUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(HttpMethod.GET.name());
            connection.setConnectTimeout(5 * 1000);

            inputStream = connection.getInputStream();
            outputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            data = outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(outputStream);
        }
        return data;
    }

}
