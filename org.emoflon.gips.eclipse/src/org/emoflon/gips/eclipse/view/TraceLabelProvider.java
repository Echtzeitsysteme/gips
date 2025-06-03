package org.emoflon.gips.eclipse.view;

import org.eclipse.jface.viewers.DelegatingStyledCellLabelProvider.IStyledLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IToolTipProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.emoflon.gips.eclipse.TracePlugin;
import org.emoflon.gips.eclipse.service.ProjectContext;
import org.emoflon.gips.eclipse.trace.TraceModelLink;
import org.emoflon.gips.eclipse.view.model.ContextNode;
import org.emoflon.gips.eclipse.view.model.LinkModelNode;
import org.emoflon.gips.eclipse.view.model.ModelNode;
import org.emoflon.gips.eclipse.view.model.ValueNode;

final class TraceLabelProvider extends LabelProvider implements IStyledLabelProvider, ILabelProvider, IToolTipProvider {

	private Image projectImage;
	private Image projectClosedImage;
	private Image rightImage;
	private Image leftImage;

	public TraceLabelProvider() {
		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		this.projectImage = sharedImages.getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT);
		this.projectClosedImage = sharedImages.getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT_CLOSED);
		this.rightImage = sharedImages.getImage(ISharedImages.IMG_TOOL_FORWARD);
		this.leftImage = sharedImages.getImage(ISharedImages.IMG_TOOL_BACK);
	}

	private Styler getValueStyler() {
		return StyledString.DECORATIONS_STYLER;
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof ContextNode node)
			return node.hasChilds() ? projectImage : projectClosedImage;

		if (element instanceof LinkModelNode node) {
			return switch (node.getDirection()) {
			case FORWARD -> rightImage;
			case BACKWARD -> leftImage;
			default -> null;
			};
		}

		return null;
	}

	@Override
	public StyledString getStyledText(Object element) {
		StyledString label;

		label = switch (element) {
		case ContextNode node -> {
			yield buildLabel(node);
		}
		case LinkModelNode node -> {
			yield buildLabel(node);
		}
		case ValueNode node -> {
			yield buildLabel(node);
		}
		case ModelNode node -> {
			yield buildLabel(node);
		}
		default -> {
			yield null;
		}
		};

		if (label != null)
			return label;

		String defaultLabel = element != null ? element.toString() : "???";
		return new StyledString(defaultLabel);
	}

	private StyledString buildLabel(LinkModelNode node) {
		ProjectContext context = TracePlugin.getInstance().getContextManager().getContext(node.getContextId());
		TraceModelLink link = null;
		StyledString label = null;

		switch (node.getDirection()) {
		case FORWARD -> {
			label = new StyledString("Creates ");
			label.append(node.getModelId(), getValueStyler());

			link = context.getModelLink(node.getParent().getModelId(), node.getModelId());
		}
		case BACKWARD -> {
			label = new StyledString("Created by ");
			label.append(node.getModelId(), getValueStyler());

			link = context.getModelLink(node.getModelId(), node.getParent().getModelId());
		}
		}

		if (link != null && label != null) {
			StringBuilder counter = new StringBuilder();
			counter.append(" maps ");
			counter.append(String.valueOf(link.getSourceNodeIds().size()));
			counter.append(" to ");
			counter.append(String.valueOf(link.getTargetNodeIds().size()));
			counter.append(" nodes");
			label.append(counter.toString(), StyledString.COUNTER_STYLER);
		}

		return label;
	}

	private StyledString buildLabel(ValueNode node) {
		ProjectContext context = TracePlugin.getInstance().getContextManager().getContext(node.getContextId());
		var values = context.getModelValues(node.getModelId());

		StyledString label = new StyledString("Stored values: ");
		label.append(String.valueOf(values.size()), StyledString.COUNTER_STYLER);

		return label;
	}

	private StyledString buildLabel(ModelNode node) {
		StyledString label = new StyledString("Model: ");
		label.append(node.getModelId(), StyledString.DECORATIONS_STYLER);

		if (!hasIncomingOrOutgoingEdges(node))
			label.append(" (empty)", StyledString.QUALIFIER_STYLER);

		return label;
	}

	private StyledString buildLabel(ContextNode node) {
		StyledString label = new StyledString("Context: ");
		label.append(node.getContextId(), StyledString.DECORATIONS_STYLER);
		return label;
	}

	private boolean hasIncomingOrOutgoingEdges(ModelNode node) {
		ProjectContext context = TracePlugin.getInstance().getContextManager().getContext(node.getContextId());

		for (String targetId : context.getTargetModels(node.getModelId())) {
			boolean edgeFound = !context.getModelLink(node.getModelId(), targetId).getSourceNodeIds().isEmpty();
			if (edgeFound)
				return true;
		}

		for (String sourceIds : context.getSourceModels(node.getModelId())) {
			boolean edgeFound = !context.getModelLink(sourceIds, node.getModelId()).getTargetNodeIds().isEmpty();
			if (edgeFound)
				return true;
		}

		return false;
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public String getToolTipText(Object element) {
		return "Tooltip (" + element + ")";
	}

//		@Override
//		public Point getToolTipShift(Object object) {
//			return new Point(5, 5);
//		}
//
//		@Override
//		public int getToolTipDisplayDelayTime(Object object) {
//			return 2000;
//		}
//
//		@Override
//		public int getToolTipTimeDisplayed(Object object) {
//			return 5000;
//		}
//
//		@Override
//		public void update(ViewerCell cell) {
//			cell.setText(cell.getElement().toString());
//		}

}