package com.assignment.recipe;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@SpringBootTest
class RecipeApplicationTest {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	void contextLoads() {
		final StopWatch watch = new StopWatch();
		watch.start("contextLoads");
		System.out.println("Recipe API");
		watch.stop();
		logger.info(watch.getLastTaskName() + " took" + watch.getTotalTimeSeconds() + " seconds.");
		Assertions.assertNotNull(watch);
	}

}
