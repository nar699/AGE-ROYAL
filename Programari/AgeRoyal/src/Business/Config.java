package Business;

/**
 * Classe que utilitzem per guardar-nos la informació per conectar-se a la base de dades del usuari.
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class Config {
    private Integer port;
    private String ip;
    private String dataBaseName;
    private String user;
    private String password;


    /**
     * Constructor de la classe Config.
     *
     * @param port         És el port de l'usuari.
     * @param ip           És la ip de l'usuari.
     * @param dataBaseName És el nom de la base de dades del usuari.
     * @param user         És el usuari en el Workbench del usuari.
     * @param password     És la contrasenya en el Workbench del usuari.
     */
    public Config(Integer port, String ip, String dataBaseName, String user, String password) {
        this.port = port;
        this.ip = ip;
        this.dataBaseName = dataBaseName;
        this.user = user;
        this.password = password;
    }

    /**
     * Getter del port.
     * @return Retorna el port.
     */
    public Integer getPort() {
        return port;
    }

    /**
     * Getter del nom d'usuari.
     * @return Retorna el nom d'usuari.
     */
    public String getUser() {
        return user;
    }

    /**
     * Getter de la contrasenya.
     * @return Retorna la contrasenya.
     */
    public String getPassword() {
        return password;
    }
}
