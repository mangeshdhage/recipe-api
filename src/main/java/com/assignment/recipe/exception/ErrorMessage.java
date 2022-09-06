package com.assignment.recipe.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mangesh Dhage
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ErrorMessage {

	private HttpStatus status;
	private String message;

}
