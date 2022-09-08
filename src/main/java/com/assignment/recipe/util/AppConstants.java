package com.assignment.recipe.util;

/**
 * AppConstants class contains all the constants used over the entire
 * application.
 * 
 * @author Mangesh Dhage
 *
 */
public final class AppConstants {

	/*
	 * The Constant for Timestamp formats
	 */
	public static final String TIMESTAMP_FORMAT = "dd‐MM‐yyyy HH:mm";
	/*
	 * The Constants for INFO/WARNING/ERROR Messages from messages.propertiees file.
	 */
	public static final String ADD_SUCCESS_MESSAGE = "recipe.add.success";
	public static final String UPDATE_SUCCESS_MESSAGE = "recipe.update.success";
	public static final String DELETE_SUCCESS_MESSAGE = "recipe.delete.success";
	public static final String RECIPE_NOT_FOUND_EXCEPTION = "recipe.not.found.exception";
	public static final String RECIPE_ALREADY_EXISTS_EXCEPTION = "recipe.exists.exception";

	/*
	 * The Constant for Content-type
	 */
	public static final String CONTENT_TYPE = "content-type";

	/*
	 * The Constant for Content Type Text
	 */
	public static final String CONTENT_TYPE_TEXT = "text/plain";
	/*
	 * The Constant for Admin Role
	 */
	public static final String ADMIN_ROLE = "ADMIN";

	/*
	 * The Constant for credentials
	 */
	public static final String USER_NAME = "test_user";
	public static final String PASSWORD = "test_pwd";

	private AppConstants() {
	}
}
