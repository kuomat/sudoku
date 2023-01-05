package org.cis1200.sudoku;

/*
 * CIS 120 HW09 - TicTacToe Demo
 * (c) University of Pennsylvania
 * Created by Bayley Tuch, Sabrina Green, and Nicolas Corona in Fall 2020.
 */

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class RunSudoku implements Runnable {
    public void run() {
        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Sudoku");
        frame.setLocation(300, 300);

        // Game board
        org.cis1200.sudoku.GameBoard board = new org.cis1200.sudoku.GameBoard();
        frame.add(board, BorderLayout.CENTER);

        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Selects a level
        String[] levels = {"Easy", "Medium", "Hard"};
        JComboBox levelList = new JComboBox(levels);
        levelList.addActionListener(e -> board.setLevel((String) levelList.getSelectedItem()));
        control_panel.add(levelList);

        // Creates a new board
        final JButton createBoard = new JButton("New");
        createBoard.addActionListener(e -> board.createGameBoard());
        control_panel.add(createBoard);

        // Loads an old board
        final JButton loadBoard = new JButton("Load");
        loadBoard.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = JOptionPane.showInputDialog(frame, "What is the filepath", null);
                try {
                    // if the user didn't enter anything for the filepath
                    if (filePath == null) {
                        JOptionPane.showMessageDialog(frame, "Enter something");
                        board.requestFocus();
                    } else {
                        // the filepath is successful
                        board.loadGameBoard(filePath);
                    }
                } catch (IOException e) {
                    // cannot find the file
                    JOptionPane.showMessageDialog(frame, "File not found");
                }
            }
        });
        control_panel.add(loadBoard);

        // Resets the board
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Saves the board
        final JButton save = new JButton("Save");
        save.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = JOptionPane.showInputDialog(frame, "What is the filepath", null);
                if (filePath == null) {
                    // if the user didn't enter anything
                    JOptionPane.showMessageDialog(frame, "Enter something");
                    board.requestFocus();
                } else {
                    // the filepath is valid
                    board.saveBoard(filePath);
                }
            }
        });
        control_panel.add(save);

        // Checks the board
        final JButton check = new JButton("Check");
        check.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                int gameState = board.check();
                // the board is correct
                if (gameState == 0) {
                    JOptionPane.showMessageDialog(frame, "Good Job!!");
                } else if (gameState == 1) {
                    // the board is unfinished
                    JOptionPane.showMessageDialog(frame, "Finish the board!");
                } else {
                    // the board is wrong
                    JOptionPane.showMessageDialog(frame, "WRONG");
                }
            }
        });
        control_panel.add(check);

        final JButton instructions = new JButton("Instructions");
        instructions.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(frame, "A basic sudoku game with normal rules. \n" +
                        "1. Select a mode on the top left corner \n" +
                        "2. Press 'new' to generate a board. The immutable cells will " +
                        "have numbers that are bolded and in blue. \n" +
                        "3. Fill in the numbers based on normal sudoku rules by pressing " +
                        "on a mutable cell and input NUMBERS. \n" +
                        "4. Can press reset to reset the board to its original state. \n" +
                        "5. When bored, can save the board by pressing 'save' and typing " +
                        "in a valid path \n" +
                        "6. To load the board again, press 'load' and give it the path \n" +
                        "7. When done, press 'check' to check answers");
            }
        });
        control_panel.add(instructions);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}