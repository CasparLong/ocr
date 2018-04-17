package com.caspar.ocr.api.service.impl;

import com.caspar.ocr.api.dto.ReceiptInfo;
import com.caspar.ocr.api.service.OcrService;
import com.caspar.ocr.common.util.Image2Base64;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AliOcrServiceImplTest {

    @Autowired
    private OcrService ocrService;

    @Test
    public void getReceiptInfo() {
        String path = "C:\\Users\\Jun1chi\\Desktop\\jingongmen.jpg";
        long l = System.currentTimeMillis();
        String imgBase64Str = Image2Base64.getImgBase64Str(path);

        long l2 = System.currentTimeMillis();

        ReceiptInfo receiptInfo = ocrService.getReceiptInfoByBase64(imgBase64Str);
        long l1 = System.currentTimeMillis();

        System.out.println("----总耗时" + (l1 - l));

        System.out.println("----识别耗时" + (l1 - l2));

        System.out.println("-----编码耗时" + (l2 - l));

    }
}