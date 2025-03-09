package ru.productstar.mockito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.ProductNotFoundException;
import ru.productstar.mockito.model.Customer;
import ru.productstar.mockito.model.Order;
import ru.productstar.mockito.repository.*;

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

    @Test
    public void CreateProductTest() {
        CustomerRepository cr = spy(InitRepository.getInstance().getCustomerRepository());
        CustomerService cs = new CustomerService(cr);

        WarehouseRepository whr = spy(InitRepository.getInstance().getWarehouseRepository());
        WarehouseService ws = new WarehouseService(whr);

        OrderRepository or = spy(InitRepository.getInstance().getOrderRepository());
        ProductRepository pr = spy(InitRepository.getInstance().getProductRepository());

        OrderService os = new OrderService(cs, ws, or, pr);

        // создание ордера для существующего клиента
        os.create("Ivan");
        verify(cr, times(1)).getByName("Ivan");
        verify(cr, never()).add(any(Customer.class));
        verify(or, times(1)).create(any(Customer.class));
        assertEquals(3, cr.size());
        assertEquals(1, or.size());

        InOrder inOrderCr = inOrder(cr);
        inOrderCr.verify(cr).getByName("Ivan");
        inOrderCr.verify(cr).size();

        InOrder inOrderOr = inOrder(or);
        inOrderOr.verify(or).create(any(Customer.class));
        inOrderOr.verify(or).size();



    }
}
