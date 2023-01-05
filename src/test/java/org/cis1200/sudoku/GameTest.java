package org.cis1200.sudoku;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.TreeSet;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    private Sudoku sudoku;

    @BeforeEach
    public void setUp() {
        sudoku = new Sudoku();
        int[][] newBoard = {
                {6,5,9,7,3,2,1,8,4},
                {4,1,2,5,8,9,7,3,6},
                {3,7,8,1,6,4,5,2,9},
                {9,4,3,6,7,5,2,1,8},
                {8,2,7,4,9,1,6,5,3},
                {1,6,5,3,2,8,4,9,7},
                {7,9,6,2,1,3,8,4,5},
                {5,3,1,8,4,6,9,7,2},
                {2,8,4,9,5,7,0,0,0}
        };

        sudoku.setBoard(newBoard);
    }

    @Test
    public void testBoardEncapsulation() {
        int[][] temp = sudoku.getBoard();
        temp[0][0] = 0;
        assertEquals(6, sudoku.getBoard()[0][0]);
    }

    @Test
    public void testSetBoard() {
        int[][] newBoard = {
            {6,5,9,7,3,2,1,8,4},
            {4,1,2,5,8,9,7,3,6},
            {3,7,8,1,6,4,5,2,9},
            {9,4,3,6,7,5,2,1,8},
            {8,2,7,4,9,1,6,5,3},
            {1,6,5,3,2,8,4,9,7},
            {7,9,6,2,1,3,8,4,5},
            {5,3,1,8,4,6,9,7,2},
            {2,8,4,9,5,7,7,0,0}
        };

        sudoku.setBoard(newBoard);
        assertArrayEquals(newBoard, sudoku.getBoard());
    }

    @Test
    public void testGetPossibleValuesForEmptyCell() {
        Collection<Integer> output = new TreeSet<>();
        output.add(3);

        assertEquals(output, sudoku.getPossibleValues(8, 6));
    }

    @Test
    public void testGetPossibleValuesForFilledCell() {
        Collection<Integer> output = new TreeSet<>();
        assertEquals(output, sudoku.getPossibleValues(0, 0));

    }

    @Test
    public void testCheckNormalBoardIsCorrect() {
        int[][] newBoard = {
                {6,5,9,7,3,2,1,8,4},
                {4,1,2,5,8,9,7,3,6},
                {3,7,8,1,6,4,5,2,9},
                {9,4,3,6,7,5,2,1,8},
                {8,2,7,4,9,1,6,5,3},
                {1,6,5,3,2,8,4,9,7},
                {7,9,6,2,1,3,8,4,5},
                {5,3,1,8,4,6,9,7,2},
                {2,8,4,9,5,7,0,0,0}
        };

        sudoku.setBoard(newBoard);
        assertTrue(sudoku.checkCurBoard());
    }

    @Test
    public void testInvalidRowsBoard() {
        int[][] newBoard = {
                {6,5,9,7,3,2,1,8,4},
                {4,1,2,5,8,9,7,3,6},
                {3,7,8,1,6,4,5,2,9},
                {9,4,3,6,7,5,2,1,8},
                {8,2,7,4,9,1,6,5,3},
                {1,6,5,3,2,8,4,9,7},
                {7,9,6,2,1,3,8,4,5},
                {5,3,1,8,4,6,9,7,2},
                {2,2,4,9,5,7,0,0,0}
        };

        sudoku.setBoard(newBoard);
        assertFalse(sudoku.checkCurBoard());
    }

    @Test
    public void testInvalidColumnsBoard() {
        int[][] newBoard = {
                {6,5,9,7,3,2,1,8,4},
                {6,1,2,5,8,9,7,3,6},
                {3,7,8,1,6,4,5,2,9},
                {9,4,3,6,7,5,2,1,8},
                {8,2,7,4,9,1,6,5,3},
                {1,6,5,3,2,8,4,9,7},
                {7,9,6,2,1,3,8,4,5},
                {5,3,1,8,4,6,9,7,2},
                {2,8,4,9,5,7,0,0,0}
        };

        sudoku.setBoard(newBoard);
        assertFalse(sudoku.checkCurBoard());
    }

    @Test
    public void testInvalidBoxBoard() {
        int[][] newBoard = {
                {6,5,9,7,3,2,1,8,4},
                {4,1,2,5,8,9,7,3,6},
                {3,7,8,1,6,4,5,2,9},
                {9,4,3,6,7,5,2,1,8},
                {8,2,7,4,9,1,6,5,3},
                {1,6,5,3,2,8,4,9,7},
                {0,9,6,2,1,3,0,7,0},
                {5,3,1,8,4,6,7,0,0},
                {2,0,4,9,5,0,0,0,0}
        };

        sudoku.setBoard(newBoard);
        assertFalse(sudoku.checkCurBoard());
    }

    @Test
    public void testGetFirstEmpty() {
        int[][] newBoard = {
                {6,0,9,7,3,2,1,8,4},
                {4,1,2,5,8,9,7,3,6},
                {3,7,8,1,6,4,5,2,9},
                {9,4,3,6,7,5,2,1,8},
                {8,2,7,4,9,1,6,5,3},
                {1,6,5,3,2,8,4,9,7},
                {0,9,6,2,1,3,0,7,0},
                {5,3,1,8,4,6,7,0,0},
                {2,0,4,9,5,0,0,0,0}
        };
        sudoku.setBoard(newBoard);
        int[] output = {0,1};
        assertArrayEquals(output, sudoku.findFirstEmpty());
    }

    @Test
    public void testSetCellNotEmpty() {
        int[][] temp = sudoku.getBoard();
        sudoku.setCell(0, 0, 0);
        int[][] temp2 = sudoku.getBoard();
        assertArrayEquals(temp, temp2);
    }

    @Test
    public void testSetCellEmpty() {
        sudoku.setTruthBoardVal(8, 8, true);
        sudoku.setCell(8,8, 7);
        int[][] temp = sudoku.getBoard();
        assertEquals(7, temp[8][8]);
    }

    @Test
    public void testCheckStateUnfinished() {
        assertEquals(1, sudoku.check());
    }

    @Test
    public void testCheckStateWrong() {
        int[][] newBoard = {
                {6,5,9,7,3,2,1,8,4},
                {4,1,2,5,8,9,7,3,6},
                {3,7,8,1,6,4,5,2,9},
                {9,4,3,6,7,5,2,1,8},
                {8,2,7,4,9,1,6,5,3},
                {1,6,5,3,2,8,4,9,7},
                {7,9,6,2,1,3,8,4,5},
                {5,3,1,8,4,6,9,7,2},
                {2,8,4,9,5,7,1,1,1}
        };

        sudoku.setBoard(newBoard);
        assertEquals(2, sudoku.check());
    }

    @Test
    public void testCheckStateCorrect() {
        int[][] newBoard = {
                {2,4,9,3,5,8,1,6,7},
                {6,1,5,7,9,2,8,4,3},
                {8,7,3,1,6,4,5,9,2},
                {7,3,4,8,2,6,9,5,1},
                {5,9,2,4,7,1,3,8,6},
                {1,6,8,9,3,5,7,2,4},
                {3,5,7,2,4,9,6,1,8},
                {9,2,1,6,8,7,4,3,5},
                {4,8,6,5,1,3,2,7,9}
        };

        sudoku.setBoard(newBoard);
        assertEquals(0, sudoku.check());
    }
}
