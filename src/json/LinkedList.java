package json;

import java.util.ArrayList;
import java.util.List;

public class LinkedList {

    Node head = null;

    public void add(Pair pair){
        if(head == null){
            head = new Node(pair,head);
        }
        else{
            Node current = head;
            while(current.next != null){
                current=current.next;
            }
            current.next = new Node(pair,current);
        }
    }

    public void remove(String key){
        Node current = head;
        Node prev = null;
        while(true){
            if(current.pair.key == key){
              if(current==head){
                  head = current.next;
              }
              else{
                  prev.next = current.next;
              }
            }
            if(current.next == null) break;
            prev = current;
            current=current.next;
        }
    }
    public void remove(int index){
        Node current = head;
        Node prev = null;
        int i = 0;
        while(true){
            if(i == index){
                if(current==head){
                    head = current.next;
                }
                else{
                    prev.next = current.next;
                }
            }
            if(current.next == null) break;
            prev = current;
            current=current.next;
            i++;
        }
    }

    public void print(){
        Node current = head;
        while(true){
            System.out.println(current);
            if(current.next == null) break;
            current = current.next;
        }


    }
}
