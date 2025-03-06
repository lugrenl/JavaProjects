package ru.productstar.mockito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.ProductNotFoundException;
import ru.productstar.mockito.model.Order;
import ru.productstar.mockito.repository.InitRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    /**
     * Покрыть тестами методы create и addProduct.
     * Можно использовать вызовы реальных методов.
     *
     * Должны быть проверены следующие сценарии:
     * - создание ордера для существующего и нового клиента
     * - добавление существующего и несуществующего товара
     * - добавление товара в достаточном и не достаточном количестве
     * - заказ товара с быстрой доставкой
     *
     * Проверки:
     * - общая сумма заказа соответствует ожидаемой
     * - корректная работа для несуществующего товара
     * - порядок и количество вызовов зависимых сервисов
     * - факт выбрасывания ProductNotFoundException
     */
}
