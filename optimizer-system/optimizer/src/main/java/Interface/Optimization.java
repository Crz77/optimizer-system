package Interface;


import java.util.Collection;
import java.util.List;

public interface Optimization<T> {
    List<OptimizationAlgorithm> getAlgorithms();
    String getOptimizationName();
    int getOptimizationID();
    List<String> getOptimizationUI();
    void runAllAlgorithms(Collection args);
}
