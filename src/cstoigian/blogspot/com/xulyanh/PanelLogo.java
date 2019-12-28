/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cstoigian.blogspot.com.xulyanh;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class PanelLogo extends JPanel{
    ImageIcon ico = new ImageIcon("images/gapi.png");
    Image img;

    public PanelLogo() {
        img = ico.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, this);
    }
}
