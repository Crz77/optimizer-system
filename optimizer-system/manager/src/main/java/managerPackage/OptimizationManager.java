package managerPackage;

import Interface.Optimization;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;


// through a service loader the manager load all optimizations
// and foreach optimization he runs all the algorithms (implementations)

public class OptimizationManager {

    public OptimizationManager() {
    }

    public void runOptimization(Optimization optimization, Collection args){
        optimization.runAllAlgorithms(args);
    }

    public List<Optimization> getAllOptimizations(){
        List<Optimization> optimizations = new ArrayList<>();
        optimizations.addAll(loadAllOptimizations());
        return optimizations;
    }

    private static List<Optimization> loadAllOptimizations() {
        List<Optimization> optimizations = new ArrayList<>();
        ServiceLoader<Optimization> loader = ServiceLoader.load(Optimization.class);
        for(Optimization optimization : loader){
            optimizations.add(optimization);
        }
        return optimizations;
    }
}
