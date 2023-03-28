package clientPackage;

import Interface.Optimization;
import managerPackage.OptimizationManager;

import java.util.List;
import java.util.Scanner;


// the client gets all optimizations through the manager
// the user choose an optimization through an input ID
// if the optimization with id x exists the user must input the individual parameters
// which the optimization requires
// if the user input for optimization id is wrong the program exits

public class OptimizerClient {

    private static List<Optimization> getAllOptimizations(OptimizationManager manager){
        return manager.getAllOptimizations();
    }

    public static void main(String[] args) {
        OptimizationManager manager = new OptimizationManager();
        Optimization optimization = null;

        System.out.println();
        System.out.println();
        System.out.println("======================================================");
        System.out.println("        Welcome to the optimizer client        ");
        System.out.println("======================================================");
        System.out.println();
        System.out.println();
        System.out.println("We have the following optimizations for you: ");
        for (var opti : getAllOptimizations(manager)){
            System.out.println(" " + opti.getOptimizationID() + ". " + opti.getOptimizationName());
        }
        System.out.println();
        System.out.println();

        System.out.println("Please choose the optimization you wanna use by typing the ID: ");
        System.out.print("> ");
        int optiID = new Scanner(System.in).nextInt();
        System.out.println();
        for(var opti : getAllOptimizations(manager)){
            if(opti.getOptimizationID() == optiID)
                optimization = opti;
        }

        if(optimization == null) {
            System.out.println("Sorry wrong ID");
        }
        else {
            var userInput = optimization.getOptimizationUI();
            manager.runOptimization(optimization, userInput);
        }
    }
}
