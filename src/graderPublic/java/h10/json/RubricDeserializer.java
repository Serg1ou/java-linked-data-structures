package h10.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Streams;
import org.junit.jupiter.api.DisplayName;
import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;
import org.tudalgo.algoutils.tutor.general.jagr.RubricUtils;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * A deserializer for {@link Criterion} objects.
 *
 * <p>This class is used to deserialize the JSON representation of a {@link Criterion} object.
 *
 * <p>The JSON representation of a {@link Criterion} object is as follows:
 * <pre>{@code
 *     {
 *       "title": "The title of the criterion",
 *       "tasks": [
 *          {
 *              "name": "The name of the task",
 *              "criteria": [
 *                  {
 *                  "order": 1,
 *                  "title": "The title of the sub-criterion",
 *                  "class": "The test class containing the test case with the display name (display name = title)"
 *                  },
 *              ...
 *              ],
 *          },
 *       ...
 *       }
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class RubricDeserializer extends JsonDeserializer<Criterion[]> {

    @Override
    public Criterion[] deserialize(JsonParser parser, DeserializationContext context) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        if (!node.isArray()) {
            throw new IOException("Expected array of criteria");
        }
        CheckedFunction<JsonNode, Criterion> deserializer = this::deserializeCriterion;
        return Streams.stream(node).map(deserializer).toArray(Criterion[]::new);
    }

    /**
     * Deserializes a {@link Criterion} object from a {@link JsonNode}. The JSON node contains the title and the tasks
     * of the criterion.
     *
     * @param node the node to deserialize from
     * @return the deserialized {@link Criterion} object
     * @throws IOException if the node is not a valid {@link Criterion} object
     */
    private Criterion deserializeCriterion(JsonNode node) throws IOException {
        if (!node.has("title")) {
            throw new IOException("Missing title");
        }
        String title = node.get("title").asText();
        if (!node.has("tasks")) {
            throw new IOException("Missing tasks");
        }
        JsonNode tasks = node.get("tasks");
        if (!(tasks instanceof ArrayNode tasksNode)) {
            throw new IOException("Expected array of criteria");
        }
        CheckedFunction<JsonNode, Criterion> deserializer = this::deserializeTask;
        return Criterion.builder()
            .shortDescription(title)
            .addChildCriteria(Streams.stream(tasksNode)
                .map(deserializer)
                .toArray(Criterion[]::new)
            ).build();
    }

    /**
     * Deserializes a {@link Criterion} object from a {@link JsonNode}. The JSON node contains the name of the task and
     * its sub-criteria.
     *
     * @param node the node to deserialize from
     * @return the deserialized {@link Criterion} object
     * @throws IOException if the node is not a valid {@link Criterion} object
     */
    private Criterion deserializeTask(JsonNode node) throws IOException {
        if (!node.has("name")) {
            throw new IOException("Missing name");
        }
        String name = node.get("name").asText();
        if (!node.has("criteria")) {
            throw new IOException("Missing criteria");
        }
        JsonNode criteria = node.get("criteria");
        if (!(criteria instanceof ArrayNode criteriaNode)) {
            throw new IOException("Expected array of criteria");
        }
        CheckedFunction<JsonNode, Map.Entry<Integer, Criterion>> deserializer = this::deserializeSubCriterion;
        return Criterion.builder()
            .shortDescription(name)
            .addChildCriteria(Streams.stream(criteriaNode)
                .map(deserializer)
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .toArray(Criterion[]::new)
            ).build();
    }

    /**
     * Returns all methods of the given class and its superclasses.
     *
     * @param clazz the class to get the methods from
     * @return all methods of the given class and its superclasses
     */
    private List<Method> getMethods(Class<?> clazz) {
        List<Method> methods = new ArrayList<>(List.of(clazz.getDeclaredMethods()));
        if (methods.isEmpty() || clazz.getSuperclass() == null) {
            return methods;
        }
        methods.addAll(getMethods(clazz.getSuperclass()));
        return methods;
    }

    /**
     * Deserializes a {@link Criterion} object from a {@link JsonNode}. The JSON node contains the order, title and
     * the class containing the test case with the display name (display name = title).
     *
     * @param node the node to deserialize from
     * @return the deserialized {@link Criterion} object
     * @throws IOException if the node is not a valid {@link Criterion} object
     */
    private Map.Entry<Integer, Criterion> deserializeSubCriterion(JsonNode node) throws IOException {
        if (!node.has("order")) {
            throw new IOException("Missing order");
        }
        int order = node.get("order").asInt();
        if (!node.has("title")) {
            throw new IOException("Missing title");
        }
        String title = node.get("title").asText();
        if (!node.has("class")) {
            throw new IOException("Missing class");
        }
        if (!node.has("class")) {
            throw new IOException("Missing class");
        }
        String className = node.get("class").asText();
        Class<?> clazz;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            // Private Tests
            return Map.entry(order, Criterion.builder()
                .grader(RubricUtils.graderPrivateOnly())
                .shortDescription(title)
                .build());
        }
        List<Method> methods = getMethods(clazz);
        return methods.parallelStream()
            .filter(method -> method.isAnnotationPresent(DisplayName.class)
                && method.getAnnotation(DisplayName.class).value().equals(title))
            .map(method -> Map.entry(order, RubricUtils.criterion(title, JUnitTestRef.ofMethod(method))))
            .findFirst()
            .orElse(Map.entry(order, Criterion.builder().grader(RubricUtils
                    .graderPrivateOnly())
                .shortDescription(title)
                .build()));
    }

    /**
     * A function that can throw an {@link Exception}.
     * This is used to wrap the checked {@link IOException} into an unchecked {@link UncheckedIOException}.
     *
     * @param <T> the type of the input to the function
     * @param <R> the type of the result of the function
     */
    private interface CheckedFunction<T, R> extends Function<T, R> {

        /**
         * {@inheritDoc}
         *
         * @param t the function argument
         * @return the function result
         * @throws UncheckedIOException if an {@link IOException} occurs
         */
        @Override
        default R apply(T t) {
            try {
                return applyChecked(t);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        R applyChecked(T t) throws IOException;
    }
}
