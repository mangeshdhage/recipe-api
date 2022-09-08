package com.assignment.recipe.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * IngredientFilter class provides two attributes exclude and ingredientName to
 * filter out recipes based on ingredients from the list. If exclude is true,
 * then the recipe which conatins the provided ingredientName will be excluded
 * from the Recipe list. If exclude is false, then the recipe which conatins the
 * provided ingredientName will only be included in the Recipe list.
 * 
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
