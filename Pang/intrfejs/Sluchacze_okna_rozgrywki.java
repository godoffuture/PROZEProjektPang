package intrfejs;

import zdarzenia.Zdarzenia_okna_rozgrywki;

/**
 * interfejs obserwatora
 */
public interface Sluchacze_okna_rozgrywki {
    /**
     * tworzenia nowego listenera
     * @param sor
     */
    public void nowy_sluchacz(Obserwator_okna_rozgrywki sor);

    /**
     * usuwanie listenera
     * @param sor
     */
    public void usun_sluchacza(Obserwator_okna_rozgrywki sor);

    /**
     * metoda informujaca listenerow o zajsciu zdarzenia
     * @param zor
     */
    public void informacja_dla_obserwatora(Zdarzenia_okna_rozgrywki zor);
}
