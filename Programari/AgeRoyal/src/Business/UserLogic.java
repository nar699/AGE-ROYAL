package Business;

import Business.Entities.Usuari;
import Persistence.DaoUsuari;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Classe que correspon a tot el que s'encarrega de controlar l'usuari que està utilitzant el programa i guardar la seva informació
 * @version 04/07/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class UserLogic {
    private DaoUsuari daoUsuari;
    private Usuari usuari;

    /**
     * Constructor de la llògica aplicable a l'usuari
     * @param model el model de dades
     * @param usuari l'usuari
     */
    public UserLogic(Model model,Usuari usuari) {
        this.daoUsuari = new DaoUsuari(model.getDatabaseConnector().getRemoteConnection());
        this.usuari = usuari;
    }

    /**
     * Actualitza l'usuari amb les dades que ha introduït
     * @param nom el nom introduït
     * @param correu el correu introduït
     */
    public void updateUsuari(String nom, String correu){
        this.usuari.setNom(nom);
        this.usuari.setCorreu(correu);
    }
    /**
     * Getter del nom d'usuari
     * @return el nom de l'usuari
     */
    public String getNom(){
        return usuari.getNom();
    }

    /**
     * Funció encarregada de controlar l'entrada de l'usuari al programa, segons les dades introduïdes
     * @param constrasenya la contrassenya d'entrada del usuari
     * @param nomCorreu el correu de l'usuari que vol entrar
     * @return boolea que informa de si l'usuari ha pogut entrar si la info era correcta
     */
    public boolean login(String constrasenya,String nomCorreu){
        try{
            Usuari usuari = daoUsuari.login(constrasenya,nomCorreu);
            if(usuari == null){
                return false;
            }
            this.usuari = usuari;
            return true;
        }
        catch (SQLException e){
            return false;
        }
    }

    /**
     * Funció per registrar un nou usuari en la base de dades
     * @param contrasenya la contrassenya a registrar en el nou usuari
     * @return boolea per comprobar si l'usuari s'ha pogut registrar amb éxit
     */
    public int register(String contrasenya){
        try{
            return daoUsuari.register(usuari,contrasenya);
        }
        catch (SQLException | NoSuchAlgorithmException e){
            return -1;
        }


    }
    /**
     * Funció per eliminar un compte de la base de dades
     * @return boolean per comprobar si s'ha pogut eliminar el compte
     */
    public boolean eliminarCompte(){

        try{
            return daoUsuari.eliminarCompte(usuari);
        }
        catch (SQLException e){
            return false;
        }
    }
    /**
     *Funció que s'encarrega d'afegir la partida que ha fet l'usuari a la base de dades
     * @param isWin si l'usuari ha guanyat o no
     * @param pathPartida el path al fitxer que guarda els moviments de la aprtida
     * @param data la data on s'ha acabat la partida
     * @return boolean per comprobar si s'ha pogut guardar la partida correctament
     */
    public boolean afegirPartida(boolean isWin, String pathPartida, Timestamp data){

        try{
            return daoUsuari.afegirPartida(usuari, isWin,pathPartida,data);
        }
        catch (SQLException e){
            return false;
        }
    }
    /**
     * Funcio que retornara un error si s'intenta guardar una partida que ja existeix
     * @param pathPartida el path a la partida que es vol crear
     * @return si s'ha pogut guardar
     */
    public boolean partidaUnica(String pathPartida){

        try{
            return daoUsuari.partidaUnica(usuari,pathPartida);
        }
        catch (SQLException e){
            return false;
        }
    }
    /**
     * Funció que retorna les partides fetes per l'usuari actual
     * @return els strings que contenen els moviments de les partides
     */
    public String[][] getPartides(){

        try{
            return daoUsuari.getPartides(usuari);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Invoca l'historial del usuari que s'esmenta per mostrar el seu historial al ranking
     * @param usuari  l'usuari del que mostrarem el ranking
     * @return els strings que contenen els moviments de les partides
     */
    public String[][] getPartides(Usuari usuari){

        try{
            return daoUsuari.getPartides(usuari);
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * Funció que retorna el ranking
     * @return el ranking
     */
    public String[][] getRanking(){

        try{
            return daoUsuari.getRanking();
        }
        catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
}
