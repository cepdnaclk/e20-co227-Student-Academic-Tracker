package com.project.co;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoApplication.class, args);
		System.out.println("DONE!");
	}

	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

}
