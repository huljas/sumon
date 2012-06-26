package sumon;

/**
 * @author exthulja
 */
public class Clock {

    private static Clock instance = new Clock();

    public static void setClock(Clock clock) {
        instance = clock;
    }

    public static long time() {
        return instance.getTime();
    }

    protected long getTime() {
        return System.currentTimeMillis();
    }

    public static class TestClock extends Clock {

        private final long time;

        public TestClock(long time) {
            this.time = time;
        }

        @Override
        protected long getTime() {
            return time;
        }
    }

}
