import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;

import java.util.ArrayList;


public class suguru_1705071 {

    public static void main(String[] args) {

// 1. Create a Model
        Model model = new Model("my first suguru problem");
// 2. Create variables

        IntVar[][] bd = model.intVarMatrix("bd", 6, 6, 1, 5);
        IntVar[] c1 = model.intVarArray("c1",5, 1, 5);
        IntVar[] c2 = model.intVarArray("c2",5, 1, 5);
        IntVar[] c3 = model.intVarArray("c3",5, 1, 5);
        IntVar[] c4 = model.intVarArray("c4",5, 1, 5);
        IntVar[] c5 = model.intVarArray("c5",5, 1, 5);
        IntVar[] c6 = model.intVarArray("c6",5, 1, 5);
        IntVar[] c7 = model.intVarArray("c7",4, 1, 4);
        IntVar[] c8 = model.intVarArray("c8",2, 1, 2);
// 3. Post constraints


        /* post constraints for the given hints or clues */
        model.arithm (bd[1][0], "=", 2).post();
        model.arithm (bd[1][3], "=", 3).post();
        model.arithm (bd[1][5], "=", 1).post();

        model.arithm (bd[2][4], "=", 4).post();
        model.arithm (bd[3][0], "=", 4).post();
        model.arithm (bd[3][4], "=", 2).post();

        model.arithm (bd[5][3], "=", 4).post();


        model.allDifferent(c1).post();
        model.allDifferent(c2).post();
        model.allDifferent(c3).post();
        model.allDifferent(c4).post();
        model.allDifferent(c5).post();
        model.allDifferent(c6).post();
        model.allDifferent(c7).post();
        model.allDifferent(c8).post();
        // for rows

        model.arithm(bd[0][0],"=",c1[0]).post();
        model.arithm(bd[1][0],"=",c1[1]).post();
        model.arithm(bd[1][1],"=",c1[2]).post();
        model.arithm(bd[2][0],"=",c1[3]).post();
        model.arithm(bd[3][0],"=",c1[4]).post();

        model.arithm(bd[0][1],"=",c2[0]).post();
        model.arithm(bd[0][2],"=",c2[1]).post();
        model.arithm(bd[0][3],"=",c2[2]).post();
        model.arithm(bd[0][4],"=",c2[3]).post();
        model.arithm(bd[1][3],"=",c2[4]).post();

        model.arithm(bd[1][2],"=",c3[0]).post();
        model.arithm(bd[2][1],"=",c3[1]).post();
        model.arithm(bd[2][2],"=",c3[2]).post();
        model.arithm(bd[2][3],"=",c3[3]).post();
        model.arithm(bd[3][2],"=",c3[4]).post();

        model.arithm(bd[2][4],"=",c4[0]).post();
        model.arithm(bd[3][3],"=",c4[1]).post();
        model.arithm(bd[3][4],"=",c4[2]).post();
        model.arithm(bd[3][5],"=",c4[3]).post();
        model.arithm(bd[4][4],"=",c4[4]).post();

        model.arithm(bd[3][1],"=",c5[0]).post();
        model.arithm(bd[4][1],"=",c5[1]).post();
        model.arithm(bd[4][2],"=",c5[2]).post();
        model.arithm(bd[4][3],"=",c5[3]).post();
        model.arithm(bd[5][1],"=",c5[4]).post();

        model.arithm(bd[5][2],"=",c6[0]).post();
        model.arithm(bd[5][3],"=",c6[1]).post();
        model.arithm(bd[5][4],"=",c6[2]).post();
        model.arithm(bd[5][5],"=",c6[3]).post();
        model.arithm(bd[4][5],"=",c6[4]).post();

        model.arithm(bd[0][5],"=",c7[0]).post();
        model.arithm(bd[1][4],"=",c7[1]).post();
        model.arithm(bd[1][5],"=",c7[2]).post();
        model.arithm(bd[2][5],"=",c7[3]).post();

        model.arithm(bd[4][0],"=",c8[0]).post();
        model.arithm(bd[5][0],"=",c8[1]).post();

        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 6; j++){
                for(int k = 0; k < 8; k++)
                {
                    if(k==0){
                        if(i-1!=-1 && j-1!=-1)
                        {
                            model.allDifferent(bd[i][j],bd[i-1][j-1]).post();
                        }
                    }
                    if(k==1){
                        if(i-1!=-1)
                        {
                            model.allDifferent(bd[i][j],bd[i-1][j]).post();
                        }

                    }
                    if(k==2){
                        if(i-1!=-1 && j+1!=6)
                        {
                            model.allDifferent(bd[i][j],bd[i-1][j+1]).post();
                        }

                    }
                    if(k==3){
                        if(j-1!=-1)
                        {
                            model.allDifferent(bd[i][j],bd[i][j-1]).post();
                        }

                    }
                    if(k==4){
                        if(j+1!=6)
                        {
                            model.allDifferent(bd[i][j],bd[i][j+1]).post();
                        }
                    }
                    if(k==5){
                        if(i+1!=6 && j-1!=-1)
                        {
                            model.allDifferent(bd[i][j],bd[i+1][j-1]).post();
                        }

                    }
                    if(k==6){
                        if(i+1!=6)
                        {
                            model.allDifferent(bd[i][j],bd[i+1][j]).post();
                        }
                    }
                    if(k==7){
                        if(i+1!=6 && j+1!=6)
                        {
                            model.allDifferent(bd[i][j],bd[i+1][j+1]).post();
                        }

                    }

                }
            }
        }



// 4. Solve the problem

        Solver solver = model.getSolver();

        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();


// 5. Print the solution

        for (int i = 0; i < 6; i++)
        {
            for (int j = 0; j < 6; j++)
            {

                System.out.print(" ");
                /* get the value for the board position [i][j] for the solved board */
                int k = bd [i][j].getValue();
                System.out.print(k );
            }
            System.out.println();
        }


    }

}
