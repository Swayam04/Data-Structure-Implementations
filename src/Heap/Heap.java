package Heap;

import java.lang.reflect.Array;
import java.util.List;


/**
 * An abstract class representing a heap data structure.
 *
 * @param <T> the type of elements in the heap
 */
public abstract class Heap<T> {
    /**
     * The array storing the elements of the heap.
     */
    protected T[] elements;

    /**
     * The number of elements in the heap.
     */
    protected int size;

    /**
     * The default initial capacity of the heap.
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * Constructs a new heap with the default initial capacity.
     */
    public Heap() {
        this(DEFAULT_CAPACITY);
    }

    /**
     * Constructs a new heap with the specified initial capacity.
     *
     * @param capacity the initial capacity of the heap
     * @throws IllegalArgumentException if the specified capacity is non-positive
     */
    public Heap(int capacity) {
        if(capacity <= 0) {
            throw new IllegalArgumentException("Initial capacity must be at least 1.");
        }
        this.elements = createArray(capacity);
        this.size = 0;
    }

    /**
     * Constructs a new heap containing the elements of the specified array.
     *
     * @param array the array whose elements are to be placed into this heap
     */
    public Heap(T[] array) {
        this.elements = array.clone();
        this.size = array.length;
        heapifyElements();
    }

    /**
     * Constructs a new heap containing the elements of the specified list.
     *
     * @param list the list whose elements are to be placed into this heap
     */
    public Heap(List<T> list) {
        this.elements = list.toArray(createArray(list.size()));
        this.size = list.size();
        heapifyElements();
    }

    /**
     * Returns the number of elements in the heap.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        return size;
    }

    /**
     * Checks whether the heap is empty.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears all elements from the heap.
     */
    public void clear() {
        size = 0;
    }

    /**
     * Retrieves, but does not remove, the root element of this heap.
     *
     * @return the root element of this heap
     * @throws RuntimeException if the heap is empty
     */
    protected T peek() {
        if (isEmpty()) {
            throw new RuntimeException("Error. Heap is empty.");
        }
        return elements[0];
    }

    /**
     * Removes and returns the root element of this heap.
     *
     * @return the root element of this heap
     * @throws RuntimeException if the heap is empty
     */
    protected T remove() {
        if (isEmpty()) {
            throw new RuntimeException("Error. Heap is empty.");
        }
        T root = elements[0];
        elements[0] = elements[--size];
        heapifyDown(0);
        return root;
    }

    /**
     * Inserts the specified element into this heap.
     *
     * @param element the element to insert
     */
    public void insert(T element) {
        if(size == elements.length) {
            expandArray();
        }
        elements[size++] = element;
        heapifyUp(size - 1);
    }

    /**
     * Compares two elements of the heap.
     *
     * @param o1 the first element to be compared
     * @param o2 the second element to be compared
     * @return a negative integer, zero, or a positive integer as the first argument
     *         is less than, equal to, or greater than the second
     */
    public abstract int compare(T o1, T o2);

    /**
     * Restores the heap property by moving the specified node down the heap.
     *
     * @param index the index of the node to heapify down
     */
    private void heapifyDown(int index) {
        while(getChildIndex(index) != -1) {
            int childIndex = getChildIndex(index);
            swap(index, childIndex);
            index = childIndex;
        }
    }

    /**
     * Restores the heap property by moving the specified node up the heap.
     *
     * @param index the index of the node to heapify up
     */
    private void heapifyUp(int index) {
        int parentIndex = (index - 1) / 2;
        while(parentIndex >= 0) {
            if(compare(elements[index], elements[parentIndex]) >= 0) {
                break;
            } else {
                swap(parentIndex, index);
                index = parentIndex;
                parentIndex = (index - 1) / 2;
            }
        }
    }

    /**
     * Restores the heap property for all elements in the heap.
     */
    private void heapifyElements() {
        int index = (size - 2) / 2;
        for(int i = index; i >= 0; i--) {
            heapifyDown(i);
        }
    }

    /**
     * Swaps the elements at the specified indices in the heap.
     *
     * @param index1 the index of the first element to swap
     * @param index2 the index of the second element to swap
     */
    private void swap(int index1, int index2) {
        T temp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = temp;
    }

    /**
     * Returns the index of the child node with the minimum value.
     *
     * @param index the index of the parent node
     * @return the index of the child node with the minimum value, or -1 if no such child exists
     */
    private int getChildIndex(int index) {
        int childIndex = -1;
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        if(leftChildIndex < size) {
            childIndex = leftChildIndex;
            if(rightChildIndex < size && compare(elements[rightChildIndex], elements[childIndex]) < 0) {
                childIndex = rightChildIndex;
            }
        }
        if(childIndex != -1 && compare(elements[index], elements[childIndex]) > 0) {
            childIndex = -1;
        }
        return childIndex;
    }

    /**
     * Expands the capacity of the elements array.
     */
    private void expandArray() {
        int newCapacity = elements.length * 2;
        T[] newArray = createArray(newCapacity);
        System.arraycopy(elements, 0, newArray, 0, size);
        elements = newArray;
    }

    /**
     * Creates a new array of type T with the specified length.
     *
     * @param length the length of the new array
     * @return the new array
     */
    @SuppressWarnings("unchecked")
    private T[] createArray(int length) {
        return (T[]) Array.newInstance(Object.class, length);
    }
}
