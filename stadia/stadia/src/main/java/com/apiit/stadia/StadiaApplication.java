package com.apiit.stadia;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@ComponentScan(basePackages = { "RestServices","Services"} )
@EntityScan("ModelClasses")
@EnableJpaRepositories("Repositories")
public class StadiaApplication{
	public static void main(String[] args) {
		SpringApplication.run(StadiaApplication.class, args);
                
	}
}
