package com.assignment.recipe.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.assignment.recipe.dto.IngredientDTO;
import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Ingredient;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exception.ErrorMessage;
import com.assignment.recipe.exception.RecipeApplicationException;
import com.assignment.recipe.repository.IngredientRepository;
import com.assignment.recipe.service.IngredientService;
import com.assignment.recipe.util.TimestampUtil;

import lombok.extern.log4j.Log4j2;

/**
 * IngredientServiceImpl class conatins business logic to perform CRUD operation on ingredients.
 * 
 * @author Mangesh Dhage
 *
 */
@Service
@Log4j2
public class IngredientServiceImpl implements IngredientService {

	@Autowired
	IngredientRepository ingredientRepository;

	/**
	 * addIngredient() will add a new Ingredient.
	 * 
	 * @param recipeDTO
	 * @param savedRecipe
	 * @throws RecipeApplicationException
	 */
	@Override
	public void addIngredient(RecipeDTO recipeDTO, Recipe savedRecipe) throws RecipeApplicationException {

		log.info(" Inside addIngredient() of IngredientServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		RecipeApplicationException recipeException = new RecipeApplicationException();

		// Map IngredientDTO object to Ingredient object
		Set<Ingredient> ingredients = mapIngredientDTOToIngredient(recipeDTO, savedRecipe);
		/*
		 * Persist each Ingredient object corresponding to the Recipe added into the
		 * database
		 */
		try {
			ingredients.forEach(ingredient -> ingredientRepository.save(ingredient));
		} catch (Exception exception) {
			log.error(
					"Inside saveIngredient() of IngredientServiceImpl :Something went wrong while saving the Ingredients !");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setMessage("Something went wrong while saving the Ingredients ! " + exception.getMessage());
			recipeException.setErrorMessage(message);
			throw recipeException;
		}
	}

	/**
	 * updateIngredient() will modify an ingredient of an existing Recipe.
	 * 
	 * @param recipeDTO
	 * @throws RecipeApplicationException
	 */
	@Override
	public void updateIngredient(RecipeDTO recipeDTO, Recipe recipe, Recipe updatedRecipe) throws RecipeApplicationException {

		log.info(" Inside updateIngredient() of IngredientServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		RecipeApplicationException recipeException = new RecipeApplicationException();
		/**
		 * This will delete any existing ingredients on the basis of the given recipeId
		 */
		try {
			ingredientRepository.deleteByRecipeId(recipe.getRecipeId());
		} catch (Exception exception) {
			log.error(
					"Inside saveIngredient() of IngredientServiceImpl :Something went wrong while deleting the Ingredients !");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setMessage("Something went wrong while deleting the Ingredients ! " + exception.getMessage());
			recipeException.setErrorMessage(message);
			throw recipeException;
		}
		// Map IngredientDTO object to Ingredient object
		Set<Ingredient> ingredients = mapIngredientDTOToIngredient(recipeDTO, updatedRecipe);
		/*
		 * Persist each Ingredient object corresponding to the Recipe added into the
		 * database
		 */
		try {
			ingredients.forEach(ingredient -> ingredientRepository.save(ingredient));
		} catch (Exception exception) {
			log.error(
					"Inside saveIngredient() of IngredientServiceImpl :Something went wrong while updating the Ingredients !");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setMessage("Something went wrong while saving the Ingredients ! " + exception.getMessage());
			recipeException.setErrorMessage(message);
			throw recipeException;
		}
	}

	/**
	 * Map Ingredient DTO to Ingredient
	 * 
	 * @param recipeDTO
	 * @param savedRecipe
	 * @return Set<Ingredient>
	 */
	private Set<Ingredient> mapIngredientDTOToIngredient(RecipeDTO recipeDTO, Recipe savedRecipe) {
		log.info("Inside mapIngredientDTOToIngredient() of IngredientServiceImpl");
		Set<Ingredient> ingredients = new HashSet<Ingredient>();
		Optional<Set<IngredientDTO>> optionalIngredient = Optional.ofNullable(recipeDTO.getIngredient());
		if (optionalIngredient.isPresent()) {
			ingredients = recipeDTO.getIngredient().stream()
					.map(ingredient -> new Ingredient(ingredient.getIngredientId(), ingredient.getIngredientName(),
							ingredient.getQuantity(), ingredient.getUnitOfMeasure(),
							TimestampUtil.getCurrentTimestamp(), TimestampUtil.getCurrentTimestamp(), savedRecipe))
					.collect(Collectors.toSet());
		}
		return ingredients;
	}

	/**
	 * getFilteredRecipeByIngredient() will filter all the recipes based on the
	 * provided Ingredient name and include/exclude flag
	 * 
	 * @param ingredientFilter
	 * @param recipeList
	 */
	public void getFilteredRecipeByIngredient(Optional<IngredientFilter> ingredientFilter, List<Recipe> recipeList) {
		log.info("Inside getFilteredRecipeByIngredient() of IngredientServiceImpl");
		List<Recipe> includeRecipes = new ArrayList<>();
		List<Recipe> excludeRecipes = new ArrayList<>();
		Optional<String> optionalIngredientName = Optional.ofNullable(ingredientFilter.get().getIngredientName());
		if (optionalIngredientName.isPresent()) {
			for (Recipe recipe : recipeList) {
				for (Ingredient ingredient : recipe.getIngredient()) {
					if (ingredientFilter.get().getIngredientName().equalsIgnoreCase(ingredient.getIngredientName())
							&& !ingredientFilter.get().isExclude()) {
						includeRecipes.add(recipe);
					} else if (ingredientFilter.get().getIngredientName().equalsIgnoreCase(ingredient.getIngredientName())
							&& ingredientFilter.get().isExclude()) {
						excludeRecipes.add(recipe);
					}
				}
			}
		}
		if (!includeRecipes.isEmpty() || !ingredientFilter.get().isExclude()) {
			recipeList.retainAll(includeRecipes);
		} else if (!excludeRecipes.isEmpty() || ingredientFilter.get().isExclude()) {
			recipeList.removeAll(excludeRecipes);
		}
	}

}
