/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cstoigian.blogspot.com.xulyanh;

import java.awt.Button;
import java.awt.Choice;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Admin
 */
public class PanelSpatialFilter extends JPanel {
    Button btnAverageFilter = new Button("Average Filter");
    Button btnWeightedFilter = new Button("Weighted Filter");
    Button btnLapacianSimple = new Button("Simple Lapacian");
    Button btnSobel = new Button("Sobel Operators");
    Button btnLowPassFilter = new Button ("Low Pass Filter");
    Button btnHighPassFilter = new Button("High Pass Filter");
    Choice lapacian = new Choice();
    Choice sobel = new Choice();
    
    public PanelSpatialFilter() {
        final int btnX = 10;
        final int btnWidth = 110;
        final int btnHeight = 20;
        
        Border border = new TitledBorder("Spatial Filtering");
        setBorder(border);

        setLayout(null);

        btnAverageFilter.setBounds(btnX, 20, btnWidth, btnHeight);
        add(btnAverageFilter);

        btnWeightedFilter.setBounds(btnX, 40, btnWidth, btnHeight);
        add(btnWeightedFilter);

        btnLapacianSimple.setBounds(btnX, 60, btnWidth, btnHeight);
        add(btnLapacianSimple);
        lapacian.add("1");
        lapacian.add("2");
        lapacian.add("3");
        lapacian.setBounds(btnX + btnWidth, 60, 30, btnHeight);
        add(lapacian);

        btnSobel.setBounds(btnX, 80, btnWidth, btnHeight);
        add(btnSobel);
        sobel.add("1");
        sobel.add("2");
        sobel.setBounds(btnX + btnWidth, 80, 30, btnHeight);
        add(sobel);

        btnLowPassFilter.setBounds(btnX, 100, btnWidth, btnHeight);
        add(btnLowPassFilter);

        btnHighPassFilter.setBounds(btnX, 120, btnWidth, btnHeight);
        add(btnHighPassFilter);
    }
}
