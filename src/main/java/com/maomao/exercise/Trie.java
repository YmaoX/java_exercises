package com.maomao.exercise;

import java.util.HashMap;
import java.util.Map;

/**
 * Simple trie implementation using map
 * @author y.xu
 *
 */
public class Trie {

//	public static void main(final String[] args) {
//		final Trie trie = new Trie();
//		trie.insert("iss");
//		trie.insert("ill");
//		trie.insert("break");
//		trie.insert("bread");
//		System.out.println(trie.find("is"));
//		System.out.println(trie.find("break"));
//		System.out.println(trie.find("isss"));
//	}

	private final TrieNode root = new TrieNode(Character.MIN_VALUE);

	public void insert(final String s) {
		Map<Character, TrieNode> children = root.getChildren();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			TrieNode node = children.get(c);
			if (node == null) {
				node = new TrieNode(c);
				children.put(c, node);
				if (i == s.length() - 1) {
					node.setLeaf(true);
				}
			}
			children = node.getChildren();
		}
	}

	public boolean find(final String s) {
		Map<Character, TrieNode> children = root.getChildren();
		for (int i = 0; i < s.length(); i++) {
			final char c = s.charAt(i);
			final TrieNode node = children.get(c);
			if (node == null) {
				return false;
			}
			if (!node.isLeaf()) {
				children = node.getChildren();
			} else {
				return i == s.length() - 1;
			}
		}
		return true;
	}

	public TrieNode getRoot() {
		return root;
	}

	public static class TrieNode {
		private final char value;
		private final Map<Character, TrieNode> children;
		private boolean isLeaf;

		TrieNode(final char value) {
			this.value = value;
			children = new HashMap<>();
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
			return "value: " + getValue();
		}
	}
}
