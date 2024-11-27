package ru.productstar.servlets.listeners;

import jakarta.servlet.ServletContextAttributeEvent;
import jakarta.servlet.ServletContextAttributeListener;

public class LogAttributeChanges implements ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent event) {
        event.getServletContext()  // пишем в лог изменение атрибутов (добавление)
                .log(String.format("[LogAttributeChanges] New value of %s: %s", event.getName(), event.getValue()));
    }

    @Override
    public void attributeReplaced(ServletContextAttributeEvent event) {
        event.getServletContext()  // пишем в лог изменение атрибутов (замена)
                .log(String.format("[LogAttributeChanges] Old value of %s: %s", event.getName(), event.getValue()));
    }
}
