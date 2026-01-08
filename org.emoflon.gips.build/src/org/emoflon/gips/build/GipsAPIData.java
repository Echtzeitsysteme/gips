package org.emoflon.gips.build;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.emf.common.util.URI;

public class GipsAPIData {

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
	public String gipsApiPkg;
	public String gipsApiPkgPath;
	public String gipsMappingPkg;
	public String gipsMappingPkgPath;
	public String gipsMapperPkg;
	public String gipsMapperPkgPath;
	public String gipsConstraintPkg;
	public String gipsConstraintPkgPath;
	public String gipsObjectivePkg;
	public String gipsObjectivePkgPath;
	public String gipsTypeExtensionPkg;
	public String gipsTypeExtensionPkgPath;

	public GipsAPIData(final IFolder apiPackageFolder) {
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

	public void setGipsApiPackage(final IFolder gipsApiFolder) {
		gipsApiPkgPath = gipsApiFolder.getProjectRelativePath().toPortableString();
		gipsApiPkg = gipsApiPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setGipsMappingPackage(final IFolder gipsMappingFolder) {
		gipsMappingPkgPath = gipsMappingFolder.getProjectRelativePath().toPortableString();
		gipsMappingPkg = gipsMappingPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setGipsMapperPackage(final IFolder gipsMapperFolder) {
		gipsMapperPkgPath = gipsMapperFolder.getProjectRelativePath().toPortableString();
		gipsMapperPkg = gipsMapperPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setGipsConstraintPackage(final IFolder gipsConstraintFolder) {
		gipsConstraintPkgPath = gipsConstraintFolder.getProjectRelativePath().toPortableString();
		gipsConstraintPkg = gipsConstraintPkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setGipsObjectivePackage(final IFolder gipsObjectiveFolder) {
		gipsObjectivePkgPath = gipsObjectiveFolder.getProjectRelativePath().toPortableString();
		gipsObjectivePkg = gipsObjectivePkgPath.replace("/", ".").replace("src-gen.", "");
	}

	public void setGipsTypeExtensionPkg(final IFolder gipsTypeExtensionFolder) {
		gipsTypeExtensionPkgPath = gipsTypeExtensionFolder.getProjectRelativePath().toPortableString();
		gipsTypeExtensionPkg = gipsTypeExtensionPkgPath.replace("/", ".").replace("src-gen.", "");
	}
}
