package h10;

import java.util.Comparator;

/**
 * Fixes a wrong implementation of {@link  #toListItem(ListItem)}.
 *
 * @param <T> the type of the elements in the set
 * @author Nhan Huynh
 */
public class TutorMySetInPlace<T> extends MySetInPlace<T> {

    /**
     * Constructs and initializes a new set with the given elements.
     *
     * @param head the head of the set
     * @param cmp  the comparator to compare elements
     * @throws IllegalArgumentException if the given elements are not pairwise different or not ordered
     */
    public TutorMySetInPlace(ListItem<T> head, Comparator<? super T> cmp) {
        super(head, cmp);
    }

    /**
     * Returns a converted version of the sets as a list.
     *
     * @param others the sets to convert
     * @return the converted version of the sets as a list
     */
    @Override
    protected ListItem<ListItem<T>> toListItem(ListItem<MySet<T>> others) {
        // Fix wrong implementation
        ListItem<ListItem<T>> heads = new ListItem<>(head);
        ListItem<ListItem<T>> tails = heads;

        // Retrieve pointers to a head pointer from all sets
        for (ListItem<MySet<T>> otherSets = others; otherSets != null; otherSets = otherSets.next) {
            ListItem<T> otherHead = otherSets.key.head;
            ListItem<ListItem<T>> item = new ListItem<>(otherHead);
            tails.next = item;
            tails = item;
        }
        return heads;
    }
}
