import json.JSON;
import json.Pair;

public class Main {

    public static void main(String [] args){

        JSON json = new JSON();
        json = json.parseFile("levelData.json");
        System.out.println(json);


    }

}
