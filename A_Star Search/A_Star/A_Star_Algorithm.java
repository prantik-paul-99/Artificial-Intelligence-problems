import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class A_Star_Algorithm {

    public void A_Star_Execute(Board initial_board, String strategy, int size){

        HashMap<Board,Integer> open_list =  new HashMap<>();
        //HashMap<Board, Integer> closed_list = new HashMap<>();
        ArrayList<Board> closed_list = new ArrayList<>();
        ArrayList<Board> temp_list = new ArrayList<>();

        Board goal_board = new Board(size, 0, "End"); //create the goal_board
        ArrayList<Integer> values = new ArrayList<>();
        for(int i = 1; i <= size*size; i++){
            if(i==size*size) values.add(0);
            else values.add(i);
        }
        goal_board.set_values(values);

        Board temp_board = null;

        int explored = 0, expanded = 0;

        open_list.put(initial_board,initial_board.get_priority_function_value(strategy));

        while(open_list.size()!=0){
            int min = Collections.min(open_list.values());
            boolean min_found = false;
            for(Map.Entry<Board,Integer> entry: open_list.entrySet()){
                if(entry.getValue().equals(min)){
                    closed_list.add(entry.getKey());
                    //entry.getKey().show_board();
                    temp_list = entry.getKey().make_a_move(); //get neighbour lists
                    for(int i = 0; i < temp_list.size(); i++){
                        open_list.put(temp_list.get(i), temp_list.get(i).get_priority_function_value(strategy)); //put neighbours in open list
                    }
                    temp_board = entry.getKey();
                    open_list.remove(entry.getKey()); // pop the min board from list
                    explored++;
                    min_found = true;
                }
                if(min_found) break;
            }
            if(temp_board.is_same(goal_board)) break;
            temp_board = null;
        }

        System.out.println("Using "+strategy);
        System.out.println("Expanded nodes : "+(closed_list.size()-1));
        System.out.println("Explored nodes : "+(open_list.size()+explored));

        System.out.println("Steps:");

        temp_board = closed_list.get(closed_list.size()-1);
        temp_list.clear();

        while(temp_board!=null){
            temp_list.add(temp_board);
            temp_board = temp_board.getPrevious_board();
        }

        //int cost = 0;

        for(int i = temp_list.size()-1; i >= 0; i--){
            temp_list.get(i).show_board();
        }

        System.out.println("Cost = "+(temp_list.size()-1));

        closed_list.clear();
        open_list.clear();
        temp_list.clear();
    }
}
