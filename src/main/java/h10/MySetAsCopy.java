package h10;

import org.tudalgo.algoutils.student.annotation.DoNotTouch;
import org.tudalgo.algoutils.student.annotation.StudentImplementationRequired;

import java.util.*;
import java.util.function.Predicate;

import static org.tudalgo.algoutils.student.Student.crash;

/**
 * An out-of-place implementation of MySet.
 *
 * @param <T> the type of the elements in the set
 * @author Lars Waßmann, Nhan Huynh
 */
@DoNotTouch
public class MySetAsCopy<T> extends MySet<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    @DoNotTouch
    public MySetAsCopy(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    @Override
    @StudentImplementationRequired
    public MySet<T> subset(Predicate<? super T> pred) {
        ListItem<T> actual = this.head;
        ListItem<T> tailSub = null;
        ListItem<T> headSub = null;
        while (actual != null) {
            if (pred.test(actual.key)) {

                ListItem<T> listItem = new ListItem<>(actual.key);
                if (headSub == null) {
                    headSub = listItem;
                } else {

                    tailSub.next = listItem;
                }
                tailSub = listItem;
            }
            actual = actual.next;
        }

        if (tailSub != null) {
            tailSub.next = null;
        }

        return new MySetAsCopy<>(headSub, this.cmp);
    }


    @Override
    @StudentImplementationRequired
    public MySet<ListItem<T>> cartesianProduct(MySet<T> other) {

        ListItem<ListItem<T>> newHead = null, actualNew = null;
        for (ListItem<T> x = this.head; x != null; x = x.next) {
            for (ListItem<T> y = other.head; y != null; y = y.next) {
                ListItem<T> firstNew = new ListItem<>(x.key);
                firstNew.next = new ListItem<>(y.key);

                ListItem<ListItem<T>> tupleNew = new ListItem<>(firstNew);
                if (newHead == null) {
                    newHead = tupleNew;
                } else {
                    actualNew.next = tupleNew;

                }
                actualNew = tupleNew;
            }
        }
        return new MySetAsCopy<>(newHead, (x1, x2) -> {
            while (x1.next != null && x2.next != null) {
                int comparator = this.cmp.compare(x1.key, x2.key);
                if (comparator != 0) {

                    return comparator;
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
        Predicate<T> predicate = key -> {
            ListItem<T> actual = other.head;
            while (actual != null) {
                if (cmp.compare(key, actual.key) == 0) {
                    return false;
                }

                actual = actual.next;
            }
            return true;
        };

        return this.subset(predicate);
    }

    @Override
    @StudentImplementationRequired
    protected MySet<T> intersectionListItems(ListItem<ListItem<T>> heads) {
        MySetAsCopy<T> ergebniss = new MySetAsCopy<>(head,cmp);

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



