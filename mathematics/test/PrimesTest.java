package com.jwetherell.algorithms.mathematics.test;

import com.jwetherell.algorithms.mathematics.Primes;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

public class PrimesTest {

    @Test
    public void testIsPrime() {
        assertTrue(Primes.isPrime(2));
        assertTrue(Primes.isPrime(3));
        assertFalse(Primes.isPrime(4));
        assertTrue(Primes.isPrime(5));
        assertFalse(Primes.isPrime(1)); // Edge case
        assertTrue(Primes.isPrime(13));
        assertFalse(Primes.isPrime(16));
    }

    @Test
    public void testGetPrimeFactorization() {
        Map<Long, Long> expectedFactors1 = new HashMap<>();
        expectedFactors1.put(2L, 1L);
        expectedFactors1.put(3L, 1L);
        assertEquals(expectedFactors1, Primes.getPrimeFactorization(6));

        Map<Long, Long> expectedFactors2 = new HashMap<>();
        expectedFactors2.put(5L, 1L);
        expectedFactors2.put(7L, 1L);
        assertEquals(expectedFactors2, Primes.getPrimeFactorization(35));

        Map<Long, Long> expectedFactors3 = new HashMap<>();
        expectedFactors3.put(5L, 2L);
        expectedFactors3.put(7L, 1L);
        assertEquals(expectedFactors3, Primes.getPrimeFactorization(175));
    }

    @Test
    public void testSieveOfEratosthenes() {
        assertTrue(Primes.sieveOfEratosthenes(2));
        assertTrue(Primes.sieveOfEratosthenes(3));
        assertFalse(Primes.sieveOfEratosthenes(4));
        assertTrue(Primes.sieveOfEratosthenes(5));
        assertFalse(Primes.sieveOfEratosthenes(1)); // Edge case
        assertTrue(Primes.sieveOfEratosthenes(13));
        assertFalse(Primes.sieveOfEratosthenes(16));
    }

    @Test
    public void testMillerRabinTest() {
        assertTrue(Primes.millerRabinTest(2));
        assertTrue(Primes.millerRabinTest(3));
        assertFalse(Primes.millerRabinTest(4));
        assertTrue(Primes.millerRabinTest(5));
        assertFalse(Primes.millerRabinTest(1)); // Edge case
        assertTrue(Primes.millerRabinTest(13));
        assertFalse(Primes.millerRabinTest(16));
    }
}