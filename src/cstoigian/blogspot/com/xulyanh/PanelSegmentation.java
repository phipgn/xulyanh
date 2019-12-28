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
public class PanelSegmentation extends JPanel{
    Button btnPointDetect = new Button("Point Detect");
    Button btnLineDetect = new Button("Line Detect");
    Button btnEdgeDetect = new Button("Edge Detect");
    Choice lineType = new Choice();
    
    public PanelSegmentation() {
        final int btnX = 10;
        final int btnWidth = 80;
        final int btnHeight = 20;

        Border border = new TitledBorder("Segmentation");
        setBorder(border);

        setLayout(null);

        btnPointDetect.setBounds(btnX, 20, btnWidth, btnHeight);
        add(btnPointDetect);

        btnLineDetect.setBounds(btnX, 40, btnWidth, btnHeight);
        add(btnLineDetect);

        btnEdgeDetect.setBounds(btnX, 60, btnWidth, btnHeight);
        add(btnEdgeDetect);

        lineType.add("Horizontal");
        lineType.add("Vertical");
        lineType.add("+45");        
        lineType.add("-45");
        lineType.setBounds(btnX + btnWidth, 40, 60, btnHeight);
        add(lineType);
    }
}
