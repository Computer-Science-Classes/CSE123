import java.util.*;

public class Quoridor extends AbstractStrategyGame {
    private static final int SIZE = 9;
    private QuoridorBoard board;
    private int nextPlayer;

    public Quoridor() {
        board = new QuoridorBoard(SIZE);
        nextPlayer = 0;
    }

    public String instructions() {
        // Constructs and returns a String describing how to play the game. Should
        // include any relevant details on how to interpret the game state as
        // returned
        // by toString(), how to make moves, the game end condition, and how to win.
        return "";
    }

    public String toString() {
        // Constructs and returns a String representation of the current game state.
        // This representation should contain all information that should be known to
        // players at any point in the game, including board state (if any) and scores
        // (if any).

        board.printBoard();

        return "";
    }

    /**
     * Returns true if the game has ended, and false otherwise.
     */
    @Override
    public boolean isGameOver() {
        return getWinner() != -1;
    }

    /**
     * Returns the index of the player who has won the game, or -1 if the game is
     * not over.
     */
    @Override
    public int getWinner() {
        return -1;
    }

    /**
     * Returns the index of the player who will take the next turn. If the game is
     * over, returns -1.
     */
    @Override
    public int getNextPlayer() {
        if (nextPlayer == 0) {
            return 1;
        } else if (nextPlayer == 1) {
            return 0;
        }

        return -1;
    }

    /**
     * Takes input from the parameter to specify the move the player with the next
     * turn wishes to make. Returns a string representation of said move.
     */
    @Override
    public String getMove(Scanner input) {
        String out = "";
        if (input.next().equalsIgnoreCase("north") || input.next().equalsIgnoreCase("up")) {
            return out + "NORTH";
        } else if (input.next().equalsIgnoreCase("south")
                || input.next().equalsIgnoreCase("down")) {
            System.out.println("HIII");
            return out + "SOUTH";
        } else if (input.next().equalsIgnoreCase("west") || input.next().equalsIgnoreCase("left")) {
            return out + "WEST";
        } else if (input.next().equalsIgnoreCase("east")
                || input.next().equalsIgnoreCase("right")) {
            return out + "EAST";
        } else if (input.next().equalsIgnoreCase("wall")) {
            System.out.println("What index do you want the wall? (0, 1, 2, ...)");
            String wallPosition = input.next();

            System.out.println("What position do you want the wall? (North, South, West, East)");
            String wallDirection = input.next();

            return out + "wall" + "/" + wallPosition + "/" + wallDirection;
        } else {
            System.out.println("Unknown Move");
        }

        return out;
    }

    /**
     * Takes in a string representation of the move the next player wishes (the
     * output from getMove() ) and executes the move. If any part of the move is
     * illegal, throw an IllegalArgumentException.
     */
    public void makeMove(String input) {
        System.out.println(input);
        if (input.equals("NORTH")) {
            board.movePawn(nextPlayer, QuoridorBoard.Direction.NORTH);
        } else if (input.equals("SOUTH")) {
            board.movePawn(nextPlayer, QuoridorBoard.Direction.SOUTH);
        } else if (input.equals("WEST")) {
            board.movePawn(nextPlayer, QuoridorBoard.Direction.WEST);
        } else if (input.equals("EAST")) {
            board.movePawn(nextPlayer, QuoridorBoard.Direction.EAST);
        } else if (input.contains("wall")) {
            String[] parts = input.split("/");

            board.buildWall(Integer.parseInt(parts[1]),
                    QuoridorBoard.Direction.stringToDirection(parts[2]));
        }
    }
}
