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
                g.setColor(c.isAlive() ? (Stats.ageActive ? disapear(0x000000, c.getAge()) : Color.BLACK) : Color.white);
                g.fillRect(c.getX() * s + Gui.PAD, c.getY() * s + Gui.PAD, s, s);
                //g.fillOval(c.getX() * s + Gui.PAD, c.getY() * s + Gui.PAD, s, s);
                if (!c.isAlive()) c.setAffected(false);
            }
        }

        g.setColor(Color.WHITE);
        g.fillRect(Gui.PAD+2, Gui.PAD, textWidth("" + board.getGen()), m.getHeight()-2);
        g.setColor(Color.RED);
        g.drawString(""+board.getGen(), Gui.PAD+2, Gui.PAD+11);

        repaint();
    }

    private Color disapear(int color, int age) {
        int speed = Clock.speed;
        int r = (color>>16)&0xFF;
        int g = (color>>8)&0xFF;
        int b = (color)&0xFF;
        r += age/speed; r = Math.min(r, 240);
        g += age/speed; g = Math.min(g, 240);
        b += age/speed; b = Math.min(b, 240);
        int newColor = (255<<24)|(r<<16)|(g<<8)|b;
        return new Color(newColor);
    }

    private int textWidth(String text) {
        return m.stringWidth(text);
    }
}
