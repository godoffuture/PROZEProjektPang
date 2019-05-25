
import java.awt.*;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferStrategy;

import zdarzenia.*;
import obiekty_panelu_gry.*;
import intrfejs.*;


/**
 * klasa tworzaca panel po ktorym sie poruszamy
 */

public class Panel_gry extends Canvas implements Runnable,KeyListener,Obserwator_zderzenia,Sluchacz_panelu_gry{
    /**
     *instrukcja podwojnego buforowannia
     */
    BufferStrategy bs=null;

    private Zderzenie zd;
    private Wrog w;
    private Bohater b;
    private ArrayList<Naboj> n;
    private ArrayList<Pilka> p;
    private ArrayList<Bonus> bon;

    /**
     * zmienna okreslajaca czy jest pauza lub gra
     */
    private boolean gra;
    /**
     * zmienna okreslajca wysokosc okna
     */
    private int wysokosc_okna;
    /**
     * amienna okreslajca szerokoosc okna
      */
    private int szerokosc_okna;

    /**
     * lista obserwatoroe panelu gry
     */
    private ArrayList<Obserwator_panelu_gry> obserwatorzy;
    /**
     * zmienna okreslajaca liczbe punktow
     */
    private int liczba_punktow;
    /**
     * zmiennaokreslacja licze zyc gracza
     */
    private int liczba_zyc;
    /**
     * xmienna okreslajca liczbe naboi
     */
    private int liczba_naboi;
    /**
     * zmienna okreslajaca liczbe pozostalych zyc wroga
     */
    private int liczba_zyc_wroga;
    /**
     * zmienna okreslajaca liczbe pilek
     */
    private int liczba_pilek;
    /**
     * zmienna okreslajca predkosc poruszania sie wroga po planszy
     */
    private float predkosc_wroga;
    /**
     * zmienna okreslajca liczbe bonusow dostepnych na danym poziomie
     */
    private int bonusy_poziomu;
    /**
     * zmienna okreslajca rozmiar pilki
     */
    private int rozmiar_pilki=1;
    /**
     * zmiennaokreslajca czestotoliwosc zmian ruchu wroga
     */
    private float zmiana_ruchu_wroga;
    /**
     * zmiennaokreslajca poziom
     */
    private int poziom;
    /**
     * zmienna okreslajaca czas gry
     */
    private float czas_gry;
    /**
     * zmienna okreslajca czas odswierzana sie watku
     */
    private int czas_watku;
    /**
     * zmienna okreslajcac liczbe punktow za ukonczenie poziomu
     */
    private int punkty_poziomu;
    /**
     * klasa plikow konfiguracyjnych
     */
    private Konfiguracja konfiguracja=new Konfiguracja();
    /**
     * klasa liczb pseudo losowych
     */
    private Random los=new Random();
    /**
     * zmienna potrzebna do tworzneia pilek
     */
    int i=0;

    /**
     * konstruktor panelu gry
     * @param szerokosc_okna
     * @param wysokosc_okna
     */

    public Panel_gry(int szerokosc_okna,int wysokosc_okna)
    {

        this.szerokosc_okna=szerokosc_okna;
        this.wysokosc_okna=wysokosc_okna;
        setSize(szerokosc_okna,wysokosc_okna);

        //do obiektow dac rozmiar okna
        poziom=1;
        liczba_punktow =0;
        poczatkowe_zmienne();
        w = new Wrog(szerokosc_okna,wysokosc_okna,predkosc_wroga);
        b = new Bohater(szerokosc_okna,wysokosc_okna);
        p = new ArrayList<Pilka>();
        n = new ArrayList<Naboj>();
        bon = new ArrayList<Bonus>();
        obserwatorzy = new ArrayList<Obserwator_panelu_gry>();
        zd = new Zderzenie(b,n,w,p,bon);
        zd.nowy_sluchacz(this);
        gra=true;
    }

    /**
     * metoda wczytujaca nowy poziom z pliku konfiguracyjnego
     * @param g
     */
    private void wczytaj_nowy_poziom(Graphics g)
    {
        while (((int) Math.round(czas_gry) == 0) || (liczba_zyc_wroga == 0)) {
            try{
                poziom++;
                liczba_punktow += punkty_poziomu;
                zmienne_poziomu(poziom);
                g.setColor(Color.BLACK);
                g.drawString("NOWY POZIOM: " + poziom, (int) Math.round(getWidth() * 0.5), (int) Math.round(getHeight() * 0.5));
                gra = false;
        } catch (Exception a){
                informacja_dla_obserwatora(new Zdarzenie_panelu_gry("koniec_poziomow",liczba_punktow));
            }
        }
    }

    /**
     * metoda stan gry
     */

    public void gra()
    {
        if(gra) { gra=false; }
        else if(!gra){gra=true;}
    }

    /**
     * metoda okreslajaca rodzaj nagrody otrzymanej za bonus
     * @param bonus
     */
    private void ustawienie_bonusu(String bonus)
    {
        switch (bonus)
        {
            case "+10 naboi":
                liczba_naboi+=10;
                break;
            case "+zycie":
                liczba_zyc+=1;
                break;
            case "-zycie":
                liczba_zyc-=1;
                break;
            case "+wieksze pilki":
                rozmiar_pilki+=1;
                break;
            case "+5 naboi":
                liczba_naboi+=5;
            case "+40 punktow":
                liczba_punktow+=40;
                break;
            case "+duze pilki":
                break;
        }
    }

    /**
     * metoda wczytujaca poczatkowe zmienne
     */
    private void poczatkowe_zmienne()
    {
        konfiguracja.loadProperties();
        predkosc_wroga=Integer.parseInt(konfiguracja.properties.getProperty("predkosc_wroga"));
        czas_watku=Integer.parseInt(konfiguracja.properties.getProperty("czas_watku"));
        punkty_poziomu=Integer.parseInt(konfiguracja.properties.getProperty("punkty_poziomu"));
        zmienne_poziomu(poziom);
    }

    /**
     * metoda wczytujaca zmienne dla danego poziomu
     * @param poziom
     */
    private void zmienne_poziomu(int poziom)
    {
        liczba_zyc =Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_liczba_zyc"));
        liczba_naboi=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_liczba_naboi"));
        liczba_zyc_wroga=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_liczba_zyc_wroga"));
        liczba_pilek=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_liczba_pilek"));
        predkosc_wroga=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_predkosc_wroga"));
        bonusy_poziomu=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_bonusy_poziomu"));
        rozmiar_pilki=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_rozmiar_pilki"));
        zmiana_ruchu_wroga=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_zmiana_ruchu_wroga"));
        czas_gry=Integer.parseInt(konfiguracja.properties.getProperty(poziom+"_czas_gry"));
    }



    public void addNotify()
    {
        super.addNotify();
        createBufferStrategy(2);
        bs = getBufferStrategy();
    }


    /**
     * medota obslugujaca co ma sie stac po nacisnieciu przycisku
     * @param keyEvent
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) { }

    /**
     * Metoda opisujaca co ma sie stac gdy nacisniety sie przycisk
     * @param keyEvent zdarzenie zwiaznae z klawiatura
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if(keyCode==KeyEvent.VK_SPACE)obsluga_naboi();
        if(keyCode==KeyEvent.VK_ESCAPE)gra();
        b.keyPressed(keyEvent);
    }

    /**
     * Metoda opisujaca co ma sie stac gdy nacisniety sie przycisk
     * @param keyEvent zdarzenie zwiaznae z klawiatura
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {

        b.keyReleased(keyEvent);
    }

    /**
     * metoda otrzymujaca wiadomosc z klasy zdarzenie o rodzaju zdarzenia jakie zaszło oraz odpowiednie dotosowanie zmiennych do zaszłego zdarzenia
     * @param az
     */
    @Override
    public void akcja_zderzenia(Zdarzenie_zderzenia az)
    {
        String akcja = az.pobranie_zdarzenia();
        switch (akcja) {
            case  "naboj-wrog":
                liczba_punktow+=10;
                liczba_zyc_wroga-=1;
                obsluga_bonusu();
                break;
            case "pilka-bohater":
                if(liczba_zyc>0) {
                    liczba_zyc-=1;
                    liczba_punktow-=5;
                    gra=false;
                    liczba_pilek+=1;
                }else if(this.isVisible()==true){
                    informacja_dla_obserwatora(new Zdarzenie_panelu_gry("koniec",liczba_punktow));
                }
                break;
            case "bonus-bohater":
                ustawienie_bonusu(az.pobranie_bonusu());
                break;
        }
    }

    /**
     * metoda wyswietlajaca tekst ekranu
     * @param dbg
     */
    public void tekst_ekranu(Graphics dbg)
    {
        dbg.setColor(Color.BLACK);
        int y=(int)Math.round(getHeight()*0.04);
        dbg.setFont(new Font("TimesRoman",Font.PLAIN,y));
        dbg.drawString("zycie "+liczba_zyc,(int)Math.round(getWidth()*0.01),y);
        dbg.drawString("punkty "+liczba_punktow,(int)Math.round(getWidth()*0.2),y);
        dbg.drawString("czas "+(int)Math.round(czas_gry),(int)Math.round(getWidth()*0.4),y);
        dbg.drawString("naboje "+liczba_naboi,(int)Math.round(getWidth()*0.6),y);
        dbg.drawString("poziom "+poziom,(int)Math.round(getWidth()*0.8),y);
    }

    /**
     * metoda obslugujaca dodawanie pilek do ekranu
     */
    private void obsluga_pilek()
    {
        if (liczba_pilek > 0)
        {
            i++;
                if (i%50==0)
                {
                    p.add(new Pilka(w.getPolozenie_x(), w.getPolozenie_y(), getWidth(), getHeight(), rozmiar_pilki));
                    liczba_pilek--;
                }
        }
    }

    /**
     * metoda dodajaca naboje
     */
    private void obsluga_naboi()
    {
        if(liczba_naboi>0)
        {
            n.add(new Naboj(b.getPolozenie_x(), b.getPolozenie_y(),getWidth(),getHeight()));
            liczba_naboi--;
        }
    }

    /**
     * metoda losowa wyrzucajaca bonusy
     */
    private void obsluga_bonusu()
    {
        if(bonusy_poziomu>0)
        {
            boolean numer= los.nextBoolean();
            if(numer){
                  bon.add(new Bonus(w.getPolozenie_x(),w.getPolozenie_y(),getWidth(),getHeight()));
                  bonusy_poziomu--;
            }
        }
    }

    /**
     * metoda dostosowywujaca componenty ekrranu do aktualnego rozmiaru okna
     */
    public void zmiana_rozmiaru_okna()
    {
       b.dostosowanie_rozmiaru(getWidth(),getHeight());
       w.dostosowanie_rozmiaru(getWidth(),getHeight());

        for (Pilka np : p) {
            np.dostosowanie_rozmiaru(getWidth(),getHeight());
        }
        for (Naboj np : n) {
            np.dostosowanie_rozmiaru(getWidth(),getHeight());
        }
        for (Bonus np : bon) {
            np.dostosowanie_rozmiaru(getWidth(),getHeight());
        }

    }

    /**
     * tworzenia nowego listenera
     * @param obserwator_panelu_gry
     */
    public void nowy_sluchacz(Obserwator_panelu_gry obserwator_panelu_gry){obserwatorzy.add(obserwator_panelu_gry);}

    /**
     * usuwanie listenera
     * @param obserwator_panelu_gry
     */
    public void usun_sluchacza(Obserwator_panelu_gry obserwator_panelu_gry){obserwatorzy.remove(obserwatorzy.indexOf(obserwator_panelu_gry));}


    public void informacja_dla_obserwatora(Zdarzenie_panelu_gry zdarzenie_panelu_gry){
        for (Obserwator_panelu_gry obserwator_panelu_gry : obserwatorzy) {
            obserwator_panelu_gry.akcja_panelu_gry(zdarzenie_panelu_gry);
        }
    }

    /**
     * metoda obslugujaca watek glowny
     */
    public void run() {

        try {

        while (true) {

            if(gra) {

                do {
                    do {
                        Graphics dbg = bs.getDrawGraphics();

                        dbg.clearRect(0, 0, getWidth(), getHeight());

                        wczytaj_nowy_poziom(dbg);

                        obsluga_pilek();

                        czas_gry -= (czas_watku * 0.001);

                        zd.aktualizacja_zderzen();
                        for (Naboj np : n) {
                            np.obraz_naboju(dbg);
                        }
                        for (Pilka np : p) {
                            np.obraz_pilki(dbg);
                        }
                        for (Bonus np : bon) {
                            np.obraz_bonusu(dbg);
                        }
                        w.obraz_wroga(dbg, liczba_zyc_wroga);
                        b.obraz_bohatera(dbg);

                        tekst_ekranu(dbg);
                        dbg.dispose();

                    } while (bs.contentsRestored());
                    bs.show();
                }while(bs.contentsLost());
                }
            Thread.sleep(czas_watku);
        }
            }catch (Exception e) { }
        }
    }
















