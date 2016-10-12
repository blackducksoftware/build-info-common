package com.blackducksoftware.integration.build.bdio;

import java.util.List;

import com.blackducksoftware.bdio.model.Component;
import com.blackducksoftware.bdio.model.ExternalIdentifier;
import com.blackducksoftware.bdio.model.Project;
import com.blackducksoftware.bdio.model.Relationship;
import com.blackducksoftware.integration.build.DependencyNode;
import com.blackducksoftware.integration.build.Gav;

public class BdioConverter {
	private final BdioIdCreator bdioIdCreator = new BdioIdCreator();

	public Project createProject(final Gav gav, final String projectName, final List<DependencyNode> children) {
		final String id = bdioIdCreator.createMavenId(gav);
		final ExternalIdentifier externalIdentifier = bdioIdCreator.createExternalIdentifier(gav);

		final Project project = new Project();
		project.setId(id);
		project.setName(projectName);
		project.setVersion(gav.getVersion());
		project.addExternalIdentifier(externalIdentifier);
		addRelationships(project, children);

		return project;
	}

	public Component createComponent(final Gav gav, final List<DependencyNode> children) {
		final String id = bdioIdCreator.createMavenId(gav);
		final ExternalIdentifier externalIdentifier = bdioIdCreator.createExternalIdentifier(gav);

		final Component component = new Component();
		component.setId(id);
		component.setVersion(gav.getVersion());
		component.addExternalIdentifier(externalIdentifier);
		addRelationships(component, children);

		return component;
	}

	private void addRelationships(final Project project, final List<DependencyNode> children) {
		for (final DependencyNode child : children) {
			final Gav childGav = child.getGav();
			project.addRelationship(Relationship.dynamicLink(bdioIdCreator.createMavenId(childGav)));
		}
	}

	private void addRelationships(final Component component, final List<DependencyNode> children) {
		for (final DependencyNode child : children) {
			final Gav childGav = child.getGav();
			component.addRelationship(Relationship.dynamicLink(bdioIdCreator.createMavenId(childGav)));
		}
	}

}