package intrfejs;

import zdarzenia.Zdarzenie_panelu_gry;
/**
 * interfejs obserwatora
 */
public interface Sluchacz_panelu_gry {
    /**
     * tworzenia nowego listenera
     * @param obserwator_panelu_gry
     */
    public void nowy_sluchacz(Obserwator_panelu_gry obserwator_panelu_gry);

    /**
     * usuwanie listenera
     * @param obserwator_panelu_gry
     */
    public void usun_sluchacza(Obserwator_panelu_gry obserwator_panelu_gry);

    /**
     * metoda informujaca listenerow o zajsciu zdarzenia
     * @param zdarzenie_panelu_gry
     */
    public void informacja_dla_obserwatora(Zdarzenie_panelu_gry zdarzenie_panelu_gry);
}
