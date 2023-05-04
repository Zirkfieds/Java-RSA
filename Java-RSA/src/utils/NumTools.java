package utils;

import java.util.Random;

public class NumTools {

    public static long genPrime(int digits) {
        Random rnd = new Random();
        long lb = (long) Math.pow(10, digits - 1);
        long ub = (long) Math.pow(10, digits) - 1;
        long n;
        do {
            n = rnd.nextLong(ub - lb + 1) + lb;
        } while (!isPrime(n));
        return n;
    }

    public static long genNum(long lb, long ub) {
        Random rnd = new Random();
        long n;
        do {
            n = rnd.nextLong(ub - lb + 1) + lb;
        } while (!isPrime(n));
        return n;
    }

    public static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCoprime(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a == 1;
    }

    public static long invMod(long a, long m) {
        if (!NumTools.isCoprime(a, m)) {
            return -1L;
        }
        long[] coef = extEuclidean(a, m);

        return (coef[0] % m + m) % m;
    }

    private static long[] extEuclidean(long a, long b) {
        long[] coef = new long[3];
        if (b == 0) {
            coef[0] = 1L;
            coef[2] = a;
        } else {
            long[] temp = extEuclidean(b, a % b);
            coef[0] = temp[1];
            coef[1] = temp[0] - (a / b) * temp[1];
            coef[2] = temp[2];
        }
        return coef;
    }

    public static long modExp(long base, long exponent, long modulus) {
        if (modulus == 1L) {
            return 0;
        }
        long result = 1;
        base = base % modulus;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            exponent = exponent >> 1;
            base = (base * base) % modulus;
        }
        return result;
    }
}

