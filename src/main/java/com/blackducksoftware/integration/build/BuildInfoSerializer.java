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

import java.lang.reflect.Type;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class BuildInfoSerializer implements JsonSerializer<BuildInfo> {
	@Override
	public JsonElement serialize(final BuildInfo buildInfo, final Type typeOfSrc,
			final JsonSerializationContext context) {
		final JsonObject buildInfoJson = new JsonObject();
		if (buildInfo.getBuildId() != null) {
			buildInfoJson.add(BuildInfo.BUILD_ID, context.serialize(buildInfo.getBuildId()));
		}

		final JsonObject buildArtifactJson = new JsonObject();
		if (buildInfo.getArtifact().getType() != null) {
			buildArtifactJson.add(BuildInfo.TYPE, context.serialize(buildInfo.getArtifact().getType()));
		}

		if (buildInfo.getArtifact().getGroup() != null) {
			buildArtifactJson.add(BuildInfo.GROUP, context.serialize(buildInfo.getArtifact().getGroup()));
		}
		if (buildInfo.getArtifact().getArtifact() != null) {
			buildArtifactJson.add(BuildInfo.ARTIFACT, context.serialize(buildInfo.getArtifact().getArtifact()));
		}
		if (buildInfo.getArtifact().getVersion() != null) {
			buildArtifactJson.add(BuildInfo.VERSION, context.serialize(buildInfo.getArtifact().getVersion()));
		}
		if (buildInfo.getArtifact().getGroup() != null && buildInfo.getArtifact().getArtifact() != null
				&& buildInfo.getArtifact().getVersion() != null) {
			final String externalId = buildInfo.getArtifact().getGroup() + ":" + buildInfo.getArtifact().getArtifact()
					+ ":" + buildInfo.getArtifact().getVersion();
			buildArtifactJson.add(BuildInfo.ID, context.serialize(externalId));
		}

		buildInfoJson.add(BuildInfo.BUILD_ARTIFACT, buildArtifactJson);

		if (buildInfo.getDependencies() != null) {
			final JsonArray dependencyArrayJson = serializeDependencies(buildInfo.getDependencies(), context);

			buildInfoJson.add(BuildInfo.DEPENDENCIES, dependencyArrayJson);
		}

		return buildInfoJson;
	}

	protected JsonArray serializeDependencies(final Set<? extends BuildDependency> dependencies,
			final JsonSerializationContext context) {
		final JsonArray dependencyArrayJson = new JsonArray();

		if (!dependencies.isEmpty()) {
			for (final BuildDependency dependency : dependencies) {
				if (dependency instanceof BuildDependencyPlus) {
					final BuildDependencyPlus dependencyPlus = (BuildDependencyPlus) dependency;
					final JsonObject buildDependencyPlusJson = serializeBuildDependencyPlus(dependencyPlus, context);
					dependencyArrayJson.add(buildDependencyPlusJson);
				} else {
					final JsonObject buildDependencyJson = serializeBuildDependency(dependency, context);
					dependencyArrayJson.add(buildDependencyJson);
				}
			}
		}
		return dependencyArrayJson;
	}

	protected JsonObject serializeBuildDependency(final BuildDependency dependency,
			final JsonSerializationContext context) {
		final JsonObject buildDependencyJson = new JsonObject();
		if (dependency.getGroup() != null) {
			buildDependencyJson.add(BuildInfo.GROUP, context.serialize(dependency.getGroup()));
		}
		if (dependency.getArtifact() != null) {
			buildDependencyJson.add(BuildInfo.ARTIFACT, context.serialize(dependency.getArtifact()));
		}
		if (dependency.getVersion() != null) {
			buildDependencyJson.add(BuildInfo.VERSION, context.serialize(dependency.getVersion()));
		}
		if (dependency.getScope() != null) {
			final JsonArray scopeArrayJson = new JsonArray();
			if (!dependency.getScope().isEmpty()) {
				for (final String scope : dependency.getScope()) {
					final JsonPrimitive scopeElement = new JsonPrimitive(scope);
					scopeArrayJson.add(scopeElement);
				}
			}
			buildDependencyJson.add(BuildInfo.SCOPE, scopeArrayJson);
		}
		if (dependency.getGroup() != null && dependency.getArtifact() != null && dependency.getVersion() != null) {
			final String externalId = dependency.getGroup() + ":" + dependency.getArtifact() + ":"
					+ dependency.getVersion();
			buildDependencyJson.add(BuildInfo.ID, context.serialize(externalId));
		}
		return buildDependencyJson;
	}

	protected JsonObject serializeBuildDependencyPlus(final BuildDependencyPlus dependency,
			final JsonSerializationContext context) {
		final JsonObject buildDependencyPlusJson = new JsonObject();

		if (dependency.getMatchType() != null) {
			buildDependencyPlusJson.add(BuildDependencyPlus.MATCH_TYPE,
					context.serialize(dependency.getMatchType().toString()));
		}
		if (dependency.getGroup() != null) {
			buildDependencyPlusJson.add(BuildInfo.GROUP, context.serialize(dependency.getGroup()));
		}
		if (dependency.getArtifact() != null) {
			buildDependencyPlusJson.add(BuildInfo.ARTIFACT, context.serialize(dependency.getArtifact()));
		}
		if (dependency.getVersion() != null) {
			buildDependencyPlusJson.add(BuildInfo.VERSION, context.serialize(dependency.getVersion()));
		}
		if (dependency.getScope() != null) {
			final JsonArray scopeArrayJson = new JsonArray();
			if (!dependency.getScope().isEmpty()) {
				for (final String scope : dependency.getScope()) {
					final JsonPrimitive scopeElement = new JsonPrimitive(scope);
					scopeArrayJson.add(scopeElement);
				}
			}
			buildDependencyPlusJson.add(BuildInfo.SCOPE, scopeArrayJson);
		}
		if (dependency.getGroup() != null && dependency.getArtifact() != null && dependency.getVersion() != null) {
			final String externalId = dependency.getGroup() + ":" + dependency.getArtifact() + ":"
					+ dependency.getVersion();
			buildDependencyPlusJson.add(BuildInfo.ID, context.serialize(externalId));
		}

		if (dependency.getProjectName() != null) {
			buildDependencyPlusJson.add(BuildDependencyPlus.PROJECT_NAME,
					context.serialize(dependency.getProjectName()));
		}
		if (dependency.getVersionName() != null) {
			buildDependencyPlusJson.add(BuildDependencyPlus.VERSION_NAME,
					context.serialize(dependency.getVersionName()));
		}
		if (dependency.getVersionName() != null) {
			buildDependencyPlusJson.add(BuildDependencyPlus.LICENSE_NAME,
					context.serialize(dependency.getLicenseName()));
		}
		if (dependency.getVulnerabilityCounts() != null) {
			final JsonObject vulnerabilityCountsJson = new JsonObject();
			vulnerabilityCountsJson.add(BuildDependencyPlus.LOW_VULNERABILITY,
					context.serialize(dependency.getVulnerabilityCounts().getLow()));
			vulnerabilityCountsJson.add(BuildDependencyPlus.MEDIUM_VULNERABILITY,
					context.serialize(dependency.getVulnerabilityCounts().getMedium()));
			vulnerabilityCountsJson.add(BuildDependencyPlus.HIGH_VULNERABILITY,
					context.serialize(dependency.getVulnerabilityCounts().getHigh()));
			buildDependencyPlusJson.add(BuildDependencyPlus.VULNERABILITY_COUNTS, vulnerabilityCountsJson);
		}

		return buildDependencyPlusJson;
	}
}
