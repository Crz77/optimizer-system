package Interface;

import java.util.EventObject;

public class OptimizationAlgorithmEvent extends EventObject {
    private double progress;
    private boolean isFinished;

    public double getProgress() {
        return progress;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public OptimizationAlgorithmEvent(Object source, double progress, boolean isFinished) {
        super(source);
        this.progress = progress;
        this.isFinished = isFinished;
    }

    public OptimizationAlgorithmEvent(Object source) {
        super(source);
    }
}
