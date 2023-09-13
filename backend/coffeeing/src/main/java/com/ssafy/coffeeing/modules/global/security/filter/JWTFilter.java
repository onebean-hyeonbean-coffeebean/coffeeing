package com.ssafy.coffeeing.modules.global.security.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ssafy.coffeeing.modules.auth.util.JWTUtils;
import com.ssafy.coffeeing.modules.global.exception.BusinessException;
import com.ssafy.coffeeing.modules.global.exception.info.AuthErrorInfo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JWTFilter extends OncePerRequestFilter {

	private final String HEADER_NAME;
	private final String GRANT_TYPE;
	private final JWTUtils jwtUtils;
	private static final List<String> PERMIT_ALL_PATHS = List.of("/auth");

	public JWTFilter(@Value("${jwt.header}") String header,
		@Value("${jwt.grant-type}") String tokenType, @Autowired JWTUtils jwtUtils) {

		this.HEADER_NAME = header;
		this.GRANT_TYPE = tokenType;
		this.jwtUtils = jwtUtils;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws
		ServletException,
		IOException {

		boolean skipCondition = isPreFlightRequest(request) || isPermitAllRequest(request);

		if(!skipCondition) {
			String accessToken = resolveToken(request);
			if (!jwtUtils.validateToken(accessToken)) {
				throw new BusinessException(AuthErrorInfo.ACCESS_TOKEN_EXPIRED);
			}

			if (accessToken != null && jwtUtils.validateToken(accessToken)) {
				Authentication authentication = jwtUtils.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				log.debug("Not found member access token. Request uri: {}", request.getRequestURI());
			}
		}
		filterChain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {
		String accessToken = request.getHeader(HEADER_NAME);
		if (StringUtils.hasText(accessToken) && accessToken.startsWith(GRANT_TYPE)) {
			return accessToken.substring(7);
		}
		return null;
	}

	private boolean isPreFlightRequest(HttpServletRequest request) {
		return (HttpMethod.OPTIONS.matches(request.getMethod()) &&
			request.getHeader(HttpHeaders.ORIGIN) != null &&
			request.getHeader(HttpHeaders.ACCESS_CONTROL_REQUEST_METHOD) != null);
	}

	private boolean isPermitAllRequest(HttpServletRequest request) {
		String requestUri = request.getRequestURI();
		String url = PERMIT_ALL_PATHS.stream().filter(requestUri::contains).findFirst().orElse(null);
		return url != null;
	}
}