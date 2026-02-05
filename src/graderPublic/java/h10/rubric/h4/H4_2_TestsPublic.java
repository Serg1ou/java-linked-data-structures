package h10.rubric.h4;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.TutorMySetInPlace;
import h10.rubric.TestConstants;
import h10.rubric.TutorAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Defines public test cases for the H4.2 assignment.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H4.2 | In-Place")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H4_2_TestsPublic extends H4_TestsPublic {

    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider() {
        return MySetInPlace::new;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> fallbackProvider() {
        return TutorMySetInPlace::new;
    }

    @Override
    protected <T extends Comparable<T>> QuadConsumer<MySet<T>, MySet<T>[], MySet<T>, Context.Builder<?>> requirementCheck() {
        return TutorAssertions::assertInPlace;
    }

    @Order(0)
    @DisplayName("Die Methode intersectionListItems(ListItem) nimmt ein Element auf, falls das Element in allen "
        + "Mengen enthalten ist.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_01.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testAddElements(JsonParameterSet parameters) {
        super.testAddElements(parameters);
    }

    @Order(1)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis zurück, wenn die Hauptmenge "
        + "leer ist.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_02.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testEmptySet(JsonParameterSet parameters) {
        super.testEmptySet(parameters);
    }

    @Order(2)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für eine leere "
        + "Eingabemenge(n) zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_03.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testInputEmpty(JsonParameterSet parameters) {
        super.testInputEmpty(parameters);
    }
}
