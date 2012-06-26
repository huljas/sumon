package sumon;

/**
 * @author exthulja
 */
public class Report {

    public String key;

    public double average;
    public double stdev;
    public long max;
    public long min;
    public double total;
    public long count;

    public double avgActive;
    public long maxActive;
    public long firstAccess;
    public long lastAccess;

    public Report() {
    }

    public Report(Report report) {
        this.key = report.key;
        this.average = report.average;
        this.stdev = report.stdev;
        this.max = report.max;
        this.min = report.min;
        this.total = report.total;
        this.count = report.count;
        this.avgActive = report.avgActive;
        this.maxActive = report.maxActive;
        this.firstAccess = report.firstAccess;
        this.lastAccess = report.lastAccess;
    }
}
