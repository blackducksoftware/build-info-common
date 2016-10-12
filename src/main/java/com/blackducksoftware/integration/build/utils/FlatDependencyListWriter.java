package com.blackducksoftware.integration.build.utils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import com.blackducksoftware.integration.build.DependencyNode;

public class FlatDependencyListWriter {
	public void write(final OutputStream outputStream, final DependencyNode root) throws IOException {
		final Set<String> gavStrings = new HashSet<>();
		addAllGavs(gavStrings, root);

		final List<String> gavList = new ArrayList<>(gavStrings);
		Collections.sort(gavList);

		for (final String gav : gavList) {
			IOUtils.write(gav, outputStream, "UTF8");
			IOUtils.write("\n", outputStream, "UTF8");
		}
	}

	private void addAllGavs(final Set<String> gavStrings, final DependencyNode node) {
		gavStrings.add(node.getGav().toString());

		for (final DependencyNode child : node.getChildren()) {
			addAllGavs(gavStrings, child);
		}
	}

}
