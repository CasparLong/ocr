package com.caspar.ocr.api.service.impl;

import com.caspar.ocr.api.service.ImageService;
import com.caspar.ocr.common.enums.ResponseEnum;
import com.caspar.ocr.common.exception.OcrException;
import com.caspar.ocr.common.util.ToolFile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @author Caspar
 * @Date 2018-04-16
 */
@Service
@Slf4j
public class ImageServiceImpl implements ImageService {

    @Value("${default.image.path}")
    private String DEFAULT_IMAGE_PATH;

    @Value("${http.image.path}")
    private String HTTP_IMAGE_PATH;

    @Override
    public String getDefaultImgPath(String imgName) {
        String imgPath = DEFAULT_IMAGE_PATH + imgName;
        if (!ToolFile.isFileExists(imgPath)) {
            throw new OcrException(ResponseEnum.IMAGE_FILE_NOT_EXIST);
        }
        return imgPath;
    }
}
