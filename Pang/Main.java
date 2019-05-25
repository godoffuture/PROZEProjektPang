
import javax.swing.*;
import java.io.*;
import java.net.Socket;

/**
 * głowna klasa
 */

class Main {
    /**
     * zmienna static okreslajaca adresIP
     */
    private static String adresIP;
    /**
     * zmienna okerslajaca port na ktorym ma dzialac program
     */
    private static int Port;

    private static Socket polaczenie_z_serwerem() {

        try {
            BufferedReader br = new BufferedReader(new FileReader("Pang\\pliki\\ip_konf.txt"));
            adresIP=br.readLine();
            Port = Integer.parseInt(JOptionPane.showInputDialog(null,"podaj port na ktorym ma działac aplikacja:", JOptionPane.INFORMATION_MESSAGE));
            Socket socket = new Socket(adresIP, Port);
            OutputStream os = socket.getOutputStream();
            PrintWriter pw = new PrintWriter(os, true);
            pw.println("zaloguj");
            InputStream is = socket.getInputStream();
            br = new BufferedReader(new InputStreamReader(is));
            if(br.readLine().contains("zalogowano")) {
                return socket;
            }
            else{
                return null;
            }
        }
        catch (Exception e) {
            System.out.println("blad nawiazania polaczenia: "+e);
        }
        return null;
    }


    public static void main(String[] args)throws Exception
    {
        Polaczenie_serwer.start(polaczenie_z_serwerem());
    }
}
