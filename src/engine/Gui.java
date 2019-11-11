package engine;

import game.Board;
import engine.panels.Draw;

import javax.swing.*;

public class Gui {

    public static final int PAD = 4;

    private Board board;
    private int size;

    public Gui(Board board, int size) {
        this.board = board;
        this.size = size;
    }

    public void create(Clock clock) {
        JFrame f = new JFrame("Game Of Life");
        f.setSize(size+PAD*2 + 15 + 150 + PAD, size+PAD*2 + 40);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(false);

        Draw d = new Draw(size, board, clock);
        d.setBounds(0, 0, f.getWidth(), f.getHeight());
        d.setVisible(true);
        f.add(d);

        f.setVisible(true);
    }
}
