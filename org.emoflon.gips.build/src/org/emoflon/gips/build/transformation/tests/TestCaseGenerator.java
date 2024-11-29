package org.emoflon.gips.build.transformation.tests;

/**
 * Generates a set of test cases for arithmetic tests.
 */
public class TestCaseGenerator {

	/**
	 * Limit that specifies the size of the data set: OUTSIDE_LIMIT^2
	 */
	public final static int OUTSIDE_LIMIT = 10;

	/**
	 * Forbid the initialization of objects of this class.
	 */
	private TestCaseGenerator() {
	}

	/**
	 * Returns a two-dimensional array of test data. The first dimension is the
	 * index for the test data set and the second dimension is a,b.
	 * 
	 * @return Two-dimensional array of test data.
	 */
	public static double[][] generateTestData() {
		final double[][] data = new double[OUTSIDE_LIMIT * OUTSIDE_LIMIT][2];

		int index = 0;
		for (int j = 0; j < OUTSIDE_LIMIT; j++) {
			for (int k = -OUTSIDE_LIMIT / 2; k < OUTSIDE_LIMIT / 2; k++) {
				data[index][0] = j;
				data[index][1] = k;
				index++;
			}
		}

		return data;
	}

}
