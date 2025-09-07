package org.kharcha.kharcha_eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
@EnableEurekaServer
public class KharchaEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KharchaEurekaApplication.class, args);
	}

}
