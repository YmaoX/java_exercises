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
//		System.out.println(searchInsertPosition(new int[] { 1, 3, 5, 6 }, 5));
//		System.out.println(Arrays.toString(searchForARange(new int[] { 5, 7, 7, 8, 8, 10 }, 6)));
//		System.out.println(multiplyStrings("123", "456"));
//		System.out.println(uniquePathsDP(7, 3));
//		System.out.println(uniquePaths2(new int[][] { { 0, 0, 0 }, { 0, 1, 0 }, { 0, 0, 0 } }));
//		System.out.println(heightOfTree(new int[] { -1, 0, 1, 6, 6, 0, 0, 2, 7 }));
//		System.out.println(simplyPath("/aaa/../.././bbb//..//c"));
		final int[][] matrix = { { 1, 3, 5, 7 }, { 10, 11, 16, 20 }, { 23, 30, 34, 50 } };
		System.out.println(searchInMatrixV2(matrix, 30));
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

	/*
	 * Given an array of integers nums sorted in ascending order, find the starting and ending position of a given target value.
	 * Your algorithm's runtime complexity must be in the order of O(log n).
	 * If the target is not found in the array, return [-1, -1].
	 */
	public static int[] searchForARange(final int[] arr, final int target) {
		final int[] rtn = new int[2];

		int l = 0, r = arr.length - 1;
		int mid = -1;
		while (l <= r) {
			mid = l + ((r - l) >> 1);
			if (arr[mid] < target) {
				l = mid + 1;
			} else if (arr[mid] > target) {
				r = mid - 1;
			} else {
				break;
			}
		}
		if (l > r) {
			rtn[0] = -1;
			rtn[1] = -1;
		} else {
			l = mid;
			while (l - 1 >= 0 && arr[l - 1] == target) {
				l -= 1;
			}
			r = mid;
			while (r + 1 < arr.length && arr[r + 1] == target) {
				r += 1;
			}
			rtn[0] = l;
			rtn[1] = r;
		}
		return rtn;
	}

	/*
	 * Given two non-negative integers num1 and num2 represented as strings,
	 * return the product of num1 and num2, also represented as a string.
	 */
	public static String multiplyStrings(final String a, final String b) {
		//'\u0000'
		final char[] aa = a.toCharArray();
		final char[] bb = b.toCharArray();
		final int[][] rtn = new int[b.length()][aa.length + bb.length];
		for (int j = bb.length - 1, row = 0; j >= 0; j--, row++) {
			final int bInt = bb[j] - '0';
			int carry = 0;
			int i = aa.length - 1;

			for (; i >= 0; i--) {
				final int aInt = aa[i] - '0';
				final int product = aInt * bInt + carry;
				final int re = product % 10;
				carry = product / 10;
				rtn[row][i + j + 1] = re;
			}
			if (carry > 0) {
				rtn[row][i + j + 1] = carry;
			}
		}
		final StringBuilder sb = new StringBuilder();
		int carry = 0;
		for (int j = rtn[0].length - 1; j >= 0; j--) {
			int sum = 0;
			for (final int[] element : rtn) {
				sum += element[j];
			}
			sum += carry;
			carry = sum / 10;
			sb.append(sum % 10);
		}
		while (sb.charAt(sb.length() - 1) == '0') {
			sb.deleteCharAt(sb.length() - 1);
		}

		return sb.reverse().toString();
	}

	/*
	 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
	 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right
	 * corner of the grid (marked 'Finish' in the diagram below).
	 * How many possible unique paths are there?
	 */
	public static int uniquePaths(final int m, final int n) {
		return uniquePaths(m, n, 0, 0);
	}

	private static int uniquePaths(final int m, final int n, final int x, final int y) {
		//reach mark
		int count = 0;
		if (x == m - 1 && y == n - 1) {
			count = 1;
		} else if (x == m - 1) {
			count = uniquePaths(m, n, x, y + 1);
		} else if (y == n - 1) {
			count = uniquePaths(m, n, x + 1, y);
		} else {
			count = uniquePaths(m, n, x, y + 1) + uniquePaths(m, n, x + 1, y);
		}

		return count;
	}

	//dp[i][j] is the number of paths to point (i,j)
	public static int uniquePathsDP(final int m, final int n) {
		if (m == 0 || n == 0) {
			return 1;
		}

		final int[][] dp = new int[m][n];
		for (int i = 0; i < n; i++) {
			dp[0][i] = 1;
		}
		for (int i = 0; i < m; i++) {
			dp[i][0] = 1;
		}

		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
			}
		}
		return dp[m - 1][n - 1];
	}

	/*
	 * An obstacle and empty space is marked as 1 and 0 respectively in the grid.
	 */
	public static int uniquePaths2(final int[][] grid) {
		final int m = grid.length;
		if (m == 0) {
			return 0;
		}
		final int n = grid[0].length;
		if (m == 0) {
			return 0;
		}

		final int[][] dp = new int[m][n];
		for (int i = 0; i < n; i++) {
			if (grid[0][i] != 1) {
				dp[0][i] = 1;
			} else {
				dp[0][i] = 0;
				break;
			}
		}
		for (int i = 0; i < m; i++) {
			if (grid[i][0] != 1) {
				dp[i][0] = 1;
			} else {
				dp[i][0] = 0;
				break;
			}
		}
		for (int i = 1; i < m; i++) {
			for (int j = 1; j < n; j++) {
				if (grid[i][j] == 1) {
					dp[i][j] = 0;
				} else {
					dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
				}
			}
		}

		return dp[m - 1][n - 1];
	}

	/*
	 * p[] indicates the parent of ith node in the tree. Parent of the root is indicated with -1.
	 */
	public static int heightOfTree(final int p[]) {
		int maxHeight = -1;
		for (int i = 0; i < p.length; i++) {
			int j = i;
			int height = 0;
			while (p[j] != -1) {
				j = p[j];
				height += 1;
			}
			if (height > maxHeight) {
				maxHeight = height;
			}
		}
		return maxHeight;
	}

	/*
	 * Given an absolute path for a file (Unix-style), simplify it.
	 */
	public static String simplyPath(final String path) {
		if (!path.startsWith("/")) {
			//must be absolute path
			return "error";
		}
		String pathF = path;
		if (!path.endsWith("/")) {
			pathF += "/";
		}
		final StringBuilder rtn = new StringBuilder();
		int startIdx = -1;
		int tmpIdx;
		while ((tmpIdx = pathF.indexOf('/', startIdx)) != -1) {
			if (startIdx != -1) {
				final String current = pathF.substring(startIdx, tmpIdx);
				switch (current) {
				case ".":
				case "":
					break;
				case "..":
					final int idx = rtn.lastIndexOf("/");
					if (idx > -1) {
						rtn.delete(idx, rtn.length());
					}
					break;
				default:
					rtn.append("/").append(current);
					break;
				}
			}
			startIdx = tmpIdx + 1;
		}

		if (rtn.length() == 0) {
			rtn.append("/");
		}
		return rtn.toString();
	}

	/*
	 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
	 * Integers in each row are sorted from left to right.
	 * The first integer of each row is greater than the last integer of the previous row.
	 */
	public static boolean searchInMatrix(final int[][] matrix, final int target) {
		//search row
		int rowStart = 0;
		int rowEnd = matrix.length - 1;
		while (rowStart <= rowEnd) {
			final int mid = (rowStart + rowEnd) / 2;
			if (matrix[mid][0] == target) {
				return true;
			}

			if (matrix[mid][0] < target) {
				rowStart = mid + 1;
			} else {
				rowEnd = mid - 1;
			}
		}
		if (rowEnd < 0) {
			return false;
		}
		logger.debug("row start: {}", rowStart);
		logger.debug("row end: {}", rowEnd);
		int row;
		if (rowStart > matrix.length - 1) {
			row = matrix.length - 1;
		} else {
			row = rowEnd;
		}
		logger.debug("row found: {}", row);
		int start = 0;
		int end = matrix[0].length - 1;
		while (start <= end) {
			final int mid = (start + end) / 2;
			if (matrix[row][mid] == target) {
				return true;
			}
			if (matrix[row][mid] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return false;
	}

	public static boolean searchInMatrixV2(final int[][] matrix, final int target) {
		final int rowNum = matrix.length, colNum = matrix[0].length;
		int start = 0, end = rowNum * colNum - 1;
		while (start <= end) {
			final int mid = (start + end) / 2;
			final int row = (mid + 1) / colNum;
			if (row >= rowNum) {
				return false;
			}
			final int col = (mid + 1) % colNum;
			if (matrix[row][col] == target) {
				return true;
			}
			if (matrix[row][col] < target) {
				start = mid + 1;
			} else {
				end = mid - 1;
			}
		}
		return false;
	}
}
