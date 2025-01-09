package org.emoflon.gips.build.transformation;

import java.util.Map;

import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.logicng.formulas.Formula;

public record GipsAnnotatedConstraint(GipsConstraint input, Formula formula, AnnotatedConstraintType type,
		Map<Formula, GipsConstraint> result) {

}

enum AnnotatedConstraintType {
	LITERAL, NEGATED_LITERAL, DISJUNCTION_OF_LITERALS
}
