package br.com.nzesportes.api.nzapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NzapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NzapiApplication.class, args);
	}

}
