package engine.panels;

import engine.Clock;
import game.*;
import engine.Gui;
import javax.swing.*;
import java.awt.*;

public class Draw extends JPanel {

    private FontMetrics m;
    private int size;
    private Board board;

    public Draw(int size, Board board, Clock clock) {
        this.setLayout(null);
        this.size = size;
        this.board = board;

        Stats stats = new Stats(size, board, clock);
        stats.setBounds(size + Gui.PAD*2, Gui.PAD-1, Gui.STATS_WIDTH, size+1);
        this.add(stats);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        m = g.getFontMetrics(this.getFont());

        g.setColor(Color.RED);
        g.drawRect(Gui.PAD-1, Gui.PAD-1, size+1, size+1);

        int s = size / board.getSize();
        for (Cell c : board.getCells()) {
            if (c.isAffected()) {
                g.setColor(c.isAlive() ? Color.BLACK : Color.WHITE);
                g.fillRect(c.getX() * s + Gui.PAD, c.getY() * s + Gui.PAD, s, s);
                if (!c.isAlive()) c.setAffected(false);
            }
        }

        g.setColor(Color.WHITE);
        g.fillRect(Gui.PAD+2, Gui.PAD, textWidth("" + board.getGen()), m.getHeight()-2);
        g.setColor(Color.RED);
        g.drawString(""+board.getGen(), Gui.PAD+2, Gui.PAD+11);

        repaint();
    }

    private int textWidth(String text) {
        return m.stringWidth(text);
    }
}
