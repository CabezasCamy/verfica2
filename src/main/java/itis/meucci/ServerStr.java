package itis.meucci;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ServerStr {
    ServerSocket server = null;
    Socket client = null;
    String stringaRicevuta = null;
    String StrinngaModificata = null;
    BufferedReader inDalClient;
    DataOutputStream outVersoClient;
    XmlMapper xmlMapper = new XmlMapper();

    public Socket attendi()
    {
        try
        {
            System.out.println("1: il server e' in esecuzione" + '\n');
            //attivo il server sulla porta, apertura della porta
            server = new ServerSocket(2018);
            //rimane in attesa di un cliente, istruzione bloccante
            client = server.accept();
            server.close(); //chiusura della porta, ma non del socket
            //stream di scrittura e di lettura
            inDalClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outVersoClient = new DataOutputStream(client.getOutputStream());
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server!");
            System.exit(1);
        }
        //
        return client;
    }

    public void comunica() throws IOException
    {
        try
        {

            System.out.println("3: benvenuto client, risponderò con un Messaggio contenente l'elenco dei biglietti disponibili" + '\n');
            //legge
            Messaggi m = xmlMapper.readValue(inDalClient.readLine(), Messaggi.class);
            System.out.println("6: ho ricevuto la stringa => " + stringaRicevuta + '\n');
            //modifico la stringa ricevuta dal client
            Biglietti b1 = new Biglietti("2A");
            Biglietti b2 = new Biglietti("6A");
            Biglietti b3 = new Biglietti("2C");
            Biglietti b4 = new Biglietti("4D");

            m.getLista().add(b1);
            m.getLista().add(b2);
            m.getLista().add(b3);
            m.getLista().add(b4);

            StrinngaModificata = stampa(m.getLista());
            //fatto, ora la invio
            System.out.println("7: invio la stringa..." + '\n');
            //invio la stringa modificata
            outVersoClient.writeBytes(StrinngaModificata + '\n');

            Messaggi carrello = new Messaggi();
            //ricevo le stringhe
            stringaRicevuta = inDalClient.readLine();
            do{
                for(int i = 0; i < m.getLista().size(); i++)
                {
                    if(stringaRicevuta == m.getLista().get(i).toString())
                    {
                        carrello.getLista().add(m.getLista().get(i));
                        m.getLista().remove(i);
                    }
                }
            }while(stringaRicevuta != "FINE");

            //manda l'elenco dei biglietti acquistati
            outVersoClient.writeBytes(stampa(carrello.getLista()) + '\n');


            //termino elaborazione sul server: chiudo la connessione, ovvero il socket
            System.out.println("9: fine elaborazione ... arrivederci");
            client.close(); //chiusura socket
        }
        catch(Exception e)
        {
        }
    }

    public String stampa(ArrayList <Biglietti> l)
    {
        String s = "";

        for(int i = 0; i < l.size(); i++)
        {
            s = s + " , " + l.get(i);
        }
        return s;
    }
}
