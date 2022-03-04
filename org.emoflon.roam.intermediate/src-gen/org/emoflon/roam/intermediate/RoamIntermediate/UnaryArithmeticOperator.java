/**
 */
package org.emoflon.roam.intermediate.RoamIntermediate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Unary Arithmetic Operator</b></em>', and utility methods for working
 * with them. <!-- end-user-doc -->
 *
 * @see org.emoflon.roam.intermediate.RoamIntermediate.RoamIntermediatePackage#getUnaryArithmeticOperator()
 * @model
 * @generated
 */
public enum UnaryArithmeticOperator implements Enumerator {
	/**
	 * The '<em><b>BRACKET</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #BRACKET_VALUE
	 * @generated
	 * @ordered
	 */
	BRACKET(0, "BRACKET", "BRACKET"),

	/**
	 * The '<em><b>NEGATE</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #NEGATE_VALUE
	 * @generated
	 * @ordered
	 */
	NEGATE(1, "NEGATE", "NEGATE"),

	/**
	 * The '<em><b>ABSOLUTE</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #ABSOLUTE_VALUE
	 * @generated
	 * @ordered
	 */
	ABSOLUTE(2, "ABSOLUTE", "ABSOLUTE"),

	/**
	 * The '<em><b>SINE</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #SINE_VALUE
	 * @generated
	 * @ordered
	 */
	SINE(3, "SINE", "SINE"),

	/**
	 * The '<em><b>COSINE</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #COSINE_VALUE
	 * @generated
	 * @ordered
	 */
	COSINE(4, "COSINE", "COSINE"),

	/**
	 * The '<em><b>SQRT</b></em>' literal object. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #SQRT_VALUE
	 * @generated
	 * @ordered
	 */
	SQRT(5, "SQRT", "SQRT");

	/**
	 * The '<em><b>BRACKET</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #BRACKET
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BRACKET_VALUE = 0;

	/**
	 * The '<em><b>NEGATE</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #NEGATE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int NEGATE_VALUE = 1;

	/**
	 * The '<em><b>ABSOLUTE</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #ABSOLUTE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ABSOLUTE_VALUE = 2;

	/**
	 * The '<em><b>SINE</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #SINE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SINE_VALUE = 3;

	/**
	 * The '<em><b>COSINE</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #COSINE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int COSINE_VALUE = 4;

	/**
	 * The '<em><b>SQRT</b></em>' literal value. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 *
	 * @see #SQRT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SQRT_VALUE = 5;

	/**
	 * An array of all the '<em><b>Unary Arithmetic Operator</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	private static final UnaryArithmeticOperator[] VALUES_ARRAY = new UnaryArithmeticOperator[] { BRACKET, NEGATE,
			ABSOLUTE, SINE, COSINE, SQRT, };

	/**
	 * A public read-only list of all the '<em><b>Unary Arithmetic
	 * Operator</b></em>' enumerators. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	public static final List<UnaryArithmeticOperator> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Unary Arithmetic Operator</b></em>' literal with the
	 * specified literal value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryArithmeticOperator get(String literal) {
		for (UnaryArithmeticOperator result : VALUES_ARRAY) {
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Arithmetic Operator</b></em>' literal with the
	 * specified name. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryArithmeticOperator getByName(String name) {
		for (UnaryArithmeticOperator result : VALUES_ARRAY) {
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Unary Arithmetic Operator</b></em>' literal with the
	 * specified integer value. <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static UnaryArithmeticOperator get(int value) {
		switch (value) {
		case BRACKET_VALUE:
			return BRACKET;
		case NEGATE_VALUE:
			return NEGATE;
		case ABSOLUTE_VALUE:
			return ABSOLUTE;
		case SINE_VALUE:
			return SINE;
		case COSINE_VALUE:
			return COSINE;
		case SQRT_VALUE:
			return SQRT;
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
	private UnaryArithmeticOperator(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 *
	 * @generated
	 */
	@Override
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

} // UnaryArithmeticOperator
