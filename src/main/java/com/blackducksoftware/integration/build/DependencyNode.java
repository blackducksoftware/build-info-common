package com.blackducksoftware.integration.build;

import java.util.List;

public class DependencyNode {
	private final Gav gav;
	private final List<DependencyNode> children;

	public DependencyNode(final Gav gav, final List<DependencyNode> children) {
		this.gav = gav;
		this.children = children;
	}

	public Gav getGav() {
		return gav;
	}

	public List<DependencyNode> getChildren() {
		return children;
	}

}
