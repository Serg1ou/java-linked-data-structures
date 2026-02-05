package h10.rubric;

import org.tudalgo.algoutils.tutor.general.assertions.Assertions3;
import org.tudalgo.algoutils.tutor.general.match.BasicStringMatchers;
import org.tudalgo.algoutils.tutor.general.match.Matcher;
import org.tudalgo.algoutils.tutor.general.match.MatcherFactories;
import org.tudalgo.algoutils.tutor.general.reflections.BasicPackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.MethodLink;
import org.tudalgo.algoutils.tutor.general.reflections.PackageLink;
import org.tudalgo.algoutils.tutor.general.reflections.TypeLink;

import java.util.Arrays;

/**
 * A utility class for getting links to classes, methods, etc.
 *
 * @author Nhan Huynh
 */
public final class Links {

    /**
     * A {@link MatcherFactories.StringMatcherFactory} that matches strings exactly. This is used to match link names.
     */
    private static final MatcherFactories.StringMatcherFactory STRING_MATCHER_FACTORY = BasicStringMatchers::identical;

    /**
     * The package link to the package containing the classes.
     */
    private static final PackageLink PACKAGE_LINK = BasicPackageLink.of("h10");

    /**
     * This class cannot be instantiated.
     */
    private Links() {

    }

    /**
     * Returns the {@link MethodLink} to the method with the given name in the given class.
     *
     * @param clazz      the class containing the method
     * @param methodName the name of the method
     * @param matchers   the matchers to match the method
     * @return the {@link MethodLink} to the method with the given name in the given class
     */
    @SafeVarargs
    public static MethodLink getMethod(Class<?> clazz, String methodName, Matcher<MethodLink>... matchers) {
        return getMethod(getType(clazz), methodName, matchers);
    }

    /**
     * Returns the {@link MethodLink} to the method with the given name in the given type.
     *
     * @param type       the type containing the method
     * @param methodName the name of the method
     * @param matchers   the matchers to match the method
     * @return the {@link MethodLink} to the method with the given name in the given type
     */
    @SafeVarargs
    public static MethodLink getMethod(TypeLink type, String methodName, Matcher<MethodLink>... matchers) {
        return Assertions3.assertMethodExists(
            type,
            Arrays.stream(matchers).reduce(STRING_MATCHER_FACTORY.matcher(methodName), Matcher::and)
        );
    }

    /**
     * Returns the {@link TypeLink} to the class with the given name.
     *
     * @param clazz the name of the class
     * @return the {@link TypeLink} to the class with the given name
     */
    public static TypeLink getType(Class<?> clazz) {
        return Assertions3.assertTypeExists(
            PACKAGE_LINK,
            STRING_MATCHER_FACTORY.matcher(clazz.getSimpleName())
        );
    }
}
