package sumon;

import java.util.HashMap;
import java.util.Map;

/**
 * @author exthulja
 */
public class StackReport extends Report {

    Map<String, StackReport> children = new HashMap<String, StackReport>();

    public StackReport(Report report) {
        super(report);
    }

    public void addChild(StackReport child) {
        children.put(child.key, child);
    }

    public StackReport child(String key) {
        return children.get(key);
    }
}
