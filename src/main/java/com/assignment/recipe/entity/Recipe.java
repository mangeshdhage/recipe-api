package com.assignment.recipe.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Mangesh Dhage
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "recipe")
public class Recipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id", updatable = false, nullable = false)
	private Long recipeId;

	private String dishName;

	private String category;

	private String instructions;

	private int noOfServings;

	private Timestamp createTimestamp;

	private Timestamp updateTimestamp;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER, mappedBy = "recipe")
	private Set<Ingredient> ingredient = new HashSet<>();
}
