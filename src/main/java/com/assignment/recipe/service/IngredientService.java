package com.assignment.recipe.service;

import java.util.List;
import java.util.Optional;

import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exception.RecipeApplicationException;

/**
 * IngredientService is an interface to define methods which will be implemented to write business logic.
 * @author Mangesh Dhage
 *
 */
public interface IngredientService {

	public void addIngredient(RecipeDTO recipeDTO, Recipe savedRecipe) throws RecipeApplicationException;

	public void updateIngredient(RecipeDTO recipeDTO, Recipe recipe, Recipe updatedRecipe) throws RecipeApplicationException;

	public void getFilteredRecipeByIngredient(Optional<IngredientFilter> ingredientFilter, List<Recipe> recipeList);

}
