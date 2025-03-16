import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsPrimeTest {

    @Test
    void zeroTest() {
        int num = 0;
        boolean result = IsPrime.isPrime(num);
        assertFalse(result);
    }

    @Test
    void oneTest() {
        int num = 1;
        boolean result = IsPrime.isPrime(num);
        assertFalse(result);
    }

    @Test
    void negativeTest() {
        int num = -7;
        boolean result = IsPrime.isPrime(num);
        assertFalse(result);
    }

    @Test
    void CompositeNumbersShouldNotBePrime() {
        assertAll(
                () -> assertFalse(IsPrime.isPrime(4), "4 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(6), "6 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(8), "8 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(9), "9 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(10), "10 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(12), "12 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(1024), "1024 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(4096), "4096 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(8192), "8192 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(16384), "16384 should not be prime"),
                () -> assertFalse(IsPrime.isPrime(1003412), "1003412 should not be prime")
                );
    }

    @Test
    void PrimeNumbersShouldBePrime() {
        assertAll(
                () -> assertTrue(IsPrime.isPrime(2), "2 should be prime"),
                () -> assertTrue(IsPrime.isPrime(3), "3 should be prime"),
                () -> assertTrue(IsPrime.isPrime(5), "5 should be prime"),
                () -> assertTrue(IsPrime.isPrime(7), "7 should be prime"),
                () -> assertTrue(IsPrime.isPrime(11), "11 should be prime"),
                () -> assertTrue(IsPrime.isPrime(13), "13 should be prime"),
                () -> assertTrue(IsPrime.isPrime(17), "17 should be prime"),
                () -> assertTrue(IsPrime.isPrime(19), "19 should be prime"),
                () -> assertTrue(IsPrime.isPrime(97), "297 should be prime"),
                () -> assertTrue(IsPrime.isPrime(101), "101 should be prime"),
                () -> assertTrue(IsPrime.isPrime(1003411), "1003411 should be prime")
        );
    }
}