package com.maomao.exercise;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple trie implementation using map
 * @author y.xu
 *
 */
public class Trie {

	public static void main(final String[] args) {
		final Trie trie = new Trie();

		trie.insert("is");
		trie.insert("ill");
		trie.insert("ile");
		trie.insert("iss");
		System.out.println(trie.root.toString());
	}

	private final TrieNode root = new TrieNode(Character.MIN_VALUE);

	public void insert(final String s) {
		TrieNode currentNode = root;
		for (int i = 0; i < s.length(); i++) {
			final Map<Character, TrieNode> children = currentNode.getChildren();
			final char c = s.charAt(i);
			TrieNode node = children.get(c);
			if (node == null) {
				node = new TrieNode(c);
				children.put(c, node);
			}
			currentNode.setLeaf(false);
			currentNode = node;
		}
	}

	public boolean find(final String s) {

		return false;
	}

	private static class TrieNode {
		final char value;
		final Map<Character, TrieNode> children;
		boolean isLeaf;

		TrieNode(final char value) {
			this.value = value;
			children = new HashMap<>();
			isLeaf = true;
		}

		void setLeaf(final boolean isLeaf) {
			this.isLeaf = isLeaf;
		}

		public boolean isLeaf() {
			return isLeaf;
		}

		public char getValue() {
			return value;
		}

		public Map<Character, TrieNode> getChildren() {
			return children;
		}

		@Override
		public String toString() {
			final StringBuilder sb = new StringBuilder();
			for (final char c : children.keySet()) {
				sb.append(c).append(",");
			}
			sb.delete(sb.length() - 1, sb.length());
			return "value: " + getValue() + ", children:" + sb.toString();
		}
	}
}
