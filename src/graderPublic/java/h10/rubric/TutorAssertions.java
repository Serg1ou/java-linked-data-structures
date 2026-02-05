package h10.rubric;

import h10.ListItem;
import h10.MySet;
import h10.Sets;
import h10.util.VisitorElement;
import org.opentest4j.AssertionFailedError;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.util.Arrays;
import java.util.List;

/**
 * Utility class for tutor assertions.
 *
 * @author Nhan Huynh
 */
public final class TutorAssertions {

    /**
     * This class should not be instantiated.
     */
    private TutorAssertions() {

    }

    /**
     * Returns the identity hash code of the given element.
     *
     * @param element the element to get the identity hash code of
     * @return the identity hash code of the given element
     */
    public static String identityHashCode(VisitorElement<?> element) {
        return element.peek().getClass() + "@" + System.identityHashCode(element.peek());
    }

    /**
     * Returns the identity hash code of the given item.
     *
     * @param item the item to get the identity hash code of
     * @return the identity hash code of the given item
     */
    public static String identityHashCode(ListItem<?> item) {
        return item.getClass().getName() + "@" + System.identityHashCode(item);
    }

    /**
     * Asserts that the given source is copied to the given output.
     *
     * @param source         the source of the operation to check
     * @param output         the output of the operation to check
     * @param contextBuilder the context builder to report the result to
     * @param <T>            the type of the elements in the set
     * @throws AssertionFailedError if the given source is not copied to the given output
     */
    public static <T extends Comparable<T>> void assertAsCopy(
        MySet<T> source,
        MySet<T> output,
        Context.Builder<?> contextBuilder
    ) {
        List<String> sourceHashCode = Sets.itemsStream(source).map(TutorAssertions::identityHashCode).toList();
        List<String> otherIdentityHashCodes = Sets.itemsStream(output).map(TutorAssertions::identityHashCode).toList();
        Context hashContext = Assertions2.contextBuilder().subject("Hashcodes")
            .add("Source", sourceHashCode)
            .add("Output", otherIdentityHashCodes)
            .build();
        contextBuilder.add("As-Copy", hashContext);
        Context context = contextBuilder.build();
        Sets.itemsStream(output)
            .forEach(other -> {
                String otherHashCode = identityHashCode(other);
                sourceHashCode.forEach(currentHashCode -> Assertions2.assertNotEquals(
                        otherHashCode, currentHashCode,
                        context,
                        result -> "Node %s (%s) was not copied, got %s"
                            .formatted(other, otherHashCode, currentHashCode)
                    )
                );
            });
    }

    /**
     * Asserts that the given source/inputs is copied to the given output.
     *
     * @param source         the source of the operation to check
     * @param inputs         the inputs of the operation to check
     * @param output         the output of the operation to check
     * @param contextBuilder the context builder to report the result to
     * @param <T>            the type of the elements in the set
     * @throws AssertionFailedError if the given source/inputs is not copied to the given output
     */
    public static <T extends Comparable<T>> void assertAsCopy(
        MySet<T> source,
        MySet<T>[] inputs,
        MySet<T> output,
        Context.Builder<?> contextBuilder
    ) {
        List<String> sourceHashCode = Sets.itemsStream(source).map(TutorAssertions::identityHashCode).toList();
        List<List<String>> inputsHashCode = Arrays.stream(inputs).map(input -> Sets.itemsStream(input)
            .map(TutorAssertions::identityHashCode)
            .toList()
        ).toList();
        List<String> otherIdentityHashCodes = Sets.itemsStream(output).map(TutorAssertions::identityHashCode).toList();
        Context hashContext = Assertions2.contextBuilder().subject("Hashcodes")
            .add("Source", sourceHashCode)
            .add("Input(s)", inputsHashCode)
            .add("Output", otherIdentityHashCodes)
            .build();
        contextBuilder.add("As-Copy", hashContext);
        Context context = contextBuilder.build();
        Sets.itemsStream(output).forEach(other -> {
            String otherHashCode = identityHashCode(other);
            sourceHashCode.forEach(sourceHash -> Assertions2.assertNotEquals(
                otherHashCode, sourceHash,
                context,
                result -> "Node %s (%s) was not copied, got %s (Match found in source)"
                    .formatted(other, otherHashCode, sourceHash)
            ));
            inputsHashCode.forEach(inputsHash -> inputsHash.forEach(inputHash -> Assertions2.assertNotEquals(
                otherHashCode, inputsHash,
                context,
                result -> "Node %s (%s) was not copied, got %s (Match found in inputs)"
                    .formatted(other, otherHashCode, inputsHash)
            )));
        });
    }

    /**
     * Asserts that the given source is not copied to the given output.
     *
     * @param source         the source of the operation to check
     * @param output         the output of the operation to check
     * @param contextBuilder the context builder to report the result to
     * @param <T>            the type of the elements in the set
     * @throws AssertionFailedError if the given source is copied to the given output
     */
    public static <T extends Comparable<T>> void assertInPlace(
        MySet<T> source,
        MySet<T> output,
        Context.Builder<?> contextBuilder
    ) {
        List<String> sourceHashCode = Sets.itemsStream(source).map(TutorAssertions::identityHashCode).toList();
        List<String> otherIdentityHashCodes = Sets.itemsStream(output).map(TutorAssertions::identityHashCode).toList();
        Context hashContext = Assertions2.contextBuilder().subject("In-Place")
            .add("Source", sourceHashCode)
            .add("Output", otherIdentityHashCodes)
            .build();
        contextBuilder.add("Hashcodes", hashContext);
        Sets.itemsStream(output)
            .forEach(other -> {
                    String otherHashCode = identityHashCode(other);
                    Assertions2.assertTrue(sourceHashCode.stream().anyMatch(otherHashCode::equals),
                        contextBuilder.build(),
                        result -> "Cannot find the same node (%s) after the operation. Node was probably copied."
                            .formatted(otherHashCode));
                }
            );
    }

    /**
     * Asserts that the given source/inputs is not copied to the given output.
     *
     * @param source         the source of the operation to check
     * @param inputs         the inputs of the operation to check
     * @param output         the output of the operation to check
     * @param contextBuilder the context builder to report the result to
     * @param <T>            the type of the elements in the set
     * @throws AssertionFailedError if the given source/inputs is not copied to the given output
     */
    public static <T extends Comparable<T>> void assertInPlace(
        MySet<T> source,
        MySet<T>[] inputs,
        MySet<T> output,
        Context.Builder<?> contextBuilder
    ) {
        List<String> sourceHashCode = Sets.itemsStream(source).map(TutorAssertions::identityHashCode).toList();
        List<List<String>> inputsHashCode = Arrays.stream(inputs).map(input -> Sets.itemsStream(input)
            .map(TutorAssertions::identityHashCode)
            .toList()
        ).toList();
        List<String> otherIdentityHashCodes = Sets.itemsStream(output).map(TutorAssertions::identityHashCode).toList();
        Context hashContext = Assertions2.contextBuilder().subject("Hashcodes")
            .add("Source", sourceHashCode)
            .add("Input(s)", inputsHashCode)
            .add("Output", otherIdentityHashCodes)
            .build();
        contextBuilder.add("In-Place", hashContext);
        Context context = contextBuilder.build();
        Sets.itemsStream(output).forEach(other -> {
            String otherHashCode = identityHashCode(other);
            Assertions2.assertTrue(
                sourceHashCode.stream().anyMatch(otherHashCode::equals)
                    || inputsHashCode.stream().anyMatch(inputsHash -> inputsHash.stream()
                    .anyMatch(otherHashCode::equals)),
                context,
                result -> "Cannot find the same node (%s) after the operation. Node was probably copied."
                    .formatted(otherHashCode));
        });
    }
}
