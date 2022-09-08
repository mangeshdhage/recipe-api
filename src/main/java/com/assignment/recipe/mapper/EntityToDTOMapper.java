package com.assignment.recipe.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;

import com.assignment.recipe.dto.RecipeDTO;
import com.assignment.recipe.entity.Recipe;

/**
 * EntityToDTOMapper interface to define methods for mapping entity to DTO and
 * vice versa.
 * 
 * @author Mangesh Dhage
 *
 */
@Mapper
public interface EntityToDTOMapper {

	public RecipeDTO mapRecipeToRecipeDTO(Recipe recipe);

	public Recipe mapRecipeDTOToRecipe(RecipeDTO recipeDTO, Optional<Recipe> recipe);

}
