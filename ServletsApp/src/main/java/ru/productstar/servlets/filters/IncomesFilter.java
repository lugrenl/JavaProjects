package ru.productstar.servlets.filters;

import jakarta.servlet.*;

import java.io.IOException;

public class IncomesFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var context = servletRequest.getServletContext();  // загружаем контекст
        context.log("[IncomeFilter] doFilter");  // пишем в лог

        // получаем и обрабатываем параметры доходов из request
        for (var k : servletRequest.getParameterMap().keySet()) {
            int income = Integer.parseInt(servletRequest.getParameter(k)); // значение дохода из параметров
            if (income < 0) {  // проверяем что доход не отрицательный
                servletResponse.getWriter().println("Income can't be negative");
                return;
            }
        }

        // если проверка пройдена успешно, нужно чтобы обработка пошла дальше или вызвался другой фильтр если он есть
        filterChain.doFilter(servletRequest, servletResponse);
    }
}