package h10.rubric.h2;

import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.MySetInPlace;
import h10.Sets;
import h10.rubric.H10_Test;
import h10.rubric.TestConstants;
import h10.util.ListItems;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;

import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Defines a base class for testing a method for the H2 assignment. A subclass of this class needs
 * to implement at least {@link #getClassType()} and {@link #setProvider()} since the criteria for {@link MySetAsCopy}
 * and {@link MySetInPlace} are the same.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H2 | cartesianProduct(MySet)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
public abstract class H2_Tests extends H10_Test {

    /**
     * The name of the method to be tested.
     */
    protected static final String METHOD_NAME = "cartesianProduct";

    @Override
    public String getMethodName() {
        return METHOD_NAME;
    }

    /**
     * Checks whether the operation result is correct.
     *
     * @param parameters the parameter set providing the test data
     */
    protected void assertTuples(JsonParameterSet parameters) {
        Context.Builder<?> contextBuilder = contextBuilder();

        // Input
        ListItem<Integer> head = parameters.get("source");
        MySet<Integer> source = createSet(head);

        ListItem<Integer> otherHead = parameters.get("other");
        MySet<Integer> other = createSet(otherHead);
        contextBuilder.add("Input", getInputContext(getDefaultComparator(), source, other));

        // Result
        MySet<ListItem<Integer>> result = source.cartesianProduct(other);
        ListItem<ListItem<Integer>> expectedHead = parameters.get("expected2D");

        Context.Builder<?> resultContext = Assertions2.contextBuilder().subject("Output");
        resultContext.add("Source set afterwards", source.toString())
            .add("Other set afterwards", other.toString())
            .add("Actual output set", result.toString())
            .add("Expected output set", String.valueOf(expectedHead));
        contextBuilder.add("Output", resultContext.build());

        // Validation of the result
        ListItems.stream(expectedHead).forEach(tuple -> {
            if (Sets.stream(result)
                .noneMatch(actual -> Objects.equals(tuple.key, actual.key)
                    && tuple.next != null && actual.next != null
                    && Objects.equals(tuple.next.key, actual.next.key))) {
                Context checkContext = Assertions2.contextBuilder().add("Expected tuple", tuple.toString())
                    .add("Actual tuple", "None").build();
                Assertions2.fail(
                    contextBuilder.add("Tuple check", checkContext).build(),
                    r -> "Expected tuple %s not found in result".formatted(tuple.toString())
                );
            }
        });
    }

    /**
     * Returns the input context information of an operation.
     *
     * @param cmp    the comparator used to compare the elements in the set
     * @param source the source set to execute the operation on
     * @param input  the input set which will be used in the operation
     * @return the input context information of an operation
     */
    protected Context getInputContext(Comparator<?> cmp, MySet<?> source, MySet<?> input) {
        return Assertions2.contextBuilder().subject("Input")
            .add("Source set", source.toString())
            .add("Comparator", cmp)
            .add("Input set", input.toString())
            .build();
    }
}
