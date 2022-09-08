package com.assignment.recipe.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * RecipeException is a custom exception which will be thrown while performing any CRUD operation
 * on Recipe.
 * @author Mangesh Dhage
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecipeApplicationException extends Exception {

	private static final long serialVersionUID = 1L;
	ErrorMessage errorMessage;

}
