package sumon;

/**
 * @author exthulja
 */
public class CompositeMeter implements Meter {

    public Meter[] meters;

    public CompositeMeter(Meter ... meters) {
        this.meters = meters;
    }

    public void start(long start) {
        for (Meter meter : meters) {
            meter.start(start);
        }
    }

    public void stop(long start, long end) {
        for (Meter meter : meters) {
            meter.stop(start, end);
        }
    }
}
