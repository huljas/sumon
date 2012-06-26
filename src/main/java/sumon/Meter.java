package sumon;

/**
 * @author exthulja
 */
public interface Meter {

    public void start(long start);

    public void stop(long start, long end);

}
