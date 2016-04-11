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

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class BuildDependencyUnitTest {
	@Test
	public void testSetGroup() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setGroup(null);
		assertNull(dependency.getGroup());
		dependency.setGroup("group");
		assertEquals(dependency.getGroup(), "group");
	}

	@Test
	public void testSetArtifact() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setArtifact(null);
		assertNull(dependency.getArtifact());
		dependency.setArtifact("artifact");
		assertEquals(dependency.getArtifact(), "artifact");
	}

	@Test
	public void testSetVersion() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setVersion(null);
		assertNull(dependency.getVersion());
		dependency.setVersion("version");
		assertEquals(dependency.getVersion(), "version");
	}

	@Test
	public void testSetClassifier() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setClassifier(null);
		assertNull(dependency.getClassifier());

		dependency.setClassifier("Something");
		assertEquals(dependency.getClassifier(), "Something");
	}

	@Test
	public void testSetScope() {
		final BuildDependency dependency = new BuildDependency();

		dependency.setScopes(null);
		assertNull(dependency.getScopes());

		final Set<String> scopes = new HashSet<String>();
		scopes.add("test");
		dependency.setScopes(scopes);
		assertEquals(dependency.getScopes(), scopes);
	}

	@Test
	public void testSetExtension() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setExtension("Extension");
		assertEquals(dependency.getExtension(), "Extension");

		dependency.setExtension(null);
		assertNull(dependency.getExtension());
	}

	@Test
	public void testSetMatchType() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setMatchType(MatchType.UNMATCHED);
		assertEquals(MatchType.UNMATCHED, dependency.getMatchType());

		dependency.setMatchType(null);
		assertNull(dependency.getMatchType());
	}

	@Test
	public void testSetProjectName() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setProjectName("project");
		assertEquals(dependency.getProjectName(), "project");

		dependency.setProjectName(null);
		assertNull(dependency.getProjectName());
	}

	@Test
	public void testSetVersionName() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setVersionName("version");
		assertEquals(dependency.getVersionName(), "version");

		dependency.setVersionName(null);
		assertNull(dependency.getVersionName());
	}

	@Test
	public void testSetLicenseName() {
		final BuildDependency dependency = new BuildDependency();
		dependency.setLicenseName("license");
		assertEquals(dependency.getLicenseName(), "license");

		dependency.setLicenseName(null);
		assertNull(dependency.getLicenseName());
	}

	@Test
	public void testSetVulnerabilityCounts() {
		final BuildDependency dependency = new BuildDependency();
		final VulnerabilityCounts counts = new VulnerabilityCounts();
		counts.setLow(0);
		counts.setMedium(234);
		counts.setHigh(3);
		dependency.setVulnerabilityCounts(counts);
		assertEquals(dependency.getVulnerabilityCounts().getLow(), 0);
		assertEquals(dependency.getVulnerabilityCounts().getMedium(), 234);
		assertEquals(dependency.getVulnerabilityCounts().getHigh(), 3);

		dependency.setVulnerabilityCounts(null);
		assertNull(dependency.getVulnerabilityCounts());
	}

	@Test
	public void testEquals() {
		final BuildDependency dependency = new BuildDependency();
		final BuildDependency dependency2 = new BuildDependency();
		dependency2.setArtifact("Equal");
		dependency2.setClassifier("Equal");
		dependency2.setExtension("Equal");
		dependency2.setGroup("Equal");
		dependency2.setVersion("Equal");
		dependency2.setMatchType(MatchType.PROJECTFOUND);
		dependency2.setProjectName("Equal");
		dependency2.setVersionName("Equal");
		dependency2.setLicenseName("Equal");
		final VulnerabilityCounts counts2 = new VulnerabilityCounts();
		counts2.setLow(0);
		counts2.setMedium(123);
		counts2.setHigh(456);
		dependency2.setVulnerabilityCounts(counts2);

		final Set<String> scopeList = new HashSet<String>();
		scopeList.add("Equal");
		dependency2.setScopes(scopeList);

		assertTrue(dependency.equals(dependency));

		assertTrue(!dependency.equals(null));
		assertTrue(!dependency.equals(this));
		assertTrue(!dependency.equals(dependency2));

		dependency.setArtifact("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setArtifact("Equal");
		assertTrue(!dependency.equals(dependency2));

		dependency.setClassifier("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setClassifier("Equal");
		assertTrue(!dependency.equals(dependency2));

		dependency.setExtension("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setExtension("Equal");
		assertTrue(!dependency.equals(dependency2));

		dependency.setGroup("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setGroup("Equal");
		assertTrue(!dependency.equals(dependency2));

		final Set<String> scopeList2 = new HashSet<String>();
		scopeList2.add("notEqual");
		dependency.setScopes(scopeList2);
		assertTrue(!dependency.equals(dependency2));

		final Set<String> scopeList3 = new HashSet<String>();
		scopeList3.add("Equal");
		dependency.setScopes(scopeList3);
		assertTrue(!dependency.equals(dependency2));

		dependency.setVersion("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setVersion("Equal");

		dependency.setMatchType(MatchType.UNKNOWNMATCH);
		assertTrue(!dependency.equals(dependency2));
		dependency.setMatchType(MatchType.PROJECTFOUND);
		assertTrue(!dependency.equals(dependency2));

		dependency.setProjectName("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setProjectName("Equal");
		assertTrue(!dependency.equals(dependency2));

		dependency.setVersionName("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setVersionName("Equal");
		assertTrue(!dependency.equals(dependency2));

		dependency.setLicenseName("notEqual");
		assertTrue(!dependency.equals(dependency2));
		dependency.setLicenseName("Equal");
		assertTrue(!dependency.equals(dependency2));

		final VulnerabilityCounts counts = new VulnerabilityCounts();
		counts.setLow(15);
		counts.setMedium(23);
		counts.setHigh(56);

		dependency.setVulnerabilityCounts(counts);
		assertTrue(!dependency.equals(dependency2));
		dependency.setVulnerabilityCounts(counts2);

		assertTrue(dependency.equals(dependency2));
	}

	@Test
	public void testToString() {
		BuildDependency dependency = new BuildDependency();

		dependency = new BuildDependency();
		assertEquals(
				"BuildDependency [group=null, artifact=null, version=null, id=null, classifier=null, scopes=[], extension=null, matchType=UNKNOWNMATCH, projectName=null, versionName=null, licenseName=null, vulnerabilityCounts=VulnerabilityCounts [low=0, medium=0, high=0]]",
				dependency.toString());

		dependency.setArtifact("TEST");
		dependency.setClassifier("TEST");
		dependency.setExtension("TEST");
		dependency.setGroup("TEST");
		dependency.setVersion("TEST");
		dependency.setMatchType(MatchType.PROJECTFOUND);
		dependency.setProjectName("TEST");
		dependency.setVersionName("TEST");
		dependency.setLicenseName("TEST");
		final VulnerabilityCounts counts = new VulnerabilityCounts();
		counts.setLow(20);
		counts.setMedium(30);
		counts.setHigh(40);
		dependency.setVulnerabilityCounts(counts);

		dependency.getScopes().add("TEST");
		assertEquals(
				"BuildDependency [group=TEST, artifact=TEST, version=TEST, id=TEST:TEST:TEST, classifier=TEST, scopes=[TEST], extension=TEST, matchType=PROJECTFOUND, projectName=TEST, versionName=TEST, licenseName=TEST, vulnerabilityCounts=VulnerabilityCounts [low=20, medium=30, high=40]]",
				dependency.toString());
	}

}
