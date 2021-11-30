package Presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Classe encarregada de mostrar el ranking basat en el winratio dels jugadors
 * @version 04/07/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class RankingView extends JPanel {
    private JTable jtRanking;
    private JPanel jpFons;
    private JButton jbHistorial;
    private JButton jbEndarrere;
    public static final String BTN_BACK_RANKING = "BTN_BACK_RANKING";
    public static final String BTN_GO_HISTORIAL = "BTN_GO_HISTORIAL";
    private String[][] dades;


    /**
     * Vista del ranking
     * @param dades2 les dades que es mostren a la taula del ranking
     */
    public RankingView(String[][] dades2){
        dades=dades2;
        this.setLayout(new BorderLayout());

        jpFons = new ImagePanel("assets/Images/fons_game.png");
        jpFons.setLayout(new BorderLayout());


        String[] nomcolumnes ={"PLAYER","WINS","WIN RATIO"};
        jtRanking = new JTable(dades2,nomcolumnes);
        JScrollPane jscroll = new JScrollPane(jtRanking);
        jscroll.setOpaque(false);
        jscroll.getViewport().setOpaque(false);
        jscroll.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        jbHistorial = new JButton("Historial");
        jbEndarrere = new JButton("Atras");
        jbHistorial.setActionCommand(BTN_GO_HISTORIAL);
        jbEndarrere.setActionCommand(BTN_BACK_RANKING);
        jbEndarrere.setVerticalAlignment(SwingConstants.CENTER);
        jbHistorial.setVerticalAlignment(SwingConstants.CENTER);

        JPanel jpButtons = new JPanel(new FlowLayout());
        jpButtons.add(jbHistorial);
        jpButtons.add(jbEndarrere);
        Dimension dimension = super.getToolkit().getScreenSize();
        jpButtons.setPreferredSize(new Dimension((int)dimension.getWidth(),100));
        jpButtons.setOpaque(false);
        jpFons.add(jscroll,BorderLayout.CENTER);
        jpFons.add(jpButtons,BorderLayout.SOUTH);

        this.add(jpFons);


    }
    /**
     * Registra els actionlisteners dels botons
     * @param actionListener parametre de ActionListener
     */
    public void registerController(ActionListener actionListener) {

        jbEndarrere.addActionListener(actionListener);
        jbHistorial.addActionListener(actionListener);
    }

    /**
     * El usuari que s'ha clicat del ranking
     * @return el nom del usuari clicat
     */
    public String getUsuari(){
        int fila = jtRanking.getSelectedRow();

        return dades[fila][0];
    }



}
