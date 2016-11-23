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

public class Constants {
    public static final String BDIO_FILE_SUFFIX = "_bdio.jsonld";

    public static final String FLAT_FILE_SUFFIX = "_flat.txt";

    public static final String BDIO_FILE_MEDIA_TYPE = "application/ld+json";

    public static final String CREATE_FLAT_DEPENDENCY_LIST = "createFlatDependencyList";

    public static final String CREATE_FLAT_DEPENDENCY_LIST_STARTING = "Black Duck Hub creating flat dependency list file for: %s starting.";

    public static final String CREATE_FLAT_DEPENDENCY_LIST_ERROR = "Error creating the output file: %s";

    public static final String CREATE_FLAT_DEPENDENCY_LIST_FINISHED = "Black Duck Hub creating flat dependency list file for: %s finished.";

    public static final String CREATE_HUB_OUTPUT = "createHubOutput";

    public static final String CREATE_HUB_OUTPUT_STARTING = "Black Duck Hub creating output file for: %s starting.";

    public static final String CREATE_HUB_OUTPUT_ERROR = "Error creating the output file: %s";

    public static final String CREATE_HUB_OUTPUT_FINISHED = "Black Duck Hub creating output file for: %s finished.";

    public static final String DEPLOY_HUB_OUTPUT = "deployHubOutput";

    public static final String DEPLOY_HUB_OUTPUT_STARTING = "Black Duck Hub deploying: %s starting.";

    public static final String DEPLOY_HUB_OUTPUT_ERROR = "Could not deploy the file to the Hub, check the logs for specific issues: %s";

    public static final String DEPLOY_HUB_OUTPUT_FINISHED = "Black Duck Hub deploying: %s finished.";

    public static final String CHECK_POLICIES = "checkPolicies";

    public static final String CHECK_POLICIES_STARTING = "Black Duck Hub checking policies for: %s starting.";

    public static final String CHECK_POLICIES_ERROR = "Could not check Hub policies, check the logs for specific issues: %s";

    public static final String CHECK_POLICIES_FINISHED = "Black Duck Hub checking policies for: %s finished.";

    public static final String FAILED_TO_CREATE_REPORT = "Could not create Hub Report, check the logs for specific issues: %s";

    public static final String DEPLOY_HUB_OUTPUT_AND_CHECK_POLICIES = "deployHubOutputAndCheckPolicies";

    public static final String DEPLOY_HUB_OUTPUT_AND_CHECK_POLICIES_STARTING = "Black Duck Hub deploying: %s for checking latest policy status starting.";

    public static final String DEPLOY_HUB_OUTPUT_AND_CHECK_POLICIES_FINISHED = "Black Duck Hub deploying: %s for checking latest policy status finished.";

    public static final String UPLOAD_FILE_MESSAGE = "Uploaded the file %s to %s";

    public static final String SCAN_ERROR_MESSAGE = "There was an error waiting for the scans: %s";

}
