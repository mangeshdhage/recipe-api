package com.assignment.recipe.exception;

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

	private static final long serialVersionUID = 1L;
	ErrorMessage errorMessage;

}
