package com.web.assistant.security;

import com.web.assistant.exception.CustomException;
import com.web.assistant.exception.SingInException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import static com.web.assistant.security.JwtTokenProvider.ACCESS_TOKEN;
@AllArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(final HttpServletRequest req, final HttpServletResponse res, final FilterChain filterChain) throws ServletException, IOException {
        final Map<String, String> tokens = jwtTokenProvider.resolveToken(req);
        try {
            if (tokens != null && tokens.size() == 2 && jwtTokenProvider.validateTokens(tokens, req, res)) {
                if(req.getServletPath().equals("/users/signin")){
                    throw new SingInException("You already logged in", HttpStatus.METHOD_NOT_ALLOWED);
                }
                final Authentication auth = jwtTokenProvider.getAuthentication(Arrays.stream(req.getCookies())
                        .filter(cookie -> cookie.getName().equals(ACCESS_TOKEN))
                        .map(Cookie::getValue).findFirst().orElseThrow(), jwtTokenProvider.accessSecretKey);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }

        }
        catch (final SingInException ex){
            res.sendError(ex.getHttpStatus().value(), ex.getMessage());
        }
        catch (final CustomException ex) {
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(req, res);
    }
}
