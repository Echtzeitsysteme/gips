package org.emoflon.gips.eclipse.view.model;

import java.util.HashMap;
import java.util.Map;

public class RootNode implements INode {

	public final Map<String, ContextNode> childs = new HashMap<>();

}