package intrfejs;

import zdarzenia.Zdarzenie_panelu_gry;

/**
 * interfejs obserwatora panelu gry
 */
public interface Obserwator_panelu_gry {
    /**
     * metoda mowiaca o zajciu zdarzenia w panelu gry
     * @param zpg
     */
    public void akcja_panelu_gry(Zdarzenie_panelu_gry zpg);
}
