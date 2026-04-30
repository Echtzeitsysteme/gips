package org.emoflon.gips.build.preference;

import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.swt.widgets.Composite;

public class DoubleFieldEditor extends StringFieldEditor {

	private static final double EPSILON = 0.0000001d;
	private double maxValidValue = Double.MAX_VALUE;
	private double minValidValue = Double.MIN_VALUE;

	private boolean isZeroAllowed;

	public DoubleFieldEditor(String name, String labelText, Composite parent) {
		super(name, labelText, parent);
		setEmptyStringAllowed(false);
		setValidRange(Double.MIN_VALUE, Double.MAX_VALUE);
	}

	public void setValidRange(double min, double max) {
		minValidValue = min;
		maxValidValue = max;

		if (Math.abs(max - min) <= EPSILON)
			throw new IllegalArgumentException("Both values are too close to zero");
		if (max < min)
			throw new IllegalArgumentException("Max needs to be greater than min");

		if (min <= Double.MIN_VALUE + EPSILON) {
			if (max >= Double.MAX_VALUE - EPSILON) {
				setErrorMessage("Please enter a valid floating-point number.");
			} else {
				setErrorMessage(String.format("The value must be equal to or smaller than %.4f", max));
			}
		} else {
			if (max >= Double.MAX_VALUE - EPSILON) {
				setErrorMessage(String.format("The value must be equal to or greater than %.4f", min));
			} else {
				setErrorMessage(String.format("The value must be between %.4f and %.4f", min, max));
			}
		}
	}

	@Override
	protected boolean doCheckState() {
		String text = getTextControl().getText();
		if (text == null || text.trim().isEmpty()) {
			return isEmptyStringAllowed();
		}

		try {
			double value = Double.parseDouble(text);
			return value >= minValidValue && value <= maxValidValue;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public double getDoubleValue() {
		try {
			return Double.parseDouble(getStringValue());
		} catch (NumberFormatException e) {
			return 0.0;
		}
	}

	public void allowZero(boolean b) {
		this.isZeroAllowed = b;
	}

}
