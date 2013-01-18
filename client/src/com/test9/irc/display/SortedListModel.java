package com.test9.irc.display;

import javax.swing.*;
import java.util.*;

@SuppressWarnings("rawtypes")
class SortedListModel extends AbstractListModel {

	private static final long serialVersionUID = 9025111200654282953L;

	SortedSet<Object> model;
	Comparator comparator;

	@SuppressWarnings("unchecked")
	public SortedListModel() {
		comparator = new MyComparator();
		model = new TreeSet<Object>(comparator);
		
	}

	public int getSize() {
		return model.size();
	}

	public Object getElementAt(int index) {
		return model.toArray()[index];
	}

	public void add(Object element) {
		if (model.add(element)) {
			fireContentsChanged(this, 0, getSize());
		}
	}
	public void addAll(Object elements[]) {
		Collection<Object> c = Arrays.asList(elements);
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

	public Iterator<Object> iterator() {
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