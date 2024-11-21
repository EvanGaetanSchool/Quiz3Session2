package com.jwetherell.algorithms.mathematics;

import com.jwetherell.algorithms.numbers.Complex;

import java.util.ArrayList;
import java.util.Collections;

public class Multiplication {

    public static final long multiplication(int a, int b) {
        long result = ((long) a) * ((long) b);
        return (a == 0 || b == 0) ? 0 : result; // Fixed: Should return 0 if either number is 0
    }

    public static final long multiplyUsingLoop(int a, int b) {
        int absB = Math.abs(b);
        long result = 0; // Fixed: Initialize result to 0, not `a`
        for (int i = 0; i < absB; i++) { // Fixed: Start loop from 0 to ensure correct multiplication
            result += a;
        }
        return (b < 0) ? -result : result;
    }

    public static final long multiplyUsingRecursion(int a, int b) {
        int absB = Math.abs(b);
        if (absB == 0) // Fixed: Base case to handle 0 multiplication
            return 0;
        long result = a + multiplyUsingRecursion(a, absB - 1); // Fixed: Recursive call
        return (b < 0) ? -result : result;
    }

    public static final long multiplyUsingShift(int a, int b) {
        int absA = Math.abs(a);
        int absB = Math.abs(b);

        long result = 0L;
        while (absB > 0) { // Fixed: Iterate over absB, not absA
            if ((absB & 1) > 0) // Is odd
                result += absA;
            absB >>= 1;
            absA <<= 1;
        }

        return (a > 0 && b > 0 || a < 0 && b < 0) ? result : -result;
    }

    public static final long multiplyUsingLogs(int a, int b) {
        long absA = Math.abs((long) a); // Fixed: Cast to long to prevent overflow
        long absB = Math.abs((long) b);
        double result = Math.pow(10, (Math.log10(absA) + Math.log10(absB))); // Fixed: Use double for intermediate calculation
        return (a > 0 && b > 0 || a < 0 && b < 0) ? Math.round(result) : -Math.round(result);
    }

    public static String multiplyUsingFFT(String a, String b) {
        if (a.equals("0") || b.equals("0")) {
            return "0";
        }
        boolean negative = false;
        if ((a.charAt(0) == '-' && b.charAt(0) != '-') || (a.charAt(0) != '-' && b.charAt(0) == '-')) {
            negative = true;
        }
        if (a.charAt(0) == '-') {
            a = a.substring(1);
        }
        if (b.charAt(0) == '-') {
            b = b.substring(1);
        }
        int size = 1;
        while (size < (a.length() + b.length())) {
            size *= 2;
        }
        Complex[] aCoefficients = new Complex[size];
        Complex[] bCoefficients = new Complex[size];
        for (int i = 0; i < size; i++) {
            aCoefficients[i] = new Complex();
            bCoefficients[i] = new Complex();
        }
        for (int i = 0; i < a.length(); i++) {
            aCoefficients[i] = new Complex((double) (Character.getNumericValue(a.charAt(a.length() - i - 1))), 0.0);
        }
        for (int i = 0; i < b.length(); i++) {
            bCoefficients[i] = new Complex((double) (Character.getNumericValue(b.charAt(b.length() - i - 1))), 0.0);
        }

        FastFourierTransform.cooleyTukeyFFT(aCoefficients);
        FastFourierTransform.cooleyTukeyFFT(bCoefficients);

        for (int i = 0; i < size; i++) {
            aCoefficients[i] = aCoefficients[i].multiply(bCoefficients[i]);
        }

        FastFourierTransform.cooleyTukeyFFT(aCoefficients); // Fixed: Removed incorrect reverse of coefficients

        ArrayList<Integer> res = new ArrayList<>();
        int carry = 0;
        for (Complex coeff : aCoefficients) {
            int value = (int) Math.round(coeff.abs() / size) + carry; // Fixed: Carry needs to propagate correctly
            res.add(value % 10);
            carry = value / 10;
        }
        while (carry > 0) {
            res.add(carry % 10);
            carry /= 10;
        }

        Collections.reverse(res);
        StringBuilder result = new StringBuilder();
        if (negative) {
            result.append('-');
        }
        boolean startPrinting = false;
        for (Integer x : res) {
            if (x != 0) {
                startPrinting = true;
            }
            if (startPrinting) {
                result.append(x);
            }
        }
        return result.toString();
    }
    public static String multiplyUsingLoopWithStringInput(String a, String b) {
        int carry = 0, rem, flag = 0, lim1, lim2, mul;

        boolean aIsNegative = false;
        ArrayList<Integer> first = new ArrayList<>();
        for (char n : a.toCharArray()) {
            if (n == '-') {
                aIsNegative = true;
                continue;
            }
            first.add(n - '0');
        }

        boolean bIsNegative = false;
        ArrayList<Integer> second = new ArrayList<>();
        for (char n : b.toCharArray()) {
            if (n == '-') {
                bIsNegative = true;
                continue;
            }
            second.add(n - '0');
        }

        lim1 = first.size() - 1;
        lim2 = second.size() - 1;

        ArrayList<Integer> res = new ArrayList<>(Collections.nCopies(first.size() + second.size(), 0));
        for (int i = 0; i <= lim1; i++) {
            int k = i;
            for (int j = 0; j <= lim2; j++) {
                int f = first.get(i);
                int s = second.get(j);
                mul = f * s;
                res.set(k, res.get(k) + (mul / 10));
                k++;
                res.set(k, res.get(k) + (mul % 10));
            }
        }

        for (int i = (lim1 + lim2) + 1; i >= 0; i--) {
            if (flag == 1) {
                res.set(i, res.get(i) + carry);
                flag = 0;
            }

            if (res.get(i) >= 10 && i != 0) {
                rem = res.get(i) % 10;
                carry = res.get(i) / 10;
                res.set(i, rem);
                flag++;
            }
        }

        StringBuilder sb = new StringBuilder();
        if (aIsNegative ^ bIsNegative)
            sb.append('-');
        boolean zeroCheck = true;
        for (Integer s : res) {
            if (zeroCheck && s.equals(0))
                continue;
            zeroCheck = false;
            sb.append(s);
        }
        return sb.toString();
    }

    public static int multiplyUsingLoopWithIntegerInput(int a, int b) {
        boolean aIsNegative = a < 0;
        boolean bIsNegative = b < 0;
        a = Math.abs(a);
        b = Math.abs(b);

        int result = 0;
        for (int i = 0; i < b; i++) {
            result += a;
        }

        if (aIsNegative ^ bIsNegative)
            result = -result;
        return result;
    }
}
