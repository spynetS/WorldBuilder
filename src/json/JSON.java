package json;

import com.sun.jdi.Value;

import javax.lang.model.util.SimpleElementVisitor6;
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
    private ArrayList<String> getLists(HashMap<String,ArrayList<String>> objects, int index){
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

    private String adder(Stack<String> stack){
        if(stack.size()>=1) return stack.get(stack.size()-1);
        else return "";
    }

    public JSON parseString(String json){

        json = format(json); // format json

        System.out.println(json);
        HashMap<String,JSON> objects = new HashMap<>(); // last in the array is the current
        HashMap<String,ArrayList<String>> lists = new HashMap<>(); // last in the array is the current
        JSON me = null; // first object
        int depth = -1; // object depth
        String key = "";
        String value = "";
        boolean checkingValue = false;
        int listDepth = -1;
        String current = "";
        Stack<String> inside = new Stack<>();

        for(char c : json.toCharArray()){
            JSON currentObj = objects.size()>0?get(objects,objects.size()-1):null;
            if(c=='['){
                lists.put(key,new ArrayList<String>());
                listDepth ++;
                key = "";
                checkingValue = false;
                inside.push("array");

            } else if (c==']') {
                getLists(lists,listDepth).add(key);
                key = "";
                get(objects,depth).add(new Pair<String>((String) lists.keySet().toArray()[listDepth],getLists(lists,listDepth).toString()));
                listDepth--;
                inside.pop();
            } else if (c == '{') {
                inside.push("obj");
                System.out.println(key);
                System.out.println(inside.get(0));
                if(adder(inside) == "array"){

                    objects.put("arrsay", new JSON());
                    key = "";
                    checkingValue = false;

                    depth++;
                }else {
                    objects.put(key, new JSON());
                    key = "";
                    checkingValue = false;
                    depth++;
                }


            } else if (c == '}') {
                if(adder(inside) == "array"){
                    if (key != "" && value != "") {
                        get(objects, depth).add(new Pair<Object>(key, value)); // should convert value to right type
                    }
                    getLists(lists,listDepth).add(get(objects,depth).toString());
                    key = "";
                    value = "";
                    objects.remove(getKey(objects, depth));

                }
                else {
                    if (key != "" && value != "") {
                        get(objects, depth).add(new Pair<Object>(key, value)); // should convert value to right type
                        key = "";
                        value = "";
                    }
                    if (depth == 0)
                        me = get(objects, 0);
                    else {
                        get(objects, depth - 1).add(getKey(objects, depth), get(objects, depth));
                        objects.remove(getKey(objects, depth));
                    }
                    checkingValue = false;
                }
                depth--;
                inside.pop();

            } else if (c == ',') {

                if(value.isEmpty()&&!key.isEmpty()){
                    getLists(lists,listDepth).add(key);
                    key = "";
                }else if(!value.isEmpty()&&!key.isEmpty()){
                    get(objects, depth).add(new Pair<Object>(key, value)); // should convert value to right type
                    key = "";
                    value = "";
                    checkingValue = false;
                }
            } else if (c == ':') {
                checkingValue = true;
            } else if (!checkingValue) {
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
