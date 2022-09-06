package com.assignment.recipe.exception;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Mangesh Dhage
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeException extends Exception {

	private static final long serialVersionUID = 8727073047854399553L;
	@Autowired
	ErrorMessage errorMessage;

}
