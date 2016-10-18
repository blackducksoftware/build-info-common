package com.blackducksoftware.integration.build.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.blackducksoftware.integration.build.DependencyNode;
import com.blackducksoftware.integration.build.bdio.BdioConverter;
import com.blackducksoftware.integration.build.bdio.CommonBomFormatter;

public class BdioDependencyWriter {
    private final Logger logger = LoggerFactory.getLogger(BdioDependencyWriter.class);

    public void write(final File outputDirectory, final String filename, final String hubProjectName,
            final DependencyNode rootNode) throws IOException {
        final BdioConverter bdioConverter = new BdioConverter();
        final CommonBomFormatter commonBomFormatter = new CommonBomFormatter(bdioConverter);

        // if the directory doesn't exist yet, let's create it
        outputDirectory.mkdirs();

        final File file = new File(outputDirectory, filename);
        logger.info(String.format("Generating file: %s", file.getCanonicalPath()));

        try (final OutputStream outputStream = new FileOutputStream(file)) {
            commonBomFormatter.writeProject(outputStream, hubProjectName, rootNode);
        }
    }

}
