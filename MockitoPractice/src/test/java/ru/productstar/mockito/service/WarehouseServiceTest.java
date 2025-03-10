package ru.productstar.mockito.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.productstar.mockito.model.Product;
import ru.productstar.mockito.model.Stock;
import ru.productstar.mockito.model.Warehouse;
import ru.productstar.mockito.repository.InitRepository;
import ru.productstar.mockito.repository.WarehouseRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseServiceTest {

    @Mock
    private WarehouseRepository warehouseRepository;

    @InjectMocks
    private WarehouseService warehouseService;

    private List<Warehouse> warehouses;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем продукты для тестирования
        Product phone = new Product("phone");
        Product laptop = new Product("laptop");
        Product keyboard = new Product("keyboard");

        // Создаем склады для тестирования
        warehouses = Arrays.asList(
                createWarehouse("Warehouse0", 30, new Stock(phone, 400, 5)),
                createWarehouse("Warehouse1", 20, new Stock(laptop, 900, 3)),
                createWarehouse("Warehouse2", 5, new Stock(keyboard, 40, 10))
        );
    }

    private static Warehouse createWarehouse(String name, int distance, Stock... stocks) {
        Warehouse warehouse = new Warehouse(name, distance);
        for (Stock stock : stocks) {
            warehouse.addStock(stock);
        }
        return warehouse;
    }

    /**
     * Покрыть тестами методы findWarehouse и findClosestWarehouse.
     * Вызывать реальные методы зависимых сервисов и репозиториев нельзя.
     * Поиск должен осуществляться как минимум на трех складах.
     * <p>
     * Должны быть проверены следующие сценарии:
     * - поиск несуществующего товара
     * - поиск существующего товара с достаточным количеством
     * - поиск существующего товара с недостаточным количеством
     * <p>
     * Проверки:
     * - товар находится на нужном складе, учитывается количество и расстояние до него
     * - корректная работа для несуществующего товара
     * - порядок и количество вызовов зависимых сервисов
     */

    @Test
    public void FindWarehouseNoneExistingProductTest() {
        // Подготовка
        when(warehouseRepository.all()).thenReturn(warehouses);

        // Действие
        Warehouse wh = warehouseService.findWarehouse("nonexistent", 1);

        // Проверка
        assertNull(wh);

        // Количество вызовов
        verify(warehouseRepository, times(1)).all();
    }

    @Test
    public void FindWarehouseExistingProductEnoughQuantityTest() {
        // Подготовка
        when(warehouseRepository.all()).thenReturn(warehouses);

        // Действие
        Warehouse wh = warehouseService.findWarehouse("phone", 2);

        // Проверка
        assertEquals("Warehouse0", wh.getName());

        // Количество вызовов
        verify(warehouseRepository, times(1)).all();
    }

    @Test
    public void FindWarehouseExistingProductNotEnoughQuantityTest() {
        // Подготовка
        when(warehouseRepository.all()).thenReturn(warehouses);

        // Действие
        Warehouse wh = warehouseService.findWarehouse("phone", 6);

        // Проверка
        assertNull(wh);

        // Количество вызовов
        verify(warehouseRepository, times(1)).all();
    }

    @Test
    public void FindClosestWarehouseNoneExistingProductTest() {
        // Подготовка
        when(warehouseRepository.all()).thenReturn(warehouses);

        // Действие
        Warehouse wh = warehouseService.findClosestWarehouse("nonexistent", 1);

        // Проверка
        assertNull(wh);

        // Количество вызовов
        verify(warehouseRepository, times(1)).all();
    }

    @Test
    public void FindClosestWarehouseExistingProductEnoughQuantityTest() {
        // Подготовка
        when(warehouseRepository.all()).thenReturn(warehouses);

        // Действие
        Warehouse wh = warehouseService.findClosestWarehouse("phone", 2);

        // Проверка
        assertEquals("Warehouse0", wh.getName());

        // Количество вызовов
        verify(warehouseRepository, times(1)).all();
    }

    @Test
    public void FindClosestWarehouseExistingProductNotEnoughQuantityTest() {
        // Подготовка
        when(warehouseRepository.all()).thenReturn(warehouses);

        // Действие
        Warehouse wh = warehouseService.findClosestWarehouse("phone", 6);

        // Проверка
        assertNull(wh);

        // Количество вызовов
        verify(warehouseRepository, times(1)).all();
    }
}
