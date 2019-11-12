import engine.*;
import game.Board;

public class GameOfLife {

    public static void main(String[] args) {

        Board board = new Board(200);
        Gui gui = new Gui(board, 800);
        Clock clock = new Clock(board, 100);

        board.setup(3);
        clock.pause();
        gui.create(clock);
        clock.start();
    }
}
