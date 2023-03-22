

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Main
{
    public static void main( String[] args )
    {

        System.out.println(ClientInfo.getInstance().getClientData());

        String server = "localhost";
        //String path = 1000;

        System.out.println( "Loading contents of URL: " + server );

        try
        {
            // Connect to the server
            Socket socket = new Socket( server, 1000 );

            // Create input and output streams to read from and write to the server
            PrintStream out = new PrintStream( socket.getOutputStream() );
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            Message mes = new Message();

            mes.put("name",ClientInfo.getInstance().name);
            mes.put("messageType","1");
            mes.put("uuid",ClientInfo.getInstance().uuid);




            out.println(mes.toJson());

            mes.clear();

            mes.put("name",ClientInfo.getInstance().name);
            mes.put("messageType","2");
            mes.put("uuid",ClientInfo.getInstance().uuid);
            mes.put("to" ,"9d241452-6c39-4049-8d5f-75336e5078a6");

            out.println(mes.toJson());

            // Read data from the server until we finish reading the document
            String line = in.readLine();
            while( line != null )
            {


                System.out.println("1" + line );
                line = in.readLine();
            }

            // Close our streams
            in.close();
            out.close();
            socket.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}