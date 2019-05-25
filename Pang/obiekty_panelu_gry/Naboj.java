package obiekty_panelu_gry;

import java.awt.*;
import java.awt.Rectangle;
import java.awt.Color;

/**
 * klasa tworzaca naboj
 */
public class Naboj
{
    /**
     * zmienna okreslajaca polozenie x
     */
    private int polozenie_x;
    /**
     * zmienna okrslajaca polozenie y
     */
    private int polozenie_y;

    /**
     * zmienna okreslajaca predkosc naboju w osi y
     */
    private int predkosc_y;
    /**
     * zmienna okreslajaca szerokosc bonusu
     */
    private int szerokosc;
    /**
     * zmienna okreslajca wysokosc bonusu
     */
    private int wysokosc;
    /**
     * zmienna okreslajaca wysokosc okna
     */
    private int wysokosc_okna;
    /**
     * zmiernna okreslaaca szerokosc okna
     */
    private int szerokosc_okna;
    /**
     * zmienna okreslajaca pole naboju
     */
    private Rectangle pole_naboju;
    /**
     * zmienna okreslajaca stan naboju
     */
    boolean stan_naboju;

    /**
     * konstruktor naboju
     * @param polozenie_x
     * @param polozenie_y
     * @param szerokosc_okna
     * @param wysokosc_okna
     */
    public Naboj(int polozenie_x,int polozenie_y,int szerokosc_okna,int wysokosc_okna)
    {
        this.polozenie_x = polozenie_x;
        this.polozenie_y = polozenie_y;
        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;
        stan_naboju=true;
        predkosc_y = (int)Math.round(wysokosc_okna*0.025);
        wysokosc = (int)Math.round(wysokosc_okna*0.025);
        szerokosc = wysokosc;
        if(polozenie_y<wysokosc){polozenie_y=wysokosc;}
        if(polozenie_y>wysokosc_okna-wysokosc){polozenie_y=wysokosc_okna;}
        this.polozenie_x = polozenie_x-szerokosc;
        this.polozenie_y = polozenie_y-wysokosc;
        pole_naboju = new Rectangle(polozenie_x,polozenie_y,wysokosc,szerokosc);

    }

    /**
     * metoda zwracajaca pole naboju
     * @return
     */
    public Rectangle getPole_naboju() {
        return pole_naboju;
    }

    /**
     * metoda zwracajaca stan naboju
     * @return
     */
    public Boolean Stan_naboju(){return stan_naboju;}

    /**
     * metoda zmieniajaca ztan naboju
     * @return
     */
    public Boolean usun_naboj(){return stan_naboju=false;}

    /**
     * medtoda rysujaca obraz naboju na ekranie
     * @param g
     */
    public void obraz_naboju(Graphics g) {
        if(stan_naboju) {
            polozenie_y -= predkosc_y;
            pole_naboju.setLocation(polozenie_x, polozenie_y);
            g.setColor(Color.blue);
            g.fillOval(polozenie_x, polozenie_y,
                    szerokosc, wysokosc);
        }
    }

    /**
     * metoda dostosowywujaca rozmiar naboju do rozmiaru okna
     * @param szerokosc_okna
     * @param wysokosc_okna
     */
    public void dostosowanie_rozmiaru(int szerokosc_okna,int wysokosc_okna)
    {
        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;

        predkosc_y = (int)Math.round(wysokosc_okna*0.025);

        wysokosc = (int)Math.round(wysokosc_okna*0.025);
        szerokosc = wysokosc;

        this.polozenie_x = polozenie_x-szerokosc;
        this.polozenie_y = wysokosc_okna-wysokosc;

        pole_naboju = new Rectangle(polozenie_x,polozenie_y,szerokosc,wysokosc);

    }


}
