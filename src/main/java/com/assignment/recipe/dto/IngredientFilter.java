package com.assignment.recipe.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mangesh Dhage
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class IngredientFilter implements Serializable {

	private static final long serialVersionUID = 6701780614019602001L;

	private boolean exclude;

	private String ingredientName;

}
