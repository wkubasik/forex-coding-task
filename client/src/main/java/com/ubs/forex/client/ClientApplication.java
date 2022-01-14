package com.ubs.forex.client;

import com.ubs.forex.client.service.ValidationService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(ClientApplication.class, args);
		ctx.getBean(ValidationService.class).testValidateTransactionsRequest();
		ctx.close();
	}

}
