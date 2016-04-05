/*******************************************************************************
 * Black Duck Software Suite SDK
 * Copyright (C) 2016 Black Duck Software, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 *******************************************************************************/
package com.blackducksoftware.integration.build;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class BuildInfo implements Serializable {
	private static final long serialVersionUID = -6057646365673191391L;

	public static final String BUILD_ID = "buildId";
	public static final String BUILD_ARTIFACT = "buildArtifact";
	public static final String GRADLE_TYPE = "org.gradle";
	public static final String MAVEN_TYPE = "org.apache.maven";
	public static final String TYPE = "type";
	public static final String ID = "id";
	public static final String SCOPE = "scope";
	public static final String VERSION = "version";
	public static final String ARTIFACT = "artifact";
	public static final String GROUP = "group";
	public static final String DEPENDENCIES = "dependencies";
	public static final String OUTPUT_FILE_NAME = "build-info.json";

	private final transient Logger logger;
	private String buildId = null;
	private BuildArtifact buildArtifact = null;
	private Set<BuildDependency> dependencies;

	public BuildInfo() {
		logger = LoggerFactory.getLogger(BuildInfo.class);
		dependencies = new HashSet<BuildDependency>();
	}

	public Set<? extends BuildDependency> getDependencies() {
		return dependencies;
	}

	public void setDependencies(final Set<? extends BuildDependency> dependencies) {
		this.dependencies = new HashSet<BuildDependency>(dependencies);
	}

	public BuildArtifact getArtifact() {
		return buildArtifact;
	}

	public void setArtifact(final BuildArtifact artifact) {
		buildArtifact = artifact;
	}

	public String getBuildId() {
		return buildId;
	}

	public void addArtifact(final BuildArtifact artifact) throws IOException {
		buildArtifact = artifact;
	}

	public void addDependencies(final List<? extends BuildDependency> dependencies) throws IOException {
		this.dependencies.addAll(dependencies);
	}

	public void addDependencies(final Set<? extends BuildDependency> dependencies) throws IOException {
		this.dependencies.addAll(dependencies);
	}

	public void setBuildId(final String buildId) {
		this.buildId = buildId;
	}

	/**
	 * Serializes the build info object to a JSON string.
	 *
	 * @param directory
	 *            The directory to contain the JSON build info file.
	 * @throws IOException
	 *             if there is an issue closing the file.
	 *
	 */
	public void close(final File directory) throws IOException {
		try {
			final File file = new File(directory.getCanonicalPath() + File.separator + OUTPUT_FILE_NAME);

			logger.debug(OUTPUT_FILE_NAME + " file: " + file.getAbsolutePath());
			if (file.exists()) {
				// we ensure that old data is deleted so the new dependencies
				// will be recorded
				file.delete();
			} else {
				file.getParentFile().mkdirs();
			}
			file.createNewFile();
			final Gson gsonJsonGenerator = new GsonBuilder()
					.registerTypeAdapter(BuildInfo.class, new BuildInfoSerializer()).serializeNulls()
					.setPrettyPrinting().create();
			final String buildInfoJsonString = gsonJsonGenerator.toJson(this);
			final FileWriter fw = new FileWriter(file.getAbsoluteFile());
			final BufferedWriter bw = new BufferedWriter(fw);
			try {
				bw.write(buildInfoJsonString);
			} finally {
				bw.close();
			}

			logger.debug(file.getAbsolutePath());
		} catch (final IOException e) {
			logger.error(e.toString());
			throw e;
		}
	}

	/**
	 * Parses the build-info.json file to get the list of dependencies for this
	 * project that was recorded during the build by the DependencyRecorder.
	 *
	 * @param in
	 *            InputStream to read the build-info.json file, must be not null
	 * @param expectedBuildId
	 *            The build ID we are expecting to read. This must match the
	 *            build ID in the file or it resutls in an Exception
	 * @throws Exception
	 *             If an argument or build identifier is invalid.
	 * @throws JsonSyntaxException
	 *             If there is a problem de-serializing the JSON content.
	 */
	public void parseFileForDependencies(final InputStream in, final String expectedBuildId)
			throws Exception, JsonSyntaxException {
		// check and save parameters
		if (in == null) {
			throw new IllegalArgumentException("You must provide an Input Stream to read the dependencies from.");
		}
		final InputStreamReader is = new InputStreamReader(in);
		final BufferedReader br = new BufferedReader(is);
		final StringBuilder stringBuilder = new StringBuilder();

		String read = br.readLine();
		while (read != null) {
			stringBuilder.append(read);
			read = br.readLine();
		}
		final String buildInfoContents = stringBuilder.toString();

		parseFileForDependencies(buildInfoContents, expectedBuildId);
	}

	/**
	 * Parses the build-info.json file contetn to get the list of dependencies
	 * for this project that was recorded during the build by the
	 * DependencyRecorder.
	 *
	 * @param content
	 *            String content of the build-info.json file, must be not null
	 * @param expectedBuildId
	 *            The build ID we are expecting to read. This must match the
	 *            build ID in the file or it resutls in an Exception
	 * @throws Exception
	 *             If there is an error with the build information contained in
	 *             the build info file.
	 * @throws JsonSyntaxException
	 *             If there is an error de-serializing the JSON content.
	 */
	public void parseFileForDependencies(final String content, final String expectedBuildId)
			throws Exception, JsonSyntaxException {
		// check and save parameters
		if (content == null || content.trim().length() == 0) {
			throw new IllegalArgumentException("You must provide the content of the build-info file.");
		}

		final Gson gsonDeserializer = new GsonBuilder()
				.registerTypeAdapter(BuildInfo.class, new BuildInfoDeSerializer()).create();

		final BuildInfo buildInfo = gsonDeserializer.fromJson(content, BuildInfo.class);

		if (expectedBuildId.equals(buildInfo.getBuildId())) {
			logger.info("The " + BuildInfo.OUTPUT_FILE_NAME + " file is current.");
		} else {
			logger.error(
					"[ERROR]: File build id : " + buildInfo.getBuildId() + ", Internal build id : " + expectedBuildId);
			// FIXME don't throw raw Exception
			throw new Exception("The build id " + buildInfo.getBuildId() + " in " + BuildInfo.OUTPUT_FILE_NAME
					+ " file was incorrect. Expected: " + expectedBuildId);
		}

		setBuildId(buildInfo.getBuildId());
		setArtifact(buildInfo.getArtifact());
		setDependencies(buildInfo.getDependencies());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildArtifact == null) ? 0 : buildArtifact.hashCode());
		result = prime * result + ((buildId == null) ? 0 : buildId.hashCode());
		result = prime * result + ((dependencies == null) ? 0 : dependencies.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BuildInfo other = (BuildInfo) obj;
		if (buildArtifact == null) {
			if (other.buildArtifact != null) {
				return false;
			}
		} else if (!buildArtifact.equals(other.buildArtifact)) {
			return false;
		}
		if (buildId == null) {
			if (other.buildId != null) {
				return false;
			}
		} else if (!buildId.equals(other.buildId)) {
			return false;
		}
		if (dependencies == null) {
			if (other.dependencies != null) {
				return false;
			}
		} else {
			if (other.dependencies == null) {
				return false;
			}
			if (dependencies != null) {
				if (dependencies.size() != other.dependencies.size()) {
					return false;
				} else {
					if (dependencies.size() > 0) {
						for (final BuildDependency currDep : dependencies) {
							// contains method on the Set was causing issues
							boolean found = false;
							for (final BuildDependency otherCurrDep : other.dependencies) {
								if (otherCurrDep.equals(currDep)) {
									found = true;
									break;
								}
							}
							if (!found) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BuildInfo [");
		builder.append("buildId=");
		builder.append(buildId);
		if (buildArtifact != null) {
			builder.append(", ");
			builder.append("buildArtifact=");
			builder.append(buildArtifact.toString());
		}
		if (dependencies != null) {
			builder.append(", ");
			builder.append("dependencies=");
			int i = 0;
			for (final BuildDependency currDep : dependencies) {
				if (i == 0) {
					builder.append(currDep.toString());
				} else {
					builder.append(", ");
					builder.append(currDep.toString());
				}
				i++;
			}
		}
		builder.append("]");
		return builder.toString();
	}

}
