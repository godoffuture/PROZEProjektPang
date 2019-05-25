import java.awt.*;
import java.util.ArrayList;
import java.lang.String;
import obiekty_panelu_gry.*;
import intrfejs.*;
import zdarzenia.Zdarzenie_zderzenia;

/**
 * klasa zdarzen panelu gry
 */
public class Zderzenie implements Sluchacz_zderzenia{

    /**
     * zmienna okreslajaca czy jest gra
     */
    private boolean pauza;
    /**
     * zmienna okreslajca puntky
     */
    private int punkty;
    /**
     * zmienna okrslajca typ zdarzenia
     */
    private String typ_zdarzenia;
    /**
     * zmienna kreslajca rodzaj bonusu
     */
    private String rodzaj_bonusu;

    private ArrayList<Obserwator_zderzenia> obserwatorzy;

    private Bohater bohater;

    private ArrayList<Naboj> naboje;

    private Wrog wrog;

    private ArrayList<Bonus> bonusy;

    private ArrayList<Pilka> pilki;

    /**
     * konstruktor klasy zdarzen
     * @param b
     * @param n
     * @param w
     * @param p
     * @param bon
     */
    public Zderzenie(Bohater b,ArrayList<Naboj> n,Wrog w,ArrayList<Pilka> p,ArrayList<Bonus> bon)
    {
        obserwatorzy = new ArrayList<>();
        bohater=b;
        naboje=n;
        wrog=w;
        bonusy= bon;
        pilki=p;
        punkty=0;
        typ_zdarzenia =null;
        rodzaj_bonusu=null;
        pauza=false;

    }

    /**
     * metoda sprawdzajaca czy nastapilo zderzenie
     */
    public void aktualizacja_zderzen(){

        for(Pilka pilka:pilki)
        {
            if(zderzenie_zb(pilka))
            {
                punkty=0;
                typ_zdarzenia = "pilka-bohater";
                informacja_dla_obserwatora();
            }
        }

        for(Naboj naboj:naboje)
        {
            if (zderzenie_nw(naboj))
            {
                punkty+=10;
                typ_zdarzenia = "naboj-wrog";
                informacja_dla_obserwatora();
            }
        }

        for(Bonus bonus:bonusy)
        {
            if(zderzenie_bb(bonus))
            {
                punkty=0;
                rodzaj_bonusu=bonus.getRodzaj_bonusu();
                typ_zdarzenia = "bonus-bohater";
                informacja_dla_obserwatora();
            }
        }
    }

    /**
     * metoda zwracaja czy nastapilo zderzenie nabouj z wrogiem
     * @param n
     * @return
     */
    private boolean zderzenie_nw(Naboj n)
    {
        if(n.Stan_naboju()) {
            if(wrog.getPole_wroga().intersects(n.getPole_naboju())){
                n.usun_naboj();
                return true;
            }
        }return false;
    }

    /**
     * metoda zwracaja czy nastapilo zderzenie pilki z bohaterem
     * @param p
     * @return
     */
    private boolean zderzenie_zb(Pilka p)
    {
        if(p.Stan_pili()) {
            if(bohater.getPole_bohatera().intersects(p.getPole_pilki())){
                p.usun_pilke();
                return true;
            }
        }return false;
    }

    /**
     * metoda zwracaja czy nastapilo zderzenie bonusu z bohaterem
     * @param b
     * @return
     */
    private boolean zderzenie_bb(Bonus b)
    {
        if(b.Stan_bonusu()) {
            if(bohater.getPole_bohatera().intersects(b.getPole_bonusu())){
                b.usun_bonus();
                return true;
            }
        }return false;
    }


    /**
     * dodawanie obserwtorow
     * @param obserwator
     */
    @Override
    public void nowy_sluchacz(Obserwator_zderzenia obserwator) { obserwatorzy.add(obserwator);}

    /**
     * usuwanie obsewtorow
     * @param obserwator
     */
    @Override
    public void usun_sluchacza(Obserwator_zderzenia obserwator) { obserwatorzy.remove(obserwatorzy.indexOf(obserwator));}

    @Override
    public void informacja_dla_obserwatora(){
        Zdarzenie_zderzenia akcja_zdarzenia = new Zdarzenie_zderzenia(typ_zdarzenia,punkty,rodzaj_bonusu);
        for(Obserwator_zderzenia obserwator:obserwatorzy){
            obserwator.akcja_zderzenia(akcja_zdarzenia);
        }
    }
}
