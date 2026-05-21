import java.util.*;

// A client class for creating scenarios (list of regions) and allocating relief to said regions.
public class Client {
    private static final Random RAND = new Random();

    public static void main(String[] args) throws Exception {
        // List<Region> scenario = createRandomScenario(10, 10, 100, 1000, 100000);
        List<Region> scenario = createSimpleScenario();
        System.out.println(scenario);

        double budget = 2000;
        Allocation allocation = allocateRelief(budget, scenario);
        printResult(allocation, budget);
    }

    /**
     * Computes and returns the optimal allocation of relief resource that helps the
     * greatest number of people without exceeding the given budget. In the case of
     * a tie in population helped, the allocation with the lower total cost is
     * returned.
     *
     * @param budget
     *            the maximum amount of money available for relief.
     * @param sites
     *            the list of regions that may receive relief.
     * @return the optimal Allocation for the given budget.
     * @throws IllegalArgumentException
     *             if sites is null.
     */
    public static Allocation allocateRelief(double budget, List<Region> sites) {
        if (sites == null) {
            throw new IllegalArgumentException();
        }

        return allocateRelief(budget, sites, 0, new Allocation());
    }

    /**
     * Explores all possible allocations by deciding whether to include or exclude
     * each region in the list; searches the powerset of possibilities.
     *
     * @param budget
     *            the maximum amount of money available for relief.
     * @param sites
     *            the list of regions being considered.
     * @param index
     *            the current index of the region being considered.
     * @param current
     *            the current allocation built so far.
     * @return the best possible Allocation from this point.
     */
    private static Allocation allocateRelief(double budget, List<Region> sites, int index,
            Allocation current) {
        if (index == sites.size()) {
            return current;
        }

        Region region = sites.get(index);

        Allocation without = allocateRelief(budget, sites, index + 1, current);
        Allocation with = without;
        if (current.totalCost() + region.getCost() <= budget) {
            with = allocateRelief(budget, sites, index + 1, current.withRegion(region));
        }

        int withPop = with.totalPeople();
        int withoutPop = without.totalPeople();

        if (withPop != withoutPop) {
            if (withPop > withoutPop) {
                return with;
            } else {
                return without;
            }
        }

        if (with.totalCost() < without.totalCost()) {
            return with;
        } else {
            return without;
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // PROVIDED HELPER METHODS - **DO NOT MODIFY ANYTHING BELOW THIS LINE!** //
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Prints each allocation in the provided set. Useful for getting a quick
     * overview of all allocations currently in the system.
     *
     * @param allocations
     *            Set of allocations to print
     */
    public static void printAllocations(Set<Allocation> allocations) {
        System.out.println("All Allocations:");
        for (Allocation a : allocations) {
            System.out.println("  " + a);
        }
    }

    /**
     * Prints details about a specific allocation result, including the total people
     * helped, total cost, and any leftover budget. Handy for checking if we're
     * within budget limits!
     *
     * @param alloc
     *            The allocation to print
     * @param budget
     *            The budget to compare against
     */
    public static void printResult(Allocation alloc, double budget) {
        System.out.println("Result: ");
        System.out.println("  " + alloc);
        System.out.println("  People helped: " + alloc.totalPeople());
        System.out.printf("  Cost: $%.2f\n", alloc.totalCost());
        System.out.printf("  Unused budget: $%.2f\n", (budget - alloc.totalCost()));
    }

    /**
     * Creates a scenario with numRegions regions by randomly choosing the
     * population and cost of each region.
     *
     * @param numRegions
     *            Number of regions to create
     * @param minPop
     *            Minimum population per region
     * @param maxPop
     *            Maximum population per region
     * @param minCostPer
     *            Minimum cost per person
     * @param maxCostPer
     *            Maximum cost per person
     * @return A list of randomly generated regions
     */
    public static List<Region> createRandomScenario(int numRegions, int minPop, int maxPop,
            double minCostPer, double maxCostPer) {
        List<Region> result = new ArrayList<>();

        for (int i = 0; i < numRegions; i++) {
            int pop = RAND.nextInt(maxPop - minPop + 1) + minPop;
            double cost = (RAND.nextDouble(maxCostPer - minCostPer) + minCostPer) * pop;
            result.add(new Region("Region #" + i, pop, round2(cost)));
        }

        return result;
    }

    /**
     * Manually creates a simple list of regions to represent a known scenario.
     *
     * @return A simple list of regions
     */
    public static List<Region> createSimpleScenario() {
        List<Region> result = new ArrayList<>();

        result.add(new Region("Region #1", 50, 500));
        result.add(new Region("Region #2", 100, 700));
        result.add(new Region("Region #3", 60, 1000));
        result.add(new Region("Region #4", 20, 1000));
        result.add(new Region("Region #5", 200, 900));

        return result;
    }

    /**
     * Rounds a number to two decimal places.
     *
     * @param num
     *            The number to round
     * @return The number rounded to two decimal places
     */
    private static double round2(double num) {
        return Math.round(num * 100) / 100.0;
    }
}
