/**
 */
package org.emoflon.gips.intermediate.GipsIntermediate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc --> A representation of the literals of the enumeration
 * '<em><b>Binary Arithmetic Operator</b></em>', and utility methods for working
 * with them. <!-- end-user-doc -->
 * @see org.emoflon.gips.intermediate.GipsIntermediate.GipsIntermediatePackage#getBinaryArithmeticOperator()
 * @model
 * @generated
 */
public enum BinaryArithmeticOperator implements Enumerator {
	/**
	 * The '<em><b>ADD</b></em>' literal object.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #ADD_VALUE
	 * @generated
	 * @ordered
	 */
	ADD(0, "ADD", "ADD"),

	/**
	 * The '<em><b>SUBTRACT</b></em>' literal object.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #SUBTRACT_VALUE
	 * @generated
	 * @ordered
	 */
	SUBTRACT(1, "SUBTRACT", "SUBTRACT"),

	/**
	 * The '<em><b>MULTIPLY</b></em>' literal object.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #MULTIPLY_VALUE
	 * @generated
	 * @ordered
	 */
	MULTIPLY(2, "MULTIPLY", "MULTIPLY"),

	/**
	 * The '<em><b>DIVIDE</b></em>' literal object.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #DIVIDE_VALUE
	 * @generated
	 * @ordered
	 */
	DIVIDE(3, "DIVIDE", "DIVIDE"),

	/**
	 * The '<em><b>POW</b></em>' literal object.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #POW_VALUE
	 * @generated
	 * @ordered
	 */
	POW(4, "POW", "POW");

	/**
	 * The '<em><b>ADD</b></em>' literal value.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #ADD
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int ADD_VALUE = 0;

	/**
	 * The '<em><b>SUBTRACT</b></em>' literal value.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #SUBTRACT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SUBTRACT_VALUE = 1;

	/**
	 * The '<em><b>MULTIPLY</b></em>' literal value.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #MULTIPLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MULTIPLY_VALUE = 2;

	/**
	 * The '<em><b>DIVIDE</b></em>' literal value.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #DIVIDE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int DIVIDE_VALUE = 3;

	/**
	 * The '<em><b>POW</b></em>' literal value.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @see #POW
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int POW_VALUE = 4;

	/**
	 * An array of all the '<em><b>Binary Arithmetic Operator</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private static final BinaryArithmeticOperator[] VALUES_ARRAY = new BinaryArithmeticOperator[] { ADD, SUBTRACT,
			MULTIPLY, DIVIDE, POW, };

	/**
	 * A public read-only list of all the '<em><b>Binary Arithmetic Operator</b></em>' enumerators.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<BinaryArithmeticOperator> VALUES = Collections
			.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Binary Arithmetic Operator</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BinaryArithmeticOperator get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BinaryArithmeticOperator result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Binary Arithmetic Operator</b></em>' literal with the specified name.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BinaryArithmeticOperator getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			BinaryArithmeticOperator result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Binary Arithmetic Operator</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static BinaryArithmeticOperator get(int value) {
		switch (value) {
		case ADD_VALUE:
			return ADD;
		case SUBTRACT_VALUE:
			return SUBTRACT;
		case MULTIPLY_VALUE:
			return MULTIPLY;
		case DIVIDE_VALUE:
			return DIVIDE;
		case POW_VALUE:
			return POW;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	private BinaryArithmeticOperator(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return value;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
		return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}

} // BinaryArithmeticOperator
