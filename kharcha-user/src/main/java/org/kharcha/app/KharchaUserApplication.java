package org.kharcha.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class KharchaUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(KharchaUserApplication.class, args);
	}

}
