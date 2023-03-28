package Interface;

// an algorithm has one specific implementation

public interface OptimizationAlgorithm {
    boolean isRunning();
    String getAlgorithmName();
    int getCount();
    void implementation(String searchString, char c);
    void addOptimizationAlgorithmListener(OptimizationAlgorithmListener listener);
    void removeOptimizationAlgorithmListener(OptimizationAlgorithmListener listener);
}
