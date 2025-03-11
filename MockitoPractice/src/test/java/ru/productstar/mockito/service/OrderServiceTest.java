package ru.productstar.mockito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.ProductNotFoundException;
import ru.productstar.mockito.model.*;
import ru.productstar.mockito.repository.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private WarehouseService warehouseService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Product product;
    private Warehouse warehouse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Подготовка данных
        order = new Order(new Customer("test_client"));
        product = new Product("test_product");
        warehouse = new Warehouse("test_warehouse", 10);
    }

    /*
      Покрыть тестами методы create и addProduct.
      Можно использовать вызовы реальных методов.
      <p>
      Должны быть проверены следующие сценарии:
      - создание ордера для существующего и нового клиента
      - добавление существующего и несуществующего товара
      - добавление товара в достаточном и не достаточном количестве
      - заказ товара с быстрой доставкой
      <p>
      Проверки:
      - общая сумма заказа соответствует ожидаемой
      - корректная работа для несуществующего товара
      - порядок и количество вызовов зависимых сервисов
      - факт выбрасывания ProductNotFoundException
     */

    /**
     * Создание заказа для существующего клиента
     */
    @Test
    public void CreateOrderForExistingCustomerTest() {
        // Подготовка
        Customer expectedCustomer = new Customer("Ivan");
        Order expectedOrder = new Order(expectedCustomer);
        when(customerService.getOrCreate("Ivan")).thenReturn(expectedCustomer);
        when(orderRepository.create(any(Customer.class))).thenReturn(expectedOrder);

        // Действие
        Order createdOrder = orderService.create("Ivan");

        // Проверка
        assertEquals(expectedOrder, createdOrder);

        // Количество вызовов
        verify(customerService, times(1)).getOrCreate("Ivan");
        verify(orderRepository, times(1)).create(expectedCustomer);

        // Очерёдность вызовов
        InOrder inOrder = inOrder(customerService, orderRepository);
        inOrder.verify(customerService).getOrCreate("Ivan");
        inOrder.verify(orderRepository).create(expectedCustomer);
    }

    /**
     * Создание заказа для нового клиента
     */
    @Test
    public void CreateOrderForNewCustomerTest() {
        // Подготовка
        Customer newCustomer = new Customer("Oleg");
        Order expectedOrder = new Order(newCustomer);
        when(customerService.getOrCreate("Oleg")).thenReturn(newCustomer);
        when(orderRepository.create(any(Customer.class))).thenReturn(expectedOrder);

        // Действие
        Order createdOrder = orderService.create("Oleg");

        // Проверка
        assertEquals(expectedOrder, createdOrder);

        // Количество вызовов
        verify(customerService, times(1)).getOrCreate("Oleg");
        verify(orderRepository, times(1)).create(newCustomer);

        // Очерёдность вызовов
        InOrder inOrder = inOrder(customerService, orderRepository);
        inOrder.verify(customerService).getOrCreate("Oleg");
        inOrder.verify(orderRepository).create(newCustomer);
    }

    /**
     * Добавление существующего товара в заказ с достаточным количеством на складе
     */
    @Test
    public void AddExistingProductWithEnoughCountTest() throws ProductNotFoundException {
        // Подготовка
        String productName = "test_product";
        int count = 5;
        boolean fastestDelivery = false;
        Stock stock = new Stock(product, 50, 10);

        when(warehouseService.findWarehouse(productName, count)).thenReturn(warehouse);
        when(productRepository.getByName(productName)).thenReturn(product);
        when(warehouseService.getStock(warehouse, productName)).thenReturn(stock);
        when(orderRepository.addDelivery(anyInt(), any(Delivery.class))).thenReturn(order);

        // Действие
        Order updatedOrder = orderService.addProduct(order, productName, count, fastestDelivery);

        // Проверка
        assertEquals(order, updatedOrder);

        // Количество вызовов
        verify(warehouseService, times(1)).findWarehouse(productName, count);
        verify(productRepository, times(1)).getByName(productName);
        verify(warehouseService, times(1)).getStock(warehouse, productName);
        verify(orderRepository, times(1)).addDelivery(anyInt(), any(Delivery.class));

        // Очерёдность вызовов
        InOrder inOrder = inOrder(warehouseService, productRepository, warehouseService, orderRepository);
        inOrder.verify(warehouseService).findWarehouse(productName, count);
        inOrder.verify(productRepository).getByName(productName);
        inOrder.verify(warehouseService).getStock(warehouse, productName);
        inOrder.verify(orderRepository).addDelivery(anyInt(), any(Delivery.class));
    }

    /**
     * Добавление существующего товара в заказ с недостаточным количеством на складе
     */
    @Test
    public void AddExistingProductWithNotEnoughCountTest() {
        // Подготовка
        String productName = "test_product";
        int count = 15;
        boolean fastestDelivery = false;

        when(warehouseService.findWarehouse(productName, count)).thenReturn(null);

        // Действие и ожидание исключения
        assertThrows(ProductNotFoundException.class,
                () -> orderService.addProduct(order, productName, count, fastestDelivery));

        // Количество вызовов
        verify(warehouseService, times(1)).findWarehouse(productName, count);
    }

    /**
     * Добавление несуществующего товара в заказ
     */
    @Test
    public void AddNonExistingProductTest() {
        // Подготовка
        String productName = "nonexistent_product";
        int count = 3;
        boolean fastestDelivery = false;

        when(warehouseService.findWarehouse(productName, count)).thenReturn(null);

        // Действие и ожидание исключения
        assertThrows(ProductNotFoundException.class,
                () -> orderService.addProduct(order, productName, count, fastestDelivery));

        // Количество вызовов
        verify(warehouseService, times(1)).findWarehouse(productName, count);
    }

    /**
     * Добавление существующего товара в заказ с достаточным количеством на складе
     * в режиме быстрой доставки
     */
    @Test
    public void AddProductWithFastestDeliveryTest() throws ProductNotFoundException {
        // Подготовка
        String productName = "test_product";
        int count = 5;
        boolean fastestDelivery = true;
        Stock stock = new Stock(product, 50, 10);

        when(warehouseService.findClosestWarehouse(productName, count)).thenReturn(warehouse);
        when(productRepository.getByName(productName)).thenReturn(product);
        when(warehouseService.getStock(warehouse, productName)).thenReturn(stock);
        when(orderRepository.addDelivery(anyInt(), any(Delivery.class))).thenReturn(order);

        // Действие
        Order updatedOrder = orderService.addProduct(order, productName, count, fastestDelivery);

        // Проверка
        assertEquals(order, updatedOrder);

        // Количество вызовов
        verify(warehouseService, times(1)).findClosestWarehouse(productName, count);
        verify(productRepository, times(1)).getByName(productName);
        verify(warehouseService, times(1)).getStock(warehouse, productName);
        verify(orderRepository, times(1)).addDelivery(anyInt(), any(Delivery.class));

        // Очерёдность вызовов
        InOrder inOrder = inOrder(warehouseService, productRepository, warehouseService, orderRepository);
        inOrder.verify(warehouseService).findClosestWarehouse(productName, count);
        inOrder.verify(productRepository).getByName(productName);
        inOrder.verify(warehouseService).getStock(warehouse, productName);
        inOrder.verify(orderRepository).addDelivery(anyInt(), any(Delivery.class));
    }
}
