import controllers.CombateController;
import controllers.JugadorController;
import controllers.LuchadorController;
import controllers.MenuController;
import view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();

        JugadorController jugadorController = new JugadorController();
        LuchadorController luchadorController = new LuchadorController();
        CombateController combateController = new CombateController();

        new MenuController(view, jugadorController, luchadorController, combateController).iniciar();
    }
}