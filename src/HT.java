import java.io.*;
import java.util.ArrayList;

class HT implements java.io.Serializable {
    static final class Node {
        Object key;
        Node next;
        // int count;

        double value;
        Node(Object k, double v, Node n) { key = k; value = v; next = n; }
    }
    Node[] table = new Node[8]; // always a power of 2
    int size = 0;
    ArrayList<Object> keySet = new ArrayList<>();

    boolean contains(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
                return true;
        }
        return false;
    }


    /*
    //Not fully sure if this will work
    Object getKey(Object value){
        int h = value.hashCode();
        int i = h & (table.length -1);
        for(Node e = table[i]; e != null; e = e.next){
            if (value.equals(e.value))
                return e.key;
        }
        System.out.println("\nKey not found\n");
        return null;
    }
     */

    double getDouble(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next) {
            if (key.equals(e.key))
                return e.value;
        }
        return -999999999;
    }

    void add(Object key, double value) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        for (Node e = table[i]; e != null; e = e.next ) {
            if (key.equals(e.key)) {
                // Update the value if the key already exists
                e.value = value;
                return;
            }
        }
        // Create a new node for the key-value pair
        table[i] = new Node(key, value, table[i]);
        //Add new key to the keySet
        keySet.add(key);
        ++size;
        if ((float)size/table.length >= 0.75f)
            resize();
    }

    ArrayList<Object> getKeySet(){
        return keySet;
    }


    void resize() {
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Node[] newTable = new Node[newCapacity];
        for (int i = 0; i < oldCapacity; ++i) {
            for (Node e = oldTable[i]; e != null; e = e.next) {
                int h = e.key.hashCode();
                int j = h & (newTable.length - 1);
                newTable[j] = new Node(e.key, e.value, newTable[j]);
            }
        }
        table = newTable;
    }
    void resizeV2() { // avoids unnecessary creation
        Node[] oldTable = table;
        int oldCapacity = oldTable.length;
        int newCapacity = oldCapacity << 1;
        Node[] newTable = new Node[newCapacity];
        for (int i = 0; i < oldCapacity; ++i) {
            Node e = oldTable[i];
            while (e != null) {
                Node next = e.next;
                int h = e.key.hashCode();
                int j = h & (newTable.length - 1);
                e.next = newTable[j];
                newTable[j] = e;
                e = next;
            }
        }
        table = newTable;
    }
    void remove(Object key) {
        int h = key.hashCode();
        int i = h & (table.length - 1);
        Node e = table[i], p = null;
        while (e != null) {
            if (key.equals(e.key)) {
                if (p == null)
                    table[i] = e.next;
                else
                    p.next = e.next;
                break;
            }
            p = e;
            e = e.next;
        }
    }
    void printAll() {
        for (int i = 0; i < table.length; ++i)
            for (Node e = table[i]; e != null; e = e.next)
                System.out.println(e.key);
    }

    private void writeObject(ObjectOutputStream s) throws Exception {
        s.defaultWriteObject();
        s.writeInt(size);
        for (int i = 0; i < table.length; ++i) {
            for (Node e = table[i]; e != null; e = e.next) {
                s.writeObject(e.key);
            }
        }
    }

    private void readObject(ObjectInputStream s) throws Exception {
        s.defaultReadObject();
        int n = s.readInt();
        for (int i = 0; i < n; ++i)
            //Might have to change this after
            add(s.readObject(), s.readDouble());
    }
}

