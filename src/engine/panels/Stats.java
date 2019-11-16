package engine.panels;

import engine.Clock;
import engine.Gui;
import game.Board;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Stats extends JPanel {

    private int size;
    private Board board;
    private Clock clock;
    private JLabel speed;
    HashMap<String, JButton> b = new HashMap<>();
    JCheckBox box;
    JSlider slider;
    JComboBox<Integer> combo;
    static boolean ageActive = false;

    Stats(int size, Board board, Clock clock) {
        this.setLayout(new FlowLayout());
        this.size = size;
        this.board = board;
        this.clock = clock;
        init();
    }

    private void init() {
        b.put("pause", new JButton("Démarrer"));
        this.add(b.get("pause"));
        b.put("quit", new JButton("Quitter"));
        this.add(b.get("quit"));

        box = new JCheckBox("Mode vieillesse");
        this.add(box);

        slider = new JSlider();
        this.add(speed = new JLabel(speedify(Clock.speed)));
        this.add(slider);
        configSlider(5, 150);

        combo = new JComboBox<>(getBoardSizes(size));
        this.add(new JLabel("Taille du plateau :"));
        this.add(combo);
        configCombo();

        b.put("alea", new JButton("Aléatoire"));
        this.add(b.get("alea"));
        b.put("seed", new JButton("Graine"));
        this.add(b.get("seed"));
        b.put("clear", new JButton("Apocalypse"));
        this.add(b.get("clear"));

        this.makePause();
        board.setSize((int) combo.getSelectedItem());
        board.randSetup();

        this.setActionListeners();
    }

    private void configCombo() {
        int i = 0;
        while (i < combo.getItemCount() && combo.getItemAt(i) < board.getSize()) {
            i++;
        }
        if (i < combo.getItemCount()) combo.setSelectedIndex(i);
        else combo.setSelectedIndex(i-1);
    }

    private void configSlider(int min, int max) {
        slider.setPreferredSize(new Dimension(Gui.STATS_WIDTH, 20));
        slider.setMinimum(min);
        slider.setValue(Clock.speed);
        slider.setMaximum(max);
        slider.setInverted(true);
    }

    private void makePause() {
        if (!clock.isPaused()) {
            clock.pause();
            b.get("pause").setText("Démarrer");
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

    private Integer[] getBoardSizes(int windowSize) {
        ArrayList<Integer> t = new ArrayList<>();
        for (int i = windowSize; i > 0; i--) {
            float num = (float)windowSize / i;
            if (num == (int)num && num >= board.getSeedSize()) t.add((int) num);
        }
        Integer[] tailles = new Integer[t.size()];
        for (int i = 0; i < tailles.length; i++) {
            tailles[i] = t.get(i);
        }
        return tailles;
    }

    private void setActionListeners() {
        b.forEach((name, btn) -> {
            switch (name) {
                case "pause":
                    btn.addActionListener(e -> {
                        if (!clock.isPaused() || Draw.pop > 0) clock.pause();
                        else board.randSetup();
                        if (!clock.isPaused()) btn.setText("Pause");
                        else btn.setText("Démarrer");
                    });
                    break;
                case "quit":
                    btn.addActionListener(e -> System.exit(0));
                    break;
                case "alea":
                    btn.addActionListener(e -> {
                        this.makePause();
                        board.randSetup();
                    });
                    break;
                case "seed":
                    btn.addActionListener(e -> {
                        this.makePause();
                        board.setSeed();
                    });
                    break;
                case "clear":
                    btn.addActionListener(e -> {
                        this.makePause();
                        board.apocalypse();
                    });
                    break;
            }
        });
        box.addActionListener(e -> ageActive = box.isSelected());
        slider.addChangeListener(e -> clock.setSpeed(slider.getValue()));
        combo.addActionListener(e -> {
            this.makePause();
            board.setSize((int) combo.getSelectedItem());
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        speed.setText(speedify(Clock.speed));
    }
}
