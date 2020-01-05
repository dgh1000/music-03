package ptcs;

import java.util.*;
import ptcs.core.*;
import ptcs.util.*;
import java.util.stream.*;

public class TestPcs {
    public static void testAnalyze() {
        // okay so here we have to create a comp with certain
        // pitches. maybe take 
        int[] ps = {0, 1, 3, 6, 7};
        Set<Note> ls = Arrays.stream(ps)
            .mapToObj(i -> new Note(0, 1, i))
            .collect(Collectors.toSet());
        Comp c = new Comp(ls);
        PcsLibrary pl = new PcsLibrary(6);
        Map<PcsI,Double> m = pl.analyzeComp(c);
        // oh yeah we can't have static 
        FormatMap<PcsI,Double> fm = new FormatMap<>();
        fm.writeMap(m);
    }

    public static Note mkTestNote(int pitch) {
        return new Note(0, 1, pitch);
    }
}