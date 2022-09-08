package com.assignment.recipe.service;

import java.util.List;
import java.util.Optional;

import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.exception.RecipeApplicationException;

/**
 * RecipeService is an interface to define methods which will be implemented to write business logic.
 * @author Mangesh Dhage
 *
 */
public interface RecipeService {

	public List<RecipeDTO> getAllRecipes(String category, Integer noOfServings, String instructions,
			Optional<IngredientFilter> ingredientName) throws RecipeApplicationException;

	public void addRecipe(RecipeDTO recipeDTO) throws RecipeApplicationException;

	public void updateRecipe(RecipeDTO recipeDTO) throws RecipeApplicationException;

	public void deleteRecipeById(Long recipeId) throws RecipeApplicationException;

	public void deleteRecipeByName(String dishName) throws RecipeApplicationException;

}
