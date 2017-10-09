package solutions.serDeser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException{

        ListRand listRand = new ListRand();

        for (int i=0; i<10;i++){
            listRand.Add(Integer.toString(i));
        }
        listRand.AddRandom();

        System.out.println("Список до сериализации в прямом порядке: \n");
        ListNode current = listRand.Head;
        while (current != null){
            System.out.println("Data: "+ current +" Rand: "+ current.Rand);
            current=current.Next;
        }

        listRand.Serialize(new FileOutputStream(new File("store.txt")));

        listRand.Clear();
        System.out.println("\nСписок очищен: "+ listRand+ "\n");

        listRand.Deserialize(new FileInputStream("store.txt"));

        System.out.println("Список после десериализации в обратном порядке:\n");
        current = listRand.Tail;
        while (current != null){
            System.out.println("Data: "+ current +" Rand: "+ current.Rand);
            current=current.Prev;
        }
    }
}



