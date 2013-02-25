package com.test9.irc.display;

import javax.swing.*;
import java.util.*;

@SuppressWarnings("rawtypes")
class SortedListModel<T> extends AbstractListModel {

	private static final long serialVersionUID = 9025111200654282953L;

	SortedSet<T> model;
	Comparator comparator;

	@SuppressWarnings("unchecked")
	SortedListModel() {
		comparator = new MyComparator();
		model = Collections.synchronizedSortedSet(new TreeSet<T>(comparator));
	}

	@Override
	public int getSize() {
		return model.size();
	}

	@Override
	public Object getElementAt(int index) {
		return model.toArray()[index];
	}

	public void add(T element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}
	public void addAll(T elements[]) {
		Collection<T> c = Arrays.asList(elements);
		model.addAll(c);
		fireContentsChanged(this, 0, getSize());
	}

	public void clear() {
		model.clear();
		fireContentsChanged(this, 0, getSize());
	}

	public boolean contains(Object element) {
		return model.contains(element);
	}

	public Object firstElement() {
		return model.first();
	}

	public Iterator<T> iterator() {
		return model.iterator();
	}

	public Object lastElement() {
		return model.last();
	}

	public boolean removeElement(Object element) {
		boolean removed = model.remove(element);
		if (removed) {
			fireContentsChanged(this, 0, getSize());
		}
		return removed;
	}
}