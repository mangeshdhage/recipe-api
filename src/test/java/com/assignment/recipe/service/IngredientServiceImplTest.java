package com.assignment.recipe.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import com.assignment.recipe.dto.IngredientDTO;
import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Ingredient;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exception.RecipeApplicationException;
import com.assignment.recipe.mapper.EntityToDTOMapper;
import com.assignment.recipe.repository.IngredientRepository;
import com.assignment.recipe.repository.RecipeRepository;
import com.assignment.recipe.service.impl.IngredientServiceImpl;

/**
 * Test cases for Ingredient Service class
 * 
 * @author Mangesh Dhage
 */
@SpringBootTest
class IngredientServiceImplTest {

	@Autowired
	private IngredientServiceImpl ingredientService;

	@MockBean
	private RecipeRepository recipeRepository;

	@MockBean
	private IngredientRepository ingredientRepository;

	@MockBean
	private EntityToDTOMapper mapper;

	@Test
	@DisplayName("Test Service- Add Ingredient")
	void testAddIngredient() throws RecipeApplicationException {
		RecipeDTO recipeDTO = getMockedRecipeDTO();
		Recipe recipe = getMockedRecipe();
		ingredientService.addIngredient(recipeDTO, recipe);
	}

	@Test
	@DisplayName("Test Service- Update Ingredient")
	void testUpdateIngredient() throws RecipeApplicationException {
		RecipeDTO recipeDTO = getMockedRecipeDTO();
		Recipe recipe = getMockedRecipe();
		ingredientService.updateIngredient(recipeDTO, recipe, recipe);
	}

	@Test
	@DisplayName("Test Service- Update Ingredient Exception")
	void testUpdateIngredientException() throws RecipeApplicationException {
		RecipeApplicationException exception = assertThrows(RecipeApplicationException.class, () -> {
			RecipeDTO recipeDTO = getMockedRecipeDTO();
			ingredientService.updateIngredient(recipeDTO, null, getMockedRecipe());
		});
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getErrorMessage().getStatus());
	}

	@Test
	@DisplayName("Test Get Filetered Recipe By Ingredient")
	void testGetFilteredRecipeByIngredient() {
		List<Recipe> recipeList = new ArrayList<>();
		recipeList.add(getMockedRecipe());
		ingredientService.getFilteredRecipeByIngredient(getMockedIngredientFilter(), recipeList);
	}

	@Test
	@DisplayName("Test Get Filetered Recipe Exclude Ingredient")
	void testGetFilteredRecipeExcludeIngredient() {
		List<Recipe> recipeList = new ArrayList<>();
		recipeList.add(getMockedRecipe());
		Optional<IngredientFilter> mockedIngredientFilter = getMockedIngredientFilter();
		mockedIngredientFilter.get().setExclude(true);
		ingredientService.getFilteredRecipeByIngredient(mockedIngredientFilter, recipeList);
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

	private Recipe getMockedRecipe() {

		Ingredient rice = new Ingredient();
		rice.setIngredientId(1L);
		rice.setIngredientName("Rice");
		rice.setQuantity(1);
		rice.setUnitOfMeasure("KG");

		Ingredient daal = new Ingredient();
		daal.setIngredientId(2L);
		daal.setIngredientName("Daal");
		daal.setQuantity(1);
		daal.setUnitOfMeasure("KG");

		Set<Ingredient> ingredients = new HashSet<>();
		ingredients.add(rice);
		ingredients.add(daal);

		Recipe recipe = new Recipe();
		recipe.setRecipeId(1L);
		recipe.setDishName("Khichadi");
		recipe.setInstructions("Use ghee");
		recipe.setCategory("Veg");
		recipe.setNoOfServings(5);
		recipe.setIngredient(ingredients);
		return recipe;
	}

	private Optional<IngredientFilter> getMockedIngredientFilter() {

		Optional<IngredientFilter> ingredientFilter = Optional.of(new IngredientFilter());
		ingredientFilter.get().setExclude(false);
		ingredientFilter.get().setIngredientName("Rice");
		return ingredientFilter;
	}
}
