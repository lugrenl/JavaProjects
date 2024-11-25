package ru.productstar.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.productstar.servlets.model.Expense;

import java.io.IOException;
import java.util.List;

public class ExpensesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var context = req.getServletContext();  // загружаем контекст
        context.log("[ExpensesServlet] doGet");  // пишем в лог

        var expenses = (List<Expense>) context.getAttribute("expenses");  // загружаем список расходов из атрибутов

        int freeMoney = (int) context.getAttribute("freeMoney");  // загружаем свободные деньги из атрибутов
        resp.getWriter().println("Expenses: " + freeMoney);  // вернём расходы

        // получаем и обрабатываем параметры расходов из реквеста
        for (var k : req.getParameterMap().keySet()) {
            int value = Integer.parseInt(req.getParameter(k)); // значение расхода из параметров
            freeMoney -= value;  // вычитаем расход из свободных денег
            expenses.add(new Expense(k, value));  // добавляем расход в список расходов
        }

        context.setAttribute("freeMoney", freeMoney);  // сохраняем новое значение свободных денег в контекст
        context.setAttribute("expenses", expenses);  // сохраняем новый список расходов в контекст
        resp.getWriter().println("Expenses were added. Free money: " + freeMoney);  // вернём ответ клиенту
    }
}
