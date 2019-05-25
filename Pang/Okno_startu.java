
import intrfejs.*;

import zdarzenia.Zdarzenia_okna_rozgrywki;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.Integer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.util.*;
import java.lang.Exception;

/**
 *klasa okna staru
 */
public class Okno_startu extends JFrame implements ActionListener, Obserwator_okna_rozgrywki {

    private Socket socket;

    private JPanel p,p1,p2,p11,p12,p13;

    private JButton nowa_gra,tablica_wynikow,pomoc;
    /**
     * zmienna okreslajaca nick gracza
     */
    private String nick = null;

    private Konfiguracja konf=new Konfiguracja();

    private Okno_rozgrywki okno_rozgrywki;

    private ArrayList<String > wypis=new ArrayList<String>();

    private ArrayList<TablicaWynikow> gracze = new ArrayList<TablicaWynikow>();


    /**
     * konstruktor panelu gry
     */
    public Okno_startu(Socket socket)
    {
        this.socket=socket;
        konf.loadProperties();

        nowa_gra = new JButton("nowa gra");
        nowa_gra.setFont(new Font(konf.properties.getProperty("_rodzaj"),
                Integer.parseInt(konf.properties.getProperty("_styl")),
                Integer.parseInt(konf.properties.getProperty("_wielkosc_duze"))));

        nowa_gra.setActionCommand("nowa_gra");
        nowa_gra.addActionListener(this);

        p11 = new JPanel();
        p11.add(nowa_gra);

        pomoc = new JButton("pomoc");
        pomoc.setFont(new Font(konf.properties.getProperty("_rodzaj"),
                Integer.parseInt(konf.properties.getProperty("_styl")),
                Integer.parseInt(konf.properties.getProperty("_wielkosc_duze"))));

        pomoc.setActionCommand("pomoc");
        pomoc.addActionListener(this);
        p12 = new JPanel();
        p12.add(pomoc);

        tablica_wynikow = new JButton("tablica wynikow");
        tablica_wynikow.setFont(new Font(konf.properties.getProperty("_rodzaj"),
                Integer.parseInt(konf.properties.getProperty("_styl")),
                Integer.parseInt(konf.properties.getProperty("_wielkosc_duze"))));

        tablica_wynikow.setActionCommand("tablica_wynikow");
        tablica_wynikow.addActionListener(this);

        p13 = new JPanel();
        p13.add(tablica_wynikow);

        p1 = new JPanel();
        p1.setLayout(new GridLayout(3,1));

        p1.add(p11);
        p1.add(p12);
        p1.add(p13);

        p2 = new JPanel();
        p2.setLayout(new GridLayout(5,2,10,20));
        p2.setFont(new Font(konf.properties.getProperty("_rodzaj"),
                Integer.parseInt(konf.properties.getProperty("_styl")),
                Integer.parseInt(konf.properties.getProperty("_wielkosc_duze"))));


        p = new JPanel();
        p.setLayout(new GridLayout(2,1));
        p.add(p2);
        p.add(p1);

        add(p);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(new Dimension(Integer.parseInt(konf.properties.getProperty("_dlugosc")),
                Integer.parseInt(konf.properties.getProperty("_szerokosc"))));
        setLocationRelativeTo(null);
        setVisible(true);
    }



    /**
     * metoda tworzaca okno rozgrywki
     * @param nick
     */
    void dodanie(String nick)
    {
        okno_rozgrywki = new Okno_rozgrywki(getWidth(),getHeight(),nick,socket) ;
        okno_rozgrywki.nowy_sluchacz(this);
    }

    /**
     * metoda sluchajaca zdarzen
     * @param a
     */

    @Override
    public void actionPerformed(ActionEvent a)
    {
        String akcja = a.getActionCommand();

        switch(akcja){
            case "nowa_gra":
                while(nick==null || nick.length()==0) {
                    nick = JOptionPane.showInputDialog(this, "podaj swoj nick", "okno nicku", JOptionPane.INFORMATION_MESSAGE);
                    if(nick==null){
                        break;
                    }
                }
                if(nick==null){
                    this.setVisible(true);
                    break;
                }
                this.dispose();
                dodanie(nick);
                break;
            case "tablica_wynikow":
                try{ panel_wynikow();} catch (Exception e){}
                JOptionPane.showMessageDialog(this,
                        wypis.get(0)+ wypis.get(1)+ wypis.get(2)+ wypis.get(3)+ wypis.get(4)+
                                wypis.get(5)+ wypis.get(6)+ wypis.get(7)+ wypis.get(8)+ wypis.get(9)
                        ,"tablica wynikow od najlepszego",JOptionPane.INFORMATION_MESSAGE);
                break;

            case "pomoc":
                JOptionPane.showMessageDialog(this,"1.gracz porusza sie w lewo i prawo strzałkami na klawiaturze \n" +
                        "2.gracz spacja strzela nabojami we wroga(prostokat poruszajacy sie u gory ekranu)\n" +
                        "3.celem gracza jest zabicie wroga lub przetrwanie wyznaczonego czasu na planszy gry \n" +
                        "4.za przejscie poziomu gry gracz otrzymuje 100pkt \n"+
                        "5.za trafienie nabojem gracz otrzymuje 10pkt \n"+
                        "6.liczba poziomow jest ograniczona \n"+
                        "           Tworca: Arkadiusz Majkowski \n","okno pomocy",JOptionPane.INFORMATION_MESSAGE);
                break;
        }
    }

    /**
     * metoda sluchajaca zdarzenia okna rozgrywki
     * @param zor
     */
    @Override
    public void akcja_okna_rozgrywki(Zdarzenia_okna_rozgrywki zor)
    {
        String akcja = zor.pobranie_akcji();
        switch (akcja) {

            case "koniec dzialania programu":
               try{ OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("rozlaczenie");} catch (IOException a){}
                System.exit(0);
                break;
            case "restart gry":
                okno_rozgrywki.dispose();
                okno_rozgrywki = new Okno_rozgrywki(getWidth(),getHeight(),nick,socket) ;
                okno_rozgrywki.nowy_sluchacz(this);
                break;
            case "menu":
                this.setVisible(true);
                nick=null;
                okno_rozgrywki.dispose();
                break;
            default:
                break;

        }
    }



    /**
     * metoda wczytujaca aktualna tablice wynikow
     * @throws FileNotFoundException
     */
    public void panel_wynikow() throws FileNotFoundException
    {
        if(socket!=null) {
            try {
                OutputStream os = socket.getOutputStream();
                PrintWriter pw = new PrintWriter(os, true);
                pw.println("tablica");
                InputStream is = socket.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                for (int i = 0; i < 10; i++) {
                    gracze.add(new TablicaWynikow(br.readLine(), Integer.parseInt(br.readLine())));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }else {
            File lista = new File("Pang\\pliki\\listawynikow.txt");
            Scanner scan = new Scanner(lista);
            for (int i = 0; i < 10; i++) {
                gracze.add(new TablicaWynikow(scan.nextLine(), Integer.parseInt(scan.nextLine())));
            }
        }

        Collections.sort(gracze, new Comparator<TablicaWynikow>() {
            @Override
            public int compare(TablicaWynikow o1, TablicaWynikow o2) {
                if(o2 == null) return -1;
                if(o1.getWynik() < o2.getWynik()) return 1;
                else if(o1.getWynik() > o2.getWynik()) return -1;
                return 0;
            }
        });

        for(TablicaWynikow  gracz : gracze) {
            wypis.add(gracz.wypisz());
        }
    }

}