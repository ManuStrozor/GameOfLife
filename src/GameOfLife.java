import engine.*;
import game.Board;

public class GameOfLife {

    public static void main(String[] args) {

        Board board = new Board(400);
        Gui gui = new Gui(board, 800);
        Clock clock = new Clock(board, 50);

        board.setCanon();
        clock.pause();
        gui.create(clock);
        clock.start();
    }
}
