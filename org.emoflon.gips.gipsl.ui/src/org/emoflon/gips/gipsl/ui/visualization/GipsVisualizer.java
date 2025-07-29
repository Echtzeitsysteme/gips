package org.emoflon.gips.gipsl.ui.visualization;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipsConfig;
import org.emoflon.gips.gipsl.gipsl.GipsConstraint;
import org.emoflon.gips.gipsl.gipsl.GipsLinearFunction;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsObjective;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
import org.emoflon.ibex.gt.editor.gT.EditorPattern;
import org.emoflon.ibex.gt.editor.ui.visualization.GTPlantUMLGenerator;
import org.emoflon.ibex.gt.editor.ui.visualization.GTVisualizer;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * The GTVisualizer provides a PlantUML visualization of graph transformation
 * rules.
 */
public class GipsVisualizer extends GTVisualizer {

	@Inject
	@Named(Constants.LANGUAGE_NAME)
	private String languageName;

	@Inject
	private EObjectAtOffsetHelper offsetHelper;

	@Override
	public String getDiagramBody(final IEditorPart editor, final ISelection selection) {
		Optional<EditorGTFile> file = this.loadFileFromEditor(editor);
		if (!file.isPresent())
			return GipsPlantUMLGenerator.visualizeNothing();

//		if (true)
//			return visualizeSelection(selection, file.get().getPatterns());

		Collection<EObject> selectedEObjects = selectionToEObjects((XtextEditor) editor, (ITextSelection) selection);
		return visualizeSelection(selectedEObjects);
	}

	private Collection<EObject> selectionToEObjects(XtextEditor editor, ITextSelection selection) {
		if (selection.isEmpty())
			return Collections.emptyList();

		return editor.getDocument().readOnly(res -> {
			Collection<EObject> selectedObjects = new HashSet<>();

			int currentOffset = selection.getOffset();
			int endOffset = selection.getOffset() + selection.getLength();

			while (currentOffset <= endOffset) {
				EObject objectAtOffset = offsetHelper.resolveContainedElementAt(res, currentOffset);
				if (objectAtOffset == null)
					break;

				EObject objectOfInterest = getObjectOfInterest(objectAtOffset);
				if (objectOfInterest != null)
					selectedObjects.add(objectOfInterest);

				EObject objectForNextOffset = objectOfInterest != null ? objectOfInterest : objectAtOffset;
				ICompositeNode textNode = NodeModelUtils.findActualNodeFor(objectForNextOffset);
				int newOffset = textNode.getEndOffset() + 1;

				if (newOffset <= currentOffset)
					break; // This can happen if a cross-reference is resolved, taking us to a different
							// part of the document
				currentOffset = newOffset;
			}

			return selectedObjects;
		});
	}

	// Not all objects are suitable for a UML presentation. This method returns the
	// 'best' container of a given object or the object itself, if there is none
	private EObject getObjectOfInterest(EObject base) {
		if (base == null)
			return null;

		EObject element = base;
		do {
			switch (base) {
			case EditorPattern e:
				return base;
			case GipsConstraint e:
				return base;
			case GipsMapping e:
				return base;
			case GipsConfig e:
				return base;
			case GipsLinearFunction e:
				return base;
			case GipsObjective e:
				return base;
			default:
				break;
			}

			element = element.eContainer();
		} while (element != null);

		return base;
	}

	private String visualizeSelection(Collection<EObject> selectedEObjects) {
		if (selectedEObjects.isEmpty())
			return GipsPlantUMLGenerator.visualizeOverview();

		return GipsPlantUMLGenerator.visualizeCollection(selectedEObjects);
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
		if (selection instanceof ITextSelection textSelection) {
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
		return selection instanceof TextSelection;
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
