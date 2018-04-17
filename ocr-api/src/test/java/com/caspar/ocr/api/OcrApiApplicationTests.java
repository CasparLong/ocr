package com.caspar.ocr.api;

import com.caspar.ocr.persistent.entity.TextWord;
import com.caspar.ocr.service.service.TextWordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OcrApiApplicationTests {

	@Autowired
	private TextWordService textWordService;

	@Test
	public void contextLoads() {
		List<TextWord> textWords = textWordService.selectAll();
		System.out.println(textWords);
	}

}
