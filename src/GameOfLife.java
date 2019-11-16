import engine.*;
import game.Board;

public class GameOfLife {

    public static void main(String[] args) {

        Board board = new Board(800);
        Gui gui = new Gui(board, 800);
        Clock clock = new Clock(board, 10);

        gui.create(clock);
        clock.start();
    }
}
