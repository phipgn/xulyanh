/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cstoigian.blogspot.com.xulyanh;

import java.awt.Button;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Admin
 */
public class PanelRotateCanvas extends JPanel{
    Button btnFlipH = new Button("Flip Horizontal");
    Button btnFlipV = new Button("Flip Vertical");
    Button btnRotateL = new Button("Rotate Left");
    Button btnRotateR = new Button("Rotate Right");
    Button btnZoomIn = new Button("Zoom + [2x]");
    Button btnZoomOut = new Button("Zoom - [2x]");

    public PanelRotateCanvas() {
        final int btnX = 10;
        final int btnWidth = 100;
        final int btnHeight = 20;

        Border border = new TitledBorder("Rotate Canvas");
        setBorder(border);

        setLayout(null);

        btnFlipH.setBounds(btnX, 20, btnWidth, btnHeight);
        add(btnFlipH);

        btnFlipV.setBounds(btnX, 40, btnWidth, btnHeight);
        add(btnFlipV);

//        btnRotateL.setBounds(btnX, 60, btnWidth, btnHeight);
//        add(btnRotateL);
//
//        btnRotateR.setBounds(btnX, 80, btnWidth, btnHeight);
//        add(btnRotateR);

        btnZoomIn.setBounds(btnX, 100, btnWidth, btnHeight);
        add(btnZoomIn);

        btnZoomOut.setBounds(btnX, 120, btnWidth, btnHeight);
        add(btnZoomOut);
    }
}
