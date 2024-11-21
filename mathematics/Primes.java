package com.jwetherell.algorithms.mathematics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Justin Wetherell <phishman3579@gmail.com>
 * @author Bartlomiej Drozd <mail@bartlomiejdrozd.pl>
 */
public class Primes {

    /**
     * Get the prime factorization of a number.
     */
    public static final Map<Long, Long> getPrimeFactorization(long number) {
        Map<Long, Long> map = new HashMap<>();
        long n = number;
        long sqrt = (long) Math.sqrt(number);

        // For each potential factor i
        for (long i = 2; i <= sqrt; i++) {
            int c = 0;

            // Divide out all factors of i
            while (n % i == 0) {
                n /= i;
                c++;
            }

            if (c > 0) {
                map.put(i, map.getOrDefault(i, 0L) + c);
            }
        }

        // If n is still greater than 1, it is prime
        if (n > 1) {
            map.put(n, map.getOrDefault(n, 0L) + 1);
        }

        return map;
    }

    /**
     * Check if a number is prime using optimized logic.
     */
    public static final boolean isPrime(long number) {
        if (number <= 1) return false; // 1 and negative numbers are not prime
        if (number < 4) return true;  // 2 and 3 are prime
        if (number % 2 == 0) return false;
        if (number < 9) return true;  // We've already excluded 4, 6, and 8
        if (number % 3 == 0) return false;

        long r = (long) Math.sqrt(number);
        for (long i = 5; i <= r; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    /*
     * Thread-local sieve to ensure thread safety.
     */
    private static ThreadLocal<boolean[]> sieve = ThreadLocal.withInitial(() -> null);

    /**
     * Sieve of Eratosthenes implementation.
     */
    public static final boolean sieveOfEratosthenes(int number) {
        if (number <= 1) {
            return false;
        }

        boolean[] localSieve = sieve.get();

        if (localSieve == null || number >= localSieve.length) {
            int newSize = Math.max(number + 1, localSieve != null ? localSieve.length * 2 : 2);
            boolean[] newSieve = new boolean[newSize];

            if (localSieve != null) {
                System.arraycopy(localSieve, 0, newSieve, 0, localSieve.length);
            }

            localSieve = newSieve;
            for (int i = 2; i <= Math.sqrt(localSieve.length); i++) {
                if (!localSieve[i]) {
                    for (int j = i * i; j < localSieve.length; j += i) {
                        localSieve[j] = true;
                    }
                }
            }

            sieve.set(localSieve);
        }

        return !localSieve[number];
    }

    /**
     * Miller-Rabin primality test (deterministic for numbers < 10^18).
     */
    public static final boolean millerRabinTest(int number) {
        final List<Integer> witnesses = Arrays.asList(2, 325, 9375, 28178, 450775, 9780504, 1795265022);

        if (number <= 1) return false;
        if (number == 2 || number == 3) return true;

        int s = 0;
        int d = number - 1;

        // Decompose (number - 1) into 2^s * d
        while (d % 2 == 0) {
            d /= 2;
            s++;
        }

        for (int a : witnesses) {
            if (a > number) break;

            int x = fastExponentiationModulo(a, d, number);
            if (x == 1 || x == number - 1) continue;

            boolean isComposite = true;
            for (int r = 1; r < s; r++) {
                x = (x * x) % number;
                if (x == number - 1) {
                    isComposite = false;
                    break;
                }
            }

            if (isComposite) return false;
        }

        return true;
    }

    /**
     * Fast modular exponentiation.
     */
    private static int fastExponentiationModulo(int base, int exponent, int mod) {
        long result = 1;
        long power = base % mod;

        while (exponent > 0) {
            if ((exponent & 1) == 1) {
                result = (result * power) % mod;
            }
            power = (power * power) % mod;
            exponent >>= 1;
        }

        return (int) result;
    }
}

