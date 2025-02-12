package org.emoflon.gips.debugger.view;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IToolTipProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.emoflon.gips.debugger.view.model.ContextNode;
import org.emoflon.gips.debugger.view.model.LinkModelNode;

final class TraceLabelProvider extends LabelProvider implements ILabelProvider, IToolTipProvider {

		private Image projectImage;
		private Image rightImage;
		private Image leftImage;

		public TraceLabelProvider() {
			ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
			this.projectImage = sharedImages.getImage(org.eclipse.ui.ide.IDE.SharedImages.IMG_OBJ_PROJECT);
			this.rightImage = sharedImages.getImage(ISharedImages.IMG_TOOL_FORWARD);
			this.leftImage = sharedImages.getImage(ISharedImages.IMG_TOOL_BACK);
		}

		@Override
		public Image getImage(Object element) {
			if (element instanceof ContextNode)
				return projectImage;

			if (element instanceof LinkModelNode node) {
				return switch (node.direction) {
				case FORWARD -> rightImage;
				case BACKWARD -> leftImage;
				default -> null;
				};
			}

			return null;
		}

		@Override
		public String getText(Object element) {
			return element != null ? element.toString() : "???";
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