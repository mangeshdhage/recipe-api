package com.assignment.recipe.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.assignment.recipe.dto.IngredientFilter;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.exception.ErrorMessage;
import com.assignment.recipe.exception.RecipeException;
import com.assignment.recipe.mapper.EntityToDTOMapper;
import com.assignment.recipe.repository.IngredientRepository;
import com.assignment.recipe.repository.RecipeRepository;
import com.assignment.recipe.service.IngredientService;
import com.assignment.recipe.service.RecipeService;
import com.assignment.recipe.util.AppConstants;

import lombok.extern.log4j.Log4j2;

/**
 * RecipeServiceImpl to write business logic for Recipes and it's ingredients.
 * 
 * @author Mangesh Dhage
 *
 */
@Service
@Log4j2
public class RecipeServiceImpl implements RecipeService {

	@Autowired
	RecipeRepository recipeRepository;

	@Autowired
	IngredientRepository ingredientRepository;

	@Autowired
	IngredientService ingredientService;

	@Autowired
	EntityToDTOMapper mapper;

	@Autowired
	MessageSource messageSource;

	/**
	 * getAllRecipes() method will fetch all available recipes along with
	 * ingredients based on the provided criteria. If no recipe is available then
	 * getAllRecipes() method will throw an exception with error message The recipe
	 * could not be found!
	 * 
	 * @param category
	 * @param noOfServings
	 * @param instructions
	 * @param ingredientFilter
	 * @return
	 * @throws RecipeException
	 */
	@Override
	public List<RecipeDTO> getAllRecipes(String category, Integer noOfServings, String instructions,
			Optional<IngredientFilter> ingredientFilter) throws RecipeException {
		log.info("Inside getAllRecipes() of RecipeServiceImpl :");

		// Fetch all recipes based on category, noOfServings, instructions
		List<Recipe> recipeList = findRecipeByCriteria(category, noOfServings, instructions);
		ingredientService.getFilteredRecipeByIngredient(ingredientFilter, recipeList);

		/*
		 * Using Optional class to check if the recipe is available. If no recipe is
		 * present then getAllRecipes() method will throw an exception with error
		 * message "The recipe could not be found!"
		 */
		Optional<List<Recipe>> optionalRecipe = Optional.ofNullable(recipeList);
		if (!optionalRecipe.isPresent()) {
			log.error("Inside getAllRecipes() of RecipeServiceImpl : The recipe could not be found!");
			ErrorMessage message = new ErrorMessage();
			RecipeException recipeException = new RecipeException();
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage(messageSource.getMessage(AppConstants.RECIPE_NOT_FOUND_EXCEPTION, null, Locale.ENGLISH));
			recipeException.setErrorMessage(message);
			throw recipeException;
		}

		// Using mapStruct to map Recipe object to RecipeDTO object
		List<RecipeDTO> recipeDTOList = new ArrayList<>();
		recipeList.forEach(recipe -> {
			recipeDTOList.add(mapper.mapRecipeToRecipeDTO(recipe));
		});
		return recipeDTOList;
	}

	/**
	 * addRecipe() will add a new Recipe. If the given recipe is present then the
	 * method will throw an exception with error message : The Recipe already exists
	 * else it will add the given Recipe along with the provided ingredients into
	 * the database.
	 * 
	 * @param recipeDTO
	 * @throws RecipeException
	 */
	@Override
	public void addRecipe(RecipeDTO recipeDTO) throws RecipeException {

		log.info(" Inside addRecipe() of RecipeServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		RecipeException recipeException = new RecipeException();
		/*
		 * Check if the given Recipe Name is present in the database, if so, throw an
		 * exception.
		 */
		Optional<Recipe> recipeDB = recipeRepository.findByDishName(recipeDTO.getDishName());
		if (recipeDB.isPresent()) {
			log.error("Inside addRecipe() of RecipeServiceImpl :The Recipe already exists!");
			message.setStatus(HttpStatus.BAD_REQUEST);
			message.setMessage(
					messageSource.getMessage(AppConstants.RECIPE_ALREADY_EXISTS_EXCEPTION, null, Locale.ENGLISH));
			recipeException.setErrorMessage(message);
			throw recipeException;
		}
		// Using mapStruct to map RecipeDTO object to Recipe object
		Recipe recipe = mapper.mapRecipeDTOToRecipe(recipeDTO, recipeDB);
		Recipe savedRecipe = new Recipe();
		// Persist the Recipe object into database.
		try {
			savedRecipe = recipeRepository.save(recipe);
		} catch (Exception exception) {
			log.error("In saveRecipe() of RecipesServiceImpl :Something went wrong while saving the Recipe !");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setMessage("Something went wrong while saving the Recipe ! " + exception.getMessage());
			recipeException.setErrorMessage(message);
			throw recipeException;

		}
		/*
		 * Persist each Ingredient object corresponding to the Recipe added into the
		 * database
		 */
		ingredientService.addIngredient(recipeDTO, savedRecipe);
	}

	/**
	 * updateRecipe() will modify an existing Recipe. If the given recipe is not
	 * present then the method will throw an exception with error message : The
	 * Recipe does not exist else it will modify the given Recipe along with the
	 * provided ingredients into the database.
	 * 
	 * @param recipeDTO
	 * @throws RecipeException
	 */
	@Override
	public void updateRecipe(RecipeDTO recipeDTO) throws RecipeException {

		log.info(" Inside updateRecipe() of RecipeServiceImpl ");
		ErrorMessage message = new ErrorMessage();
		RecipeException recipeException = new RecipeException();
		// Check if the given Recipe Name is present in the database.If not, throw an
		// expection
		Optional<Recipe> recipeDB = recipeRepository.findByDishName(recipeDTO.getDishName());
		if (!recipeDB.isPresent()) {
			log.error("Inside updateRecipe() of  RecipeServiceImpl :The Recipe could not be found!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage(messageSource.getMessage(AppConstants.RECIPE_NOT_FOUND_EXCEPTION, null, Locale.ENGLISH));
			recipeException.setErrorMessage(message);
			throw recipeException;
		}
		// Using mapStruct to map RecipeDTO object to Recipe object
		Recipe recipe = mapper.mapRecipeDTOToRecipe(recipeDTO, recipeDB);
		Recipe updatedRecipe = new Recipe();
		// Persist the Recipe object into database.
		try {
			updatedRecipe = recipeRepository.save(recipe);
		} catch (Exception exception) {
			log.error("In updateRecipe() of  RecipesServiceImpl :Something went wrong while updating the Recipe !");
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			message.setMessage("Something went wrong while updating the Recipe ! " + exception.getMessage());
			recipeException.setErrorMessage(message);
			throw recipeException;
		}
		// update ingredients by recipeId
		ingredientService.updateIngredient(recipeDTO, recipe, updatedRecipe);
	}

	/**
	 * removeRecipeById() this method will remove/delete an existing Recipe and its
	 * ingredients by Recipe Id. If the given recipe does not exist then the method
	 * will throw an exception with error message The Recipe could not be found!
	 * 
	 * @param recipeId
	 * @throws RecipeException
	 */
	@Override
	public void deleteRecipeById(Long recipeId) throws RecipeException {
		log.info(" Inside deleteRecipeById() of RecipeServiceImpl ");
		// Check if the given Recipe exists
		Optional<Recipe> recipeById = recipeRepository.findById(recipeId);
		// Remove/Delete Recipe by Recipe Id
		removeRecipeById(recipeById);
	}

	/**
	 * removeRecipeByName() this method will remove/delete an existing Recipe and
	 * its ingredients by Recipe Name. If the given recipe does not exist then the
	 * method will throw an exception with error message The Recipe could not be
	 * found!
	 * 
	 * @param dishName
	 * @throws RecipeException
	 */
	@Override
	public void deleteRecipeByName(String dishName) throws RecipeException {
		log.info(" Inside deleteRecipeByName() of RecipeServiceImpl ");
		// Check if the given Recipe exists
		Optional<Recipe> recipeByName = recipeRepository.findByDishName(dishName);
		// Remove/Delete Recipe by Recipe Id
		removeRecipeById(recipeByName);
	}

	/**
	 * Remove Recipe by Recipe Id
	 * 
	 * @param recipe
	 * @throws RecipeException
	 */
	private void removeRecipeById(Optional<Recipe> recipe) throws RecipeException {
		ErrorMessage message = new ErrorMessage();
		RecipeException recipeException = new RecipeException();
		if (!recipe.isPresent()) {
			log.error("Inside removeRecipeById() of  RecipeServiceImpl :The Recipe could not be found!");
			message.setStatus(HttpStatus.NOT_FOUND);
			message.setMessage(messageSource.getMessage(AppConstants.RECIPE_NOT_FOUND_EXCEPTION, null, Locale.ENGLISH));
			recipeException.setErrorMessage(message);
			throw recipeException;
		} else {
			/*
			 * If the give Recipe exists then delete the recipe and its ingredients by
			 * recipeId
			 */
			try {
				recipe.get().getIngredient().stream()
						.forEach(ingredient -> ingredientRepository.deleteById(ingredient.getIngredientId()));
				recipeRepository.deleteById(recipe.get().getRecipeId());
			} catch (Exception exception) {
				log.error(
						"In removeRecipeById() of  RecipesServiceImpl :Something went wrong while deleting the Recipe !");
				message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
				message.setMessage("Something went wrong while deleting the Recipe ! " + exception.getMessage());
				recipeException.setErrorMessage(message);
				throw recipeException;
			}
		}
	}

	/**
	 * findRecipeByCriteria() will fetch all the recipes from database based on
	 * category, noOfServings and instructions. If user doe not pass any of these
	 * parameters then this will fetch all the recipes.
	 * 
	 * @param category
	 * @param noOfServings
	 * @param instructions
	 * @return List<Recipe>
	 */
	private List<Recipe> findRecipeByCriteria(String category, Integer noOfServings, String instructions) {
		log.info("Inside findRecipeByCriteria() of RecipesServiceImpl ");
		return recipeRepository.findAll(new Specification<Recipe>() {

			private static final long serialVersionUID = -4209095705417313819L;

			@Override
			public Predicate toPredicate(Root<Recipe> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				Optional<String> optionalCategory = Optional.ofNullable(category);
				Optional<Integer> optionalNoOfServings = Optional.ofNullable(noOfServings);
				Optional<String> optionalInstructions = Optional.ofNullable(instructions);
				if (optionalCategory.isPresent()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("category"), category)));
				}
				if (optionalNoOfServings.isPresent()) {
					predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("noOfServings"), noOfServings)));
				}
				if (optionalInstructions.isPresent()) {
					predicates.add(criteriaBuilder
							.and(criteriaBuilder.like(root.get("instructions"), "%" + instructions + "%")));
				}
				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		});
	}
}
