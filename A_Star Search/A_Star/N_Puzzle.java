import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class N_Puzzle {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter k");

        int size = input.nextInt();
        Board board = new Board(size, 0, "Start");

        System.out.println("Enter the numbers");

        ArrayList<Integer> values = new ArrayList<>();

        for(int i = 0; i < size; i++){
            for (int j = 0; j < size; j++){
                byte value = input.nextByte();
                /*if((String)value == '*'){
                    values.add(0);
                }
                else */values.add((int)value);
            }
        }
        board.set_values(values);

        /*System.out.println(values);
        System.out.println(board.get_hamming_distance());
        System.out.println(board.get_manhattan_distance());
        System.out.println(board.get_linear_conflict());*/
        board.show_board();
        /*System.out.println(board.is_solvable());*/

        if(board.is_solvable()){
            //-- Hamming Distance--//
            A_Star_Algorithm a_star_hamming = new A_Star_Algorithm();
            a_star_hamming.A_Star_Execute(board, "hamming distance",size);

            System.out.println();
            System.out.println();

            //-- Manhattan Distance --//
            A_Star_Algorithm a_star_manhattan = new A_Star_Algorithm();
            a_star_hamming.A_Star_Execute(board, "manhattan distance",size);

            System.out.println();
            System.out.println();

            //-- Linear Conflict --//
            A_Star_Algorithm a_star_linear_conflict = new A_Star_Algorithm();
            a_star_hamming.A_Star_Execute(board, "linear conflict",size);

            System.out.println();
            System.out.println();

        }

        else{
            System.out.println("The puzzle is not solvable");
        }

    }
}
