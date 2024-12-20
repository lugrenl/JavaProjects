package ru.productstar.servlets;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Transaction;
import ru.productstar.servlets.model.TransactionType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SummaryServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config) {
        var context = config.getServletContext();  // загружаем контекст
        context.log("[SummaryServlet] init");  // пишем в лог

        var salary = Integer.parseInt(context.getInitParameter("salary"));  // загружаем переменную из контекста
        var rent = Integer.parseInt(config.getInitParameter("rent"));  // загружаем переменную из конфигурации

        context.setAttribute("freeMoney", salary - rent);  // сохраняем свободные деньги в атрибуты

        List<Transaction> transactions = new ArrayList<>();  // создаем список транзакций
        transactions.add(new Transaction("rent", rent, TransactionType.EXPENSE));  // добавляем транзакцию

        context.setAttribute("transactions", transactions);  // сохраняем список транзакций в атрибуты
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();  // загружаем контекст
        context.log("[SummaryServlet] doGet");  // пишем в лог

        var session = req.getSession(false);  // Загружаем сессию. Если не передавать false - создаётся новая сессия
        if (session == null) {  // если нет активной сессии
            resp.sendRedirect("/login");  // перенаправляем пользователя на страницу LoginServlet
            return;
        }

        req.getRequestDispatcher("/details").include(req, resp); // вызываем servlet DetailsServlet
        resp.getWriter().println("Free money: " + context.getAttribute("freeMoney"));  // вернём свободные деньги
    }
}
