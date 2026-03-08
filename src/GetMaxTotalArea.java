import java.util.Arrays;
import java.util.List;

/**
 * Template class for the Amazon 'rectangle from sticks' game.
 *
 * Problem summary:
 * Amazon games have introduced a new mathematical game for kids. You will be given n sticks and the player is required to form rectangles from those sticks.
   Formally, given an array of n integers representing the lengths of the sticks, you are required to create rectangles using those sticks. Note that a particular stick can be used in at most one rectangle and in order to create a rectangle we must have exactly two pairs of sticks with the same lengths. For example, you can create a rectangle using sticks of lengths [2, 2, 5, 5] and [4, 4, 4, 4] but not with [3, 3, 5, 8]. The goal of the game is to maximize the total sum of areas of all the rectangles formed.
   In order to make the game more interesting, we are allowed to reduce any integer by at most 1. Given the array sideLengths, representing the length of the sticks, find the maximum sum of areas of rectangles that can be formed such that each element of the array can be used as length or breadth of at most one rectangle and you are allowed to decrease any integer by at most 1. Since this number can be quite large, return the answer modulo 10⁹+7.
   Note: It is not a requirement that all side lengths be used. Also, a modulo b here represents the remainder obtained when an integer a is divided by an integer b.
 *
 * This file is a template only — no algorithm implementation is provided here.
 * Implement the logic inside `getMaxTotalArea`.
 *
 * Hints (for implementation): sort the sticks, greedily form pairs while
 * considering reducing by 1 to increase pairing opportunities, and multiply
 * two largest available sides to form rectangles. Use long arithmetic and
 * take modulo 1e9+7 where appropriate.
 */
public class GetMaxTotalArea {

    /** Modulo for the answer. */
    public static final long MOD = 1_000_000_007L;

    /**
     * Compute maximum total area of rectangles that can be formed from the
     * given stick side lengths with the option to decrease any stick by at most 1.
     *
     * NOTE: This is only the function signature and documentation. Provide an
     * implementation in this method to solve the problem.
     *
     * @param sideLengths array of stick lengths
     * @return maximum total area modulo 1_000_000_007
     */
    public static long getMaxTotalArea(int[] sideLengths) {
        // TODO: implement the algorithm here
        // Placeholder to indicate the method is not yet implemented.

        // Sort ascending, then reverse to get descending order
        // (Arrays.sort for primitive int[] doesn't accept a Comparator)
        Arrays.sort(sideLengths);
        for (int i = 0, j = sideLengths.length - 1; i < j; i++, j--) {
            int tmp = sideLengths[i];
            sideLengths[i] = sideLengths[j];
            sideLengths[j] = tmp;
        }

        //2, 2, 3, 5, 6, 6
        // 6, 6, 5, 3, 2, 2

        List<Integer> pairs = new java.util.ArrayList<>();
        int i = 0;
        while(i < sideLengths.length - 1) {
            if (sideLengths[i] == sideLengths[i+1]) {
                pairs.add(sideLengths[i]);
                i += 2; // skip the next one since it's a pair
            } else if (sideLengths[i] - 1 == sideLengths[i+1]) {
                // If we can reduce sideLengths[i] by 1 to form a pair
                pairs.add(sideLengths[i] - 1);
                i += 2; // skip the next one since it's a pair
            } else {
                i++; // move to the next stick
                
            }
        }

    
        long ans = 0;

       for(int i1 = 0 ; i1 < pairs.size() - 1; i1 += 2) {
            ans = (ans + (long) pairs.get(i1) * pairs.get(i1 + 1)) % MOD;
        }

        return ans;
    }

    /**
     * Simple main showing usage of the template. Replace or extend with tests.
     */
    // ───── Helper to run a single test case ─────
    private static int passed = 0, failed = 0;

    private static void test(String name, int[] input, long expected) {
        long result = getMaxTotalArea(input);
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
        // Pairs: (6,6) and (2,2) → rectangle 6×2 = 12
        test("Example from problem",
             new int[]{2, 6, 6, 2, 3, 5}, 12);

        // ── All four sticks identical → square ──
        // [4,4,4,4] → one rectangle 4×4 = 16
        test("Square (all same)",
             new int[]{4, 4, 4, 4}, 16);

        // ── No rectangle possible ──
        // [3,3,5,8] → only one pair (3,3), need two pairs for a rectangle
        test("No rectangle possible",
             new int[]{3, 3, 5, 8}, 0);

        // ── Single stick ──
        test("Single stick",
             new int[]{7}, 0);

        // ── Two sticks (one pair, but need two pairs) ──
        test("Two sticks same",
             new int[]{5, 5}, 0);

        // ── Three sticks ──
        test("Three sticks",
             new int[]{3, 3, 3}, 0);

        // ── Empty array ──
        test("Empty array",
             new int[]{}, 0);

        // ── Reduce-by-1 creates a pair ──
        // [3,4] → reduce 4→3 → pair (3,3). Still only one pair, no rectangle.
        test("Reduce creates one pair only",
             new int[]{3, 4}, 0);

        // ── Reduce-by-1 enables a rectangle ──
        // [3,4,3,4] → already two pairs (3,3) and (4,4) → 3×4 = 12
        test("Two natural pairs",
             new int[]{3, 4, 3, 4}, 12);

        // ── Reduce-by-1 to form pairs ──
        // [5,6,5,6] → two pairs already → 5×6 = 30
        // But also [5,6,6,5] same thing
        test("Already paired sticks",
             new int[]{5, 6, 5, 6}, 30);

        // ── Reduce-by-1 needed for both pairs ──
        // [3,4,5,6] sorted desc: 6,5,4,3
        //   6→5 pairs with 5 → pair(5)
        //   4→3 pairs with 3 → pair(3)
        //   rectangle 5×3 = 15
        test("Reduce-by-1 for both pairs",
             new int[]{3, 4, 5, 6}, 15);

        // ── Multiple rectangles ──
        // [2,2,3,3,5,5,8,8] → pairs: 8,5,3,2 → rects: 8×5=40, 3×2=6 → 46
        test("Multiple rectangles",
             new int[]{2, 2, 3, 3, 5, 5, 8, 8}, 46);

        // ── Large identical sticks ──
        // [1000000000, 1000000000, 1000000000, 1000000000]
        // → 10^9 × 10^9 = 10^18 mod 10^9+7
        long bigExpected = ((long) 1_000_000_000L * 1_000_000_000L) % MOD;
        test("Large sticks (mod check)",
             new int[]{1_000_000_000, 1_000_000_000, 1_000_000_000, 1_000_000_000},
             bigExpected);

        // ── Consecutive values → reduce-by-1 pairs them ──
        // [1,2,3,4,5,6,7,8] sorted desc: 8,7,6,5,4,3,2,1
        //   (8→7,7) pair 7 — (6→5,5) pair 5 — (4→3,3) pair 3 — (2→1,1) pair 1
        //   rects: 7×5=35, 3×1=3 → 38
        test("Consecutive 1-8",
             new int[]{1, 2, 3, 4, 5, 6, 7, 8}, 38);

        // ── Odd one out ──
        // [10, 10, 10, 10, 10] → two pairs (10,10), leftover 10
        //   rectangle 10×10 = 100
        test("Five identical sticks",
             new int[]{10, 10, 10, 10, 10}, 100);

        // ── All sticks differ by more than 1 ──
        // [1, 5, 10, 20] → no pairs possible even with reduce
        test("No pairs possible",
             new int[]{1, 5, 10, 20}, 0);

        // ── Mix of natural and reduce pairs ──
        // [7, 7, 8, 8, 9, 10]
        //   sorted desc: 10,9,8,8,7,7
        //   (10→9,9) pair 9, (8,8) pair 8, (7,7) pair 7
        //   3 pairs → rect 9×8=72, leftover pair 7 → 72
        test("Mix natural and reduce pairs",
             new int[]{7, 7, 8, 8, 9, 10}, 72);

        // ── Six sticks forming three pairs ──
        // [1,1,2,2,3,3] → pairs 3,2,1 → rect 3×2=6, leftover 1 → 6
        test("Three pairs, one rectangle",
             new int[]{1, 1, 2, 2, 3, 3}, 6);

        // ── Stress: eight pairs → four rectangles ──
        // [1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8]
        // pairs: 8,7,6,5,4,3,2,1 → rects: 8×7=56, 6×5=30, 4×3=12, 2×1=2 → 100
        test("Eight pairs, four rectangles",
             new int[]{1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 8, 8}, 100);

        // ── Summary ──
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  Total: " + (passed + failed)
                         + "  |  Passed: " + passed
                         + "  |  Failed: " + failed);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
