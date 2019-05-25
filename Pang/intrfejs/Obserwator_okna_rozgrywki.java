package intrfejs;

import zdarzenia.Zdarzenia_okna_rozgrywki;

/**
 * interfejs obserwatora okna rozgrywki
 */
public interface Obserwator_okna_rozgrywki {
    /**
     * metoda mowiaca o zajsciu zdarzenia w oknie rozgrywki
     * @param zor
     */
    public void akcja_okna_rozgrywki(Zdarzenia_okna_rozgrywki zor);
}
