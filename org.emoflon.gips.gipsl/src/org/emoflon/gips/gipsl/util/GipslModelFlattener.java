package org.emoflon.gips.gipsl.util;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.eclipse.emf.ecore.EObject;
import org.emoflon.gips.gipsl.gipsl.EditorFile;
import org.emoflon.gips.gipsl.gipsl.GipsAndBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsArithmeticLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBool;
import org.emoflon.gips.gipsl.gipsl.GipsBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBooleanLiteral;
import org.emoflon.gips.gipsl.gipsl.GipsBracketBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsBracketExpr;
import org.emoflon.gips.gipsl.gipsl.GipsConfig;
import org.emoflon.gips.gipsl.gipsl.GipsConstant;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsContains;
import org.emoflon.gips.gipsl.gipsl.GipsContextExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsExpressionOperand;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureExpr;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureLit;
import org.emoflon.gips.gipsl.gipsl.GipsFeatureNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsGlobalContext;
import org.emoflon.gips.gipsl.gipsl.GipsGlobalObjective;
import org.emoflon.gips.gipsl.gipsl.GipsImplicationBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaAttributeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaExpression;
import org.emoflon.gips.gipsl.gipsl.GipsLambdaSelfExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsMappingCheckValue;
import org.emoflon.gips.gipsl.gipsl.GipsMappingContext;
import org.emoflon.gips.gipsl.gipsl.GipsMappingValue;
import org.emoflon.gips.gipsl.gipsl.GipsNodeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsNotBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipsObjectiveExpression;
import org.emoflon.gips.gipsl.gipsl.GipsOrBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsPatternContext;
import org.emoflon.gips.gipsl.gipsl.GipsProductArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsRelExpr;
import org.emoflon.gips.gipsl.gipsl.GipsSelect;
import org.emoflon.gips.gipsl.gipsl.GipsStreamArithmetic;
import org.emoflon.gips.gipsl.gipsl.GipsStreamBoolExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamExpr;
import org.emoflon.gips.gipsl.gipsl.GipsStreamNavigation;
import org.emoflon.gips.gipsl.gipsl.GipsStreamSet;
import org.emoflon.gips.gipsl.gipsl.GipsSumArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeAttributeExpr;
import org.emoflon.gips.gipsl.gipsl.GipsTypeCast;
import org.emoflon.gips.gipsl.gipsl.GipsTypeContext;
import org.emoflon.gips.gipsl.gipsl.GipsUnaryArithmeticExpr;
import org.emoflon.gips.gipsl.gipsl.GipsVariableOperationExpression;
import org.emoflon.gips.gipsl.gipsl.GipslFactory;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.ibex.common.slimgt.util.SlimGTModelUtil;
import org.emoflon.ibex.gt.gtl.gTL.SlimRule;
import org.emoflon.ibex.gt.gtl.util.GTLModelFlattener;

public class GipslModelFlattener extends GTLModelFlattener {

	final protected GipslFactory gipslFactory = GipslPackage.eINSTANCE.getGipslFactory();

	protected Map<String, GipsMapping> name2mapping = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, GipsObjective> name2objective = Collections.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, List<Consumer<GipsMapping>>> pendingMappingJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());
	protected Map<String, List<Consumer<GipsObjective>>> pendingObjectiveJobs = Collections
			.synchronizedMap(new LinkedHashMap<>());

//	public GipslModelFlattener(final EditorFile file, boolean loadCompletePackage) throws Exception {
//		this(new GipslResourceManager(), file, loadCompletePackage);
//	}
//
//	public GipslModelFlattener(final GipslResourceManager gtlManager, final EditorFile file,
//			boolean loadCompletePackage) throws Exception {
//		super(gtlManager, (loadCompletePackage) ? loadAllFilesInPkg(gtlManager, file) : List.of(file));
//	}
//
//	public GipslModelFlattener(final Collection<EditorFile> files) throws Exception {
//		this(new GipslResourceManager(), files);
//	}
//	
//	public static Collection<org.emoflon.ibex.gt.gtl.gTL.EditorFile> loadAllFilesInPkg(
//			final GipslResourceManager gtlManager, final EditorFile file) {
//		Collection<org.emoflon.ibex.gt.gtl.gTL.EditorFile> files = gtlManager.loadAllOtherEditorFilesInPackage(file);
//		files.add(file);
//		return files;
//	}

	public GipslModelFlattener(final GipslResourceManager gtlManager,
			final Collection<org.emoflon.ibex.gt.gtl.gTL.EditorFile> files) throws Exception {
		super(gtlManager, files);

		files.stream().filter(file -> (file instanceof EditorFile)).map(file -> (EditorFile) file)
				.forEach(file -> flattenGipslModel(file));

		name2mapping.forEach((name, mapping) -> {
			if (pendingMappingJobs.containsKey(name))
				pendingMappingJobs.get(name).forEach(consumer -> consumer.accept(mapping));
		});

		name2objective.forEach((name, objective) -> {
			if (pendingObjectiveJobs.containsKey(name))
				pendingObjectiveJobs.get(name).forEach(consumer -> consumer.accept(objective));
		});
	}

	@Override
	protected EditorFile createNewEditorFile() {
		EditorFile file = gipslFactory.createEditorFile();
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
		// Flatten constraints
		for (GipsConstraint constraint : file.getConstraints()) {
			GipsConstraint flattenedConstraint = flatten(constraint);
			flattenedFile.getConstraints().add(flattenedConstraint);
		}
		// Flatten Objectives
		for (GipsObjective objective : file.getObjectives()) {
			GipsObjective flattenedObjective = flatten(objective);
			flattenedFile.getObjectives().add(flattenedObjective);
		}
		// Flatten global objective, if defined
		if (file.getGlobalObjective() != null) {
			flattenedFile.setGlobalObjective(flatten(file.getGlobalObjective()));
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
		return fc;
	}

	protected GipsMapping flatten(final GipsMapping mapping) {
		GipsMapping flattenedMapping = gipslFactory.createGipsMapping();
		flattenedMapping.setName(mapping.getName());
		name2mapping.put(mapping.getName(), flattenedMapping);

		flattenedMapping.setPattern(name2rule.get(mapping.getPattern().getName()));
		return flattenedMapping;
	}

	protected GipsConstraint flatten(final GipsConstraint constraint) {
		GipsConstraint flattenedConstraint = gipslFactory.createGipsConstraint();
		EObject context = flattenContext(flattenedConstraint.getContext());
		flattenedConstraint.setContext(context);
		flattenedConstraint.setExpr(flatten(constraint.getExpr()));
		return flattenedConstraint;
	}

	protected GipsObjective flatten(final GipsObjective objective) {
		GipsObjective flattenedObjective = gipslFactory.createGipsObjective();
		flattenedObjective.setName(objective.getName());
		name2objective.put(flattenedObjective.getName(), flattenedObjective);

		EObject context = flattenContext(flattenedObjective.getContext());
		flattenedObjective.setContext(context);
		flattenedObjective.setExpr(flatten(objective.getExpr()));
		return flattenedObjective;
	}

	protected GipsGlobalObjective flatten(final GipsGlobalObjective go) {
		GipsGlobalObjective flattenedGO = gipslFactory.createGipsGlobalObjective();
		flattenedGO.setObjectiveGoal(go.getObjectiveGoal());
		if (go.getExpr() != null) {
			flattenedGO.setExpr(flatten(go.getExpr()));
		}
		return flattenedGO;
	}

	protected GipsBool flatten(final GipsBool expr) {
		GipsBool flattenedExpr = gipslFactory.createGipsBool();
		GipsBoolExpr flattenedBoolExpr = flatten(expr.getExpr());
		flattenedExpr.setExpr(flattenedBoolExpr);
		return flattenedExpr;
	}

	protected GipsBoolExpr flatten(final GipsBoolExpr expr) {
		if (expr instanceof GipsImplicationBoolExpr imp) {
			GipsImplicationBoolExpr flattenedImp = gipslFactory.createGipsImplicationBoolExpr();
			flattenedImp.setLeft(flatten(imp.getLeft()));
			flattenedImp.setRight(flatten(imp.getRight()));
			return flattenedImp;
		} else if (expr instanceof GipsOrBoolExpr or) {
			GipsOrBoolExpr flattenedOr = gipslFactory.createGipsOrBoolExpr();
			flattenedOr.setLeft(flatten(or.getLeft()));
			flattenedOr.setRight(flatten(or.getRight()));
			flattenedOr.setOperator(or.getOperator());
			return flattenedOr;
		} else if (expr instanceof GipsAndBoolExpr and) {
			GipsAndBoolExpr flattenedAnd = gipslFactory.createGipsAndBoolExpr();
			flattenedAnd.setLeft(flatten(and.getLeft()));
			flattenedAnd.setRight(flatten(and.getRight()));
			flattenedAnd.setOperator(and.getOperator());
			return flattenedAnd;
		} else if (expr instanceof GipsNotBoolExpr not) {
			GipsNotBoolExpr flattenedNot = gipslFactory.createGipsNotBoolExpr();
			flattenedNot.setOperand(flatten(not.getOperand()));
			return flattenedNot;
		} else if (expr instanceof GipsBracketBoolExpr brack) {
			GipsBracketBoolExpr flattenedBrack = gipslFactory.createGipsBracketBoolExpr();
			flattenedBrack.setOperand(flatten(brack.getOperand()));
			return flattenedBrack;
		} else if (expr instanceof GipsBooleanLiteral lit) {
			GipsBooleanLiteral flattenedLit = gipslFactory.createGipsBooleanLiteral();
			flattenedLit.setLiteral(lit.isLiteral());
			return flattenedLit;
		} else if (expr instanceof GipsRelExpr rel) {
			GipsRelExpr flattenedRel = gipslFactory.createGipsRelExpr();
			flattenedRel.setLeft(flatten(rel.getLeft()));
			if (rel.getRight() != null) {
				flattenedRel.setOperator(rel.getOperator());
				flattenedRel.setRight(rel.getRight());
			}
			return flattenedRel;
		} else {
			throw new UnsupportedOperationException("Unknown boolean expression type: " + expr);
		}
	}

	protected GipsArithmeticExpr flatten(final GipsArithmeticExpr expr) {
		if (expr instanceof GipsSumArithmeticExpr sum) {
			GipsSumArithmeticExpr flattenedSum = gipslFactory.createGipsSumArithmeticExpr();
			flattenedSum.setLeft(flatten(sum.getLeft()));
			flattenedSum.setRight(flatten(sum.getRight()));
			flattenedSum.setOperator(sum.getOperator());
			return flattenedSum;
		} else if (expr instanceof GipsProductArithmeticExpr prod) {
			GipsProductArithmeticExpr flattenedProd = gipslFactory.createGipsProductArithmeticExpr();
			flattenedProd.setLeft(flatten(prod.getLeft()));
			flattenedProd.setRight(flatten(prod.getRight()));
			flattenedProd.setOperator(prod.getOperator());
			return flattenedProd;
		} else if (expr instanceof GipsExpArithmeticExpr exp) {
			GipsExpArithmeticExpr flattenedExp = gipslFactory.createGipsExpArithmeticExpr();
			flattenedExp.setLeft(flatten(exp.getLeft()));
			flattenedExp.setRight(flatten(exp.getRight()));
			flattenedExp.setOperator(exp.getOperator());
			return flattenedExp;
		} else if (expr instanceof GipsUnaryArithmeticExpr unary) {
			GipsUnaryArithmeticExpr flattenedUnary = gipslFactory.createGipsUnaryArithmeticExpr();
			flattenedUnary.setOperand(flatten(unary.getOperand()));
			flattenedUnary.setOperator(unary.getOperator());
			return flattenedUnary;
		} else if (expr instanceof GipsBracketExpr brack) {
			GipsBracketExpr flattenedBrack = gipslFactory.createGipsBracketExpr();
			flattenedBrack.setOperand(flatten(brack.getOperand()));
			return flattenedBrack;
		} else if (expr instanceof GipsExpressionOperand op) {
			return flatten(op);
		} else {
			throw new UnsupportedOperationException("Unknown arithmetic expression type: " + expr);
		}
	}

	protected GipsExpressionOperand flatten(final GipsExpressionOperand expr) {
		if (expr instanceof GipsAttributeExpr atr) {
			return flatten(atr);
		} else if (expr instanceof GipsObjectiveExpression obj) {
			GipsObjectiveExpression flattenedObj = gipslFactory.createGipsObjectiveExpression();
			addPendingObjectiveConsumer(obj.getObjective().getName(),
					(objective) -> flattenedObj.setObjective(objective));
			return flattenedObj;
		} else if (expr instanceof GipsArithmeticLiteral lit) {
			GipsArithmeticLiteral flattenedLit = gipslFactory.createGipsArithmeticLiteral();
			flattenedLit.setValue(lit.getValue());
			return flattenedLit;
		} else if (expr instanceof GipsConstant cons) {
			GipsConstant flattenedCons = gipslFactory.createGipsConstant();
			flattenedCons.setValue(cons.getValue());
			return flattenedCons;
		} else {
			throw new UnsupportedOperationException("Unknown expression operand type: " + expr);
		}
	}

	protected GipsAttributeExpr flatten(final GipsAttributeExpr expr) {
		if (expr instanceof GipsMappingAttributeExpr mAtr) {
			GipsMappingAttributeExpr flattendMAtr = gipslFactory.createGipsMappingAttributeExpr();
			addPendingMappingConsumer(mAtr.getMapping().getName(), (mapping -> flattendMAtr.setMapping(mapping)));
			GipsStreamExpr flattenedStream = flatten(mAtr.getExpr());
			flattendMAtr.setExpr(flattenedStream);
			return flattendMAtr;
		} else if (expr instanceof GipsTypeAttributeExpr tAtr) {
			GipsTypeAttributeExpr flattenedTAtr = gipslFactory.createGipsTypeAttributeExpr();
			flattenedTAtr.setType(tAtr.getType());
			GipsStreamExpr flattenedStream = flatten(tAtr.getExpr());
			flattenedTAtr.setExpr(flattenedStream);
			return flattenedTAtr;
		} else if (expr instanceof GipsPatternAttributeExpr pAtr) {
			GipsPatternAttributeExpr flattenedPAtr = gipslFactory.createGipsPatternAttributeExpr();
			flattenedPAtr.setPattern(name2rule.get(pAtr.getPattern().getName()));
			GipsStreamExpr flattenedStream = flatten(pAtr.getExpr());
			flattenedPAtr.setExpr(flattenedStream);
			return flattenedPAtr;
		} else if (expr instanceof GipsContextExpr cont) {
			GipsContextExpr flattenedCont = gipslFactory.createGipsContextExpr();
			if (cont.getTypeCast() != null && cont.getTypeCast().getType() != null) {
				GipsTypeCast typeCast = gipslFactory.createGipsTypeCast();
				typeCast.setType(cont.getTypeCast().getType());
				flattenedCont.setTypeCast(typeCast);
			}
			if (cont.getExpr() == null)
				return flattenedCont;

			if (cont.getExpr() instanceof GipsNodeAttributeExpr nae) {
				flattenedCont.setExpr(flatten(nae));
			} else if (cont.getExpr() instanceof GipsVariableOperationExpression voe) {
				flattenedCont.setExpr(flatten(voe));
			} else if (cont.getExpr() instanceof GipsFeatureExpr fe) {
				flattenedCont.setExpr(flatten(fe));
			} else {
				throw new UnsupportedOperationException("Unknown expression type: " + cont.getExpr());
			}

			if (cont.getStream() == null)
				return flattenedCont;

			GipsStreamExpr flattenedStream = flatten(cont.getStream());
			flattenedCont.setStream(flattenedStream);
			return flattenedCont;
		} else if (expr instanceof GipsLambdaAttributeExpression lAtr) {
			GipsLambdaAttributeExpression flattenedLAtr = gipslFactory.createGipsLambdaAttributeExpression();
			flattenedLAtr.setVar(flatten(lAtr.getVar()));
			if (lAtr.getExpr() instanceof GipsNodeAttributeExpr nae) {
				flattenedLAtr.setExpr(flatten(nae));
			} else if (lAtr.getExpr() instanceof GipsVariableOperationExpression voe) {
				flattenedLAtr.setExpr(flatten(voe));
			} else if (lAtr.getExpr() instanceof GipsFeatureExpr fe) {
				flattenedLAtr.setExpr(flatten(fe));
			} else {
				throw new UnsupportedOperationException("Unknown expression type: " + lAtr.getExpr());
			}
			return flattenedLAtr;
		} else if (expr instanceof GipsLambdaSelfExpression lSelf) {
			GipsLambdaSelfExpression flattenedLSelf = gipslFactory.createGipsLambdaSelfExpression();
			flattenedLSelf.setVar(flatten(lSelf.getVar()));
			return flattenedLSelf;
		} else {
			throw new UnsupportedOperationException("Unknown attribute expression type: " + expr);
		}
	}

	protected GipsNodeAttributeExpr flatten(final GipsNodeAttributeExpr expr) {
		GipsNodeAttributeExpr flattenedExpr = gipslFactory.createGipsNodeAttributeExpr();
		SlimRule rule = SlimGTModelUtil.getContainer(expr.getNode(), SlimRule.class);
		flattenedExpr.setNode(rule2nodes.get(name2rule.get(rule.getName())).get(expr.getNode().getName()));
		if (expr.getTypeCast() != null && expr.getTypeCast().getType() != null) {
			GipsTypeCast typeCast = gipslFactory.createGipsTypeCast();
			typeCast.setType(expr.getTypeCast().getType());
			flattenedExpr.setTypeCast(typeCast);
		}
		if (expr.getExpr() == null)
			return flattenedExpr;

		expr.setExpr(flatten(expr.getExpr()));
		return flattenedExpr;
	}

	protected GipsVariableOperationExpression flatten(final GipsVariableOperationExpression expr) {
		if (expr instanceof GipsMappingValue) {
			return gipslFactory.createGipsMappingValue();
		}
		if (expr instanceof GipsMappingCheckValue mCheck) {
			GipsMappingCheckValue flattenedMCheck = gipslFactory.createGipsMappingCheckValue();
			if (mCheck.getCount() != null)
				flattenedMCheck.setCount(flatten(mCheck.getCount()));

			return gipslFactory.createGipsMappingValue();
		}
		throw new UnsupportedOperationException("Unknown variable operaion type: " + expr);
	}

	protected GipsFeatureExpr flatten(final GipsFeatureExpr expr) {
		if (expr instanceof GipsFeatureNavigation nav) {
			return flatten(nav);
		} else if (expr instanceof GipsFeatureLit lit) {
			return flatten(lit);
		} else {
			throw new UnsupportedOperationException("Unknown feature expression type: " + expr);
		}
	}

	protected GipsFeatureNavigation flatten(final GipsFeatureNavigation expr) {
		GipsFeatureNavigation flattenedExpr = gipslFactory.createGipsFeatureNavigation();
		flattenedExpr.setLeft(flatten(expr.getLeft()));
		flattenedExpr.setRight(flatten(expr.getRight()));
		return flattenedExpr;
	}

	protected GipsFeatureLit flatten(final GipsFeatureLit expr) {
		GipsFeatureLit flattenedExpr = gipslFactory.createGipsFeatureLit();
		if (expr.getTypeCast() != null && expr.getTypeCast().getType() != null) {
			GipsTypeCast typeCast = gipslFactory.createGipsTypeCast();
			typeCast.setType(expr.getTypeCast().getType());
			flattenedExpr.setTypeCast(typeCast);
		}
		flattenedExpr.setFeature(expr.getFeature());
		return flattenedExpr;
	}

	protected GipsLambdaExpression flatten(final GipsLambdaExpression expr) {
		GipsLambdaExpression flattenedExpr = gipslFactory.createGipsLambdaExpression();
		flattenedExpr.setName(expr.getName());
		flattenedExpr.setExpr(flatten(expr.getExpr()));
		return flattenedExpr;
	}

	protected GipsStreamExpr flatten(final GipsStreamExpr expr) {
		if (expr instanceof GipsStreamNavigation nav) {
			GipsStreamNavigation flattenedExpr = gipslFactory.createGipsStreamNavigation();
			flattenedExpr.setLeft(flatten(nav.getLeft()));
			flattenedExpr.setRight(flatten(nav.getRight()));
			return flattenedExpr;
		} else if (expr instanceof GipsSelect select) {
			GipsSelect flattenedExpr = gipslFactory.createGipsSelect();
			flattenedExpr.setType(select.getType());
			return flattenedExpr;
		} else if (expr instanceof GipsStreamSet set) {
			GipsStreamSet flattenedExpr = gipslFactory.createGipsStreamSet();
			flattenedExpr.setOperator(set.getOperator());
			flattenedExpr.setLambda(flatten(set.getLambda()));
			return flattenedExpr;
		} else if (expr instanceof GipsStreamArithmetic arithmetic) {
			GipsStreamArithmetic flattenedExpr = gipslFactory.createGipsStreamArithmetic();
			flattenedExpr.setOperator(arithmetic.getOperator());
			flattenedExpr.setLambda(flatten(arithmetic.getLambda()));
			return flattenedExpr;
		} else if (expr instanceof GipsContains contains) {
			GipsContains flattenedExpr = gipslFactory.createGipsContains();
			flattenedExpr.setExpr(flatten(contains.getExpr()));
			return flattenedExpr;
		} else if (expr instanceof GipsStreamBoolExpr bool) {
			GipsStreamBoolExpr flattenedExpr = gipslFactory.createGipsStreamBoolExpr();
			flattenedExpr.setOperator(bool.getOperator());
			return flattenedExpr;
		} else {
			throw new UnsupportedOperationException("Unknown stream expression type: " + expr);
		}
	}

	protected EObject flattenContext(final EObject context) {
		if (context instanceof GipsGlobalContext gc) {
			GipsGlobalContext flattenedGC = gipslFactory.createGipsGlobalContext();
			return flattenedGC;
		} else if (context instanceof GipsMappingContext mc) {
			GipsMappingContext flattenedMC = gipslFactory.createGipsMappingContext();
			addPendingMappingConsumer(mc.getMapping().getName(), (mapping) -> flattenedMC.setMapping(mapping));
			return flattenedMC;
		} else if (context instanceof GipsTypeContext tc) {
			GipsTypeContext flattenedTC = gipslFactory.createGipsTypeContext();
			flattenedTC.setType(tc.getType());
			return flattenedTC;
		} else if (context instanceof GipsPatternContext pc) {
			GipsPatternContext flattenedPC = gipslFactory.createGipsPatternContext();
			flattenedPC.setPattern(name2rule.get(pc.getPattern().getName()));
			return flattenedPC;
		} else {
			throw new IllegalArgumentException("Unknown context type: " + context);
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

	protected void addPendingObjectiveConsumer(final String objective, final Consumer<GipsObjective> consumer) {
		List<Consumer<GipsObjective>> consumers = pendingObjectiveJobs.get(objective);
		if (consumers == null) {
			consumers = Collections.synchronizedList(new LinkedList<>());
			pendingObjectiveJobs.put(objective, consumers);
		}
		consumers.add(consumer);
	}
}
