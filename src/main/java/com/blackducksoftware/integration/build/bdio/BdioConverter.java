package com.blackducksoftware.integration.build.bdio;

import java.util.List;

import com.blackducksoftware.bdio.model.Component;
import com.blackducksoftware.bdio.model.ExternalIdentifier;
import com.blackducksoftware.bdio.model.File;
import com.blackducksoftware.bdio.model.Project;
import com.blackducksoftware.bdio.model.Relationship;

public class BdioConverter {
	private final BdioIdCreator bdioIdCreator;

	public BdioConverter(final BdioIdCreator bdioIdCreator) {
		this.bdioIdCreator = bdioIdCreator;
	}

	public Project createProject(final Gav gav, final String projectName, final String buildFilePath) {
		final String projectMavenId = bdioIdCreator.createMavenId(gav);
		final String fileId = bdioIdCreator.createFileId(buildFilePath);
		final ExternalIdentifier externalIdentifier = bdioIdCreator.createExternalIdentifier(gav);

		final Project project = new Project();
		project.setId(projectMavenId);
		project.setName(projectName);
		project.setVersion(gav.getVersion());
		project.addExternalIdentifier(externalIdentifier);
		project.addRelationship(Relationship.dynamicLink(fileId));

		return project;
	}

	public File createFile(final String buildFilePath, final List<DependencyNode> children) {
		final String fileId = bdioIdCreator.createFileId(buildFilePath);

		final File file = new File();
		file.setId(fileId);
		file.setPath(buildFilePath);
		addRelationships(file, children);

		return file;
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

	private void addRelationships(final File file, final List<DependencyNode> children) {
		for (final DependencyNode child : children) {
			final Gav childGav = child.getGav();
			file.addRelationship(Relationship.dynamicLink(bdioIdCreator.createMavenId(childGav)));
		}
	}

	private void addRelationships(final Component component, final List<DependencyNode> children) {
		for (final DependencyNode child : children) {
			final Gav childGav = child.getGav();
			component.addRelationship(Relationship.dynamicLink(bdioIdCreator.createMavenId(childGav)));
		}
	}

}
