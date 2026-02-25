package com.juan.app_security.security;

import com.juan.app_security.services.JwtService;
import com.juan.app_security.services.JwtUserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JwtValidationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final JwtUserDetailService jwtUserDetailService;

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORIZATION_HEADER_BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var requestTokenHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        String jwt = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith(AUTHORIZATION_HEADER_BEARER)) {
            // extraer el Bearer
            jwt = requestTokenHeader.substring(7);
            try {
                username = jwtService.getUsernameFromToken(jwt);
            } catch (IllegalArgumentException e) {
                log.error("No se pudo obtener el token JWT {}", e.getMessage());
                this.setErrorResponse(response, "JWT token invalido");
                return;
            } catch (ExpiredJwtException e) {
                log.warn("El token JWT ha expirado para el usuario: {}", e.getClaims().getSubject());
                this.setErrorResponse(response, "La sesión ha expirado. Por favor, ingrese de nuevo.");
                return;
            } catch (Exception e) {
                log.error("Error procesando el JWT: {}", e.getMessage());
                this.setErrorResponse(response, "Error de autenticación");
                return;
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final var userDetails = jwtUserDetailService.loadUserByUsername(username);

            if (jwtService.validateToken(jwt, userDetails)) {
                var usernameAndPassAuthToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernameAndPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernameAndPassAuthToken);
            }
        }

        filterChain.doFilter(request, response);

    }

    private void setErrorResponse(HttpServletResponse response, String mensaje) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String mensajeErrorJson = String.format("{\"error\": \"Unauthorized\", \"message\": \"%s\"}", mensaje);
        response.getWriter().write(mensajeErrorJson);
    }

}
