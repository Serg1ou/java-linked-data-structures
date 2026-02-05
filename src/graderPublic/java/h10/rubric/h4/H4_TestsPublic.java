package h10.rubric.h4;

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
 * Defines a base class for testing a method for the H4 assignment (public tests). A subclass of this class needs
 * to implement at least {@link #getClassType()} and {@link #setProvider()} since the criteria for {@link MySetAsCopy}
 * and {@link MySetInPlace} are the same.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H4 | intersectionListItems(ListItem)")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
public abstract class H4_TestsPublic extends H4_Tests {

    @Order(0)
    @DisplayName("Die Methode intersectionListItems(ListItem) nimmt ein Element auf, falls das Element in allen "
        + "Mengen enthalten ist.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_01.json", customConverters = CUSTOM_CONVERTERS)
    public void testAddElements(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }

    @Order(1)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis zurück, wenn die Hauptmenge "
        + "leer ist.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_02.json", customConverters = CUSTOM_CONVERTERS)
    public void testEmptySet(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }

    @Order(2)
    @DisplayName("Die Methode intersectionListItems(ListItem) gibt das korrekte Ergebnis für eine leere "
        + "Eingabemenge(n) zurück.")
    @ParameterizedTest
    @JsonParameterSetTest(value = "H4_Criterion_03.json", customConverters = CUSTOM_CONVERTERS)
    public void testInputEmpty(JsonParameterSet parameters) {
        assertEqualSet(parameters);
    }
}
