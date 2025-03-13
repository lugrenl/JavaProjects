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
        assertFalse(IsPrime.isPrime(4), "4 should not be prime");
        assertFalse(IsPrime.isPrime(6), "6 should not be prime");
        assertFalse(IsPrime.isPrime(8), "8 should not be prime");
        assertFalse(IsPrime.isPrime(9), "9 should not be prime");
        assertFalse(IsPrime.isPrime(10), "10 should not be prime");
        assertFalse(IsPrime.isPrime(12), "12 should not be prime");
    }

    @Test
    void PrimeNumbersShouldBePrime() {
        assertTrue(IsPrime.isPrime(2), "2 should be prime");
        assertTrue(IsPrime.isPrime(3), "3 should be prime");
        assertTrue(IsPrime.isPrime(5), "5 should be prime");
        assertTrue(IsPrime.isPrime(7), "7 should be prime");
        assertTrue(IsPrime.isPrime(11), "11 should be prime");
        assertTrue(IsPrime.isPrime(13), "13 should be prime");
    }
}