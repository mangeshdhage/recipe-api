package com.assignment.recipe.mapper.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.Generated;

import org.springframework.stereotype.Service;

import com.assignment.recipe.dto.IngredientDTO;
import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Ingredient;
import com.assignment.recipe.entity.Recipe;
import com.assignment.recipe.mapper.EntityToDTOMapper;
import com.assignment.recipe.util.TimestampUtil;

/**
 * EntityToDTOMapperImpl is an auto generated class using mapStruct to provide
 * implementation for mapping entity to DTO and vice versa.
 *
 */
@Generated(value = "org.mapstruct.ap.MappingProcessor", date = "2022-09-05T18:43:42+0530", comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.3 (Eclipse Adoptium)")
@Service
public class EntityToDTOMapperImpl implements EntityToDTOMapper {

	@Override
	public RecipeDTO mapRecipeToRecipeDTO(Recipe recipe) {
		if (recipe == null) {
			return null;
		}
		RecipeDTO recipeDTO = new RecipeDTO();
		recipeDTO.setRecipeId(recipe.getRecipeId());
		recipeDTO.setDishName(recipe.getDishName());
		recipeDTO.setCategory(recipe.getCategory());
		recipeDTO.setInstructions(recipe.getInstructions());
		recipeDTO.setNoOfServings(recipe.getNoOfServings());
		recipeDTO.setCreateTimestamp(recipe.getCreateTimestamp());
		recipeDTO.setUpdateTimestamp(recipe.getUpdateTimestamp());
		mapIngredientToIngredientDTO(recipe, recipeDTO);

		return recipeDTO;
	}

	private void mapIngredientToIngredientDTO(Recipe recipe, RecipeDTO recipesDTO) {
		Set<IngredientDTO> ingredients = new HashSet<IngredientDTO>();
		Optional<Set<Ingredient>> optional = Optional.ofNullable(recipe.getIngredient());
		if (optional.isPresent()) {
			ingredients = recipe.getIngredient().stream()
					.map(ingredient -> new IngredientDTO(ingredient.getIngredientId(), ingredient.getIngredientName(),
							ingredient.getQuantity(), ingredient.getUnitOfMeasure(), ingredient.getCreateTimestamp(),
							ingredient.getUpdateTimestamp()))
					.collect(Collectors.toSet());
		}
		recipesDTO.setIngredient(ingredients);
	}

	@Override
	public Recipe mapRecipeDTOToRecipe(RecipeDTO recipeDTO, Optional<Recipe> recipeDB) {
		if (recipeDTO == null) {
			return null;
		}
		Recipe recipe = new Recipe();
		if (recipeDB.isPresent() && recipeDB.get().getRecipeId() > 0) {
			recipe.setRecipeId(recipeDB.get().getRecipeId());
		}
		recipe.setDishName(recipeDTO.getDishName());
		recipe.setCategory(recipeDTO.getCategory());
		recipe.setInstructions(recipeDTO.getInstructions());
		recipe.setNoOfServings(recipeDTO.getNoOfServings());
		recipe.setCreateTimestamp(TimestampUtil.getCurrentTimestamp());
		recipe.setUpdateTimestamp(TimestampUtil.getCurrentTimestamp());
		return recipe;
	}

}
