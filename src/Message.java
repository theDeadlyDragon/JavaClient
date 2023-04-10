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

    public Message(){
    }





    public String toJson(){
        String output = "{";

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
