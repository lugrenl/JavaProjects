package ru.productstar.servlets.handlers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/errorHandler")
public class ErrorHandler extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Exception exception = (Exception) req.getAttribute("jakarta.servlet.error.exception");  // получаем исключение
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");  // получаем статус

        if (statusCode == 404) {
            resp.getWriter().println("Error (" + statusCode + ") - page not found");
        } else if (statusCode == 500) {
            resp.getWriter().println("Error (" + statusCode + ") - value must not be null");
        } else if (exception != null) {
            resp.getWriter().println("Error (" + statusCode + ") - " + exception.getClass().getName());
        }
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doGet(request, response);  // вызываем метод doGet
    }
}