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
/*

{
  "list":[
    {
      "pos": {
        "x": 10,
        "y": 20
      },
      "scale":{
        "x": 100,
        "y": 100
      }
    },
    {
      "pos": {
        "x": 10,
        "y": 20
      },
      "scale":{
        "x": 100,
        "y": 100
      }
    },
    {
      "pos": {
        "x": 10,
        "y": 20
      },
      "scale":{
        "x": 100,
        "y": 100
      }
    }]
}
 */