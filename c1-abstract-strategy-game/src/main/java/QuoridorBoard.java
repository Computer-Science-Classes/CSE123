public class QuoridorBoard {
    private final int size;
    private final int boardsize;
    // Neighbors are positioned [North, South, West, East]
    private int[][] neighbors;
    private QuoridorPawn player0;
    private QuoridorPawn player1;

    public enum Direction {
        NORTH(0), SOUTH(1), WEST(2), EAST(3);

        private final int index;

        Direction(int index) {
            this.index = index;
        }

        public int index() {
            return index;
        }

        public static Direction stringToDirection(String dir) {
            return Direction.valueOf(dir.toUpperCase());
        }
    }

    public QuoridorBoard(int size) {
        this.size = size;
        boardsize = size * size;
        neighbors = new int[boardsize][Direction.values().length];
        player0 = new QuoridorPawn(0);
        player1 = new QuoridorPawn(1);

        initBoard();
    }

    public int[] getNeighbors(int index) {
        int[] neighbors = new int[4];

        neighbors[0] = this.neighbors[index][0];
        neighbors[1] = this.neighbors[index][1];
        neighbors[2] = this.neighbors[index][2];
        neighbors[3] = this.neighbors[index][3];

        return neighbors;
    }

    public int getNorthNeighbor(int index) {
        return neighbors[index][0];
    }

    public int getSouthNeighbor(int index) {
        return neighbors[index][1];
    }

    public int getWestNeighbor(int index) {
        return neighbors[index][2];
    }

    public int getEastNeighbor(int index) {
        return neighbors[index][3];
    }

    public void movePawn(int player, Direction dir) {
        QuoridorPawn pawn = null;

        if (player == 0) {
            pawn = player0;
        } else if (player == 1) {
            pawn = player1;
        } else {
            throw new IllegalArgumentException("Player must either 0 or 1");
        }

        int move = neighbors[pawn.getPosition()][dir.index()];
        if (getNeighbors(move) == null) {
            throw new IllegalArgumentException("Inputted direction leads to an invalid movement");
        } else {
            pawn.setPosition(move);
        }
    }

    public void buildWall(int index, Direction dir) {
        if (dir == Direction.NORTH) {
            index = index - size;
            dir = Direction.SOUTH;
        } else if (dir == Direction.WEST) {
            index = index - 1;
            dir = Direction.EAST;
        }

        neighbors[index][dir.index()] = -1;
    }

    public void printBoard() {
        System.out.print("     0   ");
        for (int i = 1; i < size - 1; i++) {
            System.out.print(i + "   ");
        }
        System.out.println(size - 1);

        // First line
        System.out.print("   +");
        for (int i = 0; i < size; i++) {
            System.out.print("---+");
        }
        System.out.println();

        for (int row = 0; row < size; row++) {
            System.out.printf("%2d |", row);
            for (int col = 0; col < size; col++) {

                if (index(row, col) == player0.getPosition()) {
                    System.out.print(" 1");
                    System.out.print(" ");
                } else if (index(row, col) == player1.getPosition()) {
                    System.out.print(" 2");
                    System.out.print(" ");
                } else {
                    System.out.print("   ");
                }

                if (col < size - 1 && hasEastEdge(row, col)) {
                    System.out.print(" ");
                } else {
                    System.out.print("|");
                }
            }
            System.out.println();

            System.out.print("   +");
            for (int col = 0; col < size; col++) {
                if (row < size - 1 && hasSouthEdge(row, col)) {
                    System.out.print("   +");
                } else {
                    System.out.print("---+");
                }
            }
            System.out.println();
        }
    }

    private final void initBoard() {
        for (int i = 0; i < boardsize; i++) {
            int row = i / size;
            int col = i % size;

            neighbors[i][Direction.NORTH.index()] = (row == 0) ? -1 : i - size;
            neighbors[i][Direction.SOUTH.index()] = (row == size - 1) ? -1 : i + size;
            neighbors[i][Direction.WEST.index()] = (col == 0) ? -1 : i - 1;
            neighbors[i][Direction.EAST.index()] = (col == size - 1) ? -1 : i + 1;
        }

        int topStartingPosition = (size - 1) / 2;

        player0.setPosition(topStartingPosition);
        player1.setPosition((boardsize - 1) - topStartingPosition);
    }

    private int index(int row, int col) {
        return row * size + col;
    }

    private boolean hasEastEdge(int row, int col) {
        return neighbors[index(row, col)][Direction.EAST.index()] != -1;
    }

    private boolean hasSouthEdge(int row, int col) {
        return neighbors[index(row, col)][Direction.SOUTH.index()] != -1;
    }
}
