package org.emoflon.gips.build.generator;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.emoflon.gips.intermediate.GipsIntermediate.Variable;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXNode;
import org.emoflon.ibex.patternmodel.IBeXPatternModel.IBeXParameter;
import org.moflon.core.utilities.EcoreUtils;

public class GipsImportManager {

	/**
	 * The mapping eClassifier name to the name of the package containing the
	 * meta-model code.
	 */
	private Map<String, String> eClassifierNameToPath = new HashMap<>();

	/**
	 * The mapping name of the meta-model Java Package to the name of the package
	 * containing it.
	 */
	private Map<String, String> packageNameToPath = new HashMap<>();

	/**
	 * The mappings of URIs to package paths set via the properties file.
	 */
	private final Map<String, String> mappings;

	/**
	 * Creates a new EClassifiersManager.
	 *
	 * @param mappings the mappings of URIs to package paths set via the properties
	 *                 file
	 */
	public GipsImportManager(final Map<String, String> mappings) {
		this.mappings = mappings;
	}

	/**
	 * Loads the EClasses from the given meta-model resource.
	 *
	 * @param ecoreFile the resource
	 */
	public void loadMetaModelClasses(final Resource ecoreFile) {
		EObject rootElement = ecoreFile.getContents().get(0);
		if (rootElement instanceof EPackage) {
			loadMetaModelClasses((EPackage) rootElement);
		}
	}

	/**
	 * Loads the EClasses from the given package.
	 *
	 * @param ePackage the package
	 */
	private void loadMetaModelClasses(final EPackage ePackage) {
		boolean isEcore = "ecore".equals(ePackage.getName());
		String name = isEcore ? "org.eclipse.emf.ecore" : getPackagePath(ePackage);
		ePackage.getEClassifiers().stream() //
				.filter(c -> !isEcore || c instanceof EClass) //
				.forEach(c -> eClassifierNameToPath.put(c.getName(), name));
		if (!isEcore) {
			addPackage(ePackage);
		}

		ePackage.getESubpackages().forEach(p -> loadMetaModelClasses(p));
	}

	/**
	 * Returns the import path for the Java package containing the code for the
	 * given Ecore package.
	 *
	 * @param ePackage the package
	 * @return the import path for package
	 */
	private String getPackagePath(final EPackage ePackage) {
		String uriString = ePackage.eResource().getURI().toString();
		if (mappings.containsKey(uriString)) {
			return mappings.get(uriString);
		} else {
			return EcoreUtils.getFQNIfPossible(ePackage).orElse(ePackage.getName());
		}
	}

	/**
	 * Adds the EPackage to the meta-models.
	 *
	 * @param ePackage the package to add
	 */
	private void addPackage(final EPackage ePackage) {
		String packageClassName = getPackageClassName(ePackage.getName());
		String packageImport = getPackagePath(ePackage) + "." + packageClassName;
		packageImport = correctPackageImportWithMapping(packageImport);
		packageNameToPath.put(packageClassName, packageImport);
	}

	/**
	 * Give the user the chance to correct any package mapping with values in the
	 * property file.
	 *
	 * @param packageImport initial import
	 * @return fixed import
	 */
	private String correctPackageImportWithMapping(String packageImport) {
		if (mappings.containsKey(packageImport))
			return mappings.get(packageImport);
		return packageImport;
	}

	/**
	 * Return the name of the Package class.
	 *
	 * @param modelName the package of the meta-model
	 */
	private static String getPackageClassName(String modelName) {
		return Character.toUpperCase(modelName.charAt(0)) + modelName.substring(1) + "Package";
	}

	/**
	 * Determines the set of necessary imports for the given EClassifiers.
	 *
	 * @param types the EClassifiers to import
	 * @return the types for Java import statements
	 */
	public Set<String> getImportsForTypes(final Set<? extends EClassifier> types) {
		Set<String> imports = new TreeSet<>();
		types.stream().distinct().forEach(eClassifier -> {
			String typePackageName = eClassifierNameToPath.get(eClassifier.getName());
			if (typePackageName != null) {
				imports.add(typePackageName + '.' + eClassifier.getName());
			}
		});
		return imports;
	}

	/**
	 * Determines the set of necessary imports for the given EClassifiers.
	 *
	 * @param types the EClassifiers to import
	 * @return the types for Java import statements
	 */
	public String getImportsForType(final EClassifier type) {
		String imp = "";
		String typePackageName = eClassifierNameToPath.get(type.getName());
		if (typePackageName != null) {
			imp = typePackageName + '.' + type.getName();
		}
		return imp;
	}

	/**
	 * Determines the set of necessary type imports for a set of nodes.
	 *
	 * @param nodes the nodes
	 * @return the types for Java import statements
	 */
	public Set<String> getImportsForNodeTypes(final Collection<IBeXNode> nodes) {
		return getImportsForTypes(nodes.stream().map(n -> n.getType()).collect(Collectors.toSet()));
	}

	/**
	 * Determines the set of necessary type imports for the parameters.
	 *
	 * @param parameters the parameters
	 * @return the types for Java import statements
	 */
	public Set<String> getImportsForDataTypes(final List<IBeXParameter> parameters) {
		return getImportsForTypes(parameters.stream().map(p -> p.getType()).collect(Collectors.toSet()));
	}

	/**
	 * Returns the names of the meta-model packages.
	 *
	 * @return names of the meta-model packages
	 */
	public Set<String> getPackages() {
		return packageNameToPath.keySet();
	}

	/**
	 * Determines the set of necessary imports for the meta-models packages.
	 *
	 * @return the types for Java import statements
	 */
	public Set<String> getImportsForPackages() {
		return new HashSet<>(packageNameToPath.values());
	}

	public String getPackage(final EPackage pkg) {
		return packageNameToPath.get(pkg.getName());
	}

	public static String variableToJavaDataType(final Variable var, final Collection<String> imports) {
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
			throw new UnsupportedOperationException("Unsupported (M)ILP variable data type : " + var.getType());

		}
	}

	public static String variableToSimpleJavaDataType(final Variable var, final Collection<String> imports) {
		switch (var.getType()) {
		case BINARY:
			return "boolean";
		case INTEGER:
			return "int";
		case REAL:
			return "double";
		default:
			throw new UnsupportedOperationException("Unsupported (M)ILP variable data type : " + var.getType());

		}
	}

	public static String variableToJavaDefaultValue(final Variable var) {
		switch (var.getType()) {
		case BINARY:
			return "false";
		case INTEGER:
			return "0";
		case REAL:
			return "0.0";
		default:
			throw new UnsupportedOperationException("Unsupported (M)ILP variable data type : " + var.getType());

		}
	}

	public static String parameterToJavaDefaultValue(final IBeXParameter par) {
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
