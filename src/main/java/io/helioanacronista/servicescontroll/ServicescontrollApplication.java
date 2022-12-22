package io.helioanacronista.servicescontroll;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class ServicescontrollApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(ServicescontrollApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}
