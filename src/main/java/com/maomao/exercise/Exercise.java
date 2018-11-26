package com.maomao.exercise;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maomao.exercise.Trie.TrieNode;

public class Exercise {
	private static Logger logger = LoggerFactory.getLogger(Exercise.class);

	public static void main(final String[] args) {
//		final List<Integer> listA = Arrays.asList(2, 4, 6);
//		final List<Integer> listB = Arrays.asList(9, 3, 6, 5);
//		System.out.println(add2Numbers(listA, listB));
//		System.out.println(longestSubstring("bbbbb"));
//		System.out.println(Arrays.toString(mostWater(new int[] { 1, 8, 6, 2, 5, 4, 8, 3, 7 })));
//		for (final int[] arr : threeSum(new int[] { -1, 0, 1, -2, 2, -1, -4 })) {
//			System.out.println(Arrays.toString(arr));
//		}
//		final int[] in = new int[] { 3, 1, 8, 3, 2, 5, 4, 8, 3, 7 };
//		Sort.quicksort(in);
//		final int i = removeDuplicates(in);
//		for (int j = 0; j < i; j++) {
//			System.out.println(in[j]);
//		}
//		System.out.println(countAndSay(5));
//		final List<List<Integer>> ans = combinationSum(new HashSet<>(Arrays.asList(2, 3, 5)), 8);
//		System.out.println(ans);
//		System.out.println(splitArrayConsecutiveSub(new int[] { 1, 2, 3, 4, 4, 5 }));
//		final int[] arr = new int[] { 7, 6, 5, 4, 3, 2 };
//		nextPermutation(arr);
//		System.out.println(Arrays.toString(arr));
		System.out.println(searchInsertPosition(new int[] { 1, 3, 5, 6 }, 5));
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

	/*
	 * Write a function to find the longest common prefix string amongst an array of strings.
	 * If there is no common prefix, return an empty string "".
	 */
	public static String longestCommonPrefix(final String... ss) {
		final Trie trie = new Trie();
		for (final String s : ss) {
			trie.insert(s);
		}
		final StringBuilder sb = new StringBuilder();
		Map<Character, TrieNode> children = trie.getRoot().getChildren();
		while (children.size() == 1) {
			final Entry<Character, TrieNode> entry = children.entrySet().iterator().next();
			sb.append(entry.getKey());
			children = entry.getValue().getChildren();
		}
		return sb.toString();
	}

	/*
	 * Given an array nums of n integers, are there elements a, b, c in nums such that a + b + c = 0?
	 * Find all unique triplets in the array which gives the sum of zero.
	 */
	public static List<int[]> threeSum(final int[] input) {
		final List<int[]> list = new ArrayList<>();
		Arrays.sort(input);
		for (int i = 0; i < input.length; i++) {
			final int fixed = input[i];
			if (i > 0 && fixed == input[i - 1]) {
				i++;
			} else {
				for (int l = i, r = input.length - 1; l < r;) {
					if (fixed + input[l] + input[r] < 0) {
						l += 1;
					} else if (fixed + input[l] + input[r] > 0) {
						r -= 1;
					} else {
						list.add(new int[] { input[i], input[l], input[r] });
						l += 1;
						r -= 1;
					}
				}
			}
		}
		return list;
	}

	/*
	 * Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.
	 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
	 */
	public static int removeDuplicates(final int[] arr) {
		if (arr.length < 2) {
			return arr.length;
		}
		int noDup = 0;
		for (int i = 1; i < arr.length; i++) {
			if (arr[noDup] != arr[i]) {
				noDup += 1;
				arr[noDup] = arr[i];
			}
		}
		return noDup + 1;
	}

	/*
	 * The count-and-say sequence is the sequence of integers with the first five terms as following:
	 * 1.     1
	 * 2.     11
	 * 3.     21
	 * 4.     1211
	 * 5.     111221
	 *
	 * 1 is read off as "one 1" or 11.
	 * 11 is read off as "two 1s" or 21.
	 * 21 is read off as "one 2, then one 1" or 1211.
	 * Given an integer n, generate the nth term of the count-and-say sequence.
	 * Note: Each term of the sequence of integers will be represented as a string.
	 */
	public static String countAndSay(final int n) {
		String previous = "1";
		for (int i = 1; i < n; i++) {
			previous = countAndSayIter(previous);
		}
		return previous;
	}

	private static String countAndSayIter(final String in) {
		int count = 0;
		char previous = ' ';
		final StringBuilder sb = new StringBuilder();
		for (final char c : in.toCharArray()) {
			if (previous == c || previous == ' ') {
				count += 1;
			} else {
				sb.append(count).append(previous);
			}
			previous = c;
		}
		sb.append(count).append(previous);
		return sb.toString();
	}

	/*
	 * Given a set of candidate numbers (candidates) (without duplicates) and a target number (target),
	 * find all unique combinations in candidates where the candidate numbers sums to target.
	 * The same repeated number may be chosen from candidates unlimited number of times.
	 * Note:
	 * 	All numbers (including target) will be positive integers.
	 * 	The solution set must not contain duplicate combinations.
	 */
	public static List<List<Integer>> combinationSum(final Set<Integer> set, final int target) {
		if (set.isEmpty()) {
			return Collections.emptyList();
		}
		//solutions = solutions with first element + solutions without first element
		final List<List<Integer>> solutions = new ArrayList<>();
		final int first = set.iterator().next();
		final int restTarget = target - first;
		if (restTarget == 0) {
			final List<Integer> list = new ArrayList<>();
			list.add(first);
			solutions.add(list);
		} else if (restTarget > 0) {
			final Set<Integer> rest = new HashSet<>(set);
			rest.remove(first);
			final List<List<Integer>> s1 = combinationSum(set, restTarget);
			final List<List<Integer>> s2 = combinationSum(rest, target);
			for (final List<Integer> list : s1) {
				list.add(first);
			}
			solutions.addAll(s1);
			solutions.addAll(s2);
		}

		return solutions;
	}

	/*
	 * You are given an integer array sorted in ascending order (may contain duplicates),
	 * you need to split them into several subsequences, where each subsequences consist
	 * of at least 3 consecutive integers. Return whether you can make such a split.
	 */
	public static boolean splitArrayConsecutiveSub(final int[] nums) {
		final Map<Integer, Integer> frequency = new HashMap<>();
		final Map<Integer, Integer> tails = new HashMap<>();
		for (final int num : nums) {
			frequency.put(num, frequency.getOrDefault(num, 0) + 1);
		}
		for (final int num : nums) {
			if (frequency.get(num) == 0) {
				continue;
			}
			final int count = tails.getOrDefault(num - 1, 0);
			if (count > 0) {
				tails.put(num - 1, count - 1);
				tails.put(num, tails.getOrDefault(num, 0) + 1);
			} else if (count == 0) {
				if (frequency.getOrDefault(num + 1, 0) > 0 && frequency.getOrDefault(num + 2, 0) > 0) {
					frequency.put(num + 1, frequency.getOrDefault(num + 1, 0) - 1);
					frequency.put(num + 2, frequency.getOrDefault(num + 2, 0) - 1);
					tails.put(num + 2, tails.getOrDefault(num + 2, 0) + 1);
				} else {
					return false;
				}
			}
			frequency.put(num, frequency.getOrDefault(num, 0) - 1);
		}
		return true;
	}

	/*Implement next permutation, which rearranges numbers into the lexicographically next greater permutation of numbers.
	 * If such arrangement is not possible, it must rearrange it as the lowest possible order (ie, sorted in ascending order).
	 * The replacement must be in-place and use only constant extra memory.
	 */
	public static void nextPermutation(final int[] arr) {
		if (arr.length >= 2) {
			int i = arr.length - 1, j = arr.length - 2;
			while (j >= 0 && arr[j] >= arr[i]) {
				i -= 1;
				j -= 1;
			}

			if (j >= 0) {
				while (arr[i] >= arr[j]) {
					i += 1;
				}
				final int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
			}

			j += 1;
			i = arr.length - 1;
			while (j < i) {
				final int tmp = arr[i];
				arr[i] = arr[j];
				arr[j] = tmp;
				j += 1;
				i -= 1;
			}
		}
	}

	public static int searchInsertPosition(final int[] arr, final int target) {
		int i = 0, j = arr.length - 1;
		while (i <= j) {
			final int mid = i + ((j - i) >> 1);
			if (arr[mid] > target) {
				j = mid - 1;
			} else if (arr[mid] < target) {
				i = mid + 1;
			} else {
				return mid;
			}
		}
		//return i is always correct
		return i;
	}

}
