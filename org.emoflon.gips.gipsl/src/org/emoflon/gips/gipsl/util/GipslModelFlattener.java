package org.emoflon.gips.gipsl.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.gipsl.gipsl.EditorFile;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticBracket;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticConstant;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExponential;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpression;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticOperand;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticProduct;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticSum;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticUnary;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanBracket;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanConjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanDisjunction;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanExpression;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanImplication;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanNegation;
import org.emoflon.gips.gipsl.gipsl.GipsConcatenationOperation;
import org.emoflon.gips.gipsl.gipsl.GipsConfig;
import org.emoflon.gips.gipsl.gipsl.GipsConstant;
import org.emoflon.gips.gipsl.gipsl.GipsConstantReference;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsElementQuery;
import org.emoflon.gips.gipsl.gipsl.GipsFilterOperation;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunctionReference;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsNodeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsPatternExpression;
import org.emoflon.gips.gipsl.gipsl.GipsReduceOperation;
import org.emoflon.gips.gipsl.gipsl.GipsRelationalExpression;
import org.emoflon.gips.gipsl.gipsl.GipsRuleExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetElementExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetExpression;
import org.emoflon.gips.gipsl.gipsl.GipsSetOperation;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleAlgorithm;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleQuery;
import org.emoflon.gips.gipsl.gipsl.GipsSimpleSelect;
import org.emoflon.gips.gipsl.gipsl.GipsSortOperation;
import org.emoflon.gips.gipsl.gipsl.GipsSortPredicate;
import org.emoflon.gips.gipsl.gipsl.GipsSumOperation;
import org.emoflon.gips.gipsl.gipsl.GipsTransformOperation;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsTypeQuery;
import org.emoflon.gips.gipsl.gipsl.GipsTypeSelect;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;
import org.emoflon.ibex.gt.gtl.util.GTLModelFlattener;

public class GipslModelFlattener extends GTLModelFlattener {

	final protected GipslFactory gipslFactory = GipslPackage.eINSTANCE.getGipslFactory();

	protected Map<String, GipsMapping> name2mapping = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, GipsConstant> name2constant = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, GipsLinearFunction> name2function = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, List<Consumer<GipsMapping>>> pendingMappingJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, List<Consumer<GipsConstant>>> pendingConstantJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, List<Consumer<GipsLinearFunction>>> pendingFunctionJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());

	public GipslModelFlattener(final GipslResourceManager gtlManager,
			final Collection<org.emoflon.ibex.gt.gtl.gTL.EditorFile> files) throws Exception {
		super(gtlManager, files);

		files.stream().filter(file -> (file instanceof EditorFile)).map(file -> (EditorFile) file)
				.forEach(file -> flattenGipslModel(file));

		name2mapping.forEach((name, mapping) -> {
			if (pendingMappingJobs.containsKey(name))
				pendingMappingJobs.get(name).forEach(consumer -> consumer.accept(mapping));
		});

		name2constant.forEach((name, constant) -> {
			if (pendingConstantJobs.containsKey(name))
				pendingConstantJobs.get(name).forEach(consumer -> consumer.accept(constant));
		});

		name2function.forEach((name, objective) -> {
			if (pendingFunctionJobs.containsKey(name))
				pendingFunctionJobs.get(name).forEach(consumer -> consumer.accept(objective));
		});
	}

	@Override
	protected EditorFile createNewEditorFile() {
		EditorFile file = GipslPackage.eINSTANCE.getGipslFactory().createEditorFile();
		return file;
	}

	@Override
	public EditorFile getFlattenedModel() {
		return (EditorFile) flattenedFile;
	}

	protected void flattenGipslModel(final EditorFile file) {
		EditorFile flattenedFile = getFlattenedModel();
		// Flatten config, if defined
		if (file.getConfig() != null) {
			flattenedFile.setConfig(flatten(file.getConfig()));
		}
		// Flatten mappings
		for (GipsMapping mapping : file.getMappings()) {
			GipsMapping flattenedMapping = flatten(mapping);
			flattenedFile.getMappings().add(flattenedMapping);
		}
		// Flatten constants
		for (GipsConstant constant : file.getConstants()) {
			GipsConstant flattenedConstant = flatten(constant, true);
			flattenedFile.getConstants().add(flattenedConstant);
		}
		// Flatten constraints
		for (GipsConstraint constraint : file.getConstraints()) {
			GipsConstraint flattenedConstraint = flatten(constraint);
			flattenedFile.getConstraints().add(flattenedConstraint);
		}
		// Flatten Objectives
		for (GipsLinearFunction function : file.getFunctions()) {
			GipsLinearFunction flattenedLinearFunction = flatten(function);
			flattenedFile.getFunctions().add(flattenedLinearFunction);
		}
		// Flatten global objective, if defined
		if (file.getObjective() != null) {
			flattenedFile.setObjective(flatten(file.getObjective()));
		}
	}

	protected GipsConfig flatten(final GipsConfig config) {
		GipsConfig fc = gipslFactory.createGipsConfig();
		fc.setSolver(config.getSolver());
		fc.setHome(config.getHome());
		fc.setLicense(config.getLicense());

		fc.setEnableLaunchConfig(config.isEnableLaunchConfig());
		fc.setMainLoc(config.getMainLoc());

		fc.setEnableLimit(config.isEnableLimit());
		fc.setTimeLimit(config.getTimeLimit());

		fc.setEnableSeed(config.isEnableSeed());
		fc.setRndSeed(config.getRndSeed());

		fc.setEnablePresolve(config.isEnablePresolve());

		fc.setEnableDebugOutput(config.isEnableDebugOutput());

		fc.setEnableTolerance(config.isEnableTolerance());
		fc.setTolerance(config.getTolerance());

		fc.setEnableLpOutput(config.isEnableLpOutput());
		fc.setPath(config.getPath());

		fc.setEnableThreadCount(config.isEnableThreadCount());
		fc.setThreads(config.getThreads());

		return fc;
	}

	protected GipsMapping flatten(final GipsMapping mapping) {
		GipsMapping flattenedMapping = gipslFactory.createGipsMapping();
		flattenedMapping.setName(mapping.getName());
		name2mapping.put(mapping.getName(), flattenedMapping);

		flattenedMapping.setPattern(name2rule.get(mapping.getPattern().getName()));
		return flattenedMapping;
	}

	protected GipsConstant flatten(final GipsConstant constant, boolean isGlobal) {
		GipsConstant flattenedConstant = gipslFactory.createGipsConstant();
		flattenedConstant.setName(constant.getName());
		if (isGlobal)
			name2constant.put(constant.getName(), flattenedConstant);

		flattenedConstant.setExpression(flatten(constant.getExpression(), new HashMap<>()));
		return flattenedConstant;
	}

	protected GipsConstraint flatten(final GipsConstraint constraint) {
		GipsConstraint flattenedConstraint = gipslFactory.createGipsConstraint();

		setContext(flattenedConstraint, constraint.getContext());

		Map<String, GipsConstant> localConstants = new HashMap<>();
		for (GipsConstant constant : constraint.getConstants()) {
			GipsConstant flattenedConstant = flatten(constant, false);
			flattenedConstraint.getConstants().add(flattenedConstant);
			localConstants.put(constant.getName(), flattenedConstant);
		}

		flattenedConstraint.setExpression(flatten(constraint.getExpression(), localConstants));
		return flattenedConstraint;
	}

	protected GipsLinearFunction flatten(final GipsLinearFunction function) {
		GipsLinearFunction flattenedFunction = gipslFactory.createGipsLinearFunction();
		flattenedFunction.setName(function.getName());
		name2function.put(flattenedFunction.getName(), flattenedFunction);

		setContext(flattenedFunction, function.getContext());

		Map<String, GipsConstant> localConstants = new HashMap<>();
		for (GipsConstant constant : function.getConstants()) {
			GipsConstant flattenedConstant = flatten(constant, false);
			flattenedFunction.getConstants().add(flattenedConstant);
			localConstants.put(constant.getName(), flattenedConstant);
		}

		flattenedFunction.setExpression(flatten(function.getExpression(), localConstants));
		return flattenedFunction;
	}

	protected GipsObjective flatten(final GipsObjective objective) {
		GipsObjective flattenedObjective = gipslFactory.createGipsObjective();
		flattenedObjective.setGoal(objective.getGoal());

		Map<String, GipsConstant> localConstants = new HashMap<>();
		for (GipsConstant constant : objective.getConstants()) {
			GipsConstant flattenedConstant = flatten(constant, false);
			flattenedObjective.getConstants().add(flattenedConstant);
			localConstants.put(constant.getName(), flattenedConstant);
		}

		if (objective.getExpression() != null) {
			flattenedObjective.setExpression(flatten(objective.getExpression(), localConstants));
		}
		return flattenedObjective;
	}

	protected GipsBooleanExpression flatten(final GipsBooleanExpression expr,
			final Map<String, GipsConstant> localConstants) {
		if (expr instanceof GipsBooleanImplication imp) {
			GipsBooleanImplication flattenedImp = gipslFactory.createGipsBooleanImplication();
			flattenedImp.setLeft(flatten(imp.getLeft(), localConstants));
			flattenedImp.setRight(flatten(imp.getRight(), localConstants));
			flattenedImp.setOperator(imp.getOperator());
			return flattenedImp;
		} else if (expr instanceof GipsBooleanDisjunction or) {
			GipsBooleanDisjunction flattenedOr = gipslFactory.createGipsBooleanDisjunction();
			flattenedOr.setLeft(flatten(or.getLeft(), localConstants));
			flattenedOr.setRight(flatten(or.getRight(), localConstants));
			flattenedOr.setOperator(or.getOperator());
			return flattenedOr;
		} else if (expr instanceof GipsBooleanConjunction and) {
			GipsBooleanConjunction flattenedAnd = gipslFactory.createGipsBooleanConjunction();
			flattenedAnd.setLeft(flatten(and.getLeft(), localConstants));
			flattenedAnd.setRight(flatten(and.getRight(), localConstants));
			flattenedAnd.setOperator(and.getOperator());
			return flattenedAnd;
		} else if (expr instanceof GipsBooleanNegation not) {
			GipsBooleanNegation flattenedNot = gipslFactory.createGipsBooleanNegation();
			flattenedNot.setOperand(flatten(not.getOperand(), localConstants));
			return flattenedNot;
		} else if (expr instanceof GipsBooleanBracket brack) {
			GipsBooleanBracket flattenedBrack = gipslFactory.createGipsBooleanBracket();
			flattenedBrack.setOperand(flatten(brack.getOperand(), localConstants));
			return flattenedBrack;
		} else if (expr instanceof GipsBooleanLiteral lit) {
			GipsBooleanLiteral flattenedLit = gipslFactory.createGipsBooleanLiteral();
			flattenedLit.setLiteral(lit.isLiteral());
			return flattenedLit;
		} else if (expr instanceof GipsArithmeticConstant constant) {
			GipsArithmeticConstant flattenedConstant = gipslFactory.createGipsArithmeticConstant();
			flattenedConstant.setValue(constant.getValue());
			return flattenedConstant;
		} else if (expr instanceof GipsConstantReference reference) {
			return flatten(reference, localConstants);
		} else if (expr instanceof GipsRelationalExpression rel) {
			GipsRelationalExpression flattenedRel = gipslFactory.createGipsRelationalExpression();

			if (rel.getLeft() instanceof GipsArithmeticExpression ae) {
				flattenedRel.setLeft(flatten(ae, localConstants));
			} else {
				flattenedRel.setLeft(flatten((GipsBooleanExpression) rel.getLeft(), localConstants));
			}

			if (rel.getRight() instanceof GipsArithmeticExpression ae) {
				flattenedRel.setRight(flatten(ae, localConstants));
			} else {
				flattenedRel.setRight(flatten(rel.getRight(), localConstants));
			}

			flattenedRel.setOperator(rel.getOperator());

			return flattenedRel;
		} else if (expr instanceof GipsArithmeticExpression ae) {
			return flatten(ae, localConstants);
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression type: " + expr);
		}
	}

	protected GipsConstantReference flatten(final GipsConstantReference reference,
			final Map<String, GipsConstant> localConstants) {
		GipsConstantReference flattenedReference = gipslFactory.createGipsConstantReference();
		if (reference.getConstant().eContainer() instanceof EditorFile) {
			addPendingConstantConsumer(reference.getConstant().getName(),
					(constant) -> flattenedReference.setConstant(constant));
		} else {
			flattenedReference.setConstant(localConstants.get(reference.getConstant().getName()));
		}

		if (reference.getSetExpression() != null) {
			reference.setSetExpression(flatten(reference.getSetExpression(), localConstants));
		}

		return flattenedReference;
	}

	protected GipsArithmeticExpression flatten(final GipsArithmeticExpression expr,
			final Map<String, GipsConstant> localConstants) {
		if (expr instanceof GipsArithmeticSum sum) {
			GipsArithmeticSum flattenedSum = gipslFactory.createGipsArithmeticSum();
			flattenedSum.setLeft(flatten(sum.getLeft(), localConstants));
			flattenedSum.setRight(flatten(sum.getRight(), localConstants));
			flattenedSum.setOperator(sum.getOperator());
			return flattenedSum;
		} else if (expr instanceof GipsArithmeticProduct prod) {
			GipsArithmeticProduct flattenedProd = gipslFactory.createGipsArithmeticProduct();
			flattenedProd.setLeft(flatten(prod.getLeft(), localConstants));
			flattenedProd.setRight(flatten(prod.getRight(), localConstants));
			flattenedProd.setOperator(prod.getOperator());
			return flattenedProd;
		} else if (expr instanceof GipsArithmeticExponential exp) {
			GipsArithmeticExponential flattenedExp = gipslFactory.createGipsArithmeticExponential();
			flattenedExp.setLeft(flatten(exp.getLeft(), localConstants));
			flattenedExp.setRight(flatten(exp.getRight(), localConstants));
			flattenedExp.setOperator(exp.getOperator());
			return flattenedExp;
		} else if (expr instanceof GipsArithmeticUnary unary) {
			GipsArithmeticUnary flattenedUnary = gipslFactory.createGipsArithmeticUnary();
			flattenedUnary.setOperand(flatten(unary.getOperand(), localConstants));
			flattenedUnary.setOperator(unary.getOperator());
			return flattenedUnary;
		} else if (expr instanceof GipsArithmeticBracket brack) {
			GipsArithmeticBracket flattenedBrack = gipslFactory.createGipsArithmeticBracket();
			flattenedBrack.setOperand(flatten(brack.getOperand(), localConstants));
			return flattenedBrack;
		} else if (expr instanceof GipsArithmeticOperand op) {
			return flatten(op, localConstants);
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expr);
		}
	}

	protected GipsArithmeticOperand flatten(final GipsArithmeticOperand expr,
			final Map<String, GipsConstant> localConstants) {
		if (expr instanceof GipsValueExpression value) {
			return flatten(value, localConstants);
		} else if (expr instanceof GipsLinearFunctionReference reference) {
			GipsLinearFunctionReference flattenedReference = gipslFactory.createGipsLinearFunctionReference();
			addPendingFunctionConsumer(reference.getFunction().getName(),
					(function) -> flattenedReference.setFunction(function));
			return flattenedReference;
		} else if (expr instanceof GipsConstantReference reference) {
			return flatten(reference, localConstants);
		} else if (expr instanceof GipsArithmeticLiteral lit) {
			GipsArithmeticLiteral flattenedLit = gipslFactory.createGipsArithmeticLiteral();
			flattenedLit.setValue(lit.getValue());
			return flattenedLit;
		} else if (expr instanceof GipsArithmeticConstant constant) {
			GipsArithmeticConstant flattenedConstant = gipslFactory.createGipsArithmeticConstant();
			flattenedConstant.setValue(constant.getValue());
			return flattenedConstant;
		} else {
			throw new UnsupportedOperationException("Unknown expression operand type: " + expr);
		}
	}

	protected GipsValueExpression flatten(final GipsValueExpression expr,
			final Map<String, GipsConstant> localConstants) {
		GipsValueExpression flattenedValue = gipslFactory.createGipsValueExpression();
		if (expr.getValue() instanceof GipsMappingExpression mapping) {
			GipsMappingExpression flattenedMapping = gipslFactory.createGipsMappingExpression();
			addPendingMappingConsumer(mapping.getMapping().getName(), (map) -> flattenedMapping.setMapping(map));
			flattenedValue.setValue(flattenedMapping);
		} else if (expr.getValue() instanceof GipsTypeExpression type) {
			GipsTypeExpression flattenedType = gipslFactory.createGipsTypeExpression();
			flattenedType.setType(type.getType());
			flattenedValue.setValue(flattenedType);
		} else if (expr.getValue() instanceof GipsPatternExpression pattern) {
			GipsPatternExpression flattenedPattern = gipslFactory.createGipsPatternExpression();
			flattenedPattern.setPattern(name2rule.get(pattern.getPattern().getName()));
			flattenedValue.setValue(flattenedPattern);
		} else if (expr.getValue() instanceof GipsRuleExpression rule) {
			GipsRuleExpression flattenedRule = gipslFactory.createGipsRuleExpression();
			flattenedRule.setRule(name2rule.get(rule.getRule().getName()));
			flattenedValue.setValue(flattenedRule);
		} else if (expr.getValue() instanceof GipsLocalContextExpression local) {
			flattenedValue.setValue(flatten(local));
		} else if (expr.getValue() instanceof GipsSetElementExpression set) {
			flattenedValue.setValue(flatten(set));
		}

		if (expr.getSetExperession() != null) {
			flattenedValue.setSetExperession(flatten(expr.getSetExperession(), localConstants));
		}

		return flattenedValue;
	}

	protected GipsLocalContextExpression flatten(final GipsLocalContextExpression local) {
		GipsLocalContextExpression flattenedLocal = gipslFactory.createGipsLocalContextExpression();

		if (local.getExpression() instanceof GipsAttributeExpression attribute) {
			flattenedLocal.setExpression(flatten(attribute));
		} else if (local.getExpression() instanceof GipsNodeExpression node) {
			flattenedLocal.setExpression(flatten(node));
		} else if (local.getExpression() instanceof GipsVariableReferenceExpression reference) {
			flattenedLocal.setExpression(flatten(reference));
		}

		return flattenedLocal;
	}

	protected GipsSetElementExpression flatten(final GipsSetElementExpression set) {
		GipsSetElementExpression flattenedSet = gipslFactory.createGipsSetElementExpression();

		if (set.getExpression() instanceof GipsAttributeExpression attribute) {
			flattenedSet.setExpression(flatten(attribute));
		} else if (set.getExpression() instanceof GipsNodeExpression node) {
			flattenedSet.setExpression(flatten(node));
		} else if (set.getExpression() instanceof GipsVariableReferenceExpression reference) {
			flattenedSet.setExpression(flatten(reference));
		}

		return flattenedSet;
	}

	protected GipsAttributeExpression flatten(final GipsAttributeExpression attribute) {
		GipsAttributeExpression flattenedAttribute = gipslFactory.createGipsAttributeExpression();
		flattenedAttribute.setAttribute(flatten(attribute.getAttribute()));
		if (attribute.getRight() != null) {
			flattenedAttribute.setRight(flatten(attribute.getRight()));
		}

		return flattenedAttribute;
	}

	protected GipsAttributeLiteral flatten(final GipsAttributeLiteral literal) {
		GipsAttributeLiteral flattenedLiteral = gipslFactory.createGipsAttributeLiteral();
		flattenedLiteral.setLiteral(literal.getLiteral());
		return flattenedLiteral;
	}

	protected GipsNodeExpression flatten(final GipsNodeExpression node) {
		GipsNodeExpression flattenedNode = gipslFactory.createGipsNodeExpression();
		SlimRule rule = SlimGTModelUtil.getContainer(node.getNode(), SlimRule.class);
		flattenedNode.setNode(rule2nodes.get(name2rule.get(rule.getName())).get(node.getNode().getName()));

		if (node.getAttributeExpression() != null) {
			GipsAttributeExpression flattenedAttribute = flatten(node.getAttributeExpression());
			flattenedNode.setAttributeExpression(flattenedAttribute);
		}
		return flattenedNode;
	}

	protected GipsVariableReferenceExpression flatten(final GipsVariableReferenceExpression reference) {
		GipsVariableReferenceExpression flattenedReference = gipslFactory.createGipsVariableReferenceExpression();
		if (reference.isIsMappingValue()) {
			flattenedReference.setIsMappingValue(true);
		} else {
			flattenedReference.setIsGenericValue(true);
			GipsMapping mapping = (GipsMapping) reference.getVariable().eContainer();
			addPendingMappingConsumer(mapping.getName(), (map) -> flattenedReference.setVariable(map.getVariables()
					.stream().filter(v -> v.getName().equals(reference.getVariable().getName())).findFirst().get()));
		}
		return flattenedReference;
	}

	protected GipsSetExpression flatten(final GipsSetExpression set, final Map<String, GipsConstant> localConstants) {
		GipsSetExpression flattenedSet = gipslFactory.createGipsSetExpression();
		if (set.getOperation() instanceof GipsSetOperation op) {
			flattenedSet.setOperation(flatten(op, localConstants));
		} else if (set.getOperation() instanceof GipsReduceOperation reduce) {
			flattenedSet.setOperation(flatten(reduce, localConstants));
		}

		if (set.getRight() != null) {
			flattenedSet.setRight(flatten(set.getRight(), localConstants));
		}

		return flattenedSet;
	}

	protected GipsSetOperation flatten(final GipsSetOperation set, final Map<String, GipsConstant> localConstants) {
		if (set instanceof GipsFilterOperation filter) {
			GipsFilterOperation flattened = gipslFactory.createGipsFilterOperation();
			flattened.setExpression(flatten(filter.getExpression(), localConstants));
			return flattened;
		} else if (set instanceof GipsTypeSelect select) {
			GipsTypeSelect flattened = gipslFactory.createGipsTypeSelect();
			flattened.setType(select.getType());
			return flattened;
		} else if (set instanceof GipsSortOperation sort) {
			GipsSortOperation flattened = gipslFactory.createGipsSortOperation();
			flattened.setPredicate(flatten(sort.getPredicate()));
			return flattened;
		} else if (set instanceof GipsSimpleAlgorithm algo) {
			GipsSimpleAlgorithm flattened = gipslFactory.createGipsSimpleAlgorithm();
			flattened.setOperator(algo.getOperator());
			return flattened;
		} else if (set instanceof GipsConcatenationOperation cat) {
			GipsConcatenationOperation flattened = gipslFactory.createGipsConcatenationOperation();
			flattened.setOperator(cat.getOperator());
			flattened.setValue(flatten(cat.getValue(), localConstants));
			return flattened;
		} else {
			GipsTransformOperation transform = (GipsTransformOperation) set;
			GipsTransformOperation flattened = gipslFactory.createGipsTransformOperation();
			flattened.setExpression(flatten(transform.getExpression()));
			return flattened;
		}
	}

	protected GipsSortPredicate flatten(final GipsSortPredicate predicate) {
		GipsSortPredicate flattenedPredicate = gipslFactory.createGipsSortPredicate();
		flattenedPredicate.setRelation(predicate.getRelation());
		if (predicate.getE1() instanceof GipsNodeExpression node) {
			flattenedPredicate.setE1(flatten(node));
		} else if (predicate.getE1() instanceof GipsAttributeExpression attribute) {
			flattenedPredicate.setE1(flatten(attribute));
		}
		if (predicate.getE2() instanceof GipsNodeExpression node) {
			flattenedPredicate.setE2(flatten(node));
		} else if (predicate.getE2() instanceof GipsAttributeExpression attribute) {
			flattenedPredicate.setE2(flatten(attribute));
		}
		return flattenedPredicate;
	}

	protected GipsReduceOperation flatten(final GipsReduceOperation reduce,
			final Map<String, GipsConstant> localConstants) {
		if (reduce instanceof GipsSumOperation sum) {
			GipsSumOperation flattened = gipslFactory.createGipsSumOperation();
			flattened.setExpression(flatten(sum.getExpression(), localConstants));
			return flattened;
		} else if (reduce instanceof GipsSimpleSelect select) {
			GipsSimpleSelect flattened = gipslFactory.createGipsSimpleSelect();
			flattened.setOperator(select.getOperator());
			return flattened;
		} else if (reduce instanceof GipsTypeQuery type) {
			GipsTypeQuery flattened = gipslFactory.createGipsTypeQuery();
			flattened.setType(type.getType());
			return flattened;
		} else if (reduce instanceof GipsElementQuery element) {
			GipsElementQuery flattened = gipslFactory.createGipsElementQuery();
			flattened.setElement(flatten(element.getElement(), localConstants));
			return flattened;
		} else {
			GipsSimpleQuery simple = (GipsSimpleQuery) reduce;
			GipsSimpleQuery flattened = gipslFactory.createGipsSimpleQuery();
			flattened.setOperator(simple.getOperator());
			return flattened;
		}
	}

	protected void setContext(final GipsConstraint constraint, final EObject context) {
		if (context instanceof GipsMapping mapping) {
			addPendingMappingConsumer(mapping.getName(), (map) -> constraint.setContext(map));
		} else if (context instanceof SlimRule sr) {
			constraint.setContext(name2rule.get(sr.getName()));
		} else {
			constraint.setContext(context);
		}
	}

	protected void setContext(final GipsLinearFunction function, final EObject context) {
		if (context instanceof GipsMapping mapping) {
			addPendingMappingConsumer(mapping.getName(), (map) -> function.setContext(map));
		} else if (context instanceof SlimRule sr) {
			function.setContext(name2rule.get(sr.getName()));
		} else {
			function.setContext(context);
		}
	}

	protected void addPendingMappingConsumer(final String mapping, final Consumer<GipsMapping> consumer) {
		List<Consumer<GipsMapping>> consumers = pendingMappingJobs.get(mapping);
		if (consumers == null) {
			consumers = Collections.synchronizedList(new LinkedList<>());
			pendingMappingJobs.put(mapping, consumers);
		}
		consumers.add(consumer);
	}

	protected void addPendingConstantConsumer(final String constant, final Consumer<GipsConstant> consumer) {
		List<Consumer<GipsConstant>> consumers = pendingConstantJobs.get(constant);
		if (consumers == null) {
			consumers = Collections.synchronizedList(new LinkedList<>());
			pendingConstantJobs.put(constant, consumers);
		}
		consumers.add(consumer);
	}

	protected void addPendingFunctionConsumer(final String objective, final Consumer<GipsLinearFunction> consumer) {
		List<Consumer<GipsLinearFunction>> consumers = pendingFunctionJobs.get(objective);
		if (consumers == null) {
			consumers = Collections.synchronizedList(new LinkedList<>());
			pendingFunctionJobs.put(objective, consumers);
		}
		consumers.add(consumer);
	}
}