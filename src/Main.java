import javagameengine.JavaGameEngine;
import javagameengine.backend.Scene;
import javagameengine.backend.input.Input;
import javagameengine.backend.input.Keys;
import javagameengine.components.GameObject;
import javagameengine.msc.Vector2;
import json.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;

public class Main extends JavaGameEngine {

    public static void main(String [] args){

        Main m = new Main();
        LinkedList<GameObject> gameObjects = new LinkedList<>();
        Scene s = new Scene(){
            @Override
            public void update() {
                super.update();
                if(Input.isMouseDown()){
                    Vector2 pos = Input.getMouseWorldPosition();
                    GameObject g = new GameObject();
                    g.setPosition(pos);
                    gameObjects.add(g);
                    instantiate(g);
                }
                if(Input.isKeyPressed(Keys.SPACE)) createSome(gameObjects);
                if(Input.isKeyPressed(Keys.ESCAPE)) getSaved();
            }
        };

        setSelectedScene(s);

        m.start();


    }

    public static void createSome(LinkedList<GameObject> gameObjects){

        JsonList file = new JsonList();

        for(int i = 0;i<gameObjects.size();i++) {
            GameObject gameObject = gameObjects.get(i);
            JsonObject gameobject = new JsonObject();

            JsonObject pos = new JsonObject();
            pos.add(new Pair<Float>("x",gameObject.getPosition().getX()));
            pos.add(new Pair<Float>("y",gameObject.getPosition().getY()));
            gameobject.add(new Pair("pos",pos));

            JsonObject scale = new JsonObject();
            scale.add(new Pair<Float>("x",gameObject.getScale().getX()));
            scale.add(new Pair<Float>("y",gameObject.getScale().getY()));

            gameobject.add(new Pair("scale",scale));

            file.add(gameobject);
        }

        System.out.println(file );
    }

    public static LinkedList<GameObject> getSaved(){
        JsonList list = new JsonList();
        LinkedList<GameObject> objects = new LinkedList<>();
        list = (JsonList) list.parseFile("levelData.json");
        for(ListItem item : list.objects){
            GameObject newG = new GameObject();
            JsonObject o = (JsonObject) item.value;
            newG.setPosition(new Vector2((Float) ((JsonObject)o.getPair("pos").value).getPair("x").value, (Float) ((JsonObject)o.getPair("pos").value).getPair("y").value));
            objects.add(newG);
        }

    return objects;
    }

}
