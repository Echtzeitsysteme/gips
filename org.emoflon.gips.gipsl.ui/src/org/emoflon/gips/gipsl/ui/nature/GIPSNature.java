package org.emoflon.gips.gipsl.ui.nature;

import java.util.ArrayList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;

public class GIPSNature implements IProjectNature {

	public static final String NATURE_ID = "org.emoflon.gips.gipsl.ui.gipsNature";

	private IProject project;

	@Override
	public void configure() throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		String[] newNatures;
		int idx = -1;
		for (int i = 0; i < natures.length; i++) {
			if (natures[i].equals(NATURE_ID)) {
				idx = i;
				break;
			}
		}

		if (idx >= 0) {
			natures[idx] = natures[0];
			natures[0] = NATURE_ID;
			newNatures = new String[natures.length];
			System.arraycopy(natures, 0, newNatures, 0, natures.length);
		} else {
			newNatures = new String[natures.length + 1];
			System.arraycopy(natures, 0, newNatures, 1, natures.length);
			newNatures[0] = NATURE_ID;
		}

		// validate the natures
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus status = workspace.validateNatureSet(newNatures);

		// only apply new nature, if the status is ok
		if (status.getCode() == IStatus.OK) {
			description.setNatureIds(newNatures);
			project.setDescription(description, null);
		}

	}

	@Override
	public void deconfigure() throws CoreException {
		IProjectDescription description = project.getDescription();
		String[] natures = description.getNatureIds();
		ArrayList<String> newNatures = new ArrayList<>();
		for (String nature : natures) {
			if (nature.equals(NATURE_ID))
				continue;

			newNatures.add(nature);
		}

		String[] natures2 = new String[newNatures.size()];
		newNatures.toArray(natures2);

		// validate the natures
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IStatus status = workspace.validateNatureSet(natures2);

		// only apply new nature, if the status is ok
		if (status.getCode() == IStatus.OK) {
			description.setNatureIds(natures2);
			project.setDescription(description, null);
		}
	}

	@Override
	public IProject getProject() {
		return project;
	}

	@Override
	public void setProject(IProject project) {
		this.project = project;
	}

}
