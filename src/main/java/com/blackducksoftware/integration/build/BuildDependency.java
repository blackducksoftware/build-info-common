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

import java.io.Serializable;
import java.util.List;

public class BuildDependency implements Serializable {
	private static final long serialVersionUID = 7110967596618980400L;

	private String group;
	private String artifact;
	private String version;
	private String classifier;
	private List<String> scope;
	private String extension;

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

	public List<String> getScope() {
		return scope;
	}

	public void setScope(final List<String> newScopes) {
		if (newScopes != null) {
			for (String scope : newScopes) {
				if (scope == null) {
					scope = "";
				}

			}
			scope = newScopes;
		}
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(final String extension) {
		this.extension = extension;
	}

	@Override
	public String toString() {
		String scopes = null;
		if (scope != null) {
			for (final String currScope : scope) {
				if (scopes == null) {
					scopes = currScope;
				} else {
					scopes = scopes + ", " + currScope;
				}
			}
		}

		return "BuildDependency [group=" + group + ", artifactId=" + artifact + ", version=" + version + ", classifier="
				+ classifier + ", scopes=" + scopes + ", extension=" + extension + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artifact == null) ? 0 : artifact.hashCode());
		result = prime * result + ((classifier == null) ? 0 : classifier.hashCode());
		result = prime * result + ((extension == null) ? 0 : extension.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		if (scope == null) {
			if (other.scope != null) {
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

		if (version == null) {
			if (other.version != null) {
				return false;
			}
		} else if (!version.equals(other.version)) {
			return false;
		}
		return true;
	}

}
