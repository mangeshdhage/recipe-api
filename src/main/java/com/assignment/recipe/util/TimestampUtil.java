package com.assignment.recipe.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Mangesh Dhage
 *
 */
public final class TimestampUtil {

	private TimestampUtil() {
		throw new IllegalStateException("Timestamp Utility class");
	}

	public static Timestamp getCurrentTimestamp() {
		try {
			SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT);
			return new Timestamp(
					DATE_TIME_FORMAT.parse(DATE_TIME_FORMAT.format(Calendar.getInstance().getTime())).getTime());
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

}
