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

public class BuildDependencyUnitTest {
	private BuildDependency dependency;

	@Before
	public void testSetup() {
		dependency = new BuildDependency();
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
	public void testHashCode() {
		dependency.setExtension("someString");
		final int prime = 31;
		final int hash = prime * prime * prime * (prime * prime * prime + "someString".hashCode());
		assertEquals(hash, dependency.hashCode());
	}

	@Test
	public void testEquals() {
		final BuildDependency dependency2 = new BuildDependency();
		dependency2.setArtifact("Equal");
		dependency2.setClassifier("Equal");
		dependency2.setExtension("Equal");
		dependency2.setGroup("Equal");

		final ArrayList<String> scopeList = new ArrayList<String>();
		scopeList.add("Equal");
		dependency2.setScope(scopeList);
		dependency2.setVersion("Equal");
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
		assertTrue(dependency.equals(dependency2));
	}

}
