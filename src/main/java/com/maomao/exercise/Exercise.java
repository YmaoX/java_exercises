package com.maomao.exercise;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Exercise {
	public static void main(final String[] args) {
//		final List<Integer> listA = Arrays.asList(2, 4, 6);
//		final List<Integer> listB = Arrays.asList(9, 3, 6, 5);
//		System.out.println(add2Numbers(listA, listB));

//		System.out.println(longestSubstring("bbbbb"));
	}

	/*
	 * You are given two non-empty linked lists representing two non-negative integers.
	 * The digits are stored in reverse order and each of their nodes contain a single digit.
	 * Add the two numbers and return it as a linked list.
	 * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
	 */
	public static List<Integer> add2Numbers(final List<Integer> a, final List<Integer> b) {
		//suppose lists are not null
		final List<Integer> rtn = new ArrayList<>();
		int carry = 0;
		final Iterator<Integer> iterA = a.iterator();
		final Iterator<Integer> iterB = b.iterator();
		while (iterA.hasNext() && iterB.hasNext()) {
			int sum = iterA.next() + iterB.next() + carry;
			if (sum >= 10) {
				carry = 1;
				sum -= 10;
			} else {
				carry = 0;
			}
			rtn.add(sum);
		}
		appendRest(rtn, iterA, carry);
		appendRest(rtn, iterB, carry);

		return rtn;
	}

	private static void appendRest(final List<Integer> rtn, final Iterator<Integer> iter, final int carry) {
		int localCarry = carry;
		while (iter.hasNext()) {
			int sum = iter.next() + localCarry;
			if (sum >= 10) {
				localCarry = 1;
				sum -= 10;
			} else {
				localCarry = 0;
			}
			rtn.add(sum);
		}
	}

	/*
	 * Given a string, find the length of the longest substring without repeating characters.
	 */
	public static int longestSubstring(final String s) {
		final Set<Character> unique = new HashSet<>();
		int length = 0;
		int i = 0;
		for (int j = 0; j < s.length(); j++) {
			final char c = s.charAt(j);
			if (unique.contains(c)) {
				final int currentLen = j - i;
				if (currentLen > length) {
					length = currentLen;
				}
				//search for new start
				for (; i < j; i++) {
					if (c == s.charAt(i)) {
						i += 1;
						break;
					} else {
						unique.remove(s.charAt(i));
					}
				}
			} else {
				unique.add(c);
			}
		}

		return length;
	}

	/*
	 * Given a string s, find the longest palindromic substring in s. You may assume that the maximum length of s is 1000.
	 */
	public static String longestPalindromicSubstring(final String s) {

		return null;
	}
}
