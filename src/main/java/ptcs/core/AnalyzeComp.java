package ptcs.core;

import java.util.*;
import java.util.stream.*;
import ptcs.pcs.*;
import ptcs.util.*;

public class AnalyzeComp {

    static public boolean sizeInRange(List<Integer> ls) {
        return ls.size() >= 2 && ls.size() <= 6;
    }

    static public Map<PcsI,Double> analyze(Comp in, PcsLibrary lib) {
        // find every subset of pitches from size 2 to 6. find and normalize pcs, 
        // add to score
        // 
        //  - we look at notes in.notes
        //  - we need just pitches, so map n -> n.pitch and collect
        //    in list Ps (List<Integer>).
        //    (should this be changed to list of pcs now? a set of pcs? when a note appears
        //     more than once, then it appears in more subsets so it emphasizes it. maybe
        //     we want that.)
        //  - find all subsets in List<List<Integer>> allSub. this is going to 
        //    use Number Sets called on Ps. 
        //  - convert each number set to PcsI
        //
        //  - we need to find map of all distinct PcsI, so I guess we create
        //    a set. probably TreeSet. then iterate over it and then count
        //    number of matching items using collector and then . way of counting occurences in a 
        //    java list  
        //  
        // now we need to test this. 
        List<Integer> ps = in.notes.stream()
            .map(n -> n.pitch).collect(Collectors.toList());
        List<List<Integer>> allSub = NumberSets.allSubsets(ps);
        /*
        List<PcsI> normPcs = NumberSets.allSubsets(ps)
            .stream()
            .filter(ls -> sizeInRange(ls))
            .map(l -> lookup(new PcsI(l)))
            .collect(Collectors.toList());
        */ 
        List<PcsI> pcsIs = allSub.stream()
            .filter(ls -> sizeInRange(ls))
            .map(l -> new PcsI(l))
            .collect(Collectors.toList());
        List<PcsI> normPcsIs = pcsIs.stream()
            .map(l -> lib.lookup(l))
            .collect(Collectors.toList());
        // Find all unique PcsI among normalized
        Set<PcsI> pcsSet = new TreeSet<>(normPcsIs);

        // now for each unique normalize PcsI we need to
        // to find out how many times it occurs in allSub
        // 'out' is map of PcsI to how many times it occurs in
        // subsets of pitches in the composition
        Map<PcsI,Double> out = new TreeMap<>();
        for (PcsI p: pcsSet) {
            int f = Collections.frequency(normPcsIs, p);
            out.put(p, (double)f);
        }
        return out;
    }
}