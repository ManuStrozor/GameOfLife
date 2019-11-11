package engine.panels;

import engine.Clock;
import game.Board;
import javax.swing.*;
import java.awt.*;

public class Stats extends JPanel {

    private int size;
    private Board board;
    private Clock clock;

    private JLabel labelSpeed;

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
        clockSlider.setMaximum(150);
        clockSlider.setInverted(true);

        JTextField sizeField = new JTextField(""+board.getSize());
        sizeField.setColumns(10);
        JButton clearButton = new JButton("Apocalypse");
        JButton exitButton = new JButton("Quit");

        pauseButton.addActionListener(e -> {
            if (!clock.isPaused() || board.getPopulation() > 0) clock.pause();
            else board.setup(3);
            if (!clock.isPaused()) pauseButton.setText("Pause");
            else pauseButton.setText("Start");
        });

        setupButton.addActionListener(e -> {
            pause(pauseButton);
            board.setup(3);
        });

        clockSlider.addChangeListener(e -> clock.setSpeed(clockSlider.getValue()));

        sizeField.addActionListener(e -> {
            pause(pauseButton);
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
            pause(pauseButton);
            board.apocalypse();
        });

        exitButton.addActionListener(e -> System.exit(0));

        this.add(pauseButton);
        this.add(setupButton);
        labelSpeed = new JLabel("Speed : " + clock.getSpeed());
        this.add(labelSpeed);
        this.add(clockSlider);
        this.add(new JLabel("Size :"));
        this.add(sizeField);
        this.add(clearButton);
        this.add(exitButton);
    }

    private void pause(JButton btn) {
        if (!clock.isPaused()) {
            clock.pause();
            btn.setText("Start");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        labelSpeed.setText("Speed : " + clock.getSpeed());
        int cnt = board.getPopulation();
        g.setColor(Color.BLACK);
        g.drawString("Pop: " + cnt, 0, size);
    }
}
