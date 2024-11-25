package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Expense;

import java.io.IOException;
import java.util.List;

public class DetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();  // загружаем контекст
        context.log("[DetailsServlet] doGet");  // пишем в лог

        resp.getWriter().println("Expenses: ");  // вернём расходы
        for (Expense e : (List<Expense>) context.getAttribute("expenses")) {  // загружаем список расходов из контекста
            resp.getWriter().println(String.format("- %s(%d)", e.getName(), e.getSum()));  // вернём расход
        }
        resp.getWriter().println("\n");  // переход на новую строку
    }
}
