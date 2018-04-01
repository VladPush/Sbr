package solutions.serDeser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        NodeLinkedList nodes = new NodeLinkedList();

        for (int i=0; i<10;i++) {
            nodes.add(Integer.toString(i));
        }
        nodes.addRandom();

        System.out.println("Список до сериализации в прямом порядке: \n");
        Node current = nodes.head;
        while (current != null) {
            System.out.println("data: "+ current.getData() +" rand: "+ current.getRand());
            current = current.getNext();
        }

        nodes.serialize(new FileOutputStream(new File("store.txt")));

        nodes.clear();
        System.out.println("\nСписок очищен: "+ nodes+ "\n");

        nodes.deserialize(new FileInputStream("store.txt"));

        System.out.println("Список после десериализации в обратном порядке:\n");
        current = nodes.tail;
        while (current != null) {
            System.out.println("data: "+ current.getData() +" rand: "+ current.getRand());
            current=current.getPrev();
        }
    }
}



