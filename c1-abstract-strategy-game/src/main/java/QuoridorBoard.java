/**
 * Represents the board topology for a game of Quoridor.
 */
public class QuoridorBoard {
    private final int size;
    private final int boardSize;

    // Neighbors are positioned [North, South, West, East]
    private int[][] neighbors;

    /**
     * Represents the four cardinal directions used by the board graph.
     */
    public enum Direction {
        NORTH(0), SOUTH(1), WEST(2), EAST(3);

        private final int index;

        /**
         * Constructs a direction with its neighbor array index.
         *
         * @param index
         *            the index used for this direction in the neighbors array.
         */
        Direction(int index) {
            this.index = index;
        }

        /**
         * Return this direction's index in the neighbors array.
         *
         * @return the array index for this direction.
         */
        public int index() {
            return index;
        }

        /**
         * Converts a string into a Direction.
         *
         * @param dir
         *            the direction name.
         * @return the matching Direction.
         */
        public static Direction stringToDirection(String dir) {
            return Direction.valueOf(dir.toUpperCase());
        }

        /**
         * Returns the opposite cardinal direction.
         *
         * @return the opposite direction.
         */
        public Direction opposite() {
            if (this == NORTH) {
                return SOUTH;
            } else if (this == SOUTH) {
                return NORTH;
            } else if (this == WEST) {
                return EAST;
            } else {
                return WEST;
            }
        }
    }

    /**
     * Constructs a Quoridor board with the given side length.
     *
     * @param size
     *            the number of rows and columns on the board.
     * @throws IllegalArgumentException
     *             if size is less than 2 or even.
     */
    public QuoridorBoard(int size) {
        if (size <= 1 || size % 2 == 0) {
            throw new IllegalArgumentException();
        }

        this.size = size;
        boardSize = size * size;
        neighbors = new int[boardSize][Direction.values().length];

        initBoard();
    }

    /**
     * Returns the side length of the board.
     *
     * @return the number of rows and columns.
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the total number of squares on the board.
     *
     * @return size multiplied by size;
     */
    public int getBoardSize() {
        return boardSize;
    }

    /**
     * Returns the starting square index for the given player.
     *
     * <p>
     * Player 0 starts in the top middle square. Player 1 starts in the bottom
     * middle square.
     *
     * @param player
     *            the player number, either 0 or 1.
     * @return the starting board index for that player.
     * @throws IllegalArgumentException
     *             if player is not 0 or 1.
     */
    public int getStartingPosition(int player) {
        int middle = (size - 1) / 2;

        if (player == 0) {
            return middle;
        } else if (player == 1) {
            return boardSize - 1 - middle;
        } else {
            throw new IllegalArgumentException("Player must be either 0 or 1");
        }
    }

    /**
     * Returns a copy of the neighbor array for a square.
     *
     * <p>
     * The returned array is ordered as north, south, west, east. A value of -1
     * means there is no available edge in that direction.
     *
     * @param index
     *            the board square index.
     * @return a copy of the square's neighbor indices.
     */
    public int[] getNeighbors(int index) {
        validateIndex(index);

        int[] copy = new int[Direction.values().length];
        for (int i = 0; i < Direction.values().length; i++) {
            copy[i] = neighbors[index][i];
        }

        return copy;
    }

    /**
     * Returns the neighboring square in the given direction.
     *
     * @param index
     *            the board square index.
     * @param dir
     *            the direction to check.
     * @return the neighboring square index, or -1 if no edge exists.
     */
    public int getNeighbor(int index, Direction dir) {
        validateIndex(index);
        return neighbors[index][dir.index()];
    }

    /**
     * Places a Quoridor wall.
     *
     * <p>
     * A horizontal wall removes two vertical movement edges. A vertical wall
     * removes two horizontal movement edges.
     *
     * <p>
     * A south or north wall is treated as a horizontal wall. An east or west wall
     * is treated as a vertical wall. North and west placements are normalized to
     * equivalent south or east wall anchors.
     *
     * @param index
     *            the square index used as the wall anchor.
     * @param dir
     *            the side of the anchor square where the wall is placed.
     */
    public void buildWall(int index, Direction dir) {
        validateIndex(index);

        int row = index / size;
        int col = index % size;

        if (dir == Direction.NORTH) {
            row--;
            dir = Direction.SOUTH;
        } else if (dir == Direction.WEST) {
            col--;
            dir = Direction.EAST;
        }

        if (dir == Direction.SOUTH) {
            buildHorizontalWall(row, col);
        } else if (dir == Direction.EAST) {
            buildVerticalWall(row, col);
        }
    }

    /**
     * Returns a string representation of the board with two pawn positions shown.
     *
     * <p>
     * Player 0 s displayed as 1 and player 1 is displayed as 2.
     *
     * @param player0Position
     *            the board index of player 0's pawn.
     * @param player1Position
     *            the board index of player 1's pawn.
     * @return a printable board representation.
     */
    public String printBoard(int player0Position, int player1Position) {
        validateIndex(player0Position);
        validateIndex(player1Position);

        String out = "";
        int width = digitWidth();

        out += " ".repeat(width + 3) + "1";
        for (int i = 2; i <= size; i++) {
            out += "   " + i;
        }
        out += "\n";

        // First line
        out += " ".repeat(width + 1) + "+";
        for (int i = 0; i < size; i++) {
            out += "---+";
        }
        out += "\n";

        for (int row = 0; row < size; row++) {
            String rowStr = String.valueOf(row + 1);
            while (rowStr.length() < width) {
                rowStr = " " + rowStr;
            }
            out += rowStr + " |";

            for (int col = 0; col < size; col++) {
                int current = index(row, col);

                if (current == player0Position) {
                    out += " 1 ";
                } else if (current == player1Position) {
                    out += " 2 ";
                } else {
                    out += "   ";
                }

                if (col < size - 1 && hasEastEdge(row, col)) {
                    out += " ";
                } else {
                    out += "|";
                }
            }
            out += "\n";

            out += " ".repeat(width + 1) + "+";
            for (int col = 0; col < size; col++) {
                if (row < size - 1 && hasSouthEdge(row, col)) {
                    out += ("   +");
                } else {
                    out += ("---+");
                }
            }
            out += "\n";
        }

        return out;
    }

    /**
     * Returns the number of digits needed to display the largest row or column
     * number on this board.
     *
     * @return the display width for board labels.
     */
    private int digitWidth() {
        int max = size;
        int width = 0;

        while (max > 0) {
            width++;
            max /= 10;
        }

        return (width == 0) ? 1 : width;
    }

    /**
     * Places a horizontal wall anchored at the given row and column.
     *
     * <p>
     * The wall blocks movement south from two horizontal adjacent squares.
     *
     * @param row
     *            the anchor row.
     * @param col
     *            the anchor column.
     * @throws IllegalArgumentException
     *             if the wall cannot be placed.
     */
    private void buildHorizontalWall(int row, int col) {
        validateWallAnchor(row, col);

        int leftTop = index(row, col);
        int rightTop = index(row, col + 1);

        if (neighbors[leftTop][Direction.SOUTH.index()] == -1
                || neighbors[rightTop][Direction.SOUTH.index()] == -1) {
            throw new IllegalArgumentException("Horizontal wall cannot be placed there");
        }

        removeEdge(leftTop, Direction.SOUTH);
        removeEdge(rightTop, Direction.SOUTH);
    }

    /**
     * Places a vertical wall anchored at the given row and column.
     *
     * <p>
     * The wall blocks movement east from two vertically adjacent squares.
     *
     * @param row
     *            the anchor row.
     * @param col
     *            the anchor column.
     * @throws IllegalArgumentException
     *             if the wall cannot be placed.
     */
    private void buildVerticalWall(int row, int col) {
        validateWallAnchor(row, col);

        int leftTop = index(row, col);
        int leftBottom = index(row + 1, col);

        if (neighbors[leftTop][Direction.EAST.index()] == -1
                || neighbors[leftBottom][Direction.EAST.index()] == -1) {
            throw new IllegalArgumentException("Vertical wall cannot be placed there");
        }

        removeEdge(leftTop, Direction.EAST);
        removeEdge(leftBottom, Direction.EAST);
    }

    /**
     * Removes one undirected edge from the board graph.
     *
     * @param index
     *            index one endpoint of the edge.
     * @param dir
     *            the direction from index to the other endpoint.
     * @throws IllegalArgumentException
     *             if no edge exists in that direction.
     */
    private void removeEdge(int index, Direction dir) {
        int neighbor = neighbors[index][dir.index()];

        if (neighbor == -1) {
            throw new IllegalArgumentException("No edge exists there");
        }

        neighbors[index][dir.index()] = -1;
        neighbors[neighbor][dir.opposite().index()] = -1;
    }

    /**
     * Initializes the board graph with all normal grid adjacencies.
     */
    private final void initBoard() {
        for (int i = 0; i < boardSize; i++) {
            int row = i / size;
            int col = i % size;

            neighbors[i][Direction.NORTH.index()] = (row == 0) ? -1 : i - size;
            neighbors[i][Direction.SOUTH.index()] = (row == size - 1) ? -1 : i + size;
            neighbors[i][Direction.WEST.index()] = (col == 0) ? -1 : i - 1;
            neighbors[i][Direction.EAST.index()] = (col == size - 1) ? -1 : i + 1;
        }
    }

    /**
     * Checks that an index is a valid square on the board.
     *
     * @param index
     *            the board index to check.
     * @throws IllegalArgumentException
     *             if index is outside the board.
     */
    private void validateIndex(int index) {
        if (index < 0 || index >= boardSize) {
            throw new IllegalArgumentException("Invalid board index: " + index);
        }
    }

    /**
     * Checks that a wall anchor can refer to a wall.
     *
     * @param row
     *            the anchor row.
     * @param col
     *            the anchor column.
     * @throws IllegalArgumentException
     *             if the anchor is outside the valid wall area.
     */
    private void validateWallAnchor(int row, int col) {
        if (row < 0 || row >= size - 1 || col < 0 || col >= size - 1) {
            throw new IllegalArgumentException("Invalid wall position");
        }
    }

    /**
     * Converts a row and column into a board index.
     *
     * @param row
     *            the row number.
     * @param col
     *            the column number.
     * @return the board index.
     */
    private int index(int row, int col) {
        return row * size + col;
    }

    /**
     * Returns whether a square has an available east edge.
     *
     * @param row
     *            the row number.
     * @param col
     *            the column number.
     * @return true if the movement east is available, otherwise false.
     */
    private boolean hasEastEdge(int row, int col) {
        return neighbors[index(row, col)][Direction.EAST.index()] != -1;
    }

    /**
     * Returns whether a square has an available south edge.
     *
     * @param row
     *            the row number.
     * @param col
     *            the column number.
     * @return true if the movement south is available, otherwise false.
     */
    private boolean hasSouthEdge(int row, int col) {
        return neighbors[index(row, col)][Direction.SOUTH.index()] != -1;
    }
}
