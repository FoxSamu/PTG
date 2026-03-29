package dev.runefox.ptg.rng;

import java.util.Map;

import dev.runefox.ptg.noise.util.Hash;

final class RNGUtil {
    static final int[] SOME_PRIMES = {
        3, 5, 7, 11,
        13, 17, 19, 23,
        29, 31, 37, 41,
        43, 47, 53, 59,
        61, 67, 71, 73,
        79, 83, 89, 97
    };

    private static final int SEED_1 = 0x93FA1C43;
    private static final int SEED_2 = 0x37718FED;

    private static final long HASH_SCRAMBLER = 0x5DEECE66DL;

    static long seed(int x) {
        return x ^ SEED_1;
    }

    static long seed(long x, long y) {
        var h1 = (long) Hash.hash2I(SEED_1, x, y);
        var h2 = (long) Hash.hash2I(SEED_2, x, y);
        return h1 << 32 | h2;
    }

    static long seed(long x, long y, long z) {
        var h1 = (long) Hash.hash3I(SEED_1, x, y, z);
        var h2 = (long) Hash.hash3I(SEED_2, x, y, z);
        return h1 << 32 | h2;
    }

    static long seed(long x, long y, long z, long w) {
        var h1 = (long) Hash.hash4I(SEED_1, x, y, z, w);
        var h2 = (long) Hash.hash4I(SEED_2, x, y, z, w);
        return h1 << 32 | h2;
    }

    static long tryHashAsLong(Object value) {
        return switch (value) {
            case null -> 0L;
            case LongHashable s -> s.longHashCode();
            case CharSequence s -> hash(s);
            case byte[] a -> hash(a);
            case short[] a -> hash(a);
            case int[] a -> hash(a);
            case long[] a -> hash(a);
            case float[] a -> hash(a);
            case double[] a -> hash(a);
            case boolean[] a -> hash(a);
            case char[] a -> hash(a);
            case Object[] a -> hashRecursively(a);
            case Iterable<?> i -> hashRecursively(i);
            case Map<?, ?> m -> hashRecursively(m);
            case Byte i -> i;
            case Short i -> i;
            case Integer i -> i;
            case Long i -> i;
            case Float f -> Float.floatToIntBits(f);
            case Double f -> Double.doubleToLongBits(f);
            case Character c -> c;
            case Boolean b -> b ? 1 : 0;
            default -> value.hashCode();
        };
    }

    private static long hashRecursively(Object[] a) {
        var hash = (long) SEED_1;
        for (var i : a) {
            hash *= 31L;
            hash += tryHashAsLong(i);
        }
        return hash;
    }

    private static long hashRecursively(Iterable<?> a) {
        var hash = (long) SEED_1;
        for (var i : a) {
            hash *= 31L;
            hash += tryHashAsLong(i);
        }
        return hash;
    }

    private static long hashRecursively(Map<?, ?> a) {
        var hash = (long) SEED_1;
        for (var i : a.entrySet()) {
            hash *= 31L;
            hash += tryHashAsLong(i.getKey());
            hash *= 31L;
            hash += tryHashAsLong(i.getValue());
        }
        return hash;
    }

    private static long hash(byte[] a) {
        var hash = (long) SEED_1;

        for (var b : a) {
            hash *= 31L;
            hash += (long) b * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(short[] a) {
        var hash = (long) SEED_1;

        for (var i : a) {
            hash *= 31L;
            hash += (long) i * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(int[] a) {
        var hash = (long) SEED_1;

        for (var i : a) {
            hash *= 31L;
            hash += (long) i * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(long[] a) {
        var hash = (long) SEED_1;

        for (var i : a) {
            hash *= 31L;
            hash += i * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(float[] a) {
        var hash = (long) SEED_1;

        for (var i : a) {
            hash *= 31L;
            hash += Float.floatToIntBits(i) * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(double[] a) {
        var hash = (long) SEED_1;

        for (var i : a) {
            hash *= 31L;
            hash += Double.doubleToLongBits(i) * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(boolean[] a) {
        var hash = (long) SEED_1;

        for (var i : a) {
            hash *= 31L;
            hash += (i ? 1 : 0) * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(char[] a) {
        var hash = (long) SEED_1;

        for (var i : a) {
            hash *= 31L;
            hash += i * HASH_SCRAMBLER;
        }

        return hash;
    }

    private static long hash(CharSequence a) {
        var hash = (long) SEED_1;

        var l = a.length();
        for (var i = 0; i < l; i ++) {
            hash *= 31L;
            hash += a.charAt(i) * HASH_SCRAMBLER;
        }

        return hash;
    }
}
