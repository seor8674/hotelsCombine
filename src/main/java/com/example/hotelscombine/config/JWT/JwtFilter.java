package com.example.hotelscombine.config.JWT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtFilter extends GenericFilterBean {

    private static final Logger logger= LoggerFactory.getLogger(JwtFilter.class);

    public static final String AUTHORIZATION_HEADER ="Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider){
        this.tokenProvider=tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);
        StringBuffer requestURL = httpServletRequest.getRequestURL();

        if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            logger.debug("Security Context에 '{}' 인증정보를 저장했습니다.",authentication.getName(),requestURL);
        }else{
            logger.debug("유효한 JWT 토큰이 없습니다, uri: {}",requestURL);
        }

        chain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest httpServletRequest){
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")){
            return bearerToken.substring(7);
        }
        return null;

    }
}
