package zdarzenia;

/**
 * klasa zdarzen panelu gry
 */
public class Zdarzenie_panelu_gry {
    /**
     * zmienna opisujaca rodzaj komendy wyslanaj przez panel gry
     */
    private String komanda;
    /**
     * zmienna okreslajaca liczbe punktow zwrocownych z panelu gry
     */
    private int punkty;

    /**
     * konstruktor zedarzen panelu gry
     * @param komenda
     * @param punkty
     */
    public Zdarzenie_panelu_gry (String komenda,int punkty)
    {
    this.komanda=komenda;
    this.punkty=punkty;
    }

    /**
    * pobranie zdarzenia
    * @return komenda
    */
    public String pobranie_akcji(){return komanda;}

    /**
     * metoda zwracajaca punkty panelu gry
     * @return
     */
    public int getPunkty() { return punkty;}

}
