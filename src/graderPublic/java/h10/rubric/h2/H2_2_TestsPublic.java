package h10.rubric.h2;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.rubric.TestConstants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Defines public test cases for the H2.2 assignment.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H2.2 | In-Place")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H2_2_TestsPublic extends H2_TestsPublic {
    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider() {
        return MySetInPlace::new;
    }

    @Order(0)
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für simple Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H2_Criterion_01.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testSimple(JsonParameterSet parameters) {
        super.testSimple(parameters);
    }
}
