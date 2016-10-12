package com.blackducksoftware.integration.build.bdio;

import com.blackducksoftware.bdio.model.ExternalIdentifier;
import com.blackducksoftware.bdio.model.ExternalIdentifierBuilder;
import com.blackducksoftware.integration.build.Gav;

public class BdioIdCreator {
	private final ExternalIdentifierBuilder externalIdentifierBuilder = ExternalIdentifierBuilder.create();

	public ExternalIdentifier createExternalIdentifier(final Gav gav) {
		final String groupId = gav.getGroupId();
		final String artifactId = gav.getArtifactId();
		final String version = gav.getVersion();

		return externalIdentifierBuilder.maven(groupId, artifactId, version).build().get();
	}

	public String createMavenId(final Gav gav) {
		return String.format("mvn:%s/%s/%s", gav.getGroupId(), gav.getArtifactId(), gav.getVersion());
	}

}
