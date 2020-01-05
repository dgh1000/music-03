package ptcs.util;

import java.util.*;
import java.util.stream.*;

public class NumberSets {

    public static List<List<Integer>> allSubsets(List<Integer> inputList) {
        int s = inputList.size();
        assert(s <= 24);
        List<List<Integer>> out = new ArrayList<>();
        for (int i = 1; i < (1 << s); i++) {
            out.add(selectionToSubset(inputList, i));
        }
        return out;
    }

    public static List<List<Integer>> allSubsetsFilterSize(
        List<List<Integer>> inputList, int minN, int maxN) {
        
        return inputList.stream()
            .filter(l -> inSizeRange(l, minN, maxN))
            .collect(Collectors.toList());
    }
    
    private static boolean inSizeRange(List<Integer> l, int minN, int maxN) {
        return minN <= l.size() && l.size() <= maxN;
    }

    private static List<Integer> selectionToSubset(List<Integer> input, int selection) {
        assert(input.size() <= 24);
        List<Integer> out = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            if ((selection & (1 << i)) > 0) {
                out.add(input.get(i));
            }
        }
        return out;
    }


}