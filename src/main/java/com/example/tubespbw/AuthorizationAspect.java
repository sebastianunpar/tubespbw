package com.example.tubespbw;

import java.io.IOException;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        String currentRole = getCurrentUserRole();

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
