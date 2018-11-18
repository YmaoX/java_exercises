package com.maomao.exercise;

import java.util.Arrays;
import java.util.Random;

//simple sort implementation, not generic, asc
public class Sort {
	public static void main(final String[] args) {
		final Random r = new Random();
		final int limit = 40;
		final int[] arr = new int[limit];
		for (int i = 0; i < limit; i++) {
			arr[i] = r.nextInt(50);
		}

		final int[] copy = arr.clone();
		quicksort(arr);
		printArray(arr);
		shellsort(copy);
		printArray(copy);
	}

	private static void printArray(final int[] arr) {
		System.out.println(Arrays.toString(arr));
	}

	public static void quicksort(final int[] arr) {
		if (arr != null) {
			quicksort(arr, 0, arr.length - 1);
		}
	}

	private static void quicksort(final int[] arr, final int s, final int e) {
		if (e >= s) {
			final int pivot = partition(arr, s, e);
			quicksort(arr, s, pivot - 1);
			quicksort(arr, pivot + 1, e);
		}
	}

	private static int partition(final int[] arr, final int s, final int e) {
		final int p = arr[e];
		int j = s - 1;
		for (int i = s; i <= e - 1; i++) {
			if (arr[i] < p) {
				j += 1;
				swap(arr, i, j);
			}
		}
		j += 1;
		swap(arr, j, e);
		return j;
	}

	private static void swap(final int[] arr, final int i, final int j) {
		if (i != j) {
			final int temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
		}
	}

	//---------------------------------------------------------------------
	public static void shellsort(final int[] arr) {
		int h = 1;
		while (h <= arr.length / 3) {
			h = h * 3 + 1;
		}
		while (h > 0) {
			for (int i = h; i < arr.length; i += h) {
				int j = i - h;
				for (; j >= 0; j -= h) {
					if (arr[i] > arr[j]) {
						shift(arr, j + h, i, h);
						break;
					}
				}
				if (j < 0) {
					shift(arr, 0, i, h);
				}
			}
			h = (h - 1) / 3;
		}
	}

	private static void shift(final int[] arr, final int s, final int e, final int step) {
		if (s < e) {
			final int tmp = arr[e];
			for (int i = e; i > s; i -= step) {
				arr[i] = arr[i - step];
			}
			arr[s] = tmp;
		}
	}
}
