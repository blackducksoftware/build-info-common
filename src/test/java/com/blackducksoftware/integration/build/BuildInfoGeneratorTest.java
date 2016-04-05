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
import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BuildInfoGeneratorTest {
	private static String basePath;
	private final BuildInfo info = new BuildInfo();

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@BeforeClass
	public static void init() throws Exception {
		basePath = BuildInfoGeneratorTest.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	}

	@Test
	public void testParseFileForDependenciesNoDependencies() throws Exception {
		final String path = basePath + "Test-Files/NoDependencies.info";
		final File pathFile = new File(path);

		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "2014-03-05_15-28-48");
		if (!(buildInfo.getDependencies().size() == 0)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies.");
		}
	}

	@Test
	public void testNullParseFileForDependencies() throws Exception {
		exception.expect(Exception.class);
		final BuildInfo buildInfo = new BuildInfo();
		final InputStream nullStream = null;
		buildInfo.parseFileForDependencies(nullStream, null);
	}

	@Test
	public void testParseFileForDependenciesNonJSONInputFile() throws Exception {
		exception.expect(IllegalArgumentException.class);
		exception.expectMessage("The JsonElement provided must be a JsonObject! Type provided : ");
		// This file has invalid information and should cause the parsing to
		// Assert.fail
		final String path = basePath + "Test-Files/Invalid.info";
		final File pathFile = new File(path);
		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "2014-03-05_15-28-48");
	}

	@Test
	public void testParseFileForDependenciesIrregularJSONInputFile() throws Exception {
		final String path = basePath + "Test-Files/SomewhatInvalid.info";
		final File pathFile = new File(path);
		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "2014-03-05_15-28-48");
		if ((buildInfo.getDependencies().size() != 27)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies and some invalid parts.");
		}
	}

	@Test
	public void testParseFileForDependencies() throws Exception {
		final String path = basePath + "Test-Files/TestBuild.info";
		final File pathFile = new File(path);
		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "2014-03-05_15-28-48");
		if ((buildInfo.getDependencies().size() != 27)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies.");
		}

		assertEquals("2014-03-05_15-28-48", buildInfo.getBuildId());
	}

	@Test
	public void testParseFileForDependenciesDuplicateDependency() throws Exception {
		final String path = basePath + "Test-Files/TestBuildDuplicateDependency.info";
		final File pathFile = new File(path);
		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "2014-03-05_15-28-48");
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
		final String path = basePath + "Test-Files/TestBuild.info";
		final File pathFile = new File(path);
		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "This is Wrong");
	}

	@Test
	public void testParseFileForDependenciesNoBuildId() throws Exception {
		exception.expect(Exception.class);
		exception.expectMessage("The build id null in build-info.json file was incorrect. Expected: Id");
		final String path = basePath + "Test-Files/NoBuildId.info";
		final File pathFile = new File(path);
		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "Id");
	}

	@Test
	public void testGetBuildId() {
		assertNull(info.getBuildId());
		info.setBuildId("SomeID");
		assertEquals("SomeID", info.getBuildId());
	}

	@Test
	public void testGetArtifact() throws Exception {
		assertNull(info.getArtifact());
		BuildArtifact artifact = new BuildArtifact();
		info.setArtifact(artifact);
		assertEquals(artifact, info.getArtifact());

		artifact = new BuildArtifact();
		info.addArtifact(artifact);
		assertEquals(artifact, info.getArtifact());
	}

	@Test
	public void testSetDependencies() throws Exception {
		final BuildDependency dependency = new BuildDependency();
		final BuildDependency dependency2 = new BuildDependency();
		final List<BuildDependency> dependencyList = new ArrayList<BuildDependency>();
		dependencyList.add(dependency);
		dependencyList.add(dependency2);

		info.addDependencies(dependencyList);
		assertTrue(info.getDependencies().containsAll(dependencyList));

		final Set<BuildDependency> dependencies = new HashSet<BuildDependency>();
		info.setDependencies(new HashSet<BuildDependency>());
		assertEquals(0, info.getDependencies().size());

		dependencies.addAll(dependencyList);
		info.addDependencies(dependencies);
		assertTrue(info.getDependencies().containsAll(dependencies));
	}

	@Test
	public void testClose() throws Exception {
		final File pathFile = new File(basePath + "Test-Files/");
		final File output = new File(basePath + "Test-Files/build-info.json");

		final BuildArtifact artifact = new BuildArtifact();
		artifact.setArtifact("someArtifact");
		artifact.setGroup("someGroup");
		artifact.setId("artifactID");
		artifact.setType("someType");
		artifact.setVersion("someVersion");
		info.addArtifact(artifact);

		final BuildDependency dependency = new BuildDependency();
		final BuildDependency dependency2 = new BuildDependency();
		final List<BuildDependency> dependencyList = new ArrayList<BuildDependency>();

		final ArrayList<String> scopeList = new ArrayList<String>();
		final ArrayList<String> scopeList2 = new ArrayList<String>();

		dependency.setArtifact("someID");
		dependency.setClassifier("SomeClassifier");
		dependency.setGroup("someGroup");
		scopeList.add("someScope");
		dependency.setScope(scopeList);
		dependency.setVersion("someVersion");
		dependency2.setArtifact("someID2");
		dependency2.setClassifier("SomeClassifier2");
		dependency2.setGroup("someGroup2");
		scopeList2.add("someScope2");
		dependency2.setScope(scopeList2);
		dependency2.setVersion("someVersion2");
		dependencyList.add(dependency);
		dependencyList.add(dependency2);
		info.addDependencies(dependencyList);

		info.setBuildId("some id");
		info.close(pathFile); // Info has an artifact and dependencies
		BufferedReader reader = null;
		final StringBuilder sb = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(output));
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
		final String path = basePath + "Test-Files/Gradle-build-info.json";
		final File pathFile = new File(path);
		final FileInputStream in = new FileInputStream(pathFile);
		final BuildInfo buildInfo = new BuildInfo();
		buildInfo.parseFileForDependencies(in, "testBuildId");
		if ((buildInfo.getDependencies().size() != 10)) {
			throw new Exception("Incorrect parsing for the file with no Dependencies and some invalid parts.");
		}
	}

}
