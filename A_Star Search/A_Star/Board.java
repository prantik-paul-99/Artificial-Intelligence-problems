import java.util.ArrayList;

public class Board {
    private int size;
    private int [][] board;
    private int moves;
    private Board previous_board;
    private String move;
    ArrayList<Integer> values;
    public Board(int n, int m, String move){
        size = n;
        board = new int[n][n];
        moves = m;
        previous_board = null;
        this.move = move;
    }

    public Board(int n, int m, Board b, String move){
        size = n;
        board = new int[n][n];
        moves = m;
        previous_board = b;
        this.move = move;
    }


    private int get_value(int row, int col){
        return board[row][col];
    }

    public boolean is_same(Board b){
        if(b==null) return false;
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col]!=b.get_value(row,col)) return false;
            }
        }
        return true;
    }


    public int getMoves() {
        return moves;
    }

    public Board getPrevious_board() {
        return previous_board;
    }

    public ArrayList<Integer> getValues(){
        ArrayList<Integer> values = new ArrayList<>();
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                values.add(board[row][col]);
            }
        }
        return values;
    }

    public void set_values(ArrayList<Integer> values){
        int counter = 0;

        this.values = values;

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                board[row][col] = values.get(counter);
                counter++;
            }
        }
    }

    private ArrayList<Integer> get_row_column(int n){
        ArrayList<Integer> r_c= new ArrayList<>();

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col]==n){
                    r_c.add(row);
                    r_c.add(col);
                    return r_c;
                }
            }
        }
        r_c.add(-1);
        r_c.add(-1);
        return r_c;
    }

    public int get_hamming_distance(){
        int h_dist = 0;

        int expected = 1;

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col] != expected && expected != size*size) h_dist++;
                expected++;
            }
        }

        return h_dist;
    }

    public int get_manhattan_distance(){
        int m_dist = 0;
        ArrayList<Integer> r_c;
        int expected = 1;

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(expected!=size*size){
                    r_c = get_row_column(expected);
                    m_dist+=(Math.abs(r_c.get(0)-row));
                    m_dist+=(Math.abs(r_c.get(1)-col));
                    expected++;
                }

            }
        }

        return m_dist;
    }

    private boolean is_in_target_row(int value, int row){

        if(value>=(row*size+1) && value<=(row*size+size))
            return true;

        return false;
    }

    private boolean is_in_target_col(int value, int col){

        if((value-1)%size == col) return true;

        return false;
    }

    public int get_linear_conflict(){
        int l_conf = get_manhattan_distance();

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(is_in_target_row(board[row][col],row)){
                    for(int after_col =col+1; after_col < size; after_col++){
                        if(is_in_target_row(board[row][after_col],row)){
                            if(board[row][col]!=0 && board[row][after_col]!=0){
                                if(board[row][col]>board[row][after_col]){
                                    l_conf+=2;
                                }
                            }
                        }
                    }
                }
            }
        }

        for(int col = 0; col < size; col++){
            for(int row = 0; row < size; row++){
                if(is_in_target_col(board[row][col],col)){
                    for(int after_row =row+1; after_row < size; after_row++){
                        if(is_in_target_col(board[after_row][col],col)){
                            if(board[row][col]!=0 && board[after_row][col]!=0){
                                if(board[row][col]>board[after_row][col]){
                                    l_conf+=2;
                                }
                            }
                        }
                    }
                }
            }
        }

        return l_conf;
    }

    public void show_board(){
        System.out.println();
        System.out.println(this.move);
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col]==0) System.out.print("*");
                else System.out.print(board[row][col]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private int get_inversion(){
        int inversion = 0;
        ArrayList<Integer> values = new ArrayList<>();

        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(board[row][col]!=0) values.add(board[row][col]);
            }
        }
        for(int i = 0; i < values.size(); i++){
            for(int j = i+1; j < values.size(); j++){
                if(values.get(i)>values.get(j)) inversion+=1;
            }
        }

        System.out.println(inversion);
        return inversion;
    }

    public boolean is_solvable(){
        int inversion = get_inversion();

        if(size%2==1 && inversion%2==0) return true;

        if(size%2==0){
            ArrayList<Integer> r_c;
            r_c = get_row_column(0);
            if(r_c.get(0)%2==0 && inversion%2==1) return true;
            if(r_c.get(0)%2==1 && inversion%2==0) return true;
        }

        return false;
    }

    public void swap(int val1, int row1, int col1, int val2, int row2, int col2) {
        board[row1][col1] = val2;
        board[row2][col2] = val1;
    }

    public ArrayList<Board> make_a_move(){

        ArrayList<Board> neighbour_boards = new ArrayList<>();

        ArrayList<Integer> r_c = get_row_column(0);
        int row = r_c.get(0);
        int col = r_c.get(1);

        //show_board();
        //System.out.println("row+col "+row+" "+col);

        if(row-1!=-1){
            Board b = new Board(size, moves+1, this, "Down");
            b.set_values(this.getValues());
            //System.out.println(b.getValues());
            b.swap(0,row,col,b.get_value(row-1,col),row-1,col);

            //b.show_board();

            if(!b.is_same(previous_board)){
                neighbour_boards.add(b);
            }
        }

        if(row+1!=size){
            Board b = new Board(size, moves+1, this, "Up");
            b.set_values(this.getValues());
            //System.out.println(b.getValues());
            b.swap(0,row,col,b.get_value(row+1,col),row+1,col);

            //b.show_board();

            if(!b.is_same(previous_board)){
                neighbour_boards.add(b);
            }
        }

        if(col-1!=-1){
            Board b = new Board(size, moves+1, this, "Right");
            b.set_values(this.getValues());
            //System.out.println(b.getValues());
            b.swap(0,row,col,b.get_value(row,col-1),row,col-1);

            //b.show_board();

            if(!b.is_same(previous_board)){
                neighbour_boards.add(b);
            }
        }

        if(col+1!=size){
            Board b = new Board(size, moves+1, this, "Left");
            b.set_values(this.getValues());
            //System.out.println(b.getValues());
            b.swap(0,row,col,b.get_value(row,col+1),row,col+1);

            //b.show_board();

            if(!b.is_same(previous_board)){
                neighbour_boards.add(b);
            }
        }

        return neighbour_boards;
    }

    public int get_priority_function_value(String strategy){

        if (strategy.equals("hamming distance")) return (moves+get_hamming_distance());
        else if (strategy.equals("manhattan distance")) return (moves+get_manhattan_distance());
        else if (strategy.equals("linear conflict")) return (moves+get_linear_conflict());

        return 0;
    }
}
