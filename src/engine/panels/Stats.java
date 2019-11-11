package engine.panels;

import engine.Clock;
import engine.Gui;
import game.Board;
import javax.swing.*;
import java.awt.*;

public class Stats extends JPanel {

    private int size;
    private Board board;
    private Clock clock;
    private JLabel speed;

    Stats(int size, Board board, Clock clock) {
        this.setLayout(new FlowLayout());
        this.size = size;
        this.board = board;
        this.clock = clock;
        init();
    }

    private void init() {
        JButton pauseButton = new JButton("Démarrer");
        JButton exitButton = new JButton("Quitter");
        JSeparator separator = new JSeparator();
        separator.setPreferredSize(new Dimension(Gui.STATS_WIDTH, 0));
        JSlider clockSlider = new JSlider();
        clockSlider.setPreferredSize(new Dimension(Gui.STATS_WIDTH, 30));
        clockSlider.setMinimum(1);
        clockSlider.setValue(clock.getSpeed());
        clockSlider.setMaximum(150);
        clockSlider.setInverted(true);
        JComboBox<Object> sizeCombo = new JComboBox<>(new Object[] {
                "40", "50", "80", "100", "160", "200", "400", "800"
        });
        sizeCombo.setSelectedIndex(6);
        JButton aleaButton = new JButton("Aléatoire");
        JButton canonButton = new JButton("Canon");
        JButton clearButton = new JButton("Apocalypse");

        pauseButton.addActionListener(e -> {
            if (!clock.isPaused() || board.getPopulation() > 0) clock.pause();
            else board.setup(3);
            if (!clock.isPaused()) pauseButton.setText("Pause");
            else pauseButton.setText("Démarrer");
        });

        aleaButton.addActionListener(e -> {
            makePause(pauseButton);
            board.setup(3);
        });

        clockSlider.addChangeListener(e -> clock.setSpeed(clockSlider.getValue()));

        sizeCombo.addActionListener(e -> {
            makePause(pauseButton);
            int val = board.getSize();
            try {
                val = Integer.parseInt(sizeCombo.getSelectedItem().toString());
            } catch(NumberFormatException ignored) {}
            board.setSize(val);
            //board.setup(3);
        });

        clearButton.addActionListener(e -> {
            makePause(pauseButton);
            board.apocalypse();
        });

        canonButton.addActionListener(e -> {
            makePause(pauseButton);
            board.setCanon();
        });

        exitButton.addActionListener(e -> System.exit(0));

        this.add(pauseButton);
        this.add(exitButton);
        this.add(speed = new JLabel(speedify(clock.getSpeed())));
        this.add(clockSlider);
        this.add(new JLabel("Taille :"));
        this.add(sizeCombo);
        this.add(separator);
        this.add(aleaButton);
        this.add(canonButton);
        this.add(clearButton);
    }

    private void makePause(JButton btn) {
        if (!clock.isPaused()) {
            clock.pause();
            btn.setText("Démarrer");
        }
    }

    private String speedify(int speed) {
        String s = "Vitesse : ";
        if (speed <= 10) {
            return s+"très rapide";
        } else if (speed <= 50) {
            return s+"rapide";
        } else if (speed <= 100) {
            return s+"lente";
        } else {
            return s+"très lente";
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        speed.setText(speedify(clock.getSpeed()));

        g.setColor(Color.BLACK);
        g.drawString("Population: " + board.getPopulation(), 0, size);
    }
}
