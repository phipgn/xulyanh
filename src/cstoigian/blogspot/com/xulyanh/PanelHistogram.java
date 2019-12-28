/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cstoigian.blogspot.com.xulyanh;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class PanelHistogram extends JPanel {
    int[] h = new int[256];
    
    public PanelHistogram() {
        setBackground(Color.black);
    }

    public void setHistogram(int[] h){
        this.h = h;
        repaint();
    }

    public void setHistogram(Pixel[][] data, int width, int height) {
        this.h = new int[256];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                int temp = (data[row][col].r + data[row][col].g + data[row][col].b) / 3;
                this.h[temp]++;
            }
        }
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.gray);
        for (int i = 0; i < 256; i++) {            
            g.drawRect(i, 0, 1, h[i]/14);
        }
        g.setColor(Color.WHITE);
        g.drawString("Histogram Chart", 10, 220);
    }
}
