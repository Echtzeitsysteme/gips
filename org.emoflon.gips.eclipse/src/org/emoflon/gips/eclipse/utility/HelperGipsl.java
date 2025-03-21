package org.emoflon.gips.eclipse.utility;

import java.nio.file.Path;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsConfig;

public final class HelperGipsl {
	private HelperGipsl() {
	}

	public static IPath getLpPath(final XtextEditor editor) {
		return getLpPath(editor.getDocument(), editor.getResource());
	}

	public static IPath getLpPath(final IXtextDocument document) {
		return getLpPath(document, document.getAdapter(IResource.class));
	}

	public static IPath getLpPath(final IXtextDocument document, final IResource resource) {
		final var path = getLpOutputPath_(document);
		if (path == null) {
			return null;
		}

		final var ePath = IPath.fromPath(path);

		IPath targetPath;

		if (!ePath.isAbsolute()) {
			final var project = resource.getProject();
			if (project != null) {
				final var file = project.getFile(ePath);
				targetPath = file.getFullPath();
			} else {
				final var file = ResourcesPlugin.getWorkspace().getRoot().getFile(ePath);
				targetPath = file.getFullPath();
			}
		} else {
			targetPath = ePath;
		}

		return targetPath;
	}

	public static String removeQuotes(String in) {
		if (in == null || in.length() == 0) {
			return in;
		}

		if (in.length() == 1) {
			if (in.charAt(0) == '\"') {
				return "";
			} else {
				return in;
			}
		}

		if (in.charAt(0) == '\"') {
			if (in.charAt(in.length() - 1) == '\"') {
				return in.substring(1, in.length() - 1);
			} else {
				return in.substring(1);
			}
		} else if (in.charAt(in.length() - 1) == '\"') {
			return in.substring(0, in.length() - 1);
		}

		return in;
	}

	private static GipsConfig getConfig(IXtextDocument document) {
		return document.readOnly(resource -> {
			var editorFile = resource.getContents().stream().filter(EditorGTFile.class::isInstance)
					.map(e -> (EditorGTFile) e).findAny();
			if (editorFile.isPresent()) {
				return editorFile.get().getConfig();
			}
			return null;
		});
	}

	private static Path getLpOutputPath_(final IXtextDocument document) {
		var config = getConfig(document);
		var rawPath = config != null ? config.getPath() : null;
		rawPath = removeQuotes(rawPath);
		if ((rawPath == null) || rawPath.isBlank()) {
			return null;
		}
		return Path.of(rawPath).normalize();
	}

}
