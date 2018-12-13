package com.maomao.exercise;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Deque;

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
		final int[] arr = new int[] { 13, 51, 57, 5, 48, 11, 26, 7, 19, 35, 55, 60 };
		for (final int i : arr) {
			tree.insert(i);
		}
		System.out.println(tree.toString());
		tree.levelOrderPrint();
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
		/*
		 * if node is leaf, then delete directly
		 * if node has a single child, then move up child
		 * if node has two children, then find its successor
		 */
		Node<T> current = root;
		Node<T> parent = null;
		Node<T> found = null;
		while (current != null) {
			if (current.value.equals(value)) {
				found = current;
				break;
			} else if (comparator.compare(current.value, value) > 0) {
				parent = current;
				current = current.left;
			} else {
				parent = current;
				current = current.right;
			}
		}
		if (found == null) {
			throw new RuntimeException("value not found");
		}
		//no child
		final Node<T> replace;
		if (found.left == null && found.right == null) {
			replace = null;
		}
		//single child
		else if (found.right == null || found.left == null) {
			replace = found.right == null ? found.left : found.right;
		}
		//two children
		else {
			final Node<T> firstRight = found.right;
			if (firstRight.left == null) {
				replace = firstRight;
				replace.left = found.left;
			} else {
				Node<T> tmp = firstRight.left;
				Node<T> tmpParent = firstRight;
				while (tmp.left != null) {
					tmpParent = tmp;
					tmp = tmp.left;
				}
				tmpParent.left = null;
				replace = tmp;
				replace.left = found.left;
				replace.right = found.right;
			}
		}
		if (parent == null) {
			root = replace;
		} else if (parent.left == found) {
			parent.left = replace;
		} else if (parent.right == found) {
			parent.right = replace;
		}
	}

	public void inOrderPrint(final Node<T> node) {
		if (node != null) {
			inOrderPrint(node.left);
			System.out.print(node.value);
			System.out.print(" ");
			inOrderPrint(node.right);
		}
	}

	//iterative
	public void inOrderPrint() {
		final Deque<Node<T>> stack = new ArrayDeque<>();
		Node<T> root = this.root;
		while (true) {
			while (root != null) {
				stack.push(root);
				root = root.left;
			}

			if (stack.isEmpty()) {
				break;
			}

			final Node<T> current = stack.pop();
			System.out.print(current.value);
			System.out.print(" ");

			root = current.right;
		}
	}

	public void levelOrderPrint() {
		final Deque<Node<T>> queue = new ArrayDeque<>();
		if (this.root == null) {
			return;
		}
		queue.offerLast(this.root);
		while (!queue.isEmpty()) {
			final Node<T> node = queue.pollFirst();
			System.out.print(node.value);
			System.out.print(" ");

			if (node.left != null) {
				queue.offerLast(node.left);
			}
			if (node.right != null) {
				queue.offerLast(node.right);
			}
		}
	}

	public int depth() {
		return depth(root);
	}

	private int depth(final Node<T> root) {
		if (root != null) {
			final int depthChild = Math.max(depth(root.left), depth(root.right));
			return 1 + depthChild;
		}
		return 0;
	}

	/*
	 * Print a binary tree in an m*n 2D string array following these rules:
	 * 	The row number m should be equal to the height of the given binary tree.
	 * 	The column number n should always be an odd number.
	 * 	The root node's value (in string format) should be put in the exactly middle of the first row it can be put.
	 * 		The column and the row where the root node belongs will separate the rest space into two parts (left-bottom part and right-bottom part).
	 * 		You should print the left subtree in the left-bottom part and print the right subtree in the right-bottom part.
	 * 		The left-bottom part and the right-bottom part should have the same size. Even if one subtree is none while the other is not,
	 * 		you don't need to print anything for the none subtree but still need to leave the space as large as that for the other subtree.
	 * 		However, if two subtrees are none, then you don't need to leave space for both of them.
	 * 	Each unused space should contain an empty string "".
	 * 	Print the subtrees following the same rules.
	 *
	 */
	@Override
	public String toString() {
		final int n = depth();
		final int m = (int) (Math.pow(2, n) - 1);
		final String[][] arr = new String[n][m];
		for (final String[] element : arr) {
			for (int i = 0; i < element.length; i++) {
				element[i] = "";
			}
		}

		fillStringArray(arr, root, 0, 0, m - 1);

		final StringBuilder sb = new StringBuilder();
		for (final String[] element : arr) {
			sb.append(Arrays.toString(element)).append("\n");
		}
		return sb.toString();
	}

	private void fillStringArray(final String[][] arr, final Node<T> node, final int level, final int start, final int end) {
		final int current = (start + end) / 2;
		arr[level][current] = node.toString();
		if (node.left != null) {
			fillStringArray(arr, node.left, level + 1, start, current - 1);
		}
		if (node.right != null) {
			fillStringArray(arr, node.right, level + 1, current + 1, end);
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
