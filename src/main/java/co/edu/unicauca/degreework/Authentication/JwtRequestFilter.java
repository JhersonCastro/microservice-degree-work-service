package co.edu.unicauca.degreework.Authentication;

import co.edu.unicauca.degreework.Utilities.Logger;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final ThreadLocal<Long> currentAccountId = new ThreadLocal<>();
    private static final ThreadLocal<String> currentRoles = new ThreadLocal<>();

    /**
     * Filters incoming HTTP requests to extract account ID and roles from headers
     * @param request HTTP servlet request
     * @param response HTTP servlet response
     * @param filterChain Filter chain for request processing
     * @throws ServletException If servlet error occurs
     * @throws IOException If I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        Logger.info(getClass(), "Iniciando filtro JwtRequestFilter para: " + path);

        try {
            String accountIdHeader = request.getHeader("X-Account-Id");
            String rolesHeader = request.getHeader("X-User-Roles");

            Logger.info(getClass(), "Headers recibidos: X-Account-Id=" + accountIdHeader + ", X-User-Roles=" + rolesHeader);

            // Process account ID header
            if (accountIdHeader != null && !accountIdHeader.isBlank()) {
                try {
                    Long accountId = Long.parseLong(accountIdHeader);
                    currentAccountId.set(accountId);
                } catch (NumberFormatException e) {
                    Logger.error(getClass(), "Formato inválido de X-Account-Id: " + accountIdHeader);
                }
            }

            // Process roles header and set up Spring Security context
            if (rolesHeader != null && !rolesHeader.isBlank()) {
                currentRoles.set(rolesHeader);

                // Convert roles to Spring Security authorities (requires "ROLE_" prefix)
                List<SimpleGrantedAuthority> authorities = Arrays.stream(rolesHeader.split(","))
                        .map(String::trim)
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                        .collect(Collectors.toList());

                // Create simulated authentication
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(accountIdHeader, null, authorities);

                // Register in security context
                SecurityContextHolder.getContext().setAuthentication(authentication);

                Logger.info(getClass(), "Usuario autenticado con roles: " + authorities);
            }

            // Continue normal flow (proceeds to controller)
            filterChain.doFilter(request, response);

            Logger.info(getClass(), "El flujo regresó del controlador sin errores.");

        } finally {
            // Cleanup after processing request
            SecurityContextHolder.clearContext();
            currentAccountId.remove();
            currentRoles.remove();
            Logger.info(getClass(), "ThreadLocal y contexto de seguridad limpiados.");
        }
    }

    /**
     * Gets current account ID from ThreadLocal storage
     * @return Current account ID or null if not set
     */
    public static Long getCurrentAccountId() {
        return currentAccountId.get();
    }

    /**
     * Gets current user roles from ThreadLocal storage
     * @return Current roles string or null if not set
     */
    public static String getCurrentRoles() {
        return currentRoles.get();
    }
}
