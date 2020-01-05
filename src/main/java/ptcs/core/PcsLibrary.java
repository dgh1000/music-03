package ptcs.core;

import java.util.*;
import java.util.stream.Collectors;

import ptcs.util.NumberSets;

public class PcsLibrary {
    Map<PcsI,PcsI> map;

    public PcsLibrary(int maxN) {
        map = new TreeMap<>();
        for (int i = 2; i <= maxN; i++) {
            createEntriesForSizeN(i);
        }
        Set<PcsI> distinct = new TreeSet<>(map.values());
        System.out.println(distinct.size());
    }

    void createEntriesForSizeN(int n) {
        List<PcsL> l = PcsL.genAllWithN(n);
        for (PcsL p: l) {
            PcsI i = new PcsI(p);
            PcsI i2 = i.normalize();
            map.put(i, i2);
        }

    }
    
    public PcsI lookup(PcsI inx) {
        assert(map.containsKey(inx));
        return map.get(inx);
    }
    
    public boolean containsKey(PcsI inx) {
        return map.containsKey(inx);
    }

    @Override
    public String toString() {
        String out = "";
        out += "size: " + map.size();
        List<PcsI> ks = new ArrayList<>(map.keySet());
        for (int i = 0; i < 10; i++) {
            out += ks.get(i) + " " + map.get(ks.get(i)) + '\n';
        }
        return out;
    }

    static public Set<PcsI> createTestSet(int n) {
        Set<PcsI> s = new TreeSet<>();
        for (int i = 2; i <= n; i++) {
            List<PcsL> all = PcsL.genAllWithN(i);
            System.out.println("i: " + i + " all size: " + all.size());
            for (PcsL one: all) {
                s.add(new PcsI(one));
            }
            System.out.println("Set size: " + s.size());
        }
        return s;
    }

    static public boolean sizeInRange(List<Integer> ls) {
        return ls.size() >= 2 && ls.size() <= 6;
    }

    public Map<PcsI,Double> analyzeComp(Comp in) {
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
            .map(l -> lookup(l))
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