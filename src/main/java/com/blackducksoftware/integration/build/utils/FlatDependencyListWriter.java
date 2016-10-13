package com.blackducksoftware.integration.build.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.integration.build.DependencyNode;

public class FlatDependencyListWriter {
	private final Logger logger = LoggerFactory.getLogger(BdioDependencyWriter.class);

	public void write(final File outputDirectory, final String filename, final DependencyNode rootNode)
			throws IOException {
		final Set<String> gavStrings = new HashSet<>();
		addAllGavs(gavStrings, rootNode);
		final List<String> gavList = new ArrayList<>(gavStrings);
		Collections.sort(gavList);

		// if the directory doesn't exist yet, let's create it
		outputDirectory.mkdirs();

		final File file = new File(outputDirectory, filename);
		logger.info(String.format("Generating file: %s", file.getCanonicalPath()));

		try (final OutputStream outputStream = new FileOutputStream(file)) {
			for (final String gav : gavList) {
				IOUtils.write(gav, outputStream, "UTF8");
				IOUtils.write("\n", outputStream, "UTF8");
			}
		}
	}

	private void addAllGavs(final Set<String> gavStrings, final DependencyNode node) {
		gavStrings.add(node.getGav().toString());

		for (final DependencyNode child : node.getChildren()) {
			addAllGavs(gavStrings, child);
		}
	}

}
