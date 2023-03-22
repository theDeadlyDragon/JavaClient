

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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

            Gson gson = new Gson();

            System.out.println(gson.toJson(new Message()));


            out.println(gson.toJson(new Message()));

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