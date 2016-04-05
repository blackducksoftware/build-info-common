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

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BuildDependencyPlusUnitTest {
	private BuildDependencyPlus dependency;

	@Before
	public void testSetup() {
		dependency = new BuildDependencyPlus();
	}

	@Test
	public void testSetGroup() {
		dependency.setGroup(null);
		assertNull(dependency.getGroup());
		dependency.setGroup("group");
		assertEquals(dependency.getGroup(), "group");
	}

	@Test
	public void testSetArtifact() {
		dependency.setArtifact(null);
		assertNull(dependency.getArtifact());
		dependency.setArtifact("artifact");
		assertEquals(dependency.getArtifact(), "artifact");
	}

	@Test
	public void testSetVersion() {
		dependency.setVersion(null);
		assertNull(dependency.getVersion());
		dependency.setVersion("version");
		assertEquals(dependency.getVersion(), "version");
	}

	@Test
	public void testSetClassifier() {
		dependency.setClassifier(null);
		assertNull(dependency.getClassifier());

		dependency.setClassifier("Something");
		assertEquals(dependency.getClassifier(), "Something");
	}

	@Test
	public void testSetScope() {
		try {

			dependency.setScope(null);

			assertNull(dependency.getScope());

			final ArrayList<String> scopes = new ArrayList<String>();
			scopes.add("test");
			dependency.setScope(scopes);
			assertEquals(dependency.getScope(), scopes);
		} finally {
			// reset dependency scopes
			dependency.setScope(null);
		}
	}

	@Test
	public void testSetExtension() {
		dependency.setExtension("Extension");
		assertEquals(dependency.getExtension(), "Extension");

		dependency.setExtension(null);
		assertNull(dependency.getExtension());
	}

	@Test
	public void testSetMatchType() {
		dependency.setMatchType(MatchType.UNMATCHED);
		assertEquals(MatchType.UNMATCHED, dependency.getMatchType());

		dependency.setMatchType(null);
		assertNull(dependency.getMatchType());
	}

	@Test
	public void testSetProjectName() {
		dependency.setProjectName("project");
		assertEquals(dependency.getProjectName(), "project");

		dependency.setProjectName(null);
		assertNull(dependency.getProjectName());
	}

	@Test
	public void testSetVersionName() {
		dependency.setVersionName("version");
		assertEquals(dependency.getVersionName(), "version");

		dependency.setVersionName(null);
		assertNull(dependency.getVersionName());
	}

	@Test
	public void testSetLicenseName() {
		dependency.setLicenseName("license");
		assertEquals(dependency.getLicenseName(), "license");

		dependency.setLicenseName(null);
		assertNull(dependency.getLicenseName());
	}

	@Test
	public void testSetVulnerabilityCounts() {
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
		final BuildDependencyPlus dependency2 = new BuildDependencyPlus();
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

		final ArrayList<String> scopeList = new ArrayList<String>();
		scopeList.add("Equal");
		dependency2.setScope(scopeList);

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

		final ArrayList<String> scopeList2 = new ArrayList<String>();
		scopeList2.add("notEqual");
		dependency.setScope(scopeList2);
		assertTrue(!dependency.equals(dependency2));

		scopeList2.remove(0);
		scopeList2.add("Equal");
		dependency.setScope(scopeList2);
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

		dependency = new BuildDependencyPlus();
		assertEquals(
				"BuildDependencyPlus [matchType= UNKNOWNMATCH, group= null, artifactId= null, version= null, classifier= null, scopes= [null], extension= null, projectName= null, versionName= null, licenseName= null, vulnerabilityCounts=VulnerabilityCounts [low=0, medium=0, high=0]",
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

		final ArrayList<String> scopeList = new ArrayList<String>();
		scopeList.add("TEST");
		dependency.setScope(scopeList);
		assertEquals(
				"BuildDependencyPlus [matchType= " + MatchType.PROJECTFOUND
						+ ", group= TEST, artifactId= TEST, version= TEST, classifier= TEST, scopes= [TEST], extension= TEST, projectName= TEST, versionName= TEST, licenseName= TEST, vulnerabilityCounts=VulnerabilityCounts [low=20, medium=30, high=40]",
				dependency.toString());

	}
}
