package ptcs.pcs;

import java.util.*;

public class PcsLibrary {
    Map<PcsI,PcsI> map;

    public PcsLibrary(int maxN) {
        map = new TreeMap<>();
        for (int i = 2; i <= maxN; i++) {
            createEntriesForSizeN(i);
        }
        Set<PcsI> distinct = new TreeSet<>(map.values());
        System.out.println(distinct.size
        ());
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
    
}