package com.blackducksoftware.integration.build.bdio;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.blackducksoftware.bdio.io.BdioWriter;
import com.blackducksoftware.bdio.io.LinkedDataContext;
import com.blackducksoftware.bdio.model.BillOfMaterials;
import com.blackducksoftware.bdio.model.Component;
import com.blackducksoftware.bdio.model.CreationInfo;
import com.blackducksoftware.bdio.model.ExternalIdentifier;
import com.blackducksoftware.bdio.model.Project;

public class CommonBomFormatter {
	private final BdioConverter bdioConverter;
	private final Set<String> externalIds = new HashSet<>();

	public CommonBomFormatter(final BdioConverter bdioConverter) {
		this.bdioConverter = bdioConverter;
	}

	public void writeProject(final OutputStream outputStream, final String projectName, final DependencyNode root)
			throws IOException {
		final LinkedDataContext linkedDataContext = new LinkedDataContext();

		try (BdioWriter bdioWriter = new BdioWriter(linkedDataContext, outputStream)) {
			final BillOfMaterials bom = new BillOfMaterials();
			bom.setId(String.format("uuid:%s", UUID.randomUUID()));
			bom.setName(String.format("%s Black Duck I/O Export", projectName));
			bom.setSpecVersion(linkedDataContext.getSpecVersion());
			bom.setCreationInfo(CreationInfo.currentTool());
			bdioWriter.write(bom);

			final Project project = bdioConverter.createProject(root.getGav(), projectName, root.getChildren());
			bdioWriter.write(project);

			for (final DependencyNode child : root.getChildren()) {
				writeDependencyGraph(bdioWriter, child);
			}
		}
	}

	private void writeDependencyGraph(final BdioWriter writer, final DependencyNode dependencyNode) throws IOException {
		writeDependencyNode(writer, dependencyNode);

		for (final DependencyNode child : dependencyNode.getChildren()) {
			writeDependencyGraph(writer, child);
		}
	}

	private void writeDependencyNode(final BdioWriter writer, final DependencyNode dependencyNode) throws IOException {
		final Component component = bdioConverter.createComponent(dependencyNode.getGav(),
				dependencyNode.getChildren());
		final List<ExternalIdentifier> externalIdentifiers = component.getExternalIdentifiers();
		boolean alreadyAdded = false;
		for (final ExternalIdentifier externalIdentifier : externalIdentifiers) {
			if (!externalIds.add(externalIdentifier.getExternalId())) {
				alreadyAdded = true;
			}
		}

		if (!alreadyAdded) {
			writer.write(component);
		}
	}

}
