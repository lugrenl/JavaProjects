package ru.productstar.servlets.report;

import jakarta.servlet.AsyncContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.WriteListener;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(value = "big-report", asyncSupported = true)
public class BigReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        var session = req.getSession(false);  // Загружаем сессию. Если не передавать false - создаётся новая сессия
        if (session == null) {  // если нет активной сессии
            resp.sendRedirect("/login");  // перенаправляем пользователя на страницу LoginServlet
            return;
        }

        long startTime = System.currentTimeMillis();  // время запуска большого отчёта

        // реализуем поддержку неблокирующего чтения и записи
        final AsyncContext asyncContext = req.startAsync();  // получаем асинхронный контекст
        ServletOutputStream os = resp.getOutputStream();  // тк нужно записать ответ, его берём из response
        os.setWriteListener(new WriteListener() {  // listener, который будет в асинхронном режиме писать данные
            final byte[] result = new byte[100_000_000];  // имитация большого отчёта
            int offset = 0;

            @Override
            public void onWritePossible() throws IOException {
                while (os.isReady()) {
                    if (offset >= result.length) {
                        asyncContext.complete();
                        return;
                    }
                    os.write(result, offset, Math.min(1024, result.length - offset));
                    offset += 1024;
                }
            }

            @Override
            public void onError(Throwable throwable) {
                asyncContext.complete();  // в случае ошибки завершаем асинхронный контекст
            }
        });

        long endTime = System.currentTimeMillis();  // время окончания большого отчёта
        long duration = endTime - startTime;  // вычисляем время работы большого отчёта
        req.getServletContext()  // пишем в лог время работы большого отчёта
                .log(String.format("[BigReportServlet] Thread %s, %d", Thread.currentThread().getName(), duration));
    }
}
