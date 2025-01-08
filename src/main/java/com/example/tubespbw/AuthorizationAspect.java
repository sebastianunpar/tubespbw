package com.example.tubespbw;

import java.net.Authenticator;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpSession;

@Aspect
@Component
public class AuthorizationAspect {
    
    @Autowired
    private HttpSession session;

    @Before("@annotation(requiresRole)")
    public void checkAuthorization(RequiresRole requiresRole) {
        System.out.println("Intercepting method with role: " + requiresRole.value());

        // Simulating a user session. Replace with real session handling.
        String currentRole = getCurrentUserRole();
        System.out.println("\n\n\n\n");
        System.out.println("Current Role: " + currentRole);

        if (!currentRole.equals(requiresRole.value())) {
            throw new SecurityException("Access Denied: Insufficient permissions, this page should only be accessed by " + requiresRole.value()+" but your current role is " + currentRole);
        }
    }

    private String getCurrentUserRole() {
        return (String) session.getAttribute("role"); // "USER" or "ADMIN"
    }
}
