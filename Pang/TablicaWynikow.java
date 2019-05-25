import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.lang.Integer;
import java.util.*;

/*
clasa zczytujaca wyniki z pliku .txt
 */

public class TablicaWynikow {
    /**
     * zmienna nicku gracza
     */
    private String nick;
    /**
     * zmienna okreslajca liczbe punktow gracza
     */
    private int punkty;

    private List<TablicaWynikow > gracze = new ArrayList<TablicaWynikow >();

    /**
     * konstruktor tablicy wynikow
     * @param nick
     * @param punkty
     */
    public TablicaWynikow (String nick, int punkty)
    {
        this.nick=nick;
        this.punkty=punkty;
    }

    /**
     * metoda zwracajaca wartosci danego gracza
     * @return
     */
    public String wypisz()
    {
        return "\n"+this.nick+" : "+this.punkty;
    }

    /**
     * metoda zwracajaca wynik danego gracza
     * @return
     */
    public int getWynik()
    {
        return punkty;
    }

    /**
     *funkcja zapisujaca do pliku
     */
    public void zapis(Socket socket)
    {


        if(socket!=null) {
            try {
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("osiagniety wynik"+"#"+nick+":"+getWynik());
            } catch (IOException e) {
                System.out.println(e);
            }
        }
        try{
            File lista = new File("Pang\\pliki\\listawynikow.txt");
            Scanner scan = new Scanner(lista);
            for (int i = 0; i < 10; i++) {
                gracze.add(new TablicaWynikow(scan.nextLine(), Integer.parseInt(scan.nextLine())));
            }

        gracze.add(new TablicaWynikow (nick,punkty));

        Collections.sort(gracze, new Comparator<TablicaWynikow>() {
            @Override
            public int compare(TablicaWynikow o1, TablicaWynikow o2) {
                if(o2 == null) return -1;
                if(o1.getWynik() < o2.getWynik()) return 1;
                else if(o1.getWynik() > o2.getWynik()) return -1;
                return 0;
            }
        });

        PrintWriter wpis = new PrintWriter("Pang\\pliki\\listawynikow.txt");
        for(TablicaWynikow  gracz : gracze) {
            wpis.println(gracz.nick);
            wpis.println(gracz.punkty);
        }
        wpis.close();
        }catch (FileNotFoundException a){System.out.print("brak");}
    }


    /*
     *funkcja odczytujaca z pliku
     */
    public void odczyt()throws FileNotFoundException{
        File lista=new File("Pang\\pliki\\listawynikow.txt");
        Scanner scan=new Scanner(lista);
        List<TablicaWynikow> gracze = new ArrayList<TablicaWynikow >();
        try {
            for (int i = 0; i < 10; i++) {
                gracze.add(new TablicaWynikow(scan.nextLine(), Integer.parseInt(scan.nextLine())));
            }
        }catch (Exception e) {
            System.out.print(e);
        }
    }
}


