package org.emoflon.gips.gipsl.validation;

import java.util.LinkedList;
import java.util.List;

import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.ImplicationOperator;
import org.emoflon.gips.gipsl.special.PatternMatcher;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutA;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutB;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutC;
import org.emoflon.gips.gipsl.special.pattern.EquivalenceShortcutD;
import org.emoflon.gips.gipsl.special.pattern.ImplicationShortcutA;

public class GipslBooleanImplicationValidator {

	private GipslBooleanImplicationValidator() {
	}

	private final static PatternMatcher[] IMPLICATION_SHORTCUT_PATTERNS = new PatternMatcher[] {
			new EquivalenceShortcutA(), new EquivalenceShortcutB(), new EquivalenceShortcutC(),
			new EquivalenceShortcutD(), new ImplicationShortcutA() };

	public static void checkBooleanImplicationShortcut(GipsBooleanImplication implication) {
		if (GipslValidator.DISABLE_VALIDATOR)
			return;

		if (implication == null)
			return;

		if (implication.getOperator() == ImplicationOperator.SC_EQUIVALENCE
				|| implication.getOperator() == ImplicationOperator.SC_IMPLICATION) {
			checkForValidShortcutPattern(implication);
		}
	}

	private static void checkForValidShortcutPattern(GipsBooleanImplication implication) {
		for (PatternMatcher pattern : IMPLICATION_SHORTCUT_PATTERNS) {
			if (pattern.matchPattern(implication))
				return; // good!
		}

		List<String> expectedPatterns = new LinkedList<>();
		for (PatternMatcher pattern : IMPLICATION_SHORTCUT_PATTERNS)
			expectedPatterns.addAll(pattern.patterns());
		String list = String.join("\n", expectedPatterns);

		GipslValidator.err( //
				String.format(GipslValidatorUtil.IMPLICATION_SHORTCUT_NO_MATCH, list), //
				implication, //
				null //
		);
	}

}
