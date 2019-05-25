package obiekty_panelu_gry;

import javax.swing.*;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.Color;
import java.util.Random;

/**
 * klasa tworzaca bonus
 */
public class Bonus  {
    /**
     * zmienna okreslajaca polozenie x
     */
    private int polozenie_x;
    /**
     * zmienna okrslajaca polozenie y
     */
    private int polozenie_y;
    /**
     * zmienna okreslajaca predkosc bonusu wzdlorz osi y
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
     * zmienna okreslajaca rodzaj bonusu
     */
    String rodzaj_bonusu;
    /**
     * zmienna okreslajaca pole bonusu
     */
    private Rectangle pole_bonusu;
    /**
     * zmienna okreslajaca czy danu bonus istnieje
     */
    private boolean stan_bonusu;

    /**
     * konstruktor bonusu
     * @param polozenie_x
     * @param polozenie_y
     * @param szerokosc_okna
     * @param wysokosc_okna
     */
    public Bonus(int polozenie_x,int polozenie_y,int szerokosc_okna,int wysokosc_okna) {

        this.polozenie_x = polozenie_x;
        this.polozenie_y = polozenie_y;

        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;

        szerokosc = (int)Math.round(szerokosc_okna*0.1);
        wysokosc = (int)Math.round(wysokosc_okna*0.02);

        predkosc_y= (int)Math.round(wysokosc_okna*0.003);

        pole_bonusu = new Rectangle(polozenie_x,polozenie_y,szerokosc,wysokosc);

        stan_bonusu=true;

        losowanie_bonusu();
    }

    /**
     * metoda zwracajaca pole bonusu
     * @return
     */
    public Rectangle getPole_bonusu() { return pole_bonusu; }

    /**
     * metoda zwracajaca stan bonusu
     * @return
     */
    public boolean Stan_bonusu(){return stan_bonusu;}

    /**
     * metoda usuwajaca bonus
     * @return
     */
    public boolean usun_bonus(){return stan_bonusu=false;}

    /**
     * metoda zwracajaca rodzaj bonusu
     * @return
     */
    public String getRodzaj_bonusu(){return rodzaj_bonusu;}

    /**
     * metoda losujaca rodzaj bonusu
     */
    private void losowanie_bonusu()
    {
        Random los=new Random();
        int numer= los.nextInt(6);
        switch (numer)
        {
            case 0:
                rodzaj_bonusu="+zycie";
                break;
            case 1:
                rodzaj_bonusu="+10 naboi";
                break;
            case 2:
                rodzaj_bonusu="+5 naboi";
                break;
            case 3:
                rodzaj_bonusu="-zycie";
                break;
            case 4:
                rodzaj_bonusu="+wieksze pilki";
                break;
            case 5:
                rodzaj_bonusu="+40 punktow";
                break;
            case 6:
                rodzaj_bonusu="+duze pilki";
                break;

        }

    }

    /**
     * metoda dostosowywujaca rozmiar bonusu do rozmiaru ekranu
     * @param szerokosc_okna
     * @param wysokosc_okna
     */
    public void dostosowanie_rozmiaru(int szerokosc_okna,int wysokosc_okna)
    {
        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;

        szerokosc = (int)Math.round(szerokosc_okna*0.1);
        wysokosc = (int)Math.round(wysokosc_okna*0.02);

        predkosc_y= (int)Math.round(wysokosc_okna*0.003);

        this.polozenie_x = polozenie_x-szerokosc;
        this.polozenie_y = wysokosc_okna-wysokosc;

        pole_bonusu = new Rectangle(polozenie_x,polozenie_y,szerokosc,wysokosc);

    }
    /**
     * medtoda rysujaca obraz bonusu na ekranie
     * @param g
     */
    public void obraz_bonusu(Graphics g) {
        if(stan_bonusu) {
            polozenie_y += predkosc_y;
            g.setColor(Color.RED);
            pole_bonusu.setLocation(polozenie_x, polozenie_y);
            g.setFont(new Font("Sansferin", 1, 30));
            g.drawString(rodzaj_bonusu, polozenie_x, polozenie_y);
        }
    }


}