package com.assignment.recipe.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.assignment.recipe.entity.Recipe;

/**
 * RecipeRepository is a repository interface to write and execute JPA queries
 * in recipe table. It extends CrudRepository interface to perfrom CRUD
 * operations on recipe table and JpaSpecificationExecutor interface to
 * allow execution of queries based on the JPA criteria API.
 * 
 * @author Mangesh Dhage
 *
 */
@Repository
public interface RecipeRepository extends CrudRepository<Recipe, Long>, JpaSpecificationExecutor<Recipe> {

	List<Recipe> findAll();

	Optional<Recipe> findByDishName(String recipeName);

}
