package h10.rubric;

import com.fasterxml.jackson.databind.JsonNode;
import h10.ListItem;
import h10.MySet;
import h10.MySetAsCopy;
import h10.MySetInPlace;
import h10.json.JsonConverters;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.json.JsonParameterSet;
import org.tudalgo.algoutils.tutor.general.reflections.BasicMethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;
import spoon.reflect.declaration.CtImport;
import spoon.reflect.visitor.ImportScanner;
import spoon.reflect.visitor.ImportScannerImpl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Defines a base class for testing a method for the H10 assignment.
 *
 * @author Nhan Huynh
 * @see MySet
 * @see MySetAsCopy
 * @see MySetInPlace
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class H10_Test {

    /**
     * The name of the field containing the custom converters for this test class (JSON Converters).
     */
    public static final String CUSTOM_CONVERTERS = "CONVERTERS";

    /**
     * The ignored class which should not be checked for the requirement check.
     */
    private static final Set<String> IGNORED_IMPORTS = Set.of(
        Comparator.class,
        Predicate.class
    ).stream().map(Class::getName).collect(Collectors.toSet());

    /**
     * The custom converters for this test class (JSON Converters).
     *
     * @see JsonParameterSet
     */
    @SuppressWarnings("unused")
    public static final Map<String, Function<JsonNode, ?>> CONVERTERS = new HashMap<>(
        Map.of(
            "source", JsonConverters::toListItemInteger,
            "predicate", JsonConverters::toPredicateInteger,
            "expected", JsonConverters::toListItemInteger,
            "inputs", JsonConverters::toListItemListItemInteger,
            "other", JsonConverters::toListItemInteger,
            "expected2D", JsonConverters::toListItemListItemInteger
        )
    );

    /**
     * The type of the class to be tested.
     */
    private @Nullable TypeLink type;

    /**
     * The method to be tested.
     */
    private @Nullable MethodLink method;

    /**
     * Initializes needed information for the testing before all tests.
     */
    @BeforeAll
    public void globalSetup() {
        type = Links.getType(getClassType());
        method = Links.getMethod(type, getMethodName());
    }

    @BeforeEach
    public void setup() {
        // Check Imports
        ImportScanner importScanner = new ImportScannerImpl();
        importScanner.computeImports(((BasicMethodLink) method).getCtElement());
        Set<CtImport> imports = importScanner.getAllImports();
        Set<String> found = imports.stream()
            .map(element -> element.getReference().toString())
            .filter(Predicate.not(element -> IGNORED_IMPORTS.contains(element)))
            .filter(element -> element.contains("java.util")).collect(Collectors.toSet());
        Assertions2.assertTrue(
            found.isEmpty(),
            contextBuilder().add("Found", found).build(),
            r -> "Expected nno imports from java.util.*, but got".formatted(found)
        );
    }

    /**
     * Returns the type of the class to be tested.
     *
     * @return the type of the class to be tested
     */
    public abstract Class<?> getClassType();

    /**
     * Returns the name of the method to be tested.
     *
     * @return the name of the method to be tested
     */
    public abstract String getMethodName();

    /**
     * Returns the type of the class to be tested.
     *
     * @return the type of the class to be tested
     */
    public TypeLink getType() {
        if (type == null) {
            throw new IllegalStateException("Type not initialized");
        }
        return type;
    }

    /**
     * Returns a {@link Context.Builder} with the method to be tested as the subject.
     *
     * @return a {@link Context.Builder} with the method to be tested as the subject
     */
    protected Context.Builder<?> contextBuilder() {
        return Assertions2.contextBuilder().subject(getMethod());
    }

    /**
     * Returns the method to be tested.
     *
     * @return the method to be tested
     */
    public MethodLink getMethod() {
        if (method == null) {
            throw new IllegalStateException("Method not initialized");
        }
        return method;
    }

    /**
     * Creates a set from the given head and comparator.
     *
     * @param head the head of the list to create a set from
     * @param <T>  the type of the elements in the set
     * @return a set from the given head and comparator
     */
    public <T extends Comparable<? super T>> MySet<T> createSet(ListItem<T> head) {
        return this.<T>setProvider().apply(head, getDefaultComparator());
    }

    /**
     * Returns a function that creates a set from the given head and comparator.
     *
     * @param <T> the type of the elements in the set
     * @return a function that creates a set from the given head and comparator
     */
    protected abstract <T> BiFunction<ListItem<T>, Comparator<T>, MySet<T>> setProvider();

    /**
     * Returns a comparator that compares elements using their natural order. This comparator provides a better error
     * message than {@link Comparator#naturalOrder()} since we override the {@link Object#toString()} method.
     *
     * @param <T> the type of the elements to compare
     * @return a comparator that compares elements using their natural order
     */
    protected static <T extends Comparable<? super T>> Comparator<T> getDefaultComparator() {
        return new Comparator<>() {
            @Override
            public int compare(T o1, T o2) {
                return o1.compareTo(o2);
            }

            @Override
            public String toString() {
                return "o1 <= o2";
            }
        };
    }
}
