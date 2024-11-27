package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Transaction;

import java.io.IOException;
import java.util.List;

@WebServlet("/incomes/add")
public class IncomesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();  // загружаем контекст
        context.log("[IncomesServlet] doGet");  // пишем в лог

        var session = req.getSession(false);  // Загружаем сессию. Если не передавать false - создаётся новая сессия
        if (session == null) {  // если нет активной сессии
            resp.sendRedirect("/login");  // перенаправляем пользователя на страницу LoginServlet
            return;
        }

        var expenses = (List<Transaction>) context.getAttribute("expenses");  // загружаем список расходов из атрибутов

        int freeMoney = (int) context.getAttribute("freeMoney");  // загружаем свободные деньги из атрибутов
        resp.getWriter().println("Incomes: " + freeMoney);  // вернём свободные деньги

        // получаем и обрабатываем параметры расходов из реквеста
        for (var k : req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k)); // значение расхода из параметров
            freeMoney += value;  // прибавляем доход к свободным деньгам
            expenses.add(new Transaction(k, value, "income"));  // добавляем доход в список расходов
        }

        context.setAttribute("freeMoney", freeMoney);  // сохраняем новое значение свободных денег в контекст
        context.setAttribute("expenses", expenses);  // сохраняем новый список расходов в контекст
        resp.getWriter().println("Incomes were added. Free money: " + freeMoney);  // вернём ответ клиенту

    }
}
