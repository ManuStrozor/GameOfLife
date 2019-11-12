package game;

public class Cell {

    private int x, y, age = 0;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = Math.min(age, Integer.MAX_VALUE-1);
    }

    public boolean isAffected() {
        return affected;
    }

    public void setAffected(boolean affected) {
        this.affected = affected;
    }
}
