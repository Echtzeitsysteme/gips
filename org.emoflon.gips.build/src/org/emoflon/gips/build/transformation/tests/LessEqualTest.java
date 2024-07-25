package org.emoflon.gips.build.transformation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LessEqualTest {

	@Test
	public void test_0_0() {
		assertTrue(GipsIlpTransformations.calcLessEqual(0, 0));
	}

	@Test
	public void test_1_1() {
		assertTrue(GipsIlpTransformations.calcLessEqual(1, 1));
	}

	@Test
	public void test_0_1() {
		assertTrue(GipsIlpTransformations.calcLessEqual(0, 1));
	}

	@Test
	public void test_1_0() {
		assertFalse(GipsIlpTransformations.calcLessEqual(1, 0));
	}

	@Test
	public void test_127_130() {
		assertTrue(GipsIlpTransformations.calcLessEqual(127, 130));
	}

	@Test
	public void test_130_130() {
		assertTrue(GipsIlpTransformations.calcLessEqual(130, 130));
	}

	@Test
	public void test_130_127() {
		assertFalse(GipsIlpTransformations.calcLessEqual(130, 127));
	}

	@Test
	public void test_data() {
		final double[][] data = TestCaseGenerator.generateTestData();
		for (int i = 0; i < data.length; i++) {
			double a = data[i][0];
			double b = data[i][1];
			assertEquals(a <= b, GipsIlpTransformations.calcLessEqual(a, b));
		}
	}

}
