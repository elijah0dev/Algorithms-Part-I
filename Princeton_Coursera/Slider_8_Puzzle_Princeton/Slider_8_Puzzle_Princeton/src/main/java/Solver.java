import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Solver{
    private SearchNode goalNode;

    private class SearchNode implements Comparable<SearchNode>{
        private Board board;
        private int moves;
        private SearchNode prev;
        private int manhattan;

        public SearchNode(Board board, int moves, SearchNode prev){
            this.board = board;
            this.moves = moves;
            this.prev = prev;
            this.manhattan = board.manhattan();
        }

        @Override
        public int compareTo(SearchNode other){
            int thisBoard = this.moves + this.manhattan;
            int otherBoard = other.moves + other.manhattan;
            return Integer.compare(thisBoard, otherBoard);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if (initial == null) throw new IllegalArgumentException();
        this.goalNode = null;

        SearchNode start = new SearchNode(initial, 0, null);
        MinPQ<SearchNode> minPQ = new MinPQ<>();
        minPQ.insert(start);

        SearchNode twin = new SearchNode(initial.twin(), 0, null);
        MinPQ<SearchNode> twinPQ = new MinPQ<>();
        twinPQ.insert(twin);

        while(!minPQ.isEmpty() && !twinPQ.isEmpty()){
            start = minPQ.delMin();
            twin = twinPQ.delMin();

            if (start.board.isGoal()){
                goalNode = start;
                break;
            }
            if (twin.board.isGoal()){
                break;
            }

            for (Board board: start.board.neighbors()){
                if(start.prev != null && board.equals(start.prev.board)) continue;
                minPQ.insert(new SearchNode(board, start.moves + 1, start));
            }
            for (Board board: twin.board.neighbors()){
                if(twin.prev != null && board.equals(twin.prev.board)) continue;
                twinPQ.insert(new SearchNode(board, twin.moves + 1, twin));


            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return goalNode != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves(){
        if (goalNode == null) return -1;
        return goalNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution(){
        if (goalNode == null) return null;

        return new Iterable<Board>(){

            @Override
            public Iterator<Board> iterator(){
                return new Iterator<Board>(){
                    private Stack<SearchNode> stack = traceBack();

                    public Stack<SearchNode> traceBack(){
                        Stack<SearchNode> traceStack = new Stack<>();
                        SearchNode index = new SearchNode(goalNode.board, goalNode.moves, goalNode.prev);

                        while (index != null){
                            traceStack.push(index);
                            index = index.prev;
                        }
                        return traceStack;
                    }

                    @Override
                    public boolean hasNext(){
                        return !stack.isEmpty();
                    }

                    @Override
                    public Board next(){
                        if (!hasNext()) throw new NoSuchElementException();

                        return stack.pop().board;
                    }
                };
            }
        };
    }

    // test client (see below)
    public static void main(String[] args){

    }

}