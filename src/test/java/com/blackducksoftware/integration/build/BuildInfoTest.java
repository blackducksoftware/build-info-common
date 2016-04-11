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
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class BuildInfoTest {
	private static final String TEST_JSON_STRING = "{\"buildId\":\"testBuildId\",\"buildArtifact\":{\"type\":\"TestString\",\"group\":\"TestString\",\"artifact\":\"TestString\",\"version\":\"TestString\",\"id\":\"TestString:TestString:TestString\"},\"dependencies\":[{\"matchType\":\"PROJECTFOUND\",\"group\":\"TestString\",\"artifact\":\"TestString\",\"version\":\"TestString\",\"scopes\":[\"TestString\"],\"id\":\"TestString:TestString:TestString\",\"projectName\":\"TestString\",\"versionName\":\"TestString\",\"licenseName\":\"TestString\",\"vulnerabilityCounts\":{\"low\":0,\"medium\":123,\"high\":456}}]}";

	@Rule
	public ExpectedException exception = ExpectedException.none();

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

		buildInfo.setBuildArtifact(artifact);

		final Set<BuildDependency> dependencies = new HashSet<BuildDependency>();

		final BuildDependency dependency = new BuildDependency();
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

		final Set<String> scopes = new HashSet<String>();
		scopes.add("TestString");
		dependency.setScopes(scopes);
		dependencies.add(dependency);

		buildInfo.setDependencies(dependencies);

		final Gson gson = new Gson();
		final String jsonString = gson.toJson(buildInfo);

		assertEquals(TEST_JSON_STRING, jsonString);
	}

	@Test
	public void deSerializeJsonStringToObjects() throws Exception {
		final Gson gson = new Gson();
		final BuildInfo buildInfo = gson.fromJson(TEST_JSON_STRING, BuildInfo.class);
		assertEquals("testBuildId", buildInfo.getBuildId());
		assertEquals("TestString", buildInfo.getBuildArtifact().getType());
		assertEquals("TestString", buildInfo.getBuildArtifact().getGroup());
		assertEquals("TestString", buildInfo.getBuildArtifact().getArtifact());
		assertEquals("TestString", buildInfo.getBuildArtifact().getVersion());
		assertEquals("TestString:TestString:TestString", buildInfo.getBuildArtifact().getId());

		final Set<? extends BuildDependency> dependencies = buildInfo.getDependencies();
		final BuildDependency dep = dependencies.iterator().next();
		assertTrue(dep instanceof BuildDependency);
		final BuildDependency dependency = dep;

		assertEquals("TestString", dependency.getGroup());
		assertEquals("TestString", dependency.getArtifact());
		assertEquals("TestString", dependency.getVersion());
		assertEquals(MatchType.PROJECTFOUND, dependency.getMatchType());
		assertEquals("TestString", dependency.getProjectName());
		assertEquals("TestString", dependency.getVersionName());
		assertEquals("TestString", dependency.getLicenseName());
		final Set<String> scopes = dependency.getScopes();
		assertTrue(scopes.contains("TestString"));
		assertEquals(1, scopes.size());
		final VulnerabilityCounts counts = dependency.getVulnerabilityCounts();
		assertEquals(0, counts.getLow());
		assertEquals(123, counts.getMedium());
		assertEquals(456, counts.getHigh());
	}

	@Test
	public void testParseFileForDependenciesNoDependencies() throws Exception {
		final InputStream inputStream = getInputStream("NoDependencies.info");

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(inputStream, "2014-03-05_15-28-48");
		if (!(buildInfo.getDependencies().size() == 0)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies.");
		}
	}

	@Test
	public void testNullParseFileForDependencies() throws Exception {
		exception.expect(Exception.class);

		final InputStream nullStream = null;

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(nullStream, null);
	}

	@Test
	public void testParseFileForDependenciesNonJSONInputFile() throws Exception {
		exception.expect(JsonSyntaxException.class);
		exception.expectMessage("Expected BEGIN_OBJECT but was STRING at line 1 column 1");

		final InputStream inputStream = getInputStream("Invalid.info");

		final BuildInfo buildInfo = new BuildInfo();
		// This file has invalid information and should cause the parsing to
		// Assert.fail
		buildInfo.parseFileForDependencies(inputStream, "2014-03-05_15-28-48");
	}

	@Test
	public void testParseFileForDependenciesIrregularJSONInputFile() throws Exception {
		final InputStream inputStream = getInputStream("SomewhatInvalid.info");

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(inputStream, "2014-03-05_15-28-48");
		if ((buildInfo.getDependencies().size() != 27)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies and some invalid parts.");
		}
	}

	@Test
	public void testParseFileForDependencies() throws Exception {
		final InputStream inputStream = getInputStream("TestBuild.info");

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(inputStream, "2014-03-05_15-28-48");
		if ((buildInfo.getDependencies().size() != 27)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies.");
		}

		assertEquals("2014-03-05_15-28-48", buildInfo.getBuildId());
	}

	@Test
	public void testParseFileForDependenciesDuplicateDependency() throws Exception {
		final InputStream inputStream = getInputStream("TestBuildDuplicateDependency.info");

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(inputStream, "2014-03-05_15-28-48");
		if ((buildInfo.getDependencies().size() != 27)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies.");
		}

		assertEquals("2014-03-05_15-28-48", buildInfo.getBuildId());
	}

	@Test
	public void testParseFileForDependenciesBadBuildId() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage(
				"The build id 2014-03-05_15-28-48 in build-info.json file was incorrect. Expected: This is Wrong");

		final InputStream inputStream = getInputStream("TestBuild.info");

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(inputStream, "This is Wrong");
	}

	@Test
	public void testParseFileForDependenciesNoBuildId() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage("The build id null in build-info.json file was incorrect. Expected: Id");

		final InputStream inputStream = getInputStream("NoBuildId.info");

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(inputStream, "Id");
	}

	@Test
	public void testGetBuildId() {
		final BuildInfo buildInfo = new BuildInfo();
		assertNull(buildInfo.getBuildId());
		buildInfo.setBuildId("SomeID");
		assertEquals("SomeID", buildInfo.getBuildId());
	}

	@Test
	public void testGetArtifact() throws Exception {
		final BuildInfo buildInfo = new BuildInfo();

		assertNull(buildInfo.getBuildArtifact());
		final BuildArtifact artifact = new BuildArtifact();
		buildInfo.setBuildArtifact(artifact);
		assertEquals(artifact, buildInfo.getBuildArtifact());
	}

	@Test
	public void testSetDependencies() throws Exception {
		final BuildInfo buildInfo = new BuildInfo();

		final BuildDependency dependency = new BuildDependency();
		final BuildDependency dependency2 = new BuildDependency();
		final Set<BuildDependency> dependencySet = new HashSet<BuildDependency>();
		dependencySet.add(dependency);
		dependencySet.add(dependency2);

		buildInfo.setDependencies(dependencySet);
		assertTrue(buildInfo.getDependencies().containsAll(dependencySet));

		buildInfo.setDependencies(new HashSet<BuildDependency>());
		assertEquals(0, buildInfo.getDependencies().size());
	}

	@Test
	public void testClose() throws Exception {
		final BuildInfo buildInfo = new BuildInfo();

		final BuildArtifact artifact = new BuildArtifact();
		artifact.setArtifact("someArtifact");
		artifact.setGroup("someGroup");
		artifact.setId("artifactID");
		artifact.setType("someType");
		artifact.setVersion("someVersion");
		buildInfo.setBuildArtifact(artifact);

		final BuildDependency dependency = new BuildDependency();
		final BuildDependency dependency2 = new BuildDependency();

		final Set<String> scopeList = new HashSet<String>();
		final Set<String> scopeList2 = new HashSet<String>();

		dependency.setArtifact("someID");
		dependency.setClassifier("SomeClassifier");
		dependency.setGroup("someGroup");
		scopeList.add("someScope");
		dependency.setScopes(scopeList);
		dependency.setVersion("someVersion");
		dependency2.setArtifact("someID2");
		dependency2.setClassifier("SomeClassifier2");
		dependency2.setGroup("someGroup2");
		scopeList2.add("someScope2");
		dependency2.setScopes(scopeList2);
		dependency2.setVersion("someVersion2");

		final Set<BuildDependency> dependencies = new HashSet<BuildDependency>();
		dependencies.add(dependency);
		dependencies.add(dependency2);
		buildInfo.setDependencies(dependencies);

		buildInfo.setBuildId("some id");
		buildInfo.close(getFile(""));
		// Info has an artifact and dependencies
		BufferedReader reader = null;
		final StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(getFile("build-info.json")));
			String line = reader.readLine();
			sb.append(line);
			while (line != null) {
				line = reader.readLine();
				sb.append(line);
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		final String readerOutput = sb.toString();

		assertTrue(readerOutput.contains("{  \"buildId\": \"some id\","));
		assertTrue(readerOutput.contains("\"buildArtifact\": {"));

		assertTrue(readerOutput.contains("\"type\": \"someType\","));
		assertTrue(readerOutput.contains("\"group\": \"someGroup\","));
		assertTrue(readerOutput.contains("\"artifact\": \"someArtifact\","));
		assertTrue(readerOutput.contains("\"version\": \"someVersion\","));
		assertTrue(readerOutput.contains("\"id\": \"someGroup:someArtifact:someVersion\"  },"));

		assertTrue(readerOutput.contains("\"dependencies\": ["));
		assertTrue(readerOutput.contains("\"group\": \"someGroup2\","));
		assertTrue(readerOutput.contains("\"artifact\": \"someID2\","));
		assertTrue(readerOutput.contains("\"version\": \"someVersion2\","));
		assertTrue(readerOutput.contains("\"scope\": ["));
		assertTrue(readerOutput.contains("\"someScope2\""));
		assertTrue(readerOutput.contains("\"id\": \"someGroup2:someID2:someVersion2\""));
		assertTrue(readerOutput.contains("\"group\": \"someGroup\","));
		assertTrue(readerOutput.contains("\"artifact\": \"someID\","));
		assertTrue(readerOutput.contains("\"version\": \"someVersion\","));
		assertTrue(readerOutput.contains("\"someScope\""));
		assertTrue(readerOutput.contains("\"id\": \"someGroup:someID:someVersion\""));
	}

	@Test
	public void testParseGradleBuildInfoFile() throws Exception {
		final InputStream inputStream = getInputStream("Gradle-build-info.json");

		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(inputStream, "testBuildId");
		if ((buildInfo.getDependencies().size() != 10)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies and some invalid parts.");
		}
	}

	private File getFile(final String filename) {
		final URL url = this.getClass().getResource("/com/blackducksoftware/integration/build/" + filename);
		final File file = new File(url.getFile());
		return file;
	}

	private InputStream getInputStream(final String filename) throws FileNotFoundException {
		final File file = getFile(filename);
		final FileInputStream fileInputStream = new FileInputStream(file);
		return fileInputStream;
	}

}
