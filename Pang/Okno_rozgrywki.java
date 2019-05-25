import intrfejs.*;

import zdarzenia.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentListener;
import java.awt.event.*;

import java.net.Socket;
import java.util.ArrayList;
/**
 *klasa okna rozgrywki
 */
public  class Okno_rozgrywki extends Frame implements Sluchacze_okna_rozgrywki,Obserwator_panelu_gry,ComponentListener{

    /**
     * lista tworzaca obserwatorow
     */
    private ArrayList<Obserwator_okna_rozgrywki> obserwatorzy;

    private Panel_gry panel_gry;
    Socket socket;

    private TablicaWynikow tablicaWynikow;
    /**
     * zmienna okreslajcaa nick gracza
     */
    private String nick;

    /**
     * konstruktor klasy okna rozgrywki
     * @param szerokosc
     * @param wysokosc
     * @param nick
     */
     Okno_rozgrywki(int szerokosc, int wysokosc, String nick, Socket socket){
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
         setSize(szerokosc,wysokosc);

         this.socket=socket;

         this.nick=nick;

         panel_gry = new Panel_gry(getWidth(),getHeight());
         panel_gry.nowy_sluchacz(this);
         add(panel_gry);
         pack();
         setVisible(true);
         setFocusable(true);
         setLocationRelativeTo(null);
         addComponentListener(this);

         new Thread(panel_gry).start();
         addKeyListener(panel_gry);

         obserwatorzy = new ArrayList<>();

    }


    /**
     * metoda otrzymujaca zdarzneie z panelu gry i dostosowywujaca do nie go zachowanie okna rozgrywki
     * @param zdarzenia_panelu_gry
     */
    @Override
    public void akcja_panelu_gry(Zdarzenie_panelu_gry zdarzenia_panelu_gry) {
        String akcja = zdarzenia_panelu_gry.pobranie_akcji();
        switch (akcja) {
            case "koniec":
                tablicaWynikow=new TablicaWynikow(nick,zdarzenia_panelu_gry.getPunkty());
                zakonczenie_gry(zdarzenia_panelu_gry.getPunkty());
                tablicaWynikow.zapis(socket);

                break;
            case "koniec_poziomow":
                tablicaWynikow=new TablicaWynikow(nick,zdarzenia_panelu_gry.getPunkty());
                brak_poziomow(zdarzenia_panelu_gry.getPunkty());
                tablicaWynikow.zapis(socket);
                break;
        }

    }

    /**
     * metoda tweorzaca onka po zakonczeniu gry
     * @param punkty
     */
    public void zakonczenie_gry(int punkty)
    {
         panel_gry.gra();
        Object[] wybor = {"koniec dzialania programu", "restart gry", "menu"};
        switch (JOptionPane.showOptionDialog(this, "Koniec gry "+punkty+" pkt Brawo!", "koniec",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, wybor, wybor[0]))
        {
            case JOptionPane.YES_OPTION:
                informacja_dla_obserwatora(new Zdarzenia_okna_rozgrywki("koniec dzialania programu"));

                break;
            case JOptionPane.NO_OPTION:
                informacja_dla_obserwatora(new Zdarzenia_okna_rozgrywki("restart gry"));

                break;
            case JOptionPane.CANCEL_OPTION:
                informacja_dla_obserwatora(new Zdarzenia_okna_rozgrywki("menu"));

                break;
            default:
                break;
        }
    }

    /**
     * metoda tworzaca okno informujaca o braku dalszej liczby poziomow
     * @param punkty
     */
    public void brak_poziomow(int punkty)
    {
        panel_gry.gra();
        Object[] wybor = {"koniec dzialania programu", "restart gry", "menu"};
        switch (JOptionPane.showOptionDialog(this, "Brak kolejnych poziomow zdobyles "+punkty+" pkt Brawo!", "koniec"
                , JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, wybor, wybor[0])) {
            case JOptionPane.YES_OPTION:
                informacja_dla_obserwatora(new Zdarzenia_okna_rozgrywki("koniec dzialania programu"));

                break;
            case JOptionPane.NO_OPTION:
                informacja_dla_obserwatora(new Zdarzenia_okna_rozgrywki("restart gry"));

                break;
            case JOptionPane.CANCEL_OPTION:
                informacja_dla_obserwatora(new Zdarzenia_okna_rozgrywki("menu"));

                break;
            default:
                break;
        }
    }

    /**
     * metoda zmieniająca rozmiar komponentow panlu gry
     * @param e
     */
    @Override
    public void componentResized(ComponentEvent e){
        panel_gry.zmiana_rozmiaru_okna();
           }

    /**
     * Invoked when the component's position changes.
     */
    @Override
    public void componentMoved(ComponentEvent e){}

    /**
     * Invoked when the component has been made visible.
     */
    @Override
    public void componentShown(ComponentEvent e){}

    /**
     * Invoked when the component has been made invisible.
     */
    @Override
    public void componentHidden(ComponentEvent e){}

    @Override
    public void nowy_sluchacz(Obserwator_okna_rozgrywki sor) { obserwatorzy.add(sor); }

    /**
     * usuwanie listenera
     *
     * @param sor
     */
    @Override
    public void usun_sluchacza(Obserwator_okna_rozgrywki sor) { obserwatorzy.remove(obserwatorzy.indexOf(sor)); }

    /**
     * metoda informujaca listenerow o zajsciu zdarzenia
     *
     * @param zor
     */
    @Override
    public void informacja_dla_obserwatora(Zdarzenia_okna_rozgrywki zor) {
        for (Obserwator_okna_rozgrywki sor : obserwatorzy) {
            sor.akcja_okna_rozgrywki(zor);
        }
    }

}






