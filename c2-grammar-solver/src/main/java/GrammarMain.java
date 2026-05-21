// Client program that prompts a user for the name of a
// grammar file and then gives the user the opportunity to generate random
// versions of various elements of the grammar.

import java.io.*; // for File, FileNotFoundException
import java.util.*; // for Scanner, List, Set, Collections

public class GrammarMain {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Welcome to the CSE 123 random sentence generator!");
        System.out.println();

        // open grammar file
        System.out.print("What is the name of the grammar file? ");
        Scanner console = new Scanner(System.in);
        String fileName = console.nextLine();
        List<String> lines = readLines(fileName);

        // construct grammar solver and begin user input loop
        GrammarSolver solver = new GrammarSolver(lines);

        // repeatedly prompt for symbols to generate, and generate them
        String symbol = getSymbol(console, solver);
        while (symbol.length() > 0) {
            if (solver.contains(symbol)) {
                int choice = getChoice(console);
                if (choice == 1) {
                    doGenerateBasic(console, solver, symbol);
                } else if (choice == 2) {
                    doGenerateComplex(console, solver, symbol);
                } else {
                    System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Illegal symbol.");
            }

            symbol = getSymbol(console, solver);
        }
    }

    // Displays all non-terminal symbols, prompts for a symbol to generate
    // and returns the symbol as a string.
    public static String getSymbol(Scanner console, GrammarSolver solver) {
        System.out.println();
        System.out.println("Available symbols to generate are:");
        Set<String> symbols = solver.getSymbols();
        System.out.println(symbols);

        System.out.print("What do you want to generate (Enter to quit)? ");
        String target = console.nextLine().trim();
        return target;
    }

    // Prompts user for 1 or 2 input to determine basic and complex respectively.
    // Returns the choice
    public static int getChoice(Scanner console) {
        int choice = 0;
        System.out.print("Enter 1 for basic or 2 for complex: ");
        if (console.hasNextInt()) {
            choice = console.nextInt();
        }

        console.nextLine(); // to position to next line

        return choice;
    }

    // Prompts user for a number of basic phrases to generate from the given symbol,
    // generates that many using the provided GrammarSolver and displays them to the
    // console.
    public static void doGenerateBasic(Scanner console, GrammarSolver solver, String symbol) {
        System.out.print("How many do you want me to generate? ");
        if (console.hasNextInt()) {
            int number = console.nextInt();
            if (number < 0) {
                System.out.println("No negatives allowed.");
            } else {
                String[] answers = solver.generateBasic(symbol, number);
                for (int i = 0; i < number; i++) {
                    System.out.println(answers[i]);
                }
            }
        } else {
            System.out.println("That is not a valid integer.");
        }
        console.nextLine(); // to position to next line
    }

    // Prompts user for a number of complex phrases to generate from the given
    // symbol,
    // generates that many using the provided GrammarSolver and displays them to the
    // console.
    public static void doGenerateComplex(Scanner console, GrammarSolver solver, String symbol) {
        System.out.print("How many do you want me to generate? ");
        if (console.hasNextInt()) {
            int number = console.nextInt();
            if (number < 0) {
                System.out.println("No negatives allowed.");
            } else {
                String[] answers = solver.generateComplex(symbol, number);
                for (int i = 0; i < number; i++) {
                    System.out.println(answers[i]);
                }
            }
        } else {
            System.out.println("That is not a valid integer.");
        }
        console.nextLine(); // to position to next line
    }

    // Reads text from the file with the given name and returns as a List.
    // Empty lines are omitted and and each string in the returned list has no
    // leading/trailing whitespace.
    // pre: a file with the given name exists, throws FileNotFoundException
    // otherwise
    public static List<String> readLines(String fileName) throws FileNotFoundException {
        List<String> lines = new ArrayList<String>();
        Scanner input = new Scanner(new File(fileName));
        while (input.hasNextLine()) {
            String line = input.nextLine().trim();
            if (!line.isEmpty()) {
                lines.add(line);
            }
        }
        return lines;
    }
}
