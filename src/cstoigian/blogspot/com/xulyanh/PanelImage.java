/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cstoigian.blogspot.com.xulyanh;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Admin
 */
public class PanelImage extends JPanel {

    BufferedImage bi;
    final int zoom = 2;
    private String filename;

    public PanelImage() {
        setBackground(Color.black);
    }

    public void loadImage(String filename) {
        try {
            this.filename = filename;
            Image img = Toolkit.getDefaultToolkit().getImage(filename);
            try {
                MediaTracker mtracker = new MediaTracker(this);
                mtracker.addImage(img, 0);
                mtracker.waitForID(0);
            } catch (Exception e) {
                System.out.println("Error: " + e);
            }
            int w = img.getWidth(this);
            int h = img.getHeight(this);
            if (w > h) {
                h = w;
            } else {
                w = h;
            }
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D bicontext = bi.createGraphics();
            bicontext.drawImage(img, 0, 0, this);
            repaint();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Please select an image file!!!", "Error", 2);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (bi != null) {
            g.drawImage(bi, 0, 0, this);
        } else {
            g.setColor(Color.WHITE);
            g.drawString("NO IMAGE LOADED!!!", 10, 20);
        }
    }
    Pixel[][] data;

    public Pixel[][] getData() {
        Raster r = bi.getRaster();
        data = new Pixel[r.getHeight()][r.getWidth()];
        int[] temp = new int[3];
        for (int row = 0; row < bi.getHeight(); row++) {
            for (int col = 0; col < bi.getWidth(); col++) {
                temp = r.getPixel(col, row, temp); // return one-dimension array including values of RGB
                Pixel pixel = new Pixel(temp[0], temp[1], temp[2]);
                data[row][col] = pixel;
            }
        }
        return data;
    }

    public void zoomInData(int zoomParameter) {
        try {
            Image img = Toolkit.getDefaultToolkit().getImage(this.filename);
            try {
                MediaTracker mtracker = new MediaTracker(this);
                mtracker.addImage(img, 0);
                mtracker.waitForID(0);
            } catch (Exception e) {
            }
            int w = img.getWidth(this) * zoomParameter;
            int h = img.getHeight(this) * zoomParameter;
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D bicontext = bi.createGraphics();
            bicontext.drawImage(img, 0, 0, this);
            //repaint();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Please select an image file!!!", "Error", 2);
        }
        /* important section */
        Raster r = bi.getRaster();
        /* tạo mảng pixel mới với kích thước đã được nhân lên */
        Pixel[][] dataMagnified = new Pixel[r.getHeight() * zoomParameter][r.getWidth() * zoomParameter];
        int[] temp = new int[3];
        for (int i = 0, row = 0; row < bi.getHeight() * zoomParameter; row += zoomParameter, i++) {
            for (int j = 0, col = 0; col < bi.getWidth() * zoomParameter; col += zoomParameter, j++) {
                temp = r.getPixel(col - j, row - i, temp);
                Pixel pixel = new Pixel(temp[0], temp[1], temp[2]);
                dataMagnified[row][col] = pixel;
                dataMagnified[row][col + 1] = pixel;
                dataMagnified[row + 1][col] = pixel;
                dataMagnified[row + 1][col + 1] = pixel;
            }
        }
        this.setData(dataMagnified);
    }

    public void zoomOutData() {
        try {
            Image img = Toolkit.getDefaultToolkit().getImage(this.filename);
            try {
                MediaTracker mtracker = new MediaTracker(this);
                mtracker.addImage(img, 0);
                mtracker.waitForID(0);
            } catch (Exception e) {
            }
            int w = img.getWidth(this);
            int h = img.getHeight(this);
            bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D bicontext = bi.createGraphics();
            bicontext.drawImage(img, 0, 0, this);
            //repaint();
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, "Please select an image file!!!", "Error", 2);
        }
        /* important section */
        Raster r = bi.getRaster();
        Pixel[][] dataThumbnailed = new Pixel[r.getHeight()][r.getWidth()];
        int[] temp = new int[3];
        for (int i = 0, row = 0; row < bi.getHeight(); row += 2, i++) {
            for (int j = 0, col = 0; col < bi.getWidth(); col += 2, j++) {
                temp = r.getPixel(col, row, temp);
                Pixel pixel = new Pixel(temp[0], temp[1], temp[2]);
                dataThumbnailed[row - i][col - j] = pixel;
            }
        }
        this.setData2(dataThumbnailed);
    }

    public void setData2(Pixel[][] data) {
        WritableRaster wr = bi.getRaster();
        int[] temp = new int[3];
        for (int row = 0; row < bi.getHeight() / 2; row++) {
            for (int col = 0; col < bi.getWidth() / 2; col++) {
                temp[0] = data[row][col].r;
                temp[1] = data[row][col].g;
                temp[2] = data[row][col].b;
                wr.setPixel(col, row, temp);
            }
        }
        repaint();
    }

    public void setData(Pixel[][] data) {
        WritableRaster wr = bi.getRaster();
        int[] temp = new int[3];
        for (int row = 0; row < bi.getHeight(); row++) {
            for (int col = 0; col < bi.getWidth(); col++) {
                temp[0] = data[row][col].r;
                temp[1] = data[row][col].g;
                temp[2] = data[row][col].b;
                wr.setPixel(col, row, temp);
            }
        }
        repaint();
    }

    /* start from pixel[0,0] */
    public void transformDouble_1(double[][] input) {
        Pixel[][] pix = this.getData();
        double scale = 0;

        for (int row = 0; row < this.bi.getHeight() - 2; row++) {
            for (int col = 0; col < this.bi.getWidth() - 2; col++) {
                float r = 0, g = 0, b = 0;
                for (int i = 0; i < input.length; i++) {
                    for (int j = 0; j < input.length; j++) {
                        r += pix[row + i][col + j].r * input[i][j];
                        g += pix[row + i][col + j].g * input[i][j];
                        b += pix[row + i][col + j].b * input[i][j];
                    }
                }
                pix[row][col].r = (int) Math.round(r);
                pix[row][col].g = (int) Math.round(g);
                pix[row][col].b = (int) Math.round(b);
            }
        }
        this.setData(pix);
    }

    /* start from pixel[1,1] */
    public void transformDouble_2(double[][] input) {
        Pixel[][] pix = this.getData();
        double scale = 0;

        for (int row = 1; row < this.bi.getHeight() - 1; row++) {
            for (int col = 1; col < this.bi.getWidth() - 1; col++) {
                float r = 0, g = 0, b = 0;
                for (int i = -1; i < input.length - 1; i++) {
                    for (int j = -1; j < input.length - 1; j++) {
                        r += pix[row + i][col + j].r * input[i + 1][j + 1];
                        g += pix[row + i][col + j].g * input[i + 1][j + 1];
                        b += pix[row + i][col + j].b * input[i + 1][j + 1];
                    }
                }
                pix[row][col].r = (int) Math.round(r);
                pix[row][col].g = (int) Math.round(g);
                pix[row][col].b = (int) Math.round(b);
            }
        }
        this.setData(pix);
    }

    /* start from pixel[0,0] */
    public void transformInt_1(int[][] input) {
        Pixel[][] pix = this.getData();        

        int scale = 0;
        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < input.length; j++) {
                scale += input[i][j];
            }
        }
        if (scale == 0) {
            scale = 1; // prevent dividing by zero
        }

        int len = input.length;
        for (int row = 0; row < this.bi.getHeight() - 2; row++) {
            for (int col = 0; col < this.bi.getWidth() - 2; col++) {
                int r = 0, g = 0, b = 0;
                for (int i = 0; i < len; i++) {
                    for (int j = 0; j < len; j++) {
                        r += pix[row + i][col + j].r * input[i][j];
                        g += pix[row + i][col + j].g * input[i][j];
                        b += pix[row + i][col + j].b * input[i][j];
                    }
                }

                r /= scale;
                g /= scale;
                b /= scale;

                pix[row][col].r = valid(r);
                pix[row][col].g = valid(g);
                pix[row][col].b = valid(b);
            }
        }
        this.setData(pix);
    }

    /* start from pixel[1,1] */
    public void transformInt_2(int[][] input) {
        Pixel[][] pix = this.getData();
        try {
            int scale = 0;
            for (int i = 0; i < input.length; i++) {
                for (int j = 0; j < input.length; j++) {
                    scale += input[i][j];
                }
            }
            if (scale == 0) {
                scale = 1; // prevent dividing by zero
            }
            //JOptionPane.showMessageDialog(this, scale);

            for (int row = 1; row < this.bi.getHeight() - 1; row++) {
                for (int col = 1; col < this.bi.getWidth() - 1; col++) {
                    int r = 0, g = 0, b = 0;
                    for (int i = -1; i < input.length - 1; i++) {
                        for (int j = -1; j < input.length - 1; j++) {
                            r += pix[row + i][col + j].r * input[i + 1][j + 1];
                            g += pix[row + i][col + j].g * input[i + 1][j + 1];
                            b += pix[row + i][col + j].b * input[i + 1][j + 1];
                        }
                    }
                    // scale the value of r, g, b back to the range of 0 to 255
                    r /= scale;
                    g /= scale;
                    b /= scale;
                    // set the RGB value of pix
                    pix[row][col].r = valid(r);
                    pix[row][col].g = valid(g);
                    pix[row][col].b = valid(b);
                }
            }
            this.setData(pix); // set data and repaint
        } catch (Exception error) {
            JOptionPane.showMessageDialog(this, error);
        }
    }

    public int valid(int num) {
        if (num < 0) {
            return 0;
        } else if (num > 255) {
            return 255;
        } else {
            return num;
        }
    }

    public void rotateLeft() {
    }

}
