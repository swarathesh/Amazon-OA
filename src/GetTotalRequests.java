import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Template class for Amazon OA — "Total Requests Served" problem.
 *
 * Problem summary:
 * Developers at Amazon have their applications deployed on n servers.
 * Initially, the i-th server has an id server[i] and can handle server[i]
 * requests at a time.
 *
 * For maintenance purposes, some servers are replaced periodically. On the
 * j-th day, ALL servers whose id equals replaceId[j] are replaced with
 * servers having id newId[j] (and therefore can now serve newId[j] requests).
 *
 * The total number of requests served on the j-th day is the sum of the ids
 * of all servers AFTER that day's replacement.
 *
 * Given server[], replaceId[], and newId[], return an array where the j-th
 * element is the total requests served on day j.
 *
 * Note: Indices are 1-based in the problem statement; Java arrays are 0-based.
 *
 * ──────────────────────────────────────────────────────
 * Example (from problem):
 *   n = 2, server = [20, 10], replaceId = [10, 20], newId = [20, 1]
 *
 *   Day 1: servers [20,10] → replace id 10 with 20 → [20,20] → total = 40
 *   Day 2: servers [20,20] → replace id 20 with 1  → [1, 1]  → total = 2
 *
 *   Answer: [40, 2]
 *
 * ──────────────────────────────────────────────────────
 * Constraints:
 *   1 ≤ n ≤ 10^5
 *   1 ≤ server[i], replaceId[i], newId[i] ≤ 10^4
 *
 * ──────────────────────────────────────────────────────
 * Function Description:
 *   Complete the function getTotalRequests.
 *
 *   Parameters:
 *     int server[n]    — the initial server ids
 *     int replaceId[n] — the ids of the servers to replace on each day
 *     int newId[n]     — the new ids for the replaced servers
 *
 *   Returns:
 *     int[n] — the total number of requests served each day
 */
public class GetTotalRequests {

    /**
     * Compute the total requests served each day after performing the
     * server replacements.
     *
     * @param server    initial server ids
     * @param replaceId id to replace on each day
     * @param newId     new id that replaces all matching servers
     * @return array of total requests per day
     */
    public static int[] getTotalRequests(int[] server, int[] replaceId, int[] newId) {
        int n = replaceId.length;
        int[] totalRequests = new int[n];

        // Step 1: Build frequency map and compute initial total sum
        Map<Integer, Integer> freqMap = new HashMap<>();
        int totalSum = 0;
        for (int id : server) {
            freqMap.merge(id, 1, Integer::sum);
            totalSum += id;
        }

        // Step 2: Process each day in O(1)
        for (int i = 0; i < n; i++) {
            int oldId = replaceId[i];
            int newIdVal = newId[i];
            int count = freqMap.getOrDefault(oldId, 0);

            if (count > 0 && oldId != newIdVal) {
                // Remove old contribution, add new contribution
                totalSum -= oldId * count;
                totalSum += newIdVal * count;

                // Update frequency map
                freqMap.remove(oldId);
                freqMap.merge(newIdVal, count, Integer::sum);
            }

            totalRequests[i] = totalSum;
        }

        return totalRequests;
    }

    // ───── Test harness ─────
    private static int passed = 0, failed = 0;

    private static void test(String name, int[] server, int[] replaceId, int[] newId, int[] expected) {
        int[] result = getTotalRequests(server, replaceId, newId);
        if (Arrays.equals(result, expected)) {
            System.out.println("  ✅ " + name + " — got " + Arrays.toString(result));
            passed++;
        } else {
            System.out.println("  ❌ " + name
                + " — expected " + Arrays.toString(expected)
                + ", got " + Arrays.toString(result));
            failed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("Running sample test cases...\n");

        // ── Problem example ──
        // Day 1: [20,10] → replace 10→20 → [20,20] → 40
        // Day 2: [20,20] → replace 20→1  → [1,1]   → 2
        test("Problem example",
             new int[]{20, 10},
             new int[]{10, 20},
             new int[]{20, 1},
             new int[]{40, 2});

        // ── Sample Case 0 ──
        // server=[3,3], replaceId=[3,1], newId=[1,5]
        // Day 1: [3,3] → replace 3→1 → [1,1] → 2
        // Day 2: [1,1] → replace 1→5 → [5,5] → 10
        test("Sample Case 0",
             new int[]{3, 3},
             new int[]{3, 1},
             new int[]{1, 5},
             new int[]{2, 10});

        // ── Sample Case 1 ──
        // server=[2,5,2], replaceId=[2,5,3], newId=[3,1,5]
        // Day 1: [2,5,2] → replace 2→3 → [3,5,3] → 11
        // Day 2: [3,5,3] → replace 5→1 → [3,1,3] → 7
        // Day 3: [3,1,3] → replace 3→5 → [5,1,5] → 11
        test("Sample Case 1",
             new int[]{2, 5, 2},
             new int[]{2, 5, 3},
             new int[]{3, 1, 5},
             new int[]{11, 7, 11});

        // ── Edge: single server ──
        // server=[5], replaceId=[5], newId=[3]
        // Day 1: [5] → replace 5→3 → [3] → 3
        test("Single server",
             new int[]{5},
             new int[]{5},
             new int[]{3},
             new int[]{3});

        // ── Edge: replacement id not present ──
        // server=[1,2,3], replaceId=[9,9,9], newId=[5,5,5]
        // No server has id 9, so nothing changes each day → sum = 6
        test("Replace id not present",
             new int[]{1, 2, 3},
             new int[]{9, 9, 9},
             new int[]{5, 5, 5},
             new int[]{6, 6, 6});

        // ── Edge: all servers same id, replaced to same id ──
        // server=[4,4,4], replaceId=[4,4,4], newId=[4,4,4]
        // Nothing effectively changes → 12 each day
        test("No-op replacement",
             new int[]{4, 4, 4},
             new int[]{4, 4, 4},
             new int[]{4, 4, 4},
             new int[]{12, 12, 12});

        // ── Chain replacement ──
        // server=[1,1,1,1], replaceId=[1,2,3,4], newId=[2,3,4,5]
        // Day 1: [1,1,1,1] → replace 1→2 → [2,2,2,2] → 8
        // Day 2: [2,2,2,2] → replace 2→3 → [3,3,3,3] → 12
        // Day 3: [3,3,3,3] → replace 3→4 → [4,4,4,4] → 16
        // Day 4: [4,4,4,4] → replace 4→5 → [5,5,5,5] → 20
        test("Chain replacement",
             new int[]{1, 1, 1, 1},
             new int[]{1, 2, 3, 4},
             new int[]{2, 3, 4, 5},
             new int[]{8, 12, 16, 20});

        // ── Partial replacement (mixed ids) ──
        // server=[1,2,3,4,5], replaceId=[2,4,6,1,3], newId=[10,10,10,10,10]
        // Day 1: [1,2,3,4,5] → replace 2→10  → [1,10,3,4,5]  → 23
        // Day 2: [1,10,3,4,5] → replace 4→10 → [1,10,3,10,5] → 29
        // Day 3: no 6 present                 → [1,10,3,10,5] → 29
        // Day 4: replace 1→10                 → [10,10,3,10,5]→ 38
        // Day 5: replace 3→10                 → [10,10,10,10,5]→45
        test("Partial replacement",
             new int[]{1, 2, 3, 4, 5},
             new int[]{2, 4, 6, 1, 3},
             new int[]{10, 10, 10, 10, 10},
             new int[]{23, 29, 29, 38, 45});

        // ── Summary ──
        System.out.println("\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("  Total: " + (passed + failed)
                         + "  |  Passed: " + passed
                         + "  |  Failed: " + failed);
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }
}
