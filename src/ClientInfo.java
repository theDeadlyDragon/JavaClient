import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class ClientInfo {
    private static ClientInfo instance;

    public static UUID uuid = UUID.fromString("f8cc13c3-62e9-4860-8c99-cadf2aaddd46");
    public static String name = "";
    public static String fileName = "src/config.json";
    private ClientInfo(){
        readConfigFile();
        if(uuid == null)
            uuid = UUID.randomUUID();
    }

    private void readConfigFile(){
        String fileData = "";
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileData += myReader.nextLine();

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        HashMap<String,Object> data = JsonFomatter.jsonToHashmap(fileData);
        if(data.containsKey("id"))
            uuid =  UUID.fromString(data.get("id").toString());
        if(data.containsKey("name"))
            name =  data.get("name").toString();

    }
    public static ClientInfo getInstance(){
        if(instance == null)
            instance = new ClientInfo();

        return instance;
    }

    public static String getClientData() {
        return "clientName = " + name + "\nClientId = " + uuid;
    }
}
