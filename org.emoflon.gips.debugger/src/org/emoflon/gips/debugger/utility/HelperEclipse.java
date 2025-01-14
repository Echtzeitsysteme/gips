package org.emoflon.gips.debugger.utility;

import java.util.concurrent.Callable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.emf.common.CommonPlugin;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.ui.editor.XtextEditor;

public final class HelperEclipse {
	private HelperEclipse() {

	}

	public static IEclipseContext getActiveContext() {
		final IEclipseContext context = getWorkbenchContext();
		return context == null ? null : context.getActiveLeaf();
	}

	public static IEclipseContext getWorkbenchContext() {
		return PlatformUI.getWorkbench().getService(IEclipseContext.class);
	}

	public static IWorkspace getWorkspace() {
		return ResourcesPlugin.getWorkspace();
	}

	public static org.eclipse.emf.common.util.URI toPlatformURI(org.eclipse.emf.common.util.URI uri) {
		if (uri.isPlatform()) {
			return uri;
		}

		var path = IPath.fromOSString(uri.toFileString());
		var project = tryAndGetProject(path);
		if (project == null) {
			throw new IllegalArgumentException("Unable to locate project for URI: " + uri);
		}

		var relativePath = path.makeRelativeTo(project.getLocation());
		var newUri = URI.createPlatformResourceURI("/" + project.getName() + "/" + relativePath.toString(), true);

		if (uri.hasFragment()) {
			newUri.appendFragment(uri.fragment());
		}
		if (uri.hasQuery()) {
			newUri.appendQuery(uri.query());
		}

		return newUri;
	}

	public static org.eclipse.emf.common.util.URI toFileURI(org.eclipse.emf.common.util.URI uri) {
		if (uri.isFile()) {
			return uri;
		}

		var newUri = CommonPlugin.resolve(uri);
		if (uri.hasFragment()) {
			newUri.appendFragment(uri.fragment());
		}
		if (uri.hasQuery()) {
			newUri.appendQuery(uri.query());
		}

		return newUri;
	}

	public static boolean isValidPlatformURI(org.eclipse.emf.common.util.URI uri) {
		if (!uri.isPlatform()) {
			return false;
		}

		var projectName = uri.segment(1);
		for (var project : getWorkspace().getRoot().getProjects()) {
			if (project.getName().equals(projectName)) {
				return true;
			}
		}
		return false;
	}

	public static IProject tryAndGetProject(org.eclipse.emf.ecore.resource.Resource resource) {
		var uri = resource.getURI();
		if (uri == null) {
			throw new IllegalArgumentException("Resource has no URI");
		}
		return tryAndGetProject(uri);
	}

	public static IProject tryAndGetProject(org.eclipse.emf.common.util.URI uri) {
		if (uri.isPlatformResource()) {
			var expectedProjectName = uri.segment(1);
			for (var project : getWorkspace().getRoot().getProjects()) {
				if (project.getName().equalsIgnoreCase(expectedProjectName)) {
					return project;
				}
			}
			return null;
		}

		var path = IPath.fromOSString(uri.toFileString());
		return tryAndGetProject(path);
	}

	public static IProject tryAndGetProject(org.eclipse.core.resources.IResource resource) {
		return resource.getProject();
	}

	public static IProject tryAndGetProject(org.eclipse.core.resources.IFile file) {
		return file.getProject();
	}

	public static IProject tryAndGetProject(org.eclipse.core.resources.IFolder folder) {
		return folder.getProject();
	}

	public static IProject tryAndGetProject(org.eclipse.core.runtime.IPath path) {
		if (path.isAbsolute()) {
			for (var project : getWorkspace().getRoot().getProjects()) {
				if (project.getLocation().isPrefixOf(path)) {
					return project;
				}
			}

			return null;
		}

		// search for partial project location match at the start of path
//		for (var project : getWorkspace().getRoot().getProjects()) {
//			var projectLocation = project.getLocation();
//
//			var upperSearchLimit = Math.min(path.segmentCount(), projectLocation.segmentCount());
//			for (var i = 0; i < upperSearchLimit; ++i) {
//				if (projectLocation.lastSegment().equals(path.segment(i))) {
//
//					var counter = i - 1;
//					for (; counter >= 0; --counter) {
//						if (!projectLocation.segment(counter).equals(path.segment(counter))) {
//							break;
//						}
//					}
//
//					if (counter < 0) {
//						return project;
//					}
//				}
//			}
//		}

		for (var project : getWorkspace().getRoot().getProjects()) {
			if (project.getLocation().lastSegment().equals(path.segment(0))) {
				return project;
			}
		}

		return null;
	}

	@Deprecated
	public static IProject tryAndGetProject(org.eclipse.ui.IEditorInput input) {
		IProject project = input.getAdapter(IProject.class);
		if (project != null) {
			return project;
		}

		{
			var resource = input.getAdapter(IResource.class);
			if (resource != null) {
				project = tryAndGetProject(resource);
				if (project != null) {
					return project;
				}
			}
		}

		{
			var file = input.getAdapter(IFile.class);
			if (file != null) {
				project = tryAndGetProject(file);
				if (project != null) {
					return project;
				}
			}
		}

		if (input instanceof URIEditorInput uriInput) {
			project = tryAndGetProject(uriInput.getURI());
			if (project != null) {
				return project;
			}
		}

		return null;
	}

	@Deprecated
	public static IProject getProjectOfResource(final IWorkspace workspace,
			final org.eclipse.emf.ecore.resource.Resource resource) {

		if (resource.getURI().segmentCount() < 2) {
			return null;
		}

		var expectedProjectName = resource.getURI().segment(1);
		for (var project : workspace.getRoot().getProjects()) {
			if (project.getName().equalsIgnoreCase(expectedProjectName)) {
				return project;
			}
		}

		return null;
	}

	@Deprecated
	public static IProject tryAndFindProject(final IEditorInput input) {
		IProject project = input.getAdapter(IProject.class);
		if (project != null) {
			return project;
		}

		{
			var resource = input.getAdapter(IResource.class);
			if (resource != null) {
				project = resource.getProject();
			}
			if (project != null) {
				return project;
			}
		}

		{
			var file = input.getAdapter(IFile.class);
			if (file != null) {
				project = file.getProject();
			}
			if (project != null) {
				return project;
			}
		}

		{
			if (input instanceof URIEditorInput uriInput) {
				project = tryAndFindProject(uriInput.getURI());
				if (project != null) {
					return project;
				}
			}
		}

		return null;
	}

	@Deprecated
	public static IProject tryAndFindProject(final URI uri) {
		if (uri.isPlatformResource()) {
			var expectedProjectName = uri.segment(1);
			for (var project : getWorkspace().getRoot().getProjects()) {
				if (project.getName().equalsIgnoreCase(expectedProjectName)) {
					return project;
				}
			}

			return null;
		}

		var filePath = IPath.fromOSString(uri.toFileString());
		for (var project : getWorkspace().getRoot().getProjects()) {
			var matches = project.getLocation().matchingFirstSegments(filePath);
			if (matches == project.getLocation().segmentCount()) {
				return project;
			}
		}

		return null;
	}

	@Deprecated
	public static XtextEditor findActiveXtextEditor(final IFile file) {
		var activeEditors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();

		for (var activeEditor : activeEditors) {
			var part = activeEditor.getPart(false);
			if (!(part instanceof XtextEditor eEditor)) {
				continue;
			}

			var input = eEditor.getEditorInput();
			if (input instanceof FileEditorInput fileInput) {
				if (file.equals(fileInput.getFile())) {
					return eEditor;
				}
			}
		}

		return null;
	}

	public static <T> T runInUIThread(final Callable<T> call) throws Exception {
		var result = PlatformUI.getWorkbench().getDisplay().syncCall(() -> call.call());
		return result;
	}

	@Deprecated
	public static EcoreEditor findActiveEcoreEditor(final URI uri) {
		var activeEditors = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getEditorReferences();

		if (uri.isPlatform()) {
			var relativePath = new Path(uri.toPlatformString(true)).makeRelative();

			for (var activeEditor : activeEditors) {
				var part = activeEditor.getPart(false);
				if (!(part instanceof EcoreEditor eEditor)) {
					continue;
				}

				var input = eEditor.getEditorInput();

				var resource = input.getAdapter(IResource.class);
				if (resource != null) {
					var editorFile = resource.getProjectRelativePath();
					if (editorFile.segmentCount() == relativePath.segmentCount()) {
						if (editorFile.matchingFirstSegments(editorFile) == editorFile.segmentCount()) {
							return eEditor;
						}
					}
				}
			}
		} else if (uri.isFile()) {
			var editorInput = new URIEditorInput(uri);

			for (var activeEditor : activeEditors) {
				var part = activeEditor.getPart(false);
				if (!(part instanceof EcoreEditor eEditor)) {
					continue;
				}

				var input = eEditor.getEditorInput();
				if (editorInput.equals(input)) {
					return eEditor;
				}
			}
		}

		return null;
	}
}
