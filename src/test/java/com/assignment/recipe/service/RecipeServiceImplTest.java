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
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;

import com.assignment.recipe.dto.IngredientDTO;
import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Ingredient;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exception.RecipeException;
import com.assignment.recipe.mapper.EntityToDTOMapper;
import com.assignment.recipe.repository.IngredientRepository;
import com.assignment.recipe.repository.RecipeRepository;
import com.assignment.recipe.service.impl.RecipeServiceImpl;

@SpringBootTest
class RecipeServiceImplTest {

	@Autowired
	private RecipeServiceImpl recipeService;

	@MockBean
	private MessageSource messageSource;

	@MockBean
	private RecipeRepository recipeRepository;

	@MockBean
	private IngredientRepository ingredientRepository;

	@MockBean
	private EntityToDTOMapper mapper;

	@Test
	@DisplayName("Test Service- Get all Recipes")
	void testGetAllRecipes() throws RecipeException {
		List<Recipe> recipies = new ArrayList<>();
		recipies.add(getMockedRecipe());
		Mockito.when(recipeRepository.findAll()).thenReturn(recipies);
		List<RecipeDTO> result = recipeService.getAllRecipes(recipies.get(0).getCategory(),
				recipies.get(0).getNoOfServings(), recipies.get(0).getInstructions(), getMockedIngredientFilter());
		Assertions.assertNotNull(result);
	}

	@Test
	@DisplayName("Test Service- Add Recipe")
	void testAddRecipe() throws RecipeException {
		RecipeDTO recipeDTO = getMockedRecipeDTO();
		Mockito.when(recipeRepository.findByDishName(recipeDTO.getDishName())).thenReturn(Optional.empty());
		recipeService.addRecipe(recipeDTO);
	}

	@Test
	@DisplayName("Test Service- Add Recipe Exception")
	void testAddRecipeException() throws RecipeException {
		RecipeException exception = assertThrows(RecipeException.class, () -> {
			RecipeDTO recipeDTO = getMockedRecipeDTO();
			Mockito.when(recipeRepository.findByDishName(recipeDTO.getDishName()))
					.thenReturn(getMockedOptionalRecipe());
			recipeService.addRecipe(recipeDTO);
		});
		Assertions.assertEquals(HttpStatus.BAD_REQUEST, exception.getErrorMessage().getStatus());
	}

	@Test
	@DisplayName("Test Service- Update Recipe Exception")
	void testUpdateRecipeException() throws RecipeException {
		RecipeException exception = assertThrows(RecipeException.class, () -> {
			RecipeDTO recipeDTO = getMockedRecipeDTO();
			Mockito.when(recipeRepository.findByDishName(recipeDTO.getDishName()))
					.thenReturn(getMockedOptionalRecipe());
			recipeService.updateRecipe(recipeDTO);
		});
		Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, exception.getErrorMessage().getStatus());
	}

	@Test
	@DisplayName("Test Service- Update Recipes Not Found Exception")
	void testUpdateRecipeNotFoundException() throws RecipeException {
		RecipeException exception = assertThrows(RecipeException.class, () -> {
			Mockito.when(recipeRepository.findByDishName("Test")).thenReturn(Optional.empty());
			recipeService.updateRecipe(getMockedRecipeDTO());
		});
		Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getErrorMessage().getStatus());
	}

	@Test
	@DisplayName("Test Service- Delete Recipe by Id")
	void testRemoveRecipeById() throws RecipeException {
		Mockito.when(recipeRepository.findById(1L)).thenReturn(getMockedOptionalRecipe());
		recipeService.deleteRecipeById(1L);
	}

	@Test
	@DisplayName("Test Service- Delete Recipe by Name")
	void testrRemoveRecipeByName() throws RecipeException {
		Mockito.when(recipeRepository.findByDishName("Khichadi")).thenReturn(getMockedOptionalRecipe());
		recipeService.deleteRecipeByName("Khichadi");
	}

	@Test
	@DisplayName("Test Service- Delete Recipes Exception")
	void testRemoveRecipesException() throws RecipeException {
		RecipeException exception = assertThrows(RecipeException.class, () -> {
			Mockito.when(recipeRepository.findById(1234L)).thenReturn(Optional.empty());
			recipeService.deleteRecipeById(1234L);
		});
		Assertions.assertEquals(HttpStatus.NOT_FOUND, exception.getErrorMessage().getStatus());
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

	private Optional<Recipe> getMockedOptionalRecipe() {

		Optional<Recipe> recipe = Optional.of(getMockedRecipe());
		return recipe;
	}

	private Optional<IngredientFilter> getMockedIngredientFilter() {

		Optional<IngredientFilter> ingredientFilter = Optional.of(new IngredientFilter());
		ingredientFilter.get().setExclude(false);
		ingredientFilter.get().setIngredientName("Rice");
		return ingredientFilter;
	}
}
