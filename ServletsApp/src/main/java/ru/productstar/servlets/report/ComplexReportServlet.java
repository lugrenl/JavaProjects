package ru.productstar.servlets.report;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "/complex-report", asyncSupported = true)
public class ComplexReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var session = req.getSession(false);  // Загружаем сессию. Если не передавать false - создаётся новая сессия
        if (session == null) {  // если нет активной сессии
            resp.sendRedirect("/login");  // перенаправляем пользователя на страницу LoginServlet
            return;
        }

        long startTime = System.currentTimeMillis();  // время запуска комплексного отчёта

//        try {
//            Thread.sleep(3000);  // имитация работы комплексного отчёта
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
        final AsyncContext asyncContext = req.startAsync();  // получаем асинхронный контекст
        // в таком режиме время работы основного потока не затрачивается, потоки основного пула свободны
        new Thread(() -> {
            try {
                Thread.sleep(3000);  // имитация работы комплексного отчёта
                asyncContext.getResponse().getWriter().println("Success");
                asyncContext.complete();  // завершаем асинхронный контекст
            } catch (InterruptedException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        long endTime = System.currentTimeMillis();  // время окончания комплексного отчёта
        long duration = endTime - startTime;  // вычисляем время работы комплексного отчёта
        req.getServletContext()  // пишем в лог время работы комплексного отчёта
                .log(String.format("[ComplexReportServlet] Thread %s, %d", Thread.currentThread().getName(), duration));
        //resp.getWriter().println("Success");
    }
}
