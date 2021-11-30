package Business.Entities;

import Business.Moviment;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * Classe que emmagatzema els moviments que hi ha hagut en una partida
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class Partida implements Serializable {
    private ArrayList<Moviment> moviments;

    /**
     * Constructor de la classe
     */
    public Partida(){
        moviments = new ArrayList<>();
    }

    /**
     * Getter de l'atribut moviments
     * @return els moviments
     */
    public ArrayList<Moviment> getMoviments() {
        return moviments;
    }


}
