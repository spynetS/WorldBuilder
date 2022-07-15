package json;

import java.util.LinkedList;

public class JsonObject extends JSON{

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
    public Pair getPair(String key){
        for(Pair p : pairs){
            if(p.key.equals("\""+key+"\"")){
                return p;
            }
        }
        return null;
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
