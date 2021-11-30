package Persistence;

import Business.Entities.Usuari;
import Presentation.Controller.UserController;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * Aquesta classe és l'encarregada de fer totes les operacions i comprovacions pertinents a l'hora d'inserir i borrar informació a la base de dades.
 * @version 27/03/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class DaoUsuari {
    private static final String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String passwordRegex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
    private Connection remoteConnection;

    /**
     * Constructor de la classe
     * @param remoteConnection la connexió amb la base de dades
     */
    public DaoUsuari(Connection remoteConnection) {
        this.remoteConnection = remoteConnection;
    }


    /**
     * Aquest mètode és l'encarregat d'insertar les dades de l'usuari a la base de datos, abans d'insertar al usuari comproba que no estigui
     *
     * @param contrasenya la contrasenya
     * @return un boolean per si s'ha pogut registrar satisfactoriament
     * @throws SQLException excepció si l'usuari ja esta a la base de dades
     */
    public int register(Usuari usuari, String contrasenya) throws SQLException, NoSuchAlgorithmException {
        Statement statement = remoteConnection.createStatement();
        statement.executeQuery("USE ageroyale");

        String queryUser = "SELECT COUNT(*) AS counter FROM Usuari WHERE nom_usuari = BINARY\"" + usuari.getNom() + "\" OR correu = BINARY\"" + usuari.getCorreu() + "\"";
        String update = "INSERT INTO Usuari (nom_usuari, correu, contrasenya, wins, partides) VALUES (\"" + usuari.getNom() + "\",\"" + usuari.getCorreu() + "\",\"" + contrasenya + "\", 0, 0)";

        Pattern pat = Pattern.compile(emailRegex);

        if (!pat.matcher(usuari.getCorreu()).matches()) {
            return UserController.ERROR_CORREU;
        }
        Pattern pat2 = Pattern.compile(passwordRegex);
        if (!pat2.matcher(contrasenya).matches()) {
            return UserController.ERROR_FORMAT_PASSWORD;

        }

        ResultSet rs = statement.executeQuery(queryUser);

        rs.next();
        if (rs.getInt("counter") == 0) {
            statement.executeUpdate(update);
            return 0;
        }

        System.out.println("Error al registrar-se.");

        return UserController.ERROR_USUARI;
    }

    /**
     * Aquest mètodee és l'encarregat de comprobar si les dades introduides sobre l'usuari estan en la base de datos. aquesta classe comproba tant si l'usuari entrodueix correu
     * com nom i si es correcte permet al usuari avança
     *
     * @param contrasenya la contrasenya que s'ha escrit
     * @param nomCorreu el correu que s'ha escrit
     * @return l'usuari
     * @throws SQLException
     */
    public Usuari login(String contrasenya, String nomCorreu) throws SQLException {
        Statement statement = remoteConnection.createStatement();
        statement.executeQuery("USE ageroyale");
        String searchUser = "SELECT COUNT(*) AS counter FROM Usuari WHERE nom_usuari = BINARY\"" + nomCorreu + "\" AND contrasenya = BINARY\"" + contrasenya + "\"";
        String getUsuari = "SELECT nom_usuari, correu FROM Usuari WHERE nom_usuari = \"" + nomCorreu + "\" AND contrasenya = \"" + contrasenya + "\"";

        Pattern pat = Pattern.compile(emailRegex);
        if (pat.matcher(nomCorreu).matches()) {
            getUsuari = "SELECT nom_usuari, correu FROM Usuari WHERE correu = \"" + nomCorreu + "\" AND contrasenya = \"" + contrasenya + "\"";
            searchUser = "SELECT COUNT(*) AS counter FROM Usuari WHERE correu = BINARY\"" + nomCorreu + "\" AND contrasenya = BINARY\"" + contrasenya + "\"";
        }
        ResultSet rs = statement.executeQuery(searchUser);

        rs.next();

        if (rs.getInt("counter") == 0) {
            return null;
        }

        rs = statement.executeQuery(getUsuari);
        rs.next();

        String nom = rs.getString("nom_usuari");
        String correu = rs.getString("correu");
        Usuari usuari = new Usuari(nom, correu);


        return usuari;
    }
    /**
     *Funcio que borra els fitxers de les partides al borrar el compte de l'usuari
     * @param file el fitxer a borrar
     * @throws IOException
     */
    private void deleteDirectoryRecursion(File file) throws IOException {
        if (file.isDirectory()) {
            File[] entries = file.listFiles();
            if (entries != null) {
                for (File entry : entries) {
                    deleteDirectoryRecursion(entry);
                }
            }
        }
        if (!file.delete()) {
            throw new IOException("Failed to delete " + file);
        }
    }


    /**
     * Aquest mètode és l'encarregat d'eliminar l'usuari de la base de dades, tant la informacio del usuari com la del historial de partides
     *
     * @return un boolean per saber si s'ha pogut eliminar el compte de l'usuari correctament
     * @throws SQLException
     */
    public boolean eliminarCompte(Usuari usuari) throws SQLException {
        Statement statement = remoteConnection.createStatement();
        statement.executeQuery("USE ageroyale");
        String eliminarHistorial = "DELETE FROM Historial Where nom_usuari=?";
        PreparedStatement psmt = remoteConnection.prepareStatement(eliminarHistorial);
        psmt.setString(1, usuari.getNom());
        psmt.executeUpdate();

        String eliminarUser = "DELETE FROM Usuari WHERE nom_usuari = ?  AND correu = ?";
        psmt = remoteConnection.prepareStatement(eliminarUser);
        psmt.setString(1, usuari.getNom());
        psmt.setString(2, usuari.getCorreu());
        psmt.executeUpdate();

        File f1 = new File("./assets/RegistrePartides/" + usuari.getNom() + "/");
        try {
            deleteDirectoryRecursion(f1);
        } catch (IOException e) {
            System.out.println("No hi ha partides");
        }


        return true;
    }
    /**
     * Comprova que la partida no existeixi abans de guardarla
     * @param usuari nom de l'usuari
     * @param pathPartida path del fitxer partida
     * @return boolean per si la partida esta repetida o no
     * @throws SQLException
     */
    public boolean partidaUnica(Usuari usuari,String pathPartida) throws SQLException {

            Statement statement = remoteConnection.createStatement();
            statement.executeQuery("USE ageroyale");
            String searchPartida = "SELECT COUNT(*) AS counter FROM Historial WHERE url_partida = \"" + pathPartida + "\" AND nom_usuari = \"" + usuari.getNom() + "\"";
            ResultSet rs = statement.executeQuery(searchPartida);

            rs.next();
            if (rs.getInt("counter") == 0) {

                return true;
            }

        return false;
    }
    /**
     * Aquest mètode és l'encarregat d'afegir partides a la base de dades i d'actualitzar la informació del usuari (les partides jugades i el total de wins)
     * que ens servira per despres poder calcular el  win rate
     *
     * @param isWin
     * @param pathPartida
     * @param data
     * @return
     * @throws SQLException
     */
    public boolean afegirPartida(Usuari usuari, boolean isWin, String pathPartida, Timestamp data) throws SQLException {

        Statement statement = remoteConnection.createStatement();
        statement.executeQuery("USE ageroyale");

        //String query = "INSERT INTO Historial (url_partida, data, guanyador, nom_usuari) VALUES (\"" + pathPartida + "\" AND  \"" + data + "\" AND \"" + isWin + "\" AND \"" + this.nom + "\"";
        String query = "INSERT INTO Historial (url_partida, data, guanyador, nom_usuari) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = remoteConnection.prepareStatement(query);
        pstmt.setString(1, pathPartida);
        pstmt.setTimestamp(2, data);
        pstmt.setBoolean(3, isWin);
        pstmt.setString(4, usuari.getNom());
        pstmt.execute();

        String queryWin = "UPDATE usuari set partides=partides+1,wins = wins + 1 where nom_usuari = \"" + usuari.getNom() + "\"";
        String queryLose = "UPDATE usuari set partides = partides + 1 WHERE nom_usuari = \"" + usuari.getNom() + "\"";

        if (isWin) {
            statement.executeUpdate(queryWin);
        } else {
            statement.executeUpdate(queryLose);
        }

        return true;
    }
    /**
     * Agafa totes les partides pertanyents a l'usuari de la base de dades
     * @param usuari el nom d'usuari
     * @return les partides del usuari
     * @throws SQLException
     */
    public String[][] getPartides(Usuari usuari) throws SQLException {
        String query = "Select url_partida,DATE_FORMAT(data, '%Y-%m-%d %H:%i') as data,guanyador FROM Historial WHERE nom_usuari=? ORDER BY data DESC";


        PreparedStatement pstmt = remoteConnection.prepareStatement(query);
        pstmt.setString(1, usuari.getNom());
        ResultSet res = pstmt.executeQuery();

        ArrayList<String>url_partida = new ArrayList<>();
        ArrayList<String>data = new ArrayList<>();
        ArrayList<String>guanyador = new ArrayList<>();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
        Integer trobatFiles = 0;
        while (res.next()){
            String nomPartida = res.getString("url_partida");
            String[] pathpartida = nomPartida.split("/");
            nomPartida = pathpartida[pathpartida.length - 1];
            pathpartida = nomPartida.split("\\.json");
            nomPartida = pathpartida[0];
            url_partida.add(nomPartida);

            data.add(res.getString("data"));
            String resultat;
            if(res.getBoolean("guanyador")){
              resultat = "win";
            }else{
                resultat = "loss";
            }
            guanyador.add(resultat);
            trobatFiles++;
        }
        String[][] dades = new String[trobatFiles][3];
        for (int i = 0; i < trobatFiles; i++) {
            dades[i][0]=data.get(i);
            dades[i][1]= url_partida.get(i);
            dades[i][2]=guanyador.get(i);

        }

        return dades;
    }
    /**
     * Agafa totes les partides guardades del usuari ordenades per la data
     * @return les partides guardades del usuari
     * @throws SQLException
     */
    public String[][] getRanking() throws SQLException {
        Statement statement = remoteConnection.createStatement();
        statement.executeQuery("USE ageroyale");
        String searchRanking = "SELECT nom_usuari,wins,((wins/partides)*100) as ratio FROM Usuari ORDER BY ratio DESC;";

        ResultSet rs = statement.executeQuery(searchRanking);
        ArrayList<String>nom_usuari = new ArrayList<>();
        ArrayList<String>wins = new ArrayList<>();
        ArrayList<String>ratio = new ArrayList<>();

        Integer trobatFiles = 0;
        while(rs.next()){
            nom_usuari.add(rs.getString("nom_usuari"));

            wins.add(rs.getString("wins"));
            String ratioPercentatge = rs.getString("ratio");
            if(ratioPercentatge == null){
                ratioPercentatge = "No ha jugat cap partida"; //no hi ha partides
            }else{
                ratioPercentatge = ratioPercentatge +"%";
            }
            ratio.add(ratioPercentatge);
            trobatFiles++;
        }


        String[][] dades = new String[trobatFiles][3];
        for (int i = 0; i < trobatFiles; i++) {
            dades[i][0]=nom_usuari.get(i);
            dades[i][1]= wins.get(i);
            dades[i][2]=ratio.get(i);

        }

        return dades;
    }

}