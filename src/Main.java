import Boundary.CLI;
import Control.GameController;

public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI();
         GameController gc = new GameController(cli);
        gc.run();
    }
}
