package ru.productstar.mockito.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
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
     */
    @Test
    public void GetByNameTest() {
        CustomerRepository cr = spy(CustomerRepository.class);
        CustomerService cs = new CustomerService(cr);
        cs.getOrCreate("Ivan");

        verify(cr, times(1)).getByName("Ivan");
        verify(cr, never()).add(any(Customer.class));
        assertEquals(3, cr.size());

        InOrder inOrder = inOrder(cr);
        inOrder.verify(cr).getByName("Ivan");
        inOrder.verify(cr).size();
    }

    /**
     * Тест 2 - Получение покупателя "Oleg"
     * Проверки:
     * - очередность и точное количество вызовов каждого метода из CustomerRepository
     * - в метод getOrCreate была передана строка "Oleg"
     */
    @Test
    public void GetOrCreateTest() {
        CustomerRepository cr = mock(CustomerRepository.class);
        CustomerService cs = new CustomerService(cr);
        cs.getOrCreate("Oleg");

        verify(cr, times(1)).getByName("Oleg");
        verify(cr, times(1)).add(any(Customer.class));

        when(cr.size()).thenReturn(1);
        assertEquals(1, cr.size());

        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(cr, times(1)).add(customerArgumentCaptor.capture());
        Customer customer = customerArgumentCaptor.getValue();
        assertEquals("Oleg", customer.getName());

        InOrder inOrder = inOrder(cr);
        inOrder.verify(cr).getByName("Oleg");
        inOrder.verify(cr).add(any(Customer.class));
        inOrder.verify(cr).size();
    }
}
