package com.assignment.recipe.service;

import java.util.List;
import java.util.Optional;

import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exception.RecipeException;

/**
 * @author Mangesh Dhage
 *
 */
public interface IngredientService {

	public void addIngredient(RecipeDTO recipeDTO, Recipe savedRecipe) throws RecipeException;

	public void updateIngredient(RecipeDTO recipeDTO, Recipe recipe, Recipe updatedRecipe) throws RecipeException;

	public void getFilteredRecipeByIngredient(Optional<IngredientFilter> ingredientFilter, List<Recipe> recipeList);

}
