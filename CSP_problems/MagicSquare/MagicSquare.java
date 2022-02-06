import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solver;
import org.chocosolver.solver.variables.IntVar;


public class MagicSquare {
    public static void main(String[] args) {
        int n = 4, sum = 34, up = 16 , low = 1;


// 1. Create a Model
        Model model = new Model("my first magic square problem");
// 2. Create variables


        IntVar[][] bd = model.intVarMatrix("bd", n, n, low, up);
        IntVar[] bdtmp = model.intVarArray("bdtmp", n*n, low, up);
        IntVar[][] col = model.intVarMatrix("col",n,n,low,up);
        IntVar[] corn1 = model.intVarArray("corn1",n,low, up);
        IntVar[] corn2 = model.intVarArray("corn2",n,low, up);

// 3. Post constraints
        int k = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++) {
                model.arithm(bdtmp[k],"=",bd[i][j]).post();
                k++;
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < n; j++) {
                model.arithm(col[j][i],"=",bd[i][j]).post();
            }
        }

        for(int i = 0; i < n; i++){
            model.arithm(corn1[i],"=",bd[i][i]).post();
        }
        for(int i = 0; i < n; i++){
            model.arithm(corn2[i],"=",bd[i][n-i-1]).post();
        }

        /* post constraints for the given hints or clues */
        model.allDifferent(bdtmp).post();

        for(int i = 0; i < n; i++){
            model.sum(bd[i],"=",sum).post();
            model.sum(col[i],"=",sum).post();
        }

        model.sum(corn1,"=",sum).post();
        model.sum(corn2,"=",sum).post();


// 4. Solve the problem

        Solver solver = model.getSolver();

        solver.showStatistics();
        solver.showSolutions();
        solver.findSolution();


// 5. Print the solution

        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {

                System.out.print("\t");
                /* get the value for the board position [i][j] for the solved board */
                k = bd [i][j].getValue();
                System.out.print(k );
            }
            System.out.println();
        }


    }

}
