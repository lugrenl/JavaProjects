package ru.productstar.mockito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.model.Customer;
import ru.productstar.mockito.repository.CustomerRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository cr;

    @InjectMocks
    private CustomerService cs;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Тест 1 - Получение существующего покупателя "Ivan"
     * Проверки:
     * - очередность и точное количество вызовов каждого метода из CustomerRepository
     */
    @Test
    public void GetByNameTest() {
        // Подготовка
        Customer expectedCustomer = new Customer("Ivan");
        when(cr.getByName("Ivan")).thenReturn(expectedCustomer);
        when(cr.size()).thenReturn(3);

        // Действие
        Customer actualCustomer = cs.getOrCreate("Ivan");

        // Проверка
        assertEquals(expectedCustomer, actualCustomer);
        assertEquals(3, cr.size());

        // Количество вызовов
        verify(cr, times(1)).getByName("Ivan");
        verify(cr, never()).add(any(Customer.class));

        // Очерёдность вызовов
        InOrder inOrder = inOrder(cr);
        inOrder.verify(cr).getByName("Ivan");
        inOrder.verify(cr).size();
    }

    /**
     * Тест 2 - Получение нового покупателя "Oleg"
     * Проверки:
     * - очередность и точное количество вызовов каждого метода из CustomerRepository
     * - в метод getOrCreate была передана строка "Oleg"
     */
    @Test
    public void GetOrCreateTest() {
        // Подготовка
        Customer newCustomer = new Customer("Oleg");
        when(cr.getByName("Oleg")).thenReturn(null);
        when(cr.add(any(Customer.class))).thenReturn(newCustomer);
        when(cr.size()).thenReturn(1);

        // Действие
        Customer actualCustomer = cs.getOrCreate("Oleg");

        // Проверка
        assertEquals(newCustomer, actualCustomer);
        assertEquals(1, cr.size());

        // Количество вызовов
        verify(cr, times(1)).getByName("Oleg");
        verify(cr, times(1)).add(any(Customer.class));

        // Очерёдность вызовов
        InOrder inOrder = inOrder(cr);
        inOrder.verify(cr).getByName("Oleg");
        inOrder.verify(cr).add(any(Customer.class));
        inOrder.verify(cr).size();

        // Захват аргумента
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(cr, times(1)).add(customerArgumentCaptor.capture());
        Customer customer = customerArgumentCaptor.getValue();
        assertEquals("Oleg", customer.getName());
    }
}
