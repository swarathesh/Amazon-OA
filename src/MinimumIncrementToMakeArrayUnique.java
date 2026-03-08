import java.util.Arrays;

/**
 * Template class for LeetCode/Amazon OA — "Minimum Increment to Make Array Unique" problem.
 *
 * Problem summary:
 * Given an integer array nums, increment any element by 1 any number of times.
 * Return the minimum number of moves required to make all elements unique.
 *
 * Example:
 *   nums = [3,2,1,2,1,7]
 *   After moves: [1,2,3,4,5,7] (unique)
 *   Minimum moves = 6
 *
 * Constraints:
 *   1 ≤ nums.length ≤ 10^5
 *   0 ≤ nums[i] ≤ 10^6
 *
 * Function Description:
 *   Complete the function minIncrementForUnique.
 *
 *   Parameters:
 *     int[] nums — the input array
 *
 *   Returns:
 *     int — minimum number of moves required
 */
public class MinimumIncrementToMakeArrayUnique {

    /**
     * Compute the minimum number of moves to make all elements unique.
     *
     * @param nums input array
     * @return minimum number of moves
     */
    public static int minIncrementForUnique(int[] nums) {
         // nums = [3,2,1,2,1,7] → moves = 6, [ 3, 4, 1, 2, 5, 7]
         // 1,1,2,2,3,7 -> 1,2,3,4,5,7
         Arrays.sort(nums);
         int nextNum = Integer.MIN_VALUE;
         int moves = 0;
         for(int i = 0 ; i < nums.length ; i++){
            int current = nums[i];
            if(current < nextNum){
                // need to bump up current to nextNum
                moves += nextNum - current;
                current = nextNum;
            }
            nextNum = current+ 1;

         }
            return moves;
    }

    // ───── Test harness ─────
    private static int passed = 0, failed = 0;

    private static void test(String name, int[] nums, int expected) {
        int result = minIncrementForUnique(nums);
        if (result == expected) {
            System.out.println("  ✅ " + name + " — got " + result);
            passed++;
        } else {
            System.out.println("  ❌ " + name + " — expected " + expected + ", got " + result);
            failed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("Running sample test cases...\n");

        // ── Provided example ──
        // nums = [3,2,1,2,1,7] → moves = 6
        test("Example from problem",
             new int[]{3, 2, 1, 2, 1, 7},
             6);

        // ── All unique ──
        test("All unique",
             new int[]{1, 2, 3, 4},
             0);

        // ── All same ──
        // [2,2,2,2] → [2,3,4,5], moves = 1+2+3=6
        test("All same",
             new int[]{2, 2, 2, 2},
             6);

        // ── Large values ──
        test("Large values",
             new int[]{1000000, 1000000},
             1);

        // ── Empty array ──
        test("Empty array",
             new int[]{},
             0);

        // ── Summary ──
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  Total: " + (passed + failed)
                         + "  |  Passed: " + passed
                         + "  |  Failed: " + failed);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
