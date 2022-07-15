package json;


public class ListItem <T>{

    public T value; // could be string, float, JsonObject etc

    public <T>ListItem(){}
    public ListItem(T value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
