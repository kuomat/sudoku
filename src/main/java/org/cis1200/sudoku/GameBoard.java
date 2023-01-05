package org.cis1200.sudoku;

/*
 * CIS 120 HW09 - Sudoku
 * (c) University of Pennsylvania
 * Created by Matthew Kuo
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class GameBoard extends JPanel {

    private Sudoku sudoku; // model for the game

    // Game constants
    public static final int BOARD_WIDTH = 550;
    public static final int BOARD_HEIGHT = 550;
    private static int row;
    private static int col;

    /**
     * Initializes the game board.
     */
    public GameBoard() {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);
        this.sudoku = new Sudoku(); // initializes model for the game
    }

    /**
     * Resets the game to its initial state.
     */
    public void reset() {
        sudoku.reset();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Checks if the game is finished or whether the board is correct
     */
    public int check() {
        repaint();
        requestFocusInWindow();
        return sudoku.check();
    }

    /**
     * Focuses the window again to listen for mouse or keyboard events
     */
    public void requestFocus() {
        requestFocusInWindow();
    }

    /**
     * Creates the game board
     */
    public void createGameBoard() {
        sudoku.createBoard();
        repaint();
        requestFocusInWindow();
    }

    /**
     * Loads a previous board
     */
    public void loadGameBoard(String filepath) throws IOException {
        sudoku.loadBoard(filepath);
        repaint();
        requestFocusInWindow();
    }

    /**
     * Sets the difficulty of the board
     * @param level
     */
    public void setLevel(String level) {
        sudoku.setLevel(level);
    }

    /**
     * Saves the board
     */
    public void saveBoard(String filepath) {
        sudoku.save(filepath);
        repaint();
        requestFocusInWindow();
    }

    /**
     * Draws the game board
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        // Draws the board
        int space = 50;

        // Vertical lines
        for (int i = 0; i < 10; i ++) {
            // bolded lines
            if (i % 3 == 0) {
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(50 + space * i, 50, 50 + space * i, 500);
            } else {
                g.drawLine(50 + space * i, 50, 50 + space * i, 500);
            }
        }
        // Horizontal lines
        for (int i = 0; i < 10; i ++) {
            if (i % 3 == 0) {
                g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawLine(50, 50 + space * i, 500, 50 + space * i);
            } else {
                g.drawLine(50, 50 + space * i, 500, 50 + space * i);
            }
        }

        // Draws numbers
        for (int row = 0; row < 9; row ++) {
            for (int col = 0; col < 9; col ++) {
                int num = sudoku.getBoard()[col][row];

                // original board
                if (num != 0 && !sudoku.getTruthBoard()[col][row]) {
                    // make the original ones bolded and a different color
                    g.setColor(new Color(0,0,255));
                    g.setFont(new Font("default", Font.BOLD, 16));
                    g.drawString(String.valueOf(num), 70 + row * 50 , 30 + (col + 1) * 50);
                }

                // values entered when playing
                if (num != 0 && sudoku.getTruthBoard()[col][row]) {
                    g.setColor(new Color(0,0,0));
                    g.setFont(new Font("default2", Font.PLAIN, 13));
                    g.drawString(String.valueOf(num), 70 + row * 50 , 30 + (col + 1) * 50);
                }
            }
        }

        // mouse listener
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Mouse pressed");
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                int xPos = p.x;
                int yPos = p.y;

                // the mouseclick is inside the grid
                if (xPos >= 50 && xPos <= 500 && yPos >= 50 && yPos <= 500) {
                    col = (xPos - 50) / 50;
                    row = (yPos - 50) / 50;
                }
                repaint();
            }
        });

        // keyboard listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                System.out.println("key pressed");
                int ascii = e.getKeyCode();

                // restrict only to numbers
                if (ascii >= 49 && ascii <= 57) {
                    int value = Character.getNumericValue(e.getKeyChar());
                    sudoku.setCell(row, col, value);
                }
                repaint();
            }
        });
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
