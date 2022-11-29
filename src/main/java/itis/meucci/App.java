package itis.meucci;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
   
        //questo è per la connessione tra client contemporaneamente
        ServerStr servente = new ServerStr();
        for(;;)
        {
            servente.attendi();
            servente.comunica();
        }
        
    }
}
