package ru.productstar.mockito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.model.Customer;
import ru.productstar.mockito.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    /**
     * Тест 1 - Получение покупателя "Ivan"
     * Проверки:
     * - очередность и точное количество вызовов каждого метода из CustomerRepository
     *
     * Тест 2 - Получение покупателя "Oleg"
     * Проверки:
     * - очередность и точное количество вызовов каждого метода из CustomerRepository
     * - в метод getOrCreate была передана строка "Oleg"
     */

}
