package sumon;

import java.util.ArrayList;
import java.util.List;

/**
 * @author exthulja
 */
public class MergeSort {

    public <T extends Comparable> List<T> sort(List<T> list) {
        return doSort(list);
    }

    protected <T extends Comparable> List<T> doSort(List<T> list) {
        if (list.size() <= 1) {
            return list;
        } else {
            List<T> left = leftSublist(list);
            List<T> right = rightSublist(list);
            if (left.size() >= 2) {
                left = doSort(left);
            }
            if (right.size() >= 2) {
                right = doSort(right);
            }
            return merge(left, right);
        }
    }

    protected <T extends Comparable> List<T> merge(List<T> left, List<T> right) {
        int leftIndex = 0;
        int rightIndex = 0;
        int size = left.size() + right.size();
        List<T> list = new ArrayList<T>(size);
        for (int i = 0; i < size; i++) {
            if (rightIndex >= right.size()) {
                list.add(left.get(leftIndex++));
            }
            else if (leftIndex >= left.size()) {
                list.add(right.get(rightIndex++));
            }
            else if (right.get(rightIndex).compareTo(left.get(leftIndex)) < 0) {
                list.add(right.get(rightIndex++));
            }
            else {
                list.add(left.get(leftIndex++));
            }
        }
        return list;
    }

    protected <T extends Comparable> List<T> rightSublist(List<T> list) {
        return list.subList(list.size() / 2, list.size());
    }

    protected <T extends Comparable> List<T> leftSublist(List<T> list) {
        return list.subList(0, list.size() / 2);
    }

}
