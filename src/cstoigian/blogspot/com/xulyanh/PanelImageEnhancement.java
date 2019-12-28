/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cstoigian.blogspot.com.xulyanh;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Graphics;
import java.awt.TextField;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Admin
 */
public class PanelImageEnhancement extends JPanel {
    Button btnHistogramEqualize = new Button("Equalize");
    Button btnBnW = new Button("Black n' White");
    Button btnNegative = new Button ("Negative");
    Button btnThreshold = new Button("Threshold");
    Button btnLogaricthmic = new Button("Logaricthmic");
    Button btnPowerLaw = new Button("Power Law");
    Button btnBitPlane = new Button("Bit Plane");
    Choice bitChoice = new Choice();
    String[] bitChoiceItem = {"1", "2", "3", "4", "5", "6", "7"};
    TextField powerChoice = new TextField("3");
    TextField thresChoice = new TextField("128");
    
    public PanelImageEnhancement() {
        final int btnX = 10;
        final int btnWidth = 95;
        final int btnHeight = 20;

        for (int i = 0; i < bitChoiceItem.length; i++) {
            bitChoice.addItem(bitChoiceItem[i]);
        }

        Border border = new TitledBorder("Image Enhancement");
        setBorder(border);

        setLayout(null);
        
        btnHistogramEqualize.setBounds(btnX, 20, btnWidth, btnHeight);
        add(btnHistogramEqualize);

        btnBnW.setBounds(btnX, 40, btnWidth, btnHeight);
        add(btnBnW);

        btnNegative.setBounds(btnX, 60, btnWidth, btnHeight);
        add(btnNegative);

        btnThreshold.setBounds(btnX, 80, btnWidth, btnHeight);
        add(btnThreshold);
        thresChoice.setBounds(btnX + btnWidth, 80, 40, btnHeight);
        add(thresChoice);

        btnLogaricthmic.setBounds(btnX, 100, btnWidth, btnHeight);
        add(btnLogaricthmic);

        btnPowerLaw.setBounds(btnX, 120, btnWidth, btnHeight);
        add(btnPowerLaw);
        powerChoice.setBounds(btnX + btnWidth, 120, 40, btnHeight);
        add(powerChoice);

        btnBitPlane.setBounds(btnX, 140, btnWidth, btnHeight);
        add(btnBitPlane);
        bitChoice.setBounds(btnX + btnWidth, 140, 40, 5);
        add(bitChoice);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
