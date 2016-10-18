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

    public static final String DEPLOY_HUB_OUTPUT_AND_CHECK_POLICIES = "deployHubOutputAndCheckPolicies";

    public static final String DEPLOY_HUB_OUTPUT_AND_CHECK_POLICIES_STARTING = "Black Duck Hub deploying: %s for checking latest policy status starting.";

    public static final String DEPLOY_HUB_OUTPUT_AND_CHECK_POLICIES_FINISHED = "Black Duck Hub deploying: %s for checking latest policy status finished.";

}
