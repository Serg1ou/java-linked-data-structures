package h10;

import org.jetbrains.annotations.Nullable;
import org.tudalgo.algoutils.student.annotation.DoNotTouch;

import java.util.Objects;

/**
 * An instance of this class represents a sequence of list items (wrapped elements) of an ordered sequence that can be addressed
 * by means of a reference to the direct successor.
 *
 * <p>Example:
 * <ul>
 * <li> List containing the elements: 1, 2, 3</li>
 * </ul>
 *
 * <pre>{@code
 *    ListItem<Integer> head = new ListItem<>();
 *    head.key = 1; // First element of the list
 *    head.next = new ListItem<>();
 *    head.next.key = 2; // Second element of the list
 *    head.next.next = new ListItem<>();
 *    head.next.next.key = 3; // Third element of the list
 * }</pre>
 *
 * @param <T> type of key
 * @author Nhan Huynh
 */
@DoNotTouch
public class ListItem<T> {

    /**
     * The value of this list item.
     */
    @DoNotTouch
    public T key;

    /**
     * The successor node of this list item.
     */
    @DoNotTouch
    public @Nullable ListItem<T> next;

    /**
     * Constructs and initializes an empty list item.
     */
    @DoNotTouch
    public ListItem() {
    }

    /**
     * Constructs and initializes a list item with the given key.
     *
     * @param key the key of the list item
     */
    @DoNotTouch
    public ListItem(T key) {
        this.key = key;
    }

    @Override
    @DoNotTouch
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ListItem<?> other)) {
            return false;
        }
        return Objects.equals(key, other.key) && Objects.equals(next, other.next);
    }

    @Override
    @DoNotTouch
    public int hashCode() {
        return Objects.hash(key, next);
    }

    @Override
    @DoNotTouch
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{").append(key).append(" -> ");
        for (ListItem<T> current = next; current != null; current = current.next) {
            sb.append(current.key).append(" -> ");
        }
        sb.append("null}");
        return sb.toString();
    }
}
