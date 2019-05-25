package obiekty_panelu_gry;


import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
/**
 *klasa tworzaca bohatera do panelu gry
 */
public class Bohater  implements KeyListener{

    /**
     * zmienna okreslajaca polozenie bohatera na osi x
     */
    private int polozenie_x;
    /**
     * zmienna okreslajaca polozenie bohatera na osi y
     */
    private int polozenie_y;
    /**
     * zmienna wysokosci bohatera
     */
    private int wysokosc;
    /**
     * zmienna szerokosci bohatera
     */
    private int szerokosc;
    /**
     * zmienna okreslajaca wysokosc okna
     */
    private int wysokosc_okna;
    /**
     * zmienna okreslajaca szerokosc okna
     */
    private int szerkosc_okna;
    /**
     * zmienna okreslajaca pole bohatera
     */
    private Rectangle pole_bohatera;

    /**
     * konstruktor bohatra
     * @param szerkosc_okna
     * @param wysokosc_okna
     */
    public Bohater(int szerkosc_okna,int wysokosc_okna) {

        dostosowanie_rozmiaru(szerkosc_okna,wysokosc_okna);
    }

    /**
     * metoda zwracajaca polozenie bohatera osi x
     * @return (int)Math.round(polozenie_x+szerokosc*0.5)
     */
    public int getPolozenie_x(){return (int)Math.round(polozenie_x+szerokosc*0.5);}

    /**
     * metoda zwracajaca polozenie bohatera osi y
     * @return polozenie_y
     */
    public int getPolozenie_y(){return polozenie_y;}

    /**
     * metoda zwracajaca pole pod bohaterem
     * @return
     */
    public Rectangle getPole_bohatera() {
        return pole_bohatera;
    }

    /**
     * metoda dostosowywujaca rozmiar bohatera do rozmiaru ekranu
     * @param szerkosc_okna
     * @param wysokosc_okna
     */
    public void dostosowanie_rozmiaru(int szerkosc_okna,int wysokosc_okna)
    {
        this.szerkosc_okna=szerkosc_okna;
        this.wysokosc_okna=wysokosc_okna;

        wysokosc = (int)Math.round(wysokosc_okna*0.20);
        szerokosc = (int)Math.round(szerkosc_okna*0.03);

        polozenie_x=(int)Math.round(szerkosc_okna*0.5);

        this.polozenie_x = polozenie_x-szerokosc;
        this.polozenie_y = wysokosc_okna-wysokosc;

        pole_bohatera = new Rectangle(polozenie_x,polozenie_y,szerokosc,wysokosc);

    }

    /**
     * metoda zmieniajaca polozenie bochatera po nacisnieciu przyciksu
     */
    public void ruch_bohatera(String a) {
        if (a == "lewo") {
            if(polozenie_x < 0){polozenie_x=0;}else
                if(polozenie_x >0){ polozenie_x-=(int)Math.round(szerkosc_okna*0.009);}
        }

        if (a == "prawo") {
            if(polozenie_x > szerkosc_okna){polozenie_x=szerkosc_okna-szerokosc;}else
                if(polozenie_x <szerkosc_okna-szerokosc){ polozenie_x+=(int)Math.round(szerkosc_okna*0.009);}
        }
    }

    /**
     * metoda rysujaca bohatera na ekranie
     * @param g
     */
    public void obraz_bohatera(Graphics g) {
        pole_bohatera.setLocation(polozenie_x,polozenie_y);
        g.setColor(Color.green);
        g.fillRect(polozenie_x,polozenie_y, szerokosc,wysokosc);
    }
    /**
     * Metoda opisujaca co ma sie stac z bohaterem gdy nacisniety sie przycisk
     * @param keyEvent obiekt zdarzenia zwianzaego z nacisnieciem przycisku
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    /**
     * Metoda opisujaca co ma sie stac z bohaterem gdy nacisniety sie przycisk
     * @param keyEvent obiekt zdarzenia zwianzaego z wcisnieciem przycisku
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int ruch = keyEvent.getKeyCode();

        if(ruch==keyEvent.VK_LEFT)
        {
            ruch_bohatera("lewo");
        }
        if(ruch==keyEvent.VK_RIGHT)
        {
            ruch_bohatera("prawo");
        }
    }

    /**
     * Metoda opisujaca co ma sie stac z bohaterem gdy nacisniety sie przycisk
     * @param keyEvent obiekt zdarzenia zwianzaego z nacisnieciem przycisku
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if(keyCode==keyEvent.VK_LEFT)
        {
            ruch_bohatera("lewo");
        }
        if(keyCode==keyEvent.VK_RIGHT)
        {
            ruch_bohatera("prawo");
        }
    }
}