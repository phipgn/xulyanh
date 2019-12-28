/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cstoigian.blogspot.com.xulyanh;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author songtoigianvn.blogspot.com
 */
public class Process extends JFrame implements ActionListener {

    PanelImageEnhancement panelIE = new PanelImageEnhancement();
    PanelRotateCanvas panelRC = new PanelRotateCanvas();
    PanelSpatialFilter panelSF = new PanelSpatialFilter();
    PanelFrequencyFilter panelFF = new PanelFrequencyFilter();
    PanelSegmentation panelSM = new PanelSegmentation();
    PanelLogo panelLG = new PanelLogo();
    PanelHistogram panelH = new PanelHistogram();
    PanelImage panelIM = new PanelImage();
    PanelFileLoader panelFL = new PanelFileLoader();
    Pixel[][] data;
    int[] h;
    int[] cdf = new int[256]; // cumulative distribution function
    File f;

    public Process() {
        super("Image Processing Program [Author: songtoigianvn@gmail.com]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(955, 760);
        setLayout(null);

        final int panelHeight = 180;
        final int panelWidth = 150;

        panelFL.setBounds(20, 5, 900, 35);
        panelFL.filebox.setText("images\\Lady Agnew of Lochnaw.jpg"); // default path
        panelFL.btnBrowse.addActionListener(this);
        panelFL.btnLoad.addActionListener(this);
        panelFL.btnExit.addActionListener(this);
        add(panelFL);

        panelIE.setBounds(20, 50, panelWidth, panelHeight);
        panelIE.btnHistogramEqualize.addActionListener(this);
        panelIE.btnBnW.addActionListener(this);
        panelIE.btnNegative.addActionListener(this);
        panelIE.btnThreshold.addActionListener(this);
        panelIE.btnLogaricthmic.addActionListener(this);
        panelIE.btnPowerLaw.addActionListener(this);
        panelIE.btnBitPlane.addActionListener(this);
        add(panelIE);

        panelRC.setBounds(170, 50, panelWidth, panelHeight);
        panelRC.btnFlipH.addActionListener(this);
        panelRC.btnFlipV.addActionListener(this);
        panelRC.btnRotateL.addActionListener(this);
        panelRC.btnRotateR.addActionListener(this);
        panelRC.btnZoomIn.addActionListener(this);
        panelRC.btnZoomOut.addActionListener(this);
        add(panelRC);

        panelSF.setBounds(320, 50, panelWidth, panelHeight);
        panelSF.btnAverageFilter.addActionListener(this);
        panelSF.btnWeightedFilter.addActionListener(this);
        panelSF.btnLapacianSimple.addActionListener(this);
        panelSF.btnSobel.addActionListener(this);
        panelSF.btnLowPassFilter.addActionListener(this);
        panelSF.btnHighPassFilter.addActionListener(this);
        add(panelSF);

        panelFF.setBounds(470, 50, panelWidth, panelHeight);
        add(panelFF);

        panelSM.setBounds(620, 50, panelWidth, panelHeight);
        panelSM.btnPointDetect.addActionListener(this);
        panelSM.btnLineDetect.addActionListener(this);
        panelSM.btnEdgeDetect.addActionListener(this);
        add(panelSM);

        /* panelIM to show the histogram chart */
        panelH.setBounds(680, 230, 256, 256);
        add(panelH);

        /* panelIM to show the image */
        panelIM.setBounds(20, 230, 650, 480);
        add(panelIM);

        panelLG.setBounds(710, 530, 200, 200);
        add(panelLG);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        /* hit the browse button to open the browse file dialog */
        if (e.getSource() == panelFL.btnBrowse) {
            JFileChooser jfc = new JFileChooser("images");
            jfc.showOpenDialog(null);
            f = jfc.getSelectedFile();
            if (f != null) {
                try {
                    panelFL.filebox.setText(f.getCanonicalPath());
                    //System.out.println(panelFL.filebox.getText());
                } catch (Exception error) {
                    JOptionPane.showMessageDialog(this, error);
                }
            }
        }
        /* hit the load button to load the image to the panelIM */
        if (e.getSource() == panelFL.btnLoad) {
            String filename = panelFL.filebox.getText().trim();
            //System.out.println(filename);
            panelIM.loadImage(filename);
            data = panelIM.getData();

            h = new int[256];
            for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                    int temp = (data[row][col].r + data[row][col].g + data[row][col].b) / 3;
                    h[temp]++;
                }
            }
            panelH.setHistogram(h); // update histogram chart
        }
        ////////////////////////////////
        /* IMAGE ENHANCEMENT FUNCTION */
        ////////////////////////////////
        /* hit the black n white button */
        if (e.getSource() == panelIE.btnBnW) {
            if (data.length != 0) {
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        data[row][col].r = data[row][col].g = data[row][col].b = data[row][col].getGrayLevel();
                    }
                }
                panelIM.setData(data);
                panelH.setHistogram(data, panelIM.bi.getWidth(), panelIM.bi.getHeight());
            }
        }
        /* hit the negative button */
        if (e.getSource() == panelIE.btnNegative) {
            if (data.length != 0) {
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        data[row][col].r = 255 - data[row][col].r;
                        data[row][col].b = 255 - data[row][col].b;
                        data[row][col].g = 255 - data[row][col].g;
                    }
                }
                panelIM.setData(data);
                panelH.setHistogram(data, panelIM.bi.getWidth(), panelIM.bi.getHeight()); // update histogram chart
            }
        }
        /* HISTOGRAM EQUALIZING FUNCTION */
        /* hit the histogram equalize button */
        if (e.getSource() == panelIE.btnHistogramEqualize) {
            try {
                int width = panelIM.bi.getWidth();
                int height = panelIM.bi.getHeight();
                
                cdf[0] = h[0];
                for (int i = 0; i < 255; i++) {
                    cdf[i + 1] = cdf[i] + h[i + 1];
                }

                int min_cdf = cdf[0];
                for (int i = 0; i < 256; i++) {
                    if (cdf[i] < min_cdf) {
                        min_cdf = cdf[i];
                    }
                }

                /* h table is now set to the new gray values */
                for (int i = 0; i < 256; i++) {
                    float e1 = cdf[i] - min_cdf;
                    float e2 = (width * height) - min_cdf;
                    h[i] = Math.round((e1 / e2) * 255);
                }

                /* now all we have to do is to update new gray levels to the image */
                for (int row = 0; row < height; row++) {
                    for (int col = 0; col < width; col++) {
                        int temp = data[row][col].getGrayLevel();
                        for (int i = 0; i < 256; i++) {
                            if (temp == i) {
                                data[row][col].setPixel(h[i], h[i], h[i]);
                                break;
                            }
                        }
                    }
                }
                panelIM.setData(data);
                panelH.setHistogram(data, width, height);
            } catch (Exception error) {
                JOptionPane.showMessageDialog(rootPane, error, "There was a crash!!!", 1);
            }
        }
        /* hit the threshold button */
        if (e.getSource() == panelIE.btnThreshold) {
            int threshold_level = Integer.parseInt(panelIE.thresChoice.getText());
            if (data.length != 0) {
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        int temp = data[row][col].getGrayLevel();
                        if (temp < threshold_level) {
                            data[row][col].r = data[row][col].g = data[row][col].b = 0; // make pixel black
                        } else {
                            data[row][col].r = data[row][col].g = data[row][col].b = 255; // make pixel white
                        }
                    }
                }
                panelIM.setData(data); // update image
                panelH.setHistogram(data, panelIM.bi.getWidth(), panelIM.bi.getHeight()); // update histogram chart
            }
        }
        /* hit the logaricthmic button */
        if (e.getSource() == panelIE.btnLogaricthmic) {
            try {
                final int c = 1;
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        double r = data[row][col].getGrayLevel_2();
                        r /= 255;
                        r = c * Math.log10(1 + r);
                        r *= 255;
                        if (r > 255) {
                            r = 255;
                        }
                        data[row][col].r = data[row][col].g = data[row][col].b = (int) Math.round(r);
                    }
                }
                panelIM.setData(data);
                panelH.setHistogram(data, panelIM.bi.getWidth(), panelIM.bi.getHeight());
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* hit the power law button */
        if (e.getSource() == panelIE.btnPowerLaw) {
            try {
                final int c = 1;
                double power = (double) Float.parseFloat(panelIE.powerChoice.getText());
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        double r = data[row][col].getGrayLevel_2();
                        r /= 255;
                        r = c * Math.pow(r, power);
                        r *= 255;
                        if (r > 255) {
                            r = 255;
                        }
                        data[row][col].r = data[row][col].g = data[row][col].b = (int) Math.round(r);
                    }
                }
                panelIM.setData(data);
                panelH.setHistogram(data, panelIM.bi.getWidth(), panelIM.bi.getHeight());
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* hit the bit plane slicing button */
        if (e.getSource() == panelIE.btnBitPlane) {
            try {
                int bit = panelIE.bitChoice.getSelectedIndex() + 1;
                int bit_plane = (int) Math.pow(2, bit);
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        int r = data[row][col].getGrayLevel();
                        r = r & bit_plane;
                        r *= 255;
                        if (r > 255) {
                            r = 255;
                        }
                        data[row][col].r = data[row][col].g = data[row][col].b = r;
                    }
                }
                panelIM.setData(data);
                panelH.setHistogram(data, panelIM.bi.getWidth(), panelIM.bi.getHeight());
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /*****************/
        /* ROTATE CANVAS */
        /*****************/
        /* hit the flip horizontal button */
        if (e.getSource() == panelRC.btnFlipH) {
            if (data.length != 0) {
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth() / 2; col++) {
                        Pixel pixel = data[row][col];
                        data[row][col] = data[row][panelIM.bi.getWidth() - col - 1];
                        data[row][panelIM.bi.getWidth() - col - 1] = pixel;
                    }
                }
                panelIM.setData(data);
                data = panelIM.getData();
            }
        }
        /* hit the flip vertical button */
        if (e.getSource() == panelRC.btnFlipV) {
            if (data.length != 0) {
                for (int row = 0; row < panelIM.bi.getHeight() / 2; row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        Pixel pixel = data[row][col];
                        data[row][col] = data[panelIM.bi.getHeight() - row - 1][col];
                        data[panelIM.bi.getHeight() - row - 1][col] = pixel;
                    }
                }
                panelIM.setData(data);
                data = panelIM.getData();
            }
        }
        /* rotate left */
        if (e.getSource() == panelRC.btnRotateL) {
            
        }
        /* rotate right */
        if (e.getSource() == panelRC.btnRotateR) {
            try {
                for (int row = 0; row < panelIM.bi.getHeight(); row++) {
                    for (int col = 0; col < panelIM.bi.getWidth(); col++) {
                        Pixel pixel = data[row][col];
                        data[row][col] = data[col][row];
                        data[col][row] = pixel;
                    }
                    panelIM.setData(data);
                    data = panelIM.getData();
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* zoom in */
        if (e.getSource() == panelRC.btnZoomIn) {
            try {
                panelIM.zoomInData(2);
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* zoom out */
        if (e.getSource() == panelRC.btnZoomOut) {
            try {
                panelIM.zoomOutData();
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        ///////////////////////
        /* SPATIAL FILTERING */
        ///////////////////////
        /* average filter */
        if (e.getSource() == panelSF.btnAverageFilter) {
            try {
                double[][] m_average_filter = {
                    {0.111, 0.111, 0.111},
                    {0.111, 0.111, 0.111},
                    {0.111, 0.111, 0.111}};
                panelIM.transformDouble_2(m_average_filter);
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, "Pleas load an image file first!!!");
            }
        }
        /* weighted smoothing filter */
        if (e.getSource() == panelSF.btnWeightedFilter) {
            try {
                double[][] m_weighted_filter = {
                    {0.0625, 0.125, 0.0625},
                    {0.125, 0.25, 0.125},
                    {0.0625, 0.125, 0.0625}};
                panelIM.transformDouble_2(m_weighted_filter);
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, "Pleas load an image file first!!!");
            }
        }
        /* hit the simple lapacian filter */
        if (e.getSource() == panelSF.btnLapacianSimple) {
            try {
                int[][] m_lapacian_simple = {
                    {0, 1, 0},
                    {1, -4, 1},
                    {0, 1, 0}};
                int[][] m2 = {
                    {0, -1, 0},
                    {-1, 5, -1},
                    {0, -1, 0}};
                int[][] m3 = {
                    {1, 1, 1},
                    {1, -8, 1},
                    {1, 1, 1}};
                int control = panelSF.lapacian.getSelectedIndex();
                switch(control) {
                    case 0: {
                        panelIM.transformInt_1(m_lapacian_simple);
                        break;
                    }
                    case 1: {
                        panelIM.transformInt_1(m2);
                        break;
                    }
                    case 2: {
                        panelIM.transformInt_1(m3);
                        break;
                    }
                }                
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* hit the sobel button */
        if (e.getSource() == panelSF.btnSobel) {
            try {
                int[][] m_sobel_1 = {
                    {-1, -2, -1},
                    {0, 0, 0},
                    {1, 2, 1}};
                int[][] m_sobel_2 = {
                    {-1, 0, 1},
                    {-2, 0, 2},
                    {-1, 0, 1}};
                int choose = panelSF.sobel.getSelectedIndex();
                if (choose == 0) {
                    panelIM.transformInt_1(m_sobel_1);
                } else {
                    panelIM.transformInt_1(m_sobel_2);
                }              
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* low pass filter */
        if (e.getSource() == panelSF.btnLowPassFilter) {
            try {
                double[][] m_low_pass = {
                    {0, 0.125, 0},
                    {0.125, 0.25, 0.125},
                    {0, 0.125, 0}};
                panelIM.transformDouble_1(m_low_pass);
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* high pass filter */
        if (e.getSource() == panelSF.btnHighPassFilter) {
            try {
                int[][] m_high_pass = {
                    {-1, -1, -1},
                    {-1, 9, -1},
                    {-1, -1, -1}};
                panelIM.transformInt_1(m_high_pass);
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /////////////////////////
        /* FREQUENCY FILTERING */
        /////////////////////////

        //////////////////
        /* SEGMENTATION */
        //////////////////
        /* point detection */
        if (e.getSource() == panelSM.btnPointDetect) {
            try {
                int[][] m_point = {
                    {-1, -1, -1},
                    {-1, 8, -1},
                    {-1, -1, -1}};
                panelIM.transformInt_1(m_point);
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* line detection */
        if (e.getSource() == panelSM.btnLineDetect) {
            try {
                int[][] m_h_line = {
                    {-1, -1, -1},
                    {2, 2, 2},
                    {-1, -1, -1}};
                int[][] m_v_line = {
                    {-1, 2, -1},
                    {-1, 2, -1},
                    {-1, 2, -1}};
                int[][] m_45_pos = {
                    {-1, -1, 2},
                    {-1, 2, -1},
                    {2, -1, -1}};
                int[][] m_45_neg = {
                    {2, -1, -1},
                    {-1, 2, -1},
                    {-1, -1, 2}};
                int type = panelSM.lineType.getSelectedIndex();
                switch (type) {
                    case 0: {
                        panelIM.transformInt_1(m_h_line);
                        break;
                    }
                    case 1: {
                        panelIM.transformInt_1(m_v_line);
                        break;
                    }
                    case 2: {
                        panelIM.transformInt_1(m_45_pos);
                        break;
                    }
                    case 3: {
                        panelIM.transformInt_1(m_45_neg);
                        break;
                    }
                }
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }
        /* edge detection */
        if (e.getSource() == panelSM.btnEdgeDetect) {
            try {
                int[][] m_prewitt_1 = {
                    {-1, -1, -1},
                    {0, 0, 0},
                    {1, 1, 1}};
                int[][] m_prewitt_2 = {
                    {-1, 0, 1},
                    {-1, 0, 1},
                    {-1, 0, 1}};
                panelIM.transformInt_1(m_prewitt_1);
                panelIM.transformInt_1(m_prewitt_2);
                panelH.setHistogram(panelIM.getData(), panelIM.bi.getWidth(), panelIM.bi.getHeight());
                data = panelIM.getData();
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, error);
            }
        }

        if (e.getSource() == panelFL.btnExit) {
            System.exit(0);
        }
    }
}
