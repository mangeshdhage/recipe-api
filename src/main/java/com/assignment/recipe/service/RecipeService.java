package com.assignment.recipe.service;

import java.util.List;
import java.util.Optional;

import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.exception.RecipeException;

/**
 * @author Mangesh Dhage
 *
 */
public interface RecipeService {

	public List<RecipeDTO> getAllRecipes(String category, Integer noOfServings, String instructions,
			Optional<IngredientFilter> ingredientName) throws RecipeException;

	public void addRecipe(RecipeDTO recipeDTO) throws RecipeException;

	public void updateRecipe(RecipeDTO recipeDTO) throws RecipeException;

	public void deleteRecipeById(Long recipeId) throws RecipeException;

	public void deleteRecipeByName(String dishName) throws RecipeException;

}
