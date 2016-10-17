package com.blackducksoftware.integration.build.utils;

import java.io.File;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.blackducksoftware.integration.build.Gav;

public class FilePathGavExtractor {
	public Gav getMavenPathGav(final String filePath, final String localMavenRepoPath) {

		if (filePath == null || localMavenRepoPath == null) {
			return null;
		}

		final String cleanedFilePath = filePath.replace(localMavenRepoPath, "");
		final String[] cleanedFilePathSegments = cleanedFilePath.split(File.separator);

		String[] groupIdSegments;
		if (cleanedFilePathSegments[0].equals("")) {
			if (cleanedFilePathSegments.length < 4) {
				return null;
			}
			groupIdSegments = Arrays.copyOfRange(cleanedFilePathSegments, 1, cleanedFilePathSegments.length - 3);
		} else {
			if (cleanedFilePathSegments.length < 3) {
				return null;
			}
			groupIdSegments = Arrays.copyOfRange(cleanedFilePathSegments, 0, cleanedFilePathSegments.length - 3);
		}

		final String groupId = StringUtils.join(groupIdSegments, ".");
		final String artifactId = cleanedFilePathSegments[cleanedFilePathSegments.length - 3];
		final String version = cleanedFilePathSegments[cleanedFilePathSegments.length - 2];

		return new Gav(groupId, artifactId, version);

	}

	public Gav getGradlePathGav(final String filePath) {
		if (filePath == null) {
			return null;
		}

		final String[] filePathSegments = filePath.split(File.separator);

		if (filePathSegments.length < 5) {
			return null;
		}

		final String groupId = filePathSegments[filePathSegments.length - 5];
		final String artifactId = filePathSegments[filePathSegments.length - 4];
		final String version = filePathSegments[filePathSegments.length - 3];

		return new Gav(groupId, artifactId, version);
	}

}
