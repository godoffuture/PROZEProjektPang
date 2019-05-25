package zdarzenia;

/**
 * klasa obsugujaca zdarzenia okna rozgrywki
 */
public class Zdarzenia_okna_rozgrywki {
    /**
     * zmienna okreslajaca rodzaj zdarzenia na oknie rozgrywki
     */
      private String komanda_akcja;

    /**
     * metoda zwracajaca zdarzenie na oknie rozgrywki
     * @param komenda
     */
    public Zdarzenia_okna_rozgrywki(String komenda)
    {
       this.komanda_akcja=komenda;
    }

    /**
     * pobranie zdarzenia
      * @return komenda
     */
    public String pobranie_akcji(){return komanda_akcja;}

}
