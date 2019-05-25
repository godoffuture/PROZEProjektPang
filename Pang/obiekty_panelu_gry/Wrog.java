package obiekty_panelu_gry;
import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.Random;

import java.awt.Canvas;
/*
klasa tworzaca wroga do panelu gry
 */
public class Wrog {

    /**
     * zmienna okreslajaca polozenie wroga naosi x
     */
    private int polozenie_x;
    /**
     * zmienna okreslajaca polozenie wroga na osi y
     */
    private int polozenie_y;
    /**
     * zmienna okreslajca predkosc wroga wzgledem osi x
     */
    private float predkosc_x;
    /**
     * zmienna okreslajaca wysokosc wroga
     */
    private int wysokosc;
    /**
     * zmienna okreslajca szerokosc wroga
     */
    private int szerokosc;
    /**
     * zmienna okreslajaca wysokosc okna
     */
    private int wysokosc_okna;
    /**
     * zmiennaokreslajaca szerokosc okna
     */
    private int szerokosc_okna;
    /**
     * zmienna sluzaca do zminay ruchu wroga
     */
    private int i =0;
    /**
     * zmienna opisujaca pole wroga
     */
    private Rectangle pole_wroga;

    /**
     * konstruktor wroga
     * @param szerokosc_okna
     * @param wysokosc_okna
     * @param predkosc_x
     */
    public Wrog(int szerokosc_okna,int wysokosc_okna,float predkosc_x) {
        polozenie_x=(int)Math.round(szerokosc_okna*0.5);
        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;

        this.predkosc_x=predkosc_x*(int)Math.round(szerokosc_okna*0.005);

        wysokosc = (int)Math.round(wysokosc_okna*0.15);
        szerokosc = (int)Math.round(szerokosc_okna*0.1);
        if(polozenie_x<szerokosc){polozenie_x=szerokosc;}
        if(polozenie_x>szerokosc_okna-szerokosc){polozenie_x=szerokosc_okna;}
        this.polozenie_x = polozenie_x-szerokosc;
        this.polozenie_y = (int)Math.round(wysokosc_okna*0.08);
        pole_wroga = new Rectangle(polozenie_x,polozenie_y,szerokosc,wysokosc);

    }

    /**
     * metoda zwracajaca pole wroga
     * @return
     */
    public Rectangle getPole_wroga() { return pole_wroga; }

    /**
     * metdoda zwracajaca polozenie wroga
     * @return
     */
    public int getPolozenie_x(){return (int)Math.round(polozenie_x+szerokosc*0.5);}

    /**
     * metoda zwracajaca polozenie wroga na osi y
     * @return
     */
    public int getPolozenie_y(){return polozenie_y;}

    /**
     * zmienna okreslajaca ruch wroga
     */
    private void ruch_wroga()
    {
        i++;
        if(i%(int)Math.round(szerokosc_okna*0.05)==0) {
            Random random = new Random();
            boolean r = random.nextBoolean();
            if(r!=true){predkosc_x=-predkosc_x;}
        }
        polozenie_x+=predkosc_x;
        if (polozenie_x <= 0 || polozenie_x >= szerokosc_okna-szerokosc) {
            predkosc_x = -predkosc_x;
        }
        pole_wroga.setLocation(polozenie_x,polozenie_y);
    }

    /**
     * zmienna dostosowywujaca rozmiar i zachowanie wroga do romiaru okna
     * @param szerokosc_okna
     * @param wysokosc_okna
     */
    public void dostosowanie_rozmiaru(int szerokosc_okna,int wysokosc_okna)
    {
        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;

        wysokosc = (int)Math.round(wysokosc_okna*0.15);
        szerokosc = (int)Math.round(szerokosc_okna*0.1);

        polozenie_x=(int)Math.round(szerokosc_okna*0.5);
        this.polozenie_x = polozenie_x-szerokosc;
        this.polozenie_y = (int)Math.round(wysokosc_okna*0.08);

        this.predkosc_x=(int)Math.round(szerokosc_okna*0.006);

        pole_wroga = new Rectangle(polozenie_x,polozenie_y,szerokosc,wysokosc);
    }
    /**
     * metoda rysujaca obraz wroga na ekrnaie
     * @param g
     */
    public void obraz_wroga(Graphics g,int zycie_wroga)
    {
        ruch_wroga();
        g.setColor(Color.BLACK);
        g.fillRect(polozenie_x,polozenie_y, szerokosc,wysokosc);
        g.setColor(Color.white);
        g.drawString("["+zycie_wroga+"]",(int)Math.round(polozenie_x+szerokosc*0.3),(int)Math.round(polozenie_y+wysokosc*0.5));
    }


}