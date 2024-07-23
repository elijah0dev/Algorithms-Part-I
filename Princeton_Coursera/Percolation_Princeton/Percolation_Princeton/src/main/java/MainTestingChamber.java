import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class MainTestingChamber {

    public static void main(String[] args){
        Percolation perc = new Percolation(5);
        perc.open(1,1);
        perc.open(3,3);

        System.out.println(perc.isFull(1,1));
        System.out.println(perc.isOpen(1,1));

        System.out.println();

        System.out.println(perc.isFull(1,2));
        System.out.println(perc.isOpen(1,2));

        System.out.println();

        System.out.println(perc.isFull(3,3));
        System.out.println(perc.isOpen(3,3));
    }
}
