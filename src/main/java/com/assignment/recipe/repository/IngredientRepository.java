package com.assignment.recipe.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.recipe.entity.Ingredient;

/**
 * IngredientRepository is a repository interface to write and execute JPA
 * queries in ingredient table. It extends CrudRepository interface to perfrom
 * CRUD operations on ingredient table and JpaSpecificationExecutor interface to
 * allow execution of queries based on the JPA criteria API.
 * 
 * @author Mangesh Dhage
 *
 */
@Repository
@Transactional
public interface IngredientRepository extends CrudRepository<Ingredient, Long>, JpaSpecificationExecutor<Ingredient> {

	@Modifying
	@Query(value = "DELETE FROM INGREDIENT WHERE RECIPE_ID = :recipeId", nativeQuery = true)
	public void deleteByRecipeId(Long recipeId);
}
