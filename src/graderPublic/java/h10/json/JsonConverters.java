package h10.json;

import com.fasterxml.jackson.databind.JsonNode;
import h10.ListItem;
import h10.util.ListItems;

import java.util.function.Predicate;
import java.util.stream.StreamSupport;

/**
 * A collection of JSON converters for this assignment.
 *
 * @author Nhan Huynh
 */
public class JsonConverters extends org.tudalgo.algoutils.tutor.general.json.JsonConverters {

    /**
     * This class cannot be instantiated.
     */
    private JsonConverters() {

    }

    /**
     * Converts a {@link JsonNode} to a list item sequence containing a list item sequence of integers.
     *
     * @param node the node to convert
     * @return a {@link JsonNode} to a list item sequence containing a list item sequence of integers
     * @throws IllegalArgumentException if the node is not an array node
     */
    public static ListItem<ListItem<Integer>> toListItemListItemInteger(JsonNode node) {
        if (!node.isArray()) {
            throw new IllegalArgumentException("Node is not an array");
        }

        return ListItems.convert(
            StreamSupport.stream(node.spliterator(), false)
                .map(JsonConverters::toListItemInteger).toList()
        );
    }

    /**
     * Converts a {@link JsonNode} to a list item sequence containing integers.
     *
     * @param node the node to convert
     * @return a {@link JsonNode} to a list item sequence containing integers
     * @throws IllegalArgumentException if the node is not an array node
     */
    public static ListItem<Integer> toListItemInteger(JsonNode node) {
        if (!node.isArray()) {
            throw new IllegalArgumentException("Node is not an array");
        }
        return ListItems.convert(toList(node, JsonNode::asInt));
    }

    /**
     * Converts a {@link JsonNode} to a predicate for integers.
     *
     * @param node the node to convert
     * @return a {@link JsonNode} to a predicate for integers
     */
    public static Predicate<Integer> toPredicateInteger(JsonNode node) {
        return new PredicateParser.BasicIntMath().parse(node);
    }
}
