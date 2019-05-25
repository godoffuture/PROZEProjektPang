import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Polaczenie_serwer {


    Okno_startu os;
    /**
     * Metoda startu programu
     * @param socket Socket serwera
     */
    public static void start (final Socket socket) {
        if(socket!=null) {
            konfiguracja(socket);
        } else {
            Object[] options={
                    "tak","Nie"
            };
            switch(JOptionPane.showOptionDialog(null, "Czy chcesz grać offline?","nie nawiazano polaczenia",  JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,options,options[1])){
                case JOptionPane.YES_OPTION:
                    Okno_startu os = new Okno_startu(socket);

                    break;
                case JOptionPane.NO_OPTION:
                    System.exit(0);
                    break;
                default:

                    break;
            }

        }


    }

    /**
     * Metoda pobieraj�ca pliki konfiguracyjne z serwera
     * @param socket socket serwera
     */


    private static void konfiguracja(Socket socket){
        try{
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("konfiguracja");
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<50;i++) {
                 sb.append(br.readLine()+"\n");
            }
            PrintWriter out = new PrintWriter("Pang\\pliki\\konfiguracja.properties");
            out.println(sb);
            out.close();
        }
        catch (IOException e){
            System.out.println("Blad polaczenia z serwerem, plik nie mogl zostac pobrany");
            System.out.println(e);
        }

    }
}
