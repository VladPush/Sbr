package solutions.serDeser;

public class ListNode  {
    public ListNode Prev;
    public ListNode Next;
    public ListNode Rand; // произвольный элемент внутри списка
    public String Data;

    ListNode(String data){
        Data=data;
    }

    @Override
    public String toString() {
        return "ListNode{" +
              /*  "Prev=" + Prev +
                ", Next=" + Next +*/
                /*", Rand= "+ Rand +*/
                ", Data=' " + Data + " " + '\'' +
                '}';
    }
}
