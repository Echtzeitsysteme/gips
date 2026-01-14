package org.emoflon.gips.gipsl.ui.visualization;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.EcoreUtil2;
import org.emoflon.gips.gipsl.gipsl.GipsConstant;
import org.emoflon.gips.gipsl.gipsl.GipsConstantReference;
import org.emoflon.gips.gipsl.gipsl.GipsLocalContextExpression;
import org.emoflon.gips.gipsl.gipsl.GipsMapping;
import org.emoflon.gips.gipsl.gipsl.GipsMappingExpression;
import org.emoflon.gips.gipsl.gipsl.GipsNodeExpression;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExtension;
import org.emoflon.gips.gipsl.gipsl.GipsTypeExtensionVariable;
import org.emoflon.gips.gipsl.gipsl.GipsValueExpression;
import org.emoflon.gips.gipsl.gipsl.GipsVariableReferenceExpression;
import org.emoflon.ibex.gt.editor.gT.EditorNode;

public class GipslNodeCollector {

	private final EObject node;
	private Collection<GipsMappingExpression> gipsMappingExpressions;
	private Collection<EditorNode> allEditorNodes;
	private Map<GipsMapping, Collection<EditorNode>> mapping2Node;
	private Collection<EditorNode> context2Node;
	private Collection<GipsConstant> usedConstants;
	private Collection<GipsTypeExtension> typeExtensions;

	public GipslNodeCollector(EObject node) {
		this.node = Objects.requireNonNull(node, "node");
		collectMappingExpressions();
		collectNodeExpressions();
		collectUsedConstants();
	}

	private void collectMappingExpressions() {
		gipsMappingExpressions = EcoreUtil2.getAllContentsOfType(node, GipsMappingExpression.class);
	}

	private void collectNodeExpressions() {
		mapping2Node = new HashMap<>();
		for (var mappingExpression : gipsMappingExpressions) {
			Collection<EditorNode> nodes = mapping2Node.computeIfAbsent(mappingExpression.getMapping(),
					key -> new HashSet<EditorNode>());

			if (mappingExpression.eContainer() instanceof GipsValueExpression valueExpression) {
				TreeIterator<EObject> iterator = EcoreUtil2.eAll(valueExpression.getSetExperession());
				while (iterator.hasNext()) {
					EObject next = iterator.next();
					switch (next) {
					case GipsMappingExpression exp:
						iterator.prune();
						break;
					case GipsLocalContextExpression exp:
						iterator.prune();
						break;
					case GipsNodeExpression exp:
						nodes.add(exp.getNode());
					default:
						break;
					}
				}
			}
		}

		context2Node = EcoreUtil2.getAllContentsOfType(node, GipsLocalContextExpression.class).stream() //
				.map(GipsLocalContextExpression::getExpression) //
				.filter(GipsNodeExpression.class::isInstance) //
				.map(e -> (GipsNodeExpression) e) //
				.map(GipsNodeExpression::getNode) //
				.collect(Collectors.toSet());

		allEditorNodes = EcoreUtil2.getAllContentsOfType(node, EditorNode.class).stream() //
				.collect(Collectors.toSet());

		typeExtensions = EcoreUtil2.getAllContentsOfType(node, GipsVariableReferenceExpression.class).stream() //
				.map(e -> e.getVariable()) //
				.filter(e -> e instanceof GipsTypeExtensionVariable) //
				.map(e -> e.eContainer()) //
				.filter(e -> e instanceof GipsTypeExtension) //
				.map(e -> (GipsTypeExtension) e) //
				.collect(Collectors.toSet());

	}

	private void collectUsedConstants() {
		usedConstants = EcoreUtil2.getAllContentsOfType(node, GipsConstantReference.class).stream() //
				.map(GipsConstantReference::getConstant) //
				.collect(Collectors.toSet());
	}

	public List<GipsMapping> getMappings() {
		return gipsMappingExpressions.stream().map(GipsMappingExpression::getMapping).distinct().toList();
	}

	public Collection<EditorNode> getEditorNodes() {
		return allEditorNodes;
	}

	public Collection<GipsConstant> getUsedConstants() {
		return usedConstants;
	}

	public Map<GipsMapping, Collection<EditorNode>> getMappingToEditorNodes() {
		return mapping2Node;
	}

	public Collection<EditorNode> getContextToEditorNodes() {
		return context2Node;
	}

	public Collection<GipsTypeExtension> getUsedTypeExtensions() {
		return typeExtensions;
	}

}
