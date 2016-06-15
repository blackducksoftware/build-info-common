package com.blackducksoftware.integration.build.bdio;

import java.util.ArrayList;
import java.util.List;

public class DependencyNode {
	private final Gav gav;
	private final List<DependencyNode> children = new ArrayList<>();

	public DependencyNode(final Gav gav, final List<DependencyNode> children) {
		this.gav = gav;
		if (null != children && children.size() > 0) {
			this.children.addAll(children);
		}
	}

	public Gav getGav() {
		return gav;
	}

	public List<DependencyNode> getChildren() {
		return children;
	}

}
