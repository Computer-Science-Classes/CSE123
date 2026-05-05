/**
 * Represents one player's pawn state in a game of Quoridor.
 *
 * <p>
 * A pawn stores the player number, its current board position, and the number
 * of walls that player has remaining.
 */
public final class QuoridorPawn {
    private int player;
    private int position;
    private int wallsRemaining;

    /**
     * Constructs a pawn for the given player.
     *
     * @param player
     *            the player number for this pawn.
     */
    public QuoridorPawn(int player) {
        this.player = player;
        wallsRemaining = 10;
    }

    /**
     * Returns the player number for this pawn.
     *
     * @return the player number
     */
    public int getPlayer() {
        return player;
    }

    /**
     * Updates this pawn's board position.
     *
     * @param position
     *            the new board index for this pawn
     */

    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Returns this pawn's current board position.
     *
     * @return the current board index
     */
    public int getPosition() {
        return position;
    }

    /**
     * Returns the number of walls this player has remaining.
     *
     * @return the number of remaining walls
     */
    public int getWallsRemaining() {
        return wallsRemaining;
    }

    /**
     * Removes one wall from this player's remaining wall count.
     */
    public void removeWall() {
        wallsRemaining--;
    }
}
