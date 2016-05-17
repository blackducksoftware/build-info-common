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

import java.io.Serializable;

public class BuildArtifact implements Serializable {
	private static final long serialVersionUID = -7222217482933769480L;

	private String type = null;
	private String group = null;
	private String artifact = null;
	private String version = null;
	private String id = null;

	private void setId() {
		if (null != group && null != artifact && null != version) {
			id = group + ":" + artifact + ":" + version;
		} else if (null != artifact && null != version) {
			id = artifact + ":" + version;
		}
	}

	public void setGroup(final String group) {
		this.group = group;
		setId();
	}

	public void setArtifact(final String artifact) {
		this.artifact = artifact;
		setId();
	}

	public void setVersion(final String version) {
		this.version = version;
		setId();
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("BuildArtifact [type=");
		builder.append(type);
		builder.append(", group=");
		builder.append(group);
		builder.append(", artifact=");
		builder.append(artifact);
		builder.append(", version=");
		builder.append(version);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((artifact == null) ? 0 : artifact.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		final BuildArtifact other = (BuildArtifact) obj;
		if (artifact == null) {
			if (other.artifact != null) {
				return false;
			}
		} else if (!artifact.equals(other.artifact)) {
			return false;
		}
		if (group == null) {
			if (other.group != null) {
				return false;
			}
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
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

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public String getArtifact() {
		return artifact;
	}

	public String getVersion() {
		return version;
	}

	public String getId() {
		return id;
	}

}
