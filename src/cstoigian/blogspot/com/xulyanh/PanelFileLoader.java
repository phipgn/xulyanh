/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cstoigian.blogspot.com.xulyanh;

import com.sun.java.swing.plaf.motif.MotifBorders.BevelBorder;
import java.awt.Button;
import java.awt.Color;
import java.awt.TextField;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author Admin
 */
public class PanelFileLoader extends JPanel {
    Button btnBrowse = new Button("Browse");
    TextField filebox = new TextField(90);
    Button btnLoad = new Button("Load Image");
    Button btnExit = new Button("Exit");
    
    public PanelFileLoader() {
       add(btnBrowse);
       add(filebox);
       add(btnLoad);
       add(btnExit);
       Border border = new BevelBorder(true, Color.darkGray, Color.lightGray);
       setBorder(border);
    }
}
