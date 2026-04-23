import java.util.*;

public class Client {
    public static void main(String[] args) {
        Scanner console = new Scanner(System.in);
        System.out.println("Do you want to play with the extension enabled?");
        System.out.println("\"y\" for yes. Otherwise no. ");
        System.out.print("Enter your choice: ");

        String input = console.nextLine();
        boolean extension = false;
        if (input.toLowerCase().startsWith("y")) {
            extension = true;
        }

        /* Make sure to pass the extension variable into your constructor! */
        AbstractStrategyGame game = new Quoridor();

        System.out.println(game.instructions());
        System.out.println();

        while (!game.isGameOver()) {
            System.out.println(game);
            System.out.printf("Player %d's turn.\n", game.getNextPlayer());
            try {
                game.makeMove(game.getMove(console));
            } catch (IllegalArgumentException ex) {
                System.out.println("**Illegal move: " + ex.getMessage());
            }
            /**
             * Note - the above structure is a try/catch, which is something we've included
             * to help deal with the IllegalArgumentExceptions in your abstract strategy
             * game! We want to remind you that try/catch is a forbidden feature in 123, so
             * you SHOULD NOT INCLUDE IT in any code you submit (other than this file)!
             * Please see the Code Quality Guide for more info on this.
             */
        }
        System.out.println(game);
        int winner = game.getWinner();
        if (winner > 0) {
            System.out.printf("Player %d wins!\n", winner);
        } else {
            System.out.println("It's a tie!");
        }
    }
}
