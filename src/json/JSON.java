package json;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.LinkedList;
import java.util.concurrent.SynchronousQueue;

public class JSON{
    public java.util.LinkedList<Pair> pairs = new java.util.LinkedList<>();
    public void add (Pair pair){
        pairs.add(pair);
    }

    public void add (String key,JSON json){
        pairs.add(new Pair<JSON>(key,json));
    }

    private String getRandomId(){
        Random r = new Random();
        return String.valueOf(r.nextFloat());
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
    public Pair getPair(String key){
        for(Pair p : pairs){
            if(p.key.equals("\""+key+"\"")){
                return p;
            }
        }
        return null;
    }

    private String adder(Stack<String> stack,int dep){
        if(stack.size()>=dep) return stack.get(stack.size()-dep);
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
                System.out.println("key = "+ key);
                JSON j = new JSON();
                objects.put((key!=""?key:"no key bram")+getRandomId(), j);
                key = "";
                checkingValue = false;
                depth++;
                System.out.println(objects);
                //System.out.println(getKey(objects,depth));


            } else if (c == '}') {
                if(adder(inside,2) == "array"){
                    getLists(lists,listDepth).add(get(objects,depth).toString());
                    key = "";
                    value = "";

                }
                else {
                    if (key != "" && value != "") {
                        get(objects, depth).add(new Pair<Object>(key, value)); // should convert value to right type
                        key =   "";
                        value = "";
                    }
                    if (depth == 0)
                        me = get(objects, 0);
                    else {
                        get(objects, depth - 1).add(getKey(objects, depth), get(objects, depth));
                        objects.remove(getKey(objects, depth));
                    }
                }
                depth--;
                inside.pop();
                checkingValue = false;


            } else if (c == ',') {

                if(!value.isEmpty()&&!key.isEmpty()&&checkingValue){

                    get(objects, 0).add(new Pair<Object>(key, value)); // should convert value to right type
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
