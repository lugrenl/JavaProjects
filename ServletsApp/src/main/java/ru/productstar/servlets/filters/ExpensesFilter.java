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

        // получаем и обрабатываем параметры расходов из request
        for (var k : servletRequest.getParameterMap().keySet()) {
            int expense = Integer.parseInt(servletRequest.getParameter(k)); // значение расхода из параметров
            if (expense < 0) {  // проверяем что расход не отрицательный
                servletResponse.getWriter().println("Expense can't be negative");
                return;
            }
            freeMoney -= expense; // вычитаем расход из свободных денег
            if (freeMoney < 0) {  // проверяем хватает ли денег
                servletResponse.getWriter().println("Not enough money");
                return;
            }
        }

        // если проверка пройдена успешно, нужно чтобы обработка пошла дальше или вызвался другой фильтр если он есть
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
