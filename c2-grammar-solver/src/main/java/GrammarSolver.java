import java.util.*;

/**
 * Generates random {@code String}s from a grammar written in Backus-Naur Form
 * (BNF).
 */
public class GrammarSolver {
    private static final String DEFINITION_SEPARATOR = "::=";
    private static final String RULE_SEPARATOR = "\\|";
    private static final String TOKEN_SEPARATOR = "\\s+";
    private Map<String, List<String>> grammarMap;
    private Map<String, List<Integer>> weightMap;
    private Random rand;

    /**
     * Constructs a new GrammarSolver using the provided grammar rules.
     *
     * <p>
     * Each {@code String} in the {@code List} must be formatted as:
     * non-terminal::=rule|rule|...|rule
     *
     * @param grammar
     *            the list of grammar rules in BNF format.
     * @throws IllegalArgumentException
     *             if grammar is null, empty, or contains duplicate non-terminals.
     */
    public GrammarSolver(List<String> grammar) {
        if (grammar == null || grammar.isEmpty()) {
            throw new IllegalArgumentException();
        }

        this.grammarMap = new HashMap<>();
        this.weightMap = new HashMap<>();

        for (String cur : grammar) {
            String[] split = cur.split(DEFINITION_SEPARATOR);
            String lhs = split[0].trim();
            String rhs = split[1].trim();

            if (grammarMap.containsKey(lhs)) {
                throw new IllegalArgumentException();
            }

            List<String> rules = new ArrayList<>(Arrays.asList(rhs.split(RULE_SEPARATOR)));
            List<Integer> weights = new ArrayList<>();

            for (String rule : rhs.split(RULE_SEPARATOR)) {
                rule = rule.trim();

                if (rule.endsWith("]")) {
                    int openBracket = rule.lastIndexOf('[');
                    int closeBracket = rule.lastIndexOf(']');

                    int weight = Integer.parseInt(rule.substring(openBracket + 1, closeBracket));
                    String cleanRule = rule.substring(0, openBracket).trim();

                    rules.add(cleanRule);
                    weights.add(weight);
                } else {
                    rules.add(rule);
                    weights.add(1);
                }
            }

            grammarMap.put(lhs, rules);
            weightMap.put(lhs, weights);
        }

        this.rand = new Random();
    }

    /**
     * Returns whether the given {@code symbol} is a non-terminal in the grammar.
     *
     * @param symbol
     *            the symbol to check.
     * @return true if the symbol exists as a non-terminal in the grammar, false
     *         otherwise.
     * @throws IllegalArgumentException
     *             if {@code symbol} is null.
     */
    public boolean contains(String symbol) {
        if (symbol == null) {
            throw new IllegalArgumentException();
        }

        return grammarMap.containsKey(symbol);
    }

    /**
     * Randomly generates the specified number of {@code Strings} for the given
     * non-terminal {@code symbol} using equal probability for each rule option.
     *
     * @param symbol
     *            the non-terminal symbol to generate from.
     * @param times
     *            the number of {@code String}s to generate.
     * @return an array containing the generated {@code String}s.
     * @throws IllegalArgumentException
     *             if {@code symbol} is null, {@code symbol} is not a non-terminal,
     *             or {@code times} is negative.
     */
    public String[] generateBasic(String symbol, int times) {
        if (symbol == null || times < 0 || !contains(symbol)) {
            throw new IllegalArgumentException();
        }

        String[] out = new String[times];

        for (int i = 0; i < times; i++) {
            out[i] = generateBasic(symbol);
        }

        return out;
    }

    /**
     * Recursively generates a single random expansion of the given grammar symbol.
     *
     * @param symbol
     *            the symbol to recursively expand.
     * @return a generated terminal {@code String} corresponding to the symbol.
     */
    private String generateBasic(String symbol) {
        if (!contains(symbol)) {
            return symbol;
        }

        List<String> rules = grammarMap.get(symbol);
        String rule = rules.get(rand.nextInt(rules.size()));

        String out = "";
        String[] tokens = rule.split(TOKEN_SEPARATOR);

        for (String token : tokens) {
            out += generateBasic(token) + " ";
        }

        return out.trim();
    }

    /**
     * Randomly generates the specified number of {@code String}s for the given
     * non-terminal {@code symbol} using weighted probabilities for each rule
     * option.
     *
     * @param symbol
     *            the non-terminal symbol to generate from.
     * @param times
     *            the number of {@code String}s to generate.
     * @return an array containing the generated {@code String}s.
     * @throws IllegalArgumentException
     *             if {@code symbol} is null, {@code symbol} is not a non-terminal,
     *             or {@code times} is negative.
     */
    public String[] generateComplex(String symbol, int times) {
        if (symbol == null || times < 0 || !contains(symbol)) {
            throw new IllegalArgumentException();
        }

        String[] out = new String[times];

        Random rand = new Random();
        for (int i = 0; i < times; i++) {
            out[i] = generateComplex(symbol, rand);
        }

        return out;
    }

    /**
     * Recursively generates a single random expansion of the given grammar symbol,
     * selecting rule options according to their weights.
     *
     * @param symbol
     *            the symbol to recursively expand.
     * @param rand
     *            used to randomly generate a single expansion.
     * @return a generated terminal {@code String} corresponding to the symbol.
     */
    private String generateComplex(String symbol, Random rand) {
        if (!contains(symbol)) {
            return symbol;
        }

        List<String> rules = grammarMap.get(symbol);
        List<Integer> weights = weightMap.get(symbol);

        int totalWeight = 0;
        for (int weight : weights) {
            totalWeight += weight;
        }

        int choice = rand.nextInt(totalWeight);
        int runningTotal = 0;
        int ruleIndex = 0;

        for (int i = 0; i < weights.size(); i++) {
            runningTotal += weights.get(i);
            if (choice < runningTotal) {
                ruleIndex = i;
                i = weights.size();
            }
        }

        String rule = rules.get(ruleIndex);

        String out = "";
        String[] tokens = rule.split(TOKEN_SEPARATOR);

        for (String token : tokens) {
            out += generateComplex(token, rand) + " ";
        }

        return out.trim();
    }

    /**
     * Returns all non-terminal symbols in the grammar as a sorted set.
     *
     * @return a sorted set containing all non-terminal symbols.
     */
    public Set<String> getSymbols() {
        return new TreeSet<String>(grammarMap.keySet());
    }
}
