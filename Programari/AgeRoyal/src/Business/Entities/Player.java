package Business.Entities;


/**
 * Classe per crear el jugador i per la màquina
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */


public class Player {
    private int vides;
    private int monedes;
    private static final int MONEDESINICIALS = 30;
    private int tropesPosades;
    private int tempsOfensive;
    private static final int VIDESTOTALS = 500;
    private Boolean maquina;

    /**
     * Constructor del jugador
     * @param maquina boolean per si es tracta del jugador o na màquina
     */

    public Player( Boolean maquina) {
        tempsOfensive = 3;
        vides = VIDESTOTALS;
        monedes = MONEDESINICIALS;
        this.maquina = maquina;
    }

    /**
     * getter de vides
     * @return vides
     */

    public int getVides() {
        return vides;
    }

    /**
     * getter de monedes
     * @return monedes
     */
    public int getMonedes() {
        return monedes;
    }


    /**
     * getter de la quantitat de tropes posades
     * @return tropesPosades
     */
    public int getTropesPosades() {
        return tropesPosades;
    }

    /**
     * setter de les vides
     * @param vides nombre de vides
     */
    public void setVides(int vides) {
        this.vides = vides;
        if(this.vides < 0){
            this.vides = 0;
        }
    }

    /**
     * setter de monedes
     * @param monedes nombre de monedes
     */
    public void setMonedes(int monedes) {
        this.monedes = monedes;
    }

    /**
     * getter de la vida incial de la torre
     * @return VIDESTOTALS
     */

    public static int getVIDESTOTALS() {
        return VIDESTOTALS;
    }

    /**
     * getter del temps que queda per colocar tropa ofensiva
     * @return tempsOfensive
     */
    public int getTempsOfensive() {
        return tempsOfensive;
    }

    /**
     * setter de temps que queda per colocar tropa ofensiva
     * @param tempsOfensive temps restant
     */
    public void setTempsOfensive(int tempsOfensive) {
        this.tempsOfensive = tempsOfensive;
    }

    public void resetPlayer(){
        this.monedes=MONEDESINICIALS;
        this.vides=VIDESTOTALS;
    }


}