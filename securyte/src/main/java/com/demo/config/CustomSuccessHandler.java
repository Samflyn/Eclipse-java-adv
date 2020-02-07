package com.demo.config;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
//		boolean isAdmin = false;
//		boolean isUser = false;
//		authentication.getAuthorities().forEach(authority -> {
//			if (authority.getAuthority().equals("ROLE_ADMIN")) {
//				isAdmin = true;
//				break;
//			} else if (authority.getAuthority().equals("ROLE_USER")) {
//				isUser = true;
//				break;
//			}
//		});
		DefaultSavedRequest defaultSavedRequest = (DefaultSavedRequest) request.getSession()
				.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		String url = "";
		if (defaultSavedRequest == null) {
			Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
			for (GrantedAuthority authority : authorities) {
				if (authority.getAuthority().equals("ROLE_ADMIN")) {
					url = "admin/";
					break;
				} else if (authority.getAuthority().equals("ROLE_USER")) {
					url = "user/";
					break;
				}
			}
		} else {
			url = defaultSavedRequest.getRedirectUrl();
		}
		redirectStrategy.sendRedirect(request, response, url);
	}

}