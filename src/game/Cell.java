package game;

import java.util.ArrayList;

public class Cell {

    private int x, y;
    private boolean affected = false, alive = false;

    Cell(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    void affect(boolean alive) {
        this.alive = alive;
        affected = true;
    }

    int neighbors(ArrayList<Cell> cells, int size) {
        int neighbors = 0;
        int[] offX = {-1, 0, 1, -1, 1, -1, 0, 1};
        int[] offY = {-1, -1, -1, 0, 0, 1, 1, 1};
        for (int i = 0; i < 8; i++) {
            int newX = x + offX[i];
            int newY = y + offY[i];
            if (newX >= 0 && newX < size && newY >= 0 && newY < size && cells.get(newY * size + newX).isAlive()) {
                neighbors++;
            }
        }
        return neighbors;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isAffected() {
        return affected;
    }

    public void setAffected(boolean affected) {
        this.affected = affected;
    }
}
