package h10.rubric.h2;

import h10.MySetAsCopy;
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

import java.util.concurrent.TimeUnit;

/**
 * Defines a base class for testing a method for the H2 assignment (public tests). A subclass of this class needs
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
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public abstract class H2_TestsPublic extends H2_Tests {

    @Order(0)
    @DisplayName("Die Methode cartesianProduct(MySet) gibt das korrekte Ergebnis für simple Eingaben zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H2_Criterion_01.json", customConverters = CUSTOM_CONVERTERS)
    public void testSimple(JsonParameterSet parameters) {
        assertTuples(parameters);
    }
}
