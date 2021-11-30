package Persistence;

import Business.config_load;

import java.sql.*;

/**
 * Aquesta classe és l'encarregada de conectar el nostre projecte amb la nostra base de dades.
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class DatabaseConnector {

    private Connection remoteConnection;
    private config_load configuracio;

    /**
     * Mètode que revisa i comprova la configuració de nostre host, en cas de no poder conectar-se ho notifica per la consola.
     */
    public DatabaseConnector() {
        configuracio = new config_load();
        configuracio.llegirDades();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            remoteConnection = DriverManager.getConnection("jdbc:mysql://localhost:" + configuracio.getConfiguracio().getPort() + "/?user=" + configuracio.getConfiguracio().getUser() + "&password=" + configuracio.getConfiguracio().getPassword() + "&serverTimezone=UTC");
            System.out.println("Connectat a la base de dades correctament");
        } catch (Exception e) {
            System.err.println("Can not connect to jdbc:mysql://localhost");
        }
    }

    /**
     * Getter de l'atribut remoteConnection
     * @return la remoteConnection
     */
    public Connection getRemoteConnection() {
        return remoteConnection;
    }

}
