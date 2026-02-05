package h10.rubric.h3;

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
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.concurrent.TimeUnit;

/**
 * Defines a base class for testing a method for the H3 assignment (public tests). A subclass of this class needs
 * to implement at least {@link #getClassType()} and {@link #setProvider()} since the criteria for {@link MySetAsCopy}
 * and {@link MySetInPlace} are the same.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H3 | difference(MySet)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
public abstract class H3_TestsPublic extends H3_Tests {

    @Order(0)
    @DisplayName("Die Methode difference(MySet) nimmt ein Element nicht auf, falls ein Element sowohl in M als auch "
        + "in N ist.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_01.json", customConverters = CUSTOM_CONVERTERS)
    public void testNotAddElements(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }

    @Order(1)
    @DisplayName("Die Methode difference(MySet) gibt das korrekte Ergebnis für eine leere Menge zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H3_Criterion_02.json", customConverters = CUSTOM_CONVERTERS)
    public void testEmptySet(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }
}
