package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Transaction;
import ru.productstar.servlets.model.TransactionType;

import java.io.IOException;
import java.util.List;

public class ExpensesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();  // загружаем контекст
        context.log("[ExpensesServlet] doGet");  // пишем в лог

        var session = req.getSession(false);  // Загружаем сессию. Если не передавать false - создаётся новая сессия
        if (session == null) {  // если нет активной сессии
            resp.sendRedirect("/login");  // перенаправляем пользователя на страницу LoginServlet
            return;
        }

        // загружаем список транзакций из атрибутов
        var transactions = (List<Transaction>) context.getAttribute("transactions");

        int freeMoney = (int) context.getAttribute("freeMoney");  // загружаем свободные деньги из атрибутов

        // получаем и обрабатываем параметры расходов из request
        for (var k : req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k)); // значение расхода из параметров
            freeMoney -= value;  // вычитаем расход из свободных денег
            transactions.add(new Transaction(k, value, TransactionType.EXPENSE));  // добавляем расход в список расходов
        }

        context.setAttribute("freeMoney", freeMoney);  // сохраняем новое значение свободных денег в контекст
        context.setAttribute("transactions", transactions);  // сохраняем новый список транзакций в контекст
        resp.getWriter().println("Expenses were added. Free money: " + freeMoney);  // вернём ответ клиенту
        resp.sendRedirect("/summary");  // перенаправляем пользователя на страницу SummaryServlet
    }
}
