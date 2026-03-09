import java.util.*;

/**
 * Template class for "Final Priorities" scheduling problem (Code Question 2).
 *
 * Problem summary:
 * Several processes are scheduled for execution on an AWS server. On one
 * server, n processes are queued in order and the i-th process has an
 * initial priority priority[i].  The server repeatedly executes the
 * following algorithm:
 *
 *   1. Find the maximum priority p that is shared by **at least two**
 *      processes. (If there is no such priority or p == 0, the algorithm
 *      terminates.)
 *   2. Among the processes holding priority p, select the two with the
 *      lowest indices; call them process1 and process2.
 *   3. Execute process1 and remove it from the queue.
 *   4. Reduce the priority of process2 to floor(p/2) (to avoid starvation).
 *   5. Go back to step 1.
 *
 * When the algorithm terminates, some processes will have been removed and
 * the remaining ones will have updated priorities.  The relative order of
 * surviving processes stays the same; only their priorities may change.
 *
 * Given the initial priorities, return an array containing the final
 * priorities of the processes that remain (in queue order).
 *
 * Constraints:
 *   1 ≤ priority[i] ≤ 10^9
 *   1 ≤ n ≤ 10^5 (implicit by problem statement)
 *
 * Example (from prompt):
 *   priorities = [6,6,6,1,2,2]
 *   p = 6 at indices 0,1,2 → remove index 0, reduce index 1 to 3 →
 *         priorities = [3,6,1,2,2]
 *   p = 2 at indices 3,4     → remove index 3, reduce index 4 to 1 →
 *         priorities = [3,6,1,1]
 *   p = 1 at indices 2,3     → remove index 2, reduce index 3 to 0 →
 *         priorities = [3,6,0]
 *   no p>0 with freq≥2 remaining; algorithm stops → result [3,6,0]
 *
 * Sample Case 0:
 *   Input: priority = [4,4,2,1]
 *   Output: [0]
 *   Explanation: remove first 4, second becomes 2 -> [2,2,1]
 *                remove first 2, second becomes 1 -> [1,1]
 *                remove first 1, second becomes 0 -> [0]
 *                terminate.
 */
public class FindFinalPriorities {

    /**
     * Compute the final priorities after running the described scheduling
     * algorithm.
     *
     * @param priority initial priority array (queue order)
     * @return priorities of remaining processes in the same order
     */
    public static int[] findFinalPriorities(int[] priority) {
        LinkedList<Integer> queue = new LinkedList<>();
        for (int p : priority) {
            queue.add(p);
        }

        while (true) {
            // Step 1: Find max priority appearing at least twice
            Map<Integer, Integer> freq = new HashMap<>();
            for (int p : queue) freq.merge(p, 1, Integer::sum);

            int maxP = 0;
            for (Map.Entry<Integer, Integer> e : freq.entrySet()) {
                if (e.getValue() >= 2) maxP = Math.max(maxP, e.getKey());
            }

            // Step 2: Terminate if no duplicate or p == 0
            if (maxP == 0) break;

            // Step 3: Find first two indices with priority maxP
            int firstIdx = -1, secondIdx = -1;
            for (int i = 0; i < queue.size(); i++) {
                if (queue.get(i) == maxP) {
                    if (firstIdx == -1) firstIdx = i;
                    else { secondIdx = i; break; }
                }
            }

            // Step 4: Remove process1, update process2 to floor(p/2)
            queue.remove(firstIdx);
            queue.set(secondIdx - 1, maxP / 2); // shift by -1 after removal
        }

        int[] result = new int[queue.size()];
        for (int i = 0; i < queue.size(); i++) {
            result[i] = queue.get(i);
        }
        return result;
    }

    // ───── Test harness ─────
    private static int passed = 0, failed = 0;

    private static void test(String name, int[] priority, int[] expected) {
        int[] result = findFinalPriorities(priority);
        if (Arrays.equals(result, expected)) {
            System.out.println("  ✅ " + name + " — got " + Arrays.toString(result));
            passed++;
        } else {
            System.out.println("  ❌ " + name + " — expected " + Arrays.toString(expected)
                               + ", got " + Arrays.toString(result));
            failed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("Running sample test cases...\n");

        test("Example from prompt",
             new int[]{6, 6, 6, 1, 2, 2},
             new int[]{3, 6, 0});

        test("Sample case 0",
             new int[]{4, 4, 2, 1},
             new int[]{0});

        // some additional edge cases
        test("No duplicates",
             new int[]{5, 4, 3},
             new int[]{5, 4, 3});

        test("All same",
             new int[]{2, 2, 2, 2},
           new int[]{0});

        test("Single element",
             new int[]{7},
             new int[]{7});

        test("Reduction to zero stays",
             new int[]{1, 1},
             new int[]{0});

        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  Total: " + (passed + failed)
                         + "  |  Passed: " + passed
                         + "  |  Failed: " + failed);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
