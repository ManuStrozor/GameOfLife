package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class Board {

    /**
     * Attributs : un plateau a une taille, un numéro de génération, des cellules
     * et un canon tout mignon !
     */
    private int size, gen;
    private ArrayList<Cell> cells;
    private int[][] canon = new int[][] {
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
    };

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
            int n = neighbors(c);
            if (n == 3) {
                if (!c.isAlive()) changes.put(c, true);
            } else if (n != 2) {
                if (c.isAlive()) changes.put(c, false);
            }
        }
        changes.forEach(Cell::setAlive);
        changes.clear();
        gen++;
    }

    int neighbors(Cell c) {
        int neighbors = 0;
        int[] offX = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] offY = {-1, -1, -1, 0, 0, 1, 1, 1};
        for (int i = 0; i < 8; i++) {
            int newX = c.getX() + offX[i];
            int newY = c.getY() + offY[i];
            if (newX >= 0 && newX < size && newY >= 0 && newY < size && cells.get(newY * size + newX).isAlive()) {
                neighbors++;
            }
        }
        return neighbors;
    }

    public void setCanon() {
        apocalypse();
        for (int y = 0; y < canon.length; y++) {
            for (int x = 0; x < canon[y].length; x++) {
                if (canon[y][x] == 1) cells.get(y * size + x).setAlive(true);
            }
        }
    }

    /**
     * Chaque cellule du plateau a une chance sur val de prendre vie
     */
    public void setup(int val) {
        gen = 0;
        for (Cell c : cells) {
            c.setAlive(randomize(val));
        }
    }

    /**
     * Toutes les cellules vivantes du plateau meurent
     */
    public void apocalypse() {
        gen = 0;
        for (Cell c : cells) {
            c.setAlive(false);
        }
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

    public int getPopulation() {
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
        ArrayList<Cell> cells = new ArrayList<>();
        if (this.cells != null && this.size < size) {
            for (int y = 0; y < this.size; y++) {
                for (int x = 0; x < this.size; x++) {
                    cells.add(this.cells.get(y * this.size + x));
                }
                for (int i = 0; i < size - this.size; i++) {
                    cells.add(new Cell(this.size+i, y));
                }
            }
            for (int y = this.size; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    cells.add(new Cell(x, y));
                }
            }
        } else {
            for (int y = 0; y < size; y++) {
                for (int x = 0; x < size; x++) {
                    if (this.cells != null && this.size > size) {
                        Cell oldCell = this.cells.get(y * this.size + x);
                        if (oldCell.isAlive()) cells.add(oldCell);
                        else cells.add(new Cell(x, y));
                    } else {
                        cells.add(new Cell(x, y));
                    }
                }
            }
        }
        this.cells = cells;
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getGen() {
        return gen;
    }
}
