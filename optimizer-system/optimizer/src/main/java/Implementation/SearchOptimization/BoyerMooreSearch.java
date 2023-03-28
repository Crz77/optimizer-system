package Implementation.SearchOptimization;

import Interface.OptimizationAlgorithm;
import Interface.OptimizationAlgorithmEvent;
import Interface.OptimizationAlgorithmListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

// an advanced implementation for the SearchImplementation is the Boyer-Moore algorithm
// its really effective when it searches a pattern in a string
// for this it uses the bad-character-rule -- https://www.cs.jhu.edu/~langmea/resources/lecture_notes/boyer_moore.pdf --
// in this use case it just searches for a letter in a string so the bad-character-rule can't show its potential
// but for the completion its implemented
// like in the Brute-Force-Algorithm this implementation fires two events, one for the progress and one when
// its finished


public class BoyerMooreSearch implements OptimizationAlgorithm {

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
    public void implementation(String searchString, char c) {
        tickerThread.set(new Thread(() -> {
            int progressCount = 0;
            double progress = 0;
            int length = searchString.length();
            int i = length - 1;

            //preprocess target char for bad char rule
            int[] badChar = new int[256];
            Arrays.fill(badChar, -1);
            badChar[c] = 0;

            while(i >= 0){
                sleep(sleepTime);

                if(searchString.charAt(i) == c) {
                    count.getAndIncrement();
                }

                // Use the bad character rule to shift the index to the left
                int j = badChar[searchString.charAt(i)];
                if (j != -1 && j < i) {
                    i -= j + 1;
                } else {
                    i--;
                }

                progressCount++;

                //calculate progress
                progress = ((double) progressCount / length) * 100;

                // fire event
                var event = new OptimizationAlgorithmEvent(this, progress, false);
                listeners.forEach((OptimizationAlgorithmListener listener) -> listener.fireAlgorithmEvent(event));
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
        return "Boyer-Moore-Search";
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
