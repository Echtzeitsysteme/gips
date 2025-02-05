package org.emoflon.gips.build.generator;

import java.util.Set;

import org.eclipse.emf.ecore.EcorePackage;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.ibex.gt.build.template.ExpressionHelper;
import org.emoflon.ibex.gt.build.template.IBeXGTApiData;
import org.emoflon.ibex.gt.gtmodel.IBeXGTModel.GTParameter;

public class GipsExpressionHelper extends ExpressionHelper {

	public GipsExpressionHelper(IBeXGTApiData data, Set<String> imports) {
		super(data, imports);
	}

	public String variableToJavaDataType(final Variable var) {
		switch (var.getType()) {
		case BINARY:
			imports.add("org.emoflon.gips.core.milp.model.BinaryVariable");
			return "BinaryVariable";
		case INTEGER:
			imports.add("org.emoflon.gips.core.milp.model.IntegerVariable");
			return "IntegerVariable";
		case REAL:
			imports.add("org.emoflon.gips.core.milp.model.RealVariable");
			return "RealVariable";
		default:
			throw new UnsupportedOperationException("Unsupported ilp variable data type : " + var.getType());

		}
	}

	public String variableToSimpleJavaDataType(final Variable var) {
		switch (var.getType()) {
		case BINARY:
			return "boolean";
		case INTEGER:
			return "int";
		case REAL:
			return "double";
		default:
			throw new UnsupportedOperationException("Unsupported ilp variable data type : " + var.getType());

		}
	}

	public String parameterToJavaDefaultValue(final GTParameter par) {
		if (par.getType() == EcorePackage.Literals.EINT || par.getType() == EcorePackage.Literals.ESHORT
				|| par.getType() == EcorePackage.Literals.ELONG || par.getType() == EcorePackage.Literals.EBYTE) {
			return "0";
		} else if (par.getType() == EcorePackage.Literals.EFLOAT || par.getType() == EcorePackage.Literals.EDOUBLE) {
			return "0.0";
		} else if (par.getType() == EcorePackage.Literals.ESTRING || par.getType() == EcorePackage.Literals.ECHAR) {
			return "\"\"";
		} else if (par.getType() == EcorePackage.Literals.EBOOLEAN) {
			return "false";
		} else {
			throw new UnsupportedOperationException("Unsupported parameter data type : " + par.getType());
		}
	}
}
