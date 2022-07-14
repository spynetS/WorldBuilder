import json.JSON;
import json.LinkedList;
import json.Pair;

public class Main {

    public static void main(String [] args){
        JSON json = new JSON();

        json = json.parseFile("levelData.json");

       //System.out.println(json.get("name"));
        System.out.println(((JSON)json.getPair("items").value).getPair("backpack").value.);

    }

    static JSON test1(){
        JSON json = new JSON();
        json.add(new Pair<String>("name","Alfred"));
        json.add(new Pair<String>("last-name","Roos"));
        json.add(new Pair<Integer>("age",18));
        json.add(new Pair<Float>("height",180.4f));

        JSON items = new JSON();
        items.add(new Pair<String>("item1","socks"));
        items.add(new Pair<String>("item2","pants"));

        json.add("items",items);

        json.remove("items");

        return json;
    }

}
