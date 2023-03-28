package Implementation.SearchOptimization;

import Interface.OptimizationAlgorithm;
import Interface.OptimizationAlgorithmEvent;
import Interface.OptimizationAlgorithmListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// a simple implementation for the SearchOptimization is the Brute-Force algorithm
// it gets started in a new thread and fires one event for continuous progress
// and one when its finished

public class BruteForceSearch implements OptimizationAlgorithm {

    private AtomicInteger                       count = new AtomicInteger();
    private int                                 sleepTime = 100;
    private List<OptimizationAlgorithmListener> listeners = new ArrayList<>();
    private AtomicReference<Thread>             tickerThread = new AtomicReference<>(null);


    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    public int getCount(){
        return count.get();
    }

    @Override
    public void implementation(String searchString, char c){
        tickerThread.set(new Thread(() -> {
            double progress = 0;
            double step = 0;
            double length = searchString.length();

            for(int i = 0; i<searchString.length(); ++i){
                sleep(sleepTime);

                if(searchString.charAt(i) == c) {
                    count.getAndIncrement();
                }

                //calculate progress
                step = i + 1;
                progress =  (step / length) * 100;

                // fire progress event
                var progressEvent = new OptimizationAlgorithmEvent(this, progress, false);
                listeners.forEach((OptimizationAlgorithmListener listener) -> listener.fireAlgorithmEvent(progressEvent));
            }
            // fire finished event
            var finishedEvent = new OptimizationAlgorithmEvent(this, progress, true);
            listeners.forEach((OptimizationAlgorithmListener listener) -> listener.fireAlgorithmEvent(finishedEvent));

            tickerThread.set(null);
        }));
        tickerThread.get().start();
    }



    @Override
    public boolean isRunning() {
        return tickerThread.get() != null;
    }

    @Override
    public String getAlgorithmName() {
        return "Brute-Force-Search";
    }

    @Override
    public void addOptimizationAlgorithmListener(OptimizationAlgorithmListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeOptimizationAlgorithmListener(OptimizationAlgorithmListener listener) {
        listeners.remove(listener);
    }

}
