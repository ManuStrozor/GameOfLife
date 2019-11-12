package engine;

import game.Board;

public class Clock extends Thread {

    private Board board;
    public static int speed;
    private volatile boolean running = true;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public Clock(Board board, int speed) {
        this.board = board;
        Clock.speed = speed;
    }

    @Override
    public void run() {
        while (running) {
            synchronized (pauseLock) {
                if (!running) break;
                if (paused) {
                    try {
                        synchronized (pauseLock) {
                            pauseLock.wait();
                        }
                    } catch (InterruptedException ex) {
                        break;
                    }
                    if (!running) break;
                }
            }
            try {
                sleep(speed);
                board.cycle();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        if (paused) {
            synchronized (pauseLock) {
                paused = false;
                pauseLock.notifyAll();
            }
        } else {
            paused = true;
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public void setSpeed(int speed) {
        Clock.speed = speed;
    }
}
