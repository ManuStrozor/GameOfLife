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

    void setAlive(boolean alive) {
        this.alive = alive;
        affected = true;
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
