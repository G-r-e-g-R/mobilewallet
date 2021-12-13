package com.nttdata.mobilewallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class WalletmovilApplication {

	public static void main(String[] args) {
		SpringApplication.run(WalletmovilApplication.class, args);
	}

}
