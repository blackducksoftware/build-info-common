package com.blackducksoftware.integration.build.utils;

import java.io.File;
import java.util.Arrays;

public class FilePathGavExtractor {

	private static String makeMessage(final String groupId, final String artifactId, final String version) {
		final StringBuilder sb = new StringBuilder();
		sb.append("group: ");
		sb.append(groupId);
		sb.append(", ");
		sb.append("artifact: ");
		sb.append(artifactId);
		sb.append(", ");
		sb.append("version: ");
		sb.append(version);
		return sb.toString();
	}

	public static String getMavenPathGav(final String jarPath, final String localMavenRepoPath) {
		final String[] filePathSegments = jarPath.split(File.separator);
		final String[] m2RepoSegments = localMavenRepoPath.split(File.separator);
		final String[] groupIdSegments = Arrays.copyOfRange(filePathSegments, m2RepoSegments.length,
				filePathSegments.length - 3);
		final StringBuilder groupIdBuilder = new StringBuilder();
		for (int i = 0; i < groupIdSegments.length; i++) {
			groupIdBuilder.append(groupIdSegments[i]);
			if (i < groupIdSegments.length - 1) {
				groupIdBuilder.append(".");
			}
		}
		final String groupId = groupIdBuilder.toString();
		final String artifactId = filePathSegments[filePathSegments.length - 3];
		final String version = filePathSegments[filePathSegments.length - 2];
		return makeMessage(groupId, artifactId, version);

	}

	public static String getGradlePathGav(final String filePath) {
		final String[] filePathSegments = filePath.split(File.separator);
		final String groupId = filePathSegments[filePathSegments.length - 5];
		final String artifactId = filePathSegments[filePathSegments.length - 4];
		final String version = filePathSegments[filePathSegments.length - 3];
		return makeMessage(groupId, artifactId, version);
	}
}
