package Business;

import java.io.Serializable;

/**
 * Classe utilitzada en el moviment de les tropes
 *  * @version 23/05/21
 *  * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class Moviment implements Serializable {
    private Boolean isMaquina;
    private long milisegons;
    private int tropa;
    private int fila;
    private int columna;
    private Boolean offensive;

    /**
     * constructor del moviment
     * @param isMaquina boolea de si el que es va a moure es la persona o la maquina
     * @param milisegons el milisegon en que s'ha posat
     * @param tropa de quina tropa es tracta
     * @param fila la fila on es troba la tropa
     * @param columna la columna on es troba la tropa
     * @param offensive si la tropa es ofensiva o defensiva
     */
    public Moviment(Boolean isMaquina, long milisegons, int tropa, int fila, int columna, Boolean offensive) {
        this.isMaquina = isMaquina;
        this.milisegons = milisegons;
        this.tropa = tropa;
        this.fila = fila;
        this.columna = columna;
        this.offensive = offensive;
    }

    /**
     * getter del segon actual
     * @return segons
     */
    public long getMilisegons() {
        return milisegons;
    }

    /**
     * getter de si es maquina o usuari
     * @return el boolea segons si es maquina o user
     */
    public Boolean getMaquina() {
        return isMaquina;
    }


    /**
     * getter del numero per saber de quina tropa es tracta
     * @return la tropa
     */
    public int getTropa() {
        return tropa;
    }

    /**
     * getter de la fila on està la tropa
     * @return la fila de la tropa
     */
    public int getFila() {
        return fila;
    }

    /**
     * getter de la columna on està la tropa
     * @return la columna de la tropa
     */

    public int getColumna() {
        return columna;
    }

    /**
     * getter de si la tropa es ofensiva o defensiva
     * @return si es ofensiva o defensiva
     */

    public Boolean getOffensive() {
        return offensive;
    }


}