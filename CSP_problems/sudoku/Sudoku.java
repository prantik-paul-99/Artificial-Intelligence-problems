import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


public class Sudoku {
    public static void main(String[] args) {


        int i, j, k;


// 1. Create a Model
        Model model = new Model("my first sudoku problem");
// 2. Create variables



        /* the board which is 9 X 9 */
        /* (0, 0) is the top left position and (8, 8) is the bottom right position */
        /*each cell is an integer variable taking their value in [1, 9] */
        IntVar[][] bd = model.intVarMatrix("bd", 9, 9, 1, 9);

// 3. Post constraints


        /* post constraints for the given hints or clues */

        model.arithm (bd[0][2], "=", 2).post();
        model.arithm (bd[1][1], "=", 8).post();
        model.arithm (bd[1][4], "=", 3).post();
        model.arithm (bd[1][7], "=", 7).post();

        model.arithm (bd[2][0], "=", 3).post();
        model.arithm (bd[2][3], "=", 5).post();
        model.arithm (bd[2][5], "=", 4).post();
        model.arithm (bd[3][7], "=", 2).post();
        model.arithm (bd[3][8], "=", 8).post();

        model.arithm (bd[4][0], "=", 8).post();
        model.arithm (bd[4][1], "=", 3).post();
        model.arithm (bd[4][4], "=", 1).post();
        model.arithm (bd[5][1], "=", 4).post();
        model.arithm (bd[5][3], "=", 7).post();

        model.arithm (bd[5][6], "=", 3).post();
        model.arithm (bd[5][7], "=", 5).post();
        model.arithm (bd[5][8], "=", 1).post();
        model.arithm (bd[6][1], "=", 7).post();
        model.arithm (bd[6][4], "=", 5).post();
        model.arithm (bd[6][5], "=", 6).post();
        model.arithm (bd[6][8], "=", 4).post();
        model.arithm (bd[7][2], "=", 3).post();

        model.arithm (bd[8][2], "=", 5).post();
        model.arithm (bd[8][3], "=", 4).post();
        model.arithm (bd[8][5], "=", 1).post();
        model.arithm (bd[8][6], "=", 6).post();

        // for rows
        model.allDifferent(bd[0]).post();
        model.allDifferent(bd[1]).post();
        model.allDifferent(bd[2]).post();
        model.allDifferent(bd[3]).post();
        model.allDifferent(bd[4]).post();
        model.allDifferent(bd[5]).post();
        model.allDifferent(bd[6]).post();
        model.allDifferent(bd[7]).post();
        model.allDifferent(bd[8]).post();

        // for columns
        model.allDifferent(bd[0][0],bd[1][0],bd[2][0],bd[3][0],bd[4][0],bd[5][0],bd[6][0],bd[7][0],bd[8][0]).post();
        model.allDifferent(bd[0][1],bd[1][1],bd[2][1],bd[3][1],bd[4][1],bd[5][1],bd[6][1],bd[7][1],bd[8][1]).post();
        model.allDifferent(bd[0][2],bd[1][2],bd[2][2],bd[3][2],bd[4][2],bd[5][2],bd[6][2],bd[7][2],bd[8][2]).post();
        model.allDifferent(bd[0][3],bd[1][3],bd[2][3],bd[3][3],bd[4][3],bd[5][3],bd[6][3],bd[7][3],bd[8][3]).post();
        model.allDifferent(bd[0][4],bd[1][4],bd[2][4],bd[3][4],bd[4][4],bd[5][4],bd[6][4],bd[7][4],bd[8][4]).post();
        model.allDifferent(bd[0][5],bd[1][5],bd[2][5],bd[3][5],bd[4][5],bd[5][5],bd[6][5],bd[7][5],bd[8][5]).post();
        model.allDifferent(bd[0][6],bd[1][6],bd[2][6],bd[3][6],bd[4][6],bd[5][6],bd[6][6],bd[7][6],bd[8][6]).post();
        model.allDifferent(bd[0][7],bd[1][7],bd[2][7],bd[3][7],bd[4][7],bd[5][7],bd[6][7],bd[7][7],bd[8][7]).post();
        model.allDifferent(bd[0][8],bd[1][8],bd[2][8],bd[3][8],bd[4][8],bd[5][8],bd[6][8],bd[7][8],bd[8][8]).post();

        //for boxes
        model.allDifferent(bd[0][0],bd[0][1],bd[0][2],bd[1][0],bd[1][1],bd[1][2],bd[2][0],bd[2][1],bd[2][2]).post();
        model.allDifferent(bd[0][3],bd[0][4],bd[0][5],bd[1][3],bd[1][4],bd[1][5],bd[2][3],bd[2][4],bd[2][5]).post();
        model.allDifferent(bd[0][6],bd[0][7],bd[0][8],bd[1][6],bd[1][7],bd[1][8],bd[2][6],bd[2][7],bd[2][8]).post();
        model.allDifferent(bd[3][0],bd[3][1],bd[3][2],bd[4][0],bd[4][1],bd[4][2],bd[5][0],bd[5][1],bd[5][2]).post();
        model.allDifferent(bd[3][3],bd[3][4],bd[3][5],bd[4][3],bd[4][4],bd[4][5],bd[5][3],bd[5][4],bd[5][5]).post();
        model.allDifferent(bd[3][6],bd[3][7],bd[3][8],bd[4][6],bd[4][7],bd[4][8],bd[5][6],bd[5][7],bd[5][8]).post();
        model.allDifferent(bd[6][0],bd[6][1],bd[6][2],bd[7][0],bd[7][1],bd[7][2],bd[8][0],bd[8][1],bd[8][2]).post();
        model.allDifferent(bd[6][3],bd[6][4],bd[6][5],bd[7][3],bd[7][4],bd[7][5],bd[8][3],bd[8][4],bd[8][5]).post();
        model.allDifferent(bd[6][6],bd[6][7],bd[6][8],bd[7][6],bd[7][7],bd[7][8],bd[8][6],bd[8][7],bd[8][8]).post();



// 4. Solve the problem

        Solver solver = model.getSolver();

        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();


// 5. Print the solution

        for ( i = 0; i < 9; i++)
        {
            for ( j = 0; j < 9; j++)
            {

                System.out.print(" ");
                /* get the value for the board position [i][j] for the solved board */
                k = bd [i][j].getValue();
                System.out.print(k );
            }
            System.out.println();
        }


    }

}
