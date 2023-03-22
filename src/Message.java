import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message extends HashMap<String,Object>{

    HashMap<String,Object> jsonData  = new HashMap<>();

    public Message(HashMap<String,Object> input){
        this.putAll(input);
        System.out.println(Arrays.asList(this));
    }
    public Message(String input){
        this.jsonToHashmap(input);
    }
    public Message(){
    }


    private void jsonToHashmap(String input){


        //var output = new HashMap<String,Object>();
        this.clear();

        Pattern JsonArray = Pattern.compile("(\"+[^\"]+\"+[:]+[\\[]+.+[\\]])");//(\"+[^\"]+\"+[:]+\"+[^\"]+\")
        Pattern JsonValue = Pattern.compile("\"([^\"]+)\":\\s*\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"");//(\"+[^\"]+\"+[:]+\"+[^\"]+\"+[,])|(\"+[^\"]+\"+[:]+\d+[,])
        //System.out.println("\"([^\"]+)\":\\s*\"([^\"\\\\]*(?:\\\\.[^\"\\\\]*)*)\"");
        // "([^"]+)":\s*"([^"\\]*(?:\\.[^"\\]*)*)"

        Matcher m = JsonValue.matcher(input);

        while(m.find()){

            String[] value = m.group().replaceAll("(?<!\\\\)\"", "").split(":");
            this.put(value[0], value[1]);
        }

        m = JsonArray.matcher(input);
        while(m.find()){
            String value = m.group();
            System.out.println(m.group());


            List<Object> list = new ArrayList<>();
            this.put(value.split(":")[0].replace("\"",""), list);
            String[] stringList = value.split(":")[1]
                    .replaceAll("[\"]|[\\]]|[\\[]","").split(",");
            list.addAll(Arrays.asList(stringList));

        }






        // return new Message(output);
    }



    public String toJson(){
        String output = "{";
        System.out.println("toJson");

        var it = this.entrySet().iterator();

        while(it.hasNext()){
            Entry<String,Object> set = it.next();
            if (set.getValue() instanceof ArrayList)
                output +=String.format("\"%s\":%s", set.getKey(),set.getValue().toString());
            else
                output +=String.format("\"%s\":\"%s\"", set.getKey(),set.getValue().toString());


            if(it.hasNext())
                output += ",";


        }

        return output+"}";
    }


}
