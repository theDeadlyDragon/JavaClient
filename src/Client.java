import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;



public class Client {

    String server;
    int port;
    Socket socket;
    PrintStream out;
    BufferedReader in;



    public Client(String server, int port) {
        this.server = server;
        this.port = port;

        try{
            this.socket = new Socket(server,port);
            out = new PrintStream(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.sockAuth();

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void run(){

    }

    public String listenMessage() {
        String output = "";



        try{
            String line = in.readLine();

            while( line != null )
            {
                System.out.println("1: " + line );
                line = in.readLine();
            }

            in.close();
            out.close();
            socket.close();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        return output;
    }

    public void sockAuth(){
        Message message = new Message();
        message.put("clientId",ClientInfo.uuid);
        message.put("messageType",messageTypes.Auth);

        sendJson(message);
    }

    public void sendMessage(String message){

    }
    public void sendJson(Message message){
        String data = message.toJson();
        out.println(data);
    }




    public static class messageTypes{
        public final static String Auth = "auth";
        public final static String Instruction = "instr";


    }


}
