

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

        System.out.println(ClientInfo.getClientData());
        System.out.println("Loading config");

        Client tcpClient =  new Client("192.168.178.124",8080);

        System.out.println(tcpClient.listenMessage());
    }
}