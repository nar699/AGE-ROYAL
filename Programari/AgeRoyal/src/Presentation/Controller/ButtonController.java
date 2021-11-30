package Presentation.Controller;

import Business.Entities.Usuari;
import Business.Model;
import Business.PartidaLogic;
import Presentation.View.HistorialView;
import Presentation.View.MainView;
import Presentation.View.RankingView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 *Aquest es el controller que implementa el ActionListener que pertany a tots els botons del programa,
 * excepte els que pertanyen al joc. El que fem aquí es canviar la vista del cardLayout segons el
 * botó premut.
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */

public class ButtonController implements ActionListener {
    private final MainView view;
    private final UserController userController;
    private final Model model;
    private final PartidaLogic partidaLogic;
    private final GameController gameController;

    /**
     * Constructor del controller
     * @param model el model
     * @param view la vista
     */
    public ButtonController(MainView view, Model model, GameController gameController){

        this.view = view;
        this.model = model;
        userController = new UserController(model,view);
        this.partidaLogic = model.getPartidaLogic();
        this.gameController = gameController;
    }

    /**
     * Métode del ActionListener que activa les diferents card de la vista
     * @param e l'Action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){

            case MainView.BTN_LOGIN:
                view.showLoginCard();
                break;

            case MainView.BTN_REGISTER:
                view.showRegisterCard();
                break;


            case MainView.BTN_BACK_LOGIN:
                view.showLoginRegisterCard();
                break;


            case MainView.BTN_BACK_REGISTER:
                view.showLoginRegisterCard();
                break;

            case MainView.BTN_SUBMIT_REGISTER:
                if(this.userController.registerUsuari(this.view.retornaContra(), this.view.retornaConfirmation(), this.view.retornaNomRegister(), this.view.retornaEmail())){
                    view.showMenuCard();
                }
                view.clearRegisterText();


                break;

            case MainView.BTN_SUBMIT_LOGIN:
                if (this.userController.loginUsuari(this.view.retornaContraLogin(), this.view.retornaNomLogin())){
                    view.showMenuCard();
                }else{
                    view.loginError();
                }
                view.clearLoginText();
                break;
            case MainView.BTN_START_GAME:
                view.showGameCard();
                partidaLogic.setIniciPartida(System.currentTimeMillis());
                partidaLogic.reiniciEstatCaselles();
                partidaLogic.resetTimePassed();
                partidaLogic.getJugador().resetPlayer();
                partidaLogic.getMaquina().resetPlayer();
                partidaLogic.setJugant(true);

                break;
            case MainView.BTN_START_REPLAYS:

                view.configureHistorialView(model.getUserLogic().getPartides(),this,true);

                view.showHistorialCard();

                break;
            case MainView.BTN_START_RANKING:

                view.configureRankingView(model.getUserLogic().getRanking(),this);
                view.showRankingCard();
                break;

            case RankingView.BTN_BACK_RANKING:
                view.showMenuCard();
                break;

            case HistorialView.BTN_BACK_HISTORIAL:
                view.showMenuCard();
                break;
            case RankingView.BTN_GO_HISTORIAL:
                view.configureHistorialView(
                        model.getUserLogic().getPartides(new Usuari(view.getRankingView().getUsuari(),"")),
                        this,
                        false
                );

                view.showHistorialCard();
                break;

            case HistorialView.BTN_REPRODUIR_HISTORIAL:
                partidaLogic.reiniciEstatCaselles();
                partidaLogic.resetTimePassed();
                partidaLogic.getJugador().resetPlayer();
                partidaLogic.getMaquina().resetPlayer();
                partidaLogic.loadGame(view.getHistorialView().getNomPartidaSeleccionada(),model.getUserLogic().getNom());
                partidaLogic.setReproduint(true);
                gameController.carregarMoviments(partidaLogic.getPartidaActual().getMoviments());

                view.showReproCard();

                break;

            case MainView.BTN_START_OPTIONS:
                view.showLogoutCard();
                break;
            case MainView.BTN_BACK_OPTIONS:
                view.showMenuCard();
                break;
            case MainView.BTN_LOGOUT:
                view.showLoginRegisterCard();
                break;
            case MainView.BTN_DELETE_USER:
                view.showLoginRegisterCard();
                model.getUserLogic().eliminarCompte();
                break;
        }
    }
}
