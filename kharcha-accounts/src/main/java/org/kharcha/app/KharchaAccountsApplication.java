package org.kharcha.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class KharchaAccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(KharchaAccountsApplication.class, args);
	}

}
