package org.cis1200.sudoku;

/**
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import java.io.*;
import java.util.*;

public class Sudoku {

    // the current board
    private int[][] board = new int[9][9];

    // the original board
    private int[][] original = new int[9][9];

    // a board that has cells as true when that cell can be changed
    private boolean[][] truthBoard = new boolean[9][9];

    // the original version of the truth board
    private boolean[][] originalTruthBoard = new boolean[9][9];

    // difficulty of the board (0 is the easiest)
    private int level;


    /**
     * Constructor sets up the board
     */
    public Sudoku() {
        reset();
    }

    /**
     * Clears the board
     */
    public void clear() {
        this.board = new int[9][9];
        this.truthBoard = new boolean[9][9];
    }

    /**
     * Rests the board
     */
    public void reset() {
        // deep copy the arrays
        for (int row1 = 0; row1 < 9; row1 ++) {
            for (int col1 = 0; col1 < 9; col1 ++) {
                this.truthBoard[row1][col1] = this.originalTruthBoard[row1][col1];
                this.board[row1][col1] = this.original[row1][col1];
            }
        }
    }

    /**
     * Give a value to a specified row and col
     * @param row: the row of the board
     * @param col: the column of the board
     * @param value: the value to give
     */
    public void setCell(int row, int col, int value) {
        // check if the cell is changeable
        if (this.truthBoard[row][col]) {
            this.board[row][col] = value;
        }
    }

    /**
     * Sets the board
     * @param newBoard: the new board
     */
    public void setBoard(int[][] newBoard) {
        this.board = newBoard;
        this.original = newBoard;
    }

    /**
     * Gets a copy of the current board
     * @return a copy of the current board
     */
    public int[][] getBoard() {
        int[][] copy = new int[9][9];
        for (int row = 0; row < 9; row ++) {
            for (int col = 0; col < 9; col ++) {
                copy[row][col] = this.board[row][col];
            }
        }
        return copy;
    }

    /**
     * Sets the value of a cell in the truthboard
     * @param row: the row of the truthboard
     * @param col: the column of the truthboard
     * @param val: the value to give
     */
    public void setTruthBoardVal(int row, int col, boolean val) {
        this.truthBoard[row][col] = val;
    }

    /**
     * Creates a copy of the truthboard and returns it
     * @return the truth board
     */
    public boolean[][] getTruthBoard() {
        boolean[][] copy = new boolean[9][9];
        for (int row = 0; row < 9; row ++) {
            for (int col = 0; col < 9; col ++) {
                copy[row][col] = this.truthBoard[row][col];
            }
        }
        return copy;
    }

    /**
     * Sets the level of difficulty
     * @param level: the difficulty level (0 is the easiest, 2 is the hardest)
     */
    public void setLevel(String level) {
        if (level.equals("Easy")) {
            this.level = 0;
        } else if (level.equals("Medium")) {
            this.level = 1;
        } else {
            this.level = 2;
        }
    }

    /**
     * Creates a valid board
     */
    public void createBoard() {
        // randomly pick a place on the board and give it a random value
        clear();
        Random rand = new Random();

        int row = rand.nextInt(9);
        int col = rand.nextInt(9);
        int val = rand.nextInt(9) + 1;
        this.board[row][col] = val;

        // solve the board after giving it a random value
        solveBoard();

        int max; // the number of values to take out (more = harder)
        if (this.level == 0) {
            max = 81 - 35;
        } else if (this.level == 1) {
            max = 81 - 30;
        } else {
            max = 81 - 25;
        }

        // take values away from a filled board
        int count = 0;
        while (count < max) {
            // randomly select a row and column to set the value to 0
            row = rand.nextInt(9);
            col = rand.nextInt(9);

            if (this.board[row][col] != 0) {
                this.board[row][col] = 0;
                this.truthBoard[row][col] = true;
                count += 1;
            }
        }

        // deep copy the arrays
        for (int row1 = 0; row1 < 9; row1 ++) {
            for (int col1 = 0; col1 < 9; col1 ++) {
                this.originalTruthBoard[row1][col1] = this.truthBoard[row1][col1];
                this.original[row1][col1] = this.board[row1][col1];
            }
        }
    }

    /**
     * Gets the possible values of the specified row and column based on the current board
     * @param row: row on the board
     * @param col: column on the board
     * @return a treeSet of all the possible values of the row and column
     */
    public TreeSet<Integer> getPossibleValues(int row, int col) {
        TreeSet<Integer> output = new TreeSet<>();
        for (int i = 1; i < 10; i ++) {
            output.add(i);
        }

        // Check column
        for (int i = 0; i < this.board.length; i ++) {
            if (output.contains(this.board[i][col])) {
                output.remove(this.board[i][col]);
            }
        }

        // Check row
        for (int j = 0; j < this.board[0].length; j ++) {
            if (output.contains(this.board[row][j])) {
                output.remove(this.board[row][j]);
            }
        }

        // Check 3x3 box
        int vertSect = (int) row / 3;
        int horiSect = (int) col / 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (output.contains(this.board[vertSect * 3 + i][horiSect * 3 + j])) {
                    output.remove(this.board[vertSect * 3 + i][horiSect * 3 + j]);
                }
            }
        }

        return output;
    }

    /**
     * Check if the current board doesn't violate any of the rules
     * @return true if the board doesn't violate the rules. false otherwise
     */
    public boolean checkCurBoard() {
        // Create a TreeSet that consists values from 1 to 9
        TreeSet<Integer> allVals = new TreeSet<>();

        // Check all the rows
        for (int row = 0; row < this.board.length; row ++) {
            addNums(allVals);
            for (int col = 0; col < this.board[0].length; col ++) {
                // remove it from allVals if it is a number that hasn't appeared before
                if (allVals.contains(this.board[row][col]) || this.board[row][col] == 0) {
                    allVals.remove(this.board[row][col]);
                } else {
                    return false;
                }
            }
        }

        // Check all the columns
        for (int col = 0; col < this.board[0].length; col ++) {
            addNums(allVals);
            for (int row = 0; row < this.board.length; row ++) {
                if (allVals.contains(this.board[row][col]) || this.board[row][col] == 0) {
                    allVals.remove(this.board[row][col]);
                } else {
                    return false;
                }
            }
        }

        // Check the 3x3 boxes
        for (int vertSect = 0; vertSect < 3; vertSect ++) {
            for (int horiSect = 0; horiSect < 3; horiSect ++) {
                addNums(allVals);
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        int row = vertSect * 3 + i;
                        int col = horiSect * 3 + j;
                        if (allVals.contains(this.board[row][col]) || this.board[row][col] == 0) {
                            allVals.remove(this.board[row][col]);
                        } else {
                            return false;
                        }
                    }
                }
            }
        }

        return true;
    }

    /**
     * Solves the current board or prints out "invalid board"
     */
    public void solveBoard() {
        if (!checkCurBoard()) {
            System.out.println("invalid board");
        }

        solve();
    }

    /**
     * Solve a board
     * @return true if the board is solvable. false otherwise
     */
    public boolean solve() {
        // base case (no empty cells
        if (findFirstEmpty() == null) {
            return checkCurBoard();

        } else { //there are still empty spots left
            int[] temp = findFirstEmpty();
            int row = temp[0];
            int col = temp[1];
            TreeSet<Integer> possVals  = getPossibleValues(row, col);

            // choose a value for the empty cell
            for (int num : possVals) {
                this.board[row][col] = num;
                if (solve()) {
                    return true;
                } else {
                    // the current configuration doesn't give an answer
                    this.board[row][col] = 0;
                }
            }
            return false;
        }
    }

    /**
     * Finds the first empty position on the board
     * returns null if everything is filled
     * @return an int array with the first element being the row and second element is the column
     */
    public int[] findFirstEmpty() {
        for (int row = 0; row < this.board.length; row ++) {
            for (int col = 0; col < this.board[0].length; col ++) {
                if (this.board[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    /**
     * Adds 1 to 9 to a treeSet
     * @param temp: treeSet we want to populate
     */
    public void addNums(TreeSet<Integer> temp) {
        for (int i = 1; i < 10; i ++) {
            temp.add(i);
        }
    }

    /**
     * Loads previous board
     */
    public void loadBoard(String filepath) throws IOException {
        File file = new File(filepath);
        BufferedReader br = new BufferedReader(new FileReader(file));

        System.out.println();
        System.out.println();

        // read the int values int
        for (int row = 0; row < 9; row ++) {
            String[] line = br.readLine().split(" ", 9);
            for (int col = 0; col < 9; col ++) {
                this.board[row][col] = Integer.parseInt(line[col].trim());
            }
        }

        // read the two empty lines
        br.readLine();
        br.readLine();

        // read the true false values
        for (int row = 0; row < 9; row ++) {
            String[] line = br.readLine().split(" ", 9);
            for (int col = 0; col < 9; col ++) {
                this.truthBoard[row][col] = Boolean.parseBoolean(line[col].trim());
            }
        }
    }

    /**
     * Saves the board
     */
    public void save(String filepath) {
        // creates new file and write the board in it
        try {
            File savedFile = new File(filepath);
            if (savedFile.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
            FileWriter writer = new FileWriter(filepath);

            // write the board into the file
            for (int row = 0; row < 9; row ++) {
                for (int col = 0; col < 9; col ++) {
                    writer.write(this.board[row][col] + " ");
                }
                writer.write("\n");
            }

            writer.write("\n");
            writer.write("\n");

            // write the truth board into the file
            for (int row = 0; row < 9; row ++) {
                for (int col = 0; col < 9; col ++) {
                    writer.write(this.truthBoard[row][col] + " ");
                }
                writer.write("\n");
            }

            writer.flush();
            writer.close();
        } catch (IOException io) {
            System.out.println("An error occurred");
            io.printStackTrace();
        }
    }

    /**
     * state 0: everything is good
     * state 1: baord isn't complete
     * state 2: something is wrong with the board
     * @return
     */
    public int check() {
        // Check if the board is complete
        for (int row = 0; row < 9; row ++) {
            for (int col = 0; col < 9; col ++) {
                if (this.board[row][col] == 0) {
                    return 1;
                }
            }
        }

        // Check if the board is correct
        if (checkCurBoard()) {
            return 0;
        }
        return 2;
    }

    public static void main(String[] args) {
        Sudoku t = new Sudoku();
        int[][] newBoard = {
                {0,8,5,0,1,0,0,0,0},
                {0,0,0,0,0,0,0,2,0},
                {0,0,6,8,0,0,0,4,5},
                {0,0,4,1,0,0,0,8,6},
                {0,0,0,0,0,0,7,0,0},
                {3,0,0,0,0,9,0,0,0},
                {4,0,0,0,3,0,0,5,7},
                {0,0,7,0,0,0,1,0,0},
                {0,0,0,5,0,0,2,0,0}
        };
        t.setBoard(newBoard);
        t.solveBoard();
    }
}


