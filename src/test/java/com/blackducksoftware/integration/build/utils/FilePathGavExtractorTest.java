package com.blackducksoftware.integration.build.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class FilePathGavExtractorTest {
    @Test
    public void testExtractingMavenGav() {
        final FilePathGavExtractor extractor = new FilePathGavExtractor();
        assertNotNull(extractor);
    }

}
