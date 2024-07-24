import edu.princeton.cs.algs4.Stack;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Board{
    private final int[][] tiles; // if I want to make it immutable, i need to do a nested loop copy??

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles){
        this.tiles = new int[tiles.length][tiles[0].length];
        for (int row = 0; row < tiles.length; row++){
            for (int col = 0; col < tiles.length; col++){
                this.tiles[row][col] = tiles[row][col];
            }
        }
    }

    // string representation of this board
    public String toString(){
        StringBuilder representation = new StringBuilder(String.valueOf(tiles.length + "\n"));
        for (int row = 0; row < tiles.length; row++){
            for (int col = 0; col < tiles[row].length; col++){
                representation.append(" ").append(tiles[row][col]);
            }
            representation.append("\n");
        }
        return representation.toString();
    }

    // board dimension n
    public int dimension(){
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming(){
        int misplaced = 0;
        for (int row = 0; row < tiles.length; row++){
            for (int col = 0; col < tiles[row].length; col++){
                if (tiles[row][col] == 0) continue;
                if (tiles[row][col] != row * tiles.length + col + 1) misplaced++;
            }
        }
        return misplaced;
    }

    private int findColumn(int number, int length){
        if (number % length == 0) return length - 1;
        while (number > length){
            number -= length;
        }
        return number - 1;
    }

    private int findRow(int number, int length){
        if (number <= length) return 0;
        if (number % length == 0) return number / length - 1;

        int count = 0;
        while (number >= length){
            number -= length;
            count++;
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int misplacedSum = 0;
        for (int row = 0; row < tiles.length; row++){
            for (int col = 0; col < tiles[row].length; col++){
                if (tiles[row][col] == 0) continue;
                if (tiles[row][col] == row * tiles.length + col + 1) continue;

                int number = tiles[row][col];
                int length = tiles.length;
                int realRow = findRow(number, length);
                int realColumn = findColumn(number, length);

                misplacedSum += Math.abs(realRow - row);
                misplacedSum += Math.abs(realColumn - col);
            }
        }
        return misplacedSum;
    }

    // is this board the goal board?
    public boolean isGoal(){
        for (int row = 0; row < tiles.length; row++){
            for (int col = 0; col < tiles.length; col++){
                if (tiles[row][col] == 0) continue;
                if (tiles[row][col] != row * tiles.length + col + 1) return false;
            }
        }
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y){
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board otherBoard = (Board) y;

        if (this.dimension() != otherBoard.dimension()) return false;

        for (int row = 0; row < tiles.length; row++){
            for (int col = 0; col < tiles.length; col++){
                if (tiles[row][col] != otherBoard.tiles[row][col]) return false;
            }
        }

        return true;
    }


    private void swap(int row1, int col1, int row2, int col2){
        int num = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = num;
    }


    // all neighboring boards
    public Iterable<Board> neighbors(){
        Stack<Board> stack = new Stack<>();
        int row0 = 0;
        int col0 = 0;

        for (int row = 0; row < tiles.length; row++){
            for (int col = 0; col < tiles[row].length; col++){
                if (tiles[row][col] == 0){
                    row0 = row;
                    col0 = col;
                    break;
                }
            }
        }

        if (row0 - 1 >= 0){
            Board newBoard = new Board(tiles);
            newBoard.swap(row0, col0, row0 -1, col0);
            stack.push(newBoard);
        }
        if (row0 + 1 < tiles.length){
            Board newBoard = new Board(tiles);
            newBoard.swap(row0, col0, row0 + 1, col0);
            stack.push(newBoard);
        }
        if (col0 - 1 >= 0){
            Board newBoard = new Board(tiles);
            newBoard.swap(row0, col0, row0, col0 - 1);
            stack.push(newBoard);
        }
        if (col0 + 1 < tiles.length){
            Board newBoard = new Board(tiles);
            newBoard.swap(row0, col0, row0, col0 + 1);
            stack.push(newBoard);
        }


        return new Iterable<Board>(){
            @Override
            public Iterator<Board> iterator(){
                return new Iterator<Board>(){

                    @Override
                    public boolean hasNext(){
                        return !stack.isEmpty();
                    }

                    @Override
                    public Board next(){
                        if (!hasNext()) throw new NoSuchElementException();
                        return (Board) stack.pop();
                    }
                };
            }
        };
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        Board newBoard = new Board(tiles);
        if (tiles[0][0] != 0 && tiles [0][1] != 0){
            newBoard.swap(0, 0, 0, 1);
        }else{
            newBoard.swap(1,0, 1, 1);
        }
        return newBoard;
    }
    


    // unit testing (not graded)
    public static void main(String[] args){

    }
}