import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Mancala_Play {
    private static Board board;
    private static boolean change_counter = true;
    private static minimax_algo minimax = new minimax_algo();
    static double infinity = 100000000;
    private static final int depth = 10;
    private static float up_win_count = 0;
    private static float down_win_count = 0;
    private static float draw_count = 0;
    private static float total_game_count = 0;
    private static float heu1_win_count = 0;
    private static float heu2_win_count = 0;
    private static float heu3_win_count = 0;
    private static float heu4_win_count = 0;
    private static float heu5_win_count = 0;
    private static float heu6_win_count = 0;
    public static String winner = "";

    private static FileWriter myWriter;

    private static void update_win_heu_val(int heuristic){
        switch (heuristic) {
            case 1 -> {
                heu1_win_count++;
            }
            case 2 -> {
                heu2_win_count++;
            }
            case 3 -> {
                heu3_win_count++;
            }
            case 4 -> {
                heu4_win_count++;
            }
            case 5 -> {
                heu5_win_count++;
            }
            case 6 -> {
                heu6_win_count++;
            }
        }
    }

    static String mode = "AI_vs_Human"; // AI_vs_AI AI_vs_Human Human_vs_Human

    public static void main(String[] args) throws InterruptedException, IOException {
        /*board = new Board();
        board.set_prnt_bin_pos(-1);
        board.set_free_moves(0,"Up");
        board.set_free_moves(0,"Down");
        board.set_capture(0,"Up");
        board.set_capture(0,"Down");
        board.show_board();*/

        int max = 100, min = 0;

        String[] turn = new String[]{"Down", "Up"};

        int counter = 0;

        if(mode.equals("AI_vs_AI")){

            myWriter = new FileWriter("stat.txt");

            for(int depth_variable = 1; depth_variable < 2; depth_variable++){

                System.out.println("First move by "+turn[0]+" player");
                myWriter.write("First move by "+turn[0]+" player");
                myWriter.write('\n');

                int[] depth = new int[2];
                depth[0] = (int)Math.floor(Math.random()*(12-3+1)+3);
                System.out.println("Down player's depth = "+depth[0]);
                myWriter.write("Down player's depth = "+depth[0]);
                myWriter.write('\n');
                depth[1] = (int)Math.floor(Math.random()*(12-3+1)+3);
                System.out.println("Up player's depth = "+depth[1]);
                myWriter.write("Up player's depth = "+depth[1]);
                myWriter.write('\n');

                for(int heuristics_variable1 = 1; heuristics_variable1 < 7; heuristics_variable1++){
                    for(int heuristics_variable2 = 1; heuristics_variable2 < 7; heuristics_variable2++){

                        System.out.println("Down player using heuristics "+heuristics_variable1);
                        System.out.println("Up player using heuristics "+heuristics_variable2);
                        myWriter.write("Down player using heuristics "+heuristics_variable1);
                        myWriter.write('\n');
                        myWriter.write("Up player using heuristics "+heuristics_variable2);
                        myWriter.write('\n');

                        int heuristic[] = {heuristics_variable1, heuristics_variable2};

                        for(int move_ordering_variable = 1; move_ordering_variable < 4; move_ordering_variable++){

                            if(move_ordering_variable==1) System.out.println("Generating children 0-5");
                            else if(move_ordering_variable==2) System.out.println("Generating children 5-0");
                            else System.out.println("Generating children in random order");

                            if(move_ordering_variable==1) myWriter.write("Generating children 0-5");
                            else if(move_ordering_variable==2) myWriter.write("Generating children 5-0");
                            else myWriter.write("Generating children in random order");
                            myWriter.write('\n');

                        /*int random = (int)Math.floor(Math.random()*(max-min+1)+min);
                        if(random%2==1) turn = new String[]{"Down", "Up"};
                        else turn = new String[]{"Up", "Down"};*/

                            board = new Board();
                            board.set_prnt_bin_pos(-1);
                            board.set_free_moves(0,"Up");
                            board.set_free_moves(0,"Down");
                            board.set_capture(0,"Up");
                            board.set_capture(0,"Down");

                            //board.show_board();
                            while(true){
                                board.setCurrent_player(turn[counter%2]);
                                boolean is_over = auto_play(turn[counter%2],(turn[counter%2].equals("Down"))?depth[0]:depth[1],(turn[counter%2].equals("Down"))?heuristic[0]:heuristic[1], move_ordering_variable);
                                if(change_counter) counter++;
                                if(is_over) break;
                                //Thread.sleep(300);
                            }
                            total_game_count++;
                            if(winner.equals("Up")) update_win_heu_val(heuristics_variable2);
                            else if (winner.equals("Down")) update_win_heu_val(heuristics_variable1);
                        }
                    }

                }
            }

            System.out.println("total_games_played "+total_game_count);
            System.out.println("Up player won "+up_win_count);
            System.out.println("Down player won "+down_win_count);
            System.out.println("Tied "+draw_count);
            System.out.println("Up player win percentage "+(double)((up_win_count/total_game_count)*100));
            System.out.println("Down player win percentage "+(double)((down_win_count/total_game_count)*100));
            System.out.println("Tie percentage count "+(double)((draw_count/total_game_count)*100));
            System.out.println("Heuristic 1 win count "+heu1_win_count);
            System.out.println("Heuristic 2 win count "+heu2_win_count);
            System.out.println("Heuristic 3 win count "+heu3_win_count);
            System.out.println("Heuristic 4 win count "+heu4_win_count);
            System.out.println("Heuristic 5 win count "+heu5_win_count);
            System.out.println("Heuristic 6 win count "+heu6_win_count);

            myWriter.write("total_games_played "+total_game_count);
            myWriter.write('\n');
            myWriter.write("Up player won "+up_win_count);
            myWriter.write('\n');
            myWriter.write("Down player won "+down_win_count);
            myWriter.write('\n');
            myWriter.write("Tied "+draw_count);
            myWriter.write('\n');
            myWriter.write("Up player win percentage "+(up_win_count/total_game_count)*100);
            myWriter.write('\n');
            myWriter.write("Down player win percentage "+(down_win_count/total_game_count)*100);
            myWriter.write('\n');
            myWriter.write("Tie percentage count "+(draw_count/total_game_count)*100);
            myWriter.write('\n');
            myWriter.write("Heuristic 1 win count "+heu1_win_count);
            myWriter.write('\n');
            myWriter.write("Heuristic 2 win count "+heu2_win_count);
            myWriter.write('\n');
            myWriter.write("Heuristic 3 win count "+heu3_win_count);
            myWriter.write('\n');
            myWriter.write("Heuristic 4 win count "+heu4_win_count);
            myWriter.write('\n');
            myWriter.write("Heuristic 5 win count "+heu5_win_count);
            myWriter.write('\n');
            myWriter.write("Heuristic 6 win count "+heu6_win_count);
            myWriter.write('\n');

            myWriter.flush();
            myWriter.close();
        }

        else if(mode.equals("AI_vs_Human")){
            board = new Board();
            board.set_prnt_bin_pos(-1);
            board.set_free_moves(0,"Up");
            board.set_free_moves(0,"Down");
            board.set_capture(0,"Up");
            board.set_capture(0,"Down");

            board.show_board();

           /* String AI = turn[((int)Math.floor(Math.random()*(max-min+1)+min))%2];
            String human = (AI.equals("Up")?"Down":"Up");*/
            String AI = "Up";
            String human = "Down";
            System.out.println("You have the "+human+" bins");
            String current_turn = human;

            boolean is_over;
            while(true){
                if(current_turn.equals(human)){
                    board.setCurrent_player(human);
                    is_over = manual_play(human);
                }
                else{
                    board.setCurrent_player(AI);
                    is_over = auto_play(AI, depth, 6, 3);
                }
                if(change_counter) {
                    current_turn = current_turn.equals(human)?AI:human;
                }
                if(is_over) break;
            }
        }
        else if(mode.equals("Human_vs_Human")){
            board = new Board();
            board.set_prnt_bin_pos(-1);
            board.set_free_moves(0,"Up");
            board.set_free_moves(0,"Down");
            board.set_capture(0,"Up");
            board.set_capture(0,"Down");
            board.show_board();

            while(true){
                boolean is_over = manual_play(turn[counter%2]);
                if(change_counter) counter++;
                if(is_over) break;
            }
        }

    }

    private static boolean auto_play(String state, int depth, int heuristics_variable, int move_ordering_variable) throws IOException {
        //System.out.println(state+" player's turn");
        if(!mode.equals("AI_vs_AI")) System.out.println("AI's turn");

        int bin = minimax.minimax(board,depth,-infinity,infinity,true,heuristics_variable, move_ordering_variable).parent_bin;
        /*System.out.println(board.get_free_moves(state));
        System.out.println(board.get_capture(state));*/

        //System.out.println(state+ " player chose bin "+(bin+1));
        if(!mode.equals("AI_vs_AI")) System.out.println("AI chose bin "+(bin+1));

        int return_val = board.move(bin, state, true); //-1 -> don't change player -2 -> change player  others(steal count) -> made a steal

        change_counter = false;
        if(return_val != -1 ) change_counter = true;

        //change_counter = board.move(bin, state, false);
        if(!mode.equals("AI_vs_AI")) board.show_board();
        //board.show_board();

        if(board.is_game_over()){
            int up_storage_count = board.stones_in_storage("Up");
            int down_storage_count = board.stones_in_storage("Down");
            if(up_storage_count>down_storage_count){
                if(mode.equals("AI_vs_Human")) System.out.println("Sorry! AI won!");
                if(mode.equals("AI_vs_AI")){
                    System.out.println("Up player won");
                    myWriter.write("Up player won");
                    myWriter.write('\n');
                    up_win_count++;
                    winner = "Up";
                }
            }
            else if(up_storage_count<down_storage_count){
                if(mode.equals("AI_vs_Human")) System.out.println("Congrats! You won! You beat the AI!");
                if(mode.equals("AI_vs_AI")) {
                    System.out.println("Down player won");
                    myWriter.write("Down player won");
                    myWriter.write('\n');
                    down_win_count++;
                    winner = "Down";
                }
            }
            else{
                System.out.println("Game tied");
                if(mode.equals("AI_vs_AI")) {
                    myWriter.write("Game tied");
                    myWriter.write('\n');
                    draw_count++;
                    winner = "tie";
                }
            }
            //board.show_board();
            return true;
        }
        return false;
    }

    private static boolean manual_play(String state){
        Scanner input = new Scanner(System.in);
        //System.out.println(state+" player's turn");
        System.out.println("Your turn");
        System.out.println("Enter your bin no");

        int bin = input.nextInt();
        //System.out.println(state+ " player chose bin "+(bin+1));
        System.out.println("You chose bin "+bin);
        //check bin validity
        int return_val = board.move(bin-1, state, false); //-1 -> don't change player -2 -> change player  others(steal count) -> made a steal

        change_counter = false;
        if(return_val !=-1 ) change_counter = true;


        //change_counter = board.move(bin-1, state, false);
        board.show_board();

        if(board.is_game_over()){
            int up_storage_count = board.stones_in_storage("Up");
            int down_storage_count = board.stones_in_storage("Down");
            if(up_storage_count>down_storage_count){
                System.out.println("Sorry! AI won!");
            }
            else if(up_storage_count<down_storage_count){
                System.out.println("Congrats! You won! You beat the AI!");
            }
            else{
                System.out.println("Game tied");
            }
            board.show_board();
            return true;
        }
        return false;
    }
}
