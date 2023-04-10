import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.lang.reflect.Array;
import java.util.regex.Pattern;

import javax.swing.SpringLayout;

import java.util.regex.Matcher;


public class JsonFomatter {
    
    public static HashMap<String,Object> jsonToHashmap(String input){
        

        var output = new HashMap<String,Object>();

        Pattern JsonArray = Pattern.compile("(\"+[^\"]+\"+[:]+[\\[]+.+[\\]])");//(\"+[^\"]+\"+[:]+\"+[^\"]+\")
        Pattern JsonValue = Pattern.compile("\"([^\"]+)\":\\s*\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"");//(\"+[^\"]+\"+[:]+\"+[^\"]+\"+[,])|(\"+[^\"]+\"+[:]+\d+[,])
        //System.out.println("\"([^\"]+)\":\\s*\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"");
       // "([^"]+)":\s*"([^"\\]*(?:\\.[^"\\]*)*)"

        Matcher m = JsonValue.matcher(input);
       
        while(m.find()){

            String[] value = m.group().replaceAll("(?<!\\\\)\"", "").split(":",2);
            output.put(value[0], value[1]);
        }
        
        m = JsonArray.matcher(input);
        while(m.find()){
            String value = m.group();
            System.out.println(m.group());

            
            List<Object> list = new ArrayList<>();
            output.put(value.split(":")[0].replace("\"",""), list);
            String[] stringList = value.split(":")[1]
                            .replaceAll("[\"]|[\\]]|[\\[]","").split(",");
            list.addAll(Arrays.asList(stringList));
          
        }

        
            
            
        
        
        return output;
    }

    public static HashMap<String,Object> getFile(String path){
        HashMap<String,Object> output = new HashMap<>();

        String fileData = "";
        try {
            File myObj = new File(path);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                fileData += myReader.nextLine();

            }
            myReader.close();

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        output = JsonFomatter.jsonToHashmap(fileData);


        return output;
    }

    private static List<Object> formatArray(String input){

        return null;
    }



}
