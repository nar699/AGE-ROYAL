package Presentation.View;

import Presentation.Controller.GameController;
import Business.Pair;
import Business.Entities.Tropa;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Aquesta es la vista principal del joc, que no es més que un array de ImagePanels, als que se li aplica un MouseController, i dos jPanels amb
 * els ImagePanels de les tropes, que tenen un altre MouseController. A la dreta hi ha un seguit de JPanels, JLabels i JProgressBars per
 * fer un seguiment de les estadístiques de la partida.
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */

public class GameView extends JPanel {
    private GameController gameController;
    private JPanel jpFons;
    private JPanel jpPartida;
    private ArrayList<ArrayList<ImagePanel>> caselles;
    private JPanel jpWest;
    private JPanel jpEast;
    private JPanel jTime;
    private JPanel jLives;
    private JPanel jTroops;
    private JPanel jMoney;
    private JPanel jpOfensive;
    private JPanel jpDefensive;
    private JLabel moneyAmount;
    private JProgressBar allyLife;
    private JProgressBar enemyLife;
    private JProgressBar allyTroops;
    private JProgressBar enemyTroops;
    private JLabel numberAllyLife;
    private JLabel numberEnemyLife;
    private JLabel numberAllyTroops;
    private JLabel numberEnemyTroops;
    private JLabel jlOfensiveTroops;
    private JLabel jlDefensiveTroops;
    private Dimension dimension;
    private ArrayList<ImagePanel> ipOfensiveTroops;
    private ArrayList<ImagePanel> ipDefensiveTroops;
    private JLabel timeAmount;
    private int numFiles;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("ss");
    private int numColumnes;
    private static final int TROOPS_PANEL_HEIGHT = 100;
    private static final int TROOPS_NUMBER = 2;
    private final String[] ofImagePath;
    private final String[] defImagePath;
    private static final String TOWER_PATH_ALLY = "assets/Images/Torre_aliada.png";
    private static final String TOWER_PATH_ENEMY = "assets/Images/Torre_enemiga.png";
    private static final String EMPTY_PATH = "assets/Images/empty.png";

    /**
     * Constructor de la gameView
     * @param numFiles el nombre de files
     * @param numColumnes el nombre de columnes
     * @param ofImagePath els paths a les imatges de les tropes ofensives
     * @param defImagePath els paths a les imatges de les tropes defensives
     */

    public GameView(int numFiles, int numColumnes, String[] ofImagePath, String[] defImagePath) {
        this.ofImagePath = ofImagePath;
        this.defImagePath = defImagePath;
        this.numFiles = numFiles;
        this.numColumnes = numColumnes;
        this.dimension = super.getToolkit().getScreenSize();
        //dimension.setSize(Math.round(dimension.getWidth()*0.75), Math.round(dimension.getHeight()*0.75));
        this.setLayout(new BorderLayout());
        jpFons = new ImagePanel("assets/Images/fons_game.png"); //posar img
        jpFons.setLayout(new BorderLayout());

        jpWest = new JPanel(new GridBagLayout());
        jpWest.setOpaque(false);

        jpEast = new JPanel();
        jpEast.setLayout(new BoxLayout(jpEast, BoxLayout.Y_AXIS));
        jpEast.setOpaque(false);

        jpPartida = new JPanel(new GridLayout(numFiles, numColumnes));
        caselles = new ArrayList<ArrayList<ImagePanel>>(numFiles);
        for (int i = 0; i < numFiles; i++) {
            ArrayList<ImagePanel> casellesFila = new ArrayList<>();
            for (int j = 0; j < numColumnes; j++) {
                ImagePanel casella = new ImagePanel();
                casella.setBackground(Color.WHITE);
                casella.setBorder(BorderFactory.createBevelBorder(0, Color.BLACK, Color.BLACK));
                casella.setPreferredSize(new Dimension(5, 5));
                jpPartida.add(casella);
                casellesFila.add(casella);

            }
            caselles.add(casellesFila);
        }
        jpPartida.setPreferredSize(new Dimension((int) Math.round(dimension.getWidth() * 0.5), (int) Math.round(dimension.getHeight() * 0.5)));

        GridBagConstraints constraints = new GridBagConstraints();


        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 0, 10, 0);
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.fill = GridBagConstraints.VERTICAL;

        jpWest.add(jpPartida, constraints);


        jlOfensiveTroops = new JLabel("Ofensive Troops");
        jlOfensiveTroops.setFont(new Font("Helvetica", Font.ITALIC, 20));
        jlOfensiveTroops.setMaximumSize(new Dimension(200, 100));
        jpOfensive = new JPanel(new FlowLayout());
        ipOfensiveTroops = new ArrayList<ImagePanel>(ofImagePath.length);
        for (int i = 0; i < ofImagePath.length; i++) {
            ImagePanel ofensiveTroop = new ImagePanel(ofImagePath[i]);
            ofensiveTroop.setPreferredSize(new Dimension(90, 90));
            jpOfensive.add(ofensiveTroop);
            ipOfensiveTroops.add(ofensiveTroop);
        }
        jpOfensive.setPreferredSize(new Dimension((int) Math.round(dimension.getWidth() * 0.5), TROOPS_PANEL_HEIGHT));
        jpOfensive.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));

        jlDefensiveTroops = new JLabel("Defensive Troops");
        jlDefensiveTroops.setFont(new Font("Helvetica", Font.ITALIC, 20));
        jlDefensiveTroops.setMaximumSize(new Dimension(200, 100));
        jpDefensive = new JPanel(new FlowLayout());

        ipDefensiveTroops = new ArrayList<ImagePanel>(defImagePath.length);
        for (int i = 0; i < defImagePath.length; i++) {
            ImagePanel defensiveTroop = new ImagePanel(defImagePath[i]);
            defensiveTroop.setPreferredSize(new Dimension(90, 90));
            jpDefensive.add(defensiveTroop);
            ipDefensiveTroops.add(defensiveTroop);
        }

        jpDefensive.setPreferredSize(new Dimension((int) Math.round(dimension.getWidth() * 0.5), TROOPS_PANEL_HEIGHT));
        jpDefensive.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GRAY));


        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        jpWest.add(jlOfensiveTroops, constraints);

        constraints.gridy = 2;
        jpWest.add(jpOfensive, constraints);

        constraints.gridy = 3;
        jpWest.add(jlDefensiveTroops, constraints);

        constraints.gridy = 4;
        jpWest.add(jpDefensive, constraints);

        jpFons.setPreferredSize(dimension);


        JLabel lifelabel = new JLabel("Lives:      ");
        lifelabel.setFont(new Font("Arial", Font.ITALIC, 18));
        lifelabel.setAlignmentX(CENTER_ALIGNMENT);

        jLives = new JPanel();
        jLives.setLayout(new BoxLayout(jLives, BoxLayout.Y_AXIS));
        jLives.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));

        enemyLife = new JProgressBar();
        enemyLife.setValue(100);
        enemyLife.setBackground(Color.red);

        allyLife = new JProgressBar();
        allyLife.setValue(100);
        allyLife.setBackground(Color.blue);

        numberEnemyLife = new JLabel("  0  ");
        numberEnemyLife.setFont(new Font("Arial", Font.ITALIC, 30));
        numberEnemyLife.setAlignmentX(CENTER_ALIGNMENT);

        numberAllyLife = new JLabel("  0  ");
        numberAllyLife.setFont(new Font("Arial", Font.ITALIC, 30));
        numberAllyLife.setAlignmentX(CENTER_ALIGNMENT);


        jLives.add(enemyLife);
        jLives.add(numberEnemyLife);
        //jLives.add(Box.createRigidArea(new Dimension(100, 5)));
        jLives.add(allyLife);
        jLives.add(numberAllyLife);


        JLabel troopsLabel = new JLabel("Troops      ");
        troopsLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        troopsLabel.setAlignmentX(CENTER_ALIGNMENT);

        jTroops = new JPanel();
        jTroops.setLayout(new BoxLayout(jTroops, BoxLayout.Y_AXIS));
        jTroops.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));


        enemyTroops = new JProgressBar();
        enemyTroops.setValue(30);
        enemyTroops.setBackground(Color.red);

        allyTroops = new JProgressBar();
        allyTroops.setValue(70);
        allyTroops.setBackground(Color.blue);


        numberAllyTroops = new JLabel("  0  ");
        numberAllyTroops.setFont(new Font("Arial", Font.ITALIC, 30));
        numberAllyTroops.setAlignmentX(CENTER_ALIGNMENT);

        numberEnemyTroops = new JLabel("  0  ");
        numberEnemyTroops.setFont(new Font("Arial", Font.ITALIC, 30));
        numberEnemyTroops.setAlignmentX(CENTER_ALIGNMENT);

        jTroops.add(enemyTroops);
        jTroops.add(numberEnemyTroops);
        jTroops.add(allyTroops);
        jTroops.add(numberAllyTroops);


        JLabel moneylabel = new JLabel("Money        ");
        moneylabel.setFont(new Font("Arial", Font.ITALIC, 18));
        moneylabel.setAlignmentX(CENTER_ALIGNMENT);

        moneyAmount = new JLabel("  30  ");
        moneyAmount.setFont(new Font("Arial", Font.ITALIC, 30));
        moneyAmount.setAlignmentX(CENTER_ALIGNMENT);

        timeAmount = new JLabel("  00  ");
        timeAmount.setFont(new Font("Arial", Font.ITALIC, 30));
        timeAmount.setAlignmentX(CENTER_ALIGNMENT);


        jMoney = new JPanel();
        jMoney.setLayout(new BoxLayout(jMoney, BoxLayout.Y_AXIS));
        jMoney.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));

        jMoney.add(moneyAmount);


        jTime = new JPanel();
        jTime.setLayout(new BoxLayout(jTime, BoxLayout.Y_AXIS));
        jTime.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));

        jTime.add(timeAmount);

        JLabel timeLabel = new JLabel("Time:             ");
        timeLabel.setFont(new Font("Arial", Font.ITALIC, 18));
        timeLabel.setAlignmentX(CENTER_ALIGNMENT);


        jpEast.setPreferredSize(new Dimension(600, 0));
        jpEast.setBorder(BorderFactory.createMatteBorder(1, 8, 1, 8, Color.BLACK));

        jpEast.add(Box.createRigidArea(new Dimension(500, 50)));
        jpEast.add(timeLabel);
        jpEast.add(jTime);
        jpEast.add(Box.createRigidArea(new Dimension(500, 50)));
        jpEast.add(lifelabel);
        jpEast.add(jLives);
        jpEast.add(Box.createRigidArea(new Dimension(500, 50)));
        jpEast.add(troopsLabel);
        jpEast.add(jTroops);
        jpEast.add(Box.createRigidArea(new Dimension(500, 50)));
        jpEast.add(moneylabel);
        jpEast.add(jMoney);


        jpFons.add(jpWest, BorderLayout.WEST);
        jpFons.add(jpEast, BorderLayout.EAST);
        this.add(jpFons);
    }

    /**
     * Métode que aplica el MouseListener a totes les caselles.
     * @param gameController el controller de la partida
     */


    public void addGameController(GameController gameController) {
        this.gameController = gameController;

        for (int i = 0; i < numFiles; i++) {
            ArrayList<ImagePanel> casellesFila = caselles.get(i);
            for (int j = 0; j < numColumnes; j++) {
                casellesFila.get(j).addMouseListener(gameController);
            }
        }

        for (int i = 0; i < TROOPS_NUMBER; i++) {
            ipOfensiveTroops.get(i).addMouseListener(gameController);
            ipDefensiveTroops.get(i).addMouseListener(gameController);
        }
    }

    /**
     * Métode que actualitza la JLabel encarregada de mostrar el temps que ha transcorregut a la partida
     * @param time el temps actual
     */

    public void updateTimer(Integer time) {
        Integer minut;
        Integer segons;
        minut = time / 60;
        segons = time % 60;
        DecimalFormat format = new DecimalFormat("00");
        timeAmount.setText(format.format(minut) + ":" + format.format(segons));
    }

    /**
     * Métode que actualitza el JLabel encareegat dels diners de la partida
     * @param money els diners actuals
     */

    public void updateMoney(Integer money) {

        moneyAmount.setText("  " + money.toString() + "  ");
    }

    /**
     * Métode que retorna la casella que s'ha clicat exclusivament del tauler
     * @param panel el panel que s'ha clicat
     * @return el parell de coordenades que ocupa el panel
     */

    public Pair getCasella(JPanel panel) {

        for (int i = 0; i < caselles.size(); i++) {
            ArrayList<ImagePanel> fila = caselles.get(i);
            if (fila.indexOf(panel) >= 0) { // mirem si a la fila hi ha el panel que s'ha clicat (sino retorna -1)
                Pair<Integer, Integer> coordenades = new Pair<Integer, Integer>(i, fila.indexOf(panel));
                return (coordenades);
            }
        }
        Pair<Integer, Integer> coordenades = new Pair<Integer, Integer>(-1, -1);
        return (coordenades);
    }

    /**
     * Métode que retorna la tropa que s'ha triat, exclusivament dels ImagePanels on es veuen les tropes que podem invocar
     * @param panel el panel on està la tropa
     * @return el parell de coordenades que ocupa el panel
     */


    public Pair getFigura(ImagePanel panel) {
        if (ipOfensiveTroops.indexOf(panel) >= 0) {
            Pair<Boolean, Integer> tropaOfensiva = new Pair<>(true, ipOfensiveTroops.indexOf(panel));
            return (tropaOfensiva);
        }
        if (ipDefensiveTroops.indexOf(panel) >= 0) {
            Pair<Boolean, Integer> tropaDefensiva = new Pair<>(false, ipDefensiveTroops.indexOf(panel));
            return (tropaDefensiva);
        }
        return new Pair<Boolean, Integer>(false, -1);

    }

    /**
     * Getter de nombre de files
     * @return el nombre de files
     */

    public int getNumFiles() {
        return numFiles;
    }


    /**
     * Canvia la vida del jugador
     * @param actual la vida actual
     * @param max la vida maxima
     */
    public void actualitzaVidesJugador(int actual, int max) {
        int var = (int) Math.ceil(Double.valueOf(actual) / Double.valueOf(max) * 100);
        allyLife.setValue(var);
        numberAllyLife.setText("  " + actual + "  ");
    }

    /**
     * Canvia la vida de la màquina
     * @param actual la vida actual de la maquina
     * @param max la vida maxima de la maquina
     */
    public void actualitzaVidesMaquina(int actual, int max) {
        int var = (int) Math.ceil(Double.valueOf(actual) / Double.valueOf(max) * 100);
        enemyLife.setValue(var);
        numberEnemyLife.setText("  " + actual + "  ");
    }

    /**
     * Actualitza el comptador de tropes de la maquina i l'usuari en la partida
     * @param numTropesMaquina nombre de tropes de la maquina
     * @param numTropesJugador nombre de tropes del usuari
     */
    public void actualitzaTropes(int numTropesMaquina,int numTropesJugador) {
        numTropesMaquina=numTropesMaquina-1;
        numTropesJugador=numTropesJugador-1;
        int var = (int) Math.ceil(Double.valueOf(numTropesMaquina) / Double.valueOf(numTropesMaquina+numTropesJugador) * 100);
        enemyTroops.setValue(var);
        numberEnemyTroops.setText("  " + numTropesMaquina + "  ");
        var = (int) Math.ceil(Double.valueOf(numTropesJugador) / Double.valueOf(numTropesMaquina+numTropesJugador) * 100);
        allyTroops.setValue(var);
        numberAllyTroops.setText("  " + numTropesJugador + "  ");
        numberEnemyTroops.setText("  " + numTropesMaquina + "  ");
    }

    /**
     * Text que surt al finalitzar la partida. També reb el nom que l'usuari posa a la partida per guardarla
     * @return el nom escollit per l'usuari
     */
    public String preguntaGuardarPartida(){
        String nomPartida = JOptionPane.showInputDialog("Escriu el nom de la partida:");

        return nomPartida;
    }

    /**
     * Missatge que surt si el nom de la partida ja es trobava en la base de dades
     */
    public void nomPartidaRepetida(){
        JOptionPane.showMessageDialog(this,"Partida repetida","Error Register",JOptionPane.ERROR_MESSAGE);
    }

    /**
     * rebent un nou array de dades cada cop, aquest métode s'encarrega d'actualitzar els ImagePanels de les caselles del tauler
     * @param casellesModel totes les caselles
     */
    public void actualitzarCaselles(Tropa[][] casellesModel) {

        for (int i = 0; i < casellesModel.length; i++) {
            ArrayList<ImagePanel> fila = this.caselles.get(i);
            for (int j = 0; j < casellesModel[i].length; j++) {
                if (casellesModel[i][j].isEmpty()) {
                    fila.get(j).setBackground(EMPTY_PATH);
                } else if (casellesModel[i][j].isTower()) {
                    if(i == 0) {
                        fila.get(j).setBackground(TOWER_PATH_ENEMY);
                    } else {
                        fila.get(j).setBackground(TOWER_PATH_ALLY);
                    }
                } else if (casellesModel[i][j].isOffensive() && casellesModel[i][j].isMaquina()) {
                    fila.get(j).setColorBackground(Color.RED);
                    fila.get(j).setBackground(ofImagePath[casellesModel[i][j].getTropa()]);
                } else if (!casellesModel[i][j].isOffensive() && casellesModel[i][j].isMaquina()) {
                    fila.get(j).setColorBackground(Color.RED);
                    fila.get(j).setBackground(defImagePath[casellesModel[i][j].getTropa()]);
                } else if (!casellesModel[i][j].isOffensive() && !casellesModel[i][j].isMaquina()) {
                    fila.get(j).setColorBackground(Color.BLUE);
                    fila.get(j).setBackground(defImagePath[casellesModel[i][j].getTropa()]);
                } else if (casellesModel[i][j].isOffensive() && !casellesModel[i][j].isMaquina()) {
                    fila.get(j).setColorBackground(Color.BLUE);
                    fila.get(j).setBackground(ofImagePath[casellesModel[i][j].getTropa()]);
                }
            }

        }

    }
}


