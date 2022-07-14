package json;

import com.sun.jdi.Value;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.LinkedList;

public class JSON{
    public java.util.LinkedList<Pair> pairs = new java.util.LinkedList<>();
    public void add (Pair pair){
        pairs.add(pair);
    }

    public void add (String key,JSON json){
        pairs.add(new Pair<JSON>(key,json));
    }

    public void remove(String key){
        LinkedList<Pair> pairs = new LinkedList<>();
        for(Pair p : this.pairs){
            if(p.key != '"'+key+'"')
                pairs.add(p);
        }
        this.pairs = pairs;
    }
    private JSON get(HashMap<String,JSON> objects, int index){
        Object firstKey = objects.keySet().toArray()[index];
        return objects.get(firstKey);
    }
    private String getKey(HashMap<String,JSON> objects, int index){
        Object firstKey = objects.keySet().toArray()[index];
        return (String)firstKey;
    }

    private String format(String json){
        //remove all new lines
        //remove all spaces which are outside '"'
        String myJson = "";

        boolean inside = false;
        for(char c : json.toCharArray()) {
            if(!inside&&c=='"'){
                inside = true;
            }else if(inside&&c=='"'){
                inside = false;
            }
            if(!inside && c == ' '){}
            else{
                myJson+=c;
            }
        }

        return myJson.replace(System.getProperty("line.separator"),"");
    }
    public JSON parseFile(String path){
        Path filePath = Path.of(path);
        try {
            return parseString(Files.readString(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public Pair<Value> getPair(String key){
        for(Pair p : pairs){
            if(p.key.equals("\""+key+"\"")){
                return p;
            }
        }
        return null;
    }
    public JSON parseString(String json){
        json = format(json); // format json
        System.out.println(json);
        HashMap<String,JSON> objects = new HashMap<>(); // last in the array is the current
        JSON me = null; // first object
        int depth = -1; // object depth
        String key = "";
        String value = "";
        boolean checkingValue = false;

        for(char c : json.toCharArray()){
            JSON currentObj = objects.size()>0?get(objects,objects.size()-1):null;
            if(c=='{'){
                objects.put(key,new JSON());
                key="";
                checkingValue = false;
                depth ++;
            }else if(c=='}'){
                if(key != "" && value != ""){
                    get(objects,depth).add(new Pair<Object>(key,value)); // should convert value to right type
                    key = "";
                    value = "";
                }
                if(depth==0)
                    me = get(objects,0);
                else{
                    get(objects,depth-1).add(getKey(objects,depth),get(objects,depth));
                    objects.remove(currentObj);
                }
                checkingValue = false;
                depth--;
            }else if(c==','){
                get(objects,depth).add(new Pair<Object>(key,value)); // should convert value to right type
                key = "";
                value = "";
                checkingValue=false;
            } else if(c==':'){
                checkingValue = true;
            } else if(!checkingValue){
                key += c;
            } else if (checkingValue) {
                value += c;
            }
        }

        return me;
    }

    public String toString(){

        String myString = "{\n";
        int i = 0;
        for (Pair pair:
             pairs) {
            if(i<pairs.size()-1)
                myString += pair.toString()+",\n";
            else
                myString += pair.toString()+"\n";
            i++;
        }
        myString+="}";
        return myString;
    }
}
