import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class Testing {
    // @Test
    // @DisplayName("STUDENT TEST CASE - Win Condition")
    // public void firstCaseTest() {
    // AbstractStrategyGame g = /* TODO - construct your game*/;
    //
    // }
    //
    // @Test
    // @DisplayName("STUDENT TEST CASE - Illegal Move")
    // public void secondCaseTest() {
    // AbstractStrategyGame g = /* TODO - construct your game*/;
    //
    // }

    @Test
    void interiorCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;

        assertEquals(1, board.getNorthNeighbor(index));
        assertEquals(19, board.getSouthNeighbor(index));
        assertEquals(9, board.getWestNeighbor(index));
        assertEquals(11, board.getEastNeighbor(index));
    }

    @Test
    void topLeftCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 0;

        assertEquals(-1, board.getNorthNeighbor(index));
        assertEquals(9, board.getSouthNeighbor(index));
        assertEquals(-1, board.getWestNeighbor(index));
        assertEquals(1, board.getEastNeighbor(index));
    }

    @Test
    void topRightCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 8;

        assertEquals(-1, board.getNorthNeighbor(index));
        assertEquals(17, board.getSouthNeighbor(index));
        assertEquals(7, board.getWestNeighbor(index));
        assertEquals(-1, board.getEastNeighbor(index));
    }

    @Test
    void bottomLeftCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 72;

        assertEquals(63, board.getNorthNeighbor(index));
        assertEquals(-1, board.getSouthNeighbor(index));
        assertEquals(-1, board.getWestNeighbor(index));
        assertEquals(73, board.getEastNeighbor(index));
    }

    @Test
    void bottomRightCornerNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 80;

        assertEquals(71, board.getNorthNeighbor(index));
        assertEquals(-1, board.getSouthNeighbor(index));
        assertEquals(79, board.getWestNeighbor(index));
        assertEquals(-1, board.getEastNeighbor(index));
    }

    @Test
    void topRowNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 4;

        assertEquals(-1, board.getNorthNeighbor(index));
        assertEquals(13, board.getSouthNeighbor(index));
        assertEquals(3, board.getWestNeighbor(index));
        assertEquals(5, board.getEastNeighbor(index));
    }

    @Test
    void bottomRowNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 76;

        assertEquals(67, board.getNorthNeighbor(index));
        assertEquals(-1, board.getSouthNeighbor(index));
        assertEquals(75, board.getWestNeighbor(index));
        assertEquals(77, board.getEastNeighbor(index));
    }

    @Test
    void leftColumnNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 27;

        assertEquals(18, board.getNorthNeighbor(index));
        assertEquals(36, board.getSouthNeighbor(index));
        assertEquals(-1, board.getWestNeighbor(index));
        assertEquals(28, board.getEastNeighbor(index));
    }

    @Test
    void rightColumnNonCornerCellNeighbors() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 35;

        assertEquals(26, board.getNorthNeighbor(index));
        assertEquals(44, board.getSouthNeighbor(index));
        assertEquals(34, board.getWestNeighbor(index));
        assertEquals(-1, board.getEastNeighbor(index));
    }

    @Test
    void buildWallSouthRemovesSouthNeighbor() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;

        board.buildWall(index, QuoridorBoard.Direction.SOUTH);
        assertEquals(-1, board.getSouthNeighbor(index));
    }

    @Test
    void buildWallEastRemovesSouthNeighbor() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;

        board.buildWall(index, QuoridorBoard.Direction.EAST);
        assertEquals(-1, board.getEastNeighbor(index));
    }

    @Test
    void buildWallNorthCanonicalizesToAboveCellSouth() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;
        int above = 1;

        board.buildWall(index, QuoridorBoard.Direction.NORTH);
        assertEquals(-1, board.getSouthNeighbor(above));
    }

    @Test
    void buildWallWestCanonicalizesToLeftCellEast() {
        QuoridorBoard board = new QuoridorBoard(9);
        int index = 10;
        int left = index - 1;

        board.buildWall(index, QuoridorBoard.Direction.WEST);
        assertEquals(-1, board.getEastNeighbor(left));
    }
}
