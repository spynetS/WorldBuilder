package json;

import java.util.*;

public class JsonList extends JSON{

    public ArrayList<ListItem> objects = new ArrayList<>();
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
    public ListItem get(int index){
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
    public int size() {
        return objects.size();
    }
    public boolean isEmpty() {
        return false;
    }
    public boolean contains(Object o) {
        return false;
    }

    public Iterator iterator() {
        return null;
    }
    public Object[] toArray() {
        return new Object[0];
    }
    public boolean addAll(Collection c) {
        return false;
    }

    public boolean addAll(int index, Collection c) {
        return false;
    }

    public void clear() {

    }

    public Object set(int index, Object element) {
        return null;
    }

    public Object remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        return 0;
    }

    public int lastIndexOf(Object o) {
        return 0;
    }

    public ListIterator listIterator() {
        return null;
    }

    public ListIterator listIterator(int index) {
        return null;
    }

    public List subList(int fromIndex, int toIndex) {
        return null;
    }

    public boolean retainAll(Collection c) {
        return false;
    }

    public boolean removeAll(Collection c) {
        return false;
    }

    public boolean containsAll(Collection c) {
        return false;
    }

    public Object[] toArray(Object[] a) {
        return new Object[0];
    }
}
