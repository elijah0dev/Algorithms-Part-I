import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first;
    private Node last;
    private int size;

    private class Node {
        private Node prev = null;
        private Node next = null;
        private Item item = null;
    }

    // construct an empty deque
    public Deque() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return this.size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.prev = null;
        newFirst.next = this.first;
        this.first = newFirst;

        if(this.first.next != null) newFirst.next.prev = this.first;
        if(this.last == null) this.last = newFirst;

        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();

        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = this.last;
        this.last = newLast;

        if (this.first == null) this.first = this.last;
        if (this.last.prev != null) this.last.prev.next = this.last;

        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = this.first.item;
        if (this.size == 1){
            this.first = null;
            this.last = null;
        } else{
            this.first = this.first.next;
            this.first.prev = null;
        }

        size--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException();

        Item item = this.last.item;
        if (this.size == 1){
            this.first = null;
            this.last = null;
        }else{
            this.last = this.last.prev;
            this.last.next = null;
        }

        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    @Override
    public Iterator<Item> iterator() {
        return new Iterator<Item>(){
            private Node current = first;

            @Override
            public boolean hasNext(){
                return current != null;
            }

            @Override
            public Item next(){
                if (!hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }

            @Override
            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println("Size: " + deque.size + " isEmpty?: " + deque.isEmpty());

        deque.addFirst("hello");
        deque.addLast("wow");
        deque.addFirst("never");
        deque.addLast("goodbye");
        deque.addLast("yes");
        deque.addFirst("there");

        System.out.println("Size: " + deque.size + " isEmpty?: " + deque.isEmpty());

        System.out.println();
        for (String line: deque){
            System.out.println(line);
        }
        System.out.println();

        deque.removeFirst();
        deque.removeLast();

        System.out.println("Size: " + deque.size + " isEmpty?: " + deque.isEmpty());

        for (String line: deque){
            System.out.println(line);
        }
    }
}