import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args){
        if (args.length < 1) return;

        RandomizedQueue randomizedQueue = new RandomizedQueue();
        while (!StdIn.isEmpty()){
            String line = StdIn.readString();
            randomizedQueue.enqueue(line);
        }
        Iterator<String> iterator = randomizedQueue.iterator();
        for (int i = 0; i < Integer.parseInt(args[0]); i++){
            if (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }

    }
}
