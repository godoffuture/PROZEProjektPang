package zdarzenia;
import java.lang.String;
/**
 * klasa zdarzen zderzen/kolizji
 */
public class Zdarzenie_zderzenia {

    /**
     * zmienna okreslajaca komende zdarzenia
     */
    private String typ_zdarzenia;
    /**
     * zmienna okreslajaca bonus
     */
    private String rodzaj_bonusu;
    /**
     * zmienna liczby punktow
     */
    private int punkty;

    public Zdarzenie_zderzenia(String typ_zdarzenia,int punkty,String rodzaj_bonusu)
    {
        this.typ_zdarzenia=typ_zdarzenia;
        this.rodzaj_bonusu=rodzaj_bonusu;
        this.punkty=punkty;
    }


    /**
     * funkcja zwaracja komende zdarzenia
     * @return
     */
    public String pobranie_zdarzenia(){return typ_zdarzenia;}
    /**
     * metoda zwracajaca liczbe punktow
     */
    public int liczba_punktow(){return punkty;}
    /**
     * metoda wracajaca rodzaj bonusu
     */
    public String pobranie_bonusu(){return rodzaj_bonusu;}


}
