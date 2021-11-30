package Presentation.Controller;

import Business.Entities.Player;
import Business.Model;
import Business.PartidaLogic;
import Presentation.View.GameView;
import Presentation.View.ReproduceView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ActionListener que s'activa cada 1000ms per comptar el temps
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class TimeController implements ActionListener {
    private GameView view;
    private Model model;
    private GameController gameController;
    private int indexMoviment;
    private ReproduceView reproduceView;
    private PartidaLogic partidaLogic;

    /**
     * Constructor del TimeController
     * @param vista la Gameview
     * @param model el Model
     * @param gameController el GameController
     * @param reproduceView la Gameview de reproduccions
     */
    public TimeController(GameView vista, Model model, GameController gameController, ReproduceView reproduceView) {
        this.model = model;
        this.view = vista;
        this.gameController = gameController;
        indexMoviment = 0;
        this.reproduceView = reproduceView;
        this.partidaLogic = model.getPartidaLogic();
    }

    /**
     * Métode del ActionListener
     * @param e parametre actionlistener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        int filaEnemy;

        if (partidaLogic.isJugant()) {
            partidaLogic.incrementarTemps();
            partidaLogic.getJugador().setMonedes(partidaLogic.getJugador().getMonedes() + 1);
            partidaLogic.getMaquina().setMonedes(partidaLogic.getMaquina().getMonedes() + 1);
            view.updateTimer(partidaLogic.getTimePassed());
            view.updateMoney(partidaLogic.getJugador().getMonedes());
            view.actualitzaVidesJugador(partidaLogic.getJugador().getVides(), Player.getVIDESTOTALS());
            view.actualitzaVidesMaquina(partidaLogic.getMaquina().getVides(), Player.getVIDESTOTALS());
            filaEnemy = partidaLogic.isEnemyInEnemySide();
            if (filaEnemy >= 0) {
                gameController.addDefensiveTroop(filaEnemy, true);
            }
            gameController.mouTropesOfensives(true);
            gameController.mouTropesOfensives(false);

            gameController.atacaTropesOfensives(true, view);
            gameController.atacaTropesOfensives(false, view);



            if (partidaLogic.getMaquina().getTempsOfensive() == 0) {
                partidaLogic.getMaquina().setTempsOfensive(3);
                gameController.addOfensiveTroop(true);

            } else {
                partidaLogic.getMaquina().setTempsOfensive(partidaLogic.getMaquina().getTempsOfensive() - 1);
            }

            view.actualitzarCaselles(partidaLogic.getEstatCaselles()); // fer per actualitzar coses view.actualitzarMonedes(model.getMonedes());
            view.actualitzaTropes(partidaLogic.getTropesMaquina(),partidaLogic.getTropesJugador());
        }else if(partidaLogic.getReproduint()){
            partidaLogic.incrementarTemps();
            partidaLogic.getJugador().setMonedes(partidaLogic.getJugador().getMonedes() + 1);
            partidaLogic.getMaquina().setMonedes(partidaLogic.getMaquina().getMonedes() + 1);
            reproduceView.updateTimer(partidaLogic.getTimePassed());
            reproduceView.updateMoney(partidaLogic.getJugador().getMonedes());
            reproduceView.actualitzaVidesJugador(partidaLogic.getJugador().getVides(), partidaLogic.getJugador().getVIDESTOTALS());
            reproduceView.actualitzaVidesMaquina(partidaLogic.getMaquina().getVides(), partidaLogic.getJugador().getVIDESTOTALS());

            gameController.mouTropesOfensives(true);
            gameController.mouTropesOfensives(false);

            gameController.atacaTropesOfensives(true, reproduceView);
            gameController.atacaTropesOfensives(false, reproduceView);



            reproduceView.actualitzarCaselles(partidaLogic.getEstatCaselles());
            reproduceView.actualitzaTropes(partidaLogic.getTropesMaquina(),partidaLogic.getTropesJugador());
        }
    }


}
