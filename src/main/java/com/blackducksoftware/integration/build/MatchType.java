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

import org.apache.commons.lang3.StringUtils;

public enum MatchType {
	/**
	 * This type indicates that the dependency was matched to a specific
	 * version.
	 */
	VERSIONFOUND, /**
					 * This type indicates that the dependency could not be
					 * matched to a specific version, but it was matched to a
					 * project.
					 */
	PROJECTFOUND, /**
					 * This type indicates that the dependency could not be
					 * matched to project or version.
					 */
	UNMATCHED, /**
				 * This type indicates that the match type is unknown.
				 */
	UNKNOWNMATCH;

	public static MatchType getMatchType(final String match) {
		final String cleanedMatch = StringUtils.trimToEmpty(match).toUpperCase();
		try {
			return MatchType.valueOf(cleanedMatch);
		} catch (final IllegalArgumentException e) {
			return MatchType.UNKNOWNMATCH;
		}
	}

}
