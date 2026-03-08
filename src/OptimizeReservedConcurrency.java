import java.util.Arrays;
import java.util.Map;

/**
 * Template class for Amazon OA — "Optimize Reserved Concurrency" problem.
 *
 * Problem summary:
 * You have n AWS Lambda functions, each with a reserved-concurrency limit conc[i].
 * To avoid starvation, every function must end up with a distinct concurrency limit.
 * In one operation, you may raise function i's limit by exactly 1, incurring a cost of price[i].
 * Find the minimum total cost to make all conc values unique.
 *
 * Example:
 *   n = 5
 *   conc = [5, 2, 5, 3, 3]
 *   price = [3, 7, 8, 6, 9]
 *
 *   Optimal sequence:
 *     Bump function 0 (conc[0]) from 5 → 6 (cost 3).
 *     Bump function 3 (conc[3]) from 3 → 4 (cost 6).
 *   Final limits: [6, 2, 5, 4, 3] (all unique)
 *   Total cost: 3 + 6 = 9
 *
 * Constraints:
 *   1 ≤ n ≤ 10^5
 *   1 ≤ conc[i], price[i] ≤ 10^9
 *
 * Function Description:
 *   Complete the function optimizeReservedConcurrency.
 *
 *   Parameters:
 *     int conc[n]   — current reserved-concurrency for each lambda
 *     int price[n]  — cost to increase that lambda's limit by 1
 *
 *   Returns:
 *     long — minimum dollars required to make all conc values unique
 */
public class OptimizeReservedConcurrency {

    /**
     * Compute the minimum cost to make all concurrency limits unique.
     *
     * @param conc  current reserved-concurrency for each lambda
     * @param price cost to increase that lambda's limit by 1
     * @return minimum dollars required
     */
    // *   n = 5
//  *   conc = [5, 2, 5, 3, 3]
//  *   price = [3, 7, 8, 6, 9]
//  *
//  *   Optimal sequence:
//  *   // conc = [2,2,2], price = [1,2,3]
        // Final: [2,3,4], cost = 2+3=5
//  *   Total cost: 3 + 6 = 9
    public static long optimizeReservedConcurrency(int[] conc, int[] price) {
        int n = conc.length;
        class Lambda {
            int conc;
            int price;
            Lambda(int c, int p) { conc = c; price = p; }
        }
        Lambda[] lambdas = new Lambda[n];
        for (int i = 0; i < n; i++) lambdas[i] = new Lambda(conc[i], price[i]);
        // Sort by concurrency ascending, then by price DESCENDING
        // (expensive lambdas keep their slot; cheap ones get bumped)
        Arrays.sort(lambdas, (a, b) -> a.conc != b.conc ? Integer.compare(a.conc, b.conc) : Integer.compare(b.price, a.price));
        long totalCost = 0;
        int nextFree = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            int c = lambdas[i].conc;
            int p = lambdas[i].price;
            // If c < nextFree, bump to nextFree
            if (c < nextFree) {
                totalCost += (nextFree - c) * (long)p;
                c = nextFree;
            }
            nextFree = c + 1;
        }
        return totalCost;
    }

    // ───── Test harness ─────
    private static int passed = 0, failed = 0;

    private static void test(String name, int[] conc, int[] price, long expected) {
        long result = optimizeReservedConcurrency(conc, price);
        if (result == expected) {
            System.out.println("  ✅ " + name + " — got " + result);
            passed++;
        } else {
            System.out.println("  ❌ " + name + " — expected " + expected + ", got " + result);
            failed++;
        }
    }
    /**
     * 
i	c	nextFree	c < nextFree?	bumps	cost	totalCost
0	2	MIN	no	0	0	0
1	3	3	no	0	0	0
2	3	4	yes (3<4)	4−3=1	1×6=6	6
3	5	5	no	0	0	6
4	5	6	yes (5<6)	6−5=1	1×3=3	9 ✅
     * @param args
     */

    public static void main(String[] args) {
        System.out.println("Running sample test cases...\n");

        // ── Provided example ──
        // conc = [5, 2, 5, 3, 3], price = [3, 7, 8, 6, 9]
        // Final: [6, 2, 5, 4, 3], cost = 3 + 6 = 9
        test("Example from problem",
             new int[]{5, 2, 5, 3, 3},
             new int[]{3, 7, 8, 6, 9},
             9);

        // ── All unique already ──
        // conc = [1,2,3], price = [1,1,1] → cost = 0
        test("All unique",
             new int[]{1, 2, 3},
             new int[]{1, 1, 1},
             0);

        // ── All same, price varies ──
        // conc = [2,2,2], price = [1,2,3]
        // Optimal: price=3 stays at 2, price=2 bumps to 3 (cost 2), price=1 bumps to 4 (cost 2)
        // Total cost = 4
        test("All same, price varies",
             new int[]{2, 2, 2},
             new int[]{1, 2, 3},
             4);

        // ── Large values, minimal bumps ──
        // conc = [1000000000,1000000000], price = [1,2]
        // Optimal: price=2 stays, price=1 bumps +1 → cost = 1
        test("Large values",
             new int[]{1000000000, 1000000000},
             new int[]{1, 2},
             1);

        // ── Summary ──
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  Total: " + (passed + failed)
                         + "  |  Passed: " + passed
                         + "  |  Failed: " + failed);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
