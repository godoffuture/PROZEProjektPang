package intrfejs;

import zdarzenia.Zdarzenie_zderzenia;

public interface Sluchacz_zderzenia {
    /**
     * tworzenia nowego listenera
     * @param oz
     */
    public void nowy_sluchacz(Obserwator_zderzenia oz);

    /**
     * usuwanie listenera
     * @param oz
     */
    public void usun_sluchacza(Obserwator_zderzenia oz);

    /**
     * metoda informujaca listenerow o zajsciu zdarzenia
     */
    public void informacja_dla_obserwatora();
}
