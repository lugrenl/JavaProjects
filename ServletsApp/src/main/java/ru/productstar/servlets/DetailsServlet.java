package ru.productstar.servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.List;

public class DetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var context = req.getServletContext();  // загружаем контекст
        context.log("[DetailsServlet] doGet");  // пишем в лог

        var session = req.getSession(false);  // Загружаем сессию. Если не передавать false - создаётся новая сессия
        if (session == null) {  // если нет активной сессии
            resp.sendRedirect("/login");  // перенаправляем пользователя на страницу LoginServlet
            return;
        }

        resp.getWriter().println("Transactions: ");  // вернём список транзакций в порядке их добавления
        // загружаем список транзакций из контекста
        for (Transaction e : (List<Transaction>) context.getAttribute("transactions")) {  // вернём транзакцию
            resp.getWriter().println(String.format("- %s(%d) <- %s", e.getName(), e.getValue(), e.getType()));
        }

        resp.getWriter().println("\n");  // переход на новую строку
    }
}
