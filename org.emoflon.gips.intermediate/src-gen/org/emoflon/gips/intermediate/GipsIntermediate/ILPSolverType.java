/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>ILP Solver Type</b></em>', and utility methods for working with them.
 * <!-- end-user-doc -->
 * 
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getILPSolverType()
 * @model
 * @generated
 */
public enum ILPSolverType implements Enumerator {
	/**
	 * The '<em><b>GUROBI</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #GUROBI_VALUE
	 * @generated
	 * @ordered
	 */
	GUROBI(0, "GUROBI", "GUROBI"),
	/**
	 * The '<em><b>GLPK</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #GLPK_VALUE
	 * @generated
	 * @ordered
	 */
	GLPK(1, "GLPK", "GLPK"),
	/**
	 * The '<em><b>OR</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OR_VALUE
	 * @generated
	 * @ordered
	 */
	OR(2, "OR", "OR");

	/**
	 * The '<em><b>GUROBI</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #GUROBI
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GUROBI_VALUE = 0;

	/**
	 * The '<em><b>GLPK</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #GLPK
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int GLPK_VALUE = 1;

	/**
	 * The '<em><b>OR</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see #OR
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int OR_VALUE = 2;

	/**
	 * An array of all the '<em><b>ILP Solver Type</b></em>' enumerators. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private static final ILPSolverType[] VALUES_ARRAY = new ILPSolverType[] { GUROBI, GLPK, OR, };

	/**
	 * A public read-only list of all the '<em><b>ILP Solver Type</b></em>'
	 * enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static final List<ILPSolverType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>ILP Solver Type</b></em>' literal with the specified
	 * literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ILPSolverType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ILPSolverType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ILP Solver Type</b></em>' literal with the specified
	 * name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ILPSolverType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ILPSolverType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>ILP Solver Type</b></em>' literal with the specified
	 * integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static ILPSolverType get(int value) {
		switch (value) {
		case GUROBI_VALUE:
			return GUROBI;
		case GLPK_VALUE:
			return GLPK;
		case OR_VALUE:
			return OR;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	private ILPSolverType(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string
	 * representation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // ILPSolverType
