package com.blackducksoftware.integration.build.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class MavenExtClasspathBuilder {
	private static final String M2_REPO = "/Users/ekerwin/.m2/repository";

	public static void main(final String[] args) throws IOException, InterruptedException {
		final List<String> command = new ArrayList<>();
		command.add("mvn");
		command.add("clean");
		command.add("compile");
		command.add("-DskipTests");

		final List<String> dependencies = getMaven_3_1_Dependencies();
		final String mavenExtClasspath = "-Dmaven.ext.class.path=" + StringUtils.join(dependencies, ":");
		command.add(mavenExtClasspath);

		System.out.println(StringUtils.join(command, " "));
	}

	private static List<String> getMaven_3_0_Dependencies() {
		final List<String> dependencies = new ArrayList<>();

		dependencies.add(M2_REPO
				+ "/com/blackducksoftware/integration/build-info-recorder-mvn30/2.0.3-SNAPSHOT/build-info-recorder-mvn30-2.0.3-SNAPSHOT.jar");
		dependencies.add(M2_REPO
				+ "/com/blackducksoftware/integration/build-info-common/2.0.3-SNAPSHOT/build-info-common-2.0.3-SNAPSHOT.jar");
		dependencies.add(M2_REPO + "/com/google/code/gson/gson/2.2.4/gson-2.2.4.jar");
		dependencies.add(M2_REPO + "/com/blackducksoftware/bdio/bdio/2.0.0-SNAPSHOT/bdio-2.0.0-SNAPSHOT.jar");
		dependencies.add(M2_REPO + "/io/reactivex/rxjava/1.0.17/rxjava-1.0.17.jar");
		dependencies.add(M2_REPO + "/com/fasterxml/jackson/core/jackson-databind/2.3.3/jackson-databind-2.3.3.jar");
		dependencies.add(M2_REPO + "/com/fasterxml/jackson/core/jackson-core/2.3.3/jackson-core-2.3.3.jar");
		dependencies
				.add(M2_REPO + "/com/fasterxml/jackson/core/jackson-annotations/2.3.0/jackson-annotations-2.3.0.jar");
		dependencies.add(M2_REPO + "/joda-time/joda-time/2.1/joda-time-2.1.jar");
		dependencies.add(M2_REPO + "/org/slf4j/slf4j-api/1.7.5/slf4j-api-1.7.5.jar");
		dependencies.add(M2_REPO + "/org/slf4j/slf4j-simple/1.7.7/slf4j-simple-1.7.7.jar");
		dependencies.add(M2_REPO + "/org/apache/commons/commons-lang3/3.4/commons-lang3-3.4.jar");
		dependencies.add(M2_REPO + "/com/google/guava/guava/15.0/guava-15.0.jar");

		return dependencies;
	}

	private static List<String> getMaven_3_1_Dependencies() {
		final List<String> dependencies = new ArrayList<>();

		dependencies.add(M2_REPO
				+ "/com/blackducksoftware/integration/build-info-recorder-mvn31/2.0.3-SNAPSHOT/build-info-recorder-mvn31-2.0.3-SNAPSHOT.jar");
		dependencies.add(M2_REPO
				+ "/com/blackducksoftware/integration/build-info-common/2.0.3-SNAPSHOT/build-info-common-2.0.3-SNAPSHOT.jar");
		dependencies.add(M2_REPO + "/com/google/code/gson/gson/2.2.4/gson-2.2.4.jar");
		dependencies.add(M2_REPO + "/com/blackducksoftware/bdio/bdio/2.0.0-SNAPSHOT/bdio-2.0.0-SNAPSHOT.jar");
		dependencies.add(M2_REPO + "/io/reactivex/rxjava/1.0.17/rxjava-1.0.17.jar");
		dependencies.add(M2_REPO + "/com/fasterxml/jackson/core/jackson-databind/2.3.3/jackson-databind-2.3.3.jar");
		dependencies.add(M2_REPO + "/com/fasterxml/jackson/core/jackson-core/2.3.3/jackson-core-2.3.3.jar");
		dependencies
				.add(M2_REPO + "/com/fasterxml/jackson/core/jackson-annotations/2.3.0/jackson-annotations-2.3.0.jar");
		dependencies.add(M2_REPO + "/joda-time/joda-time/2.1/joda-time-2.1.jar");

		return dependencies;
	}

}
