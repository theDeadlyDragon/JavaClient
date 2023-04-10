

import java.io.BufferedReader;
import java.io.IOException;
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
    static Client client;
    public static void main( String[] args ) throws InterruptedException {

        System.out.println(ClientInfo.getClientData());
        System.out.println("Loading config");


        client =  new Client("192.168.178.66",8080);
        Thread tcpClient = new Thread(client);

        tcpClient.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true){

            try{
                String name = reader.readLine();

                // Printing the read line
                System.out.println(name);

                if(!proccesInput(name))
                    break;

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Reading data using readLine

        }




        tcpClient.join();


        System.out.println("program closed");
    }

    public static boolean proccesInput(String input){

        String key = input.split(":",2)[0];
        String value = input.split(":",2)[0];
        if(input.equals("close"))
            return false;
        else if(key.equals("instr")){
            client.sendString();

        }

        return true;
    }
}