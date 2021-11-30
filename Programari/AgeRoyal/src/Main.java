import Business.PartidaLogic;
import Presentation.Controller.ButtonController;
import Presentation.Controller.GameController;
import Business.Model;
import Presentation.View.MainView;

import java.sql.SQLException;

/**
 * Classe main encarregada d'executar tot el nostre projecte
 * @version 23/05/2021
 * @author Marc Postils, Joan Llobet, David Tort, Narcis Cisquella i Lluis Gumbau
 */
public class Main {
    private static final int NUM_FILES = 8;
    private static final int NUM_COLUMNES = 7;
    public static void main(String[] args) throws SQLException {


        Model model = new Model(NUM_FILES, NUM_COLUMNES);
        PartidaLogic partidaLogic =  model.getPartidaLogic();
        MainView mainView = new MainView(NUM_FILES, NUM_COLUMNES, partidaLogic.getImagePathOf(), partidaLogic.getImagePathDef());
        GameController gameController = new GameController(mainView.getGameView(),model, mainView.getReproView(), mainView);
        ButtonController firstcontroller = new ButtonController(mainView, model,gameController);
        mainView.addGameController(gameController);
        mainView.registerController(firstcontroller);
        mainView.start();
    }
}
