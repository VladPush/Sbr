package solutions.serDeser;

public class Node {
    private Node prev;
    private Node next;
    private Node rand; // произвольный элемент внутри списка
    private String data;

    Node(String data) {
        this.data = data;
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getRand() {
        return rand;
    }

    public void setRand(Node rand) {
        this.rand = rand;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Node{"+ " " + this.getData() + " }";
    }
}
