package solutions.serDeser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NodeLinkedList {
    public Node head;//первый узел
    public Node tail;//последний узел
    private int count; //кол-во узлов
    private List<Integer> keySet = new ArrayList<>();
    private int i;

    public void clear() {
        head = null;
        tail = null;
        keySet.clear();
        count = 0;
    }

    public void add(String data) {
        Node node = new Node(data);

        if (head == null) { //если head пуст то инициализируем head  и tail
            head = node;
            tail = node;
        } else { // если head не пуст то обнавляем tail
            tail.setNext(node);
            node.setPrev(tail);
            tail = node;
            tail.setNext(null);
        }
        count++;
    }

    public boolean remove(String data) {
        Node previous = null;
        Node current = head;

        while (current!=null){//перебираем лист
            if (current.getData().equals(data)) { //текущий элемент равен искомому
                if (previous != null) { // если предыдущий не равен 0
                    previous.setNext(current.getNext()); // перемещаем указатель Nxt предыдущего ЧЕРЕЗ удаляемый элемент
                    if (current.getNext() == null) { //если у текущего нет следующего то...
                        tail = previous; //Конец = предыдущий элемент.
                    } else { //если следующий не равен null
                        current.getNext().setPrev(previous);
                    }
                    count--;
                } else {
                    removeFirst();
                }
                return true; // удаление было
            }
            previous = current;
            current = current.getNext();
        }
        return false;// удаления не было
    }

    public void addFirst(String data) {
        Node node = new Node(data);
        Node tempHead = head; // "кешируем" в temp текущий head
        head = node; // обновляем первый элемент
        head.setNext(tempHead); // цепляем весь список к новому head
        if (count == 0) { //если список был пуст
            tail = head; //начало и конец один и тот же элемент
        } else {
            head.getNext().setPrev(head); // у второго элемента устанавливаем предыдущий элемент(head) в качестве предка
        }
        count++;
    }

    public void addLast(String data) {
        Node node = new Node(data);
        if (count == 0) {//если список пуст
            head = node; //присваеваем начальному элементу значение объекта
        } else {
            tail.setNext(node); //добавляем ссылку последнему элементу
            node.setPrev(tail); //добавляем ссылку на предыдущей элемент текущей последней ноде
        }
        tail = node; // обнавляем ссылку последнего элемента
    }

    public void removeFirst() {
        if (--count > 0) { //если элементов в списке изначально было больше 1...
            head = head.getNext();
            head.setPrev(null);
        } else {
            clear();
        }
    }

    public void removeLast() {
        if (--count > 0) {
            tail.getPrev().setNext(null); // у предыдущего конечному элементу чистим ссылку на след элемент
            tail = tail.getPrev(); // последнему присваеваем значение предыдущего
        } else {
            clear();
        }
    }

    public void addRandom() {
        i = 0;
        while (i < count) {
            keySet.add(i++,ThreadLocalRandom.current().nextInt(1, count));
        }
       setRand();
    }

    public void serialize(FileOutputStream s) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s, "UTF-8"))) {
            Node current = head;
            i=0;
            while (current != null) {
             // out.write((current.data+"\n")); // write быстрее чем string+string
                out.write(Integer.toString(countLines(current.getData())));
                out.write("\n");
                out.write(current.getData());
                out.write("\n");
                out.write(Integer.toString(keySet.get(i++)));
                out.write("\n");
                current = current.getNext();
            }
        } catch (IOException e) {
            System.out.println("Can not write file. " + e);
        }
    }

    public void deserialize(FileInputStream s) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s, "UTF-8"))) {
            String line;
            i = 0;
            while ((line = in.readLine()) != null) {
                 addLines(in,Integer.parseInt(line));
                 keySet.add(i++,Integer.parseInt(in.readLine()));
            }
        } catch (IOException e) {
            System.out.println("Can not read file. " + e);
        }
     setRand();
    }

    private void setRand() {
        HashMap<Integer,Node> hashMap = new HashMap<>();
        i=0;
        Node current = head;
        while (current != null) {
            hashMap.put(i++,current);
            current=current.getNext();
        }
        current = head;
        i=0;

        while (current != null) {
            current.setRand(hashMap.get(keySet.get(i++)));
            current=current.getNext();
        }
    }

    private void addLines(BufferedReader in, int i) throws IOException {
        StringBuilder line = new StringBuilder();
        while (i > 0) {
            if (--i > 0)
                line.append(in.readLine()).append("\n");
            else
                line.append(in.readLine());
        }
        add(line.toString());
    }

    private static int countLines(String str) {
        Matcher m = Pattern.compile("\r\n|\r|\n").matcher(str);
        int lines = 1;
        while (m.find()) {
            lines++;
        }
        return lines;
    }

    @Override
    public String toString() {
        return "NodeLinkedList {" + "head = " + head + ", tail = " + tail + ", count = " + count + ", KeySet = " + keySet + '}';
    }
}