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
			final int pivot = partition2(arr, s, e);
			quicksort(arr, s, pivot - 1);
			quicksort(arr, pivot + 1, e);
		}
	}

	//bad implementation
	@SuppressWarnings("unused")
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

	private static int partition2(final int[] arr, final int left, final int right) {
		final int pivot = arr[right];
		int leftPtr = left - 1; // left (after ++)
		int rightPtr = right; // right-1 (after --)
		while (true) { // find bigger item
			while (arr[++leftPtr] < pivot) {
				// (nop)
			}
			// find smaller item
			while (rightPtr > 0 && arr[--rightPtr] > pivot) {
				// (nop)
			}
			if (leftPtr >= rightPtr) {
				break; // partition done
			} else {
				swap(arr, leftPtr, rightPtr); // swap elements
			}
		} // end while(true)
		swap(arr, leftPtr, right); // restore pivot
		return leftPtr; // return pivot location
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
				final int tmp = arr[i];
				int j = i - h;
				while (j >= 0 && arr[j] > tmp) {
					arr[j + h] = arr[j];
					j -= h;
				}
				arr[j + h] = tmp;
			}
			h = (h - 1) / 3;
		}
	}

}
