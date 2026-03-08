package org.emoflon.gips.build.generator.templates

import org.emoflon.gips.intermediate.GipsIntermediate.AttributeExpression
import org.eclipse.emf.ecore.EcorePackage
import java.util.Set
import java.util.HashSet
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EEnum
import org.emoflon.gips.intermediate.GipsIntermediate.SetOperation
import org.emoflon.gips.intermediate.GipsIntermediate.SetFilter
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeSelect
import org.emoflon.gips.intermediate.GipsIntermediate.SetSort
import org.emoflon.gips.intermediate.GipsIntermediate.SetConcatenation
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleOperation
import org.emoflon.gips.intermediate.GipsIntermediate.SetTransformation
import org.emoflon.gips.build.generator.TemplateData
import org.emoflon.gips.intermediate.GipsIntermediate.ValueExpression
import org.emoflon.gips.intermediate.GipsIntermediate.MappingReference
import org.emoflon.gips.intermediate.GipsIntermediate.TypeReference
import org.emoflon.gips.intermediate.GipsIntermediate.PatternReference
import org.emoflon.gips.intermediate.GipsIntermediate.RuleReference
import org.emoflon.gips.intermediate.GipsIntermediate.SetSummation
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleSelect
import org.emoflon.gips.intermediate.GipsIntermediate.SetTypeQuery
import org.emoflon.gips.intermediate.GipsIntermediate.SetElementQuery
import org.emoflon.gips.intermediate.GipsIntermediate.SetSimpleQuery
import org.emoflon.gips.intermediate.GipsIntermediate.QueryOperator
import org.eclipse.emf.ecore.EObject
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.BooleanLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.ConstantReference
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticBinaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticUnaryExpression
import org.emoflon.gips.intermediate.GipsIntermediate.ArithmeticLiteral
import org.emoflon.gips.intermediate.GipsIntermediate.LinearFunctionReference
import org.emoflon.gips.intermediate.GipsIntermediate.MemberReference
import org.emoflon.gips.intermediate.GipsIntermediate.MemberExpression
import org.emoflon.gips.intermediate.GipsIntermediate.NodeExpression
import org.eclipse.emf.ecore.EStructuralFeature
import java.util.Collection
import org.emoflon.gips.intermediate.GipsIntermediate.VariableReference

class JavaReturnTypeExtractor {

	static final String TYPE_DOUBLE = '''double'''
	static final String TYPE_BOOL = '''boolean'''
	static final String TYPE_STRING = '''String'''

	final TemplateData data
	final ProblemGeneratorTemplate<?> genTemplate

	new(TemplateData data, ProblemGeneratorTemplate<?> genTemplate) {
		this.data = data
		this.genTemplate = genTemplate
	}

	def String extractReturnType(EObject expression, Collection<String> imports) {
		if(expression instanceof ArithmeticExpression)
			return extractReturnType(expression as ArithmeticExpression, imports)

		return extractReturnType(expression as BooleanExpression, imports)
	}

	def String extractReturnType(BooleanExpression expression, Collection<String> imports) {
		if(expression instanceof BooleanBinaryExpression) {
			return TYPE_BOOL

		} else if(expression instanceof BooleanUnaryExpression) {
			return TYPE_BOOL

		} else if(expression instanceof BooleanLiteral) {
			return TYPE_BOOL

		} else if(expression instanceof ConstantLiteral) {
			return TYPE_BOOL

		} else if(expression instanceof ConstantReference) {
			return extractReturnType(expression.constant.expression as BooleanExpression, imports)

		} else if(expression instanceof ArithmeticExpression) {
			return extractReturnType(expression as ArithmeticExpression, imports)

		}

		return TYPE_BOOL
	}

	def String extractReturnType(ArithmeticExpression expression, Collection<String> imports) {
		if(expression instanceof ArithmeticBinaryExpression) {
			val lhs = extractReturnType(expression.getLhs, imports)
			val rhs = extractReturnType(expression.getRhs, imports)

			if(!lhs.equals(rhs))
				throw new UnsupportedOperationException("Arithmetic operator types are mismatching.")

			return lhs

		} else if(expression instanceof ArithmeticUnaryExpression) {
			return extractReturnType(expression.getOperand(), imports)

		} else if(expression instanceof ArithmeticLiteral) {
			return TYPE_DOUBLE

		} else if(expression instanceof ConstantLiteral) {
			return TYPE_DOUBLE

		} else if(expression instanceof LinearFunctionReference) {
			return TYPE_DOUBLE

		} else if(expression instanceof ConstantReference) {
			return extractReturnType(expression.constant.expression as ArithmeticExpression, imports)

		}

		return extractReturnType(expression as ValueExpression, imports)
	}

	def String extractReturnType(ValueExpression expression, Collection<String> imports) {
		var objectType = ""
		var expressionType = ""

		if(expression instanceof MappingReference) {
			imports.add("java.util.stream.Stream")
			imports.add(
				data.apiData.gipsMappingPkg + "." + data.mapping2mappingClassName.get(expression.mapping))
			expressionType = '''Stream<«data.mapping2mappingClassName.get(expression.mapping)»>'''
			objectType = data.mapping2mappingClassName.get(expression.mapping)

		} else if(expression instanceof TypeReference) {
			imports.add("java.util.stream.Stream")
			imports.add(data.classToPackage.getImportsForType(expression.type))
			expressionType = '''Stream<«expression.type.name»>'''
			objectType = expression.type.name

		} else if(expression instanceof PatternReference) {
			imports.add("java.util.stream.Stream")
			imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(expression.pattern))
			expressionType = '''Stream<«data.ibex2matchClassName.get(expression.pattern)»>'''
			objectType = data.ibex2matchClassName.get(expression.pattern)

		} else if(expression instanceof RuleReference) {
			imports.add("java.util.stream.Stream")
			imports.add(data.apiData.matchesPkg + "." + data.ibex2matchClassName.get(expression.rule))
			expressionType = '''Stream<«data.ibex2matchClassName.get(expression.rule)»>'''
			objectType = data.ibex2matchClassName.get(expression.rule)

		} else if(expression instanceof VariableReference) {
			expressionType = extractReturnType(expression, imports)
			objectType = expressionType

		} else if(expression instanceof MemberReference) {
			expressionType = extractReturnType(expression, imports)
			objectType = expressionType

		} else {
			// Case: ContextReference
			expressionType = genTemplate.contextParameterType
			objectType = expressionType
		}

		if(expression.getSetExpression() !== null) {
			if(expression.setExpression.setOperation !== null) {
				imports.add("java.util.stream.Stream")
				objectType = extractReturnType(objectType, expression.setExpression.setOperation, imports)
				expressionType = '''Stream<«objectType»>'''
			}

			if(expression.setExpression.setReduce !== null) {
				val reduce = expression.getSetExpression().getSetReduce()
				if(reduce instanceof SetSummation) {
					return TYPE_DOUBLE
					
				} else if(reduce instanceof SetSimpleSelect) {
					return objectType
					
				} else if(reduce instanceof SetTypeQuery) {
					return TYPE_BOOL
					
				} else if(reduce instanceof SetElementQuery) {
					return TYPE_BOOL
					
				} else if(reduce instanceof SetSimpleQuery) {
					if(reduce.operator == QueryOperator.EMPTY || reduce.operator == QueryOperator.NOT_EMPTY) {
						return TYPE_BOOL
					} else {
						return TYPE_DOUBLE
					}
				}
			}
		}

		return expressionType
	}
	
	def String extractReturnType(VariableReference expression, Collection<String> imports) {
		return TYPE_DOUBLE
	}

	def String extractReturnType(MemberReference expression, Collection<String> imports) {
		return extractReturnType(expression.member, imports)
	}

	def String extractReturnType(MemberExpression expression, Collection<String> imports) {
		if(expression.next !== null)
			return extractReturnType(expression.next, imports)

		if(expression instanceof NodeExpression) {
			imports.add(data.classToPackage.getImportsForType(expression.node.type))
			return expression.node.type.name
		}

		if(expression instanceof AttributeExpression)
			return extractReturnType(expression.feature, imports)

		throw new IllegalArgumentException("Unknown type: " + expression)		
	}

	def String extractReturnType(EStructuralFeature feature, Collection<String> imports) {
		if(feature.EType == EcorePackage.Literals.EBOOLEAN) {
			return TYPE_BOOL

		} else if(feature.EType == EcorePackage.Literals.EDOUBLE) {
			return TYPE_DOUBLE

		} else if(feature.EType == EcorePackage.Literals.EFLOAT) {
			return TYPE_DOUBLE

		} else if(feature.EType == EcorePackage.Literals.EBYTE) {
			return TYPE_DOUBLE

		} else if(feature.EType == EcorePackage.Literals.ESHORT) {
			return TYPE_DOUBLE

		} else if(feature.EType == EcorePackage.Literals.EINT) {
			return TYPE_DOUBLE

		} else if(feature.EType == EcorePackage.Literals.ELONG) {
			return TYPE_DOUBLE

		} else if(feature.EType == EcorePackage.Literals.ESTRING) {
			return TYPE_STRING

		} else if(feature.EType instanceof EClass) {
			imports.add(data.classToPackage.getImportsForType(feature.EType))
			return feature.EType.name

		} else if(feature.EType instanceof EEnum) {
			imports.add(data.classToPackage.getImportsForType(feature.EType))
			return feature.EType.name

		}
		
		throw new IllegalArgumentException("Unsupported data type: " + feature.EType)		
	}

	def String extractReturnType(String previousType, SetOperation expression, Collection<String> imports) {
		var currentType = ""
		if(expression instanceof SetFilter) {
			currentType = previousType

		} else if(expression instanceof SetTypeSelect) {
			imports.add(data.classToPackage.getImportsForType(expression.type))
			currentType = expression.type.name

		} else if(expression instanceof SetSort) {
			currentType = previousType

		} else if(expression instanceof SetConcatenation) {
			currentType = previousType

		} else if(expression instanceof SetSimpleOperation) {
			currentType = previousType

		} else {
			val transform = expression as SetTransformation
			currentType = extractReturnType(transform.expression, imports)
			if(currentType.contains("Stream")) {
				val first = currentType.split("<")
				val second = first.get(0).split(">")
				currentType = second.get(0)
			}
		}

		if(expression.next !== null)
			return extractReturnType(currentType, expression.next, imports)

		return currentType
	}

}
