import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Formatter;
import java.util.HashMap;


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
            String line = "";

            while(true)
            {
                line = in.readLine();
                if(line == null)
                    break;


                if(line != null && !mesHandeld(line))
                    return line;


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

    public boolean mesHandeld(String data){

        HashMap<String,Object> stringData = JsonFomatter.jsonToHashmap(data);

        System.out.println(stringData.toString());

        if(stringData.toString().equals("{}") && stringData.containsKey("messageType") )
            return false;

        String key = (String)stringData.get("messageType");
        System.out.println(key);

        switch (key){
            case "auth":
                this.sockAuth();
                break;
            default:
                System.out.println("key: " + key + " not implemented");
        }

        return true;
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
