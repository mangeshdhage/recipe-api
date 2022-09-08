package com.assignment.recipe.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ErrorMessage class provides HTTP status code and message whenever any exception is thrown.
 * @author Mangesh Dhage
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class ErrorMessage implements Serializable {

	private static final long serialVersionUID = 6539354229046823538L;
	private HttpStatus status;
	private String message;

}
