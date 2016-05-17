/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
