package Presentation.Controller;

import Business.*;
import Business.Entities.Player;
import Business.Entities.Tropa;
import Presentation.View.GameView;
import Presentation.View.ImagePanel;
import Presentation.View.MainView;
import Presentation.View.ReproduceView;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;


/**
 *Aquest es el controller que implementa el ActionListener que pertany a tots els botons del programa,
 * excepte els que pertanyen al joc. El que fem aquí es canviar la vista del cardLayout segons el
 * botó premut.
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */


public class GameController implements MouseListener {
    private Model model;
    private TimeController timeController;
    private GameView vista;
    private Pair<Integer,Integer> coordenades;
    private Tropa tropa;
    private static final int MONEDESPERKILL = 10;
    private ReproduceView reproduceView;
    private MainView mainView;
    private PartidaLogic partidaLogic;

    /**
     *Aquest controller personalitzat treballa amb els mouseListener de les caselles del tauler
     * i ens permet obtenir i reaccionar als diferents esdeveniments que succeeixen al tauler.
     * @param reproduceView es la GameView de la partida reproduida
     * @param model es el model
     * @param vista es la vista del joc
     */

    public GameController(GameView vista, Model model, ReproduceView reproduceView, MainView mainView) {
        this.model = model;
        this.vista = vista;
        this.timeController = new TimeController(vista, model, this, reproduceView);
        this.timer = new Timer(1000, timeController);
        this.timer.setRepeats(true);
        this.timer.start();
        this.tropa = new Tropa(-1, -1);
        this.reproduceView = reproduceView;
        this.mainView = mainView;
        this.partidaLogic = model.getPartidaLogic();

    }

    private Timer timer;

    /**
     * Métode de MouseListener
     */

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /**
     * Métode de MouseListener
     */

    @Override
    public void mousePressed(MouseEvent e) {

    }

    /**
     * Métode del MouseListener que hem utilitzat per rebre els clicks a la vista del joc
     * @param e  el lloc on hem clicat
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();

        if(source instanceof ImagePanel){
            Pair<Boolean,Integer> tropa  = vista.getFigura((ImagePanel) source);

            if(tropa.getT() == false && tropa.getU() == -1){
                coordenades = vista.getCasella((ImagePanel) source);


                if(coordenades.getT() >= vista.getNumFiles()/2  && !this.tropa.isEmpty()){
                    Tropa[]fila = partidaLogic.getEstatCaselles()[this.coordenades.getT()];
                    int contauns = 0;
                    for (int i = 0; i < fila.length; i++) {
                        if (fila[i].isEmpty()) {
                            contauns++;
                        }
                    }
                    if(contauns >= 2 && fila[coordenades.getU()].isEmpty() && partidaLogic.getJugador().getMonedes() > this.tropa.getCost()){
                        this.tropa.setMaquina(false);
                        partidaLogic.addTropa(coordenades.getT(), coordenades.getU(), this.tropa,true);
                        partidaLogic.getJugador().setMonedes(partidaLogic.getJugador().getMonedes() - this.tropa.getCost());
                        this.tropa = new Tropa(-1, -1);
                    }

                }
            } else {
                this.tropa = partidaLogic.getTropa(tropa.getT(),tropa.getU());
            }
        }

    }

    /**
     * Métode de MouseListener
     */

    @Override
    public void mouseEntered(MouseEvent e) {

    }
    /**
     * Métode de MouseListener
     */

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Métode que calcula la distància mínima entre dues tropes
     * @param tropa la tropa la qual volem calcular la distància mínima d'una altra
     */

    public Tropa distanciaMinTropes(Tropa tropa) {
        Tropa [][] estatCaselles = partidaLogic.getEstatCaselles();
        Integer distanciaMin = Integer.MAX_VALUE;
        Integer distanciaTropes;
        Tropa tropaEnemigaTrobada = new Tropa(-1, -1);
        int numFiles = partidaLogic.getNumFiles(), numColumnes = partidaLogic.getNumColumnes();

        if (tropa.isMaquina()) {
            for (int i = numFiles - 1; i >= 0; i--) {
                for (int j = numColumnes - 1; j >= 0; j--) {
                    if (tropa.isMaquina() != estatCaselles[i][j].isMaquina() && !estatCaselles[i][j].isEmpty()) {
                        distanciaTropes = Math.abs(tropa.getNumFila() - estatCaselles[i][j].getNumFila()) + Math.abs(tropa.getNumColumna() - estatCaselles[i][j].getNumColumna());

                        if (distanciaTropes < distanciaMin) {
                            distanciaMin = distanciaTropes;
                            tropaEnemigaTrobada = estatCaselles[i][j];
                        }

                    }
                }

            }
        } else {
            for (int i = 0; i < numFiles; i++) {
                for (int j = 0; j < numColumnes; j++) {
                    if (tropa.isMaquina() != estatCaselles[i][j].isMaquina() && !estatCaselles[i][j].isEmpty()) {
                        distanciaTropes = Math.abs(tropa.getNumFila() - estatCaselles[i][j].getNumFila()) + Math.abs(tropa.getNumColumna() - estatCaselles[i][j].getNumColumna());

                        if (distanciaTropes < distanciaMin) {
                            distanciaMin = distanciaTropes;
                            tropaEnemigaTrobada = estatCaselles[i][j];
                        }

                    }
                }

            }
        }

        return tropaEnemigaTrobada;
    }


    /**
     * Métode per moure les tropes ofensives
     * @param maquina paràmetre que diu si la tropa es nostra o no
     */
    public void mouTropesOfensives(Boolean maquina) {
        Tropa [][] estatCaselles = partidaLogic.getEstatCaselles();
        Tropa tropaEnemigaPropera;
        int numFiles = partidaLogic.getNumFiles(), numColumnes = partidaLogic.getNumColumnes();

        // Moviment cap adalt i esquerra
        for (int i = 0; i < numFiles; i++) {
            for (int j = 0; j < numColumnes; j++) {
                if (estatCaselles[i][j].isMaquina() == maquina && estatCaselles[i][j].isOffensive() && !estatCaselles[i][j].isEmpty()) {
                    tropaEnemigaPropera = distanciaMinTropes(estatCaselles[i][j]);
                    if (tropaEnemigaPropera.getNumFila() == -1 || tropaEnemigaPropera.getNumColumna() == -1) {
                        continue;
                    }
                    if (i > tropaEnemigaPropera.getNumFila() && estatCaselles[i - 1][j].isEmpty()) {
                        estatCaselles[i - 1][j] = estatCaselles[i][j];
                        estatCaselles[i][j] = new Tropa(i, j);
                        estatCaselles[i - 1][j].setNumFila(i - 1);
                    } else if (i == tropaEnemigaPropera.getNumFila()) {
                        if (j > tropaEnemigaPropera.getNumColumna() && estatCaselles[i][j - 1].isEmpty()) {
                            estatCaselles[i][j - 1] = estatCaselles[i][j];
                            estatCaselles[i][j] = new Tropa(i, j);
                            estatCaselles[i][j - 1].setNumColumna(j - 1);
                        }
                    }
                }
            }

        }

        //Moviment cap abaix i cap a la dreta
        for (int i = numFiles - 1; i >= 0; i--) {
            for (int j = numColumnes - 1; j >= 0; j--) {
                if (estatCaselles[i][j].isMaquina() == maquina && estatCaselles[i][j].isOffensive() && !estatCaselles[i][j].isEmpty()) {
                    tropaEnemigaPropera = distanciaMinTropes(estatCaselles[i][j]);
                    if (i < tropaEnemigaPropera.getNumFila() && estatCaselles[i + 1][j].isEmpty()) {
                        estatCaselles[i + 1][j] = estatCaselles[i][j];
                        estatCaselles[i][j] = new Tropa(i, j);
                        estatCaselles[i + 1][j].setNumFila(i + 1);
                    } else if (i == tropaEnemigaPropera.getNumFila()) {
                        if (j < tropaEnemigaPropera.getNumColumna() && estatCaselles[i][j + 1].isEmpty()) {
                            estatCaselles[i][j + 1] = estatCaselles[i][j];
                            estatCaselles[i][j] = new Tropa(i, j);
                            estatCaselles[i][j + 1].setNumColumna(j + 1);
                        }
                    }
                }
            }

        }
    }


    /**
     * Métode que implementa l'atac de les tropes ofensives
     * @param maquina paràmetre que diu si la tropa es nostra o no
     * @param vista la Gameview del joc
     */
    public void atacaTropesOfensives(boolean maquina, GameView vista) {
        Tropa [][] estatCaselles = partidaLogic.getEstatCaselles();
        Tropa tropaEnemigaPropera;
        int distanciaTropes, numFila, numColumna;

        for (int i = 0; i < estatCaselles.length; i++) {
            for (int j = 0; j < estatCaselles[i].length; j++) {
                if (estatCaselles[i][j].isMaquina() == maquina && estatCaselles[i][j].isOffensive() && !estatCaselles[i][j].isEmpty()) {
                    tropaEnemigaPropera = distanciaMinTropes(estatCaselles[i][j]);
                    if (tropaEnemigaPropera.getNumFila() == -1 || tropaEnemigaPropera.getNumColumna() == -1) {
                        continue;
                    }
                    distanciaTropes = Math.abs(tropaEnemigaPropera.getNumFila() - estatCaselles[i][j].getNumFila()) + Math.abs(tropaEnemigaPropera.getNumColumna() - estatCaselles[i][j].getNumColumna());

                    if (distanciaTropes <= estatCaselles[i][j].getReach()) {
                        tropaEnemigaPropera.setLife(tropaEnemigaPropera.getLife() - estatCaselles[i][j].getDamage());
                        if(tropaEnemigaPropera.isTower()) {
                            if(tropaEnemigaPropera.isMaquina()) {
                                partidaLogic.getMaquina().setVides(tropaEnemigaPropera.getLife());
                            } else{
                                partidaLogic.getJugador().setVides(tropaEnemigaPropera.getLife());
                            }
                        }
                        if (tropaEnemigaPropera.getLife() <= 0) {
                            if(maquina){
                                partidaLogic.getMaquina().setMonedes(partidaLogic.getMaquina().getMonedes() + MONEDESPERKILL);
                            }else{
                                partidaLogic.getJugador().setMonedes(partidaLogic.getJugador().getMonedes() + MONEDESPERKILL);
                            }
                            if(tropaEnemigaPropera.isTower()){
                                if(partidaLogic.isJugant()){
                                    acabarPartida(tropaEnemigaPropera.isMaquina());
                                }else{
                                    partidaLogic.setReproduint(false);
                                    mainView.showMenuCard();
                                }
                                vista.actualitzaVidesJugador(partidaLogic.getJugador().getVides(), partidaLogic.getJugador().getVIDESTOTALS());
                                vista.actualitzaVidesMaquina(partidaLogic.getMaquina().getVides(), partidaLogic.getJugador().getVIDESTOTALS());

                            }
                            numFila = tropaEnemigaPropera.getNumFila();
                            numColumna = tropaEnemigaPropera.getNumColumna();
                            estatCaselles[numFila][numColumna] = new Tropa(numFila, numColumna);
                        }

                    }
                }
            }
        }
    }
    /**
     * Métode que gestiona el que passa al acabar una partida
     * @param isMaquina boolean per controlar si ha guanyat la máquina o l'usuari
     */
    public void acabarPartida(Boolean isMaquina){
        String nomPartida = vista.preguntaGuardarPartida();
        String pathPartida = "./assets/RegistrePartides/" + model.getUserLogic().getNom();
        partidaLogic.setJugant(false);
        if(model.getUserLogic().partidaUnica(pathPartida+ "/" + nomPartida + ".json") && nomPartida != null && !nomPartida.isEmpty()) {

            long milis = System.currentTimeMillis();

            Path pathPartidaX = Paths.get(pathPartida);
            if (Files.notExists(pathPartidaX)) {
                File f1 = new File(pathPartida);
                f1.mkdir();
            }
            pathPartida = pathPartida + "/" + nomPartida + ".json";
            partidaLogic.guardarPartida(pathPartida);
            //Hem de mostrar un panell amb el guanyador.
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm");
            Timestamp date = new Timestamp(milis);
            model.getUserLogic().afegirPartida(isMaquina, pathPartida, date);

            mainView.showMenuCard();
        }else{
            vista.nomPartidaRepetida();
            acabarPartida(isMaquina);
        }


    }


    /**
     * Métode per afegir tropes defensives
     * @param filaEnemic fila on la maquina ha de colocar la tropa
     * @param isMaquina boolea per si es maquina o usuari
     */

    public void addDefensiveTroop(int filaEnemic, boolean isMaquina) {
        int randomcoord;
        Random random = new Random();
        Player player;

        if(isMaquina) {
            player = partidaLogic.getMaquina();
        } else{
            player = partidaLogic.getJugador();
        }

        ArrayList<Pair<Integer, Integer>> coordenadesLliures = this.getAvailablePositions(filaEnemic, isMaquina);

        if(coordenadesLliures.size() > 0){
            randomcoord = random.nextInt(coordenadesLliures.size());
            int rand = random.nextInt(this.partidaLogic.getTropesDef().size());
            Tropa tropa = new Tropa(this.partidaLogic.getTropesDef().get(rand));
            if (tropa.getCost() <= player.getMonedes()) {
                tropa.setMaquina(isMaquina);
                this.partidaLogic.addTropa(coordenadesLliures.get(randomcoord).getT(), coordenadesLliures.get(randomcoord).getU(), tropa,true);
                player.setMonedes(player.getMonedes() - tropa.getCost());

            }
        }

    }
    /**
     * Métode que troba les caselles disponibles per que la máquina fiqui una tropa
     * i les passa a la màquina
     * @param filaenemic fila on ha de posar la tropa la maquina
     * @param isMaquina boolean de usuari o maquina
     * @return coordenadesLliures
     */

    public ArrayList<Pair<Integer, Integer>> getAvailablePositions(int filaenemic, boolean isMaquina) {
        Tropa[][] estatCaselles = this.partidaLogic.getEstatCaselles();
        ArrayList<Pair<Integer, Integer>> coordenadesLliures = new ArrayList<>();
        int start = 0;
        int end = estatCaselles.length / 2;

        if(filaenemic >= 0){
            end = filaenemic;
        }
        if (!isMaquina) {
            start = end;
            end = estatCaselles.length;
        }
        for (int i = start; i < end; i++) {
            int contauns = 0;
            for (int j = 0; j < estatCaselles[i].length; j++) {
                if (estatCaselles[i][j].isEmpty()) {
                    contauns++;
                }
            }
            if (contauns >= 2) {
                for (int j = 0; j < estatCaselles[i].length; j++) {
                    if (estatCaselles[i][j].isEmpty()) {
                        coordenadesLliures.add(new Pair<Integer, Integer>(i, j));
                    }
                }
            }
        }
        return coordenadesLliures;
    }

    /**
     * Métode per que l'usuari afegeixi una tropa al tauler
     * @param isMaquina boolea per si es maquina o no
     */

    public void addOfensiveTroop(boolean isMaquina){
        int randomcoord;
        Random random = new Random();
        Player player;

        if(isMaquina) {
            player = partidaLogic.getMaquina();
        } else{
            player = partidaLogic.getJugador();
        }
        ArrayList<Pair<Integer, Integer>> coordenadesLliures = this.getAvailablePositions(-1, isMaquina);

        if(coordenadesLliures.size() > 0){
            randomcoord = random.nextInt(coordenadesLliures.size());
            int rand = random.nextInt(this.partidaLogic.getTropesOf().size());
            Tropa tropa = new Tropa(this.partidaLogic.getTropesOf().get(rand));
            if (tropa.getCost() <= player.getMonedes()) {
                tropa.setMaquina(isMaquina);
                this.partidaLogic.addTropa(coordenadesLliures.get(randomcoord).getT(), coordenadesLliures.get(randomcoord).getU(), tropa, true);
                player.setMonedes(player.getMonedes() - tropa.getCost());
            }
        }
    }

    /**
     * Carrega els moviments enmagatzemants al fitxer de les partides a reproduir
     * @param moviments reb els moviments del fitxer on està guardada la partida
     */
    public void carregarMoviments(ArrayList<Moviment> moviments){
        int indexMoviment=0;
        ReproducerController reproducerController;
        Timer timer;
        Tropa tropa;
        while(indexMoviment < moviments.size() ){
            Moviment moviment = moviments.get(indexMoviment);

            if(moviment.getOffensive()){
                tropa = new Tropa(partidaLogic.getTropesOf().get(moviment.getTropa()));
            }else{
                tropa = new Tropa(partidaLogic.getTropesDef().get(moviment.getTropa()));
            }
            tropa.setMaquina(moviment.getMaquina());

            reproducerController = new ReproducerController(partidaLogic, moviment, tropa);
            timer = new Timer((int)moviment.getMilisegons(), reproducerController);
            timer.setRepeats(false);
            timer.start();

            indexMoviment++;
        }

    }

}
