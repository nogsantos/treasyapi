package me.fabricionogueira.treasyapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TreasyapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreasyapiApplication.class, args);
	}
}
