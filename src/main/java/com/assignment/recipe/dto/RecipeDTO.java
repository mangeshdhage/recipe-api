package com.assignment.recipe.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

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
public class RecipeDTO implements Serializable {

	private static final long serialVersionUID = 6701780614019602001L;

	private long recipeId;

	private String dishName;

	private String category;

	private Set<IngredientDTO> ingredient;

	private String instructions;

	private int noOfServings;

	private Timestamp createTimestamp;

	private Timestamp updateTimestamp;

}
