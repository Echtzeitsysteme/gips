package org.emoflon.gips.build.transformation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests (x1 != c) <=> s1.
 */
public class NotEqualTest {

	@Test
	public void test_0_0() {
		assertFalse(GipsIlpTransformations.calcNotEqual(0, 0));
	}

	@Test
	public void test_1_1() {
		assertFalse(GipsIlpTransformations.calcNotEqual(1, 1));
	}

	@Test
	public void test_0_1() {
		assertTrue(GipsIlpTransformations.calcNotEqual(0, 1));
	}

	@Test
	public void test_1_0() {
		assertTrue(GipsIlpTransformations.calcNotEqual(1, 0));
	}

	@Test
	public void test_127_130() {
		assertTrue(GipsIlpTransformations.calcNotEqual(127, 130));
	}

	@Test
	public void test_130_130() {
		assertFalse(GipsIlpTransformations.calcNotEqual(130, 130));
	}

	@Test
	public void test_130_127() {
		assertTrue(GipsIlpTransformations.calcNotEqual(130, 127));
	}

	@Test
	public void test_data() {
		final double[][] data = TestCaseGenerator.generateTestData();
		for (int i = 0; i < data.length; i++) {
			double a = data[i][0];
			double b = data[i][1];
			assertEquals(a != b, GipsIlpTransformations.calcNotEqual(a, b));
		}
	}

}
