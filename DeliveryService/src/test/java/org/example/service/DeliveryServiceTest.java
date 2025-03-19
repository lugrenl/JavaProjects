package org.example.service;

import org.example.enums.Fragile;
import org.example.enums.Size;
import org.example.enums.WorkLoad;
import org.example.exceptions.ArgumentIsNullException;
import org.example.exceptions.DistanceNotValidException;
import org.example.exceptions.MaximumFragileDistanceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryServiceTest {

    private DeliveryService deliveryService;

    @BeforeEach
    void setUp() {
        deliveryService = new DeliveryService();
    }

    @Nested
    @DisplayName("Тесты валидации данных")
    @Tag("Validation")
    class ValidationTests {

        @ParameterizedTest
        @DisplayName("Проверка входных данных на null")
        @Tag("NullArguments")
        @MethodSource("org.example.service.DeliveryServiceTest#validateDataTestProvider")
        void validateDataTest(Double distance, Size size, Fragile fragile, WorkLoad workLoad) {
            ArgumentIsNullException exception = assertThrows(
                    ArgumentIsNullException.class, () -> deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad)
            );
            assertEquals("All parameters must be not null", exception.getMessage());
        }

        @ParameterizedTest
        @DisplayName("Проверка расстояния на <= 0")
        @Tag("ZeroOrNegativeDistance")
        @ValueSource(doubles = {0.0, -1.0})
        void validateDistanceLessOrEqualZeroTest(Double distance) {
            Size size = Size.LARGE;
            Fragile fragile = Fragile.FRAGILE;
            WorkLoad workLoad = WorkLoad.NORMAL;

            DistanceNotValidException exception = assertThrows(
                    DistanceNotValidException.class, () -> deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad)
            );
            assertEquals("Distance must be more than 0", exception.getMessage());
        }

        @Test
        @DisplayName("Хрупкий груз на расстояние больше 30км")
        @Tag("FragileDistance")
        void validateFragileDistanceTest() {
            Size size = Size.LARGE;
            Fragile fragile = Fragile.FRAGILE;
            WorkLoad workLoad = WorkLoad.NORMAL;
            Double distance = 31.0;

            MaximumFragileDistanceException exception = assertThrows(
                    MaximumFragileDistanceException.class, () -> deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad)
            );
            assertEquals(String.format("Maximum fragile distance is %s", 30.0), exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Тесты расчета стоимости доставки")
    @Tag("DeliveryCostCalculation")
    class deliveryCostCalculationTests {

        @ParameterizedTest
        @DisplayName("Расчёт базовой стоимости для различных дистанций")
        @Tag("DistanceCost")
        @CsvSource({
                "1.0, SMALL, NOT_FRAGILE, NORMAL, 400.0",   // Минимальная стоимость -> (50 + 100 + 0) * 1
                "5.0, SMALL, NOT_FRAGILE, NORMAL, 400.0",   // От 2 до 10 км -> (100 + 100 + 0) * 1
                "15.0, SMALL, NOT_FRAGILE, NORMAL, 400.0",  // От 10 до 30 км -> (200 + 100 + 0) * 1
                "35.0, SMALL, NOT_FRAGILE, NORMAL, 400.0"   // Более 30 км -> (300 + 100 + 0) * 1
        })
        void CalculateBaseCostTest(Double distance, Size size, Fragile fragile, WorkLoad workLoad, Double expected) {
            assertEquals(expected, deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad),
                    "Неверная базовая стоимость для расстояния: " + distance);
        }

        @ParameterizedTest
        @DisplayName("Расчет стоимости доставки для различных размеров груза")
        @Tag("SizeCost")
        @CsvSource({
                "30.0, SMALL, NOT_FRAGILE, NORMAL, 400.0",  // Маленький размер -> (200 + 100 + 0) * 1
                "30.0, LARGE, NOT_FRAGILE, NORMAL, 400.0"   // Большой размер -> (200 + 200 + 0) * 1
        })
        void CalculateSizeCostTest(Double distance, Size size, Fragile fragile, WorkLoad workLoad, Double expected) {
            assertEquals(expected, deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad),
                    "Неверная стоимость для размера: " + size);
        }

        @ParameterizedTest
        @DisplayName("Расчет стоимости доставки для различных видов хрупкости груза")
        @Tag("FragileCost")
        @CsvSource({
                "30.0, SMALL, NOT_FRAGILE, NORMAL, 400.0",  // Не хрупкий груз -> (200 + 100 + 0) * 1
                "30.0, SMALL, FRAGILE, NORMAL, 600.0"       // Хрупкий груз -> (200 + 100 + 300) * 1
        })
        void CalculateFragileCostTest(Double distance, Size size, Fragile fragile, WorkLoad workLoad, Double expected) {
            assertEquals(expected, deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad),
                    "Неверная стоимость для хрупкости: " + fragile);
        }

        @ParameterizedTest
        @DisplayName("Расчет стоимости доставки для различных уровней нагрузки")
        @Tag("WorkLoadCost")
        @CsvSource({
                "30.0, SMALL, NOT_FRAGILE, NORMAL, 400.0",     // Нормальная загрузка -> (200 + 100 + 0) * 1
                "30.0, SMALL, NOT_FRAGILE, INTENSE, 400.0",    // Повышенная загрузка -> (200 + 100 + 0) * 1.2
                "30.0, SMALL, NOT_FRAGILE, HEAVY, 420.0",      // Высокая загрузка -> (200 + 100 + 0) * 1.4
                "30.0, SMALL, NOT_FRAGILE, VERY_HEAVY, 480.0"  // Очень высокая загрузка -> (200 + 100 + 0) * 1.6
        })
        void CalculateWorkLoadCostTest(Double distance, Size size, Fragile fragile, WorkLoad workLoad, Double expected) {
            assertEquals(expected, deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad),
                    "Неверная стоимость для уровня нагрузки: " + workLoad);
        }

        @ParameterizedTest
        @DisplayName("Граничные случаи дистанции для расчёта стоимости доставки")
        @Tag("BoarderCasesDistanceCost")
        @MethodSource("org.example.service.DeliveryServiceTest#boardDistanceTestProvider")
        void CalculateBoarderDistanceCasesTest(Double distance, Size size, Fragile fragile, WorkLoad workLoad, Double expected) {
            assertEquals(expected, deliveryService.calculateDeliveryCost(distance, size, fragile, workLoad),
                    "Неверная стоимость для граничного случая");
        }
    }

    private static Stream<Arguments> validateDataTestProvider() {
        return Stream.of(
                Arguments.of(null, Size.SMALL, Fragile.NOT_FRAGILE, WorkLoad.HEAVY),
                Arguments.of(12.0, null, Fragile.NOT_FRAGILE, WorkLoad.HEAVY),
                Arguments.of(12.0, Size.SMALL, null, WorkLoad.HEAVY),
                Arguments.of(12.0, Size.SMALL, Fragile.NOT_FRAGILE, null)
        );
    }

    private static Stream<Arguments> boardDistanceTestProvider() {
        return Stream.of(
                // (50 + 100 + 0) * 1.6
                Arguments.of(2.0, Size.SMALL, Fragile.NOT_FRAGILE, WorkLoad.VERY_HEAVY, 400.0),
                // (100 + 200 + 300) * 1.4
                Arguments.of(10.0, Size.LARGE,Fragile.FRAGILE, WorkLoad.HEAVY, 840.0),
                // (200 + 200 + 300) * 1.2
                Arguments.of(30.0, Size.LARGE, Fragile.FRAGILE, WorkLoad.INTENSE, 840.0),
                // (300 + 200 + 0) * 1.0
                Arguments.of(31.0, Size.LARGE, Fragile.NOT_FRAGILE, WorkLoad.NORMAL, 500.0)
        );
    }
}