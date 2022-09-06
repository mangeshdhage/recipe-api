package com.assignment.recipe.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id", updatable = false, nullable = false)
	private Long ingredientId;

	private String ingredientName;

	private double quantity;

	private String unitOfMeasure;

	private Timestamp createTimestamp;

	private Timestamp updateTimestamp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id", foreignKey = @ForeignKey(name = "recipe_id_fk"))
	private Recipe recipe;

}
