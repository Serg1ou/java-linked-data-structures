package h10;

import h10.util.ListItems;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * A utility class for {@link MySet}s which provides additional operations on sets.
 *
 * @author Nhan Huynh
 * @see MySet
 */
public final class Sets {

    /**
     * This class cannot be instantiated.
     */
    private Sets() {

    }

    /**
     * Returns an iterator over the list items of the given set.
     *
     * @param set the set to iterate over
     * @param <T> the type of the elements in the set
     * @return an iterator over the list items of the given set
     */
    public static <T> Iterator<ListItem<T>> itemsIterator(MySet<T> set) {
        return ListItems.itemIterator(set.head);
    }

    /**
     * Returns an iterator over the given set.
     *
     * @param set the set to iterate over
     * @param <T> the type of the elements in the set
     * @return an iterator over the given set
     */
    public static <T> Iterator<T> iterator(MySet<T> set) {
        return ListItems.iterator(set.head);
    }

    /**
     * Returns a stream to the list items of the given set.
     *
     * @param set the set to stream
     * @param <T> the type of the elements in the set
     * @return a stream to the list items of the given set
     */
    public static <T> Stream<ListItem<T>> itemsStream(MySet<T> set) {
        return ListItems.itemStream(set.head);
    }

    /**
     * Returns a stream of the given set.
     *
     * @param set the set to stream
     * @param <T> the type of the elements in the set
     * @return a stream of the given set
     */
    public static <T> Stream<T> stream(MySet<T> set) {
        return ListItems.stream(set.head);
    }
}
