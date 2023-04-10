import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;


public class Client implements Runnable {

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
        System.out.println("thread started");
        this.listenMessage();
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

        }
        catch(Exception e){
            e.printStackTrace();
        }

        return "closed";
    }



    public boolean mesHandeld(String data){

        if(data.equals(""))
            return true;

        HashMap<String,Object> stringData = JsonFomatter.jsonToHashmap(data);

        if(stringData.toString().equals("{}") && stringData.containsKey("messageType") )
            return true;

        String key = (String)stringData.get("messageType");

        switch (key){
            case "auth":
                this.sockAuth();
                break;
            case "close":
                return false;
            case "mess":
                sendMessage("sd");
            case "getDoc":
                this.sendDoc();
                break;
            default:
                System.out.println("key: " + key + " not implemented");
        }

        return true;
    }
    public void closeClient(){

        try {
            this.in.close();
            this.out.close();
            this.socket.close();
        }catch(Exception e){
            e.printStackTrace();
        }


    }

    public void sockAuth(){
        Message message = new Message();
        message.put("clientId",ClientInfo.uuid);
        message.put("messageType",messageTypes.Auth);

        sendJson(message);
    }

    public void sendMessage(String data){
        Message message = new Message();
        message.put("clientId",ClientInfo.uuid);
        message.put("messageType",messageTypes.DocumentDone);
        message.put("receiver","5d5e5b5a-8b3b-4578-95fd-831abeefec96");
        sendJson(message);

    }
    public void sendJson(Message message){
        String data = message.toJson();
        out.println(data);


    }
    public void sendInstruction(String instruction){
        Message message = new Message();
        message.put("clientId",ClientInfo.uuid);
        message.put("messageType",messageTypes.Instruction);
        message.put("receiver","5d5e5b5a-8b3b-4578-95fd-831abeefec96");
        message.put("Instruction", instruction);
        sendJson(message);
    }
    public void sendString(String message){

        out.println(message);
    }

    public void sendDoc(){
        var a = JsonFomatter.getFile("src/updateFilesWin.json");
        Message message = new Message();
        message.put("clientId",ClientInfo.uuid);
        message.put("messageType",messageTypes.Document);
        message.put("receiver","5d5e5b5a-8b3b-4578-95fd-831abeefec96");
        message.put("filename","");

        ArrayList<Object> list = (ArrayList<Object>) a.get("filenames");
        String path = a.get("path").toString().replace("\\\\","\\").replace("_","");


        for (Object name: list) {
            String fileData = "";
            try {

                System.out.println(path + name.toString());

                File myObj = new File(path + name.toString());
                Scanner myReader = new Scanner(myObj);
                while (myReader.hasNextLine()) {
                    fileData += myReader.nextLine() + "\n";

                }
                myReader.close();

            } catch (FileNotFoundException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }

            message.replace("filename",name.toString());

            sendString(message.toJson() + " $file$\n"+ fileData+"\n$!file$");
        }

        message.replace("messageType",messageTypes.DocumentDone);
        message.remove("filename");
        sendString(message.toJson());


    }


    public static class messageTypes{
        public final static String Auth = "auth";
        public final static String Instruction = "instr";
        public final static String Message = "mes";
        public final static String Document = "doc";
        public final static String DocumentDone = "docDone";


    }


}
