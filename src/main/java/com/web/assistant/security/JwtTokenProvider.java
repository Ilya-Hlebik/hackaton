package com.web.assistant.security;

import com.web.assistant.enumerated.Role;
import com.web.assistant.exception.CustomException;
import com.web.assistant.repository.BlackListRepository;
import com.web.assistant.repository.UserRepository;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {

    public static final String ACCESS_TOKEN = "access_token";
    public static final String REFRESH_TOKEN = "refresh_token";
    private final MyUserDetails myUserDetails;
    private final UserRepository userRepository;
    private final BlackListRepository blackListRepository;
    @Value("${security.jwt.token.access.secret-key}")
    public String accessSecretKey;
    @Value("${security.jwt.token.refresh.secret-key}")
    public String refreshSecretKey;
    @Value("${security.jwt.access.token.expire-length}")
    private long accessTokenValidityInMilliseconds;
    @Value("${security.jwt.refresh.token.expire-length}")
    private long refreshTokenValidityInMilliseconds;
    @Value("${server.servlet.context-path}")
    private String contextPath;

    public JwtTokenProvider(final MyUserDetails myUserDetails, final UserRepository userRepository, final BlackListRepository blackListRepository) {
        this.myUserDetails = myUserDetails;
        this.userRepository = userRepository;
        this.blackListRepository = blackListRepository;
    }

    @PostConstruct
    protected void init() {
        accessSecretKey = Base64.getEncoder().encodeToString(accessSecretKey.getBytes());
        refreshSecretKey = Base64.getEncoder().encodeToString(refreshSecretKey.getBytes());
    }


    public Map<String, String> createTokens(final String username, final List<Role> roles, final HttpServletResponse httpServletResponse) {

        final Claims claims = Jwts.claims().setSubject(username);
        claims.put("auth", roles.stream().map(role -> new SimpleGrantedAuthority(role.getAuthority())).collect(Collectors.toList()));

        final Date now = new Date();
        final Date accessTokenValidity = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        final Date refreshTokenValidity = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
        final Map<String, String> tokens = new HashMap<>();
        tokens.put(ACCESS_TOKEN, addTokenToCookie(httpServletResponse, claims, accessTokenValidity, ACCESS_TOKEN, accessSecretKey));
        tokens.put(REFRESH_TOKEN, addTokenToCookie(httpServletResponse, claims, refreshTokenValidity, REFRESH_TOKEN, refreshSecretKey));
        return tokens;
    }

    private String addTokenToCookie(final HttpServletResponse httpServletResponse, final Claims claims, final Date validity, final String tokenName,
                                    final String secretKey) {
        final String JWT = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        final Cookie cookie = new Cookie(tokenName, JWT);
        cookie.setPath(contextPath);
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        return JWT;
    }

    public Authentication getAuthentication(final String token, final String secretKey) {
        final UserDetails userDetails = myUserDetails.loadUserByUsername(getUsername(token, secretKey));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(final String token, final String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public Map<String, String> resolveToken(final HttpServletRequest req) {
        if (req.getCookies() == null)
            return null;
        return Arrays.stream(req.getCookies()).filter(e -> e.getName().equals(ACCESS_TOKEN) ||
                e.getName().equals(REFRESH_TOKEN)).collect(Collectors.toMap(Cookie::getName, Cookie::getValue));
    }

    public boolean validateTokens(final Map<String, String> token, final HttpServletRequest req, final HttpServletResponse res) {
        try {
            Jwts.parser().setSigningKey(accessSecretKey).parseClaimsJws(token.get(ACCESS_TOKEN));
            return true;
        } catch (final ExpiredJwtException e) {
            try {
                Jwts.parser().setSigningKey(refreshSecretKey).parseClaimsJws(token.get(REFRESH_TOKEN));
                final Authentication authentication = getAuthentication(token.get(REFRESH_TOKEN), refreshSecretKey);
                final User user = (User) authentication.getPrincipal();
                final Map<String, String> tokens = createTokens(user.getUsername(), userRepository.findByUsername(user.getUsername()).getRoles(), res);
                Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals(ACCESS_TOKEN))
                        .forEach(cookie -> cookie.setValue(tokens.get(ACCESS_TOKEN)));
                Arrays.stream(req.getCookies()).filter(cookie -> cookie.getName().equals(REFRESH_TOKEN))
                        .forEach(cookie -> cookie.setValue(tokens.get(REFRESH_TOKEN)));
                return true;
            } catch (final JwtException | IllegalArgumentException ex) {
                throw new CustomException("Expired or invalid JWT refresh token", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (final JwtException | IllegalArgumentException e) {
            throw new CustomException("invalid JWT tokens", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean inBLackList(final Map<String, String> token) {
        return token != null && blackListRepository.findById(token.get(REFRESH_TOKEN)).isPresent();
    }
}
