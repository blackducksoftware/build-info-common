package com.blackducksoftware.integration.build.utils;

import java.io.File;

import org.apache.commons.lang3.StringUtils;

import com.blackducksoftware.integration.build.bdio.Gav;

public class FilePathGavExtractor {
	public Gav getMavenPathGav(final String filePath, final String localMavenRepoPath) {
		final String[] filePathSegments = filePath.split(File.separator);

		final String cleanedFilePath = filePath.replace(localMavenRepoPath, "");
		final String[] groupIdSegments = cleanedFilePath.split(File.separator);

		final String groupId = StringUtils.join(groupIdSegments, ".");
		final String artifactId = filePathSegments[filePathSegments.length - 3];
		final String version = filePathSegments[filePathSegments.length - 2];

		return new Gav(groupId, artifactId, version);

	}

	public Gav getGradlePathGav(final String filePath) {
		final String[] filePathSegments = filePath.split(File.separator);

		final String groupId = filePathSegments[filePathSegments.length - 5];
		final String artifactId = filePathSegments[filePathSegments.length - 4];
		final String version = filePathSegments[filePathSegments.length - 3];

		return new Gav(groupId, artifactId, version);
	}

}
