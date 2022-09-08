package com.assignment.recipe.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * IngredientDTO is a DTO class to map ingredient object for request/response.
 * 
 * @author Mangesh Dhage
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class IngredientDTO implements Serializable {

	private static final long serialVersionUID = 5288411663705767222L;

	private long ingredientId;

	private String ingredientName;

	private double quantity;

	private String unitOfMeasure;

	private Timestamp createTimestamp;

	private Timestamp updateTimestamp;

}
