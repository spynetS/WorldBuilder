package json;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.LinkedList;
import java.util.concurrent.SynchronousQueue;

public class JSON{
    private char type1 = '{';
    private char type2 = '}';
    public JSON(){}
    public JSON(String type){
        if(type=="list"){
            type1='[';
            type2=']';
        }
    }


    private String getRandomId(){
        Random r = new Random();
        return String.valueOf(r.nextFloat());
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

        return myJson.replace("\n","");
    }
    public JSON parseFile(String path){
        Path filePath = Path.of(path);
        try {
            return parseString(Files.readString(filePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String adder(Stack<String> stack,int dep){
        if(stack.size()>=dep) return stack.get(stack.size()-dep);
        else return "";
    }

    public JSON parseString(String json){

        json = format(json); // format json
        System.out.println(json);
        Stack<Pair<JsonObject>> objects = new Stack<>(); // last in the array is the current
        Stack<Pair<JsonList>> lists = new Stack<>(); // last in the array is the current
        JSON me = null; // first object
        int depth = -1; // object depth
        String key = "";
        String value = "";
        boolean checkingValue = false;
        int listDepth = -1;
        String current = "";
        Stack<String> inside = new Stack<>();


        for(char c : json.toCharArray()){
            JsonObject currentObj = objects.size()>0?objects.peek().value:null;
            if(c=='['){
                lists.add(new Pair<JsonList>(key,new JsonList()));
                listDepth ++;
                key = "";
                checkingValue = false;
                inside.push("array");

            } else if (c==']') {
                lists.peek().value.add(key);
                key = "";
                if(objects.size()>0){
                    objects.peek().value.add(new Pair<JsonList>((String) lists.peek().key, lists.pop().value));
                }
                else {
                    me = lists.pop().value;
                }
                listDepth--;
                inside.pop();
            } else if (c == '{') {
                inside.push("obj");
                JsonObject j = new JsonObject();
                objects.push(new Pair<JsonObject>((key!=""?key:"no key bram"), j));
                key = "";
                checkingValue = false;
                depth++;
                //System.out.println(getKey(objects,depth));


            } else if (c == '}') {
                if (key != "" && value != "") {
                    objects.peek().value.add(new Pair<Object>(key, value)); // should convert value to right type
                    key =   "";
                    value = "";
                }
                if(adder(inside,2) == "array"){
                    lists.peek().value.add(new ListItem<JsonObject>(objects.pop().value));
                    key = "";
                    value = "";

                }
                else {

                    if (depth == 0){
                        me = objects.pop().value;
                    }
                    else {
                        objects.get(objects.size()-2).value.add(objects.peek().key,objects.pop().value);
                    }
                }
                depth--;
                inside.pop();
                checkingValue = false;


            } else if (c == ',') {

                if(!value.isEmpty()&&!key.isEmpty()&&checkingValue){

                    objects.peek().value.add(new Pair<Object>(key, value)); // should convert value to right type
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


}
