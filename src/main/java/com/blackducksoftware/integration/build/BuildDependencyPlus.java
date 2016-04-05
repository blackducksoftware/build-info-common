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

public class BuildDependencyPlus extends BuildDependency {
	private static final long serialVersionUID = -7508946106342665506L;

	public static final String MATCH_TYPE = "matchType";
	public static final String PROJECT_NAME = "projectName";
	public static final String VERSION_NAME = "versionName";
	public static final String LICENSE_NAME = "licenseName";
	public static final String VULNERABILITY_COUNTS = "vulnerabilityCounts";
	public static final String LOW_VULNERABILITY = "low";
	public static final String MEDIUM_VULNERABILITY = "medium";
	public static final String HIGH_VULNERABILITY = "high";

	private MatchType matchType = MatchType.UNKNOWNMATCH;
	private String projectName;
	private String versionName;
	private String licenseName;
	private VulnerabilityCounts vulnerabilityCounts = new VulnerabilityCounts();

	public BuildDependencyPlus() {
		this(null);
	}

	public BuildDependencyPlus(final BuildDependency dependency) {
		if (dependency != null) {
			if (dependency.getGroup() != null) {
				setGroup(dependency.getGroup());
			}
			if (dependency.getArtifact() != null) {
				setArtifact(dependency.getArtifact());
			}
			if (dependency.getVersion() != null) {
				setVersion(dependency.getVersion());
			}
			if (dependency.getClassifier() != null) {
				setClassifier(dependency.getClassifier());
			}
			if (dependency.getScope() != null) {
				setScope(dependency.getScope());
			}
			if (dependency.getExtension() != null) {
				setExtension(dependency.getExtension());
			}
		}
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

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BuildDependencyPlus [");
		builder.append("matchType= " + getMatchType() + ", ");
		builder.append("group= " + getGroup() + ", ");
		builder.append("artifactId= " + getArtifact() + ", ");
		builder.append("version= " + getVersion() + ", ");
		builder.append("classifier= " + getClassifier() + ", ");
		builder.append("scopes= [");
		String scopes = null;
		if (getScope() != null) {
			for (final String currScope : getScope()) {
				if (scopes == null) {
					scopes = currScope;
				} else {
					scopes = scopes + ", " + currScope;
				}
			}
		} else {
			scopes = "null";
		}
		builder.append(scopes + "], ");
		builder.append("extension= " + getExtension() + ", ");
		builder.append("projectName= " + projectName + ", ");
		builder.append("versionName= " + versionName + ", ");
		builder.append("licenseName= " + licenseName + ", ");
		if (vulnerabilityCounts == null) {
			builder.append("vulnerabilityCounts= null");
		} else {
			builder.append("vulnerabilityCounts=" + vulnerabilityCounts.toString());
		}
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getArtifact() == null) ? 0 : getArtifact().hashCode());
		result = prime * result + ((getClassifier() == null) ? 0 : getClassifier().hashCode());
		result = prime * result + ((getExtension() == null) ? 0 : getExtension().hashCode());
		result = prime * result + ((getGroup() == null) ? 0 : getGroup().hashCode());
		result = prime * result + ((getScope() == null) ? 0 : getScope().hashCode());
		result = prime * result + ((getVersion() == null) ? 0 : getVersion().hashCode());
		result = prime * result + ((matchType == null) ? 0 : matchType.hashCode());
		result = prime * result + ((projectName == null) ? 0 : projectName.hashCode());
		result = prime * result + ((versionName == null) ? 0 : versionName.hashCode());
		result = prime * result + ((licenseName == null) ? 0 : licenseName.hashCode());
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
		final BuildDependencyPlus other = (BuildDependencyPlus) obj;
		if (getArtifact() == null) {
			if (other.getArtifact() != null) {
				return false;
			}
		} else if (!getArtifact().equals(other.getArtifact())) {
			return false;
		}
		if (getClassifier() == null) {
			if (other.getClassifier() != null) {
				return false;
			}
		} else if (!getClassifier().equals(other.getClassifier())) {
			return false;
		}
		if (getExtension() == null) {
			if (other.getExtension() != null) {
				return false;
			}
		} else if (!getExtension().equals(other.getExtension())) {
			return false;
		}
		if (getGroup() == null) {
			if (other.getGroup() != null) {
				return false;
			}
		} else if (!getGroup().equals(other.getGroup())) {
			return false;
		}
		if (getScope() == null) {
			if (other.getScope() != null) {
				return false;
			}
		} else {
			if (other.getScope() == null) {
				return false;
			}

			if (other.getScope() == null) {
				return false;
			}
			if (getScope() != null) {
				if (getScope().size() != other.getScope().size()) {
					return false;
				} else {
					if (getScope().size() > 0) {
						for (final String currScope : getScope()) {
							// contains method on the List was causing issues
							boolean found = false;
							for (final String otherCurrScope : other.getScope()) {
								if (otherCurrScope.equals(currScope)) {
									found = true;
									break;
								}
							}
							if (!found) {
								return false;
							}
						}
					}
				}
			}
		}
		if (matchType == null) {
			if (other.matchType != null) {
				return false;
			}
		} else if (!matchType.equals(other.matchType)) {
			return false;
		}

		if (projectName == null) {
			if (other.projectName != null) {
				return false;
			}
		} else if (!projectName.equals(other.projectName)) {
			return false;
		}
		if (versionName == null) {
			if (other.versionName != null) {
				return false;
			}
		} else if (!versionName.equals(other.versionName)) {
			return false;
		}
		if (licenseName == null) {
			if (other.licenseName != null) {
				return false;
			}
		} else if (!licenseName.equals(other.licenseName)) {
			return false;
		}
		if (vulnerabilityCounts == null) {
			if (other.vulnerabilityCounts != null) {
				return false;
			}
		} else {
			if (other.vulnerabilityCounts == null) {
				return false;
			}
			if (!vulnerabilityCounts.equals(other.vulnerabilityCounts)) {
				return false;
			}
		}

		return true;
	}

}
