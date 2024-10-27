package com.example.transaction.infrastructure.adapter.securityconfig;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RoleInterceptor implements HandlerInterceptor {

    private final JwtClient jwtClient;
    private final Map<String, List<String>> rolePermissions = new HashMap<>();

    public RoleInterceptor(JwtClient jwtClient) {
        this.jwtClient = jwtClient;
        rolePermissions.put("/admin/**", List.of("ROLE_ADMIN"));
        rolePermissions.put("/company/**", List.of("ROLE_AUX_BODEGA", "ROLE_ADMIN"));
        rolePermissions.put("/secure/**", List.of( "ROLE_USER","ROLE_ADMIN"));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            String requestPath = request.getRequestURI();


            List<String> allowedRoles = getAllowedRolesForPath(requestPath);

            if (allowedRoles == null || allowedRoles.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }

            if (!jwtClient.hasAnyRole(token, allowedRoles)) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }

    private List<String> getAllowedRolesForPath(String requestPath) {
        return rolePermissions.entrySet().stream()
                .filter(entry -> requestPath.matches(entry.getKey().replace("**", ".*")))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}


