package ru.productstar.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Expense;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SummaryServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        var context = config.getServletContext();  // загружаем контекст
        context.log("[SummaryServlet] init");  // пишем в лог

        var salary = Integer.parseInt(context.getInitParameter("salary"));  // загружаем переменную из контекста
        var rent = Integer.parseInt(config.getInitParameter("rent"));  // загружаем переменную из конфигурации

        context.setAttribute("freeMoney", salary - rent);  // сохраняем свободные деньги в атрибуты

        List<Expense> expenses = new ArrayList();  // создаем список расходов
        expenses.add(new Expense("rent", rent));  // добавляем расход

        context.setAttribute("expenses", expenses);  // сохраняем список расходов в атрибуты
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();  // загружаем контекст
        context.log("[SummaryServlet] doGet");  // пишем в лог

        resp.getWriter().println("Free money: " + context.getAttribute("freeMoney"));  // вернём свободные деньги
    }
}
