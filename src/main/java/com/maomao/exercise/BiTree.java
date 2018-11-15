package com.maomao.exercise;

import java.util.Comparator;

/**
 * @author y.xu
 */
public class BiTree<T> {

	private Node<T> root;
	private final Comparator<T> comparator;

	public BiTree(final Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public Node<T> find(final T value) {
		final Node<T> current = root;
		while (current != null) {
			if (current.getValue().equals(value)) {
				return current;
			}
			//TODO
		}
		return null;
	}

	public void insert(final T value) {

	}

	public void delete(final T value) {
		//TODO
	}

	//left < this < right
	public class Node<T> {
		private final T value;
		private Node<T> left;
		private Node<T> right;

		private Node(final T value) {
			this.value = value;
		}

		public void setLeft(final Node<T> left) {
			this.left = left;
		}

		public void setRight(final Node<T> right) {
			this.right = right;
		}

		public Node<T> getLeft() {
			return left;
		}

		public Node<T> getRight() {
			return right;
		}

		public T getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value.toString();
		}
	}
}
