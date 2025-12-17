package org.example.parkinglot.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Luăm datele din formular
        String u = request.getParameter("username");
        String p = request.getParameter("password");

        try {
            // 2. Încercăm logarea programatică
            request.login(u, p);

            // 3. Dacă ajunge aici, login-ul a reușit!
            response.sendRedirect(request.getContextPath() + "/Cars");

        } catch (ServletException e) {
            // 4. Dacă ajunge aici, datele sunt greșite sau configurarea Realm e invalidă
            request.setAttribute("message", "Username sau parolă incorectă!");
            request.getRequestDispatcher("/WEB-INF/pages/login.jsp").forward(request, response);
        }
    }
}