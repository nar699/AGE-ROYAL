package Business;

import Business.Entities.Partida;
import Business.Entities.Player;
import Business.Entities.Tropa;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;


/**
 * Classe que tracta la llògica de funcionament de la partida
 * @version 05/07/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class PartidaLogic {
    private int numFiles;
    private int numColumnes;
    private Boolean jugant; //inici partida
    private Boolean reproduint;
    private Tropa[][] estatCaselles;
    private static final int EMPTY = -1;
    private static final int TOWER = 1;
    private static final int OFENSIVE = 2;
    private static final int DEFENSIVE = 3;
    private int timePassed;
    private Partida partidaActual;
    private long iniciPartida;


    private Player maquina;
    private Player jugador;
    private ArrayList<Tropa> tropesOf;
    private ArrayList<Tropa> tropesDef;

    /**
     * Constructor de la llogica de la partida
     *
     * @param numColumnes el nombre de columnes del tauler
     * @param numFiles    el nombre de files del tauler
     */
    public PartidaLogic(int numColumnes, int numFiles) {
        this.numFiles = numFiles;
        this.numColumnes = numColumnes;
        estatCaselles = new Tropa[numFiles][numColumnes];
        timePassed = 0;
        this.jugant = false;
        this.reproduint = false;
        for (int i = 0; i < numFiles; i++) {
            for (int j = 0; j < numColumnes; j++) {
                estatCaselles[i][j] = new Tropa(i, j);
            }
        }

        int posicioTorre = (int) Math.floor(numColumnes / 2);
        estatCaselles[0][posicioTorre] = new Tropa(TOWER, true, 0, posicioTorre);
        estatCaselles[numFiles - 1][posicioTorre] = new Tropa(TOWER, false, numFiles - 1, posicioTorre);

        jugador = new Player(false);
        maquina = new Player(true);

        tropesOf = new ArrayList<Tropa>();
        tropesDef = new ArrayList<Tropa>();
        tropesDef.add(new Tropa(150, 0, false, false, 0, 2, false, "Cano", 10, "assets/Images/Defensive1.png", -1, -1));
        tropesDef.add(new Tropa(150, 0, false, false, 1, 4, false, "Morter", 10, "assets/Images/Defensive2.png", -1, -1));
        tropesDef.add(new Tropa(200, 0, false, false, 2, 1, false, "Mur", 5, "assets/Images/Defensive3.png", -1, -1));
        tropesOf.add(new Tropa(100, 15, true, false, 0, 1, false, "Barbaro", 12, "assets/Images/barbaro.png", -1, -1));
        tropesOf.add(new Tropa(70, 20, true, false, 1, 3, false, "Arquera", 12, "assets/Images/arquera.png", -1, -1));

        partidaActual = new Partida();
    }

    /**
     * Getter de l'atribut imagePath
     *
     * @return Retorna el path de les tropes ofensives.
     */
    public String[] getImagePathOf() {
        String[] imageTroops = new String[tropesOf.size()];
        for (int i = 0; i < tropesOf.size(); i++) {
            imageTroops[i] = tropesOf.get(i).getPath();
        }
        return imageTroops;
    }

    /**
     * Métode que incrementa el temps transcorregut
     */
    public void incrementarTemps() {
        timePassed++;
    }

    /**
     * Funció que retorna el temps transcorregut
     */
    public int getTimePassed() {
        return timePassed;
    }

    /**
     * Métode que reseteja a 0 el temps transcorregut
     */
    public void resetTimePassed() {
        timePassed = 0;
    }


    /**
     * Mètode que reinicia totes les caselles del taulell
     */
    public synchronized void reiniciEstatCaselles() {
        for (int i = 0; i < numFiles; i++) {
            for (int j = 0; j < numColumnes; j++) {
                estatCaselles[i][j] = new Tropa(i, j);
            }
        }
        int posicioTorre = (int) Math.floor(numColumnes / 2);
        estatCaselles[0][posicioTorre] = new Tropa(TOWER, true, 0, posicioTorre);
        estatCaselles[numFiles - 1][posicioTorre] = new Tropa(TOWER, false, numFiles - 1, posicioTorre);
    }

    /**
     * Getter de l'atribut ImagePathDef
     *
     * @return Retorna el path de les tropes defensives.
     */
    public String[] getImagePathDef() {
        String[] imageTroops = new String[tropesDef.size()];
        for (int i = 0; i < tropesDef.size(); i++) {
            imageTroops[i] = tropesDef.get(i).getPath();
        }
        return imageTroops;
    }

    /**
     * Getter de l'atribut tropesOf
     *
     * @return l'arraylist de tropes ofensives
     */
    public ArrayList<Tropa> getTropesOf() {
        return tropesOf;
    }

    /**
     * Getter de l'atribut tropesDef
     *
     * @return l'arraylist de tropes defensives
     */
    public ArrayList<Tropa> getTropesDef() {
        return tropesDef;
    }

    /**
     * Métode per poder guardar la partida
     *
     * @param pathPartida el path al fitxer que conté la partida
     */
    public void guardarPartida(String pathPartida) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(partidaActual);

        try {
            Writer writer = new FileWriter(pathPartida);

            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mètode que utilitzem per afegir una nova tropa.
     *
     * @param i     És el número de la fila en el taulell.
     * @param j     És el número de la columna en el taulell.
     * @param tropa És la tropa que afegim.
     */
    public synchronized void addTropa(Integer i, Integer j, Tropa tropa, boolean save) {
        if (save) {
            Moviment moviment = new Moviment(tropa.isMaquina(), System.currentTimeMillis() - iniciPartida, tropa.getTropa(), i, j, tropa.isOffensive());
            partidaActual.getMoviments().add(moviment);
        }
        estatCaselles[i][j] = tropa;
        tropa.setNumColumna(j);
        tropa.setNumFila(i);
    }

    /**
     * Retorna la quantitat de tropes de la maquina
     *
     * @return la quantitat de tropes de la maquina
     */
    public int getTropesMaquina() {
        int numTropes = 0;
        for (int i = 0; i < numFiles; i++) {
            for (int j = 0; j < numColumnes; j++) {
                if (estatCaselles[i][j].isMaquina() && !estatCaselles[i][j].isEmpty()) {
                    numTropes++;
                }
            }
        }
        return numTropes;
    }

    /**
     * Retorna la quantitat de tropes del jugador
     *
     * @return la quantitat de tropes del jugador
     */
    public int getTropesJugador() {
        int numTropes = 0;
        for (int i = 0; i < numFiles; i++) {
            for (int j = 0; j < numColumnes; j++) {
                if (!estatCaselles[i][j].isMaquina() && !estatCaselles[i][j].isEmpty()) {
                    numTropes++;
                }
            }
        }
        return numTropes;
    }


    /**
     * Getter de l'atribut estatCaselles
     *
     * @return l'estat de les caselles
     */
    public Tropa[][] getEstatCaselles() {
        return estatCaselles;
    }

    /**
     * Mètode que utilitzem per saber si una tropa ofensiva de l'usuari està en la part del taulell de la màquina.
     *
     * @return Retorna la fila en la que es troba la tropa ofensiva de l'usuari si està en la part del taulell de la
     * màquina o retorna -1 si no hi ha cap tropa ofensive de l'usuari a la part del taulell de la màquina.
     */
    public int isEnemyInEnemySide() {

        for (int i = 0; i < numFiles / 2; i++) {
            for (int j = 0; j < numColumnes; j++) {
                if (estatCaselles[i][j].isMaquina() == false && !estatCaselles[i][j].isEmpty()) {
                    return i;
                }
            }
        }
        return -1;
    }


    /**
     * Getter de l'atribut maquina
     *
     * @return la maquina
     */
    public Player getMaquina() {
        return maquina;
    }

    /**
     * Getter de l'atribut jugador
     *
     * @return el jugador
     */
    public Player getJugador() {
        return jugador;
    }

    /**
     * Seteja el milisegon on comença la partida
     *
     * @param iniciPartida el temps on comença la partida
     */
    public void setIniciPartida(long iniciPartida) {
        this.iniciPartida = iniciPartida;
    }


    /**
     * Mètode que utilitzem per obtenir ja sigui la tropa ofensiva o defensiva.
     *
     * @param ofdef    Booleà que indica si la tropa és ofensiva o defensiva.
     * @param tropaNum És el número de tropa.
     * @return Retorna la tropa.
     */
    public Tropa getTropa(Boolean ofdef, Integer tropaNum) {
        if (ofdef == true) {
            return tropesOf.get(tropaNum);
        } else {
            return tropesDef.get(tropaNum);
        }
    }


    /**
     * Getter de l'atribut numFiles
     *
     * @return el nombre de files
     */
    public int getNumFiles() {
        return numFiles;
    }


    /**
     * Getter de jugant
     *
     * @return si esta jugant
     */
    public Boolean isJugant() {
        return jugant;
    }

    /**
     * Setter de jugant
     *
     * @param jugant si esta jugant
     */
    public void setJugant(Boolean jugant) {
        this.jugant = jugant;
    }

    /**
     * Getter de l'atribut reproduint
     *
     * @return si s'esta reproduint
     */
    public Boolean getReproduint() {
        return reproduint;
    }

    /**
     * Setter de l'atribut reproduint
     *
     * @param reproduint si s'esta reproduint
     */
    public void setReproduint(Boolean reproduint) {
        this.reproduint = reproduint;
    }

    /**
     * Mètode que carreguem la partida jugada.
     *
     * @param nomPartida És el nomPartida del fitxer generat.
     */
    public void loadGame(String nomPartida, String nomUsuari) {
        Gson gson = new Gson();
        String path = "./assets/RegistrePartides/" + nomUsuari + "/" + nomPartida + ".json";
        try {
            FileReader filerdr = new FileReader(path);
            JsonReader reader = new JsonReader(filerdr);
            partidaActual = gson.fromJson(reader, partidaActual.getClass());

            filerdr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Getter de l'atribut partidaActual
     *
     * @return la partida actual
     */
    public Partida getPartidaActual() {
        return partidaActual;
    }

    /**
     * Getter de l'atribut numColumnes
     *
     * @return el nombre de columnes
     */
    public int getNumColumnes() {
        return numColumnes;
    }
}

