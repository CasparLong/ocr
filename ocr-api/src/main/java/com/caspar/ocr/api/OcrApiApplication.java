package com.caspar.ocr.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.caspar.ocr")
@MapperScan(basePackages = "com.caspar.ocr.persistent.mapper")
public class OcrApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OcrApiApplication.class, args);
	}
}
