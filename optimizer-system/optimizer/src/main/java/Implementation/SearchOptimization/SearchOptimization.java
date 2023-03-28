package Implementation.SearchOptimization;

import Interface.Optimization;
import Interface.OptimizationAlgorithm;
import Interface.OptimizationAlgorithmEvent;

import java.util.*;


// this optimization includes two algorithms
// the simple brute-force and the advanced boyer-moore
// with the method getAlgorithms() the manager can get
// all algorithms for one optimization
// both algorithms count a char in a string, inform the user
// about the progress and give him/her an output in the end
// how often the char is included in the searchstring


public class SearchOptimization implements Optimization{

    private final String optimizationName = "Search-Optimization";
    private final int optimizationID = 1;

    public SearchOptimization(){
    }

    @Override
    public List<OptimizationAlgorithm> getAlgorithms(){
        return loadAllAlgorithms();
    }

    @Override
    public String getOptimizationName() {
        return optimizationName;
    }

    @Override
    public int getOptimizationID() {
        return optimizationID;
    }

    @Override
    public List<String> getOptimizationUI() {
        List<String> userInput = new ArrayList<>();
        System.out.println();
        System.out.println("======================================================");
        System.out.println("          Welcome to the Search Optimization          ");
        System.out.println("======================================================");
        System.out.println();
        System.out.println("You give us a string and a char and we are looking");
        System.out.println("  how often the char is included in the string  ");
        System.out.println();
        System.out.println();
        System.out.println("Please give us a string you would like to search: ");
        System.out.print("> ");
        String searchString = new Scanner(System.in).nextLine();
        userInput.add(searchString);
        System.out.println("Please give us the char you wanna count: ");
        System.out.print("> ");
        String targetChar = new Scanner(System.in).nextLine();
        userInput.add(targetChar);
        System.out.println();
        System.out.println("Take a coffee while we are working for you...");
        System.out.println();

        return userInput;
    }

    @Override
    public void runAllAlgorithms(Collection args) {
        Object syncObject = new Object();
        var searchParameters = args.toArray();

        for(var algo : getAlgorithms()){
            algo.addOptimizationAlgorithmListener((OptimizationAlgorithmEvent ev) -> {
                synchronized (syncObject) {
                    if (ev.isFinished()) {
                        System.out.println();
                        System.out.println("===================================");
                        System.out.println(algo.getAlgorithmName() + " is finished");
                        System.out.println("Count: " + algo.getCount());
                        System.out.println("===================================");
                        System.out.println();
                    } else
                        System.out.printf("%s: %.2f%%\n", algo.getAlgorithmName(), ev.getProgress());
                }

            });


            String arg1 = searchParameters[0].toString();
            char arg2 = searchParameters[1].toString().charAt(0);

            algo.implementation(arg1, arg2);
        }
    }

    private static List<OptimizationAlgorithm> loadAllAlgorithms() {
        List<OptimizationAlgorithm> algos = new ArrayList<>();
        ServiceLoader<OptimizationAlgorithm> loader = ServiceLoader.load(OptimizationAlgorithm.class);
        for(OptimizationAlgorithm algo : loader){
            algos.add(algo);
        }
        return algos;
    }

}
