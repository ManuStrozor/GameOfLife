package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    /**
     * Attributs : un plateau a une taille, un numéro de génération et des cellules
     */
    private int size, gen;
    private ArrayList<Cell> cells;
    private HashMap<Cell, Boolean> changes = new HashMap<>();

    /**
     * Constructeur : initialisation des attributs
     * @param size taille du plateau
     */
    public Board(int size) {
        gen = 0;
        setSize(size);
        setCells();
    }

    public void cycle() {
        for (Cell c : cells) {
            int n = c.neighbors(cells, size);
            if (n == 3) {
                if (!c.isAlive()) changes.put(c, true);
            } else if (n != 2) {
                if (c.isAlive()) changes.put(c, false);
            }
        }
        changes.forEach(Cell::affect);
        changes.clear();
        gen++;
    }

    public boolean setXYAmbrion(int offX, int offY, int[][] ambrion) {
        if (!checkXYAmbrion(offX, offY, ambrion)) {
            return false;
        } else {
            for (int y = 0; y < ambrion.length; y++) {
                for (int x = 0; x < ambrion[y].length; x++) {
                    if (ambrion[y][x] == 1) cells.get((offY + y) * size + (offX + x)).affect(true);
                }
            }
            return true;
        }
    }

    /**
     * Chaque cellule du plateau a une chance sur val de prendre vie
     */
    public void setup(int val) {
        gen = 0;
        for (Cell c : cells) {
            c.affect(randomize(val));
        }
    }

    /**
     * Toutes les cellules vivantes du plateau meurent
     */
    public void apocalypse() {
        for (Cell c : cells) {
            if (c.isAlive()) c.affect(false);
        }
    }

    private boolean checkXYAmbrion(int offX, int offY, int[][] ambrion) {
        int maxLength = 0;
        for (int[] line : ambrion) maxLength = Math.max(maxLength, line.length);
        if (maxLength == 0) return false;
        return maxLength + offX <= size && ambrion.length + offY <= size;
    }

    /**
     * Utiliser ThreadLocalRandom.current().nextInt()
     * pour générer un nombre aléatoire entre deux nombre
     * @param val chance
     * @return true 1 fois sur val
     */
    private boolean randomize(int val) {
        return ThreadLocalRandom.current().nextInt(1, val) == 1;
    }

    public int countAffected() {
        int cnt = 0;
        for (Cell c : cells) {
            if (c.isAffected()) cnt++;
        }
        return cnt;
    }

    /**
     * Créé des cellules, chaque cellule a une coordonnée x et y,
     * x étant compri entre 0 et size-1, idem pour y
     */
    private void setCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                cells.add(new Cell(x, y));
            }
        }
        this.cells = cells;
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setSize(int size) {
        this.size = size;
        setCells();
    }

    public int getSize() {
        return size;
    }

    public int getGen() {
        return gen;
    }
}
