import java.util.*;

/**
 * Represents a two-player game of Quoridor.
 *
 * <p>
 * The game is played on a square grid (default 9x9). Player 1 begins at the top
 * center of the board and attempts to reach the top row. Players alternate
 * turns, and on each turn may either move their pawn or place a wall.
 */
public class Quoridor extends AbstractStrategyGame {
    private static final int SIZE = 9;

    private QuoridorBoard board;
    private QuoridorPawn player0;
    private QuoridorPawn player1;
    private int nextPlayer;
    private boolean extension;

    /**
     * Represents the movement command requested by a player.
     *
     * <p>
     * The cardinal moves are always available when not blocked by walls. The
     * diagonal moves are extension moves and are only legal when the opposing pawn
     * is adjacent and a wall blocks a straight jump.
     */
    private enum Move {
        NORTH, SOUTH, WEST, EAST, NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
    }

    /**
     * Constructs a new Quoridor game.
     *
     * @param extension
     *            true if the jump and diagonal movement extension is enabled,
     *            otherwise false.
     */
    public Quoridor(boolean extension) {
        board = new QuoridorBoard(SIZE);

        player0 = new QuoridorPawn(0);
        player1 = new QuoridorPawn(1);

        player0.setPosition(board.getStartingPosition(0));
        player1.setPosition(board.getStartingPosition(1));

        nextPlayer = 0;
        this.extension = extension;
    }

    /**
     * Returns instructions explaining how to play Quoridor.
     *
     * @return a String containing the game instructions.
     */
    public String instructions() {
        String out = "";

        out += "Quoridor is a two-player strategy game.\n";
        out += "Player 1 starts at the top of the board and tries to reach the bottom row.\n";
        out += "Player 2 starts at the bottom of the board and tries to reach the top row.\n";
        out += "\n";
        out += "On one's turn, one may either move one's pawn or place a wall.\n";
        out += "To move, type one of: north, south, west, east.\n";
        out += "One may also use: up, down, left, right.\n";
        out += "To place a wall, type: wall.\n";
        out += "Then enter the wall's board index and direction.\n";
        out += "\n";
        out += "A wall blocks movement between squares.\n";
        out += "Each player starts with 10 walls.\n";
        out += "The first player to reach the opposite side wins.";

        return out;
    }

    /**
     * Returns a String representation of the current game state.
     *
     * @return a String containing the board, turn, and remaining wall counts.
     */
    public String toString() {
        String out = "";

        out += board.printBoard(player0.getPosition(), player1.getPosition());
        out += "\n";
        out += "Player 1 walls remaining: " + player0.getWallsRemaining() + "\n";
        out += "Player 2 walls remaining: " + player1.getWallsRemaining() + "\n";

        if (isGameOver()) {
            out += "Game over. Player " + (getWinner() + 1) + " wins!" + "\n";
        } else {
            out += "Next player: Player " + (nextPlayer + 1) + "\n";
        }

        return out;
    }

    /**
     * Returns true if the game has ended, and false otherwise.
     *
     * @return true if player has won, otherwise false.
     */
    @Override
    public boolean isGameOver() {
        return getWinner() != -1;
    }

    /**
     * Returns the index of the player who has won the game, or -1 if the game is
     * not over.
     *
     * @return 0 if player 0 has won, 1 if player 1 has won, or -1 if neither has
     *         won.
     */
    @Override
    public int getWinner() {
        int player0Row = player0.getPosition() / SIZE;
        int player1Row = player1.getPosition() / SIZE;

        if (player0Row == SIZE - 1) {
            return 0;
        } else if (player1Row == 0) {
            return 1;
        }

        return -1;
    }

    /**
     * Returns the index of the player who will take the next turn. If the game is
     * over, returns -1.
     *
     * @return the next player, or -1 if the game is over.
     */
    @Override
    public int getNextPlayer() {
        if (isGameOver()) {
            return -1;
        }

        return nextPlayer;
    }

    /**
     * Takes input from the parameter to specify the move the player with the next
     * turn wishes to make.
     *
     * @param input
     *            the Scanner used to read user input.
     * @return a String representation of the requested move.
     */
    @Override
    public String getMove(Scanner input) {
        String move = input.next();

        if (move.equalsIgnoreCase("north") || move.equalsIgnoreCase("up")) {
            return "NORTH";
        } else if (move.equalsIgnoreCase("south") || move.equalsIgnoreCase("down")) {
            return "SOUTH";
        } else if (move.equalsIgnoreCase("west") || move.equalsIgnoreCase("left")) {
            return "WEST";
        } else if (move.equalsIgnoreCase("east") || move.equalsIgnoreCase("right")) {
            return "EAST";
        } else if (move.equalsIgnoreCase("northeast") || move.equalsIgnoreCase("ne")) {
            return "NORTHEAST";
        } else if (move.equalsIgnoreCase("northwest") || move.equalsIgnoreCase("nw")) {
            return "NORTHWEST";
        } else if (move.equalsIgnoreCase("southeast") || move.equalsIgnoreCase("se")) {
            return "SOUTHEAST";
        } else if (move.equalsIgnoreCase("southwest") || move.equalsIgnoreCase("sw")) {
            return "SOUTHWEST";
        } else if (move.equalsIgnoreCase("wall")) {
            System.out.println("What index do you want the wall? (1, 2, 3, ...)");
            String wallPosition = input.next();

            System.out.println("What position do you want the wall? (North, South, West, East)");
            String wallDirection = input.next();

            return "WALL/" + wallPosition + "/" + wallDirection;
        }

        throw new IllegalArgumentException("Unknown move");
    }

    /**
     * Moves the current player's pawn in the given direction.
     *
     * @param input
     *            the move String returned from getMove.
     * @throws IllegalStateException
     *             if the game is over a move is trying to be made.
     * @throws IllegalArgumentException
     *             if the move is illegal.
     */
    @Override
    public void makeMove(String input) {
        if (isGameOver()) {
            throw new IllegalStateException("Cannot make move because the game is over");
        }

        if (input.equals("NORTH")) {
            movePawn(Move.NORTH);
        } else if (input.equals("SOUTH")) {
            movePawn(Move.SOUTH);
        } else if (input.equals("WEST")) {
            movePawn(Move.WEST);
        } else if (input.equals("EAST")) {
            movePawn(Move.EAST);
        } else if (input.equals("NORTHEAST")) {
            movePawn(Move.NORTHEAST);
        } else if (input.equals("NORTHWEST")) {
            movePawn(Move.NORTHWEST);
        } else if (input.equals("SOUTHEAST")) {
            movePawn(Move.SOUTHEAST);
        } else if (input.equals("SOUTHWEST")) {
            movePawn(Move.SOUTHWEST);
        } else if (input.startsWith("WALL/")) {
            placeWall(input);
        } else {
            throw new IllegalArgumentException("Unknown move");
        }

        nextPlayer = 1 - nextPlayer;
    }

    /**
     * Moves the current player's pawn according to the given more.
     *
     * @param move
     *            the move requested by the current player.
     * @throws IllegalArgumentException
     *             if the move is blocked or illegal.
     */
    private void movePawn(Move move) {
        if (move == Move.NORTH) {
            moveCardinal(QuoridorBoard.Direction.NORTH);
        } else if (move == Move.SOUTH) {
            moveCardinal(QuoridorBoard.Direction.SOUTH);
        } else if (move == Move.WEST) {
            moveCardinal(QuoridorBoard.Direction.WEST);
        } else if (move == Move.EAST) {
            moveCardinal(QuoridorBoard.Direction.EAST);
        } else {
            moveDiagonal(move);
        }
    }

    /**
     * Moves the current player's pawn in the cardinal direction.
     *
     * <p>
     * If the extension is enabled and opposing pawn is directly adjacent in this
     * direction, this method attempts to jump over the opposing pawn. If a wall
     * blocks the straight jump, the player must request a diagonal move instead.
     *
     * @param dir
     *            the cardinal direction to move.
     * @throws IllegalArgumentException
     *             if the move is blocked, occupied, or requires a diagonal move
     *             instead.
     */
    private void moveCardinal(QuoridorBoard.Direction dir) {
        QuoridorPawn pawn = getCurrentPawn();
        QuoridorPawn other = getOtherPawn();

        int current = pawn.getPosition();
        int adjacent = board.getNeighbor(current, dir);

        if (adjacent == -1) {
            throw new IllegalArgumentException("Move is blocked");
        }

        if (adjacent != other.getPosition()) {
            pawn.setPosition(adjacent);
            return;
        }

        if (!extension) {
            throw new IllegalArgumentException("Cannot move onto the other pawn");
        }

        int jump = board.getNeighbor(other.getPosition(), dir);
        if (jump == -1) {
            throw new IllegalArgumentException(
                    "Cannot jump because a wall blocks the pawn. Move diagonally instead.");
        }

        pawn.setPosition(jump);
    }

    /**
     * Attempts to move the current player's pawn diagonally.
     *
     * <p>
     * A diagonal move is only legal when the extension is enabled, the opposing
     * pawn is directly adjacent, and a wall blocks the jump over the opposing pawn.
     *
     * @param move
     *            the requested diagonal move.
     * @throws IllegalArgumentException
     *             if the diagonal movement is disabled or the requested diagonal
     *             move is illegal.
     */
    private void moveDiagonal(Move move) {
        if (!extension) {
            throw new IllegalArgumentException("Diagonal movement is not enabled");
        }

        if (tryDiagonal(move, QuoridorBoard.Direction.NORTH, QuoridorBoard.Direction.EAST)) {
            return;
        } else if (tryDiagonal(move, QuoridorBoard.Direction.NORTH, QuoridorBoard.Direction.WEST)) {
            return;
        } else if (tryDiagonal(move, QuoridorBoard.Direction.SOUTH, QuoridorBoard.Direction.EAST)) {
            return;
        } else if (tryDiagonal(move, QuoridorBoard.Direction.SOUTH, QuoridorBoard.Direction.WEST)) {
            return;
        } else if (tryDiagonal(move, QuoridorBoard.Direction.EAST, QuoridorBoard.Direction.NORTH)) {
            return;
        } else if (tryDiagonal(move, QuoridorBoard.Direction.EAST, QuoridorBoard.Direction.SOUTH)) {
            return;
        } else if (tryDiagonal(move, QuoridorBoard.Direction.WEST, QuoridorBoard.Direction.NORTH)) {
            return;
        } else if (tryDiagonal(move, QuoridorBoard.Direction.WEST, QuoridorBoard.Direction.SOUTH)) {
            return;
        }

        throw new IllegalArgumentException("Diagonal move is not legal");
    }

    /**
     * Attempts one possible diagonal movement path around the opposing pawn.
     *
     * @param move
     *            the diagonal move requested by the player.
     * @param towardOther
     *            the direction from the current pawn toward the opposing pawn.
     * @param aroundOther
     *            the direction around the opposing pawn.
     * @return true if this helper completed the diagonal move, otherwise false.
     */
    private boolean tryDiagonal(Move move, QuoridorBoard.Direction towardOther,
            QuoridorBoard.Direction aroundOther) {
        QuoridorPawn pawn = getCurrentPawn();
        QuoridorPawn other = getOtherPawn();

        int current = pawn.getPosition();
        int adjacent = board.getNeighbor(current, towardOther);

        if (adjacent != other.getPosition()) {
            return false;
        }

        int straightJump = board.getNeighbor(other.getPosition(), towardOther);

        if (straightJump != -1) {
            return false;
        }

        int diagonalDestination = board.getNeighbor(other.getPosition(), aroundOther);

        if (diagonalDestination == -1) {
            return false;
        }

        if (!matchesDiagonalMove(current, diagonalDestination, move)) {
            return false;
        }

        pawn.setPosition(diagonalDestination);
        return true;
    }

    /**
     * Returns whether a destination square matches the requested diagonal move.
     *
     * @param current
     *            the current pawn position.
     * @param destination
     *            the proposed destination position.
     * @param move
     *            the requested diagonal move.
     * @return true if the destination is one diagonal step from the current
     *         position in the requested direction, otherwise false.
     */
    private boolean matchesDiagonalMove(int current, int destination, Move move) {
        int currentRow = current / SIZE;
        int currentCol = current % SIZE;
        int destinationRow = destination / SIZE;
        int destinationCol = destination % SIZE;

        int rowChange = destinationRow - currentRow;
        int colChange = destinationCol - currentCol;

        if (move == Move.NORTHEAST) {
            return rowChange == -1 && colChange == 1;
        } else if (move == Move.NORTHWEST) {
            return rowChange == -1 && colChange == -1;
        } else if (move == Move.SOUTHEAST) {
            return rowChange == 1 && colChange == 1;
        } else if (move == Move.SOUTHWEST) {
            return rowChange == 1 && colChange == -1;
        }

        return false;
    }

    /**
     * Places a wall for the current player.
     *
     * @param input
     *            the wall command.
     * @throws IllegalArgumentException
     *             if the wall command or placement is invalid.
     */
    private void placeWall(String input) {
        String[] parts = input.split("/");

        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid wall command");
        }

        QuoridorPawn pawn = getCurrentPawn();

        if (pawn.getWallsRemaining() <= 0) {
            throw new IllegalArgumentException("Player has no walls remaining");
        }

        int wallPosition = Integer.parseInt(parts[1]) - 1;
        QuoridorBoard.Direction wallDirection = QuoridorBoard.Direction.stringToDirection(parts[2]);

        if (wallPosition < 0 || wallPosition >= board.getBoardSize()) {
            throw new IllegalArgumentException("Wall index out of bounds");
        }

        board.buildWall(wallPosition, wallDirection);
        pawn.removeWall();
    }

    /**
     * Returns the pawn belonging to the player whose turn it is.
     *
     * @return the current player's pawn.
     */
    private QuoridorPawn getCurrentPawn() {
        if (nextPlayer == 0) {
            return player0;
        } else {
            return player1;
        }
    }

    /**
     * Returns the pawn belonging to the player whose turn it is not.
     *
     * @return the opposing player's pawn.
     */
    private QuoridorPawn getOtherPawn() {
        if (nextPlayer == 0) {
            return player1;
        } else {
            return player0;
        }
    }
}
