package trees.core;

import java.util.List;
import java.util.function.Consumer;

public interface Trees<V extends Comparable<V>> extends Iterable<V>, Comparable<Trees<V>> {

    enum Traversal {
        PREORDER,
        INORDER,
        POSTORDER
    }

    void insert(V value);

    void delete(V value);

    V search(V value);

    List<V> traverse(Traversal order);

    V getRoot();

    int getHeight();

    int getSize();

    boolean isEmpty();

    @Override
    default void forEach(Consumer<? super V> action) {
        Iterable.super.forEach(action);
    }

    @Override
    default int compareTo(Trees<V> o) {
        // Implement comparison logic if needed
        return 0;
    }
}
