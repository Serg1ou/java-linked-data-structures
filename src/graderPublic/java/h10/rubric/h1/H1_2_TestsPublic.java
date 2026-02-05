package h10.rubric.h1;

import h10.ListItem;
import h10.MySet;
import h10.MySetInPlace;
import h10.rubric.TestConstants;
import h10.rubric.TutorAssertions;
import org.apache.logging.log4j.util.TriConsumer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.sourcegrade.jagr.api.testing.extension.JagrExecutionCondition;
import org.tudalgo.algoutils.tutor.general.annotation.SkipAfterFirstFailedTest;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSetTest;

import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

/**
 * Defines public test cases for the H1.2 assignment.
 *
 * @author Nhan Huynh
 */
@TestForSubmission
@DisplayName("H1.2 | In-Place")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Timeout(
    value = TestConstants.TEST_TIMEOUT_IN_SECONDS,
    unit = TimeUnit.SECONDS,
    threadMode = Timeout.ThreadMode.SEPARATE_THREAD
)
@SkipAfterFirstFailedTest(TestConstants.SKIP_AFTER_FIRST_FAILED_TEST)
public class H1_2_TestsPublic extends H1_TestsPublic {
    @Override
    public Class<?> getClassType() {
        return MySetInPlace.class;
    }

    @Override
    protected <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider() {
        return MySetInPlace::new;
    }

    @Override
    protected <T extends Comparable<T>> TriConsumer<MySet<T>, MySet<T>, Context.Builder<?>> requirementCheck() {
        return TutorAssertions::assertInPlace;
    }

    @Order(0)
    @DisplayName("Die Methode subset(MySet) ninmmt Elemente in die Ergebnismenge nicht auf, falls das Prädikat nicht "
        + "erfüllt wird.")
    @ExtendWith(JagrExecutionCondition.class)
    @ParameterizedTest
    @JsonParameterSetTest(value = "H1_Criterion_01.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testDropAll(JsonParameterSet parameters) {
        super.testDropAll(parameters);
    }

    @Order(1)
    @DisplayName("Die Methode subset(MySet) gibt das korrekte Ergebnis für eine komplexe Eingabe zurück.")
    @ExtendWith(JagrExecutionCondition.class)
    @ParameterizedTest
    @JsonParameterSetTest(value = "H1_Criterion_02.json", customConverters = CUSTOM_CONVERTERS)
    @Override
    public void testComplex(JsonParameterSet parameters) {
        super.testComplex(parameters);
    }
}
