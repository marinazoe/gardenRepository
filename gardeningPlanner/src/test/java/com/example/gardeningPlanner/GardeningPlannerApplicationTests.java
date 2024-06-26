package com.example.gardeningPlanner;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class GardeningPlannerApplicationTests {

	@Test
	void contextLoads() {
		assertThrows(IllegalArgumentException.class, () -> GardeningPlannerApplication.main(null));
	}

}
