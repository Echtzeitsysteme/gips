package org.emoflon.gips.gipsl.ui.visualization;

import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.ui.visualization.GTPlantUMLGenerator;
import org.emoflon.ibex.gt.editor.ui.visualization.GTVisualizer;
import org.moflon.core.ui.visualisation.EMoflonPlantUMLGenerator;

/**
 * The GTVisualizer provides a PlantUML visualization of graph transformation
 * rules.
 */
public class GipsVisualizer extends GTVisualizer {

	@Override
	public String getDiagramBody(final IEditorPart editor, final ISelection selection) {
		Optional<EditorGTFile> file = this.loadFileFromEditor(editor);
		if (!file.isPresent()) {
			return EMoflonPlantUMLGenerator.emptyDiagram();
		}
		return visualizeSelection(selection, file.get().getPatterns());
	}

	/**
	 * Returns the visualization of the selection.
	 *
	 * @param selection the selection
	 * @param patterns  the editor patterns
	 * @return the PlantUML code for the visualization
	 */
	private static String visualizeSelection(final ISelection selection, final EList<EditorPattern> patterns) {
		if (patterns.size() == 0) {
			return GTPlantUMLGenerator.visualizeNothing();
		}
		if (patterns.size() == 1) {
			try {
				return GTPlantUMLGenerator.visualizeSelectedPattern(patterns.get(0));
			} catch (Exception e) {
				return GTPlantUMLGenerator.visualizeNothing();
			}
		}
		Optional<EditorPattern> pattern = determineSelectedRule(selection, patterns);
		if (pattern.isPresent()) {
			try {
				return GTPlantUMLGenerator.visualizeSelectedPattern(pattern.get());
			} catch (Exception e) {
				return GTPlantUMLGenerator.visualizeNothing();
			}
		}

		try {
			return GTPlantUMLGenerator.visualizePatternHierarchy(patterns);
		} catch (Exception e) {
			return GTPlantUMLGenerator.visualizeNothing();
		}

	}

	/**
	 * Checks whether there is a rule with the name being equal to the current
	 * selected text.
	 *
	 * @param selection the current selection
	 * @param patterns  the patters
	 * @return an {@link Optional} for a {@link EditorPattern}
	 */
	private static Optional<EditorPattern> determineSelectedRule(final ISelection selection,
			final EList<EditorPattern> patterns) {
		if (selection instanceof TextSelection) {
			TextSelection textSelection = (TextSelection) selection;
			// For the TextSelection documents start with line 0.
			int selectionStart = textSelection.getStartLine() + 1;
			int selectionEnd = textSelection.getEndLine() + 1;

			for (final EditorPattern pattern : patterns) {
				ICompositeNode object = NodeModelUtils.getNode(pattern);
				if (selectionStart >= object.getStartLine() && selectionEnd <= object.getEndLine()) {
					return Optional.of(pattern);
				}
			}
		}
		return Optional.empty();
	}

	@Override
	public boolean supportsEditor(final IEditorPart editor) {
		return this.loadFileFromEditor(editor).isPresent();
	}

	@Override
	public boolean supportsSelection(ISelection selection) {
		// Note: If the editor is detected correctly, this must be true anyways!
		return true;
	}

	/**
	 * Loads the file from the given editor.
	 *
	 * @param editor the editor
	 * @return an {@link Optional} for the {@link GraphTransformationFile}
	 */
	private Optional<EditorGTFile> loadFileFromEditor(final IEditorPart editor) {
		try {
			return Optional.of(editor) //
					.flatMap(maybeCast(XtextEditor.class))
					.map(e -> e.getDocument().readOnly(res -> res.getContents().get(0)))
					.filter(e -> e.eClass() == GipslPackage.Literals.EDITOR_GT_FILE)
					.flatMap(maybeCast(EditorGTFile.class));
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
