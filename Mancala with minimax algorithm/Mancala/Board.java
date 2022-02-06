import java.util.ArrayList;

public class Board {
    private ArrayList<Integer> bins_player_down;
    private ArrayList<Integer> bins_player_up;

    private boolean game_over;

    private String current_player;
    private int prnt_bin_pos; //which bin of parent this child came

    private int free_moves_up; //total count of free moves till this child board
    private int free_moves_down;

    public int capture_up;
    public int capture_down;

    private int weight1 = 1, weight2 = 2, weight3 = 3, weight4 = 4, weight5 = 5, weight6 = 6;

    public int get_prnt_bin_pos() {
        return this.prnt_bin_pos;
    }

    public void set_prnt_bin_pos(int prnt_bin_pos) {
        this.prnt_bin_pos = prnt_bin_pos;
    }



    public int get_free_moves(String state) {
        if(state.equals("Up")) return this.free_moves_up;
        else return this.free_moves_down;
    }

    public void set_free_moves(int free_moves, String state) {
        if(state.equals("Up")) this.free_moves_up = free_moves;
        else this.free_moves_down = free_moves;
    }

    public int get_capture(String state) {
        if(state.equals("Up")) return this.capture_up;
        else return this.capture_down;
    }

    public void set_capture(int capture, String state) {
        if(state.equals("Up")) this.capture_up = capture;
        else this.capture_down = capture;
    }

    public void setCurrent_player(String current_player) {
        this.current_player = current_player;
    }

    public String getCurrent_player() {
        return this.current_player;
    }

    public ArrayList<Integer> getBins_player_down() {
        return this.bins_player_down;
    }

    public ArrayList<Integer> getBins_player_up() {
        return this.bins_player_up;
    }

    public Board(){
        bins_player_down = new ArrayList(7);
        bins_player_up = new ArrayList(7);

        for(int i = 0; i < 6; i++){
            bins_player_down.add(4);
        }
        bins_player_down.add(0);

        for(int i = 0; i < 6; i++){
            bins_player_up.add(4);
        }
        bins_player_up.add(0);
    }

    //to get children

    public Board(Board parent){
        bins_player_down = new ArrayList(7);
        bins_player_up = new ArrayList(7);

        for(int i = 0; i < 6; i++){
            bins_player_down.add(parent.getBins_player_down().get(i));
        }
        bins_player_down.add(parent.getBins_player_down().get(6));

        for(int i = 0; i < 6; i++){
            bins_player_up.add(parent.getBins_player_up().get(i));
        }
        bins_player_up.add(parent.getBins_player_up().get(6));
        current_player = parent.getCurrent_player();
    }

    public int get_stone_in_bin(int bin, String state){
        if(state.equals("Up")) return this.bins_player_up.get(bin);
        else return this.bins_player_down.get(bin);
    }

    public ArrayList<Board> get_children(Board parent_board, String state, int move_ordering_variable){

        ArrayList<Board> children_boards = new ArrayList<>();
        ArrayList<Integer> move_order = new ArrayList<>();

        if(move_ordering_variable==1) {
            for(int i = 0; i < 6; i++){
                move_order.add(i);
            }
        }
        else if(move_ordering_variable==2) {
            for(int i = 5; i >=0 ; i--){
                move_order.add(i);
            }
        }
        else {
            while(true){
                int i = (int)Math.floor(Math.random()*(6));
                if(!move_order.contains(i)) move_order.add(i);
                if(move_order.size()==6) break;
            }
            //System.out.println(move_order);
        }

        for(int i = 0; i < 6; i++){
            if(parent_board.get_stone_in_bin(move_order.get(i),state) != 0){

                Board b = new Board(parent_board);
                b.set_prnt_bin_pos(move_order.get(i)); //this board generated from bin no move_order.get(i)+1

                int return_val = b.move(move_order.get(i), state, true); //-1 -> don't change player -2 -> change player  others(steal count) -> made a steal

                boolean change_state = false;
                if(return_val !=-1 ) change_state = true;

                if(!change_state) {
                    b.setCurrent_player(state); //current player doesnt change
                    b.set_free_moves((parent_board.get_free_moves(state)+1), state); // add 1 to accumulated free moves
                    /*System.out.println("in children generation "+parent_board.get_free_moves(state)+" "+return_val);
                    System.out.println(b.get_free_moves(state));*/
                }
                else {
                    b.set_free_moves(parent_board.get_free_moves(state),state);
                    if(state.equals("Up")) b.setCurrent_player("Down");
                    else b.setCurrent_player("Up");
                }

                if(return_val!=-1 && return_val!=-2){
                    if(state.equals("Up")){
                        b.capture_up = parent_board.capture_up+return_val;
                    }
                    else{
                        b.capture_down = parent_board.capture_down+return_val;
                    }
                    //b.set_capture((parent_board.get_capture(state)+return_val), state);
                    /*System.out.println("in children generation "+parent_board.get_capture(state)+" "+return_val);
                    System.out.println(b.get_capture(state));*/
                    //System.out.println("capture in algo "+ return_val);
                }
                else {
                    //b.set_capture(parent_board.get_capture(state),state);
                    if(state.equals("Up")){
                        b.capture_up = parent_board.capture_up;
                    }
                    else{
                        b.capture_down = parent_board.capture_down;
                    }
                }

                children_boards.add(b);
            }
        }

        return children_boards;
    }
    /// for heuristics

    public int stones_in_storage(String state){
        if(state.equals("Up")){
            return this.bins_player_up.get(6);
        }
        else if(state.equals("Down")){
            return this.bins_player_down.get(6);
        }
        return 0;
    }

    public int stones_on_side(String state){
        int stones = 0;
        if(state.equals("Up")){
            for(int i = 0; i < 6; i++){
                stones+=bins_player_up.get(i);
            }
        }
        else if(state.equals("Down")){
            for(int i = 0; i < 6; i++){
                stones+=bins_player_down.get(i);
            }
        }
        return stones;
    }

    public int stones_close_to_storage(String state){
        int stones = 0;

        if(state.equals("Up")){
            for(int i = 0; i < 6; i++){
                if(bins_player_up.get(i)<= (i+1) ) stones+=bins_player_up.get(i);
                else stones+=(i+1);
            }
        }
        else if(state.equals("Down")) {
            for (int i = 0; i < 6; i++) {
                if (bins_player_down.get(i) <= (i+1) ) stones += bins_player_down.get(i);
                else stones+=(i+1);
            }
        }
        return stones;
    }

    //end of heuristic functions

    public int move(int bin_no, String state, boolean is_AI){  // -1 -> don't change player -2 -> change player  others(steal count) -> made a steal
        ArrayList<Integer> my_bins = null, opponent_bins = null;
        boolean my_side = true;
        if(state.equals("Up")){
            my_bins = bins_player_up;
            opponent_bins = bins_player_down;
        }
        else if(state.equals("Down")){
            my_bins = bins_player_down;
            opponent_bins = bins_player_up;
        }
        int i = my_bins.get(bin_no);
        if(i==0){
            //System.out.println(bin_no);
            System.out.println("This bin has no marbles");
            return -1;
        }
        //System.out.println(i);
        my_bins.set(bin_no, 0);
        int counter = bin_no+1;
        while(i>0){
            boolean should_exit = false;
            while(counter<7){
                my_side = true;
                //System.out.println(counter);
                my_bins.set(counter, my_bins.get(counter)+1);
                counter++;
                i--;
                if(i==0) {
                    should_exit = true;
                    break;
                }
            }

            if(!should_exit){
                counter = 0;
                while(counter<6){
                    my_side = false;
                    //System.out.println(counter);
                    opponent_bins.set(counter, opponent_bins.get(counter)+1);
                    counter++;
                    i--;
                    if(i==0) {
                        should_exit = true;
                        break;
                    }
                }
                counter = 0;
            }
            if(should_exit) break;
        }
        /*System.out.println(my_bins);
        System.out.println(opponent_bins);

        System.out.println(bins_player_up);
        System.out.println(bins_player_down);*/

        // extra move

        if(counter == 7){
            if(!is_AI) System.out.println("You have gained an extra move");
            this.game_over = this.is_over();
            return -1;
        }

        // steal
        /*System.out.println(counter);
        System.out.println(6-counter);
        System.out.println(my_side);*/

        if(my_side && my_bins.get(counter-1)==1 && opponent_bins.get(6-counter) != 0){
            if(!is_AI) System.out.println("Congrats! You made a steal");

            int steal_count = opponent_bins.get(6-counter)+my_bins.get(counter-1);
            //if(is_AI) System.out.println(" capture in algo "+ steal_count);
            my_bins.set(6,my_bins.get(6)+my_bins.get(counter-1)+opponent_bins.get(6-counter));
            my_bins.set(counter-1, 0);
            opponent_bins.set(6-counter, 0);

            this.game_over = this.is_over();
            return steal_count;
        }

        this.game_over = this.is_over();
        return -2;
    }

    private boolean is_over(){
        if(stones_on_side("Up") == 0){
            bins_player_down.set(6, stones_on_side("Down")+bins_player_down.get(6));
            for(int i = 0; i < 6 ; i++){
                this.bins_player_down.set(i,0);
            }
            return true;
        }
        else if (stones_on_side("Down") == 0){
            bins_player_up.set(6, stones_on_side("Up")+bins_player_up.get(6));
            for(int i = 0; i < 6 ; i++){
                this.bins_player_up.set(i,0);
            }
            return true;
        }
        return false;
    }

    public boolean is_game_over() {
        return game_over;
    }

    private String placeholder(int n){
        if(n < 10){
            return (" "+n+" ");
        }
        else return (n+" ");
    }

    public void show_board(){
        System.out.println("---------   -- 6 --  -- 5 --  -- 4 --  -- 3 --  -- 2 --  -- 1 --    ---------");
        System.out.println("---------   -------  -------  -------  -------  -------  -------    ---------");
        System.out.println("|       |   | "+placeholder(bins_player_up.get(5))+" |  | "+placeholder(bins_player_up.get(4))+" |  | "+placeholder(bins_player_up.get(3))+" |  | "+placeholder(bins_player_up.get(2))+" |  | "+placeholder(bins_player_up.get(1))+" |  | "+placeholder(bins_player_up.get(0))+" |    |       |");
        System.out.println("|       |   -------  -------  -------  -------  -------  -------    |       |");
        System.out.println("|  "+placeholder(bins_player_up.get(6))+"  |                                                           |  "+placeholder(bins_player_down.get(6))+"  |");
        System.out.println("|       |   -------  -------  -------  -------  -------  -------    |       |");
        System.out.println("|       |   | "+placeholder(bins_player_down.get(0))+" |  | "+placeholder(bins_player_down.get(1))+" |  | "+placeholder(bins_player_down.get(2))+" |  | "+placeholder(bins_player_down.get(3))+" |  | "+placeholder(bins_player_down.get(4))+" |  | "+placeholder(bins_player_down.get(5))+" |    |       |");
        System.out.println("---------   -------  -------  -------  -------  -------  -------    ---------");
        System.out.println("---------   -- 1 --  -- 2 --  -- 3 --  -- 4 --  -- 5 --  -- 6 --    ---------");

    }

    public double heu_1(){
        double value = 0;
        if(this.current_player.equals("Up")) value = weight1*(this.get_stone_in_bin(6,"Up")-this.get_stone_in_bin(6,"Down"));
        else if(this.current_player.equals("Down")) value = weight1*(this.get_stone_in_bin(6,"Down")-this.get_stone_in_bin(6,"Up"));
        //System.out.print("heu1 "+value+" ");
        return value;
    }

    public double heu_2() {
        double value = this.heu_1();
        if(this.current_player.equals("Up")) value += weight2*(this.stones_on_side("Up")-this.stones_on_side("Down"));
        else if(this.current_player.equals("Down")) value += weight2*(this.stones_on_side("Down")-this.stones_on_side("Up"));
        //System.out.print("heu2 "+value+" ");
        return value;
    }

    public double heu_3() {
        double value = this.heu_2();
        //double value=0;
        value += weight3*this.get_free_moves(this.getCurrent_player());
        //if(this.free_moves_up!=0)System.out.println(this.free_moves_up);
        //if(this.free_moves_down!=0)System.out.println(this.free_moves_down);
        //System.out.print("heu3 "+value+" ");
        //if(this.get_free_moves(this.getCurrent_player())!=0) System.out.println("free "+this.get_free_moves(this.getCurrent_player()));
        return value;
    }

    public double heu_4(){
        double value = this.heu_3();
        int how_close_I_am = this.get_stone_in_bin(6,this.getCurrent_player())-24;
        int how_close_opponent_is = this.get_stone_in_bin(6, this.getCurrent_player().equals("Up")?"Down":"Up")-24;
        value+=weight4*(how_close_I_am-how_close_opponent_is);
        //System.out.print("heu4 "+value+" ");
        return value;
    }

    public double heu_5() {
        double value =this.heu_4();
        int stones_close_to_my_storage = this.stones_close_to_storage(this.getCurrent_player());
        int stones_close_to_down_storage = this.stones_close_to_storage(this.getCurrent_player().equals("Up")?"Down":"Up");
        value += weight5*(stones_close_to_my_storage-stones_close_to_down_storage);
        //System.out.print("heu5 "+value+" ");
        return value;
    }

    public double heu_6() { //problem here
        double value = this.heu_5();
        double temp = value;
        //double value=0;
        //value += weight6*this.get_capture(this.getCurrent_player());
        if(this.getCurrent_player().equals("Up")) value+=this.capture_up;
        else value+=this.capture_down; //???????
        /*if (temp!= value)System.out.println("heu6 "+value);
        else System.out.println();*/
        //if((this.get_capture(this.getCurrent_player()))!=0) System.out.println("capture "+this.get_capture(this.getCurrent_player()));
        return value;
    }

}

