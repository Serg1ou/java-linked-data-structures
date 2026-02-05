package h10.rubric;

import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.MySetInPlace;
import h10.Sets;
import h10.util.ListItems;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.opentest4j.AssertionFailedError;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * Defines a base class for testing a method for the H3 or H4 assignment. A subclass of this class needs
 * to implement at least {@link #getClassType()} and {@link #setProvider()} since the criteria for {@link MySetAsCopy}
 * and {@link MySetInPlace} are the same.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
public abstract class H3_or4_Tests extends H10_Test {

    /**
     * Checks whether the result of the operation matches the expected one.
     *
     * @param parameters the parameter set providing the test data
     */
    @SuppressWarnings("unchecked")
    protected void assertEqualSet(JsonParameterSet parameters) {
        Context.Builder<?> contextBuilder = contextBuilder();
        // Inputs
        ListItem<Integer> sourceHead = parameters.get("source");
        MySet<Integer> source = createFallbackSet(sourceHead);
        ListItem<ListItem<Integer>> inputsHead = parameters.get("inputs");
        ListItem<MySet<Integer>> inputs = ListItems.map(inputsHead, this::createFallbackSet);
        contextBuilder.add("Input", getInputContext(getDefaultComparator(), source, inputs));

        // Result
        MySet<Integer> result = operation().apply(source, inputs);
        ListItem<Integer> expectedHead = parameters.get("expected");
        MySet<Integer> expected = createSet(expectedHead);
        Context resultContext = Assertions2.contextBuilder().subject("Output")
            .add("Source set afterwards", source.toString())
            .add("Input sets afterwards", String.valueOf(inputs))
            .add("Actual output set", result.toString())
            .add("Expected output set", expected.toString())
            .build();
        contextBuilder.add("Output", resultContext);

        Iterator<Integer> actualIt = Sets.iterator(result);
        Iterator<Integer> expectedIt = Sets.iterator(expected);

        Context context = contextBuilder.build();
        while (actualIt.hasNext() && expectedIt.hasNext()) {
            Integer a = actualIt.next();
            Integer e = expectedIt.next();
            Assertions2.assertEquals(
                e,
                a,
                context,
                r -> "Expected %s in result set, but got %s".formatted(e, a)
            );
        }

        // The expected size must be equal to actual size
        Assertions2.assertFalse(expectedIt.hasNext(), context,
            r -> "Expected list contains more element than actual list");
        Assertions2.assertFalse(actualIt.hasNext(), context,
            r -> "Actual list contains more element than expected list");

        assertRequirement(source, inputs, result, contextBuilder);
    }

    /**
     * Creates a fallback set from the given head and comparator.
     *
     * @param head the head of the list to create a set from
     * @param <T>  the type of the elements in the set
     * @return a set from the given head and comparator
     */
    public <T extends Comparable<? super T>> MySet<T> createFallbackSet(ListItem<T> head) {
        return this.<T>fallbackProvider().apply(head, getDefaultComparator());
    }

    /**
     * Returns the input context information of an operation.
     *
     * @param cmp    the comparator used to compare the elements in the set
     * @param source the source set to execute the operation on
     * @param inputs s the input sets which will be used in the operation
     * @return the input context information of an operation
     */
    protected Context getInputContext(
        Comparator<?> cmp,
        MySet<?> source,
        ListItem<MySet<Integer>> inputs
    ) {
        return Assertions2.contextBuilder().subject("Input")
            .add("Comparator", cmp)
            .add("Source", source.toString())
            .add("Input(s)", String.valueOf(inputs))
            .build();
    }

    /**
     * Returns the operation to be tested.
     *
     * @return the operation to be tested
     */
    protected abstract BiFunction<MySet<Integer>, ListItem<MySet<Integer>>, MySet<Integer>> operation();

    /**
     * Checks whether the given input and output satisfies the requirement of the method (as copy or in place).
     *
     * @param <T>     the type of the elements in the input
     * @param inputs  the inputs to check
     * @param output  the output to check
     * @param context the context to report the result to
     * @throws AssertionFailedError if the input does not satisfy the requirement
     */
    @SuppressWarnings("unchecked")
    protected <T extends Comparable<T>> void assertRequirement(
        MySet<T> source,
        ListItem<MySet<T>> inputs,
        MySet<T> output,
        Context.Builder<?> context
    ) {
        this.<T>requirementCheck().accept(
            source,
            (MySet<T>[]) ListItems.stream(inputs).toArray(MySet<?>[]::new),
            output,
            context
        );
    }

    /**
     * A fallback set provider used if we need another isntance of the set (e.g., subclass). If this is not overriden
     * the function behaves the same as {@link #setProvider()}
     *
     * @param <T> the type of the elements in the set
     * @return the fallback provider
     */
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> fallbackProvider() {
        return setProvider();
    }

    /**
     * Returns a function that accepts an input and output set and a context and checks whether the given input and
     * output satisfies the requirement of the method (as copy or in place).
     *
     * @param <T> the type of the elements in the input
     * @return a function that accepts an input and output set and a context and checks whether the given input and
     */
    protected abstract <T extends Comparable<T>> QuadConsumer<
        MySet<T>,
        MySet<T>[],
        MySet<T>,
        Context.Builder<?>
        > requirementCheck();

    /**
     * A {@link Consumer} that acceps four arguments.
     *
     * @param <A>    the first argument
     * @param <B>    the second argument
     * @param <C>    the third argument
     * @param <D>the forth argument
     */
    protected interface QuadConsumer<A, B, C, D> {

        /**
         * Consumes the given arguments.
         *
         * @param a the first argument
         * @param b the second argument
         * @param c the third argument
         * @param d the forth argument
         */
        void accept(A a, B b, C c, D d);
    }
}
