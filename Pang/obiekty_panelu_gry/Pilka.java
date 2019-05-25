package obiekty_panelu_gry;

import java.awt.*;
import java.util.Random;


/**
 * klasas tworzaca ppilke
 */
public class Pilka  {

    /**
     * zmienna okreslajaca polozenie x
     */
    private int polozenie_x;
    /**
     * zmienna okrslajaca polozenie y
     */
    private int polozenie_y;
    /**
     * zmienna okreslajaca szerokosc bonusu
     */
    private int szerokosc;
    /**
     * zmienna okreslajca wysokosc bonusu
     */
    private int wysokosc;
    /**
     * zmienna okreslajaca promien pilki
     */
    private float promien;
    /**
     * zmienna okreslajaca stan piki
     */
    private boolean stan_pilki;
    /**
     * zmienna okreslajaca predkosc pilki  na osi x
     */
    private double predkosc_x;
    /**
     * zmiena okreslajaca predkosc piki na osi y
     */
    private double predkosc_y;
    /**
     * zmienna okreslajaca wysokosc okna
     */
    private int wysokosc_okna;
    /**
     * zmiernna okreslaaca szerokosc okna
     */
    private int szerokosc_okna;
    /**
     * zmienna okreslajaca pole pilki
     */
    private Rectangle pole_pilki;
    /**
     * zmienna losujaca
     */
    private Random random = new Random();
    /**
     * zmienna losowania true/false
     */
    private boolean r = random.nextBoolean();

    /**
     * konstruktor pilki
     * @param polozenie_x
     * @param polozenie_y
     * @param szerokosc_okna
     * @param wysokosc_okna
     * @param promien
     */
    public Pilka(int polozenie_x,int polozenie_y,int szerokosc_okna,int wysokosc_okna,float promien)
    {
        this.polozenie_x = polozenie_x;
        this.polozenie_y = polozenie_y;
        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;
        this.promien = promien;
        predkosc_x= (int)Math.round(szerokosc_okna*0.004);
        predkosc_y=(int)Math.round(wysokosc_okna*0.01);

        wysokosc = (int)Math.round(wysokosc_okna*promien*0.05);
        szerokosc = wysokosc;
        pole_pilki = new Rectangle(polozenie_x,polozenie_y,wysokosc,szerokosc);
        stan_pilki=true;
        if(r!=true){predkosc_x=-predkosc_x;}
    }

    /**
     * metoda okreslajaca ruch pikina planszy
     */
    public void ruch_pilki()
    {
        if (polozenie_x < 0 || polozenie_x > szerokosc_okna-szerokosc) { predkosc_x = -predkosc_x; }
        polozenie_x+=(int)Math.round(predkosc_x);

        if(polozenie_y < 0) {polozenie_y=0; predkosc_y = -predkosc_y; }
        if(polozenie_y > wysokosc_okna-wysokosc){polozenie_y=wysokosc_okna-wysokosc; predkosc_y = -predkosc_y; }
        predkosc_y+=1;
        polozenie_y+=predkosc_y;
    }

    /**
     * metosda zwracajaca stan piki
     * @return
     */
    public Boolean Stan_pili(){return stan_pilki;}

    /**
     * metoda zmieniajaca stan pilki
     * @return
     */
    public Boolean usun_pilke(){return stan_pilki=false;}

    /**
     * metoda opisuojace pole zajmowane przez pilke na ekranie
     * @return zwrot pola pilki
     */
    public Rectangle getPole_pilki() {
        return pole_pilki;
    }

    /**
     * metoda dostosowywujaca rozmiar i zachowanie piki do rozmiaru okna
     * @param szerokosc_okna
     * @param wysokosc_okna
     */
    public void dostosowanie_rozmiaru(int szerokosc_okna,int wysokosc_okna)
    {
        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;

        wysokosc = (int)Math.round(wysokosc_okna*promien*0.05);
        szerokosc = wysokosc;

        pole_pilki = new Rectangle(polozenie_x,polozenie_y,szerokosc,wysokosc);

    }


    /**
     * metoda rysujaca obraz pilki na ekranie
     * @param g
     */
    public void obraz_pilki(Graphics g)
    {
        if(stan_pilki) {
            ruch_pilki();
            g.setColor(Color.red);
            g.fillOval(polozenie_x, polozenie_y, wysokosc, szerokosc);
            pole_pilki.setLocation(polozenie_x, polozenie_y);
        }
    }

}
