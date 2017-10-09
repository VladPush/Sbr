package solutions.serDeser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ListRand  {
    public ListNode Head;//первый узел
    public ListNode Tail;//последний узел
    public int Count; //кол-во узлов
    private List<Integer> keySet = new ArrayList<>();
    private int i;

    public void Clear() {
        Head = null;
        Tail = null;
        keySet.clear();
        Count = 0;
    }

    public void Add(String data){
        ListNode listNode = new ListNode(data);

        if (Head==null){ //если Head пуст то инициализируем Head  и Tail
            Head = listNode;
            Tail = listNode;
        }else{ // если Head не пуст то обнавляем Tail
            Tail.Next=listNode;
            listNode.Prev = Tail;
            Tail = listNode;
            Tail.Next = null;
        }
        Count++;
    }

    public boolean Remove(String data){
        ListNode previous = null;
        ListNode current = Head;

        while (current!=null){//перебираем лист
            if (current.Data.equals(data)) { //текущий элемент равен искомому
                if (previous != null) { // если предыдущий не равен 0
                    previous.Next = current.Next; // перемещаем указатель Nxt предыдущего ЧЕРЕЗ удаляемый элемент
                    if (current.Next == null) { //если у текущего нет следующего то...
                        Tail = previous; //Конец = предыдущий элемент.
                    } else { //если следующий не равен null
                        current.Next.Prev = previous;
                    }
                    Count--;
                } else {
                    RemoveFirst();
                }
                return true; // удаление было
            }
            previous = current;
            current=current.Next;
        }
        return false;// удаления не было
    }

    public void AddFirst(String data){
        ListNode listNode = new ListNode(data);
        ListNode tempHead = Head; // "кешируем" в temp текущий Head
        Head = listNode; // обновляем первый элемент
        Head.Next = tempHead; // цепляем весь список к новому Head
        if (Count==0){ //если список был пуст
            Tail = Head; //начало и конец один и тот же элемент
        }else{
            Head.Next.Prev = Head; // у второго элемента устанавливаем предыдущий элемент(Head) в качестве предка
        }
        Count++;
    }

    public void AddLast(String data){
        ListNode listNode = new ListNode(data);
        if (Count == 0){//если список пуст
            Head = listNode; //присваеваем начальному элементу значение объекта
        }else{
            Tail.Next = listNode; //добавляем ссылку последнему элементу
            listNode.Prev = Tail; //добавляем ссылку на предыдущей элемент текущей последней ноде
        }
        Tail = listNode; // обнавляем ссылку последнего элемента
    }

    public void RemoveFirst(){
        if (--Count > 0) { //если элементов в списке изначально было больше 1...
            Head = Head.Next;
            Head.Prev = null;
        }else {
            Clear();
        }
    }

    public void RemoveLast(){
        if (--Count > 0) {
            Tail.Prev.Next=null; // у предыдущего конечному элементу чистим ссылку на след элемент
            Tail = Tail.Prev; // последнему присваеваем значение предыдущего
        }else{
            Clear();
        }
    }

    public void AddRandom(){
        i=0;
        while(i<Count){
            keySet.add(i++,ThreadLocalRandom.current().nextInt(1, Count));
        }
       setRand();
    }

    public void Serialize(FileOutputStream s) {
        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(s, "UTF-8"))) {
            ListNode current = Head;
            i=0;
            while (current != null) {
             // out.write((current.Data+"\n")); // write быстрее чем string+string
                out.write(Integer.toString(countLines(current.Data)));
                out.write("\n");
                out.write(current.Data);
                out.write("\n");
                out.write(Integer.toString(keySet.get(i++)));
                out.write("\n");
                current = current.Next;
            }
        } catch (IOException e) { }
    }

    public void Deserialize(FileInputStream s) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(s, "UTF-8"))) {
            String line;
            i = 0;
            while ((line = in.readLine()) != null) {
                 AddLines(in,Integer.parseInt(line));
                 keySet.add(i++,Integer.parseInt(in.readLine()));
            }
        } catch (IOException e) { }

     setRand();

    }


    private void setRand(){
        HashMap<Integer,ListNode> hashMap = new HashMap<>();
        i=0;
        ListNode current = Head;
        while (current != null){
            hashMap.put(i++,current);
            current=current.Next;
        }

        current = Head;
        i=0;
        while (current != null){
            current.Rand=hashMap.get(keySet.get(i++));
            current=current.Next;
        }
    }

    private void AddLines(BufferedReader in,int i) throws IOException {
        StringBuilder line = new StringBuilder();
        while(i>0){
            if (--i>0)
                line.append(in.readLine()).append("\n");
            else
                line.append(in.readLine());
        }
        Add(line.toString());
    }

    private static int countLines(String str){
        Matcher m = Pattern.compile("\r\n|\r|\n").matcher(str);
        int lines = 1;
        while (m.find())
            lines++;

        return lines;
    }

    @Override
    public String toString() {
        return "ListRand {" + "Head = " + Head + ", Tail = " + Tail + ", Count = " + Count + ", KeySet = " + keySet + '}';
    }

}