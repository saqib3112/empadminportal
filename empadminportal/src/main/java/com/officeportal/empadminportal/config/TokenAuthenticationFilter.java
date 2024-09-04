package com.officeportal.empadminportal.config;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.officeportal.empadminportal.model.User;
import com.officeportal.empadminportal.service.UserTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private UserTokenService userTokenService;

	public TokenAuthenticationFilter(UserTokenService userTokenService) {
		super();
		this.userTokenService = userTokenService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		var token = this.recoverToken(httpRequest);
		if (token != null) {
			User user = userTokenService.findByToken(token);
			if (user != null
					&& ((httpRequest.getRequestURI().contains("/admin") && user.getRole().getRoleName().equals("ADMIN"))
							|| (httpRequest.getRequestURI().contains("/user")
									&& user.getRole().getRoleName().equals("USER")))) {

				var authentication = new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}

		}
		chain.doFilter(request, response);

	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null)
			return null;
		return authHeader.replace("Bearer ", "");
	}
}