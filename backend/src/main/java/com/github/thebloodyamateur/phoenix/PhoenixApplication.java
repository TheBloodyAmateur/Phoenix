package com.github.thebloodyamateur.phoenix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.github.thebloodyamateur.phoenix.security.SecurityProperties;

@SpringBootApplication
@EnableConfigurationProperties(SecurityProperties.class)
public class PhoenixApplication {
	public static void main(String[] args) {
		SpringApplication.run(PhoenixApplication.class, args);
	}
}
