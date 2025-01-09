package org.emoflon.gips.build.transformation.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests (x1 = c) <=> s1.
 */
public class EqualConstantTest {

	@Test
	public void test_0_0() {
		assertTrue(GipsIlpTransformations.calcEqual(0, 0));
	}

	@Test
	public void test_1_1() {
		assertTrue(GipsIlpTransformations.calcEqual(1, 1));
	}

	@Test
	public void test_0_1() {
		assertFalse(GipsIlpTransformations.calcEqual(0, 1));
	}

	@Test
	public void test_1_0() {
		assertFalse(GipsIlpTransformations.calcEqual(1, 0));
	}

	@Test
	public void test_127_130() {
		assertFalse(GipsIlpTransformations.calcEqual(127, 130));
	}

	@Test
	public void test_130_130() {
		assertTrue(GipsIlpTransformations.calcEqual(130, 130));
	}

	@Test
	public void test_130_127() {
		assertFalse(GipsIlpTransformations.calcEqual(130, 127));
	}

	@Test
	public void test_data() {
		final double[][] data = TestCaseGenerator.generateTestData();
		for (int i = 0; i < data.length; i++) {
			double a = data[i][0];
			double b = data[i][1];
			assertEquals(a == b, GipsIlpTransformations.calcEqual(a, b));
		}
	}

}
