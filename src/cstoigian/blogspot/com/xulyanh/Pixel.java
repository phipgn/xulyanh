/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cstoigian.blogspot.com.xulyanh;

/**
 *
 * @author Admin
 */
public class Pixel {
    int r, g, b;

    public Pixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public void setPixel(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public int getGrayLevel() {
        return (this.r + this.g + this.b) / 3;
    }

    public double getGrayLevel_2() {
        return (this.r + this.g + this.b) / 3;
    }
}
