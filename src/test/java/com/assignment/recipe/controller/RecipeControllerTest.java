package com.assignment.recipe.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.assignment.recipe.dto.IngredientDTO;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.exception.RecipeApplicationException;
import com.assignment.recipe.service.RecipeService;

/**
 * Test cases for Recipe Controller class
 * 
 * @author Mangesh Dhage
 */
@SpringBootTest
class RecipeControllerTest {

	@Autowired
	private RecipeController recipeController;

	@MockBean
	private RecipeService recipeService;

	@MockBean
	private MessageSource messageSource;

	@Test
	@DisplayName("Test Get all Recipes")
	void testGetAllRecipes() throws RecipeApplicationException {
		ResponseEntity<List<RecipeDTO>> result = recipeController.getAllRecipes(Mockito.any(), Mockito.any(),
				Mockito.any(), Mockito.any());
		Assertions.assertNotNull(result.getBody());
	}

	@Test
	@DisplayName("Test Add Recipe")
	void testAddRecipe() throws RecipeApplicationException {
		ResponseEntity<String> result = recipeController.addRecipe(getMockedRecipeDTO());
		Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
	}

	@Test
	@DisplayName("Test Update Recipe")
	void testUpdateRecipe() throws RecipeApplicationException {
		ResponseEntity<String> result = recipeController.updateRecipe(getMockedRecipeDTO());
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@DisplayName("Test Delete Recipe by Id")
	void testRemoveRecipeById() throws RecipeApplicationException {
		ResponseEntity<String> result = recipeController.deleteRecipeById(1L);
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	@Test
	@DisplayName("Test Delete Recipe by Name")
	void testrRemoveRecipeByName() throws RecipeApplicationException {
		ResponseEntity<String> result = recipeController.deleteRecipeByName("Khichadi");
		Assertions.assertEquals(HttpStatus.OK, result.getStatusCode());
	}

	private RecipeDTO getMockedRecipeDTO() {

		IngredientDTO rice = new IngredientDTO();
		rice.setIngredientId(1L);
		rice.setIngredientName("Rice");
		rice.setQuantity(1);
		rice.setUnitOfMeasure("KG");

		IngredientDTO daal = new IngredientDTO();
		daal.setIngredientId(2L);
		daal.setIngredientName("Daal");
		daal.setQuantity(1);
		daal.setUnitOfMeasure("KG");

		Set<IngredientDTO> ingredients = new HashSet<>();
		ingredients.add(rice);
		ingredients.add(daal);

		RecipeDTO recipe = new RecipeDTO();
		recipe.setRecipeId(1L);
		recipe.setDishName("Khichadi");
		recipe.setInstructions("Use ghee");
		recipe.setCategory("Veg");
		recipe.setNoOfServings(5);
		recipe.setIngredient(ingredients);
		return recipe;
	}

}
