package com.maomao.exercise;

import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author y.xu
 */
@SuppressWarnings("synthetic-access")
public class BiTree<T> {
	private static Logger logger = LoggerFactory.getLogger(BiTree.class);

	public static void main(final String[] args) {
		final BiTree<Integer> tree = new BiTree<>((a, b) -> Integer.compare(a, b));
		final int[] arr = new int[] { 13, 51, 57, 5, 48, 11, 26, 7, 19, 35 };
		for (final int i : arr) {
			tree.insert(i);
		}
		tree.inOrderPrint(tree.root);
	}

	private Node<T> root;
	private final Comparator<T> comparator;

	public BiTree(final Comparator<T> comparator) {
		this.comparator = comparator;
	}

	public Node<T> find(final T value) {
		Node<T> current = root;
		while (current != null) {
			final int rst = comparator.compare(current.getValue(), value);
			switch (rst) {
			case 0:
				return current;
			case 1:
				current = current.right;
				break;
			case -1:
				current = current.left;
				break;
			}
		}
		return null;
	}

	public void insert(final T value) {
		if (root == null) {
			root = new Node<>(value);
		} else {
			Node<T> current = root;
			while (true) {
				final int rst = comparator.compare(value, current.getValue());
				switch (rst) {
				case 0:
					throw new RuntimeException("value already exists.");
				case 1:
					if (current.right != null) {
						current = current.right;
					} else {
						current.right = new Node<>(value);
						return;
					}
					break;
				case -1:
					if (current.left != null) {
						current = current.left;
					} else {
						current.left = new Node<>(value);
						return;
					}
					break;
				}
			}
		}
	}

	public void delete(final T value) {

	}

	public void inOrderPrint(final Node<T> node) {
		if (node != null) {
			inOrderPrint(node.left);
			System.out.println(node.value);
			inOrderPrint(node.right);
		}
	}

	//left < this < right
	public static class Node<T> {
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
