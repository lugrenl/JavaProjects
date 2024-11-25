package ru.productstar.servlets.filters;

import jakarta.servlet.*;

import java.io.IOException;

public class ExpensesFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        var context = servletRequest.getServletContext();  // загружаем контекст
        context.log("[ExpensesFilter] doFilter");  // пишем в лог

        int freeMoney = (int) context.getAttribute("freeMoney");  // загружаем свободные деньги из атрибутов

        // получаем и обрабатываем параметры расходов из реквеста
        for (var k : servletRequest.getParameterMap().keySet()) {
            freeMoney -= Integer.parseInt(servletRequest.getParameter(k)); // вычитаем расход из свободных денег
            if (freeMoney < 0) {  // проверяем хватает ли денег
                servletResponse.getWriter().println("Not enough money");
                return;
            }
        }

        // если проверка пройдена успешно, нужно чтобы обработка пошла дальше или вызвался другой фильтр если он есть
        filterChain.doFilter(servletRequest, servletResponse);
    }
}