package Presentation.View;

import Presentation.Controller.GameController;
import Presentation.Controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Vista principal del projecte, que fa un extends de JFrame, aquí, és on es declara el cardLayout que conté totes les vistes del projecte
 * també hi ha totes les vistes que no estàn relacionades amb el tauler ni el joc, que estàn creades completament aquí, en la seva
 * respectiva funció, com configureLogin(); o configureMenu();
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class MainView extends JFrame {
    private JButton jLogin;
    private JButton jRegister;
    private JButton jSubmitLogin;
    private JButton jSubmitRegister;
    private JButton jbackbuttonL;
    private JButton jbackbuttonR;
    private JButton jstartGame;
    private JButton jstartRanking;
    private JButton jstartReplays;
    private JButton jstartOptions;
    private JButton jLogout;
    private JButton jDeleteUser;
    private JButton jbackOptions;

    private JTextField loginName;
    private JPasswordField loginPassword;

    private JTextField registerName;
    private JTextField registerEmail;
    private JPasswordField registerPassword;
    private JPasswordField registerPassword2;


    public static final String BTN_SUBMIT_REGISTER = "BTN_SUBMIT_REGISTER";
    public static final String BTN_SUBMIT_LOGIN = "BTN_SUBMIT_LOGIN";
    public static final String BTN_LOGIN = "BTN_LOGIN";
    public static final String BTN_REGISTER = "BTN_REGISTER";
    public static final String BTN_BACK_LOGIN = "BTN_BACK_LOGIN";
    public static final String BTN_BACK_REGISTER = "BTN_BACK_REGISTER";
    public static final String BTN_START_GAME = "BTN_START_GAME";
    public static final String BTN_START_RANKING = "BTN_START_RANKING";
    public static final String BTN_START_REPLAYS = "BTN_START_REPLAYS";
    public static final String BTN_START_OPTIONS = "BTN_START_OPTIONS";
    public static final String BTN_BACK_OPTIONS = "BTN_BACK_OPTIONS";
    public static final String BTN_LOGOUT = "BTN_LOGOUT";
    public static final String BTN_DELETE_USER = "BTN_DELETE_USER";

    private static final String CARD_LOGIN = "CARD_LOGIN";
    private static final String CARD_REGISTER = "CARD_REGISTER";
    private static final String CARD_LOGIN_REGISTER = "CARD_LOGIN_REGISTER";
    private static final String CARD_MENU = "CARD_MENU";
    private static final String CARD_GAME = "CARD_GAME";
    private static final String CARD_REPRO = "CARD_REPRO";
    private static final String CARD_LOGOUT = "CARD_LOGOUT";
    private static final String CARD_HISTORIAL = "CARD_HISTORIAL";
    private static final String CARD_RANKING = "CARD_RANKING";

    private final CardLayout cardManager;
    private GameView gameView;
    private ReproduceView reproView;



    private HistorialView historialView;
    private RankingView rankingView;

    /**
     * Constructor de la MainWindow
     * @param defImagePath Es el panel on es veuen les tropes defensives
     * @param numColumnes Nombre de columnes del tauler
     * @param numFiles Nombre de files del tauler
     * @param ofImagePath Es el panel on es veuen les tropes defensives
     */

    public MainView(int numFiles, int numColumnes, String[] ofImagePath, String[] defImagePath){
        cardManager = new CardLayout();
        getContentPane().setLayout(cardManager);
        setVisible(true);


        configureLoginRegister();
        configureLogin();
        configureRegister();

        configureWindow();
        configureMenuView();
        configureGameView(numFiles, numColumnes, ofImagePath, defImagePath);
        configureReproduceView(numFiles, numColumnes, ofImagePath, defImagePath);
        configureLogout();
    }

    public GameView getGameView() {
        return gameView;
    }

    /**
     * Funcions per obtenir la informació del register i el login
     */

    public String retornaContra(){
        return new String(registerPassword.getPassword());
    }
    public String retornaConfirmation(){return new String(registerPassword2.getPassword());}
    public String retornaEmail(){return registerEmail.getText();}
    public String retornaNomRegister(){ return registerName.getText();}



    public String retornaNomLogin(){return loginName.getText();}
    public String retornaContraLogin(){return new String(loginPassword.getPassword());}

    /**
     * Configura el JFrame
     */
    private void configureWindow(){
        setTitle("AgeRoyale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1920,1080);
        setLocationRelativeTo(null);
    }

    /**
     * Afegeix la vista del tauler del joc
     */
    public void addGameController(GameController gameController){
        gameView.addGameController(gameController);
    }

    private void configureGameView(int numFiles, int numColumnes, String[] ofImagePath, String[] defImagePath){
        gameView = new GameView(numFiles, numColumnes, ofImagePath, defImagePath);
        this.getContentPane().add(CARD_GAME, gameView);

    }

    /**
     * Vista de la partida en format reproducció
     */
    private void configureReproduceView(int numFiles, int numColumnes, String[] ofImagePath, String[] defImagePath){
        reproView = new ReproduceView(numFiles, numColumnes, ofImagePath, defImagePath);
        this.getContentPane().add(CARD_REPRO, reproView);

    }
    /**
     * Vista del historial, es troba en el menu career
     */
    public void configureHistorialView(String[][] dades,ActionListener actionListener,boolean reproduir){
        historialView = new HistorialView(dades,reproduir);
        historialView.registerController(actionListener);
        this.getContentPane().add(CARD_HISTORIAL, historialView);

    }
    /**
     * Vista del ranking
     */
    public void configureRankingView(String[][] dades2,ActionListener actionListener){
        rankingView = new RankingView(dades2);
        rankingView.registerController(actionListener);
        this.getContentPane().add(CARD_RANKING, rankingView);

    }
    /**
     * Vista del menu principal del joc, formada amb un GridLayout de JPanels
     */
    private void configureMenuView(){

        JPanel panel = new ImagePanel("assets/main_menu.png");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setOpaque(false);

        panel.setLayout(new BorderLayout());

        panel.add(buttonPanel, BorderLayout.CENTER);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 60, 100, 0);
        constraints.anchor = GridBagConstraints.NORTH;



        jstartReplays = new JButton("Career");
        jstartReplays.setActionCommand(BTN_START_REPLAYS);
        jstartReplays.setFont(new Font("Helvetica", Font.ITALIC, 20));

        jstartGame = new JButton("New Game");
        jstartGame.setActionCommand(BTN_START_GAME);
        jstartGame.setFont(new Font("Helvetica", Font.ITALIC, 20));

        jstartRanking = new JButton("Ranking");
        jstartRanking.setActionCommand(BTN_START_RANKING);
        jstartRanking.setFont(new Font("Helvetica", Font.ITALIC, 20));

        jstartOptions = new JButton("Options");
        jstartOptions.setActionCommand(BTN_START_OPTIONS);
        jstartOptions.setFont(new Font("Helvetica", Font.ITALIC, 20));


        jstartOptions.setPreferredSize(new Dimension(400,200));
        jstartRanking.setPreferredSize(new Dimension (400,200));
        jstartGame.setPreferredSize(new Dimension(400,200));
        jstartReplays.setPreferredSize(new Dimension (400,200));


        constraints.anchor = GridBagConstraints.FIRST_LINE_START;
        buttonPanel.add(jstartGame, constraints);

        constraints.gridx = 1;
        constraints.anchor = GridBagConstraints.FIRST_LINE_END;
        buttonPanel.add(jstartRanking, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LAST_LINE_START;
        buttonPanel.add(jstartReplays, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.anchor = GridBagConstraints.LAST_LINE_END;
        buttonPanel.add(jstartOptions, constraints);


        getContentPane().add(panel, CARD_MENU);


    }





    /**
     * Primera vista que veien al iniciar el programa
     */
    private void configureLoginRegister(){

        ImageIcon space0 = new ImageIcon("assets/view1.png");

        JLabel label = new JLabel("",space0,JLabel.CENTER);
        label.setBounds(0,0,1920,1080);
        label.setLayout(new BoxLayout(label,BoxLayout.Y_AXIS));


        jLogin = new JButton("Login");
        jLogin.setActionCommand(BTN_LOGIN);
        jLogin.setAlignmentX(CENTER_ALIGNMENT);
        jLogin.setFont(new Font("Helvetica", Font.ITALIC, 20));

        jRegister = new JButton("Register");
        jRegister.setActionCommand(BTN_REGISTER);
        jRegister.setAlignmentX(CENTER_ALIGNMENT);
        jRegister.setFont(new Font("Helvetica", Font.ITALIC, 20));


        jLogin.setMaximumSize(new Dimension(200,100));
        jRegister.setMaximumSize(new Dimension (200,100));


        label.add(Box.createRigidArea(new Dimension(100,100)));
        label.add(Box.createRigidArea(new Dimension(100,200)));
        label.add(jLogin);
        label.add(Box.createRigidArea(new Dimension(100,100)));
        label.add(jRegister);



        getContentPane().add(label, CARD_LOGIN_REGISTER);

    }

    /**
     * Passa un actionListener a tots els botons del programa que no estàn al joc
     */


    public void registerController(ActionListener actionListener) {
        jLogin.addActionListener(actionListener);
        jRegister.addActionListener(actionListener);
        jbackbuttonL.addActionListener(actionListener);
        jbackbuttonR.addActionListener(actionListener);
        jSubmitRegister.addActionListener(actionListener);
        jSubmitLogin.addActionListener(actionListener);
        jstartGame.addActionListener(actionListener);
        jstartRanking.addActionListener(actionListener);
        jstartOptions.addActionListener(actionListener);
        jstartReplays.addActionListener(actionListener);
        jLogout.addActionListener(actionListener);
        jDeleteUser.addActionListener(actionListener);
        jbackOptions.addActionListener(actionListener);

    }
    /**
     * Vista del login
     */

    private void configureLogin(){
        ImageIcon spaceLogin = new ImageIcon("assets/login.png");
        JLabel label2 = new JLabel("",spaceLogin,JLabel.CENTER);
        label2.setBounds(0,0,1920,1080);
        label2.setLayout(new BoxLayout(label2,BoxLayout.Y_AXIS));


        JLabel name = new JLabel("User Name: ");
        name.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        name.setAlignmentX(CENTER_ALIGNMENT);
        name.setForeground(Color.BLACK);


        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        password.setAlignmentX(CENTER_ALIGNMENT);
        password.setForeground(Color.BLACK);


        jSubmitLogin = new JButton("Submit");
        jSubmitLogin.setAlignmentX(CENTER_ALIGNMENT);
        jSubmitLogin.setMaximumSize(new Dimension(100,50));
        jSubmitLogin.setActionCommand(BTN_SUBMIT_LOGIN);

        loginName = new JTextField();
        loginName.setAlignmentX(CENTER_ALIGNMENT);
        loginName.setColumns(1);
        loginName.setMaximumSize(new Dimension(250,40));
        loginName.setFont(new Font("Arial", Font.PLAIN, 28));

        loginPassword = new JPasswordField();
        loginPassword.setAlignmentX(CENTER_ALIGNMENT);
        loginPassword.setColumns(1);
        loginPassword.setMaximumSize(new Dimension(250,40));

        jbackbuttonL = new JButton("Back");
        jbackbuttonL.setActionCommand(BTN_BACK_LOGIN);
        jbackbuttonL.setAlignmentX(CENTER_ALIGNMENT);
        jbackbuttonL.setMaximumSize(new Dimension(100,50));


        label2.add(Box.createRigidArea(new Dimension(1920,110)));
        label2.add(Box.createRigidArea(new Dimension(100,100)));
        label2.add(name);
        label2.add(loginName);
        label2.add(Box.createRigidArea(new Dimension(100,50)));
        label2.add(password);
        label2.add(loginPassword);
        label2.add(Box.createRigidArea(new Dimension(100,50)));
        label2.add(jSubmitLogin);
        label2.add(Box.createRigidArea(new Dimension(0,50)));
        label2.add(jbackbuttonL);


        getContentPane().add(label2, CARD_LOGIN);
    }

    /**
     * Vista del register
     */

    public void configureRegister(){

        ImageIcon spaceRegister = new ImageIcon("assets/register.png");
        JLabel label3 = new JLabel("",spaceRegister,JLabel.CENTER);
        label3.setBounds(0,0,1920,1080);
        label3.setLayout(new BoxLayout(label3,BoxLayout.Y_AXIS));

        JLabel name = new JLabel("User Name: ");
        name.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        name.setAlignmentX(LEFT_ALIGNMENT);
        name.setForeground(Color.WHITE);

        JLabel password = new JLabel("Password: ");
        password.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        password.setAlignmentX(LEFT_ALIGNMENT);
        password.setForeground(Color.WHITE);

        JLabel password2 = new JLabel("Confirm Password: ");
        password2.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        password2.setAlignmentX(LEFT_ALIGNMENT);
        password2.setForeground(Color.WHITE);

        JLabel email = new JLabel("Email: ");
        email.setFont(new Font("Times New Roman", Font.ITALIC, 30));
        email.setAlignmentX(LEFT_ALIGNMENT);
        email.setForeground(Color.WHITE);

        registerName = new JTextField();
        registerName.setAlignmentX(CENTER_ALIGNMENT);
        registerName.setFont(new Font("Helvetica", Font.ITALIC, 20));
        registerName.setMaximumSize(new Dimension(600,50));

        registerEmail = new JTextField();
        registerEmail.setAlignmentX(CENTER_ALIGNMENT);
        registerEmail.setFont(new Font("Helvetica", Font.ITALIC, 20));
        registerEmail.setMaximumSize(new Dimension(600,50));

        registerPassword = new JPasswordField();
        registerPassword.setAlignmentX(CENTER_ALIGNMENT);
        registerPassword.setMaximumSize(new Dimension(600,50));
        registerPassword.setFont(new Font("Helvetica", Font.ITALIC, 20));

        registerPassword2 = new JPasswordField();
        registerPassword2.setAlignmentX(CENTER_ALIGNMENT);
        registerPassword2.setMaximumSize(new Dimension(600,50));
        registerPassword2.setFont(new Font("Helvetica", Font.ITALIC, 20));

        jSubmitRegister = new JButton("Submit");
        jSubmitRegister.setActionCommand(BTN_SUBMIT_REGISTER);
        jSubmitRegister.setAlignmentX(LEFT_ALIGNMENT);
        jSubmitRegister.setFont(new Font("Helvetica", Font.ITALIC, 20));

        jbackbuttonR = new JButton("Back");
        jbackbuttonR.setActionCommand(BTN_BACK_REGISTER);
        jbackbuttonR.setAlignmentX(LEFT_ALIGNMENT);
        jbackbuttonR.setFont(new Font("Helvetica", Font.ITALIC, 20));

        label3.add(Box.createRigidArea(new Dimension(0,180)));
        label3.add(name);
        label3.add(Box.createRigidArea(new Dimension(0,10)));
        label3.add(registerName);
        label3.add(Box.createRigidArea(new Dimension(0,10)));
        label3.add(email);
        label3.add(Box.createRigidArea(new Dimension(0,10)));
        label3.add(registerEmail);
        label3.add(Box.createRigidArea(new Dimension(0,10)));
        label3.add(password);
        label3.add(Box.createRigidArea(new Dimension(0,10)));
        label3.add(registerPassword);
        label3.add(Box.createRigidArea(new Dimension(0,10)));
        label3.add(password2);
        label3.add(Box.createRigidArea(new Dimension(0,10)));
        label3.add(registerPassword2);
        label3.add(Box.createRigidArea(new Dimension(0,30)));
        label3.add(jSubmitRegister);
        label3.add(Box.createRigidArea(new Dimension(0,80)));
        label3.add(jbackbuttonR);

        label3.setBorder(BorderFactory.createEmptyBorder(0,150,0,0));
        getContentPane().add(label3, CARD_REGISTER);
    }
    /**
     * Vista del logout
     */

    public void configureLogout() {

        JPanel panel = new ImagePanel("assets/options_view.png");

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        jLogout = new JButton("Logout");
        jLogout.setActionCommand(BTN_LOGOUT);
        jLogout.setAlignmentX(CENTER_ALIGNMENT);
        jLogout.setMaximumSize(new Dimension(200,250));


        jDeleteUser = new JButton("Delete Account");
        jDeleteUser.setActionCommand(BTN_DELETE_USER);
        jDeleteUser.setAlignmentX(CENTER_ALIGNMENT);
        jDeleteUser.setMaximumSize(new Dimension(200,250));

        jbackOptions = new JButton("Back to Menu");
        jbackOptions.setActionCommand(BTN_BACK_OPTIONS);
        jbackOptions.setAlignmentX(CENTER_ALIGNMENT);
        jbackOptions.setMaximumSize(new Dimension(125,50));

        panel.add(Box.createRigidArea(new Dimension(100,350)));
        panel.add(jLogout);
        panel.add(Box.createRigidArea(new Dimension(100,100)));
        panel.add(jDeleteUser);
        panel.add(Box.createRigidArea(new Dimension(100,150)));
        panel.add(jbackOptions);
        panel.add(Box.createRigidArea(new Dimension(100,200)));

        getContentPane().add(panel, CARD_LOGOUT);

    }

    /**
     * Métode per fer el JFrame visible
     */



    public void start(){
        setVisible(true);
    }

    /**
     * Card que mostra el login
     */

    public void showLoginCard(){
        cardManager.show(getContentPane(), CARD_LOGIN);
    }
    /**
     * Card que mostra el registre
     */

    public void showRegisterCard(){
        cardManager.show(getContentPane(), CARD_REGISTER);
    }
    /**
     * Card que mostra la primera vista del projecte
     */

    public void showLoginRegisterCard(){cardManager.show(getContentPane(), CARD_LOGIN_REGISTER);}
    /**
     * Card que mostra el menu principal
     */
    public void showMenuCard(){cardManager.show(getContentPane(), CARD_MENU);}
    /**
     * Card que mostra el joc
     */
    public void showGameCard(){

        cardManager.show(getContentPane(), CARD_GAME);
    }
    /**
     * Card que mostra les reproduccions
     */
    public void showReproCard(){
        cardManager.show(getContentPane(), CARD_REPRO);
    }

    /**
     * Card que mostra l'historial
     */
    public void showHistorialCard(){
        cardManager.show(getContentPane(), CARD_HISTORIAL);
    }

    /**
     * Vista del ranking
     * @return la vista del ranking
     */
    public RankingView getRankingView() {
        return rankingView;
    }

    /**
     * Card que mostra el ranking
     */
    public void showRankingCard(){
        cardManager.show(getContentPane(), CARD_RANKING);
    }
    /**
     * métode que mostra la reproduccio de la partida
     * @return reproView retorna la GameView de la partida reproduida
     */
    public ReproduceView getReproView() {
        return reproView;
    }

    /**
     * Card que mostra el logout
     */
    public void showLogoutCard(){
        cardManager.show(getContentPane(), CARD_LOGOUT);
    }

    /**
     * Mostra un error quan la informació del login es incorrecta
     */
    public void loginError(){JOptionPane.showMessageDialog(this,"El usuario o password son incorrectos","Error login",JOptionPane.ERROR_MESSAGE);}

    /**
     * Gestiona els tipus d'errors a l'hora de fer el registre de l'usuari a la base de dades
     * @param error l'error del que es tracta
     */
    public void registerError(int error){
        switch (error){
            case UserController.ERROR_CORREU:
                JOptionPane.showMessageDialog(this,"El correu no és vàlid","Error Register",JOptionPane.ERROR_MESSAGE);

                break;

            case UserController.ERROR_FORMAT_PASSWORD:
                JOptionPane.showMessageDialog(this,"La contrasenya no està en el format correcte","Error Register",JOptionPane.ERROR_MESSAGE);

                break;

            case UserController.ERROR_USUARI:
                JOptionPane.showMessageDialog(this,"Aquest usuari ja existeix","Error Register",JOptionPane.ERROR_MESSAGE);

                break;
            case UserController.ERROR_EQUALS_PASSWORD:
                JOptionPane.showMessageDialog(this,"Les contrasenyes no coincideixen","Error Register",JOptionPane.ERROR_MESSAGE);

                break;

        }
    }

    /**
     * Reseteja el text préviament introduït en els camps del login
     */
    public void clearLoginText(){
        loginName.setText("");
        loginPassword.setText("");
    }
    /**
     * Reseteja el text préviament introduït en els camps del register
     */
    public void clearRegisterText(){
        registerName.setText("");
        registerEmail.setText("");
        registerPassword.setText("");
        registerPassword2.setText("");

    }

    /**
     * Retorna la vista del historial
     * @return la vista del historial
     */
    public HistorialView getHistorialView() {
        return historialView;
    }

}


