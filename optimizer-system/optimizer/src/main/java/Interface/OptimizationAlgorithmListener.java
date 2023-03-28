package Interface;

import java.util.EventListener;

@FunctionalInterface
public interface OptimizationAlgorithmListener extends EventListener {
    void fireAlgorithmEvent(OptimizationAlgorithmEvent event);
}