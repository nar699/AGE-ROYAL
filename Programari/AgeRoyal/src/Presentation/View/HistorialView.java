package Presentation.View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Classe encarregada de la vista que perany al historial de partides
 *  * @version 04/07/21
 *  * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class HistorialView extends JPanel {

    private JTable jtHistorial;
    private JPanel jpFons;
    private JButton jbReproduir;
    private JButton jbEndarrere;
    public static final String BTN_BACK_HISTORIAL = "BTN_BACK_HISTORIAL";
    public static final String BTN_REPRODUIR_HISTORIAL = "BTN_REPRODUIR_HISTORIAL";
    private String[][] dades;
    private boolean reproduir;

    /**
     * Constructor de la vista del historial de partides
     * @param dades les partides que e troben al historial
     */
    public HistorialView(String[][] dades,boolean reproduir){
        this.reproduir = reproduir;
        this.dades = dades;
        this.setLayout(new BorderLayout());

        jpFons = new ImagePanel("assets/Images/fons_game.png");
        jpFons.setLayout(new BorderLayout());


        String[] nomcolumnes ={"Data","Nom Partida","Resultat Partida"};
        jtHistorial = new JTable(dades,nomcolumnes);
        JScrollPane jscroll = new JScrollPane(jtHistorial);
        jscroll.setOpaque(false);
        jscroll.getViewport().setOpaque(false);
        jscroll.setBorder(BorderFactory.createEmptyBorder(40,40,40,40));

        jbReproduir = new JButton("Reproduir");
        jbEndarrere = new JButton("Atras");
        jbReproduir.setVerticalAlignment(SwingConstants.CENTER);
        jbEndarrere.setVerticalAlignment(SwingConstants.CENTER);
        jbEndarrere.setActionCommand(BTN_BACK_HISTORIAL);
        jbReproduir.setActionCommand(BTN_REPRODUIR_HISTORIAL);
        JPanel jpButtons = new JPanel(new FlowLayout());
        if(reproduir){
            jpButtons.add(jbReproduir);
        }
        jpButtons.add(jbEndarrere);
        Dimension dimension = super.getToolkit().getScreenSize();
        jpButtons.setPreferredSize(new Dimension((int)dimension.getWidth(),100));
        jpButtons.setOpaque(false);
        jpFons.add(jscroll,BorderLayout.CENTER);
        jpFons.add(jpButtons,BorderLayout.SOUTH);

        this.add(jpFons);

    }

    /**
     * Funció que retorna el nom de la partida seleccionada
     * @return el nom de la partida seleccionada
     */
    public String getNomPartidaSeleccionada(){
        int fila = jtHistorial.getSelectedRow();
        String nomPartida = dades[fila][1] ;

        return nomPartida;
    }
    /**
     * Registra els dos botons del historial
     * @param actionListener parametre del ActionListener
     */
    public void registerController(ActionListener actionListener) {

        jbEndarrere.addActionListener(actionListener);
        jbReproduir.addActionListener(actionListener);
    }
}
