package com.strandls.landscape.util;

import org.pac4j.core.profile.CommonProfile;

import com.strandls.authentication_utility.util.AuthUtil;

import jakarta.servlet.http.HttpServletRequest;
import net.minidev.json.JSONArray;

public class UserUtil {

	private UserUtil() {
	}

	public static boolean isAdmin(HttpServletRequest request) {
		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		if (profile == null)
			return false;

		JSONArray roles = (JSONArray) profile.getAttribute("roles");
		return roles.contains("ROLE_ADMIN");
	}

}
