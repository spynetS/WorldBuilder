package json;

public class Pair <Value>{

    public String key="a";
    public Value value;

    public Pair(String key,Value value){
        this.key = key;

        this.value = value;
    }

    @Override
    public String toString() {
        return this.key+":"+this.value.toString();
    }
}
