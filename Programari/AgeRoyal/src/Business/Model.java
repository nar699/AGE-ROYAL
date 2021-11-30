package Business;


import Persistence.DatabaseConnector;
import Business.Entities.Usuari;


/** Classe on es donen a terme totes les operacions internes de la pràctica
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class Model {
    private UserLogic userLogic;
    private DatabaseConnector databaseConnector;
    private PartidaLogic partidaLogic;


    /**
     * Constructor de la classe Model.
     * @param numFiles el nombre de les files
     * @param numColumnes el nombre de les columnes
     */
    public Model(int numFiles, int numColumnes) {

        databaseConnector = new DatabaseConnector();
        this.userLogic = new UserLogic(this,new Usuari("",""));
        partidaLogic = new PartidaLogic(numColumnes,numFiles);
    }

    /**
     * Agafa informació de la llògica de la partida
     * @return la informació pertanyent a la llògica de la partida
     */
    public PartidaLogic getPartidaLogic() {
        return partidaLogic;
    }

    /**
     * Getter de l'atribut usuari
     * @return la llogica del usuari
     */
    public UserLogic getUserLogic() {
        return userLogic;
    }

    /**
     * Setter de l'atribut usuari
     * @param usuari l'usuari
     */
    public void updateUsuari(Usuari usuari) {
        this.userLogic.updateUsuari(usuari.getNom(), usuari.getCorreu());
    }

    /**
     * Getter de l'atribut DatabaseConnector
     * @return el database connector per conectarse amb la base de dades
     */
    public DatabaseConnector getDatabaseConnector() {
        return databaseConnector;
    }



}
