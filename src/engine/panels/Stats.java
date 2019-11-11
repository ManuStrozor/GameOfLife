package engine.panels;

import engine.Clock;
import game.Board;
import javax.swing.*;
import java.awt.*;

public class Stats extends JPanel {

    private int size;
    private Board board;
    private Clock clock;

    Stats(int size, Board board, Clock clock) {
        this.setLayout(new FlowLayout());
        this.size = size;
        this.board = board;
        this.clock = clock;
        init();
    }

    private void init() {
        JButton pauseButton = new JButton("Start");
        JButton setupButton = new JButton("Setup");

        JSlider clockSlider = new JSlider();
        clockSlider.setPreferredSize(new Dimension(150, 30));
        clockSlider.setMinimum(1);
        clockSlider.setValue(clock.getSpeed());
        clockSlider.setMaximum(50);
        clockSlider.setInverted(true);

        JTextField sizeField = new JTextField(""+board.getSize());
        sizeField.setColumns(10);
        JButton clearButton = new JButton("Apocalypse");
        JButton exitButton = new JButton("Quit");

        pauseButton.addActionListener(e -> {
            clock.pause();
            if (!clock.isPaused()) pauseButton.setText("Pause");
            else pauseButton.setText("Start");
        });

        setupButton.addActionListener(e -> {
            if (!clock.isPaused()) {
                clock.pause();
                pauseButton.setText("Start");
            }
            board.setup(3);
        });

        clockSlider.addChangeListener(e -> clock.setSpeed(clockSlider.getValue()));

        sizeField.addActionListener(e -> {
            if (!clock.isPaused()) {
                clock.pause();
                pauseButton.setText("Start");
            }
            int val = board.getSize();
            try {
                val = Integer.parseInt(sizeField.getText());
            } catch(NumberFormatException ignored) {}
            if (val >= 4 && val <= size) {
                board.setSize(val);
                board.setup(3);
            }
        });

        clearButton.addActionListener(e -> {
            board.apocalypse();
            if (!clock.isPaused()) {
                clock.pause();
                pauseButton.setText("Start");
            }
        });

        exitButton.addActionListener(e -> System.exit(0));

        this.add(pauseButton);
        this.add(setupButton);
        this.add(new JLabel("Speed :"));
        this.add(clockSlider);
        this.add(new JLabel("Size :"));
        this.add(sizeField);
        this.add(clearButton);
        this.add(exitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int cnt = board.countAffected();
        if (cnt > 0) {
            g.setColor(Color.BLACK);
            g.drawString("Pop: " + cnt, 0, size);
        }
    }
}
