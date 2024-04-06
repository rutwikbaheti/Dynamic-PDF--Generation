package com.pdf.generation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:messages.properties")
public class DynamicPdfGenerationApplication {

	public static void main(String[] args) {
		SpringApplication.run(DynamicPdfGenerationApplication.class, args);
	}

}
