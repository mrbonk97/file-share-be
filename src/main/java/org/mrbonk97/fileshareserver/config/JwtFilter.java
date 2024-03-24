package org.mrbonk97.fileshareserver.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
//import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.model.Account;
import org.mrbonk97.fileshareserver.oauth2.UserPrincipal;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.mrbonk97.fileshareserver.service.RedisService;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final AccountService accountService;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.split(" ")[1].trim();
            String email = jwtUtils.validateTokenAndGetEmail(token);
            Date issuedDate = jwtUtils.validateTokenAndGetIssuedDate(token);

            String _blockedLocalDateTime = redisService.getValue(email);
            if(!_blockedLocalDateTime.equals("false") &&
            issuedDate.before(Date.from(LocalDateTime.parse(_blockedLocalDateTime).atZone(ZoneId.systemDefault()).toInstant())))
            {
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(ErrorCode.BLOCKED_TOKEN.getMessage());
                return;
            }

            Account account = accountService.loadByEmail(email);
            UserPrincipal userPrincipal = UserPrincipal.create(account);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userPrincipal,
                    null,
                    userPrincipal.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request,response);

        } catch (ExpiredJwtException e) {
            log.error("만료된 Jwt 토큰이 들어옴: {}", e.getMessage());
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write(ErrorCode.EXPIRED_TOKEN.getMessage());
        } catch (MalformedJwtException e) {
            log.error("손상된 Jwt 토큰이 들어옴: {}", e.getMessage());
            filterChain.doFilter(request,response);
        } catch (Exception e) {
            log.error("예기치 못한 오류: {}", e.getMessage());
            filterChain.doFilter(request,response);
        }



    }
}
