package h10.util;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * A {@link VisitorElement} is an element that can be visited and keeps track of the number of times it has been
 * visited.
 *
 * @param <T> the type of the element
 * @author Nhan Huynh
 */
public class VisitorElement<T extends Comparable<T>> implements Comparable<VisitorElement<T>> {

    /**
     * The wrapped element which can be visited.
     */
    private final T element;

    /**
     * The number of times the element has been visited.
     */
    private int visited = 0;

    /**
     * Creates a new {@link VisitorElement} with the given element.
     *
     * @param element the element to wrap
     */
    public VisitorElement(T element) {
        this.element = element;
    }

    /**
     * Returns the wrapped element without visiting it.
     *
     * @return the wrapped element
     */
    public T peek() {
        return element;
    }

    /**
     * Returns the number of times the wrapped element has been visited.
     *
     * @return the number of times the wrapped element has been visited
     */
    public int visited() {
        return visited;
    }

    /**
     * Returns {@code true} if the wrapped element has been visited at least once, {@code false} otherwise.
     *
     * @return {@code true} if the wrapped element has been visited at least once, {@code false} otherwise
     */
    public boolean isVisited() {
        return visited > 0;
    }

    @Override
    public int compareTo(@NotNull VisitorElement<T> o) {
        return get().compareTo(o.get());
    }

    /**
     * Returns the wrapped element and visits it.
     *
     * @return the wrapped element
     */
    public T get() {
        visit();
        return element;
    }

    /**
     * Visits the wrapped element.
     */
    public void visit() {
        visited++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        VisitorElement<?> that = (VisitorElement<?>) o;
        return Objects.equals(element, that.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, visited);
    }

    @Override
    public String toString() {
        return "{element=%s, visited=%d}".formatted(element, visited);
    }
}
