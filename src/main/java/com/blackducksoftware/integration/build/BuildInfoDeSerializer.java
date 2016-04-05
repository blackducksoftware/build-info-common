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

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class BuildInfoDeSerializer implements JsonDeserializer<BuildInfo> {
	@Override
	public BuildInfo deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		if (!(json instanceof JsonObject)) {
			throw new IllegalArgumentException("The JsonElement provided must be a JsonObject! Type provided : "
					+ json.getClass().getSimpleName());
		}

		final JsonObject jsonBuildInfo = (JsonObject) json;

		final BuildInfo buildInfo = new BuildInfo();
		if (jsonBuildInfo.get(BuildInfo.BUILD_ID) != null) {
			buildInfo.setBuildId(jsonBuildInfo.get(BuildInfo.BUILD_ID).getAsString());
		}

		BuildArtifact artifact;
		if (jsonBuildInfo.get(BuildInfo.BUILD_ARTIFACT) != null) {
			artifact = getBuildArtifactFromJson(jsonBuildInfo.get(BuildInfo.BUILD_ARTIFACT).getAsJsonObject());
		} else {
			artifact = new BuildArtifact();
		}
		buildInfo.setArtifact(artifact);

		final Set<BuildDependencyPlus> dependencies = new HashSet<BuildDependencyPlus>();
		if (jsonBuildInfo.get(BuildInfo.DEPENDENCIES) != null) {
			final JsonArray dependenciesJsonArray = jsonBuildInfo.get(BuildInfo.DEPENDENCIES).getAsJsonArray();
			if (dependenciesJsonArray.size() > 0) {
				for (int i = 0; i < dependenciesJsonArray.size(); i++) {
					final JsonObject dependencyJson = dependenciesJsonArray.get(i).getAsJsonObject();
					final BuildDependencyPlus dependency = getBuildDependencyPlusFromJson(dependencyJson);
					dependencies.add(dependency);
				}
			}
		}
		try {
			buildInfo.addDependencies(dependencies);
		} catch (final IOException e) {
			throw new JsonParseException(e);
		}

		return buildInfo;
	}

	public BuildArtifact getBuildArtifactFromJson(final JsonObject jsonBuildArtifact) {
		final BuildArtifact artifact = new BuildArtifact();
		if (jsonBuildArtifact.get(BuildInfo.TYPE) != null) {
			artifact.setType(jsonBuildArtifact.get(BuildInfo.TYPE).getAsString());
		}
		if (jsonBuildArtifact.get(BuildInfo.GROUP) != null) {
			artifact.setGroup(jsonBuildArtifact.get(BuildInfo.GROUP).getAsString());
		}
		if (jsonBuildArtifact.get(BuildInfo.ARTIFACT) != null) {
			artifact.setArtifact(jsonBuildArtifact.get(BuildInfo.ARTIFACT).getAsString());
		}
		if (jsonBuildArtifact.get(BuildInfo.VERSION) != null) {
			artifact.setVersion(jsonBuildArtifact.get(BuildInfo.VERSION).getAsString());
		}
		if (jsonBuildArtifact.get(BuildInfo.ID) != null) {
			artifact.setId(jsonBuildArtifact.get(BuildInfo.ID).getAsString());
		}

		return artifact;
	}

	public BuildDependencyPlus getBuildDependencyPlusFromJson(final JsonObject dependencyJson) {
		final BuildDependencyPlus buildDependency = new BuildDependencyPlus();
		if (dependencyJson.get(BuildInfo.GROUP) != null) {
			buildDependency.setGroup(dependencyJson.get(BuildInfo.GROUP).getAsString());
		}
		if (dependencyJson.get(BuildInfo.ARTIFACT) != null) {
			buildDependency.setArtifact(dependencyJson.get(BuildInfo.ARTIFACT).getAsString());
		}
		if (dependencyJson.get(BuildInfo.VERSION) != null) {
			buildDependency.setVersion(dependencyJson.get(BuildInfo.VERSION).getAsString());
		}
		if (dependencyJson.get(BuildInfo.SCOPE) != null) {
			final JsonArray scopesJson = dependencyJson.get(BuildInfo.SCOPE).getAsJsonArray();
			final List<String> scopeList = new ArrayList<String>();
			if (scopesJson.size() > 0) {
				for (int x = 0; x < scopesJson.size(); x++) {
					final String scope = scopesJson.get(x).getAsString();
					scopeList.add(scope);
				}
			}
			buildDependency.setScope(scopeList);
		}
		if (dependencyJson.get(BuildDependencyPlus.PROJECT_NAME) != null) {
			buildDependency.setProjectName(dependencyJson.get(BuildDependencyPlus.PROJECT_NAME).getAsString());
		} else {
			buildDependency.setProjectName(null);
		}
		if (dependencyJson.get(BuildDependencyPlus.VERSION_NAME) != null) {
			buildDependency.setVersionName(dependencyJson.get(BuildDependencyPlus.VERSION_NAME).getAsString());
		} else {
			buildDependency.setVersionName(null);
		}

		if (dependencyJson.get(BuildDependencyPlus.MATCH_TYPE) != null) {

			final MatchType matchType = MatchType
					.getMatchType(dependencyJson.get(BuildDependencyPlus.MATCH_TYPE).getAsString());
			if (buildDependency.getProjectName() == null && buildDependency.getVersionName() == null
					&& matchType == MatchType.UNKNOWNMATCH) {
				buildDependency.setMatchType(null);
			} else {
				buildDependency.setMatchType(matchType);
			}
		} else {
			buildDependency.setMatchType(null);
		}

		if (dependencyJson.get(BuildDependencyPlus.LICENSE_NAME) != null) {
			buildDependency.setLicenseName(dependencyJson.get(BuildDependencyPlus.LICENSE_NAME).getAsString());
		} else {
			buildDependency.setLicenseName(null);
		}
		if (dependencyJson.get(BuildDependencyPlus.VULNERABILITY_COUNTS) != null) {
			final JsonObject vulnCountsJson = dependencyJson.get(BuildDependencyPlus.VULNERABILITY_COUNTS)
					.getAsJsonObject();
			final VulnerabilityCounts counts = new VulnerabilityCounts();
			if (vulnCountsJson.get(BuildDependencyPlus.LOW_VULNERABILITY) != null
					&& vulnCountsJson.get(BuildDependencyPlus.LOW_VULNERABILITY).isJsonPrimitive()) {
				counts.setLow(vulnCountsJson.get(BuildDependencyPlus.LOW_VULNERABILITY).getAsInt());
			}

			if (vulnCountsJson.get(BuildDependencyPlus.MEDIUM_VULNERABILITY) != null
					&& vulnCountsJson.get(BuildDependencyPlus.MEDIUM_VULNERABILITY).isJsonPrimitive()) {
				counts.setMedium(vulnCountsJson.get(BuildDependencyPlus.MEDIUM_VULNERABILITY).getAsInt());
			}

			if (vulnCountsJson.get(BuildDependencyPlus.HIGH_VULNERABILITY) != null
					&& vulnCountsJson.get(BuildDependencyPlus.HIGH_VULNERABILITY).isJsonPrimitive()) {
				counts.setHigh(vulnCountsJson.get(BuildDependencyPlus.HIGH_VULNERABILITY).getAsInt());
			}
			buildDependency.setVulnerabilityCounts(counts);
		} else {
			buildDependency.setVulnerabilityCounts(null);
		}

		return buildDependency;
	}

}
