package com.agsr.task;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@SpringBootApplication
@SecurityScheme(
		name = "Keycloak",
		openIdConnectUrl = "http://localhost:8080/realms/agsr/.well-known/openid-configuration",
		scheme = "bearer",
		type = SecuritySchemeType.OPENIDCONNECT,
		in = SecuritySchemeIn.HEADER
)
public class TaskApplication {


	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);

	}

}
