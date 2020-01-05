package ptcs.core;

import java.util.*;

public class NormTable {

    Map<PcsI,PcsI> pcsMap;

    public NormTable(int minN, int maxN) {
        pcsMap = new TreeMap<>();
        for (int j = minN; j <= maxN; ++j) {
            List<PcsL> li = PcsL.genAllWithN(j);
            for (PcsL p: li) {
                PcsI i1 = new PcsI(p);
                PcsI i2 = i1.normalize();
                pcsMap.put(i1, i2);
            }
        }
    }

    public PcsI lookupNorm(PcsI p) throws Exception {
        PcsI o = pcsMap.get(p);
        if (o == null)
            throw new Exception("lookupNorm");
        return o;
    }
}