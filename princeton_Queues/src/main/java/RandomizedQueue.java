import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int N;



    // construct an empty randomized queue
    public RandomizedQueue(){
        this.items = (Item[]) new Object[1];
        this.N = 0;
    }

    private void resize(int newSize){
        Item[] newItems = (Item[]) new Object[newSize];

        for (int i = 0; i < this.N; i++){
           newItems[i] = this.items[i];
        }
        this.items = newItems;
    }


    // is the randomized queue empty?
    public boolean isEmpty(){
        return this.N == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return this.N;
    }

    // add the item
    public void enqueue(Item item){
        if (item == null) throw new IllegalArgumentException();

        if (this.N == this.items.length) resize(this.items.length * 2);
        this.items[this.N] = item;
        this.N++;
    }

    // remove and return a random item
    public Item dequeue(){
        if (this.N == 0) throw new NoSuchElementException();

        int random = StdRandom.uniformInt(this.N);
        Item removedItem = this.items[random];

        this.items[random] = this.items[this.N - 1];
        this.items[this.N - 1] = null;
        this.N--;

        if (N > 0 && N <= this.items.length/4) resize(this.items.length/2);
        return removedItem;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if (this.N == 0) throw new NoSuchElementException();

        return this.items[StdRandom.uniformInt(this.N)];
    }

    // return an independent iterator over items in random order
    @Override
    public Iterator<Item> iterator(){
        return new Iterator<Item>(){
            private int i = 0;
            private Item[] clone = items;

            {
                StdRandom.shuffle(clone);
            }

            public boolean hasNext(){
                return i < N;
            }

            public Item next(){
                if (!hasNext()) throw new NoSuchElementException();
                return items[i++];
            }

            public void remove(){
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue randomizedQueue = new RandomizedQueue();

        System.out.println(randomizedQueue.isEmpty());
        System.out.println(randomizedQueue.size());

        randomizedQueue.enqueue("Hi");
        randomizedQueue.dequeue();
        randomizedQueue.enqueue("Hi Again");

        System.out.println(randomizedQueue.isEmpty());
        System.out.println(randomizedQueue.size());

        System.out.println(randomizedQueue.sample());

        for (Object line: randomizedQueue){
            System.out.println(line);
        }

    }
}
