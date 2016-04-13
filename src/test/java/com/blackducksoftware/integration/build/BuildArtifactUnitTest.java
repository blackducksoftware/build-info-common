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

import org.junit.Test;

public class BuildArtifactUnitTest {
	private static final String NEW_TYPE = "NewType";
	private static final String NEW_GROUP = "NewGroup";
	private static final String NEW_ARTIFACT = "New artifact";
	private static final String NEW_ID = "NewGroup:New artifact:new VERSION";
	private static final String NEW_VERSION = "new VERSION";

	@Test
	public void testGetType() {
		final BuildArtifact artifact = new BuildArtifact();
		assertEquals(artifact.getType(), null);
		artifact.setType(NEW_TYPE);
		assertEquals(artifact.getType(), NEW_TYPE);
	}

	@Test
	public void testGetGroup() {
		final BuildArtifact artifact = new BuildArtifact();
		assertEquals(artifact.getGroup(), null);
		artifact.setGroup(NEW_GROUP);
		assertEquals(artifact.getGroup(), NEW_GROUP);
	}

	@Test
	public void testGetArtifact() {
		final BuildArtifact artifact = new BuildArtifact();
		assertEquals(artifact.getArtifact(), null);
		artifact.setArtifact(NEW_ARTIFACT);
		assertEquals(artifact.getArtifact(), NEW_ARTIFACT);
	}

	@Test
	public void testGetId() {
		final BuildArtifact artifact = new BuildArtifact();
		assertEquals(artifact.getId(), null);
		artifact.setGroup("lotsOfMonkeys");
		artifact.setArtifact("onlyAFewMonkeys");
		artifact.setVersion("aVersionForMonkeys");
		assertEquals(artifact.getId(), "lotsOfMonkeys:onlyAFewMonkeys:aVersionForMonkeys");
	}

	@Test
	public void testGetVersion() {
		final BuildArtifact artifact = new BuildArtifact();
		assertEquals(artifact.getVersion(), null);
		artifact.setVersion(NEW_VERSION);
		assertEquals(artifact.getVersion(), NEW_VERSION);
	}

	@Test
	public void testToString() {
		final BuildArtifact artifact = new BuildArtifact();
		assertEquals("BuildArtifact [type=null, group=null, artifact=null, version=null, id=null]",
				artifact.toString());
		artifact.setArtifact(NEW_ARTIFACT);
		artifact.setGroup(NEW_GROUP);
		artifact.setType(NEW_TYPE);
		artifact.setVersion(NEW_VERSION);
		assertEquals(artifact.toString(), "BuildArtifact [type=" + NEW_TYPE + ", group=" + NEW_GROUP + ", artifact="
				+ NEW_ARTIFACT + ", version=" + NEW_VERSION + ", id=" + NEW_ID + "]");
	}

}
