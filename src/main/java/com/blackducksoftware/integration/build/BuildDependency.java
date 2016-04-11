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

import java.util.HashSet;
import java.util.Set;

public class BuildDependency {
	public static final String MATCH_TYPE = "matchType";
	public static final String PROJECT_NAME = "projectName";
	public static final String VERSION_NAME = "versionName";
	public static final String LICENSE_NAME = "licenseName";
	public static final String VULNERABILITY_COUNTS = "vulnerabilityCounts";
	public static final String LOW_VULNERABILITY = "low";
	public static final String MEDIUM_VULNERABILITY = "medium";
	public static final String HIGH_VULNERABILITY = "high";

	private String group;
	private String artifact;
	private String version;
	private String classifier;
	private Set<String> scopes = new HashSet<String>();;
	private String extension;
	private MatchType matchType = MatchType.UNKNOWNMATCH;
	private String projectName;
	private String versionName;
	private String licenseName;
	private VulnerabilityCounts vulnerabilityCounts = new VulnerabilityCounts();

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BuildDependency [group=");
		builder.append(group);
		builder.append(", artifact=");
		builder.append(artifact);
		builder.append(", version=");
		builder.append(version);
		builder.append(", classifier=");
		builder.append(classifier);
		builder.append(", scopes=");
		builder.append(scopes);
		builder.append(", extension=");
		builder.append(extension);
		builder.append(", matchType=");
		builder.append(matchType);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", versionName=");
		builder.append(versionName);
		builder.append(", licenseName=");
		builder.append(licenseName);
		builder.append(", vulnerabilityCounts=");
		builder.append(vulnerabilityCounts);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artifact == null) ? 0 : artifact.hashCode());
		result = prime * result + ((classifier == null) ? 0 : classifier.hashCode());
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((licenseName == null) ? 0 : licenseName.hashCode());
		result = prime * result + ((matchType == null) ? 0 : matchType.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((scopes == null) ? 0 : scopes.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
		result = prime * result + ((versionName == null) ? 0 : versionName.hashCode());
		result = prime * result + ((vulnerabilityCounts == null) ? 0 : vulnerabilityCounts.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final BuildDependency other = (BuildDependency) obj;
		if (artifact == null) {
			if (other.artifact != null) {
				return false;
			}
		} else if (!artifact.equals(other.artifact)) {
			return false;
		}
		if (classifier == null) {
			if (other.classifier != null) {
				return false;
			}
		} else if (!classifier.equals(other.classifier)) {
			return false;
		}
		if (extension == null) {
			if (other.extension != null) {
				return false;
			}
		} else if (!extension.equals(other.extension)) {
			return false;
		}
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (licenseName == null) {
			if (other.licenseName != null) {
				return false;
			}
		} else if (!licenseName.equals(other.licenseName)) {
			return false;
		}
		if (matchType != other.matchType) {
			return false;
		}
		if (projectName == null) {
			if (other.projectName != null) {
				return false;
			}
		} else if (!projectName.equals(other.projectName)) {
			return false;
		}
		if (scopes == null) {
			if (other.scopes != null) {
				return false;
			}
		} else if (!scopes.equals(other.scopes)) {
			return false;
		}
		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		if (versionName == null) {
			if (other.versionName != null) {
				return false;
			}
		} else if (!versionName.equals(other.versionName)) {
			return false;
		}
		if (vulnerabilityCounts == null) {
			if (other.vulnerabilityCounts != null) {
				return false;
			}
		} else if (!vulnerabilityCounts.equals(other.vulnerabilityCounts)) {
			return false;
		}
		return true;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(final String group) {
		this.group = group;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(final String artifact) {
		this.artifact = artifact;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public String getClassifier() {
		return classifier;
	}

	public void setClassifier(final String classifier) {
		this.classifier = classifier;
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public void setScopes(final Set<String> scopes) {
		this.scopes = scopes;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public void setMatchType(final MatchType matchType) {
		this.matchType = matchType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(final String versionName) {
		this.versionName = versionName;
	}

	public String getLicenseName() {
		return licenseName;
	}

	public void setLicenseName(final String licenseName) {
		this.licenseName = licenseName;
	}

	public VulnerabilityCounts getVulnerabilityCounts() {
		return vulnerabilityCounts;
	}

	public void setVulnerabilityCounts(final VulnerabilityCounts vulnerabilityCounts) {
		this.vulnerabilityCounts = vulnerabilityCounts;
	}

}
