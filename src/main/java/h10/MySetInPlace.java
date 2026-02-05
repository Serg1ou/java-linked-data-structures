package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.Comparator;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An in-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetInPlace<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetInPlace(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        ListItem<T> current = this.head;
        ListItem<T> previous = null;

        while (current != null) {
            if (!pred.test(current.key)) {
                if (previous == null) {
                    this.head = current.next;
                } else {
                    previous.next = current.next;
                }
            } else {
                previous = current;
            }
            current = current.next;
        }

        return this;
    }

    @Override
    @StudentImplementationRequired
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {

        ListItem<ListItem<T>> newHead = null, currentNew = null;
        for (ListItem<T> x = this.head; x != null; x = x.next) {
            for (ListItem<T> y = other.head; y != null; y = y.next) {
                ListItem<T> newFirst = new ListItem<>(x.key);
                newFirst.next = new ListItem<>(y.key);

                ListItem<ListItem<T>> newTuple = new ListItem<>(newFirst);
                if (newHead == null) {
                    newHead = newTuple;
                } else {
                    currentNew.next = newTuple;
                }
                currentNew = newTuple;
            }
        }
        return new MySetAsCopy<>(newHead, (x1, x2) -> {
            while (x1.next != null && x2.next != null) {
                int comparatorFirst = this.cmp.compare(x1.key, x2.key);
                if (comparatorFirst != 0) {

                    return comparatorFirst;
                }

                x1 = x1.next;
                x2 = x2.next;
            }
            return other.cmp.compare(x1.key, x2.key);
        });
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> difference(MySet<T> other) {
        Predicate<T> notInOther = key -> {
            ListItem<T> current = other.head;
            while (current != null) {
                if (cmp.compare(key, current.key) == 0) {
                    return false;
                }
                current = current.next;
            }
            return true;
        };

        return this.subset(notInOther);
    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        MySetInPlace<T> ergebniss = new MySetInPlace<>(head,cmp);
        if (heads == null) {
            return ergebniss;
        }
        Predicate<T> predicate = key -> {
            ListItem<ListItem<T>> actual = heads;
            while (actual != null) {
                if (!subsetContains(actual.key, key)) {
                    return false;

                }
                actual = actual.next;
            }
            return true;
        };
        return ergebniss.subset(predicate);
    }
    private boolean subsetContains(ListItem<T> set, T key) {
        ListItem<T> actual = set;
        while (actual != null) {
            if (cmp.compare(actual.key, key) == 0) {
                return true;
            }
            actual = actual.next;
        }
        return false;
        }
    }

