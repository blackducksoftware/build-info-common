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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BuildInfoSerializerUnitTest {
	private static final String TEST_JSON_STRING = "{\"buildId\":\"testBuildId\",\"buildArtifact\":{\"type\":\"TestString\",\"group\":\"TestString\",\"artifact\":\"TestString\",\"version\":\"TestString\",\"id\":\"TestString:TestString:TestString\"},\"dependencies\":[{\"matchType\":\"PROJECTFOUND\",\"group\":\"TestString\",\"artifact\":\"TestString\",\"version\":\"TestString\",\"scope\":[\"TestString\"],\"id\":\"TestString:TestString:TestString\",\"projectName\":\"TestString\",\"versionName\":\"TestString\",\"licenseName\":\"TestString\",\"vulnerabilityCounts\":{\"low\":0,\"medium\":123,\"high\":456}}]}";

	@Test
	public void serializeObjectToJsonString() throws Exception {
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.setBuildId("testBuildId");

		final BuildArtifact artifact = new BuildArtifact();
		artifact.setType("TestString");
		artifact.setGroup("TestString");
		artifact.setArtifact("TestString");
		artifact.setVersion("TestString");
		artifact.setId("TestString:TestString:TestString");

		buildInfo.setArtifact(artifact);

		final Set<BuildDependencyPlus> dependencies = new HashSet<BuildDependencyPlus>();

		final BuildDependencyPlus dependency = new BuildDependencyPlus();
		dependency.setArtifact("TestString");
		dependency.setClassifier("TestString");
		dependency.setExtension("TestString");
		dependency.setGroup("TestString");
		dependency.setVersion("TestString");
		dependency.setMatchType(MatchType.PROJECTFOUND);
		dependency.setProjectName("TestString");
		dependency.setVersionName("TestString");
		dependency.setLicenseName("TestString");
		final VulnerabilityCounts counts2 = new VulnerabilityCounts();
		counts2.setLow(0);
		counts2.setMedium(123);
		counts2.setHigh(456);
		dependency.setVulnerabilityCounts(counts2);

		final ArrayList<String> scopeList = new ArrayList<String>();
		scopeList.add("TestString");
		dependency.setScope(scopeList);
		dependencies.add(dependency);

		buildInfo.addDependencies(dependencies);

		final Gson serializeGson = new GsonBuilder().registerTypeAdapter(BuildInfo.class, new BuildInfoSerializer())
				.create();
		final String jsonString = serializeGson.toJson(buildInfo);

		assertEquals(TEST_JSON_STRING, jsonString);
	}

	@Test
	public void deSerializeJsonStringToObjects() throws Exception {
		final Gson deSerializeGson = new GsonBuilder().registerTypeAdapter(BuildInfo.class, new BuildInfoDeSerializer())
				.create();
		final BuildInfo buildInfo = deSerializeGson.fromJson(TEST_JSON_STRING, BuildInfo.class);
		assertEquals("testBuildId", buildInfo.getBuildId());
		assertEquals("TestString", buildInfo.getArtifact().getType());
		assertEquals("TestString", buildInfo.getArtifact().getGroup());
		assertEquals("TestString", buildInfo.getArtifact().getArtifact());
		assertEquals("TestString", buildInfo.getArtifact().getVersion());
		assertEquals("TestString:TestString:TestString", buildInfo.getArtifact().getId());

		final Set<? extends BuildDependency> dependencies = buildInfo.getDependencies();
		final BuildDependency dep = dependencies.iterator().next();
		assertTrue(dep instanceof BuildDependencyPlus);
		final BuildDependencyPlus dependency = (BuildDependencyPlus) dep;

		assertEquals("TestString", dependency.getGroup());
		assertEquals("TestString", dependency.getArtifact());
		assertEquals("TestString", dependency.getVersion());
		assertEquals(MatchType.PROJECTFOUND, dependency.getMatchType());
		assertEquals("TestString", dependency.getProjectName());
		assertEquals("TestString", dependency.getVersionName());
		assertEquals("TestString", dependency.getLicenseName());
		final List<String> scopeList = dependency.getScope();
		assertEquals("TestString", scopeList.get(0));
		final VulnerabilityCounts counts = dependency.getVulnerabilityCounts();
		assertEquals(0, counts.getLow());
		assertEquals(123, counts.getMedium());
		assertEquals(456, counts.getHigh());
	}

}
