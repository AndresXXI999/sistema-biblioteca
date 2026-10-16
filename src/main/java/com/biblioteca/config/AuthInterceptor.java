package com.biblioteca.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// false: no crear una sesion nueva para quien no ha iniciado sesion
		HttpSession session = request.getSession(false);

		if (session == null || session.getAttribute("usuarioId") == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}

		String rol = (String) session.getAttribute("rol");
		String uri = request.getRequestURI();

		if (uri.startsWith("/admin/") && !"ADMIN".equals(rol)) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}

		if (uri.startsWith("/socio/") && !"SOCIO".equals(rol)) {
			response.sendRedirect(request.getContextPath() + "/");
			return false;
		}

		return true;
	}

}
