package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final String passwd = "123456";  // пароль от сервиса

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/index.html").forward(req, resp);  // вызываем страницу LoginServlet
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String password = req.getParameter("password");  // получим пароль из формы, который ввёл пользователь

        if (password.equals(passwd)) {  // если пароль совпадает
            HttpSession session = req.getSession();  // создаем сессию
            session.setMaxInactiveInterval(60);  // устанавливаем время жизни сессии
            resp.sendRedirect("/summary");  // перенаправляем пользователя на страницу SummaryServlet
        } else {  // если пароль не совпадает
            resp.getWriter().println("Wrong password");  // вернём сообщение об ошибке
        }
    }
}
