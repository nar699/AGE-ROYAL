package Presentation.Controller;


import Business.Entities.Tropa;
import Business.Moviment;
import Business.PartidaLogic;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller encarregat de gestionar les partides reproduïdes des dels fitxers de text
 * @version 04/07/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class ReproducerController implements ActionListener  {
    private PartidaLogic partidaLogic;
    private Moviment moviment;
    private Tropa tropa;

    /**
     * Constructor del controller del reproductor de la partida
     * @param partidaLogic la llogica de la partida a reproduïr
     * @param moviment els moviments de les tropes
     * @param tropa les tropes
     */
    public ReproducerController(PartidaLogic partidaLogic, Moviment moviment,Tropa tropa){
        this.partidaLogic = partidaLogic;
        this.moviment = moviment;
        this.tropa = tropa;

    }

    /**
     * Actionperformed del botó que inicia la reproduccio
     * @param e parametre de ActionPerformed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        partidaLogic.addTropa(moviment.getFila(), moviment.getColumna(), tropa,false);

    }
}
