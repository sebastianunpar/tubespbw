package com.example.tubespbw;

import java.io.IOException;
import java.net.Authenticator;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthorizationAspect {
    
    @Autowired
    private HttpSession session;

    @Autowired
    HttpServletResponse response;

    @Before("@annotation(requiresRole)")
    public void checkAuthorization(RequiresRole requiresRole) throws IOException {
        System.out.println("Intercepting method with role: " + requiresRole.value());

        // Simulating a user session. Replace with real session handling.
        String currentRole = getCurrentUserRole();
        System.out.println("\n\n\n\n");
        System.out.println("Current Role: " + currentRole);

        if (currentRole == null) {
            response.sendRedirect("/");
        } else if ((currentRole.equals("user") && requiresRole.value().equals("admin"))) {
            response.sendRedirect("/");
        } else if ((currentRole.equals("admin") && requiresRole.value().equals("user"))) {
            response.sendRedirect("/admin");
        }
    }

    private String getCurrentUserRole() {
        return (String) session.getAttribute("role"); // "USER" or "ADMIN"
    }
}
