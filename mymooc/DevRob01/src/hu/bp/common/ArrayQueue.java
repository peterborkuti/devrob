package hu.bp.common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Borkuti
 * A minimal functionality queue which retains its size.
 * When you add an element and the queue has reached its maxSize,
 * one element is removed from the other end of the queue.
 * 
 * @param <T>
 */
public class ArrayQueue<T> {

	private List<T> queue = new ArrayList<T>();
	public final int maxSize;

	public ArrayQueue(int maxSize) {
		this.maxSize = maxSize;
	}

	public void add(T e) {
		if (queue.size() >= maxSize) {
			queue.remove(0);
		}
		queue.add(e);
	}

	public T get(int index) {
		return queue.get(index);
	}

	@SuppressWarnings("unchecked")
	public T[] toArray() {
		return (T[])(queue.subList(0, maxSize).toArray());
	}

	public List<T> asList() {
		return queue.subList(0, maxSize);
	}

	public int size() {
		return queue.size();
	}
}
