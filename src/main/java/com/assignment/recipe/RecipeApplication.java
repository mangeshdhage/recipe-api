package com.assignment.recipe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

/**
 * RecipeApplication class is the main application class. It is annotated
 * with @SpringBootApplication as primary application configuration class.
 * 
 * @author Mangesh Dhage
 *
 */
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Recipe API Documentation", version = "1.0", description = "This application allows users to manage their favourite recipes which involves adding, updating, removing and fetching recipes."))
@SecurityScheme(name = "recipes-api", scheme = "basic", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
public class RecipeApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeApplication.class, args);
	}
}
