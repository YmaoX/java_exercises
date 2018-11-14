package com.maomao.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Exercise {
	public static void main(final String[] args) {
//		final List<Integer> listA = Arrays.asList(2, 4, 6);
//		final List<Integer> listB = Arrays.asList(9, 3, 6, 5);
//		System.out.println(add2Numbers(listA, listB));

//		System.out.println(longestSubstring("bbbbb"));

		System.out.println(Arrays.toString(mostWater(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 })));
	}

	/*
	 * Given an array of integers, return indices of the two numbers such that they add up to a
	 * specific target.You may assume that each input would have exactly one solution, and you
	 * may not use the same element twice.
	 */
	public static int[] twoSum1(final int[] arr, final int target) {
		//sort list, then use two pointers
		class Pair implements Comparable<Pair> {
			int value;
			int index;

			Pair(final int value, final int index) {
				this.value = value;
				this.index = index;
			}

			@Override
			public int compareTo(final Pair o) {
				return Integer.compare(value, o.value);
			}
		}
		final Pair[] pair = new Pair[arr.length];
		for (int i = 0; i < arr.length; i++) {
			pair[i] = new Pair(arr[i], i);
		}
		Arrays.sort(pair);

		for (int i = 0, j = pair.length - 1; i < j;) {
			if (pair[i].value + pair[j].value == target) {
				return new int[] { pair[i].index, pair[j].index };
			}
			if (pair[i].value + pair[j].value < target) {
				i += 1;
			} else if (pair[i].value + pair[j].value > target) {
				j -= 1;
			}
		}
		return new int[] { -1, -1 };
	}

	public static int[] twoSum2(final int[] arr, final int target) {
		//use map to index each element
		final Map<Integer, Integer> map = new HashMap<>();
		//one loop
		for (int i = 0; i < arr.length; i++) {
			final Integer found = map.get(target - arr[i]);
			if (found != null && found.intValue() != i) {
				return new int[] { found, i };
			}
			map.put(arr[i], i);
		}

		return new int[] { -1, -1 };
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

	/*
	 * The string "PAYPALISHIRING" is written in a zigzag pattern on a given number of rows like this: (you may want to display this pattern in a fixed font for better legibility)
	 * P   A   H   N
	 * A P L S I I G
	 * Y   I   R
	 * And then read line by line: "PAHNAPLSIIGYIR"
	 * Write the code that will take a string and make this conversion given a number of rows:
	 * string convert(string text, int nRows);
	 * convert("PAYPALISHIRING", 3) should return "PAHNAPLSIIGYIR".
	 */
	public static String zigzag(final String text, final int nRows) {
		/*
		 * find a way to convert index
		 * each line i contains index: i + (2n - 2)*j
		 * exception first and last line, after each index, we add idx + 2(n - i) - 2
		 */
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nRows; i++) {
			int j = 0;
			int idx = 0;
			while ((idx = i + (2 * nRows - 2) * j) < text.length()) {
				sb.append(text.charAt(idx));
				//other line
				if (i != 0 && i != nRows - 1) {
					final int idx2 = idx + 2 * (nRows - i) - 2;
					if (idx2 < text.length()) {
						sb.append(text.charAt(idx2));
					}
				}
				j += 1;
			}
		}
		return sb.toString();
	}

	/*
	 * Reverse digits of an integer.
	 * For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
	 */
	public static int reverseInt(final int input) {
		int result = 0;
		int tmp = input > 0 ? input : -input;
		while (tmp > 0) {
			final int r = tmp % 10;
			tmp = tmp / 10;
			result = result * 10 + r;
			if (result > Integer.MAX_VALUE) {
				return 0;
			}
		}
		return input > 0 ? result : -result;
	}

	/*
	 * Given n non-negative integers a1, a2, ..., an, where each represents a point at coordinate (i, ai).
	 * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
	 * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
	 */
	public static int[] mostWater(final int[] height) {
		//2 pointers, large one stay, move small one
		int s = 0;
		final int[] rtn = new int[2];
		for (int i = 0, j = height.length - 1; i < j;) {
			final int currentS = Math.min(height[i], height[j]) * (j - i);
			if (currentS > s) {
				rtn[0] = i;
				rtn[1] = j;
				s = currentS;
			}
			if (i < j) {
				i += 1;
			} else {
				j -= 1;
			}
		}
		return rtn;
	}

}
