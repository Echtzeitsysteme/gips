package org.emoflon.gips.eclipse.connector;

import static org.emoflon.gips.eclipse.api.ILPTraceKeywords.buildElementId;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.emoflon.gips.eclipse.CplexLpLanguage;
import org.emoflon.gips.eclipse.api.ILPTraceKeywords;
import org.emoflon.gips.eclipse.connector.highlight.CplexAnnotationHighlightStrategy;
import org.emoflon.gips.eclipse.cplexLp.ConstraintExpression;
import org.emoflon.gips.eclipse.cplexLp.LinearTerm;
import org.emoflon.gips.eclipse.cplexLp.NumberLiteral;
import org.emoflon.gips.eclipse.cplexLp.ObjectiveExpression;
import org.emoflon.gips.eclipse.cplexLp.SectionObjective;
import org.emoflon.gips.eclipse.cplexLp.Variable;
import org.emoflon.gips.eclipse.utility.HelperEObjects;
import org.emoflon.gips.eclipse.utility.HelperEcoreSelection;

public class CplexLpEditorTraceConnectionFactory extends XtextEditorTraceConnectionFactory {

	public CplexLpEditorTraceConnectionFactory() {
		super(CplexLpLanguage.LANGUAGE_NAME);
	}

	@Override
	protected CplexEditorTraceConnection createConnection(XtextEditor editor) {
		return new CplexEditorTraceConnection(editor);
	}

	private static final class CplexEditorTraceConnection extends XtextEditorTraceConnection {

		public CplexEditorTraceConnection(XtextEditor editor) {
			super(editor, new CplexAnnotationHighlightStrategy());
		}

		@Override
		protected Collection<String> transformSelectionToElementIds(ISelection selection) {
			var eObjects = HelperEcoreSelection.selectionToEObjects(editor, selection);
			var elementIds = new ArrayList<String>(eObjects.size());
			for (var eObject : eObjects) {
				if (eObject instanceof NumberLiteral) {
					if (eObject.eContainer() instanceof LinearTerm linearTerm) {
						if (linearTerm.getVariable() != null)
							eObject = linearTerm.getVariable();
					} else {
						eObject = eObject.eContainer();
					}
				}

				if (eObject instanceof Variable variable) {
					String variableName = variable.getRef().getName();
					EObject parent = HelperEObjects.getParentOfType(variable, ConstraintExpression.class,
							ObjectiveExpression.class);

					if (variableName == null) {
						continue;
					}

					if (parent instanceof ConstraintExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, variableName));
					} else if (parent instanceof ObjectiveExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_FUNCTION_VAR, variableName));
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE, ""));
					}

					elementIds.add(buildElementId(ILPTraceKeywords.TYPE_VARIABLE, variableName));

				} else if (eObject instanceof ConstraintExpression constraint) {
					String name = constraint.getName();
					if (name == null)
						continue;

					var delimiter = name.lastIndexOf("_");
					if (delimiter >= 0) {
						name = name.substring(0, delimiter);
					}
					name = buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT, name);
					elementIds.add(name);

				} else if (eObject instanceof SectionObjective) {
					elementIds.add(buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE, ""));
					continue;
				}
			}
			return elementIds;
		}

	}

}
