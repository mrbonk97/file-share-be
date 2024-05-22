package org.mrbonk97.fileshareserver.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mrbonk97.fileshareserver.exception.ErrorCode;
import org.mrbonk97.fileshareserver.model.User;
import org.mrbonk97.fileshareserver.service.AccountService;
import org.mrbonk97.fileshareserver.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = header.split(" ")[1].trim();
            Long id = JwtUtils.validateTokenAndGetId(token);
            User user = accountService.loadById(id);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    user,
                    null,
                    user.getAuthorities()
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
