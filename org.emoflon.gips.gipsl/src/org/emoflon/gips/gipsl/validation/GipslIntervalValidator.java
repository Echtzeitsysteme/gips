package org.emoflon.gips.gipsl.validation;

import org.emoflon.gips.gipsl.gipsl.GipsDoubleLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsIntegerLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsInterval;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;

public class GipslIntervalValidator {
	private GipslIntervalValidator() {

	}

	public static void checkInterval(final GipsInterval interval) {
		if (GipslValidator.DISABLE_VALIDATOR)
			return;

		if (interval == null)
			return;

		checkBounds(interval);
	}

	private static void checkBounds(GipsInterval interval) {
		if (!interval.isLowerInfinity() && interval.getLowerBound() == null) {
			GipslValidator.err( //
					"Lower bound not defined", //
					interval, GipslPackage.Literals.GIPS_INTERVAL__LOWER_BOUND);
			return;
		}

		if (!interval.isUpperInfinity() && interval.getUpperBound() == null) {
			GipslValidator.err( //
					"Upper bound not defined", //
					interval, GipslPackage.Literals.GIPS_INTERVAL__UPPER_BOUND);
			return;
		}

		if (!interval.isLowerInfinity() && !interval.isUpperInfinity()) {
			double lowerBound = switch (interval.getLowerBound()) {
			case GipsDoubleLiteral val -> val.getValue();
			case GipsIntegerLiteral val -> val.getValue();
			case null, default -> throw new IllegalArgumentException("Unexpected value: " + interval.getLowerBound());
			};

			double upperBound = switch (interval.getUpperBound()) {
			case GipsDoubleLiteral val -> val.getValue();
			case GipsIntegerLiteral val -> val.getValue();
			case null, default -> throw new IllegalArgumentException("Unexpected value: " + interval.getLowerBound());
			};

			if (Double.compare(lowerBound, upperBound) > 0) {
				GipslValidator.err( //
						GipslValidatorUtil.VARIABLE_BOUNDS_LIMIT_ERROR, //
						interval, //
						GipslPackage.Literals.GIPS_INTERVAL__LOWER_BOUND);
				return;
			}
		}
	}
}
