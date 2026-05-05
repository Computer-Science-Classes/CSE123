import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class Testing {
    @Test
    @DisplayName("STUDENT TEST CASE - Win Condition")
    public void firstCaseTest() {
        AbstractStrategyGame g = new Quoridor(false);

        assertEquals(0, g.getNextPlayer());
        assertFalse(g.isGameOver());
        assertEquals(-1, g.getWinner());

        g.makeMove("SOUTH");
        g.makeMove("EAST");
        g.makeMove("SOUTH");
        g.makeMove("NORTH");
        g.makeMove("SOUTH");
        g.makeMove("NORTH");
        g.makeMove("SOUTH");
        g.makeMove("NORTH");
        g.makeMove("SOUTH");
        g.makeMove("NORTH");
        g.makeMove("SOUTH");
        g.makeMove("NORTH");
        g.makeMove("SOUTH");
        g.makeMove("NORTH");
        g.makeMove("SOUTH");

        assertTrue(g.isGameOver());
        assertEquals(0, g.getWinner());
        assertEquals(-1, g.getNextPlayer());
    }

    @Test
    @DisplayName("STUDENT TEST CASE - Illegal Move")
    public void secondCaseTest() {
        AbstractStrategyGame g = new Quoridor(false);

        assertThrows(IllegalArgumentException.class, () -> {
            g.makeMove("NORTH");
        });

        assertFalse(g.isGameOver());
        assertEquals(-1, g.getWinner());
        assertEquals(0, g.getNextPlayer());
    }

    @Test
    void interiorCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;

        assertEquals(1, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(19, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(9, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(11, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void topLeftCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 0;

        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(9, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(1, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void topRightCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 8;

        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(17, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(7, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void bottomLeftCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 72;

        assertEquals(63, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(73, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void bottomRightCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 80;

        assertEquals(71, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(79, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void topRowNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 4;

        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(13, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(3, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(5, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void bottomRowNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 76;

        assertEquals(67, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(75, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(77, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void leftColumnNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 27;

        assertEquals(18, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(36, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(28, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void rightColumnNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 35;

        assertEquals(26, board.getNeighbor(index, QuoridorBoard.Direction.NORTH));
        assertEquals(44, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
        assertEquals(34, board.getNeighbor(index, QuoridorBoard.Direction.WEST));
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void buildWallSouthRemovesSouthNeighbor() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;

        board.buildWall(index, QuoridorBoard.Direction.SOUTH);
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.SOUTH));
    }

    @Test
    void buildWallEastRemovesSouthNeighbor() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;

        board.buildWall(index, QuoridorBoard.Direction.EAST);
        assertEquals(-1, board.getNeighbor(index, QuoridorBoard.Direction.EAST));
    }

    @Test
    void buildWallNorthCanonicalizesToAboveCellSouth() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;
        int above = 1;

        board.buildWall(index, QuoridorBoard.Direction.NORTH);
        assertEquals(-1, board.getNeighbor(above, QuoridorBoard.Direction.SOUTH));
    }

    @Test
    void buildWallWestCanonicalizesToLeftCellEast() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;
        int left = index - 1;

        board.buildWall(index, QuoridorBoard.Direction.WEST);
        assertEquals(-1, board.getNeighbor(left, QuoridorBoard.Direction.EAST));
    }

    @Test
    public void testHorizontalWallBlocksTwoSouthEdges() {
        QuoridorBoard board = new QuoridorBoard(9);

        board.buildWall(22, QuoridorBoard.Direction.SOUTH);

        assertEquals(-1, board.getNeighbor(22, QuoridorBoard.Direction.SOUTH));
        assertEquals(-1, board.getNeighbor(31, QuoridorBoard.Direction.NORTH));

        assertEquals(-1, board.getNeighbor(23, QuoridorBoard.Direction.SOUTH));
        assertEquals(-1, board.getNeighbor(32, QuoridorBoard.Direction.NORTH));
    }

    @Test
    public void testVerticalWallBlocksTwoSouthEdges() {
        QuoridorBoard board = new QuoridorBoard(9);

        board.buildWall(22, QuoridorBoard.Direction.EAST);

        assertEquals(-1, board.getNeighbor(22, QuoridorBoard.Direction.EAST));
        assertEquals(-1, board.getNeighbor(23, QuoridorBoard.Direction.WEST));

        assertEquals(-1, board.getNeighbor(31, QuoridorBoard.Direction.EAST));
        assertEquals(-1, board.getNeighbor(32, QuoridorBoard.Direction.WEST));
    }

    @Test
    public void testSouthWallOnBottomRowThrows() {
        QuoridorBoard board = new QuoridorBoard(9);
        assertThrows(IllegalArgumentException.class, () -> {
            board.buildWall(76, QuoridorBoard.Direction.SOUTH);
        });
    }

    @Test
    public void testEastWallOnRightColumnThrows() {
        QuoridorBoard board = new QuoridorBoard(9);
        assertThrows(IllegalArgumentException.class, () -> {
            board.buildWall(17, QuoridorBoard.Direction.EAST);
        });
    }

    @Test
    public void testNorthWallOnTopRowThrows() {
        QuoridorBoard board = new QuoridorBoard(9);
        assertThrows(IllegalArgumentException.class, () -> {
            board.buildWall(4, QuoridorBoard.Direction.NORTH);
        });
    }

    @Test
    public void testWestWallOnLeftColumnThrows() {
        QuoridorBoard board = new QuoridorBoard(9);
        assertThrows(IllegalArgumentException.class, () -> {
            board.buildWall(27, QuoridorBoard.Direction.WEST);
        });
    }
}
