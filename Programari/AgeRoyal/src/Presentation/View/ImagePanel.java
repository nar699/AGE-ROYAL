package Presentation.View;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * Utilitzem aquesta classe per poder disposar de forma rapida de JPanels que tinguin una imatge associada
 * @version 23/05/21
 * @author Narcís Cisquella, Lluis Gumbau, Joan LLobet, Marc Postils
 */
public class ImagePanel extends JPanel {

    private Image image;

    /**
     *Constructor del ImagePanel
     * @param path es el path a la imatge que pintarà l'ImagePanel
     */

    public ImagePanel(String path) {
        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * constructor d'un imagepanel sense imatge
     */
    public ImagePanel(){
        image = null;
    }

    /**
     * Mètode que, al crear el Panel, ens el pintarà amb la Imatge de fons
     * @param g Graphics que pintaran la imatge
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(image != null){
            g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
        }

    }

    /**
     * Mètode per pintar el Jpanel amb la imatge
     */

    public void setBackground(String path){

        try {
            image = ImageIO.read(new File(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.repaint();
    }
    /**
     * setter del color del fons
     */
    public void setColorBackground(Color color) {
        this.setBackground(color);
    }
}