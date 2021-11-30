package Business.Entities;


/**
 * Classe que correspon a tot el que s'encarrega de controlar l'usuari que està utilitzant el programa i guardar la seva informació
 * @version 04/07/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class Usuari {
    private String nom;
    private String correu;

    /**
     * Constructor de l'usuari
     * @param nom nom de l'usuari
     * @param correu correu de l'usuari
     */
    public Usuari(String nom, String correu) {
        this.nom = nom;
        this.correu = correu;
    }


    /**
     * Getter del nom d'usuari
     * @return el nom de l'usuari
     */
    public String getNom() {
        return nom;
    }
    /**
     * Getter del correu de l'usuari
     * @return el correu del usuari
     */
    public String getCorreu() {
        return correu;
    }
    /**
     * Setter del nom del usuari
     * @param nom el nom d'usuari
     */
    public void setNom(String nom) {
        this.nom = nom;
    }
    /**
     * Setter del correu de l'usuari
     * @param correu el correu de l'usuari
     */
    public void setCorreu(String correu) {
        this.correu = correu;
    }



}
