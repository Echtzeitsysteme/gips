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
import org.emoflon.gips.eclipse.cplexLp.VariableDecleration;
import org.emoflon.gips.eclipse.cplexLp.VariableRef;
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
					String variableName = null;
					EObject parent = null;

					if (variable instanceof VariableDecleration variableDecleration) {
						variableName = variableDecleration.getName();
					}

					if (variable instanceof VariableRef variableRef) {
						variableName = variableRef.getRef().getName();
						parent = HelperEObjects.getParentOfType(variableRef, ConstraintExpression.class,
								ObjectiveExpression.class);
					}

					if (variableName == null) {
						continue;
					}

					if (parent instanceof ConstraintExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_CONSTRAINT_VAR, variableName));
					} else if (parent instanceof ObjectiveExpression) {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_FUNCTION_VAR, variableName));
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_OBJECTIVE, ""));
					} else {
						elementIds.add(buildElementId(ILPTraceKeywords.TYPE_VARIABLE, variableName));
					}

					var delimiter = variableName.lastIndexOf("#");
					var shortVariableName = delimiter < 0 ? variableName : variableName.substring(0, delimiter);
					elementIds.add(buildElementId(ILPTraceKeywords.TYPE_MAPPING, shortVariableName));

				} else if (eObject instanceof ConstraintExpression constraint) {
					String name = constraint.getName();
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
