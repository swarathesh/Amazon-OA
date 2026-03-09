package book;

import java.util.Arrays;

/**
 * Template class for "First and Last Index of Target" problem.
 *
 * Problem summary:
 * Given an array of integers sorted in non-decreasing order, return the first and last indexes
 * of a target number. If the target is not found, return [-1, -1].
 *
 * Example:
 *   nums = [1, 2, 3, 4, 4, 4, 5, 6, 7, 8, 9, 10, 11], target = 4
 *   Output: [3, 5]
 *   Explanation: The first and last occurrences of number 4 are indexes 3 and 5, respectively.
 *
 * Constraints:
 *   1 ≤ nums.length ≤ 10^5
 *   nums is sorted in non-decreasing order
 *
 * Function Description:
 *   Complete the function findFirstAndLastIndex.
 *
 *   Parameters:
 *     int[] nums — the input sorted array
 *     int target — the number to search for
 *
 *   Returns:
 *     int[] — [firstIndex, lastIndex] of target, or [-1, -1] if not found
 */
public class FirstAndLastIndexOfTarget {

    /**
     * Find the first and last index of target in nums.
     *
     * @param nums sorted input array
     * @param target number to search for
     * @return [firstIndex, lastIndex] or [-1, -1] if not found
     * 1, 2, 3, 4 , 4, 4, S, 6, 7, 8, 9, 10, 11
     */
    public static int[] findFirstAndLastIndex(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        int first = -1, last = -1;
        // Find first occurrence
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                first = mid;
                right = mid - 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        if (first == -1) return new int[]{-1, -1};
        // Find last occurrence
        left = 0;
        right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] == target) {
                last = mid;
                left = mid + 1;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return new int[]{first, last};
    }

    // ───── Test harness ─────
    private static int passed = 0, failed = 0;

    private static void test(String name, int[] nums, int target, int[] expected) {
        int[] result = findFirstAndLastIndex(nums, target);
        if (Arrays.equals(result, expected)) {
            System.out.println("  ✅ " + name + " — got " + Arrays.toString(result));
            passed++;
        } else {
            System.out.println("  ❌ " + name + " — expected " + Arrays.toString(expected) + ", got " + Arrays.toString(result));
            failed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("Running sample test cases...\n");

        // ── Provided example ──
        test("Example from problem",
             new int[]{1, 2, 3, 4, 4, 4, 5, 6, 7, 8, 9, 10, 11},
             4,
             new int[]{3, 5});

        // ── Target not found ──
        test("Target not found",
             new int[]{1, 2, 3, 5, 6, 7},
             4,
             new int[]{-1, -1});

        // ── Single occurrence ──
        test("Single occurrence",
             new int[]{1, 2, 3, 4, 5},
             4,
             new int[]{3, 3});

        // ── All elements are target ──
        test("All elements are target",
             new int[]{4, 4, 4, 4},
             4,
             new int[]{0, 3});

        // ── Empty array ──
        test("Empty array",
             new int[]{},
             4,
             new int[]{-1, -1});

        // ── Summary ──
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  Total: " + (passed + failed)
                         + "  |  Passed: " + passed
                         + "  |  Failed: " + failed);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
