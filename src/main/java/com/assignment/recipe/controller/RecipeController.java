package com.assignment.recipe.controller;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exception.RecipeApplicationException;
import com.assignment.recipe.service.RecipeService;
import com.assignment.recipe.util.AppConstants;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.extern.log4j.Log4j2;

/**
 * Recipe Controller to handle all the HTTP requests to perform CRUD operations
 * (Add, Get, Update, Delete). It also provides API to filter recipes based on
 * the criteria provided. It interacts with the service layer to implement
 * business logic.
 * 
 * @author Mangesh Dhage
 *
 */
@Log4j2
@RestController
@RequestMapping("/recipes")
@SecurityRequirement(name = "recipes-api")
public class RecipeController {

	@Autowired
	RecipeService recipeService;

	@Autowired
	MessageSource messageSource;

	/**
	 * getAllRecipes() method will fetch all available recipes along with
	 * ingredients based on the provided criteria.
	 * 
	 * @param category
	 * @param noOfServings
	 * @param instructions
	 * @param ingredientFilter
	 * @return ResponseEntity<List<RecipeDTO>>
	 * @throws RecipeApplicationException
	 */
	@GetMapping()
	@Operation(summary = "Get all available recipes", description = "Get list of all available Recipes", tags = {
			"Recipes" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content) })
	public ResponseEntity<List<RecipeDTO>> getAllRecipes(
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "noOfServings", required = false) Integer noOfServings,
			@RequestParam(value = "instructions", required = false) String instructions,
			Optional<IngredientFilter> ingredientFilter) throws RecipeApplicationException {
		log.info(" Inside getAllRecipes() of  RecipesController ");
		return new ResponseEntity<List<RecipeDTO>>(
				recipeService.getAllRecipes(category, noOfServings, instructions, ingredientFilter), HttpStatus.OK);
	}

	/**
	 * addRecipe() method will be used to add a new Recipe and it's ingredients.
	 * 
	 * @param recipeDTO
	 * @return ResponseEntity<String>
	 * @throws RecipeApplicationException
	 */
	@PostMapping
	@Operation(summary = "Add a new recipe", description = "Add a new recipe", tags = { "Recipes" }, responses = {
			@ApiResponse(description = "Created", responseCode = "201", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
			@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
			@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
			@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content) })
	public ResponseEntity<String> addRecipe(@RequestBody RecipeDTO recipeDTO) throws RecipeApplicationException {
		log.info(" Inside addRecipe() of  RecipesController ");
		recipeService.addRecipe(recipeDTO);
		return new ResponseEntity<String>(
				messageSource.getMessage(AppConstants.ADD_SUCCESS_MESSAGE, null, Locale.ENGLISH), getHeaders(),
				HttpStatus.CREATED);
	}

	/**
	 * updateRecipe() method will be used to modify an existing Recipe and it's
	 * ingredients.
	 * 
	 * @param recipeDTO
	 * @return ResponseEntity<String>
	 * @throws RecipeApplicationException
	 */
	@PutMapping()
	@Operation(summary = "Update an existing recipe", description = "Update an existing recipe", tags = {
			"Recipes" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content) })
	public ResponseEntity<String> updateRecipe(@RequestBody RecipeDTO recipeDTO) throws RecipeApplicationException {
		log.info(" Inside updateRecipe() of  RecipesController ");
		recipeService.updateRecipe(recipeDTO);
		return new ResponseEntity<String>(
				messageSource.getMessage(AppConstants.UPDATE_SUCCESS_MESSAGE, null, Locale.ENGLISH), getHeaders(),
				HttpStatus.OK);
	}

	/**
	 * deleteRecipeById() method will remove/delete an existing Recipe and its
	 * ingredients by Recipe Id.
	 * 
	 * @param recipeId
	 * @return ResponseEntity<String>
	 * @throws RecipeApplicationException
	 */
	@DeleteMapping("id/{recipeId}")
	@Operation(summary = "Delete a recipe by recipe id", description = "Delete a recipe by recipe id", tags = {
			"Recipes" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content) })
	public ResponseEntity<String> deleteRecipeById(@PathVariable(value = "recipeId") Long recipeId)
			throws RecipeApplicationException {
		log.info(" Inside deleteRecipeById() of  RecipesController ");
		recipeService.deleteRecipeById(recipeId);
		return new ResponseEntity<String>(
				messageSource.getMessage(AppConstants.DELETE_SUCCESS_MESSAGE, null, Locale.ENGLISH), getHeaders(),
				HttpStatus.OK);
	}

	/**
	 * deleteRecipeByName() method will remove/delete an existing Recipe and its
	 * ingredients by Recipe Name.
	 * 
	 * @param dishName
	 * @return ResponseEntity<String>
	 * @throws RecipeApplicationException
	 */
	@DeleteMapping("recipeName/{dishName}")
	@Operation(summary = "Delete a recipe by recipe name", description = "Delete a recipe by recipe name", tags = {
			"Recipes" }, responses = {
					@ApiResponse(description = "Success", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Recipe.class))),
					@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
					@ApiResponse(description = "Not found", responseCode = "404", content = @Content),
					@ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content) })
	public ResponseEntity<String> deleteRecipeByName(@PathVariable(value = "dishName") String dishName)
			throws RecipeApplicationException {
		log.info(" Inside deleteRecipeByName() of  RecipesController ");
		recipeService.deleteRecipeByName(dishName);
		return new ResponseEntity<String>(
				messageSource.getMessage(AppConstants.DELETE_SUCCESS_MESSAGE, null, Locale.ENGLISH), getHeaders(),
				HttpStatus.OK);
	}

	/**
	 * Method to create HttpHeaders
	 * 
	 * @return HttpHeaders
	 */
	private HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(AppConstants.CONTENT_TYPE, AppConstants.CONTENT_TYPE_TEXT);
		return headers;
	}
}
