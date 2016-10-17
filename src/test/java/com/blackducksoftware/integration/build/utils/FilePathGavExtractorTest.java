package com.blackducksoftware.integration.build.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.blackducksoftware.integration.build.Gav;

public class FilePathGavExtractorTest {

	private final String TEST_MAVEN_PATH_1 = "/Users/janderson/.m2/repository/com/blackducksoftware/bdio/bdio/2.0.1/bdio-2.0.1.jar";
	private final String TEST_MAVEN_PATH_2 = "/Users/janderson/.m2/repository/com/fasterxml/jackson/datatype/jackson-datatype-guava/2.3.3/jackson-datatype-guava-2.3.3.jar";
	private final String LOCAL_MAVEN_REPO_PATH_1 = "/Users/janderson/.m2/repository";
	private final String LOCAL_MAVEN_REPO_PATH_2 = "/Users/janderson/.m2/repository/";
	private final String TEST_GRADLE_PATH_1 = "/Users/janderson/.gradle/caches/modules-2/files-2.1/com.blackducksoftware.integration/hub-common/1.4.5/1fe0cd8284e3e6654fa0c4554f14ab6151b416b5/hub-common-1.4.5.jar";
	private final String TEST_GRADLE_PATH_2 = "/Users/janderson/.gradle/caches/modules-2/files-2.1/joda-time/joda-time/2.3/56498efd17752898cfcc3868c1b6211a07b12b8f/joda-time-2.3.jar";

	@Test
	public void testExtractingMavenGav() {

		final FilePathGavExtractor extractor = new FilePathGavExtractor();
		assertNotNull(extractor);

		final Gav gav1 = extractor.getMavenPathGav(TEST_MAVEN_PATH_1, LOCAL_MAVEN_REPO_PATH_1);
		final Gav gav2 = extractor.getMavenPathGav(TEST_MAVEN_PATH_1, LOCAL_MAVEN_REPO_PATH_2);
		assertEquals(TEST_MAVEN_PATH_1 + " and " + LOCAL_MAVEN_REPO_PATH_1 + " failed for group ID",
				"com.blackducksoftware.bdio", gav1.getGroupId());
		assertEquals(TEST_MAVEN_PATH_1 + " and " + LOCAL_MAVEN_REPO_PATH_2 + " failed for group ID",
				"com.blackducksoftware.bdio", gav2.getGroupId());
		assertEquals(TEST_MAVEN_PATH_1 + " and " + LOCAL_MAVEN_REPO_PATH_1 + " failed for artifact ID", "bdio",
				gav1.getArtifactId());
		assertEquals(TEST_MAVEN_PATH_1 + " and " + LOCAL_MAVEN_REPO_PATH_2 + " failed for artifact ID", "bdio",
				gav2.getArtifactId());
		assertEquals(TEST_MAVEN_PATH_1 + " and " + LOCAL_MAVEN_REPO_PATH_1 + " failed for version", "2.0.1",
				gav1.getVersion());
		assertEquals(TEST_MAVEN_PATH_1 + " and " + LOCAL_MAVEN_REPO_PATH_2 + " failed for version", "2.0.1",
				gav2.getVersion());

		final Gav gav3 = extractor.getMavenPathGav(TEST_MAVEN_PATH_2, LOCAL_MAVEN_REPO_PATH_1);
		final Gav gav4 = extractor.getMavenPathGav(TEST_MAVEN_PATH_2, LOCAL_MAVEN_REPO_PATH_2);
		assertEquals(
				"maven gav extractor failed for group ID of " + TEST_MAVEN_PATH_2 + " and " + LOCAL_MAVEN_REPO_PATH_1,
				"com.fasterxml.jackson.datatype", gav3.getGroupId());
		assertEquals(
				"maven gav extractor failed for group ID of " + TEST_MAVEN_PATH_2 + " and " + LOCAL_MAVEN_REPO_PATH_2,
				"com.fasterxml.jackson.datatype", gav4.getGroupId());
		assertEquals("maven gav extractor failed for artifact ID of " + TEST_MAVEN_PATH_2 + " and "
				+ LOCAL_MAVEN_REPO_PATH_1, "jackson-datatype-guava", gav3.getArtifactId());
		assertEquals("maven gav extractor failed for artifact ID of " + TEST_MAVEN_PATH_2 + " and "
				+ LOCAL_MAVEN_REPO_PATH_2, "jackson-datatype-guava", gav4.getArtifactId());
		assertEquals(
				"maven gav extractor failed for version of " + TEST_MAVEN_PATH_2 + " and " + LOCAL_MAVEN_REPO_PATH_1,
				"2.3.3", gav3.getVersion());
		assertEquals(
				"maven gav extractor failed for version of " + TEST_MAVEN_PATH_2 + " and " + LOCAL_MAVEN_REPO_PATH_2,
				"2.3.3", gav4.getVersion());
	}

	@Test
	public void testExtractingGradleGav() {
		final FilePathGavExtractor extractor = new FilePathGavExtractor();
		assertNotNull(extractor);
		final Gav gav1 = extractor.getGradlePathGav(TEST_GRADLE_PATH_1);
		assertEquals("gradle gav extractor failed for group ID of " + TEST_GRADLE_PATH_1,
				"com.blackducksoftware.integration", gav1.getGroupId());
		assertEquals("gradle gav extractor failed for artifact ID of " + TEST_GRADLE_PATH_1, "hub-common",
				gav1.getArtifactId());
		assertEquals("gradle gav extractor failed for version of " + TEST_GRADLE_PATH_1, "1.4.5", gav1.getVersion());
		final Gav gav2 = extractor.getGradlePathGav(TEST_GRADLE_PATH_2);
		assertEquals("gradle gav extractor failed for group ID of " + TEST_GRADLE_PATH_2, "joda-time",
				gav2.getGroupId());
		assertEquals("gradle gav extractor failed for artifact ID of " + TEST_GRADLE_PATH_2, "joda-time",
				gav2.getArtifactId());
		assertEquals("gradle gav extractor failed for version of " + TEST_GRADLE_PATH_2, "2.3", gav2.getVersion());
	}

	@Test
	public void testExtractingFromInvalidFilepaths() {
		final FilePathGavExtractor extractor = new FilePathGavExtractor();
		assertNotNull(extractor);
		final Gav gav1 = extractor.getGradlePathGav("");
		assertNull(gav1);
		final Gav gav2 = extractor.getGradlePathGav("less/than/5/segments");
		assertNull(gav2);
		final Gav gav3 = extractor.getMavenPathGav("", LOCAL_MAVEN_REPO_PATH_1);
		assertNull(gav3);
		final Gav gav4 = extractor.getMavenPathGav(LOCAL_MAVEN_REPO_PATH_2 + "/invalid", LOCAL_MAVEN_REPO_PATH_2);
		assertNull(gav4);
	}

	@Test
	public void testNullArgumentHandling() {
		final FilePathGavExtractor extractor = new FilePathGavExtractor();
		assertNotNull(extractor);
		final Gav null1 = extractor.getGradlePathGav(null);
		assertNull(null1);
		final Gav null2 = extractor.getMavenPathGav(TEST_MAVEN_PATH_1, null);
		assertNull(null2);
		final Gav null3 = extractor.getMavenPathGav(null, LOCAL_MAVEN_REPO_PATH_1);
		assertNull(null3);
		final Gav null4 = extractor.getMavenPathGav(null, null);
		assertNull(null4);
	}

}
