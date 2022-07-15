package json;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class JsonList extends JSON{

    ArrayList<ListItem> objects = new ArrayList<>();
    public <T>void add (T value){
        objects.add(new ListItem<T>(value));
    }
    public <T>void remove(T value){
        ArrayList<ListItem> pairs = new ArrayList<>();
        for(ListItem p : this.objects){
            if(p.value != value)
                pairs.add(p);
        }
        this.objects = pairs;
    }
    public ListItem getPair(int index){
        int i = 0;
        for(ListItem p : objects){
            if(i==index){
                return p;
            }
            i++;
        }
        return null;
    }
    public String toString(){

        String myString = "[\n";
        int i = 0;
        for (ListItem pair:
                objects) {
            if(i<objects.size()-1)
                myString += pair.value.toString()+",\n";
            else
                myString += pair.value.toString()+"\n";
            i++;
        }
        myString+="]";
        return myString;
    }
}
