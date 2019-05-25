import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 *klasa pobierajaca wratosci z pliku konfiguracyjnego
 */
public class Konfiguracja {
    /**
     *tworzenie pliku .proprties
     */
    final File plik = new File("pliki/konfiguracja.properties");
    /**
     *przyszly projekt .proprties
     */
    Properties properties = new Properties();

    public void loadProperties(){
        /**
         * strumien wejsciowy
         */
        InputStream is;
        try {
            is = new FileInputStream(plik);
            /**
             *ladowanie strumienia
             */
            properties.load(is);

        } catch (FileNotFoundException e) {
            /**
             *test istnienia pliku
             */
            e.printStackTrace();
            System.out.print("brak pliku");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.print("blad pliku");
        }
    }
}
