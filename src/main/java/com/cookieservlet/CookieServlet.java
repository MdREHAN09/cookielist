package com.cookieservlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/CookieServlet")
public class CookieServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String userName = null;
        int count = 0;

        // Read existing cookies
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("user")) {
                    userName = c.getValue();
                }
                if (c.getName().equals("count")) {
                    count = Integer.parseInt(c.getValue());
                }
            }
        }

        // Increase visit count
        count++;

        // Update cookies
        Cookie userCookie = new Cookie("user", userName);
        Cookie countCookie = new Cookie("count", String.valueOf(count));

        // Cookie expiry (30 seconds)
        userCookie.setMaxAge(30);
        countCookie.setMaxAge(30);

        response.addCookie(userCookie);
        response.addCookie(countCookie);

        // HTML Output
        out.println("<html><body>");

        if (userName != null) {
            out.println("<h2 style='color:blue;'>Welcome back, " + userName + "!</h2>");
            out.println("<h3 style='color:green;'>You have visited this page " + count + " times.</h3>");
        } else {
            out.println("<h2 style='color:red;'>No user found. Please enter your name.</h2>");
            out.println("<a href='index.html'>Go Back</a>");
        }

        // Display cookie list
        out.println("<h3>Cookies List:</h3>");
        if (cookies != null) {
            for (Cookie c : cookies) {
                out.println("<p>Name: " + c.getName() +
                            " | Value: " + c.getValue() + "</p>");
            }
        }

        out.println("<br><a href='index.html'>Go Back</a>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("userName");

        // Create cookies
        Cookie userCookie = new Cookie("user", userName);
        Cookie countCookie = new Cookie("count", "0");

        // Expiry time (30 seconds)
        userCookie.setMaxAge(30);
        countCookie.setMaxAge(30);

        response.addCookie(userCookie);
        response.addCookie(countCookie);

        response.sendRedirect("CookieServlet");
    }
}