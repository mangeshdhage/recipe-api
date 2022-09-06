package com.assignment.recipe.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * RecipeExceptionHandler class is for handling the custom exception.
 * 
 * @author Mangesh Dhage
 */

@ControllerAdvice
@ResponseStatus
public class RecipeExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RecipeException.class)
	public ResponseEntity<ErrorMessage> recipesNotFoundException(RecipeException exception, WebRequest request) {
		return ResponseEntity.status(exception.errorMessage.getStatus()).body(exception.errorMessage);
	}

}
