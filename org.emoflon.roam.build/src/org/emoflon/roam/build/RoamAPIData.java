package org.emoflon.roam.build;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

public class RoamAPIData {

	public final static String HIPE_ENGINE_NAME = "HiPE";

	public IProject project;

	public final IFolder apiPackageFolder;
	public final String apiPkg;
	public final String apiPkgPath;

	public String matchesPkg;
	public String matchesPkgPath;
	public String rulesPkg;
	public String rulesPkgPath;
	public String probabilitiesPkg;
	public String probabilitiesPkgPath;

	public String apiClassNamePrefix;
	public String apiClass;
	public String appClass;
	public final Map<String, String> engineAppClasses = new HashMap<>();

	public URI intermediateModelURI;
	public String roamApiPkg;
	public String roamApiPkgPath;
	public String roamMappingPkg;
	public String roamMappingPkgPath;
	public String roamMapperPkg;
	public String roamMapperPkgPath;
	public String roamConstraintPkg;
	public String roamConstraintPkgPath;
	public String roamObjectivePkg;
	public String roamObjectivePkgPath;

	public RoamAPIData(final IFolder apiPackageFolder) {
		this.apiPackageFolder = apiPackageFolder;
		apiPkg = apiPackageFolder.getProjectRelativePath().toPortableString().replace("/", ".").replace("src-gen.", "");
		apiPkgPath = apiPackageFolder.getProjectRelativePath().toPortableString();
	}

	public void setMatchesPkg(final IFolder matchesFolder) {
		matchesPkgPath = matchesFolder.getProjectRelativePath().toPortableString();
		matchesPkg = matchesPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setRulesPkg(final IFolder rulesFolder) {
		rulesPkgPath = rulesFolder.getProjectRelativePath().toPortableString();
		rulesPkg = rulesPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setProbabilitiesPkg(final IFolder probabilitiesFolder) {
		probabilitiesPkgPath = probabilitiesFolder.getProjectRelativePath().toPortableString();
		probabilitiesPkg = probabilitiesPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setRoamApiPackage(final IFolder roamApiFolder) {
		roamApiPkgPath = roamApiFolder.getProjectRelativePath().toPortableString();
		roamApiPkg = roamApiPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setRoamMappingPackage(final IFolder roamMappingFolder) {
		roamMappingPkgPath = roamMappingFolder.getProjectRelativePath().toPortableString();
		roamMappingPkg = roamMappingPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setRoamMapperPackage(final IFolder roamMapperFolder) {
		roamMapperPkgPath = roamMapperFolder.getProjectRelativePath().toPortableString();
		roamMapperPkg = roamMapperPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setRoamConstraintPackage(final IFolder roamConstraintFolder) {
		roamConstraintPkgPath = roamConstraintFolder.getProjectRelativePath().toPortableString();
		roamConstraintPkg = roamConstraintPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setRoamObjectivePackage(final IFolder roamObjectiveFolder) {
		roamObjectivePkgPath = roamObjectiveFolder.getProjectRelativePath().toPortableString();
		roamObjectivePkg = roamObjectivePkgPath.replace("/", ".").replace("src-gen.", "");
	}
}
