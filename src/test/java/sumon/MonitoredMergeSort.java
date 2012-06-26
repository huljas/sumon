package sumon;

import java.util.List;

/**
 * @author exthulja
 */
public class MonitoredMergeSort extends MergeSort {
    @Override
    public <T extends Comparable> List<T> sort(List<T> list) {
        Snap snap = Sumon.snap("MergeSort.sort");
        try {
            return super.sort(list);
        } finally {
            snap.done();
        }
    }
}
