import java.util.*;

public class minimax_algo {
    double infinity = 100000000;


    public Result_value minimax(Board board, int depth, double alpha, double beta, boolean is_max, int heuristics_variable, int move_ordering_variable){
        //if(is_max) System.out.println("max");
        //else System.out.println("min");
        if(depth == 0 || board.is_game_over()){
            Result_value result_value;
            switch (heuristics_variable){
                case 1:{
                    result_value = new Result_value(board.heu_1(),board.get_prnt_bin_pos());
                    break;
                }
                case 2:{
                    result_value = new Result_value(board.heu_2(),board.get_prnt_bin_pos());
                    break;
                }
                case 3:{
                    result_value = new Result_value(board.heu_3(),board.get_prnt_bin_pos());
                    break;
                }
                case 4:{
                    result_value = new Result_value(board.heu_4(),board.get_prnt_bin_pos());
                    break;
                }
                case 5:{
                    result_value = new Result_value(board.heu_5(),board.get_prnt_bin_pos());
                    break;
                }
                default:{
                    result_value = new Result_value(board.heu_6(),board.get_prnt_bin_pos());
                    break;
                }
            }

            //System.out.println("value "+board.heu_6());
            return result_value;
        }

        if(is_max){
            double max_val = -infinity;
            ArrayList<Board> children = board.get_children(board, board.getCurrent_player(), move_ordering_variable);

            Result_value result = new Result_value(0,0);
            Result_value result_temp;
            int parent_bin = -1;

            for(int i = 0; i < children.size(); i++){

                //System.out.println("in algo "+children.get(i).get_capture("Up")+" "+children.get(i).get_capture("Down"));

                if(board.getCurrent_player().equals(children.get(i).getCurrent_player())){
                    result_temp = minimax(children.get(i), depth-1, alpha, beta, true, heuristics_variable, move_ordering_variable);
                }
                else{
                    result_temp = minimax(children.get(i), depth-1, alpha, beta, false, heuristics_variable, move_ordering_variable);
                }
                //System.out.println("in algo "+children.get(i).get_capture(board.getCurrent_player())+" from bin" +children.get(i).get_prnt_bin_pos());

                if(result_temp.value > max_val){
                    parent_bin = children.get(i).get_prnt_bin_pos();
                    //System.out.println(children.get(i).get_prnt_bin_pos());
                    //System.out.println(board.get_stone_in_bin(result.parent_bin,board.getCurrent_player()));
                }
                max_val = Math.max(max_val, result_temp.value);

                alpha = Math.max(alpha, result_temp.value);
                /*System.out.println("in max func");
                System.out.println("positions " + parent_bin);*/


                if (beta <= alpha) break;

            }
            result.value = max_val;
            result.parent_bin = parent_bin;
            return result;
        }
        else{
            double min_val = infinity;
            ArrayList<Board> children = board.get_children(board, board.getCurrent_player(), move_ordering_variable);
            int parent_bin = -1;
            Result_value result = new Result_value(0,0);
            Result_value result_temp;

            for(int i = 0; i < children.size(); i++){

                //System.out.println("in algo "+children.get(i).get_capture("Up")+" "+children.get(i).get_capture("Down"));

                if(board.getCurrent_player().equals(children.get(i).getCurrent_player())){
                    result_temp = minimax(children.get(i), depth-1, alpha, beta, false, heuristics_variable, move_ordering_variable);
                }
                else{
                    result_temp = minimax(children.get(i), depth-1, alpha, beta, true, heuristics_variable, move_ordering_variable);
                }

                //System.out.println("in algo "+children.get(i).get_capture(board.getCurrent_player()));

                if(result_temp.value < min_val){
                    parent_bin = children.get(i).get_prnt_bin_pos();
                    //System.out.println(children.get(i).get_prnt_bin_pos());
                    //System.out.println(board.get_stone_in_bin(result.parent_bin,board.getCurrent_player()));
                }
                min_val = Math.min(min_val, result_temp.value);

                beta = Math.min(beta, result_temp.value);

                if (beta <= alpha) break;

            }
            result.value = min_val;
            result.parent_bin = parent_bin;
            return result;
        }
    }
}
