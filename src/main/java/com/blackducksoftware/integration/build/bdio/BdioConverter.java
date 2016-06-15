package com.blackducksoftware.integration.build.bdio;

import java.util.List;

import com.blackducksoftware.bdio.model.Component;
import com.blackducksoftware.bdio.model.ExternalIdentifier;
import com.blackducksoftware.bdio.model.Project;
import com.blackducksoftware.bdio.model.Relationship;

public class BdioConverter {
	private final MavenIdCreator mavenIdCreator;

	public BdioConverter(final MavenIdCreator mavenIdCreator) {
		this.mavenIdCreator = mavenIdCreator;
	}

	public Project createProject(final Gav gav, final String projectName, final List<DependencyNode> children) {
		final String id = mavenIdCreator.createId(gav);
		final ExternalIdentifier externalIdentifier = mavenIdCreator.createExternalIdentifier(gav);

		final Project project = new Project();
		project.setId(id);
		project.setName(projectName);
		project.setVersion(gav.getVersion());
		project.addExternalIdentifier(externalIdentifier);
		addRelationships(project, children);

		return project;
	}

	public Component createComponent(final Gav gav, final List<DependencyNode> children) {
		final String id = mavenIdCreator.createId(gav);
		final ExternalIdentifier externalIdentifier = mavenIdCreator.createExternalIdentifier(gav);

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
			project.addRelationship(Relationship.dynamicLink(mavenIdCreator.createId(childGav)));
		}
	}

	private void addRelationships(final Component component, final List<DependencyNode> children) {
		for (final DependencyNode child : children) {
			final Gav childGav = child.getGav();
			component.addRelationship(Relationship.dynamicLink(mavenIdCreator.createId(childGav)));
		}
	}

}
