package dev.runefox.ptg.util;

import java.util.*;
import java.util.function.IntUnaryOperator;
import java.util.function.ToIntFunction;
import java.util.random.RandomGenerator;

import dev.runefox.ptg.rng.RNG;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

/**
 * A list of weighted values that can be randomly selected based on weight.
 */
@SuppressWarnings("unchecked")
public class WeightedRandom<T> {
    private final int totalWeight;
    private final int weightGcd;
    private final List<Weighted<T>> elements;
    private final Map<T, Integer> weights;
    private final Selector selector;

    @SuppressWarnings("rawtypes")
    private WeightedRandom(int totalWeight, int weightGcd, List<Weighted<? extends T>> elements, Map<T, Integer> weights, Selector selector) {
        this.totalWeight = totalWeight;
        this.weightGcd = weightGcd;
        this.elements = (List<Weighted<T>>) (List) elements;
        this.weights = weights;
        this.selector = selector;
    }

    /**
     * Returns the total weight of the elements in this list.
     *
     * @return The total weight.
     */
    public int getTotalWeight() {
        return totalWeight;
    }

    /**
     * Returns the greatest common divisor of all the weights in this list. If this list has no elements, then, and only
     * then, 0 is returned. In any other case, the returned value is nonzero and positive.
     *
     * @return The GCD of the weights.
     */
    public int getWeightGcd() {
        return weightGcd;
    }

    /**
     * Returns the list of elements of this weighted list.
     *
     * @return A list of elements.
     */
    public List<Weighted<T>> getElements() {
        return elements;
    }

    /**
     * Returns a map from values to weights.
     *
     * @return A map of weights.
     */
    public Map<T, Integer> getWeights() {
        return weights;
    }

    /**
     * Returns the weight of the given value. Returns 0 when the value is not present in this list.
     *
     * @param value The value.
     * @return The weight of the value.
     */
    public int getWeight(T value) {
        return weights.getOrDefault(value, 0);
    }

    /**
     * Returns whether this list has no elements.
     *
     * @return True if the list is empty.
     */
    public boolean isEmpty() {
        return selector == null;
    }

    /**
     * Returns the bound of the random value that should be generated to pick random values. This is the value that
     * should be passed to {@link RNG#nextInt(int) nextInt}. If the list has no elements, then, and only then, 0 is
     * returned. In any other case, the returned value is positive and nonzero.
     *
     * @return The random bound.
     */
    public int getRandomBound() {
        if (selector == null) {
            return 0;
        }
        return selector.total;
    }

    /**
     * Picks a value based on a pre-generated random number. The random number should be generated as per {@link RNG#nextInt(int) nextInt} with
     * {@link #getRandomBound} as bound. If the list is empty, or the given number is outside the range of 0 (inclusive) and {@link #getRandomBound}
     * (exclusive), then null is returned.
     *
     * @param randomValue The random number.
     * @return A value, picked by weight.
     */
    public T pickOrNull(int randomValue) {
        if (randomValue < 0 || randomValue >= getRandomBound()) {
            return null;
        }

        return (T) selector.select(randomValue);
    }

    /**
     * Picks a value based on a pre-generated random number. The random number should be generated as per {@link RNG#nextInt(int) nextInt} with
     * {@link #getRandomBound} as bound. If the list is empty, or the given number is outside the range of 0 (inclusive) and {@link #getRandomBound}
     * (exclusive), then a {@link NoSuchElementException} is thrown.
     *
     * @param randomValue The random number.
     * @return A value, picked by weight.
     * @throws NoSuchElementException Thrown when the list is empty or the random value is out of range.
     */
    public T pick(int randomValue) {
        if (selector == null) {
            throw new NoSuchElementException("Weighted random has no elements");
        }

        if (randomValue < 0 || randomValue >= getRandomBound()) {
            throw new NoSuchElementException("Random value " + randomValue + " out of bounds 0..<" + selector.total + ". Use getRandomBound() to obtain maximum random value.");
        }

        return (T) selector.select(randomValue);
    }

    /**
     * Returns a random value from this list, picked by weight. Returns null if this list is empty.
     *
     * @param rng A {@link RNG} to generate a random number with.
     * @return The randomly picked value.
     */
    public T pickOrNull(RNG rng) {
        if (selector == null) {
            return null;
        }

        return pick(rng);
    }

    /**
     * Returns a random value from this list, picked by weight. Throws a {@link NoSuchElementException} if this list is empty.
     *
     * @param rng A {@link RNG} to generate a random number with.
     * @return The randomly picked value.
     * @throws NoSuchElementException Thrown when the list is empty.
     */
    public T pick(RNG rng) {
        return pick(rng.nextInt(getRandomBound()));
    }

    /**
     * Returns a random value from this list, picked by weight. Returns null if this list is empty.
     *
     * @param rng A {@link RandomGenerator} to generate a random number with.
     * @return The randomly picked value.
     */
    public T pickOrNull(RandomGenerator rng) {
        if (selector == null) {
            return null;
        }

        return pick(rng);
    }

    /**
     * Returns a random value from this list, picked by weight. Throws a {@link NoSuchElementException} if this list is empty.
     *
     * @param rng A {@link RNG} to generate a random number with.
     * @return The randomly picked value.
     * @throws NoSuchElementException Thrown when the list is empty.
     */
    public T pick(RandomGenerator rng) {
        return pick(rng.nextInt(getRandomBound()));
    }

    /**
     * Returns a random value from this list, picked by weight. Returns null if this list is empty.
     *
     * @param nextInt A {@link IntUnaryOperator} wrapping a related {@code nextInt} operation. This function must return
     *                a value between 0 (inclusive) and the upper bound passed to the function (exclusive), otherwise
     *                the behavior is undefined.
     * @return The randomly picked value.
     */
    public T pickOrNull(IntUnaryOperator nextInt) {
        if (selector == null) {
            return null;
        }

        return pick(nextInt);
    }

    /**
     * Returns a random value from this list, picked by weight. Throws a {@link NoSuchElementException} if this list is empty.
     *
     * @param nextInt A {@link IntUnaryOperator} wrapping a related {@code nextInt} operation. This function must return
     *                a value between 0 (inclusive) and the upper bound passed to the function (exclusive), otherwise
     *                the behavior is undefined.
     * @return The randomly picked value.
     * @throws NoSuchElementException Thrown when the list is empty.
     */
    public T pick(IntUnaryOperator nextInt) {
        return pick(nextInt.applyAsInt(getRandomBound()));
    }

    private static abstract class Selector {
        final int total;

        Selector(int total) {
            this.total = total;
        }

        abstract Object select(int random);
    }

    private static abstract class SearchSelector extends Selector {
        final int[] offsets;
        final Object[] values;

        SearchSelector(int total, int gcd, int[] weights, Object[] values) {
            super(total / gcd);

            var num = weights.length;
            var offsets = new int[num];
            var weightSum = 0;

            for (var i = 0; i < num; i++) {
                weightSum += weights[i] / gcd;
                offsets[i] = weightSum;
            }

            this.offsets = offsets;
            this.values = values;
        }

        @Override
        Object select(int random) {
            var index = search(random, offsets);
            return values[index];
        }

        abstract int search(int random, int[] offsets);
    }

    private static class LinearSelector extends SearchSelector {
        LinearSelector(int total, int gcd, int[] weights, Object[] values) {
            super(total, gcd, weights, values);
        }

        @Override
        int search(int random, int[] offsets) {
            var len = offsets.length;

            var i = 0;
            while (i < len && offsets[i] <= random) {
                i++;
            }

            return i - 1;
        }
    }

    private static class BinarySelector extends SearchSelector {
        BinarySelector(int total, int gcd, int[] weights, Object[] values) {
            super(total, gcd, weights, values);
        }

        @Override
        int search(int random, int[] offsets) {
            var lo = 0;
            var hi = offsets.length;

            // Binary search
            while (hi > lo) {
                var md = lo + (hi - lo) / 2;

                if (random < offsets[md]) {
                    hi = md;
                } else {
                    lo = md + 1;
                }
            }

            return lo - 1;
        }
    }

    private static class FlatSelector extends Selector {
        final Object[] flattened;

        FlatSelector(int total, int gcd, int[] weights, Object[] values) {
            super(total / gcd);

            // Divide by gcd to save space
            var flattened = new Object[total / gcd];
            var fi = 0;

            var len = weights.length;

            for (var i = 0; i < len; i++) {
                var wgt = weights[i] / gcd;
                var val = values[i];

                while (wgt > 0) {
                    flattened[fi++] = val;
                    wgt--;
                }
            }

            this.flattened = flattened;
        }

        @Override
        Object select(int random) {
            return flattened[random];
        }
    }

    private static class SingletonSelector extends Selector {
        private final Object value;

        private SingletonSelector(Object value) {
            super(1);
            this.value = value;
        }

        @Override
        Object select(int random) {
            return value;
        }
    }

    private static Selector buildSelector(int total, int gcd, int elements, int[] weights, Object[] values) {
        if (total / gcd < 64) {
            return new FlatSelector(total, gcd, weights, values);
        }

        if (elements < 16) {
            return new LinearSelector(total, gcd, weights, values);
        }

        return new BinarySelector(total, gcd, weights, values);
    }


    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

    /**
     * Creates a {@link Builder}.
     *
     * @return The builder.
     */
    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Creates a {@link Builder}.
     *
     * @param ignored An ignored parameter, used purely to help Java's type inference.
     * @return The builder.
     */
    public static <T> Builder<T> builder(Class<T> ignored) { // Useful to help java's type checker without ugly syntax
        return new Builder<>();
    }

    /**
     * Creates an empty {@link WeightedRandom}.
     *
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of() {
        return new WeightedRandom<>(0, 0, List.of(), Map.of(), null);
    }

    /**
     * Creates a singleton {@link WeightedRandom} with a single element of given weight.
     *
     * @param element The element.
     * @param weight  The weight.
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of(T element, int weight) {
        return of(new Weighted<>(element, weight));
    }

    /**
     * Creates a singleton {@link WeightedRandom} with a single element of weight 1.
     *
     * @param element The element.
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of(T element) {
        return of(element, 1);
    }

    /**
     * Creates a singleton {@link WeightedRandom} with a single element.
     *
     * @param element The element.
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of(Weighted<? extends T> element) {
        if (element.weight() <= 0) {
            return of();
        }

        return new WeightedRandom<>(
                element.weight(),
                element.weight(),
                List.of(element),
                Map.of(element.value(), element.weight()),
                new SingletonSelector(element.value())
        );
    }

    /**
     * Creates a {@link WeightedRandom} with given elements.
     *
     * @param elements The elements.
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of(Weighted<T>... elements) {
        return WeightedRandom.<T>builder()
                .addAll(Arrays.asList(elements))
                .build();
    }

    /**
     * Creates a {@link WeightedRandom} with given elements and equal weights of 1.
     *
     * @param elements The elements.
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of(T... elements) {
        return WeightedRandom.<T>builder()
                .addAll(Arrays.asList(elements), 1)
                .build();
    }

    /**
     * Creates a {@link WeightedRandom} with given elements.
     *
     * @param elements The elements.
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of(Collection<? extends Weighted<? extends T>> elements) {
        return WeightedRandom.<T>builder()
                .addAll(elements)
                .build();
    }

    /**
     * Creates a {@link WeightedRandom} with given elements of equal weight.
     *
     * @param elements The elements.
     * @param weight   The weight for each element.
     * @return The created {@link WeightedRandom}.
     */
    public static <T> WeightedRandom<T> of(Collection<? extends T> elements, int weight) {
        return WeightedRandom.<T>builder()
                .addAll(elements, weight)
                .build();
    }

    /**
     * Creates a {@link WeightedRandom} with given elements, mapping them to weights using a function.
     *
     * @param elements The elements.
     * @param weight   The function that computes the weight for each element.
     * @return The created {@link WeightedRandom}.
     */
    public static <T, U extends T> WeightedRandom<T> of(Collection<? extends U> elements, ToIntFunction<? super U> weight) {
        return WeightedRandom.<T>builder()
                .addAll(elements, weight)
                .build();
    }

    public static class Builder<T> {
        private final List<Weighted<? extends T>> elements = new ArrayList<>();

        private Builder() {
        }

        /**
         * Adds an element with given weight.
         *
         * @param element The element.
         * @param weight  The weight.
         */
        public Builder<T> add(T element, int weight) {
            return add(new Weighted<>(element, weight));
        }

        /**
         * Adds an element with weight 1.
         *
         * @param element The element.
         */
        public Builder<T> add(T element) {
            return add(element, 1);
        }

        /**
         * Adds an element from a {@link Weighted}
         *
         * @param element The element.
         */
        public Builder<T> add(Weighted<? extends T> element) {
            if (element.weight() <= 0) {
                return this;
            }

            elements.add(element);
            return this;
        }

        /**
         * Adds a collection of elements with given weight.
         *
         * @param elements The elements.
         * @param weight   The weight.
         */
        public Builder<T> addAll(Collection<? extends T> elements, int weight) {
            for (var elem : elements) {
                add(elem, weight);
            }
            return this;
        }

        /**
         * Adds a collection of elements, mapping them to weights using a function.
         *
         * @param elements The elements.
         * @param weight   The weight function.
         */
        public <U extends T> Builder<T> addAll(Collection<? extends U> elements, ToIntFunction<? super U> weight) {
            for (var elem : elements) {
                add(elem, weight.applyAsInt(elem));
            }
            return this;
        }

        /**
         * Adds a collection of elements with weight 1.
         *
         * @param elements The elements.
         */
        public Builder<T> addAll(Collection<? extends Weighted<? extends T>> elements) {
            for (var elem : elements) {
                add(elem);
            }
            return this;
        }

        /**
         * Adds a map of elements associated with weights.
         *
         * @param elements The elements.
         */
        public Builder<T> addAll(Map<? extends T, Integer> elements) {
            return addAll(elements.keySet(), elements::get);
        }

        /**
         * Adds all elements from another builder.
         */
        public Builder<T> addAll(Builder<T> other) {
            return addAll(other.elements);
        }

        /**
         * Adds all elements from another {@link WeightedRandom}.
         */
        public Builder<T> addAll(WeightedRandom<T> other) {
            return addAll(other.getElements());
        }

        /**
         * Builds a {@link WeightedRandom} from all added elements.
         *
         * @return The created {@link WeightedRandom}.
         */
        public WeightedRandom<T> build() {
            var len = elements.size();
            if (len == 0) {
                return of();
            }

            if (len == 1) {
                var elem = elements.getFirst();
                return of(elem);
            }

            // Flatten to map so that weights add up
            // Use linked map to keep semantics dependent on builder order
            // and not hash code
            var map = new Object2IntLinkedOpenHashMap<T>();
            map.defaultReturnValue(0);

            for (var elem : elements) {
                map.addTo(elem.value(), elem.weight());
            }

            // Since elements might have merged, the amount of distinct
            // elements may have changed
            len = map.size();

            var weights = new int[len];
            var values = new Object[len];

            var gcd = 0;
            var total = 0;

            var i = 0;
            for (var entry : map.object2IntEntrySet()) {
                var val = entry.getKey();
                var wgt = entry.getIntValue();

                values[i] = val;
                weights[i] = wgt;

                total += wgt;

                if (gcd == 0) {
                    gcd = wgt;
                } else if (gcd > 1 && gcd != wgt) {
                    gcd = gcd(wgt, gcd);
                }

                i++;
            }

            return new WeightedRandom<>(
                    total,
                    gcd,
                    List.copyOf(elements),
                    Object2IntMaps.unmodifiable(map),
                    buildSelector(total, gcd, len, weights, values)
            );
        }
    }
}
