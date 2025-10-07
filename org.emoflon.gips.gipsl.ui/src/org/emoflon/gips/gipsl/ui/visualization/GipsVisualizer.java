package org.emoflon.gips.gipsl.ui.visualization;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.Constants;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.gipsl.gipsl.EditorGTFile;
import org.emoflon.gips.gipsl.gipsl.GipslPackage;
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

	@Inject
	private GipslPlantUMLProvider umlGenerator;

	public GipsVisualizer() {

	}

	/**
	 * Returns the visualization of the selection.
	 *
	 * @param editor    the editor
	 * @param selection the selection
	 * @return the PlantUML code for the visualization
	 */
	@Override
	public String getDiagramBody(final IEditorPart editor, final ISelection selection) {
		Optional<EditorGTFile> file = this.loadFileFromEditor(editor);
		if (!file.isPresent())
			return umlGenerator.visualizeNothing();

		Collection<EObject> selectedEObjects = selectionToEObjects((XtextEditor) editor, (ITextSelection) selection);
		if (selectedEObjects.isEmpty())
			return umlGenerator.visualizeNothing();

		return umlGenerator.visualizeCollection(selectedEObjects);
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
				// textNode either surrounds the current offset or is the element closest to it.
				int newOffset = textNode.getEndOffset() + 1;

				if (newOffset <= currentOffset) {
					// the new offset didn't move forward
					// This can happen if a cross-reference takes us to a different location within
					// the document, or if we have reached the last element.
					break;
				}

				currentOffset = newOffset;
			}

			return selectedObjects;
		});
	}

	/**
	 * Not all objects are suitable for presentation in UML. This method returns the
	 * 'object of interest', which can be either the input object itself or one of
	 * its parents.
	 * 
	 * @param base
	 * @return an object of interest or null
	 */
	private EObject getObjectOfInterest(EObject base) {
		if (base == null)
			return null;

		EObject element = base;
		do {
			if (umlGenerator.hasUMLRepresentation(element))
				return element;
			element = element.eContainer();
		} while (element != null);

		return null;
	}

	@Override
	public boolean supportsEditor(final IEditorPart editor) {
		return this.loadFileFromEditor(editor).isPresent();
	}

	@Override
	public boolean supportsSelection(ISelection selection) {
		return selection instanceof ITextSelection;
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
