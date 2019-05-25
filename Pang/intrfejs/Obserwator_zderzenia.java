package intrfejs;

import zdarzenia.Zdarzenie_zderzenia;

/**
 * interfejs wzorca zderzenia
 */
public interface Obserwator_zderzenia {
    /**
     * metoda zwiazana z akcja zdarzenia zderzenia
     * @param akcja_zadarzenia
     */
    public void akcja_zderzenia(Zdarzenie_zderzenia akcja_zadarzenia);
}
